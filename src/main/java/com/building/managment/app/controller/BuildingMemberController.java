package com.building.managment.app.controller;

import com.building.managment.app.model.BuildingMember;
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
@RequestMapping("/building-member")
public class BuildingMemberController {

    RestTemplate rest = new RestTemplate();

    @GetMapping
    public String showServices(Model model) {
        List<BuildingMember> buildingmemberList = Arrays.asList(rest.getForObject("http://localhost:8080/building-member/all", BuildingMember[].class));
        System.out.println(buildingmemberList);
        model.addAttribute("buildingmemberList", buildingmemberList);
        return "/building-member/listbuildingmember";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("buildingmember", new BuildingMember());
        return "/building-member/addbuildingmember";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        BuildingMember buildingmember = rest.getForObject("http://localhost:8080/building-member/{MA_NV}", BuildingMember.class, id);
        model.addAttribute("buildingmember", buildingmember);
        return "/building-member/updatebuildingmember";
    }

    @GetMapping("/delete")
    public String deleteService(@RequestParam("trackingId") String id) {
        rest.delete("http://localhost:8080/building-member/{MA_NV}", id);
        return "redirect:/building-member";
    }

    @GetMapping("/search")
    public String searchService(@RequestParam("keyword") String keyword, Model model) {
        List<BuildingMember> buildingmemberList = Arrays.asList(rest.getForObject("http://localhost:8080/building-member/search?keyword=" + keyword, BuildingMember[].class));
        model.addAttribute("buildingmemberList", buildingmemberList);
        return "/building-member/listbuildingmember";
    }

    @PostMapping
    public String addServices(BuildingMember buildingmember) {
        System.out.println(buildingmember);
        rest.postForObject("http://localhost:8080/building-member", buildingmember, BuildingMember.class);
        return "redirect:/building-member";
    }

    @PostMapping("/update")
    public String updateServices(Services service) {
        System.out.println(service);
        rest.put("http://localhost:8080/building-member/{MA_NV}", service, service.getMA_DV());
        return "redirect:/building-member";
    }
}