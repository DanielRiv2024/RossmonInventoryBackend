package com.example.models;
import javax.persistence.*;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCT")
    private Long idProduct;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "ID_CATEGORY")
    private Long idCategory;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATE_BY")
    private Long updateBy;

    public Product() {
    }

    public Product(Long idProduct, String name, int price, int amount, Long idCategory, Long createdBy, Long updateBy) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.idCategory = idCategory;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
    
    
    
     @Override
    public String toString() {
        return "Product{" +
               "idProduct=" + idProduct +
               ", name='" + name + '\'' +
               ", price=" + price +
               ", amount=" + amount +
               ", idCategory=" + idCategory +
               ", createdBy=" + createdBy +
               ", updateBy=" + updateBy +
               '}';
    }

    
}
