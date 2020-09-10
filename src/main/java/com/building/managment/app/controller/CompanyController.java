package com.building.managment.app.controller;

import com.building.managment.app.model.Company;
import com.building.managment.app.model.CompanyBill;
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
@RequestMapping("/company")
public class CompanyController {

    RestTemplate rest = new RestTemplate();

    @GetMapping
    public String showServices(Model model) {
        List<Company> companyList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/company/all", Company[].class));
        System.out.println(companyList);
        model.addAttribute("companyList", companyList);
        return "listCompany";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("company", new Company());
        return "/company/addCompany";
    }

    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("trackingId") String id, Model model) {
        Company company = rest.getForObject("http://172.16.0.196:8080/company/{MA_CT}", Company.class, id);
        model.addAttribute("company", company);
        return "/company/updateCompany";
    }

    @GetMapping("/delete")
    public String deleteService(@RequestParam("trackingId") String id) {
        rest.delete("http://172.16.0.196:8080/company/{MA_CT}", id);
        return "redirect:/company";
    }

    @GetMapping("/bill")
    public String showBill(@RequestParam("trackingId") String id, Model model) {
        List<CompanyBill> billList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/company/bill/{MA_CT}", CompanyBill[].class, id));
        System.out.println(billList);
        model.addAttribute("billList", billList);
        model.addAttribute("MA_CT", id);
        return "listBill";
    }

    @GetMapping("/search")
    public String searchService(@RequestParam("keyword") String keyword, Model model) {
        List<Company> companyList = Arrays.asList(rest.getForObject("http://172.16.0.196:8080/company/search?keyword=" + keyword, Company[].class));
        model.addAttribute("companyList", companyList);
        return "/company/listCompany";
    }

    @PostMapping
    public String addServices(Company company) {
        System.out.println(company);
        rest.postForObject("http://172.16.0.196:8080/company", company, Company.class);
        return "redirect:/company";
    }

    @PostMapping("/update")
    public String updateServices(Services service) {
        System.out.println(service);
        rest.put("http://172.16.0.196:8080/company/{MA_CT}", service, service.getMA_DV());
        return "redirect:/company";
    }

    @PostMapping("/filter")
    public String filterBill(@RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate) {
//        rest.put("http://172.16.0.196:8080/company/{MA_CT}", service, service.getMA_DV());

        System.out.println(startDate);
        System.out.println(endDate);
        return "redirect:/company";
    }
}