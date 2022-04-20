package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kat.ue.whiskyup.model.User;
import pl.kat.ue.whiskyup.model.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    String PREFIX = User.PK_PREFIX;

    @Mapping(target = "id", expression = "java( PREFIX + java.util.UUID.randomUUID() )")
    User mapDtoToNewModel(UserDto userDto);

    @Mapping(target = "id", expression = "java( PREFIX + userId)")
    User mapDtoToModel(String userId, UserDto userDto);

    @Mapping(target = "id", expression = "java( user.getId().replace(PREFIX, \"\") )")
    UserDto mapModelToDto(User user);

}