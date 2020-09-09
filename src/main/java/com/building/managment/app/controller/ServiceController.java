package com.building.managment.app.controller;

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
@RequestMapping("/service")
public class ServiceController {

    RestTemplate rest = new RestTemplate();

    @GetMapping
    public String showServices(Model model) {
        List<Services> servicesList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/service/all", Services[].class));
        System.out.println(servicesList);
        model.addAttribute("services", servicesList);
        return "listServices";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("service", new Services());
        return "addServices";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        Services service = rest.getForObject("http://172.16.0.196:8080/service/{MA_DV}", Services.class, id);
        model.addAttribute("service", service);
        return "addServices";
    }

    @GetMapping("/delete")
    public String deleteService(@RequestParam("trackingId") String id) {
        rest.delete("http://172.16.0.196:8080/service/{MA_DV}", id);
        return "redirect:/service";
    }

    @GetMapping("/search")
    public String searchService(@RequestParam("keyword") String keyword, Model model) {
        List<Services> servicesList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/service/search?keyword=" + keyword, Services[].class));
        model.addAttribute("services", servicesList);
        return "addServices";
    }

    @PostMapping
    public String addServices(Services service) {
        System.out.println(service);
        rest.postForObject("http://172.16.0.196:8080/service", service, Services.class);
        return "redirect:/service";
    }

    @PostMapping("/update")
    public String updateServices(Services service) {
        System.out.println(service);
        rest.put("http://172.16.0.196:8080/service/{MA_DV}", service, service.getMA_DV());
        return "redirect:/service";
    }
}