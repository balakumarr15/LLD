package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Vehicle;

import java.util.*;

public class VehicleRepoImpl implements VehicleRepository {
    private Map<String, Vehicle> vehicleMap;
    private long id;

    public VehicleRepoImpl() {
        vehicleMap = new HashMap<>();
        id = 1;
    }
    @Override
    public Optional<Vehicle> getVehicleByRegistrationNumber(String registrationNumber) {
        return Optional.ofNullable(vehicleMap.get(registrationNumber));
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        if(vehicle.getId() == 0) {
            vehicle.setId(id++);
        }
        vehicleMap.put(vehicle.getRegistrationNumber(), vehicle);
        return vehicle;
    }
}
