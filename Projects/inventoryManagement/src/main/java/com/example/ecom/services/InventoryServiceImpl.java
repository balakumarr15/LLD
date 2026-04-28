package com.example.ecom.services;


import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.exceptions.UnAuthorizedAccessException;
import com.example.ecom.exceptions.UserNotFoundException;
import com.example.ecom.models.Inventory;
import com.example.ecom.models.Product;
import com.example.ecom.models.User;
import com.example.ecom.models.UserType;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.ProductRepository;
import com.example.ecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public InventoryServiceImpl(InventoryRepository inventoryRepository,UserRepository userRepository,ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    @Override
    public Inventory createOrUpdateInventory(int userId, int productId, int quantity) throws ProductNotFoundException, UserNotFoundException, UnAuthorizedAccessException {
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        if(user.get().getUserType() != UserType.ADMIN) {
            throw new UnAuthorizedAccessException("Only admins can access this product");
        }

        Optional<Product> product = productRepository.findById(Long.valueOf(productId));
        if(product.isEmpty()) {
            throw new ProductNotFoundException("Product not found");
        }

        Optional<Inventory> optionalInventory = inventoryRepository.findByProductId(Long.valueOf(productId));
        Inventory inventory = optionalInventory.orElseGet(Inventory::new);

        inventory.setProduct(product.get());
        inventory.setQuantity(inventory.getQuantity() + quantity);

        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(int userId, int productId) throws UserNotFoundException, UnAuthorizedAccessException {
        Optional<User> user = userRepository.findById((long) userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        if(user.get().getUserType() != UserType.ADMIN) {
            throw new UnAuthorizedAccessException("Only admins can access this product");
        }

        inventoryRepository.deleteById((long) productId);
    }
}
