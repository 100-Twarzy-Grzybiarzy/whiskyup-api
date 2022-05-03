package pl.kat.ue.whiskyup.mapper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kat.ue.whiskyup.model.WhiskyBase;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.utils.manager.KsuidManager;

import java.time.LocalDate;

class WhiskyMapperTest {

    private final WhiskyBaseMapper whiskyBaseMapper = Mappers.getMapper(WhiskyBaseMapper.class);

    @Test
    void shouldMapFromModelToApi() {
        //given
        WhiskyBase whiskyBase = new WhiskyBase();
        whiskyBase.setUrl("whiskybase.com/market/whisky/220455");

        //when
        WhiskyDto whiskyDto = whiskyBaseMapper.mapModelToDto(whiskyBase);

        //then
        Assertions.assertEquals("whiskybase.com/market/whisky/220455", whiskyDto.getUrl());
    }

    @Test
    void shouldMapFromApiToModel() {
        //given
        WhiskyBase whiskyBase;
        WhiskyDto whiskyDto = new WhiskyDto()
                .brand("Aberlour")
                .price(120.20)
                .addedDate("30.04.22")
                .url("whiskybase.com/market/whisky/220455");

        //when
        try (MockedStatic<KsuidManager> mockedKsuid = Mockito.mockStatic(KsuidManager.class)) {
            mockedKsuid.when(() -> KsuidManager.newKsuid(LocalDate.parse("2022-04-30"))).thenReturn("1HCpXwx2EK9oYluWbacgeCnFcLf");
            whiskyBase = whiskyBaseMapper.mapDtoToModel(whiskyDto);
        }

        //then
        Assertions.assertEquals("2022-04-30", whiskyBase.getAddedDate().toString());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getPk());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getSk());
        Assertions.assertEquals("WHISKIES#2022-04-30", whiskyBase.getGsi1pk());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getGsi1sk());
        Assertions.assertEquals("PRICERANGE#100-200", whiskyBase.getGsi2pk());
        Assertions.assertEquals("PRICE#120.20#WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getGsi2sk());
        Assertions.assertEquals("BRAND#aberlour", whiskyBase.getGsi3pk());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getGsi3sk());
        Assertions.assertEquals("URLS", whiskyBase.getGsi4pk());
        Assertions.assertEquals("URL#whiskybase.com/market/whisky/220455", whiskyBase.getGsi4sk());
    }
}