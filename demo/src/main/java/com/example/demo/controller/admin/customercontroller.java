package com.example.demo.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.*;
import com.example.demo.repository.adminrepository;
import com.example.demo.repository.blogrepository;
import com.example.demo.repository.customerrepository;
import com.example.demo.repository.orderrepository;
import com.example.demo.repository.productrepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class customercontroller {

    @Autowired
    customerrepository customerrepo;

    @Autowired
    productrepository productrepo;

    @Autowired
    orderrepository orderrepo;

    @Autowired
    adminrepository adminrepo;

    @Autowired
    blogrepository blogrepo;

    @GetMapping("index")
    public String dashboard(HttpSession session) {
        Integer adminId = (Integer) session.getAttribute("loginAdmin");
        if (adminId == null) {
            return "redirect:/admin/login";
        }
        return ("admin/index");
    }

    @GetMapping("apps-ecommerce-customers")
    public String customers(Model model) {

        List<customers> customers = (List<customers>) customerrepo.findAll();
        model.addAttribute("customers", customers);
        return ("admin/apps-ecommerce-customers");
    }

    @GetMapping("apps-ecommerce-order-details")
    public String orderdetail() {
        return ("admin/apps-ecommerce-order-details");
    }

    @GetMapping("apps-ecommerce-orders")
    public String orders() {
        return ("admin/apps-ecommerce-orders");
    }

    @GetMapping("apps-invoices-details")
    public String invoicedetail() {
        return ("admin/apps-invoices-details");
    }

    @GetMapping("pages-profile-settings")
    public String setting() {
        return ("admin/pages-profile-settings");
    }

}