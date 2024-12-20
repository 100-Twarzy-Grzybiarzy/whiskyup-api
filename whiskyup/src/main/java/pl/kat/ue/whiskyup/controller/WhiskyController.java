package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kat.ue.whiskyup.api.WhiskiesApi;
import pl.kat.ue.whiskyup.dto.SearchWhiskiesDto;
import pl.kat.ue.whiskyup.model.FilterTypeDto;
import pl.kat.ue.whiskyup.model.SortTypeDto;
import pl.kat.ue.whiskyup.model.WhiskiesFindResultDto;
import pl.kat.ue.whiskyup.service.WhiskyService;

@RestController
@RequiredArgsConstructor
public class WhiskyController implements WhiskiesApi {

    private final WhiskyService whiskyService;

    @Override
    public ResponseEntity<WhiskiesFindResultDto> getWhiskies(@RequestParam(required = false) String pageCursor,
                                                             @RequestParam(required = false) FilterTypeDto filter,
                                                             @RequestParam(required = false) String value,
                                                             @RequestParam(required = false) SortTypeDto sort) {

        SearchWhiskiesDto filterDto = SearchWhiskiesDto.builder()
                .pageCursor(pageCursor)
                .filter(filter)
                .value(value)
                .sort(sort)
                .build();

        WhiskiesFindResultDto result = whiskyService.getWhiskies(filterDto);
        return ResponseEntity.ok(result);
    }
}