package com.example.ecom.services;

import com.example.ecom.exceptions.*;
import com.example.ecom.models.Notification;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    private InventoryRepository inventoryRepository;
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository, ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Notification registerUser(int userId, int productId) throws UserNotFoundException, ProductNotFoundException, ProductInStockException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        Inventory inventory = inventoryRepository.findByProduct(product).orElseThrow(() -> new ProductNotFoundException("Inventory not found"));
        if(inventory.getQuantity() > 0) {
            throw new ProductInStockException("Product is already in stock");
        }

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setProduct(product);
        notification.setStatus(NotificationStatus.PENDING);

        return notificationRepository.save(notification);
    }

    @Override
    public void deregisterUser(int userId, int notificationId) throws UserNotFoundException, NotificationNotFoundException, UnAuthorizedException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
        if(notification.getUser().getId() != user.getId()) {
            throw new UnAuthorizedException("User is not authorized");
        }

        notificationRepository.delete(notification);
    }
}
