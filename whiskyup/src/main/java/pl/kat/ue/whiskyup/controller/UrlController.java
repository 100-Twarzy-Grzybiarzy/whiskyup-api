package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.kat.ue.whiskyup.api.UrlsApi;
import pl.kat.ue.whiskyup.model.UrlsFindResultDto;
import pl.kat.ue.whiskyup.service.WhiskyService;

@RestController
@RequiredArgsConstructor
public class UrlController implements UrlsApi {

    private final WhiskyService whiskyService;

    @Override
    public ResponseEntity<UrlsFindResultDto> getWhiskiesUrls(@RequestParam(required = false) String pageCursor) {
        UrlsFindResultDto result = whiskyService.getWhiskiesUrls(pageCursor);
        return ResponseEntity.ok(result);
    }
}