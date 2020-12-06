package br.casara.sigu.web.pages;

import br.casara.sigu.infrastructure.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

  private final PositionRepository positionRepository;

  @GetMapping
  public String index(@ModelAttribute("error") final String error, final Model model) {
    model.addAttribute("error", error);
    return "position/index";
  }

  @GetMapping("/new")
  public String newForm() {
    return "position/new";
  }

  @GetMapping("/edit/{id}")
  public String editForm(
    @PathVariable("id") final UUID id,
    final Model model,
    final RedirectAttributes redirectAttributes
  ) {
    return this.positionRepository.findById(id).map(position -> {
      model.addAttribute("domain", position);
      return "position/edit";
    }).orElseGet(() -> {
      redirectAttributes.addFlashAttribute("error", String.format("O cargo '%s' não está mais disponível.", id));
      return "redirect:/positions";
    });
  }

}
