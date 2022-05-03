package pl.kat.ue.whiskyup.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.kat.ue.whiskyup.model.FilterTypeDto;
import pl.kat.ue.whiskyup.model.SortTypeDto;

@Getter
@Setter
@Builder
public class SearchWhiskiesDto {

    private String pageCursor;
    private FilterTypeDto filter;
    private String value;
    private SortTypeDto sort;

}