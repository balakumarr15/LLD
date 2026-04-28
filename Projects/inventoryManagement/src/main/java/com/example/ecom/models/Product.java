package com.example.ecom.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Product extends BaseModel {
    private String name;
    private String description;
    private double price;
}
