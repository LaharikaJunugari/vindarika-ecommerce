package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import com.project.entity.Customer;
import com.project.repository.CustomerRepository;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping("/customer/register")
    public String showRegister() {
        return "customerRegister";
    }

    @PostMapping("/customer/register")
    public String register(@RequestParam String name, @RequestParam String email,
                            @RequestParam String password, @RequestParam String phone,
                            Model model) {
        Customer existing = customerRepo.findByEmail(email);
        if (existing != null) {
            model.addAttribute("error", "Email already registered");
            return "customerRegister";
        }
        Customer c = new Customer(name, email, password, phone);
        customerRepo.save(c);
        return "redirect:/customer/login";
    }

    @GetMapping("/customer/login")
    public String showLogin() {
        return "customerLogin";
    }

    @PostMapping("/customer/login")
    public String login(@RequestParam String email, @RequestParam String password,
                         HttpSession session, Model model) {
        Customer c = customerRepo.findByEmail(email);
        if (c != null && c.getPassword().equals(password)) {
            session.setAttribute("loggedInCustomer", c);
            return "redirect:/shop";
        }
        model.addAttribute("error", "Invalid email or password");
        return "customerLogin";
    }

    @GetMapping("/customer/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/shop";
    }
}
