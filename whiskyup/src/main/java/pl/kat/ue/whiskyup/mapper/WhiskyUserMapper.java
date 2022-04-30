package pl.kat.ue.whiskyup.mapper;

import com.github.ksuid.Ksuid;
import org.mapstruct.*;
import pl.kat.ue.whiskyup.model.UserWhiskyDto;
import pl.kat.ue.whiskyup.model.WhiskyUser;

@Mapper(componentModel = "spring")
public interface WhiskyUserMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "pk", source = "userId", qualifiedByName = "mapPk")
    @Mapping(target = "sk", source = "userWhiskyDto.id", qualifiedByName = "mapSk")
    WhiskyUser mapDtoToModel(String userId, UserWhiskyDto userWhiskyDto);

    @Mapping(target = "id", source = "whiskyId")
    UserWhiskyDto mapModelToDto(WhiskyUser whiskyUser);

    @BeforeMapping
    default void generateWhiskyId(UserWhiskyDto userWhiskyDto, @TargetType Class<WhiskyUser> targetType) {
        userWhiskyDto.setId(Ksuid.newKsuid().toString());
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