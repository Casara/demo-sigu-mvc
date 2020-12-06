package br.casara.sigu.web.validation;

import br.casara.sigu.infrastructure.ProfileRepository;
import br.casara.sigu.web.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProfileDtoValidator implements Validator {

  private final ProfileRepository profileRepository;

  @Override
  public boolean supports(@NonNull final Class<?> clazz) {
    return ProfileDto.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(@NonNull final Object target, @NonNull final Errors errors) {
    final var perfilDto = (ProfileDto) target;

    if (StringUtils.hasLength(perfilDto.getName())
      && !errors.hasFieldErrors(ProfileDto.Fields.NAME)
      && !this.validateUniqueName(perfilDto.getName())) {
      errors.rejectValue(ProfileDto.Fields.NAME, "name.unique", "Já existe um perfil com esse nome.");
    }
  }

  private boolean validateUniqueName(final String value) {
    return this.getPathIdVariable()
      .map(UUID::fromString)
      .map(id -> !this.profileRepository.existsByNameAndIdIsNot(value, id))
      .orElseGet(() -> !this.profileRepository.existsByName(value));
  }

  @SuppressWarnings("unchecked")
  private Optional<String> getPathIdVariable() {
    final var variables = (Map<String, String>) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
      .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
    return Optional.ofNullable(variables).map(map -> map.get("id"));
  }

}
