package br.casara.sigu.web.pages;

import br.casara.sigu.infrastructure.ProfileRepository;
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
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileRepository profileRepository;

  @GetMapping
  public String index(@ModelAttribute("error") final String error, final Model model) {
    model.addAttribute("error", error);
    return "profile/index";
  }

  @GetMapping("/new")
  public String newForm() {
    return "profile/new";
  }

  @GetMapping("/edit/{id}")
  public String editForm(
    @PathVariable("id") final UUID id,
    final Model model,
    final RedirectAttributes redirectAttributes
  ) {
    return this.profileRepository.findById(id).map(profile -> {
      model.addAttribute("domain", profile);
      return "profile/edit";
    }).orElseGet(() -> {
      redirectAttributes.addFlashAttribute("error", String.format("O perfil '%s' não está mais disponível.", id));
      return "redirect:/profiles";
    });
  }

}
