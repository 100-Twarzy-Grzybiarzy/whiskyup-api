package pl.kat.ue.whiskyup.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.kat.ue.whiskyup.model.UserWhisky;
import pl.kat.ue.whiskyup.model.UserWhiskyDto;

class UserWhiskyMapperTest {

    private final UserWhiskyMapper userWhiskyMapper = Mappers.getMapper(UserWhiskyMapper.class);

    @Test
    void shouldMapUserIdAndGenerateWhiskyIdFromDtoToNewModel() {
        //given
        String userId = "abc123def";
        UserWhiskyDto userWhiskyDto = new UserWhiskyDto();
        userWhiskyDto.setId(null);

        //when
        UserWhisky userWhisky = userWhiskyMapper.mapDtoToNewModel(userId, userWhiskyDto);

        //then
        Assertions.assertEquals("USER#abc123def", userWhisky.getUserId());
        Assertions.assertTrue(userWhisky.getWhiskyId().startsWith("WHISKY#"));
        Assertions.assertFalse(userWhisky.getWhiskyId().replace("WHISKY#", "").isEmpty());
    }

    @Test
    void shouldMapWhiskyIdFromModelToDto() {
        //given
        UserWhisky userWhisky = new UserWhisky();
        userWhisky.setWhiskyId("WHISKY#qwerty123");

        //when
        UserWhiskyDto userWhiskyDto = userWhiskyMapper.mapModelToDto(userWhisky);

        //then
        Assertions.assertEquals("qwerty123", userWhiskyDto.getId());
    }
}