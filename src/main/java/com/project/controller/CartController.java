package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

import com.project.entity.Customer;
import com.project.entity.Order;
import com.project.entity.Product;
import com.project.service.CartService;
import com.project.service.OrderService;
import com.project.service.ProductService;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    // Helper: get logged-in customer or null
    private Customer getLoggedInCustomer(HttpSession session) {
        return (Customer) session.getAttribute("loggedInCustomer");
    }

    @PostMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable("productId") long productId,
                             @RequestParam(defaultValue = "1") int quantity,
                             HttpSession session) {
        Customer customer = getLoggedInCustomer(session);
        if (customer == null) {
            return "redirect:/customer/login";
        }
        Product product = productService.fetchbyId(productId).orElse(null);
        if (product != null) {
            cartService.addToCart(customer, product, quantity);
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        Customer customer = getLoggedInCustomer(session);
        if (customer == null) {
            return "redirect:/customer/login";
        }
        model.addAttribute("cartItems", cartService.getCartByCustomer(customer));
        model.addAttribute("total", cartService.getCartTotal(customer));
        return "cart";
    }

    @PostMapping("/cart/update/{cartItemId}")
    public String updateQuantity(@PathVariable("cartItemId") long cartItemId,
                                  @RequestParam int quantity) {
        cartService.updateQuantity(cartItemId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/cart/remove/{cartItemId}")
    public String removeFromCart(@PathVariable("cartItemId") long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model, HttpSession session) {
        Customer customer = getLoggedInCustomer(session);
        if (customer == null) {
            return "redirect:/customer/login";
        }
        model.addAttribute("cartItems", cartService.getCartByCustomer(customer));
        model.addAttribute("total", cartService.getCartTotal(customer));
        return "checkout";
    }

    @PostMapping("/checkout/place")
    public String placeOrder(@RequestParam String deliveryAddress, HttpSession session, Model model) {
        Customer customer = getLoggedInCustomer(session);
        if (customer == null) {
            return "redirect:/customer/login";
        }
        Order order = orderService.placeOrder(customer, deliveryAddress);
        if (order == null) {
            model.addAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }
        model.addAttribute("order", order);
        return "orderConfirmation";
    }

    @GetMapping("/orders")
    public String myOrders(Model model, HttpSession session) {
        Customer customer = getLoggedInCustomer(session);
        if (customer == null) {
            return "redirect:/customer/login";
        }
        model.addAttribute("orders", orderService.getOrdersByCustomer(customer));
        return "myOrders";
    }
}
