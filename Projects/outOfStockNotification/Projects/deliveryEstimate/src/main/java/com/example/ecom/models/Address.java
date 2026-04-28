package com.example.ecom.models;
import jakarta.persistence.*;

import lombok.*;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)

public class Address extends BaseModel{
    private String building;
    private int floor;
    private String roomNo;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private double latitude;
    private double longitude;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
