package com.project.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.entity.CartItem;
import com.project.entity.Customer;
import com.project.entity.Order;
import com.project.entity.OrderItem;
import com.project.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CartService cartService;

    public Order placeOrder(Customer customer, String deliveryAddress) {
        List<CartItem> cartItems = cartService.getCartByCustomer(customer);
        if (cartItems.isEmpty()) {
            return null;
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setDeliveryAddress(deliveryAddress);
        order.setStatus("PLACED");
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;
        for (CartItem ci : cartItems) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(ci.getProduct());
            oi.setQuantity(ci.getQuantity());
            double price = ci.getProduct().getDiscountPrice() > 0
                    ? ci.getProduct().getDiscountPrice()
                    : ci.getProduct().getPrice();
            oi.setPriceAtOrder(price);
            total += price * ci.getQuantity();
            orderItems.add(oi);
        }
        order.setItems(orderItems);
        order.setTotalAmount(total);

        Order saved = orderRepo.save(order);
        cartService.clearCart(customer);
        return saved;
    }

    public List<Order> getOrdersByCustomer(Customer customer) {
        return orderRepo.findByCustomerOrderByOrderDateDesc(customer);
    }
}
