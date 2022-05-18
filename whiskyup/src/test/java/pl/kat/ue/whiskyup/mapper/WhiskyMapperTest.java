package pl.kat.ue.whiskyup.mapper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.utils.manager.KsuidManager;

import java.time.LocalDate;

class WhiskyMapperTest {

    private final WhiskyMapper whiskyMapper = Mappers.getMapper(WhiskyMapper.class);

    @Test
    void shouldMapFromModelToApi() {
        //given
        Whisky whisky = new Whisky();
        whisky.setUrl("whiskybase.com/market/whisky/220455");

        //when
        WhiskyDto whiskyDto = whiskyMapper.mapModelToDto(whisky);

        //then
        Assertions.assertEquals("whiskybase.com/market/whisky/220455", whiskyDto.getUrl());
    }

    @Test
    void shouldMapFromApiToModel() {
        //given
        Whisky whisky;
        WhiskyDto whiskyDto = new WhiskyDto()
                .brand("Aberlour")
                .price(120.20)
                .addedDate("2022-04-30")
                .url("whiskybase.com/market/whisky/220455");

        //when
        try (MockedStatic<KsuidManager> mockedKsuid = Mockito.mockStatic(KsuidManager.class)) {
            mockedKsuid.when(() -> KsuidManager.newKsuid(LocalDate.parse("2022-04-30"))).thenReturn("1HCpXwx2EK9oYluWbacgeCnFcLf");
            whisky = whiskyMapper.mapDtoToModel(whiskyDto);
        }

        //then
        Assertions.assertEquals("2022-04-30", whisky.getAddedDate().toString());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whisky.getPk());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whisky.getSk());
        Assertions.assertEquals("WHISKIES#2022-04-30", whisky.getGsi1pk());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whisky.getGsi1sk());
        Assertions.assertEquals("PRICERANGE#100-200", whisky.getGsi2pk());
        Assertions.assertEquals("PRICE#120.20#WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whisky.getGsi2sk());
        Assertions.assertEquals("BRAND#aberlour", whisky.getGsi3pk());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whisky.getGsi3sk());
        Assertions.assertEquals("URLS", whisky.getGsi4pk());
        Assertions.assertEquals("URL#whiskybase.com/market/whisky/220455", whisky.getGsi4sk());
    }
}