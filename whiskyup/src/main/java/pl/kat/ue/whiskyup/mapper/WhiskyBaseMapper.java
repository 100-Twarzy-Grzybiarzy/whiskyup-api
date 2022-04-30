package pl.kat.ue.whiskyup.mapper;

import com.github.ksuid.Ksuid;
import org.mapstruct.*;
import pl.kat.ue.whiskyup.model.WhiskyBase;
import pl.kat.ue.whiskyup.model.WhiskyDto;

import java.util.Locale;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface WhiskyBaseMapper {

    @Mapping(target = "pk", source = "id", qualifiedByName = "mapPk")
    @Mapping(target = "sk", source = "id", qualifiedByName = "mapSk")
    @Mapping(target = "gsi1pk", expression = "java( WhiskyBase.GSI1_PK_PREFIX + java.time.LocalDate.now() )")
    @Mapping(target = "gsi1sk", source = "id", qualifiedByName = "mapGsi1Sk")
    @Mapping(target = "gsi2pk", source = "price", qualifiedByName = "mapGsi2Pk")
    @Mapping(target = "gsi2sk", source = "whiskyDto", qualifiedByName = "mapGsi2Sk")
    @Mapping(target = "gsi3pk", source = "brand", qualifiedByName = "mapGsi3Pk")
    @Mapping(target = "gsi3sk", source = "id", qualifiedByName = "mapGsi3Sk")
    WhiskyBase mapDtoToModel(WhiskyDto whiskyDto);

    WhiskyDto mapModelToDto(WhiskyBase whiskyBase);

    @BeforeMapping
    default void generateId(WhiskyDto whiskyDto, @TargetType Class<WhiskyBase> targetType) {
        whiskyDto.setId(Ksuid.newKsuid().toString());
    }

    @Named("mapPk")
    default String mapPk(String id) {
        return WhiskyBase.PK_PREFIX + id;
    }

    @Named("mapSk")
    default String mapSk(String id) {
        return WhiskyBase.SK_PREFIX + id;
    }

    @Named("mapGsi1Sk")
    default String mapGsi1Sk(String id) {
        return WhiskyBase.GSI1_SK_PREFIX + id;
    }

    @Named("mapGsi2Pk")
    default String mapGsi2Pk(Double price) {
        String priceRange = "0-50";
        return WhiskyBase.GSI2_PK_PREFIX + priceRange;
    }

    @Named("mapGsi2Sk")
    default String mapGsi2Sk(WhiskyDto whiskyDto) {
        return String.format(Locale.ENGLISH, WhiskyBase.GSI2_SK_PREFIX, whiskyDto.getPrice())
                + whiskyDto.getId();
    }

    @Named("mapGsi3Pk")
    default String mapGsi3Pk(String brand) {
        return WhiskyBase.GSI3_PK_PREFIX + Optional.ofNullable(brand).orElse("").toLowerCase();
    }

    @Named("mapGsi3Sk")
    default String mapGsi3Sk(String id) {
        return WhiskyBase.GSI3_SK_PREFIX + id;
    }

}