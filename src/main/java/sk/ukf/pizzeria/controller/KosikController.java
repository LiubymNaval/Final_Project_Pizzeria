package sk.ukf.pizzeria.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.ukf.pizzeria.entity.PolozkaKosika;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.service.PizzaService;
import sk.ukf.pizzeria.service.PolozkaKosikaService;
import sk.ukf.pizzeria.service.PouzivatelService;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class KosikController {

    @Autowired
    private PolozkaKosikaService polozkaKosikaService;

    @Autowired
    private PouzivatelService pouzivatelService;

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public String showCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Pouzivatel user = pouzivatelService.findByEmail(principal.getName());
        List<PolozkaKosika> items = polozkaKosikaService.getCartItems(user);

        boolean hasInactiveItems = items.stream()
                .anyMatch(item -> !item.getPizzaVelkost().getPizza().isAktivna());

        BigDecimal total = items.stream()
                .filter(item -> item.getPizzaVelkost().getPizza().isAktivna())
                .map(item -> item.getPizzaVelkost().getCena().multiply(new BigDecimal(item.getMnozstvo())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("cartItems", items);
        model.addAttribute("totalPrice", total);
        model.addAttribute("hasInactiveItems", hasInactiveItems);;

        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long pizzaId,
                            @RequestParam Long velkostId,
                            @RequestParam Integer mnozstvo,
                            Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Pouzivatel user = pouzivatelService.findByEmail(principal.getName());
        polozkaKosikaService.addItemToCart(user, pizzaId, velkostId, mnozstvo);

        return "redirect:/cart?added";
    }

    @GetMapping("/delete/{id}")
    public String removeFromCart(@PathVariable Long id, Principal principal) {
        if (principal != null) {
            polozkaKosikaService.removeItem(id);
        }
        return "redirect:/cart";
    }
}
