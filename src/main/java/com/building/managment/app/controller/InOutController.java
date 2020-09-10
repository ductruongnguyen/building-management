package com.building.managment.app.controller;

import com.building.managment.app.model.BuildingMember;
import com.building.managment.app.model.InOut;
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
@RequestMapping("/in-out")
public class InOutController {

    RestTemplate rest = new RestTemplate();

    @GetMapping
    public String showServices(Model model) {
        List<InOut> inoutList = Arrays.asList(rest.getForObject("http://localhost:8080/in-out/all", InOut[].class));
        System.out.println(inoutList);
        model.addAttribute("inoutList", inoutList);
        return "listInOut";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("inout", new InOut());
        return "addInOut";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        InOut inout = rest.getForObject("http://localhost:8080/in-out/{MA_RV}", InOut.class, id);
        model.addAttribute("inout", inout);
        return "updateInOut";
    }

    @GetMapping("/delete")
    public String deleteService(@RequestParam("trackingId") String id) {
        rest.delete("http://localhost:8080/in-out/{MA_RV}", id);
        return "redirect:/in-out";
    }

    @GetMapping("/search")
    public String searchService(@RequestParam("keyword") String keyword, Model model) {
        List<InOut> inoutList = Arrays.asList(rest.getForObject("http://localhost:8080/in-out/search?keyword=" + keyword, InOut[].class));
        model.addAttribute("inoutList", inoutList);
        return "searchInOut";
    }

    @PostMapping
    public String addServices(InOut inout) {
        System.out.println(inout);
        rest.postForObject("http://localhost:8080/in-out", inout, InOut.class);
        return "redirect:/in-out";
    }

    @PostMapping("/update")
    public String updateServices(InOut inout) {
        System.out.println(inout);
        rest.put("http://localhost:8080/in-out/{MA_RV}", inout, inout.getMA_RV());
        return "redirect:/in-out";
    }
}