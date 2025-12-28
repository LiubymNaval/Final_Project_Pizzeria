package sk.ukf.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.ukf.pizzeria.dto.ProfilDto;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.service.PouzivatelService;

import java.security.Principal;

@Controller
public class PouzivatelController {

    @Autowired
    private PouzivatelService pouzivatelService;

    @GetMapping("/profile")
    public String showProfile(Principal principal, Model model) {
        Pouzivatel user = pouzivatelService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "profile"; // profile.html
    }
    @PostMapping("/profile/update")
    public String updateProfile(Principal principal, @ModelAttribute ProfilDto profilDto) {
        pouzivatelService.updateProfile(principal.getName(), profilDto);
        return "redirect:/profile?success=profileUpdated";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(Principal principal, @RequestParam("newPassword") String newPassword) {
        pouzivatelService.updatePassword(principal.getName(), newPassword);
        return "redirect:/profile?success=passwordChanged";
    }

    @PostMapping("/profile/delete")
    public String deleteAccount(Principal principal) {
        Pouzivatel user = pouzivatelService.findByEmail(principal.getName());
        pouzivatelService.deleteUser(user.getId());
        return "redirect:/logout";
    }
}