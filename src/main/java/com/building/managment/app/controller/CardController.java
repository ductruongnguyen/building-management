package com.building.managment.app.controller;

import com.building.managment.app.model.Card;
import com.building.managment.app.model.Company;
import com.building.managment.app.model.Services;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/card")
public class CardController {

    RestTemplate rest = new RestTemplate();

    @GetMapping
    public String showServices(Model model) {
        List<Card> cardList = Arrays.asList(rest.getForObject("http://localhost:8080/card/all", Card[].class));
        System.out.println(cardList);
        model.addAttribute("cardList", cardList);
        return "listCard";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("cards", new Card());
        return "addCard";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        Card card = rest.getForObject("http://localhost:8080/card/{MA_CT}", Card.class, id);
        model.addAttribute("card", card);
        return "updateCard";
    }

    @GetMapping("/delete")
    public String deleteService(@RequestParam("trackingId") String id) {
        rest.delete("http://localhost:8080/card/{MA_CT}", id);
        return "redirect:/card";
    }

    @GetMapping("/search")
    public String searchService(@RequestParam("keyword") String keyword, Model model) {
        List<Card> cardList = Arrays.asList(rest.getForObject("http://localhost:8080/card/search?keyword=" + keyword, Card[].class));
        model.addAttribute("cardList", cardList);
        return "searchCard";
    }

    @PostMapping
    public String addServices(Card card) {
        System.out.println(card);
        rest.postForObject("http://localhost:8080/card", card, Card.class);
        return "redirect:/card";
    }

    @PostMapping("/update")
    public String updateServices(Card card) {
        System.out.println(card);
        rest.put("http://localhost:8080/card/{MA_CT}", card, card.getMA_THE());
        return "redirect:/card";
    }
}