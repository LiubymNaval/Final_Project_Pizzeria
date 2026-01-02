package sk.ukf.pizzeria.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.ukf.pizzeria.entity.Ingrediencia;
import sk.ukf.pizzeria.entity.Pizza;
import sk.ukf.pizzeria.entity.Tag;
import sk.ukf.pizzeria.service.*;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private PouzivatelService pouzivatelService;
    @Autowired private ObjednavkaService objednavkaService;
    @Autowired private IngredienciaService ingredienciaService;
    @Autowired private PizzaService pizzaService;
    @Autowired private TagService tagService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", pouzivatelService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/{id}/role")
    public String changeRole(@PathVariable Long id, @RequestParam String roleName) {
        pouzivatelService.changeUserRole(id, roleName);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/toggle-status")
    public String toggleUserStatus(@PathVariable Long id) {
        pouzivatelService.toggleUserStatus(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            pouzivatelService.deleteUser(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Používateľa nemožno odstrániť, pretože má priradené objednávky. Skúste ho radšej deaktivovať.");
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/orders")
    public String allOrders(Model model) {
        model.addAttribute("orders", objednavkaService.getAllOrders());
        return "admin/orders";
    }

    @GetMapping("/pizzas")
    public String listAllPizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.getAllPizzas());
        return "admin/pizzas-list";
    }

    @GetMapping("/pizzas/add")
    public String showAddPizzaForm(Model model) {
        Pizza pizza = new Pizza();

        if (model.containsAttribute("uploadedUrl")) {
            pizza.setObrazokUrl((String) model.getAttribute("uploadedUrl"));
        }

        model.addAttribute("pizza", pizza);
        model.addAttribute("allTags", tagService.getAllTags());
        model.addAttribute("allIngredients", ingredienciaService.getAll());
        return "admin/pizza-form";
    }

    @PostMapping("/pizzas/save")
    public String savePizza(@Valid @ModelAttribute Pizza pizza,
                            BindingResult bindingResult,
                            @RequestParam BigDecimal cenaMala,
                            @RequestParam BigDecimal cenaStredna,
                            @RequestParam BigDecimal cenaVelka,
                            @RequestParam(required = false) List<Long> selectedTags,
                            @RequestParam(required = false) List<Long> selectedIngredients,
                            Model model) throws IOException {

        if (selectedTags != null) {
            Set<Tag> tags = new HashSet<>(tagService.findAllById(selectedTags));
            pizza.setTagy(tags);
        }
        if (selectedIngredients != null) {
            Set<Ingrediencia> ingredients = new HashSet<>(ingredienciaService.findAllById(selectedIngredients));
            pizza.setIngrediencie(ingredients);
        }

        if (cenaMala == null || cenaMala.compareTo(BigDecimal.ZERO) <= 0) {
            bindingResult.rejectValue("velkosti", "error.cena", "Cena (33cm) musí byť väčšia ako nula");
        }
        if (cenaStredna == null || cenaStredna.compareTo(BigDecimal.ZERO) <= 0) {
            bindingResult.rejectValue("velkosti", "error.cena", "Cena (40cm) musí byť väčšia ako nula");
        }
        if (cenaVelka == null || cenaVelka.compareTo(BigDecimal.ZERO) <= 0) {
            bindingResult.rejectValue("velkosti", "error.cena", "Cena (50cm) musí byť väčšia ako nula");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("allTags", tagService.getAllTags());
            model.addAttribute("allIngredients", ingredienciaService.getAll());
            model.addAttribute("cenaMala", cenaMala);
            model.addAttribute("cenaStredna", cenaStredna);
            model.addAttribute("cenaVelka", cenaVelka);
            return "admin/pizza-form";
        }
        try {

        pizzaService.savePizzaWithSizes(pizza, cenaMala, cenaStredna, cenaVelka);

        return "redirect:/admin/pizzas";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Chyba pri ukladaní: " + e.getMessage());

            model.addAttribute("allTags", tagService.getAllTags());
            model.addAttribute("allIngredients", ingredienciaService.getAll());

            return "admin/pizza-form";
        }
    }
    @PostMapping("/pizzas/upload-image")
    public String uploadPizzaImage(@RequestParam("imageFile") MultipartFile imageFile,
                                   @RequestParam(required = false) Long pizzaId,
                                   RedirectAttributes redirectAttributes) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = pizzaService.saveImage(imageFile);
                redirectAttributes.addFlashAttribute("uploadedUrl", imagePath);
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Chyba pri nahrávaní: " + e.getMessage());
        }
        String returnUrl = (pizzaId != null) ? "/admin/pizzas/edit/" + pizzaId : "/admin/pizzas/add";
        return "redirect:" + returnUrl;
    }

    @GetMapping("/pizzas/edit/{id}")
    public String showEditPizzaForm(@PathVariable Long id, Model model) {
        Pizza pizza = pizzaService.getPizzaById(id);

        if (model.containsAttribute("uploadedUrl")) {
            pizza.setObrazokUrl((String) model.getAttribute("uploadedUrl"));
        }
        model.addAttribute("pizza", pizza);
        model.addAttribute("allTags", tagService.getAllTags());
        model.addAttribute("allIngredients", ingredienciaService.getAll());
        return "admin/pizza-form";
    }

    @PostMapping("/pizzas/delete/{id}")
    public String deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return "redirect:/admin/pizzas";
    }

    @GetMapping("/ingredients")
    public String listIngredients(Model model) {
        model.addAttribute("ingredients", ingredienciaService.getAll());
        return "admin/ingredients-list";
    }

    @PostMapping("/ingredients/add")
    public String addIngredient(@RequestParam String nazov) {
        if (ingredienciaService.getByName(nazov).isEmpty()) {
            Ingrediencia ing = new Ingrediencia();
            ing.setNazov(nazov);
            ingredienciaService.save(ing);
        }
        return "redirect:/admin/ingredients";
    }

    @PostMapping("/ingredients/delete/{id}")
    public String deleteIngredient(@PathVariable Long id) {
        ingredienciaService.delete(id);
        return "redirect:/admin/ingredients";
    }

    @GetMapping("/tags")
    public String listTags(Model model) {
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("newTag", new Tag());
        return "admin/tags-list";
    }

    @PostMapping("/tags/add")
    public String addTag(@ModelAttribute Tag tag) {
        if (tagService.getTagByName(tag.getNazov()).isEmpty()) {
            tagService.saveTag(tag);
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/edit/{id}")
    public String showEditTagForm(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTagById(id));
        return "admin/tag-edit";
    }

    @PostMapping("/tags/update")
    public String updateTag(@ModelAttribute Tag tag) {
        tagService.saveTag(tag);
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/delete/{id}")
    public String deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
