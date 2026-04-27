package com.example.ecom.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecom.models.*;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
