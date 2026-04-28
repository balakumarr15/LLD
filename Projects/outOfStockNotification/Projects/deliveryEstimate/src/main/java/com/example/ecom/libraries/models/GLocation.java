package com.example.ecom.libraries.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GLocation {
    private double latitude;
    private double longitude;
}
