package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.kat.ue.whiskyup.model.WhiskiesFindResultApi;
import pl.kat.ue.whiskyup.service.WhiskyService;

@RestController
@RequiredArgsConstructor
public class WhiskyController implements pl.kat.ue.whiskyup.api.WhiskyApi {

    private final WhiskyService whiskyService;

    @Override
    public ResponseEntity<WhiskiesFindResultApi> getWhiskies(String exclusiveStartKey) {
        WhiskiesFindResultApi result = whiskyService.getWhiskies(exclusiveStartKey);
        return ResponseEntity.ok(result);
    }
}