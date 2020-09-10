package com.building.managment.app.controller;

import com.building.managment.app.model.Working;
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
@RequestMapping("/working") //Đường dẫn tổng - Can phai doi
public class WorkingController {

    RestTemplate rest = new RestTemplate();

    @GetMapping //List danh sách - Trang list
    public String showList(Model model) {
        List<Working> workingsList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/working/all", Working[].class));
        System.out.println(workingsList);
        model.addAttribute("workingsList", workingsList);
        return "listworkings"; //phai doi
    }

    @GetMapping("/add") //Chuyển tới form add - Trang add
    public String showAddForm(Model model) {
        model.addAttribute("working", new Working());
        return "addworkings";
    }

    @GetMapping("/update") //Chuyển tới form update - Trang update
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        Working working = rest.getForObject("http://172.16.0.196:8080/working/{MA_DV}", Working.class, id);
        model.addAttribute("working", working);
        return "updateworkings";
    }

    @GetMapping("/delete") //Xóa Object
    public String deleteObject(@RequestParam("trackingId") String id) {
        rest.delete("http://172.16.0.196:8080/working/{MA_DV}", id);
        return "redirect:/working"; //ve duong dan tong tuong ung
    }

    @GetMapping("/search") //Tìm kiếm trả về object theo id - Trang search
    public String searchObject(@RequestParam("keyword") String keyword, Model model) {
        List<Working> workingsList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/working/search?keyword=" + keyword, Working[].class));
        model.addAttribute("workings", workingsList);
        return "searchworkings";
    }

    @PostMapping //Insert Object xuống Database khi Add Object mới - Kết quả submit của trang add
    public String savesaveObject(Working working) {
        System.out.println(working);
        rest.postForObject("http://172.16.0.196:8080/working", working, Working.class);
        return "redirect:/working";
    }

    @PostMapping("/update") //Update Object xuống Database khi update - Kết quả submit của trang update
    public String updateObject(Working working) {
        System.out.println(working);
        rest.put("http://172.16.0.196:8080/working/{MA_DV}", working, working.getMA_DV());
        return "redirect:/working";
    }
}