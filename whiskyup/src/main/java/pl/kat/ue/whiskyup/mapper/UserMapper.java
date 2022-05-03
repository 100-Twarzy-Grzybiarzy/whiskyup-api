package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.*;
import pl.kat.ue.whiskyup.model.User;
import pl.kat.ue.whiskyup.model.UserDto;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "pk", source = "id", qualifiedByName = "mapPk")
    @Mapping(target = "sk", source = "id", qualifiedByName = "mapSk")
    User mapDtoToModel(UserDto userDto);

    UserDto mapModelToDto(User user);

    @BeforeMapping
    default void generateWhiskyId(UserDto userDto, @MappingTarget User.UserBuilder target) {
        userDto.setId(UUID.randomUUID().toString());
    }

    @Named("mapPk")
    default String mapPk(String id) {
        return User.PK_PREFIX + id;
    }

    @Named("mapSk")
    default String mapSk(String id) {
        return User.SK_PREFIX + id;
    }
}