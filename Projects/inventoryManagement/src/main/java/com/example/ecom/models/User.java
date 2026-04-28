package com.example.ecom.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "app_user")
public class User extends BaseModel {
    private String name;
    private String email;
    private UserType userType;
}
