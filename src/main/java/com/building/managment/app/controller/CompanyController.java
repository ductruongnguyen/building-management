package com.building.managment.app.controller;

import com.building.managment.app.model.Company;
import com.building.managment.app.model.CompanyBill;
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
@RequestMapping("/company")
public class CompanyController {

    RestTemplate rest = new RestTemplate();

    @GetMapping
    public String showServices(Model model) {
        List<Company> companyList = Arrays.asList(rest.getForObject("http://localhost:8080/company/all", Company[].class));
        System.out.println(companyList);
        model.addAttribute("companyList", companyList);
        return "listCompany";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("company", new Company());
        return "addCompany";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        Company company = rest.getForObject("http://localhost:8080/company/{MA_CT}", Company.class, id);
        model.addAttribute("company", company);
        return "updateCompany";
    }

    @GetMapping("/delete")
    public String deleteObject(@RequestParam("trackingId") String id) {
        rest.delete("http://localhost:8080/company/{MA_CT}", id);
        return "redirect:/company";
    }

    @GetMapping("/search")
    public String searchObject(@RequestParam("keyword") String keyword, Model model) {
        List<Company> companyList = Arrays.asList(rest.getForObject("http://localhost:8080/company/search?keyword=" + keyword, Company[].class));
        model.addAttribute("companyList", companyList);
        return "searchCompany";
    }

    @GetMapping("/bill")
    public String showBill(@RequestParam("trackingId") String id, Model model) {
        List<CompanyBill> billList = Arrays.asList(rest.getForObject("http://localhost:8080/company/bill/{MA_CT}", CompanyBill[].class, id));
        model.addAttribute("billList", billList);
        model.addAttribute("MA_CT", id);
        double TONG_TIEN = 0;
        for(CompanyBill bill : billList) {
            TONG_TIEN += (bill.getTHUC_TRA()*bill.getSO_NGAY());
        }
        model.addAttribute("TONG_TIEN", TONG_TIEN + billList.get(0).getMAT_BANG());
        model.addAttribute("MAT_BANG", billList.get(0).getMAT_BANG());
        return "listBill";
    }

    @GetMapping("/filter")
    public String filterBill(@RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate, @RequestParam("trackingId")String maCT, Model model) {
        System.out.println(startDate);
        System.out.println(endDate);
        System.out.println(maCT);
        List<CompanyBill> billList = Arrays.asList(rest.getForObject("http://localhost:8080/company/bill/" + startDate + "/" + endDate + "/" + maCT, CompanyBill[].class));
        model.addAttribute("billList", billList);
        model.addAttribute("MA_CT", maCT);
        double TONG_TIEN = 0;
        for(CompanyBill bill : billList) {
            TONG_TIEN += (bill.getTHUC_TRA()*bill.getSO_NGAY());
        }
        model.addAttribute("TONG_TIEN", TONG_TIEN + billList.get(0).getMAT_BANG());
        model.addAttribute("MAT_BANG", billList.get(0).getMAT_BANG());
        return "filterBill";
    }

    @GetMapping("/list-member")
    public String listMem(@RequestParam("trackingId") String maCT, Model model) {
        List<CompanyMember> companyMembers = Arrays.asList(rest.getForObject("http://localhost:8080//company-member/list-by-company?MA_CT=" + maCT, CompanyMember[].class));
        model.addAttribute("companyMembers", companyMembers);
        return "listCompanyMember";
    }

    @PostMapping
    public String addObject(Company company) {
        System.out.println(company);
        rest.postForObject("http://localhost:8080/company", company, Company.class);
        return "redirect:/company";
    }

    @PostMapping("/update")
    public String updateObject(Company company) {
        rest.put("http://localhost:8080/company/{MA_CT}", company, company.getMA_CT());
        return "redirect:/company";
    }
}