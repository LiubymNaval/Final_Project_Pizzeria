package sk.ukf.pizzeria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sk.ukf.pizzeria.entity.Objednavka;
import sk.ukf.pizzeria.entity.PolozkaKosika;
import sk.ukf.pizzeria.entity.Pouzivatel;
import sk.ukf.pizzeria.model.StavObjednavky;
import sk.ukf.pizzeria.repository.PouzivatelRepository;
import sk.ukf.pizzeria.service.ObjednavkaService;
import sk.ukf.pizzeria.service.PolozkaKosikaService;
import sk.ukf.pizzeria.service.PouzivatelService;

import java.security.Principal;
import java.util.List;

@Controller
public class ObjednavkaController {

    @Autowired
    private ObjednavkaService objednavkaService;

    @Autowired
    private PolozkaKosikaService polozkaKosikaService;

    @Autowired
    private PouzivatelService pouzivatelService;

    @Autowired
    private PouzivatelRepository pouzivatelRepository;

    @PostMapping("/checkout")
    public String processOrder(@RequestParam String adresa,
                               @RequestParam(required = false) String poznamka,
                               Principal principal) {

        Pouzivatel pouzivatel= pouzivatelService.findByEmail(principal.getName());

        List<PolozkaKosika> items = polozkaKosikaService.getCartItems(pouzivatel);

        objednavkaService.createOrder(pouzivatel, adresa, poznamka, items);

        return "redirect:/my-orders?success=created";
    }

    @PostMapping("/my-orders/cancel/{id}")
    public String cancel(@PathVariable Long id, Principal principal) {
        try {
            objednavkaService.cancelOrder(id, principal.getName());
        } catch (Exception e) {
            return "redirect:/my-orders?error=" + e.getMessage();
        }
        return "redirect:/my-orders?success=cancelled";
    }

    @PostMapping("/kitchen/order/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam StavObjednavky status, Principal principal) {
        Pouzivatel kuchar = pouzivatelRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Používateľ nenájdený"));

        objednavkaService.changeStatus(id, status, kuchar);
        return "redirect:/kitchen";
    }

    @PostMapping("/delivery/order/{id}/status")
    public String updateDeliveryStatus(@PathVariable Long id, @RequestParam StavObjednavky status, Principal principal) {
        Pouzivatel kurier = pouzivatelRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Používateľ nenájdený"));

        objednavkaService.changeStatus(id, status, kurier);
        return "redirect:/delivery";
    }

    @GetMapping("/my-orders")
    public String showMyOrders(@RequestParam(defaultValue = "0") int page, Model model, Principal principal) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<Objednavka> orderPage = objednavkaService.getMyOrders(principal.getName(), pageable);

        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());

        return "my-orders";
    }

    @GetMapping("/kitchen")
    public String showKitchenPanel(Model model) {
        model.addAttribute("orders", objednavkaService.getOrdersForKitchen());
        model.addAttribute("hasNewOrders", objednavkaService.checkNewOrders());
        return "kitchen-panel";
    }

    @GetMapping("/delivery")
    public String showDeliveryPanel(Model model) {
        model.addAttribute("orders", objednavkaService.getOrdersForDelivery());
        return "delivery-panel";
    }

}