package sk.ukf.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import sk.ukf.pizzeria.entity.Pizza;
import sk.ukf.pizzeria.service.PizzaService;
import org.springframework.ui.Model;

@Controller
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/")
    public String listPizzas(@RequestParam(defaultValue = "0") int page, Model model) {
        Pageable pageable = PageRequest.of(page, 9);

        Page<Pizza> pizzaPage = pizzaService.getAllActivePizzas(pageable);

        model.addAttribute("pizzas", pizzaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pizzaPage.getTotalPages());
        return "index";
    }
    @GetMapping("/search")
    public String search(@RequestParam("query") String query,
                         @RequestParam(defaultValue = "0") int page,
                         Model model) {
        Pageable pageable = PageRequest.of(page, 9);

        Page<Pizza> pizzaPage = pizzaService.searchPizzas(query, pageable);

        model.addAttribute("pizzas", pizzaPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pizzaPage.getTotalPages());
        model.addAttribute("query", query);
        return "index";
    }

    @GetMapping("/pizza/{slug}")
    public String pizzaDetail(@PathVariable String slug, Model model) {
        Pizza pizza = pizzaService.getPizzaBySlug(slug);

        if (pizza == null) {
            return "redirect:/";
        }

        model.addAttribute("pizza", pizza);

        return "pizza-detail";
    }
}
