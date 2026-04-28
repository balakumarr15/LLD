package com.scaler.parking_lot.respositories;

import com.scaler.parking_lot.models.Ticket;

import java.util.*;

public class TicketRepoImpl implements TicketRepository{
    private Map<Long, Ticket> ticketMap;
    private long id;

    TicketRepoImpl() {
        ticketMap = new HashMap<>();
        id = 1;
    }

    @Override
    public Ticket save(Ticket ticket) {
        if(ticket.getId()==0) {
            ticket.setId(id++);
        }
        ticketMap.put(ticket.getId(), ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> getTicketById(long ticketId) {
        return Optional.ofNullable(ticketMap.get(ticketId));
    }
}

