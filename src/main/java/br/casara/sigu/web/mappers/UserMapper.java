package br.casara.sigu.web.mappers;

import br.casara.sigu.domain.Position;
import br.casara.sigu.domain.Profile;
import br.casara.sigu.domain.User;
import br.casara.sigu.domain.enumeration.Gender;
import br.casara.sigu.web.dto.UserDto;
import br.casara.sigu.web.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  @Mapping(target = "cpf", qualifiedByName = "MaskedToDigits")
  @Mapping(target = "gender", qualifiedByName = "StringToGender")
  @Mapping(source = "positionId", target = "position", qualifiedByName = "StringToPosition")
  @Mapping(target = "profiles", qualifiedByName = "StringsToProfiles")
  User to(UserDto userDto);

  UserResponseDto from(User user);

  List<UserResponseDto> from(List<User> users);

  @Named("MaskedToDigits")
  static String toDigits(@NonNull final String value) {
    return value.replaceAll("[^0-9]", "");
  }

  @Named("StringToGender")
  static Gender toGender(@NonNull final String value) {
    return Gender.fromValue(value);
  }

  @Named("StringToPosition")
  static Position toPosition(@NonNull final String value) {
    return Position.builder().id(UUID.fromString(value)).build();
  }

  @Named("StringsToProfiles")
  static List<Profile> toProfiles(@NonNull final List<String> ids) {
    return ids.stream()
      .map(UUID::fromString)
      .map(id -> Profile.builder().id(id).build())
      .collect(Collectors.toList());
  }

}
