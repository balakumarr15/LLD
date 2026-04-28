package com.scaler.parking_lot.services;

import com.scaler.parking_lot.exceptions.InvalidGateException;
import com.scaler.parking_lot.exceptions.InvalidParkingLotException;
import com.scaler.parking_lot.exceptions.ParkingSpotNotAvailableException;
import com.scaler.parking_lot.models.*;
import com.scaler.parking_lot.respositories.*;
import com.scaler.parking_lot.strategies.assignment.SpotAssignmentStrategy;

import java.util.*;

public class TicketServiceImpl implements  TicketService {
    private TicketRepository ticketRepository;
    private ParkingLotRepository parkingLotRepository;
    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private SpotAssignmentStrategy spotAssignmentStrategy;
    public TicketServiceImpl(TicketRepository ticketRepository, ParkingLotRepository parkingLotRepository, GateRepository gateRepository, VehicleRepository vehicleRepository,  SpotAssignmentStrategy spotAssignmentStrategy) {
        this.ticketRepository = ticketRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.spotAssignmentStrategy = spotAssignmentStrategy;
    }

    @Override
    public Ticket generateTicket(int gateId, String registrationNumber, String vehicleType) throws InvalidGateException, InvalidParkingLotException, ParkingSpotNotAvailableException {
        Optional<Gate> gate = gateRepository.findById(gateId);
        if(gate.isEmpty() || gate.get().getType().equals(GateType.EXIT)) {
            throw new InvalidGateException("Gate not found");
        }

        Optional<ParkingLot> parkingLot = parkingLotRepository.getParkingLotByGateId(gateId);
        if(parkingLot.isEmpty()) {
            throw new InvalidParkingLotException("Parking Lot not available");
        }

        Optional<ParkingSpot> parkingSpot = spotAssignmentStrategy.assignSpot(parkingLot.get(), VehicleType.valueOf(vehicleType));
        if(parkingSpot.isEmpty()) {
            throw new ParkingSpotNotAvailableException("Parking Spot not available");
        }

        Ticket ticket = new Ticket();
        ticket.setGate(gate.get());
        ticket.setEntryTime(new Date());
        ticket.setParkingAttendant(gate.get().getParkingAttendant());
        ticket.setParkingSpot(parkingSpot.get());
        Optional<Vehicle> vehicle = vehicleRepository.getVehicleByRegistrationNumber(registrationNumber);
        if(vehicle.isEmpty()) {
            Vehicle newVehicle = new Vehicle();
            newVehicle.setRegistrationNumber(registrationNumber);
            newVehicle.setVehicleType(VehicleType.valueOf(vehicleType));
            vehicleRepository.save(newVehicle);
            ticket.setVehicle(newVehicle);
        } else {
            ticket.setVehicle(vehicle.get());
        }

        return ticketRepository.save(ticket);
    }
}
