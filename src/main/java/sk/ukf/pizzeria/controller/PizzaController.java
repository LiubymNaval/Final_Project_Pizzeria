package sk.ukf.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.ukf.pizzeria.service.PizzaService;
import org.springframework.ui.Model;

@Controller
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/")
    public String listPizzas(Model model) {
        model.addAttribute("pizzas", pizzaService.getAllActivePizzas());
        return "index";
    }
    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("pizzas", pizzaService.searchPizzas(query));
        return "index";
    }
}
