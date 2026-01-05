package sk.ukf.pizzeria.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    public String updateProfile(@Valid @ModelAttribute("user") ProfilDto profilDto,
                                BindingResult result,
                                Principal principal,
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
        try {
            pouzivatelService.updatePassword(principal.getName(), newPassword);
            redirectAttributes.addFlashAttribute("successMessage", "Heslo bolo úspešne zmenené!");
            return "redirect:/profile?success";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("passwordError", e.getMessage());
            return "redirect:/profile";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("passwordError", "Nastala neočakávaná chyba.");
            return "redirect:/profile";
        }
    }

    @PostMapping("/profile/delete")
    public String deleteAccount(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        Pouzivatel user = pouzivatelService.findByEmail(principal.getName());
        pouzivatelService.deleteUser(user.getId());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login?deleted=true";
    }
}