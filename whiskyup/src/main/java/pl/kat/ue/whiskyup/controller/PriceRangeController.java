package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.kat.ue.whiskyup.api.PriceRangesApi;
import pl.kat.ue.whiskyup.service.PriceRangeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PriceRangeController implements PriceRangesApi {

    private final PriceRangeService priceRangeService;

    @Override
    public ResponseEntity<List<String>> getPriceRanges() {
        List<String> result = priceRangeService.getPriceRanges();
        return ResponseEntity.ok(result);
    }
}