package com.project.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entity.CartItem;
import com.project.entity.Customer;
import com.project.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    List<CartItem> findByCustomer(Customer customer);
    CartItem findByCustomerAndProduct(Customer customer, Product product);
    void deleteByCustomer(Customer customer);
}
