package pl.kat.ue.whiskyup.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.model.WhiskyApi;

class WhiskyMapperTest {

    private final WhiskyMapper whiskyMapper = Mappers.getMapper(WhiskyMapper.class);

    @Test
    void shouldMapUrlFromModelToApi() {
        //given
        Whisky whisky = new Whisky();
        whisky.setUrl("WHISKY#whiskybase.com/market/whisky/220455");

        //when
        WhiskyApi whiskyApi = whiskyMapper.mapModelToApi(whisky);

        //then
        Assertions.assertEquals("whiskybase.com/market/whisky/220455", whiskyApi.getUrl());
    }

    @Test
    void mapUrlFromApiToModel() {
        WhiskyApi whiskyApi = new WhiskyApi();
        whiskyApi.setUrl("whiskybase.com/market/whisky/220455");

        //when
        Whisky whisky = whiskyMapper.mapApiToModel(whiskyApi);

        //then
        Assertions.assertEquals("WHISKY#whiskybase.com/market/whisky/220455", whisky.getUrl());
    }
}