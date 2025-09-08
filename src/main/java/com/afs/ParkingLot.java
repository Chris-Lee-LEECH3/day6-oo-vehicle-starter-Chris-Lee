package com.afs;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class ParkingLot {
    private Integer capacity;
    private static final Integer DEFAULT_CAPACITY = 10;
    private Map<Ticket, Car> ticketCars = new HashMap<Ticket, Car>();

    public ParkingLot() {
        this.capacity = DEFAULT_CAPACITY;
    }

    public ParkingLot(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Map<Ticket, Car> getTicketCars() {
        return ticketCars;
    }

    public void setTicketCars(Map<Ticket, Car> ticketCars) {
        this.ticketCars = ticketCars;
    }

    public Car fetchCar(Ticket ticket) {
        if (!ticketCars.containsKey(ticket)) {
            System.out.println("Unrecognized parking ticket.");
        }

        return ticketCars.remove(ticket);
    }

    public Ticket park(Car car) {
        Ticket newTicket = IntStream.rangeClosed(1, capacity).boxed()
            .filter(position -> ticketCars.keySet().stream().noneMatch(ticket -> ticket.position().equals(position)))
            .findFirst()
            .map(position -> {
                Ticket ticket = new Ticket(position, this);
                    ticketCars.put(ticket, car);
                    return ticket;
                }).orElse(null);

        if (newTicket == null) {
            System.out.println("No available position.");
        }

        return newTicket;
    }

}
