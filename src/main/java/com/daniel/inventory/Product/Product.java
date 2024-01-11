package com.daniel.inventory.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCT")
    private int id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "ID_CATEGORY")
    private int category;

    @Column(name = "CREATED_BY")
    private int createdBy;

    @Column(name = "UPDATE_BY")
    private int updateBy;

    public Product() {
    }

    public Product(int id, String name, int price, int amount, int category, int createdBy, int updateBy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.category = category;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
    }
}
