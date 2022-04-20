package pl.kat.ue.whiskyup.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.model.WhiskyDto;

class WhiskyMapperTest {

    private final WhiskyMapper whiskyMapper = Mappers.getMapper(WhiskyMapper.class);

    @Test
    void shouldMapUrlFromModelToApi() {
        //given
        Whisky whisky = new Whisky();
        whisky.setUrl("WHISKY#whiskybase.com/market/whisky/220455");

        //when
        WhiskyDto whiskyDto = whiskyMapper.mapModelToDto(whisky);

        //then
        Assertions.assertEquals("whiskybase.com/market/whisky/220455", whiskyDto.getUrl());
    }

    @Test
    void mapUrlFromApiToModel() {
        WhiskyDto whiskyDto = new WhiskyDto();
        whiskyDto.setUrl("whiskybase.com/market/whisky/220455");

        //when
        Whisky whisky = whiskyMapper.mapDtoToModel(whiskyDto);

        //then
        Assertions.assertEquals("WHISKY#whiskybase.com/market/whisky/220455", whisky.getUrl());
    }
}