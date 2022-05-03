package pl.kat.ue.whiskyup.mapper;

import com.github.ksuid.Ksuid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kat.ue.whiskyup.model.UserWhiskyDto;
import pl.kat.ue.whiskyup.model.WhiskyUser;

class WhiskyUserMapperTest {

    private final WhiskyUserMapper whiskyUserMapper = Mappers.getMapper(WhiskyUserMapper.class);

    @Test
    void shouldMapFromDtoToNewModel() {
        //given
        WhiskyUser whiskyUser;
        UserWhiskyDto userWhiskyDto = new UserWhiskyDto();
        Ksuid id = Ksuid.fromString("1HCpXwx2EK9oYluWbacgeCnFcLf");

        //when
        try (MockedStatic<Ksuid> mockedKsuid = Mockito.mockStatic(Ksuid.class)) {
            mockedKsuid.when(Ksuid::newKsuid).thenReturn(id);
            whiskyUser = whiskyUserMapper.mapDtoToModel("abc123def", userWhiskyDto);
        }

        //then
        Assertions.assertEquals("USER#abc123def", whiskyUser.getPk());
        Assertions.assertTrue(whiskyUser.getSk().startsWith("WHISKY#"));
        Assertions.assertFalse(whiskyUser.getSk().replace("WHISKY#", "").isEmpty());
    }

    @Test
    void shouldMapFromModelToDto() {
        //given
        WhiskyUser whiskyUser = WhiskyUser.builder().build();
        whiskyUser.setWhiskyId("qwerty123");

        //when
        UserWhiskyDto userWhiskyDto = whiskyUserMapper.mapModelToDto(whiskyUser);

        //then
        Assertions.assertEquals("qwerty123", userWhiskyDto.getId());
    }
}