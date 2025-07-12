package com.apirest.model;


import com.apirest.enume.Category;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Document(collection = "Products")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(columnDefinition = "name")
    private String productName;

    @Column(columnDefinition = "description")
    private String productDescription;

    @Column(columnDefinition = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    public Product(){

    }

    public Product(String id, String productName, String productDescription, Category category) {
        super();
        this.id = id;
        this.productName = productName;
        this.productDescription = productDescription;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
