package pl.kat.ue.whiskyup.utils.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.kat.ue.whiskyup.model.SortTypeDto;

@Component
public class SortTypeDtoConverter implements Converter<String, SortTypeDto> {

    @Override
    public SortTypeDto convert(String source) {
        return SortTypeDto.valueOf(source.toUpperCase());
    }
}