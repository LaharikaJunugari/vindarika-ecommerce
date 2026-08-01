package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;

import com.project.entity.Customer;
import com.project.service.CartService;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CartService cartService;

    @ModelAttribute("cartCount")
    public int cartCount(HttpSession session) {
        Customer customer = (Customer) session.getAttribute("loggedInCustomer");
        if (customer == null) {
            return 0;
        }
        return cartService.getCartByCustomer(customer).stream()
                .mapToInt(item -> item.getQuantity())
                .sum();
    }
}
