package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.*;
import pl.kat.ue.whiskyup.dynamometadata.AttributeValues;
import pl.kat.ue.whiskyup.model.UserWhisky;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.utils.manager.KsuidManager;

@Mapper(componentModel = "spring")
public interface UserWhiskyMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "whiskyId", source = "whiskyDto.id")
    @Mapping(target = "pk", source = "userId", qualifiedByName = "mapPk")
    @Mapping(target = "sk", source = "whiskyDto.id", qualifiedByName = "mapSk")
    UserWhisky mapDtoToModel(String userId, WhiskyDto whiskyDto);

    @Mapping(target = "id", source = "whiskyId")
    WhiskyDto mapModelToDto(UserWhisky userWhisky);

    @BeforeMapping
    default void generateWhiskyId(WhiskyDto whiskyDto, @MappingTarget UserWhisky.UserWhiskyBuilder target) {
        whiskyDto.setId(KsuidManager.newKsuid());
    }

    @Named("mapPk")
    default String mapPk(String userId) {
        return AttributeValues.UserWhisky.PARTITION_KEY + userId;
    }

    @Named("mapSk")
    default String mapSk(String whiskyId) {
        return AttributeValues.UserWhisky.SORT_KEY + whiskyId;
    }
}