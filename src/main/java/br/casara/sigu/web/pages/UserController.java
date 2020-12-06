package br.casara.sigu.web.pages;

import br.casara.sigu.infrastructure.UserRepository;
import br.casara.sigu.web.mappers.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final ObjectMapper objectMapper;

  private final UserMapper userMapper;

  private final UserRepository userRepository;

  @GetMapping
  public String index(@ModelAttribute("error") final String error, final Model model) {
    model.addAttribute("error", error);
    return "user/index";
  }

  @GetMapping("/new")
  public String newForm() {
    return "user/new";
  }

  @GetMapping("/edit/{id}")
  public String editForm(
    @PathVariable("id") final UUID id,
    final Model model,
    final RedirectAttributes redirectAttributes
  ) {
    return this.userRepository.findById(id).map(user -> {
      model.addAttribute("domain", user);
      return "user/edit";
    }).orElseGet(() -> {
      redirectAttributes.addFlashAttribute("error", String.format("O usuário '%s' não está mais disponível.", id));
      return "redirect:/users";
    });
  }

}
