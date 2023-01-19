package pl.kat.ue.whiskyup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.model.Brands;
import pl.kat.ue.whiskyup.repository.BrandRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<String> getBrands() {
        Brands brands = brandRepository.getBrands();
        return Optional.ofNullable(brands.getValues())
                .map(ArrayList::new)
                .orElseGet(ArrayList::new);
    }
}