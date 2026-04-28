package com.example.ecom.models;
import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseModel{
    private String name;
    private String description;
    private double price;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "seller_id")
    private Seller seller;
}
