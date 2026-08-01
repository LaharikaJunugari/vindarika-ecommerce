package com.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.entity.Order;
import com.project.repository.OrderRepository;

@Controller
public class OrderAdminController {

    @Autowired
    private OrderRepository orderRepo;

    @GetMapping("/admin/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderRepo.findAll();
        model.addAttribute("orders", orders);
        return "adminOrders";
    }

    @PostMapping("/admin/orders/updateStatus/{id}")
    public String updateStatus(@PathVariable("id") long id, @RequestParam String status) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepo.save(order);
        }
        return "redirect:/admin/orders";
    }
}
