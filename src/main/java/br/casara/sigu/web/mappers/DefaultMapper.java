package br.casara.sigu.web.mappers;

import br.casara.sigu.domain.Position;
import br.casara.sigu.domain.Profile;
import br.casara.sigu.web.dto.IdNameResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DefaultMapper {

  List<IdNameResponseDto> fromPositions(List<Position> positions);

  List<IdNameResponseDto> fromProfiles(List<Profile> profiles);

}
