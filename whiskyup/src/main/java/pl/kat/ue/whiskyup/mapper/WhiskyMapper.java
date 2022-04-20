package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.model.WhiskyDto;

@Mapper(componentModel = "spring")
public interface WhiskyMapper {

    String PREFIX = Whisky.PK_PREFIX;

    @Mapping(target = "url", expression = "java( PREFIX + whiskyDto.getUrl() )")
    Whisky mapDtoToModel(WhiskyDto whiskyDto);

    @Mapping(target = "url", expression = "java( whisky.getUrl().replace(PREFIX, \"\") )")
    WhiskyDto mapModelToDto(Whisky whisky);

}