package com.building.managment.app.controller;

import com.building.managment.app.model.BuildingMember;
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
        List<BuildingMember> buildingmemberList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/building-member/all", BuildingMember[].class));
        System.out.println(buildingmemberList);
        model.addAttribute("buildingmemberList", buildingmemberList);
        return "listBuildingMember";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("buildingmember", new BuildingMember());
        return "addBuildingMember";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        BuildingMember buildingmember = rest.getForObject("http://172.16.0.196:8080/building-member/{MA_NV}", BuildingMember.class, id);
        model.addAttribute("buildingmember", buildingmember);
        return "updateBuildingMember";
    }

    @GetMapping("/delete")
    public String deleteService(@RequestParam("trackingId") String id) {
        rest.delete("http://172.16.0.196:8080/building-member/{MA_NV}", id);
        return "redirect:/building-member";
    }

    @GetMapping("/search")
    public String searchService(@RequestParam("keyword") String keyword, Model model) {
        List<BuildingMember> buildingmemberList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/building-member/search?keyword=" + keyword, BuildingMember[].class));
        model.addAttribute("buildingmemberList", buildingmemberList);
        return "searchBuildingMember";
    }

    @PostMapping
    public String addServices(BuildingMember buildingmember) {
        rest.postForObject("http://172.16.0.196:8080/building-member", buildingmember, BuildingMember.class);
        return "redirect:/building-member";
    }

    @PostMapping("/update")
    public String updateServices(BuildingMember buildingmember) {
        System.out.println(buildingmember);
        rest.put("http://172.16.0.196:8080/building-member/{MA_NV}", buildingmember, buildingmember.getMA_NV());
        return "redirect:/building-member";
    }
}