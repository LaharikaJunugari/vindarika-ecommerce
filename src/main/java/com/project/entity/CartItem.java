package com.project.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class CartItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    private int quantity;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public CartItem() { super(); }
    public CartItem(Customer customer, Product product, int quantity) {
        super();
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }
}