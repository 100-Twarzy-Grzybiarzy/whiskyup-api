package pl.kat.ue.whiskyup.mapper;

import org.mapstruct.Mapper;
import pl.kat.ue.whiskyup.model.Whisky;
import pl.kat.ue.whiskyup.model.WhiskyApi;

@Mapper(componentModel = "spring")
public interface WhiskyMapper {

    Whisky mapApiToModel(WhiskyApi whiskyApi);

    WhiskyApi mapModelToApi(Whisky whisky);
}
