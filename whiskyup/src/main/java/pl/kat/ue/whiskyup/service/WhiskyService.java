package pl.kat.ue.whiskyup.service;

import org.springframework.stereotype.Service;
import pl.kat.ue.whiskyup.model.WhiskyApi;

import java.util.ArrayList;
import java.util.List;

@Service
public class WhiskyService {

    public List<WhiskyApi> getWhiskies(String exclusiveStartKey) {
        return new ArrayList<>();
    }

    public WhiskyApi addWhisky(WhiskyApi whisky) {
        return whisky;
    }
}