package com.project.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.entity.CartItem;
import com.project.entity.Customer;
import com.project.entity.Product;
import com.project.repository.CartItemRepository;

@Service
public class CartService {
    @Autowired
    private CartItemRepository cartRepo;

    public List<CartItem> getCartByCustomer(Customer customer) {
        return cartRepo.findByCustomer(customer);
    }

    @Transactional
    public void addToCart(Customer customer, Product product, int quantity) {
        CartItem existing = cartRepo.findByCustomerAndProduct(customer, product);
        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + quantity);
            cartRepo.save(existing);
        } else {
            CartItem item = new CartItem(customer, product, quantity);
            cartRepo.save(item);
        }
    }

    @Transactional
    public void updateQuantity(long cartItemId, int quantity) {
        CartItem item = cartRepo.findById(cartItemId).orElse(null);
        if (item != null) {
            if (quantity <= 0) {
                cartRepo.deleteById(cartItemId);
            } else {
                item.setQuantity(quantity);
                cartRepo.save(item);
            }
        }
    }

    @Transactional
    public void removeFromCart(long cartItemId) {
        cartRepo.deleteById(cartItemId);
    }

    @Transactional
    public void clearCart(Customer customer) {
        cartRepo.deleteByCustomer(customer);
    }

    public double getCartTotal(Customer customer) {
        List<CartItem> items = cartRepo.findByCustomer(customer);
        double total = 0;
        for (CartItem item : items) {
            double price = item.getProduct().getDiscountPrice() > 0
                    ? item.getProduct().getDiscountPrice()
                    : item.getProduct().getPrice();
            total += price * item.getQuantity();
        }
        return total;
    }
}