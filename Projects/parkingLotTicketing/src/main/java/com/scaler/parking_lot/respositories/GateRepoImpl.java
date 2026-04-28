package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Gate;

import java.util.*;

public class GateRepoImpl implements GateRepository{
    private Map<Long, Gate> gateMap;
    private long id;
    public GateRepoImpl() {
        gateMap = new HashMap<>();
        id = 1;
    }
    @Override
    public Optional<Gate> findById(long gateId) {
        return Optional.ofNullable(gateMap.get(gateId));
    }

    @Override
    public Gate save(Gate gate) {
        if(gate.getId() == 0) {
            gate.setId(id++);
        }
        gateMap.put(gate.getId(), gate);
        return gate;
    }
}
