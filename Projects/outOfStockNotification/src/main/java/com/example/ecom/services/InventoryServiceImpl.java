package com.example.ecom.services;

import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.models.*;
import com.example.ecom.repositories.InventoryRepository;
import com.example.ecom.repositories.NotificationRepository;
import com.example.ecom.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    private ProductRepository productRepository;
    private NotificationRepository notificationRepository;
    private EmailAdapter emailAdapter;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository, ProductRepository productRepository,
                                NotificationRepository notificationRepository, EmailAdapter emailAdapter) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.notificationRepository = notificationRepository;
        this.emailAdapter = emailAdapter;
    }

    @Override
    @Transactional
    public Inventory updateInventory(int productId, int quantity) throws ProductNotFoundException {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Optional<Inventory> inventoryOptional = this.inventoryRepository.findByProduct(product);
        Inventory inventory;
        if (inventoryOptional.isEmpty()) {
            inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(quantity);
        } else {
            inventory = inventoryOptional.get();
            inventory.setQuantity(inventory.getQuantity() + quantity);
        }
        inventory = this.inventoryRepository.save(inventory);

        if (inventory.getQuantity() > 0) {
            List<Notification> notifications = notificationRepository.findByProduct(product);
            for (Notification notification : notifications) {
                if (notification.getStatus() == NotificationStatus.PENDING) {
                    emailAdapter.sendEmail(
                            notification.getUser().getEmail(),
                            "Product back in stock",
                            product.getName() + " is back in stock!"
                    );
                    notification.setStatus(NotificationStatus.SENT);
                    notificationRepository.save(notification);
                }
            }
        }

        return inventory;
    }
}
