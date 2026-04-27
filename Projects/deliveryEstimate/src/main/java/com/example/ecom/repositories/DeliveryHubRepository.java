package com.example.ecom.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecom.models.*;

@Repository
public interface DeliveryHubRepository extends JpaRepository<DeliveryHub, Long>{
    Optional<DeliveryHub> findByAddress_ZipCode(String address_zipCode);
}
