package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kat.ue.whiskyup.model.WhiskiesFindResultApi;
import pl.kat.ue.whiskyup.model.WhiskyApi;
import pl.kat.ue.whiskyup.service.WhiskyService;

import java.util.List;

@RestController
@RequestMapping("/whisky")
@RequiredArgsConstructor
public class WhiskyController {

    private final WhiskyService whiskyService;

    @GetMapping
    public WhiskiesFindResultApi getWhiskies(@RequestParam(required = false) String exclusiveStartKey) {
        List<WhiskyApi> whiskies = whiskyService.getWhiskies(exclusiveStartKey);
        return new WhiskiesFindResultApi().results(whiskies);
    }

    @PostMapping
    public WhiskyApi addWhisky(@RequestBody WhiskyApi whiskyApi) {
        return whiskyService.addWhisky(whiskyApi);
    }
}