package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.*;
import pl.kat.ue.whiskyup.model.UserWhiskyDto;
import pl.kat.ue.whiskyup.model.WhiskyUser;
import pl.kat.ue.whiskyup.utils.manager.KsuidManager;

@Mapper(componentModel = "spring")
public interface WhiskyUserMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "whiskyId", source = "userWhiskyDto.id")
    @Mapping(target = "pk", source = "userId", qualifiedByName = "mapPk")
    @Mapping(target = "sk", source = "userWhiskyDto.id", qualifiedByName = "mapSk")
    WhiskyUser mapDtoToModel(String userId, UserWhiskyDto userWhiskyDto);

    @Mapping(target = "id", source = "whiskyId")
    UserWhiskyDto mapModelToDto(WhiskyUser whiskyUser);

    @BeforeMapping
    default void generateWhiskyId(UserWhiskyDto userWhiskyDto, @MappingTarget WhiskyUser.WhiskyUserBuilder target) {
        userWhiskyDto.setId(KsuidManager.newKsuid());
    }

    @Named("mapPk")
    default String mapPk(String id) {
        return WhiskyUser.PK_PREFIX + id;
    }

    @Named("mapSk")
    default String mapSk(String id) {
        return WhiskyUser.SK_PREFIX + id;
    }
}