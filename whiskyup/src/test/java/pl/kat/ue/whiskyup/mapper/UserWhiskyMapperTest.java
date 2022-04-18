package pl.kat.ue.whiskyup.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.kat.ue.whiskyup.model.UserWhisky;
import pl.kat.ue.whiskyup.model.UserWhiskyApi;

class UserWhiskyMapperTest {

    private final UserWhiskyMapper userWhiskyMapper = Mappers.getMapper(UserWhiskyMapper.class);

    @Test
    void shouldMapUserIdAndGenerateWhiskyIdFromApiToNewModel() {
        //given
        String userId = "abc123def";
        UserWhiskyApi userWhiskyApi = new UserWhiskyApi();
        userWhiskyApi.setId(null);

        //when
        UserWhisky userWhisky = userWhiskyMapper.mapApiToNewModel(userId, userWhiskyApi);

        //then
        Assertions.assertEquals("USER#abc123def", userWhisky.getUserId());
        Assertions.assertTrue(userWhisky.getWhiskyId().startsWith("WHISKY#"));
        Assertions.assertFalse(userWhisky.getWhiskyId().replace("WHISKY#", "").isEmpty());
    }

    @Test
    void shouldMapWhiskyIdFromModelToApi() {
        //given
        UserWhisky userWhisky = new UserWhisky();
        userWhisky.setWhiskyId("WHISKY#qwerty123");

        //when
        UserWhiskyApi userWhiskyApi = userWhiskyMapper.mapModelToApi(userWhisky);

        //then
        Assertions.assertEquals("qwerty123", userWhiskyApi.getId());
    }
}