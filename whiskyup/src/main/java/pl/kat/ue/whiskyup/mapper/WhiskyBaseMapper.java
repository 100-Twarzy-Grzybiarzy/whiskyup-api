package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.*;
import pl.kat.ue.whiskyup.model.WhiskyBase;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.service.PriceRangeService;
import pl.kat.ue.whiskyup.utils.manager.KsuidManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface WhiskyBaseMapper {

    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy");

    @Mapping(target = "pk", source = "id", qualifiedByName = "mapPk")
    @Mapping(target = "sk", source = "id", qualifiedByName = "mapSk")
    @Mapping(target = "gsi1pk", source = "addedDate", qualifiedByName = "mapGsi1Pk")
    @Mapping(target = "gsi1sk", source = "id", qualifiedByName = "mapGsi1Sk")
    @Mapping(target = "gsi2pk", source = "price", qualifiedByName = "mapGsi2Pk")
    @Mapping(target = "gsi2sk", source = "whiskyDto", qualifiedByName = "mapGsi2Sk")
    @Mapping(target = "gsi3pk", source = "brand", qualifiedByName = "mapGsi3Pk")
    @Mapping(target = "gsi3sk", source = "id", qualifiedByName = "mapGsi3Sk")
    @Mapping(target = "gsi4pk", expression = "java( WhiskyBase.GSI4_PK_PREFIX )")
    @Mapping(target = "gsi4sk", source = "url", qualifiedByName = "mapGsi4Sk")
    WhiskyBase mapDtoToModel(WhiskyDto whiskyDto);

    WhiskyDto mapModelToDto(WhiskyBase whiskyBase);

    @BeforeMapping
    default void generateIdFromAddedDate(WhiskyDto whiskyDto, @MappingTarget WhiskyBase.WhiskyBaseBuilder target) {
        LocalDate addedDate = LocalDate.parse(whiskyDto.getAddedDate(), DATE_FORMAT);
        whiskyDto.setId(KsuidManager.newKsuid(addedDate));
        whiskyDto.setAddedDate(addedDate.toString());
    }

    @Named("mapPk")
    default String mapPk(String id) {
        return WhiskyBase.PK_PREFIX + id;
    }

    @Named("mapSk")
    default String mapSk(String id) {
        return WhiskyBase.SK_PREFIX + id;
    }

    @Named("mapGsi1Pk")
    default String mapGsi1Pk(String addedDate) {
        return WhiskyBase.GSI1_PK_PREFIX + addedDate;
    }

    @Named("mapGsi1Sk")
    default String mapGsi1Sk(String id) {
        return WhiskyBase.GSI1_SK_PREFIX + id;
    }

    @Named("mapGsi2Pk")
    default String mapGsi2Pk(Double price) {
        return WhiskyBase.GSI2_PK_PREFIX + PriceRangeService.getPriceRange(price);
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

    @Named("mapGsi4Sk")
    default String mapGsi4Sk(String url) {
        return WhiskyBase.GSI4_SK_PREFIX + url;
    }
}