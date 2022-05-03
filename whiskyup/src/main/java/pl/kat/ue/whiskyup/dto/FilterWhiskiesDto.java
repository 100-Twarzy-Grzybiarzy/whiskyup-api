package pl.kat.ue.whiskyup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kat.ue.whiskyup.model.FilterTypeDto;

@Getter
@Setter
@Builder
public class FilterWhiskiesDto {

    private String pageCursor;
    private FilterTypeDto filter;
    private String value;

}