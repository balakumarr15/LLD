package com.scaler.parking_lot.strategies.assignment;

import com.scaler.parking_lot.models.*;

import java.util.Optional;

public class NearestSpotAssignmentStrategyImpl implements SpotAssignmentStrategy {

    @Override
    public Optional<ParkingSpot> assignSpot(ParkingLot parkingLot, VehicleType vehicleType) {
        ParkingSpot chosenParkingSpot = null;
        int bestFloorSpotNumber = Integer.MAX_VALUE;
        for (ParkingFloor floor : parkingLot.getParkingFloors()) {
            if (floor.getStatus() != FloorStatus.OPERATIONAL) {
                continue;
            }
            int noOfSpotsOnThisFloor = 0;
            ParkingSpot thisParkingSpot = null;
            for (ParkingSpot spot : floor.getSpots()) {
                if (spot.getStatus().equals(ParkingSpotStatus.AVAILABLE) && spot.getSupportedVehicleType().equals(vehicleType)) {
                    if (thisParkingSpot == null) {
                        thisParkingSpot = spot;
                    }
                    noOfSpotsOnThisFloor++;
                }
            }
            if (noOfSpotsOnThisFloor != 0 && noOfSpotsOnThisFloor < bestFloorSpotNumber) {
                bestFloorSpotNumber = noOfSpotsOnThisFloor;
                chosenParkingSpot = thisParkingSpot;
            }
        }
        if(chosenParkingSpot != null){
            chosenParkingSpot.setStatus(ParkingSpotStatus.OCCUPIED);
        }
        return Optional.ofNullable(chosenParkingSpot);
    }
}
