package pl.kat.ue.whiskyup.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Search {

    private String sort;
    private String order;
    private long limit;
    private int page;

}