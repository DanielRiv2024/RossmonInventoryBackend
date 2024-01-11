package com.daniel.inventory.Category;

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
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORY")
    private int id;

    @Column(name = "NAME_CATEGORY")
    private String name;

    @Column(name = "DESCRIPTION_CATEGORY")
    private String description;

    @Column(name = "CREATED_BY")
    private int createdBy;

    @Column(name = "UPDATE_BY")
    private int updateBy;

    public Category() {
    }

    public Category(int id, String name, String description, int createdBy, int updateBy) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
    }
}
