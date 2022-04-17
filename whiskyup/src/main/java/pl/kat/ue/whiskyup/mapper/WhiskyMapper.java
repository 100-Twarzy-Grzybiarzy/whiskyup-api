package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.model.WhiskyApi;

@Mapper(componentModel = "spring")
public interface WhiskyMapper {

    String PREFIX = pl.kat.ue.whiskyup.model.Whisky.PK_PREFIX;

    @Mapping(target = "url", expression = "java( PREFIX + whiskyApi.getUrl() )")
    Whisky mapApiToModel(WhiskyApi whiskyApi);

    @Mapping(target = "url", expression = "java( whisky.getUrl().replace(PREFIX, \"\") )")
    WhiskyApi mapModelToApi(Whisky whisky);

}