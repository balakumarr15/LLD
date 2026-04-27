package com.example.ecom.services;

import java.util.*;

import com.example.ecom.libraries.GoogleMapsAdapter;
import com.example.ecom.libraries.GoogleMapsApi;
import com.example.ecom.libraries.MapsApi;
import com.example.ecom.libraries.models.GLocation;
import org.springframework.stereotype.Service;

import com.example.ecom.exceptions.AddressNotFoundException;
import com.example.ecom.exceptions.ProductNotFoundException;
import com.example.ecom.models.*;
import com.example.ecom.repositories.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final AddressRepository addressRepository;
    private final DeliveryHubRepository deliveryHubRepository;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final GoogleMapsAdapter googleMapsAdapter;

    public ProductServiceImpl(AddressRepository addressRepository, DeliveryHubRepository deliveryHubRepository,
            ProductRepository productRepository, SellerRepository sellerRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.deliveryHubRepository = deliveryHubRepository;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.userRepository = userRepository;
        this.googleMapsAdapter = new GoogleMapsAdapter();
    }


    @Override
    public Date estimateDeliveryDate(int productId, int addressId)
            throws ProductNotFoundException, AddressNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById((long)productId);

        if(optionalProduct.isEmpty()) {
            throw new  ProductNotFoundException("product not found");
        }

        Optional<Address> optionalAddress = addressRepository.findById((long)addressId);

        if(optionalAddress.isEmpty()) {
            throw new  AddressNotFoundException("adress not found");
        }

        Product product = optionalProduct.get();
        Address userAddress = optionalAddress.get();

        Seller seller = product.getSeller();

        Address sellerAddress = seller.getAddress();

        String zipCode = sellerAddress.getZipCode();

        Optional<DeliveryHub> deliveryHub = deliveryHubRepository.findByAddress_ZipCode(sellerAddress.getZipCode());

        // Step 1: Calculate time from seller warehouse to delivery hub
        GLocation sellerLocation = new GLocation(sellerAddress.getLatitude(), sellerAddress.getLongitude());
        GLocation deliveryHubLocation = new GLocation(deliveryHub.get().getAddress().getLatitude(), deliveryHub.get().getAddress().getLongitude());
        int timeFromSellerToHub = googleMapsAdapter.estimate(sellerLocation, deliveryHubLocation);

        // Step 2: Calculate time from delivery hub to user's address
        GLocation userLocation = new GLocation(userAddress.getLatitude(), userAddress.getLongitude());
        int timeFromHubToUser = googleMapsAdapter.estimate(deliveryHubLocation, userLocation);

        // Step 3: Total time = time to hub + time from hub to user
        int totalTimeInSeconds = timeFromSellerToHub + timeFromHubToUser;

        // Step 4: Convert seconds to milliseconds and add to current time
        long totalTimeInMillis = (long) totalTimeInSeconds * 1000;
        Date estimatedDeliveryDate = new Date(System.currentTimeMillis() + totalTimeInMillis);

        return estimatedDeliveryDate;
    }

}
