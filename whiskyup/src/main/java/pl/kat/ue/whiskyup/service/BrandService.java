package pl.kat.ue.whiskyup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.repository.BrandRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public List<String> getBrands() {
        return new ArrayList<>(brandRepository.getBrands().getValues());
    }
}