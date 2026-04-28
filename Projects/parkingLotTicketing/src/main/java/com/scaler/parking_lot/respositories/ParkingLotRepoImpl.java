package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.*;

import java.util.*;

public class ParkingLotRepoImpl implements ParkingLotRepository{
    private Map<Long, ParkingLot> gateIdToParkingLotMap ;
    private Map<Long, ParkingLot> parkingLotIdToParkingLotMap;
    private long id;
    public ParkingLotRepoImpl() {
        gateIdToParkingLotMap = new HashMap<>();
        parkingLotIdToParkingLotMap = new HashMap<>();
        id = 1;
    }

    @Override
    public Optional<ParkingLot> getParkingLotByGateId(long gateId) {
        return Optional.ofNullable(gateIdToParkingLotMap.get(gateId));
    }

    @Override
    public Optional<ParkingLot> getParkingLotById(long id) {
        return Optional.ofNullable(parkingLotIdToParkingLotMap.get(id));
    }

    @Override
    public ParkingLot save(ParkingLot parkingLot) {
        if(parkingLot.getId()==0) {
            parkingLot.setId(id++);
        }
        for(Gate gate : parkingLot.getGates()) {
            gateIdToParkingLotMap.put(gate.getId(), parkingLot);
        }

        parkingLotIdToParkingLotMap.put(parkingLot.getId(), parkingLot);

        return parkingLot;
    }
}
