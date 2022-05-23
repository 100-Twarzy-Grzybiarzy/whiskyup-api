package pl.kat.ue.whiskyup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.dto.SearchWhiskiesDto;
import pl.kat.ue.whiskyup.dynamometadata.AttributeNames;
import pl.kat.ue.whiskyup.dynamometadata.AttributeValues;
import pl.kat.ue.whiskyup.mapper.PaginationCursorMapper;
import pl.kat.ue.whiskyup.mapper.WhiskyMapper;
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
    private final WhiskyMapper whiskyMapper;
    private final PaginationCursorMapper paginationCursorMapper;

    public static final LocalDate OLDEST_SAVED_DATE = LocalDate.parse("2013-07-27");
    public static final Integer PAGE_LIMIT = 25;
    public static final Integer EMPTY_DAYS_LIMIT = 12 * 30;
    public static Integer APPROXIMATE_TOTAL_PAST_WHISKY_NUMBER = 50 * 434 - 1;

    public WhiskiesFindResultDto getWhiskies(SearchWhiskiesDto searchDto) {
        Map<String, AttributeValue> exclusiveStartKey = paginationCursorMapper.mapFromCursor(searchDto.getPageCursor());
        Page<Whisky> page = getPageOfWhisky(searchDto, exclusiveStartKey);

        List<WhiskyDto> whiskies = page.items().stream()
                .map(whiskyMapper::mapModelToDto)
                .collect(Collectors.toList());

        String pageCursor = paginationCursorMapper.mapToCursor(page.lastEvaluatedKey());

        return new WhiskiesFindResultDto()
                .results(whiskies)
                .pageCursor(pageCursor);
    }

    private Page<Whisky> getPageOfWhisky(SearchWhiskiesDto searchDto,
                                         Map<String, AttributeValue> exclusiveStartKey) {

        FilterTypeDto filterType = searchDto.getFilter();
        Page<Whisky> page;

        if (FilterTypeDto.BRAND.equals(filterType)) {
            page = getWhiskiesByBrand(searchDto, exclusiveStartKey);

        } else if (FilterTypeDto.PRICERANGE.equals(filterType)) {
            page = getWhiskiesByPriceRange(searchDto, exclusiveStartKey);

        } else {
            page = getWhiskies(exclusiveStartKey);
        }

        return page;
    }

    private Page<Whisky> getWhiskiesByBrand(SearchWhiskiesDto searchDto, Map<String, AttributeValue> exclusiveStartKey) {
        return whiskyRepository.getWhiskiesByBrand(searchDto, exclusiveStartKey);
    }

    private Page<Whisky> getWhiskiesByPriceRange(SearchWhiskiesDto searchDto, Map<String, AttributeValue> exclusiveStartKey) {
        return whiskyRepository.getWhiskiesByPriceRange(searchDto, exclusiveStartKey);
    }

    private Page<Whisky> getWhiskies(Map<String, AttributeValue> exclusiveStartKey) {
        LocalDate lastSeenDate = extractLastSeenDate(exclusiveStartKey);
        List<Whisky> fetched = new ArrayList<>();
        int emptyDayCounter = 0;

        while (shouldFetchWhisky(fetched, lastSeenDate, emptyDayCounter)) {
            int limit = PAGE_LIMIT - fetched.size();
            Page<Whisky> page = whiskyRepository.getWhiskies(exclusiveStartKey, lastSeenDate, limit);
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
                .map(map -> map.get(AttributeNames.GSI1_PARTITION_KEY).s()
                        .replace(AttributeValues.Whisky.GSI1_PARTITION_KEY, ""))
                .orElseGet(() -> LocalDate.now().toString());

        return LocalDate.parse(lastSeenDate);
    }

    private static boolean shouldFetchWhisky(List<Whisky> fetched,
                                             LocalDate lastSeenDate,
                                             int emptyDayCounter) {

        return fetched.size() < PAGE_LIMIT &&
                lastSeenDate.isAfter(OLDEST_SAVED_DATE) &&
                emptyDayCounter < EMPTY_DAYS_LIMIT;
    }

    public void addWhisky(Whisky whisky) {
        fixAddedDay(whisky);
        whiskyRepository.addWhisky(whisky);
        APPROXIMATE_TOTAL_PAST_WHISKY_NUMBER--;
    }

    public static void fixAddedDay(Whisky whisky) {
        if (APPROXIMATE_TOTAL_PAST_WHISKY_NUMBER > 0) {
            int daysBack = APPROXIMATE_TOTAL_PAST_WHISKY_NUMBER / 100;
            LocalDate addedDate = whisky.getAddedDate().minusDays(daysBack);
            whisky.setAddedDate(addedDate);
        }
    }

    public UrlsFindResultDto getWhiskiesUrls(String pageCursor) {
        Map<String, AttributeValue> exclusiveStartKey = paginationCursorMapper.mapFromCursor(pageCursor);
        Page<Whisky> page = whiskyRepository.getWhiskiesUrls(exclusiveStartKey);

        List<String> urls = page.items().stream()
                .map(Whisky::getUrl)
                .collect(Collectors.toList());

        String nextPageCursor = paginationCursorMapper.mapToCursor(page.lastEvaluatedKey());

        return new UrlsFindResultDto()
                .results(urls)
                .pageCursor(nextPageCursor);
    }

    public boolean deleteWhisky(String whiskyUrl) {
        return whiskyRepository.deleteWhisky(whiskyUrl);
    }
}