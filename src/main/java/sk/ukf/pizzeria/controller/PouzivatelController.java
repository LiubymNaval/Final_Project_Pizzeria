package sk.ukf.pizzeria.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.ukf.pizzeria.dto.ProfilDto;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.service.PouzivatelService;

import java.security.Principal;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Controller
public class PouzivatelController {

    @Autowired
    private PouzivatelService pouzivatelService;

    @GetMapping("/profile")
    public String showProfile(Principal principal, Model model) {
        Pouzivatel user = pouzivatelService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("fullUser", user);
        return "profile"; // profile.html
    }

    @PostMapping("/profile/update")
    public String updateProfile(Principal principal,
                                @Valid @ModelAttribute("user") ProfilDto profilDto,
                                BindingResult result,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model) throws IOException {

        if (result.hasErrors()) {
            Pouzivatel user = pouzivatelService.findByEmail(principal.getName());
            model.addAttribute("fullUser", user);
            return "profile";
        }

        if (!imageFile.isEmpty()) {
            String imagePath = pouzivatelService.saveImage(imageFile);
            profilDto.setObrazokUrl(imagePath);
        } else {
            Pouzivatel currentUser = pouzivatelService.findByEmail(principal.getName());
            profilDto.setObrazokUrl(currentUser.getObrazokUrl());
        }

        try {
            pouzivatelService.updateProfile(principal.getName(), profilDto);
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            Pouzivatel user = pouzivatelService.findByEmail(principal.getName());
            model.addAttribute("fullUser", user);
            return "profile";
        }

        return "redirect:/profile?success=profileUpdated";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(Principal principal,
                                 @RequestParam("newPassword") String newPassword,
                                 RedirectAttributes redirectAttributes) {

        if (newPassword == null || newPassword.length() < 8) {
            redirectAttributes.addFlashAttribute("errorMessage", "Heslo musí mať aspoň 8 znakov!");
            return "redirect:/profile";
        }

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