package pl.kat.ue.whiskyup.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import pl.kat.ue.whiskyup.model.UserWhisky;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.utils.manager.KsuidManager;

class UserWhiskyMapperTest {

    private final UserWhiskyMapper userWhiskyMapper = Mappers.getMapper(UserWhiskyMapper.class);

    @Test
    void shouldMapFromDtoToNewModel() {
        //given
        UserWhisky userWhisky;
        WhiskyDto whiskyDto = new WhiskyDto();

        //when
        try (MockedStatic<KsuidManager> mockedKsuid = Mockito.mockStatic(KsuidManager.class)) {
            mockedKsuid.when(KsuidManager::newKsuid).thenReturn("1HCpXwx2EK9oYluWbacgeCnFcLf");
            userWhisky = userWhiskyMapper.mapDtoToModel("abc123def", whiskyDto);
        }

        //then
        Assertions.assertEquals("USER#abc123def", userWhisky.getPk());
        Assertions.assertTrue(userWhisky.getSk().startsWith("WHISKY#"));
        Assertions.assertFalse(userWhisky.getSk().replace("WHISKY#", "").isEmpty());
    }

    @Test
    void shouldMapFromModelToDto() {
        //given
        UserWhisky userWhisky = UserWhisky.builder().build();
        userWhisky.setWhiskyId("qwerty123");

        //when
        WhiskyDto whiskyDto = userWhiskyMapper.mapModelToDto(userWhisky);

        //then
        Assertions.assertEquals("qwerty123", whiskyDto.getId());
    }
}