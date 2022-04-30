package pl.kat.ue.whiskyup.mapper;

import com.github.ksuid.Ksuid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kat.ue.whiskyup.model.WhiskyBase;
import pl.kat.ue.whiskyup.model.WhiskyDto;

import java.time.LocalDate;

class WhiskyMapperTest {

    private final WhiskyBaseMapper whiskyBaseMapper = Mappers.getMapper(WhiskyBaseMapper.class);

    @Test
    void shouldMapUrlFromModelToApi() {
        //given
        WhiskyBase whiskyBase = new WhiskyBase();
        whiskyBase.setUrl("whiskybase.com/market/whisky/220455");

        //when
        WhiskyDto whiskyDto = whiskyBaseMapper.mapModelToDto(whiskyBase);

        //then
        Assertions.assertEquals("whiskybase.com/market/whisky/220455", whiskyDto.getUrl());
    }

    @Test
    void mapUrlFromApiToModel() {
        WhiskyBase whiskyBase;
        WhiskyDto whiskyDto = new WhiskyDto();
        whiskyDto.setBrand("Aberlour");
        whiskyDto.setPrice(120.20);
        Ksuid id = Ksuid.fromString("1HCpXwx2EK9oYluWbacgeCnFcLf");
        LocalDate date = LocalDate.parse("2022-04-30");

        try (MockedStatic<Ksuid> mockedKsuid = Mockito.mockStatic(Ksuid.class);
             MockedStatic<LocalDate> mockedLocalDate = Mockito.mockStatic(LocalDate.class)) {
            //when
            mockedKsuid.when(Ksuid::newKsuid).thenReturn(id);
            mockedLocalDate.when(LocalDate::now).thenReturn(date);
            whiskyBase = whiskyBaseMapper.mapDtoToModel(whiskyDto);
        }

        //then
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getPk());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getSk());
        Assertions.assertEquals("WHISKIES#2022-04-30", whiskyBase.getGsi1pk());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getGsi1sk());
        Assertions.assertEquals("PRICERANGE#0-50", whiskyBase.getGsi2pk());
        Assertions.assertEquals("PRICE#120.20#WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getGsi2sk());
        Assertions.assertEquals("BRAND#aberlour", whiskyBase.getGsi3pk());
        Assertions.assertEquals("WHISKY#1HCpXwx2EK9oYluWbacgeCnFcLf", whiskyBase.getGsi3sk());
    }
}