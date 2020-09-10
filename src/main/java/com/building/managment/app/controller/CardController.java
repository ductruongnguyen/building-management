package com.building.managment.app.controller;

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
        List<Company> companyList = Arrays.asList(rest.getForObject("http://localhost:8080/card/all", Company[].class));
        System.out.println(companyList);
        model.addAttribute("companyList", companyList);
        return "listCard";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("company", new Company());
        return "/card/addCompany";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        Company company = rest.getForObject("http://localhost:8080/card/{MA_CT}", Company.class, id);
        model.addAttribute("company", company);
        return "/card/updateCompany";
    }

    @GetMapping("/delete")
    public String deleteService(@RequestParam("trackingId") String id) {
        rest.delete("http://localhost:8080/card/{MA_CT}", id);
        return "redirect:/card";
    }

    @GetMapping("/search")
    public String searchService(@RequestParam("keyword") String keyword, Model model) {
        List<Company> companyList = Arrays.asList(rest.getForObject("http://localhost:8080/card/search?keyword=" + keyword, Company[].class));
        model.addAttribute("companyList", companyList);
        return "/card/listCompany";
    }

    @PostMapping
    public String addServices(Company company) {
        System.out.println(company);
        rest.postForObject("http://localhost:8080/card", company, Company.class);
        return "redirect:/card";
    }

    @PostMapping("/update")
    public String updateServices(Services service) {
        System.out.println(service);
        rest.put("http://localhost:8080/card/{MA_CT}", service, service.getMA_DV());
        return "redirect:/card";
    }
}