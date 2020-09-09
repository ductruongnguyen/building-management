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
@RequestMapping("/service") //Đường dẫn tổng
public class ServiceController {

    RestTemplate rest = new RestTemplate();

    @GetMapping //List danh sách - Trang list
    public String showList(Model model) {
        List<Services> servicesList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/service/all", Services[].class));
        System.out.println(servicesList);
        model.addAttribute("services", servicesList);
        return "listServices";
    }

    @GetMapping("/add") //Chuyển tới form add - Trang add
    public String showAddForm(Model model) {
        model.addAttribute("service", new Services());
        return "addServices";
    }

    @GetMapping("/update") //Chuyển tới form update - Trang update
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        Services service = rest.getForObject("http://172.16.0.196:8080/service/{MA_DV}", Services.class, id);
        model.addAttribute("service", service);
        return "updateServices";
    }

    @GetMapping("/delete") //Xóa Object
    public String deleteObject(@RequestParam("trackingId") String id) {
        rest.delete("http://172.16.0.196:8080/service/{MA_DV}", id);
        return "redirect:/service";
    }

    @GetMapping("/search") //Tìm kiếm trả về object theo id - Trang search
    public String searchObject(@RequestParam("keyword") String keyword, Model model) {
        List<Services> servicesList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/service/search?keyword=" + keyword, Services[].class));
        model.addAttribute("services", servicesList);
        return "searchServices";
    }

    @PostMapping //Insert Object xuống Database khi Add Object mới - Kết quả submit của trang add
    public String savesaveObject(Services service) {
        System.out.println(service);
        rest.postForObject("http://172.16.0.196:8080/service", service, Services.class);
        return "redirect:/service";
    }

    @PostMapping("/update") //Update Object xuống Database khi update - Kết quả submit của trang update
    public String updateObject(Services service) {
        System.out.println(service);
        rest.put("http://172.16.0.196:8080/service/{MA_DV}", service, service.getMA_DV());
        return "redirect:/service";
    }
}