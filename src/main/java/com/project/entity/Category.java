package com.project.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="category_id")
    private int id;
    private String name;
    private String imageName;  // NEW: icon/image for category tile

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImageName() {
        return imageName;
    }
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + ", imageName=" + imageName + "]";
    }
    public Category(int id, String name, String imageName) {
        super();
        this.id = id;
        this.name = name;
        this.imageName = imageName;
    }
    public Category() {
        super();
    }
}