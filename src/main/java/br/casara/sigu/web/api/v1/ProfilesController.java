package br.casara.sigu.web.api.v1;

import br.casara.sigu.domain.Profile;
import br.casara.sigu.infrastructure.ProfileRepository;
import br.casara.sigu.web.dto.IdNameResponseDto;
import br.casara.sigu.web.dto.ProfileDto;
import br.casara.sigu.web.mappers.DefaultMapper;
import br.casara.sigu.web.problems.DeleteRestrictionProblem;
import br.casara.sigu.web.problems.NotFoundProblem;
import br.casara.sigu.web.validation.ProfileDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfilesController {

  private final DefaultMapper defaultMapper;

  private final ProfileRepository profileRepository;

  private final ProfileDtoValidator profileDtoValidator;

  @InitBinder("profileDto")
  public void setupBinder(final WebDataBinder binder) {
    binder.addValidators(this.profileDtoValidator);
  }

  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<IdNameResponseDto> list() {
    return this.defaultMapper.fromProfiles(this.profileRepository.findAllOrderByName());
  }

  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody @NotNull @Validated final ProfileDto profileDto) {
    this.profileRepository.save(Profile.builder()
      .name(profileDto.getName())
      .build());
  }

  @PutMapping("{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public void update(
    @PathVariable("id") final UUID id,
    @RequestBody @NotNull @Validated final ProfileDto profileDto
  ) {
    this.findAndExecuteOrElseThrow(id, profile -> {
      profile.setName(profileDto.getName());
      this.profileRepository.save(profile);
    });
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") final UUID id) {
    this.findAndExecuteOrElseThrow(id, profile -> {
      if (!profile.getUsers().isEmpty()) {
        throw new DeleteRestrictionProblem("Este perfil possui usuários vinculados e não pode ser excluído.");
      }
      this.profileRepository.delete(profile);
    });
  }

  private void findAndExecuteOrElseThrow(final UUID id, final Consumer<Profile> action) {
    this.profileRepository.findById(id)
      .ifPresentOrElse(action, () -> {
        throw new NotFoundProblem(String.format("O perfil '%s' não está mais disponível.", id));
      });
  }

}
