package com.building.managment.app.controller;

import com.building.managment.app.model.BuildingMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/building-member")
public class BuildingMemberController {

    private RestTemplate rest = new RestTemplate();

    @GetMapping
    public String showAlBuildingMember(Model model) {
        List<BuildingMember> members = Arrays.asList(rest.getForObject("http://localhost:8080/building-member/all", BuildingMember.class));

        model.addAttribute("members", members);
        return "listBuildingMember";
    }
}
