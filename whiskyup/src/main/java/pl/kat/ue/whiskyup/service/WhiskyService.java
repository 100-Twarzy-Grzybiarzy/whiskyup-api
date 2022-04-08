package pl.kat.ue.whiskyup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.mapper.WhiskyMapper;
import pl.kat.ue.whiskyup.model.WhiskyApi;
import pl.kat.ue.whiskyup.repository.WhiskyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WhiskyService {

    private final WhiskyRepository whiskyRepository;
    private final WhiskyMapper whiskyMapper;

    public List<WhiskyApi> getWhiskies(String exclusiveStartKey) {
        return whiskyRepository.getAllWhiskies(exclusiveStartKey)
                .items()
                .stream()
                .map(whiskyMapper::mapModelToApi)
                .collect(Collectors.toList());
    }

    public WhiskyApi addWhisky(WhiskyApi whisky) {
        return whisky;
    }
}