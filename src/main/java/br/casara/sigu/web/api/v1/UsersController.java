package br.casara.sigu.web.api.v1;

import br.casara.sigu.domain.Person;
import br.casara.sigu.domain.User;
import br.casara.sigu.infrastructure.UserRepository;
import br.casara.sigu.web.dto.UserDto;
import br.casara.sigu.web.dto.UserResponseDto;
import br.casara.sigu.web.mappers.UserMapper;
import br.casara.sigu.web.problems.NotFoundProblem;
import br.casara.sigu.web.validation.UserDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

  private final UserMapper userMapper;

  private final UserRepository userRepository;

  private final UserDtoValidator userDtoValidator;

  @InitBinder("userDto")
  public void setupBinder(final WebDataBinder binder) {
    binder.addValidators(this.userDtoValidator);
  }

  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<UserResponseDto> list() {
    return this.userMapper.from(this.userRepository.findAllOrderByName());
  }

  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody @NotNull @Validated final UserDto userDto) {
    this.userRepository.save(this.userMapper.to(userDto));
  }

  @PutMapping("{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public void update(
    @PathVariable("id") final UUID id,
    @RequestBody @NotNull @Validated final UserDto userDto
  ) {
    this.findAndExecuteOrElseThrow(id, user -> {
      BeanUtils.copyProperties(
        this.userMapper.to(userDto), user,
        Person.Fields.ID, User.Fields.CREATED_AT);
      this.userRepository.save(user);
    });
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") final UUID id) {
    this.findAndExecuteOrElseThrow(id, this.userRepository::delete);
  }

  private void findAndExecuteOrElseThrow(final UUID id, final Consumer<User> action) {
    this.userRepository.findById(id)
      .ifPresentOrElse(action, () -> {
        throw new NotFoundProblem(String.format("O usuário '%s' não está mais disponível.", id));
      });
  }

}
