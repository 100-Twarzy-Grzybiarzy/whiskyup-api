package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.*;
import pl.kat.ue.whiskyup.dynamometadata.AttributeValues;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.model.WhiskyDto;
import pl.kat.ue.whiskyup.service.PriceRangeService;
import pl.kat.ue.whiskyup.utils.manager.KsuidManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface WhiskyMapper {

    @Mapping(target = "pk", source = "id", qualifiedByName = "mapPk")
    @Mapping(target = "sk", source = "id", qualifiedByName = "mapSk")
    @Mapping(target = "gsi1pk", source = "addedDate", qualifiedByName = "mapGsi1Pk")
    @Mapping(target = "gsi1sk", source = "id", qualifiedByName = "mapGsi1Sk")
    @Mapping(target = "gsi2pk", source = "price", qualifiedByName = "mapGsi2Pk")
    @Mapping(target = "gsi2sk", source = "whiskyDto", qualifiedByName = "mapGsi2Sk")
    @Mapping(target = "gsi3pk", source = "brand", qualifiedByName = "mapGsi3Pk")
    @Mapping(target = "gsi3sk", source = "id", qualifiedByName = "mapGsi3Sk")
    @Mapping(target = "gsi4pk", expression = "java( pl.kat.ue.whiskyup.dynamometadata.AttributeValues.Whisky.GSI4_PARTITION_KEY )")
    @Mapping(target = "gsi4sk", source = "url", qualifiedByName = "mapGsi4Sk")
    Whisky mapDtoToModel(WhiskyDto whiskyDto);

    WhiskyDto mapModelToDto(Whisky whisky);

    @BeforeMapping
    default void generateIdFromAddedDate(WhiskyDto whiskyDto, @MappingTarget Whisky.WhiskyBuilder target) {
        LocalDate addedDate = LocalDate.parse(whiskyDto.getAddedDate());
        whiskyDto.setId(KsuidManager.newKsuid(addedDate));
    }

    @Named("mapPk")
    default String mapPk(String id) {
        return AttributeValues.Whisky.PARTITION_KEY + id;
    }

    @Named("mapSk")
    default String mapSk(String id) {
        return AttributeValues.Whisky.SORT_KEY + id;
    }

    @Named("mapGsi1Pk")
    default String mapGsi1Pk(String addedDate) {
        return AttributeValues.Whisky.GSI1_PARTITION_KEY + addedDate;
    }

    @Named("mapGsi1Sk")
    default String mapGsi1Sk(String id) {
        return AttributeValues.Whisky.GSI1_SORT_KEY + id;
    }

    @Named("mapGsi2Pk")
    default String mapGsi2Pk(Double price) {
        return AttributeValues.Whisky.GSI2_PARTITION_KEY + PriceRangeService.getPriceRange(price);
    }

    @Named("mapGsi2Sk")
    default String mapGsi2Sk(WhiskyDto whiskyDto) {
        return String.format(Locale.ENGLISH, AttributeValues.Whisky.GSI2_SORT_KEY, whiskyDto.getPrice())
                + whiskyDto.getId();
    }

    @Named("mapGsi3Pk")
    default String mapGsi3Pk(String brand) {
        return AttributeValues.Whisky.GSI3_PARTITION_KEY + Optional.ofNullable(brand).orElse("").toLowerCase();
    }

    @Named("mapGsi3Sk")
    default String mapGsi3Sk(String id) {
        return AttributeValues.Whisky.GSI3_SORT_KEY + id;
    }

    @Named("mapGsi4Sk")
    default String mapGsi4Sk(String url) {
        return AttributeValues.Whisky.GSI4_SORT_KEY + url;
    }
}