package com.project.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.entity.Order;
import com.project.entity.Customer;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByCustomerOrderByOrderDateDesc(Customer customer);
}
