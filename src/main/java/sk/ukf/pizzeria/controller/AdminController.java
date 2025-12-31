package sk.ukf.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("allTags", tagService.getAllTags());
        model.addAttribute("allIngredients", ingredienciaService.getAll());
        return "admin/pizza-form";
    }

    @PostMapping("/pizzas/save")
    public String savePizza(@ModelAttribute Pizza pizza,
                            @RequestParam BigDecimal cenaMala,
                            @RequestParam BigDecimal cenaStredna,
                            @RequestParam BigDecimal cenaVelka,
                            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            String imagePath = pizzaService.saveImage(imageFile);
            pizza.setObrazokUrl(imagePath);
        } else {
            if (pizza.getId() != null) {
                Pizza existingPizza = pizzaService.getPizzaById(pizza.getId());
                pizza.setObrazokUrl(existingPizza.getObrazokUrl());
            }
        }

        pizzaService.savePizzaWithSizes(pizza, cenaMala, cenaStredna, cenaVelka);

        return "redirect:/admin/pizzas";
    }

    @GetMapping("/pizzas/edit/{id}")
    public String showEditPizzaForm(@PathVariable Long id, Model model) {
        model.addAttribute("pizza", pizzaService.getPizzaById(id));
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
