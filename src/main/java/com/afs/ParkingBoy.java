package com.afs;

import java.util.ArrayList;
import java.util.List;

public class ParkingBoy {
    private List<ParkingLot> parkingLots = new ArrayList<>();

    public ParkingBoy(ParkingLot parkingLot) {
        this.addParkingLot(parkingLot);
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public void addParkingLot(ParkingLot parkingLot) {
        if (parkingLot == null || this.parkingLots.contains(parkingLot)) {
            return;
        }
        this.parkingLots.add(parkingLot);
    }

    public Ticket performPark(Car car) {
        for (ParkingLot parkingLot : parkingLots) {
            Ticket ticket = parkingLot.park(car);
            if (ticket != null) {
                return ticket;
            }
        }
        return null;
    }

    public Car performFetchCar(Ticket ticket) {
        for (ParkingLot parkingLot : parkingLots) {
            Car car = parkingLot.fetchCar(ticket);
            if (car != null) {
                return car;
            }
        }
        return null;
    }

}
