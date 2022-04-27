package pl.kat.ue.whiskyup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.mapper.PaginationCursorMapper;
import pl.kat.ue.whiskyup.mapper.WhiskyMapper;
import pl.kat.ue.whiskyup.model.WhiskiesFindResultDto;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.repository.WhiskyRepository;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WhiskyService {

    private final WhiskyRepository whiskyRepository;
    private final WhiskyMapper whiskyMapper;
    private final PaginationCursorMapper paginationCursorMapper;

    public WhiskiesFindResultDto getWhiskies(String paginationCursor) {
        Map<String, AttributeValue> exclusiveStartKey = paginationCursorMapper.mapFromCursor(paginationCursor);
        Page<Whisky> page = whiskyRepository.getAllWhiskies(exclusiveStartKey);
        return new WhiskiesFindResultDto()
                .results(page.items().stream()
                        .map(whiskyMapper::mapModelToDto)
                        .collect(Collectors.toList()))
                .exclusiveStartKey(paginationCursorMapper.mapToCursor(page.lastEvaluatedKey()));
    }

    public void addWhisky(Whisky whisky){
        whiskyRepository.addWhisky(whisky);
    }

}