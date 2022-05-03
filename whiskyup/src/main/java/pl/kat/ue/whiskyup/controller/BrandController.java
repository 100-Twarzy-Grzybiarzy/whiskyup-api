package pl.kat.ue.whiskyup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.kat.ue.whiskyup.api.BrandsApi;
import pl.kat.ue.whiskyup.service.BrandService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BrandController implements BrandsApi {

    private final BrandService brandService;

    @Override
    public ResponseEntity<List<String>> getBrands() {
        List<String> results = brandService.getBrands();
        return ResponseEntity.ok(results);
    }
}