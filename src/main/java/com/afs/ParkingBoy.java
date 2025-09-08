package com.afs;

public class ParkingBoy {
    private ParkingLot parkingLot;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public Ticket performPark(Car car) {
        return parkingLot.park(car);
    }

    public Car performFetchCar(Ticket ticket) {
        return parkingLot.fetchCar(ticket);
    }

}
