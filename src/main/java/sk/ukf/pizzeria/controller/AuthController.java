package sk.ukf.pizzeria.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import sk.ukf.pizzeria.dto.RegistraciaDto;
import sk.ukf.pizzeria.service.PouzivatelService;

@Controller
public class AuthController {

    @Autowired
    private PouzivatelService pouzivatelService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistraciaDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid @ModelAttribute("user") RegistraciaDto dto,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        pouzivatelService.registerUser(dto);
        return "redirect:/login?success";
    }
}
