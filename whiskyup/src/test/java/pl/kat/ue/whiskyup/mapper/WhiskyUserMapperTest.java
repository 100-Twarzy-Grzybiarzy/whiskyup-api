package pl.kat.ue.whiskyup.mapper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kat.ue.whiskyup.model.UserWhiskyDto;
import pl.kat.ue.whiskyup.model.WhiskyUser;
import pl.kat.ue.whiskyup.utils.manager.KsuidManager;

class WhiskyUserMapperTest {

    private final WhiskyUserMapper whiskyUserMapper = Mappers.getMapper(WhiskyUserMapper.class);

    @Test
    void shouldMapFromDtoToNewModel() {
        //given
        WhiskyUser whiskyUser;
        UserWhiskyDto userWhiskyDto = new UserWhiskyDto();

        //when
        try (MockedStatic<KsuidManager> mockedKsuid = Mockito.mockStatic(KsuidManager.class)) {
            mockedKsuid.when(KsuidManager::newKsuid).thenReturn("1HCpXwx2EK9oYluWbacgeCnFcLf");
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