package br.casara.sigu.web.api.v1;

import br.casara.sigu.domain.Position;
import br.casara.sigu.infrastructure.PositionRepository;
import br.casara.sigu.web.dto.IdNameResponseDto;
import br.casara.sigu.web.dto.PositionDto;
import br.casara.sigu.web.mappers.DefaultMapper;
import br.casara.sigu.web.problems.DeleteRestrictionProblem;
import br.casara.sigu.web.problems.NotFoundProblem;
import br.casara.sigu.web.validation.PositionDtoValidator;
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
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
public class PositionsController {

  private final DefaultMapper defaultMapper;

  private final PositionRepository positionRepository;

  private final PositionDtoValidator positionDtoValidator;

  @InitBinder("positionDto")
  public void setupBinder(final WebDataBinder binder) {
    binder.addValidators(this.positionDtoValidator);
  }

  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<IdNameResponseDto> list() {
    return this.defaultMapper.fromPositions(this.positionRepository.findAllOrderByName());
  }

  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody @NotNull @Validated final PositionDto positionDto) {
    this.positionRepository.save(Position.builder()
      .name(positionDto.getName())
      .build());
  }

  @PutMapping("{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public void update(
    @PathVariable("id") final UUID id,
    @RequestBody @NotNull @Validated final PositionDto positionDto
  ) {
    this.findAndExecuteOrElseThrow(id, position -> {
      position.setName(positionDto.getName());
      this.positionRepository.save(position);
    });
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") final UUID id) {
    this.findAndExecuteOrElseThrow(id, position -> {
      if (!position.getUsers().isEmpty()) {
        throw new DeleteRestrictionProblem("Este cargo possui usuários vinculados e não pode ser excluído.");
      }
      this.positionRepository.delete(position);
    });
  }

  private void findAndExecuteOrElseThrow(final UUID id, final Consumer<Position> action) {
    this.positionRepository.findById(id)
      .ifPresentOrElse(action, () -> {
        throw new NotFoundProblem(String.format("O cargo '%s' não está mais disponível.", id));
      });
  }

}
