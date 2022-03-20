package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.kat.ue.whiskyup.model.Search;
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
    public WhiskiesFindResultApi getWhiskies(@RequestParam(defaultValue = "name", required = false) String sort,
                                             @RequestParam(defaultValue = "desc", required = false) String order,
                                             @RequestParam(defaultValue = "50", required = false) long limit,
                                             @RequestParam(defaultValue = "0", required = false) int page) {

        Search search = Search.builder()
                .sort(sort)
                .order(order)
                .limit(limit)
                .page(page)
                .build();

        List<WhiskyApi> whiskies = whiskyService.getWhiskies(search);
        return new WhiskiesFindResultApi()
                .results(whiskies)
                .counts((long) whiskies.size());
    }

    @PostMapping
    public WhiskyApi addWhisky(@RequestBody WhiskyApi whiskyApi) {
        return whiskyService.addWhisky(whiskyApi);
    }
}