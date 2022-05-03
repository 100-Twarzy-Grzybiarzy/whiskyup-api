package pl.kat.ue.whiskyup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.dto.SearchWhiskiesDto;
import pl.kat.ue.whiskyup.mapper.PaginationCursorMapper;
import pl.kat.ue.whiskyup.mapper.WhiskyBaseMapper;
import pl.kat.ue.whiskyup.model.*;
import pl.kat.ue.whiskyup.repository.WhiskyRepository;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WhiskyService {

    private final WhiskyRepository whiskyRepository;
    private final WhiskyBaseMapper whiskyBaseMapper;
    private final PaginationCursorMapper paginationCursorMapper;

    public static final LocalDate OLDEST_SAVED_DATE = LocalDate.parse("2013-07-27");
    public static final Integer PAGE_LIMIT = 25;
    public static final Integer EMPTY_DAYS_LIMIT = 60;

    public WhiskiesFindResultDto getWhiskies(SearchWhiskiesDto searchDto) {
        Map<String, AttributeValue> exclusiveStartKey = paginationCursorMapper.mapFromCursor(searchDto.getPageCursor());
        Page<WhiskyBase> page = getPageOfWhisky(searchDto, exclusiveStartKey);

        List<WhiskyDto> whiskies = page.items().stream()
                .map(whiskyBaseMapper::mapModelToDto)
                .collect(Collectors.toList());

        String pageCursor = paginationCursorMapper.mapToCursor(page.lastEvaluatedKey());

        return new WhiskiesFindResultDto()
                .results(whiskies)
                .pageCursor(pageCursor);
    }

    private Page<WhiskyBase> getPageOfWhisky(SearchWhiskiesDto searchDto,
                                             Map<String, AttributeValue> exclusiveStartKey) {

        FilterTypeDto filterType = searchDto.getFilter();
        Page<WhiskyBase> page;

        if (FilterTypeDto.BRAND.equals(filterType)) {
            page = getWhiskiesByBrand(searchDto, exclusiveStartKey);

        } else if (FilterTypeDto.PRICERANGE.equals(filterType)) {
            page = getWhiskiesByPriceRange(searchDto, exclusiveStartKey);

        } else {
            page = getWhiskies(exclusiveStartKey);
        }

        return page;
    }

    private Page<WhiskyBase> getWhiskiesByBrand(SearchWhiskiesDto searchDto, Map<String, AttributeValue> exclusiveStartKey) {
        return whiskyRepository.getWhiskiesByBrand(searchDto, exclusiveStartKey);
    }

    private Page<WhiskyBase> getWhiskiesByPriceRange(SearchWhiskiesDto searchDto, Map<String, AttributeValue> exclusiveStartKey) {
        return whiskyRepository.getWhiskiesByPriceRange(searchDto, exclusiveStartKey);
    }

    private Page<WhiskyBase> getWhiskies(Map<String, AttributeValue> exclusiveStartKey) {
        LocalDate lastSeenDate = extractLastSeenDate(exclusiveStartKey);
        List<WhiskyBase> fetched = new ArrayList<>();
        int emptyDayCounter = 0;

        while (shouldFetchWhisky(fetched, lastSeenDate, emptyDayCounter)) {
            int limit = PAGE_LIMIT - fetched.size();
            Page<WhiskyBase> page = whiskyRepository.getWhiskies(exclusiveStartKey, lastSeenDate, limit);
            fetched.addAll(page.items());
            exclusiveStartKey = page.lastEvaluatedKey();

            if (exclusiveStartKey == null) {
                lastSeenDate = lastSeenDate.minusDays(1);
            }

            if (page.items().size() == 0) {
                emptyDayCounter++;
            }
        }

        return Page.create(fetched, exclusiveStartKey);
    }

    private static LocalDate extractLastSeenDate(Map<String, AttributeValue> exclusiveStartKey) {
        String lastSeenDate = Optional.ofNullable(exclusiveStartKey)
                .map(map -> map.get("GSI1PK").s().replace(WhiskyBase.GSI1_PK_PREFIX, ""))
                .orElseGet(() -> LocalDate.now().toString());

        return LocalDate.parse(lastSeenDate);
    }

    private static boolean shouldFetchWhisky(List<WhiskyBase> fetched,
                                             LocalDate lastSeenDate,
                                             int emptyDayCounter) {

        return fetched.size() < PAGE_LIMIT &&
                lastSeenDate.isAfter(OLDEST_SAVED_DATE) &&
                emptyDayCounter < EMPTY_DAYS_LIMIT;
    }

    public void addWhisky(WhiskyBase whiskyBase) {
        whiskyRepository.addWhisky(whiskyBase);
    }

    public UrlsFindResultDto getWhiskiesUrls(String pageCursor){
        Map<String, AttributeValue> exclusiveStartKey = paginationCursorMapper.mapFromCursor(pageCursor);
        Page<WhiskyBase> page = whiskyRepository.getWhiskiesUrls(exclusiveStartKey);

        List<String> urls = page.items().stream()
                .map(WhiskyBase::getUrl)
                .collect(Collectors.toList());

        String nextPageCursor = paginationCursorMapper.mapToCursor(page.lastEvaluatedKey());

        return new UrlsFindResultDto()
                .results(urls)
                .pageCursor(nextPageCursor);
    }
}