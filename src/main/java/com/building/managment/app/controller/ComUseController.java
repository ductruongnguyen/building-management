package com.building.managment.app.controller;

import com.building.managment.app.model.CompanyUse;
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
@RequestMapping("/company-use") //Đường dẫn tổng - Can phai doi
public class ComUseController {

    RestTemplate rest = new RestTemplate();

    @GetMapping //List danh sách - Trang list
    public String showList(Model model) {
        List<CompanyUse> companyUseList = Arrays.asList(rest.getForObject("http://localhost:8080/company-use/all", CompanyUse[].class));
        model.addAttribute("companyUseList", companyUseList);
        return "listCompanyUse"; //phai doi
    }

    @GetMapping("/add") //Chuyển tới form add - Trang add
    public String showAddForm(Model model) {
        model.addAttribute("companyUse", new CompanyUse());
        return "addCompanyUse";
    }

    @GetMapping("/update") //Chuyển tới form update - Trang update
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        CompanyUse companyUse = rest.getForObject("http://localhost:8080/company-use/{MA_DV}", CompanyUse.class, id);
        model.addAttribute("company-use", companyUse);
        return "updateCompanyUse";
    }

    @GetMapping("/delete") //Xóa Object
    public String deleteObject(@RequestParam("trackingId") String id) {
        rest.delete("http://localhost:8080/company-use/{MA_DV}", id);
        return "redirect:/company-use"; //ve duong dan tong tuong ung
    }

    @GetMapping("/search") //Tìm kiếm trả về object theo id - Trang search
    public String searchObject(@RequestParam("keyword") String keyword, Model model) {
        List<CompanyUse> companyUseList = Arrays.asList(rest.getForObject("http://localhost:8080/company-use/search?keyword=" + keyword, CompanyUse[].class));
        model.addAttribute("companyUseList", companyUseList);
        return "searchCompanyUse";
    }

    @PostMapping //Insert Object xuống Database khi Add Object mới - Kết quả submit của trang add
    public String saveObject(CompanyUse companyUse) {
        rest.postForObject("http://localhost:8080/company-use", companyUse, CompanyUse.class);
        return "redirect:/company-use";
    }

    @PostMapping("/update") //Update Object xuống Database khi update - Kết quả submit của trang update
    public String updateObject(CompanyUse companyUse) {
        rest.put("http://localhost:8080/company-use/{MA_DV}", companyUse, companyUse.getMA_CT());
        return "redirect:/company-use";
    }
}