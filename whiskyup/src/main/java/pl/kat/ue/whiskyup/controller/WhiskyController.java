package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kat.ue.whiskyup.api.WhiskyApi;
import pl.kat.ue.whiskyup.model.WhiskiesFindResultDto;
import pl.kat.ue.whiskyup.service.WhiskyService;

@RestController
@RequiredArgsConstructor
public class WhiskyController implements WhiskyApi {

    private final WhiskyService whiskyService;

    @Override
    public ResponseEntity<WhiskiesFindResultDto> getWhiskies(@RequestParam(required = false) String exclusiveStartKey) {
        WhiskiesFindResultDto result = whiskyService.getWhiskies(exclusiveStartKey);
        return ResponseEntity.ok(result);
    }
}