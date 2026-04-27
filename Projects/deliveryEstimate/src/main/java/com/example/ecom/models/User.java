package com.example.ecom.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;


import java.util.List;

@Data
@Entity
@Table(name = "app_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseModel{
    private String name;
    private String email;
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
}
