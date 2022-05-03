package pl.kat.ue.whiskyup.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.kat.ue.whiskyup.model.FilterTypeDto;

@Component
public class FilterTypeDtoConverter implements Converter<String, FilterTypeDto> {

    @Override
    public FilterTypeDto convert(String source) {
        return FilterTypeDto.valueOf(source.toUpperCase());
    }
}