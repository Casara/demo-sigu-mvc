package br.casara.sigu.web.validation;

import br.casara.sigu.infrastructure.PositionRepository;
import br.casara.sigu.infrastructure.UserRepository;
import br.casara.sigu.web.dto.UserDto;
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
public class UserDtoValidator implements Validator {

  private final PositionRepository positionRepository;

  private final UserRepository userRepository;

  @Override
  public boolean supports(@NonNull final Class<?> clazz) {
    return UserDto.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(@NonNull final Object target, @NonNull final Errors errors) {
    final var userDto = (UserDto) target;

    if (StringUtils.hasLength(userDto.getName())
      && !errors.hasFieldErrors(UserDto.Fields.NAME)
      && !this.validateUniqueName(userDto.getName())) {
      errors.rejectValue(UserDto.Fields.NAME, "name.unique", "Já existe um usuário com esse nome.");
    }

    if (StringUtils.hasLength(userDto.getCpf())
      && !errors.hasFieldErrors(UserDto.Fields.CPF)
      && !this.validateUniqueCpf(userDto.getCpf().replaceAll("[^0-9]", ""))) {
      errors.rejectValue(UserDto.Fields.CPF, "cpf.unique", "Já existe um usuário com esse CPF.");
    }

    if (StringUtils.hasLength(userDto.getPositionId())
      && !errors.hasFieldErrors(UserDto.Fields.POSITION_ID)
      && !this.positionRepository.existsById(UUID.fromString(userDto.getPositionId()))) {
      errors.rejectValue(UserDto.Fields.POSITION_ID, "positionId.unique", "Cargo não encontrado.");
    }
  }

  private boolean validateUniqueName(final String value) {
    return this.getPathIdVariable()
      .map(UUID::fromString)
      .map(id -> !this.userRepository.existsByNameAndIdIsNot(value, id))
      .orElseGet(() -> !this.userRepository.existsByName(value));
  }

  private boolean validateUniqueCpf(final String value) {
    return this.getPathIdVariable()
      .map(UUID::fromString)
      .map(id -> !this.userRepository.existsByCpfAndIdIsNot(value, id))
      .orElseGet(() -> !this.userRepository.existsByCpf(value));
  }

  @SuppressWarnings("unchecked")
  private Optional<String> getPathIdVariable() {
    final var variables = (Map<String, String>) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())
      .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
    return Optional.ofNullable(variables).map(map -> map.get("id"));
  }

}
