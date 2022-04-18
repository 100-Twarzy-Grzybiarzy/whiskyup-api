package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kat.ue.whiskyup.model.User;
import pl.kat.ue.whiskyup.model.UserApi;

@Mapper(componentModel = "spring")
public interface UserMapper {

    String PREFIX = User.PK_PREFIX;

    @Mapping(target = "id", expression = "java( PREFIX + java.util.UUID.randomUUID() )")
    User mapApiToNewModel(UserApi userApi);

    @Mapping(target = "id", expression = "java( PREFIX + userId)")
    User mapApiToModel(String userId, UserApi userApi);

    @Mapping(target = "id", expression = "java( user.getId().replace(PREFIX, \"\") )")
    UserApi mapModelToApi(User user);

}