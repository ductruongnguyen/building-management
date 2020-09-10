package com.building.managment.app.controller;

import com.building.managment.app.model.CompanyMember;
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
<<<<<<< HEAD
@RequestMapping("/company-member") //Đường dẫn tổng - Can phai doi
=======
@RequestMapping("/company-member")
>>>>>>> fc5f7fdc05ed8fce84b1493bfd320fdc0fed8743
public class CompanyMemberController {

    RestTemplate rest = new RestTemplate();

    @GetMapping //List danh sách - Trang list
    public String showList(Model model) {
        List<CompanyMember> companyMemberList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/company-member/all", CompanyMember[].class));
        System.out.println(companyMemberList);
        model.addAttribute("companyMemberList", companyMemberList);
        return "listCompanyMember"; //phai doi
    }

    @GetMapping("/add") //Chuyển tới form add - Trang add
    public String showAddForm(Model model) {
        model.addAttribute("CompanyMember", new CompanyMember());
        return "addCompanyMember";
    }

    @GetMapping("/update") //Chuyển tới form update - Trang update
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        CompanyMember CompanyMember = rest.getForObject("http://172.16.0.196:8080/company-member/{MA_DV}", CompanyMember.class, id);
        model.addAttribute("company-member", CompanyMember);
        return "updateCompanyMember";
    }

    @GetMapping("/delete") //Xóa Object
    public String deleteObject(@RequestParam("trackingId") String id) {
        rest.delete("http://172.16.0.196:8080/company-member/{MA_DV}", id);
        return "redirect:/company-member"; //ve duong dan tong tuong ung
    }

    @GetMapping("/search") //Tìm kiếm trả về object theo id - Trang search
    public String searchObject(@RequestParam("keyword") String keyword, Model model) {
        List<CompanyMember> companyMemberList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/company-member/search?keyword=" + keyword, CompanyMember[].class));
        model.addAttribute("company-members", companyMemberList);
        return "searchCompanyMember";
    }

    @PostMapping //Insert Object xuống Database khi Add Object mới - Kết quả submit của trang add
    public String savesaveObject(CompanyMember CompanyMember) {
        System.out.println(CompanyMember);
        rest.postForObject("http://172.16.0.196:8080/company-member", CompanyMember, CompanyMember.class);
        return "redirect:/company-member";
    }

    @PostMapping("/update") //Update Object xuống Database khi update - Kết quả submit của trang update
    public String updateObject(CompanyMember CompanyMember) {
        System.out.println(CompanyMember);
        rest.put("http://172.16.0.196:8080/company-member/{MA_DV}", CompanyMember, CompanyMember.getMA_NV());
        return "redirect:/company-member";
    }
}