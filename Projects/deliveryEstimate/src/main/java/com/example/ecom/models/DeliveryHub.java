package com.example.ecom.models;
import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class DeliveryHub extends BaseModel{
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id")
    private Address address;
    private String name;
}
