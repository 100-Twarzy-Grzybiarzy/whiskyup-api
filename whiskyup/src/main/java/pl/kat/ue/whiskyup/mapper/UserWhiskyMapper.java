package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kat.ue.whiskyup.model.User;
import pl.kat.ue.whiskyup.model.UserWhisky;
import pl.kat.ue.whiskyup.model.UserWhiskyApi;

@Mapper(componentModel = "spring")
public interface UserWhiskyMapper {

    String PK_PREFIX = User.PK_PREFIX;
    String SK_PREFIX = UserWhisky.SK_PREFIX;

    @Mapping(target = "userId", expression = "java( PK_PREFIX + userId )")
    @Mapping(target = "whiskyId", expression = "java( SK_PREFIX + java.util.UUID.randomUUID() )")
    UserWhisky mapApiToNewModel(String userId, UserWhiskyApi userWhiskyApi);

    UserWhisky mapApiToModel(UserWhiskyApi userWhiskyApi);

    @Mapping(target = "id", expression = "java( userWhisky.getWhiskyId().replace(SK_PREFIX, \"\") )")
    UserWhiskyApi mapModelToApi(UserWhisky userWhisky);

}