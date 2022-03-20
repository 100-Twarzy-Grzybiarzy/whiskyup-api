package pl.kat.ue.whiskyup.service;

import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.model.Search;
import pl.kat.ue.whiskyup.model.WhiskyApi;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class WhiskyService {

    public List<WhiskyApi> whiskies = new ArrayList<>();

    @PostConstruct
    void initWhiskies() {
        whiskies.add(new WhiskyApi().name("Whisky")
                .id(0)
                .price(120.5)
                .creationDate(Instant.now().getEpochSecond())
                .amountOfRatings(200)
                .imageUrl("whisky/url/image")
                .url("whisky/url")
                .rating(21.5)
                .userRating(23.0));
    }

    public List<WhiskyApi> getWhiskies(Search search) {
        return whiskies;
    }

    public WhiskyApi addWhisky(WhiskyApi whisky) {
        whisky.setId(whiskies.size());
        whisky.setCreationDate(Instant.now().getEpochSecond());
        whiskies.add(whisky);
        return whiskies.get(whisky.getId());
    }
}