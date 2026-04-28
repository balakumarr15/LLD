package com.example.ecom.models;
import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Seller extends BaseModel{
    private String name;
    private String email;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address address;
}
