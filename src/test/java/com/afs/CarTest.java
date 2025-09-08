package com.afs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void should_return_ticket_when_park_given_car_and_parking_lot_has_space() {
        //given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        Ticket ticket = new Ticket(1, parkingLot);
        //when
        Ticket ticketResult = parkingLot.park(car);
        //then
        assertEquals(ticket, ticketResult);
    }

    @Test
    public void should_return_parked_car_when_fetch_car_given_parking_ticket_and_parked_car() {
        //given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        Ticket ticket = parkingLot.park(car);
        //when
        Car fetchedCar = parkingLot.fetchCar(ticket);
        //then
        assertEquals(car, fetchedCar);
    }

    @Test
    public void should_return_2_parked_car_when_fetch_car_given_2_parking_ticket_and_2_parked_car() {
        //given
        Car car = new Car("Car number 1");
        Car car2 = new Car("Car number 2");
        ParkingLot parkingLot = new ParkingLot();
        Ticket ticket = parkingLot.park(car);
        Ticket ticket2 = parkingLot.park(car2);

        //when
        Car fetchedCar = parkingLot.fetchCar(ticket);
        Car fetchedCar2 = parkingLot.fetchCar(ticket2);

        //then
        assertEquals(car, fetchedCar);
        assertEquals(car2, fetchedCar2);
    }

    @Test
    public void should_return_null_when_fetch_car_given_wrong_parking_ticket() {
        //given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        Ticket parkedTicket = parkingLot.park(car);
        Ticket wrongTicket = new Ticket(parkedTicket.position() + 1, parkingLot);

        //when
        Car fetchedCar = parkingLot.fetchCar(wrongTicket);

        //then
        assertNull(fetchedCar);
    }

    @Test
    public void should_return_null_when_fetch_car_given_used_parking_ticket() {
        //given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        Ticket ticket = parkingLot.park(car);

        //when
        Car fetchedCar = parkingLot.fetchCar(ticket);
        Car refetchedCar = parkingLot.fetchCar(ticket);

        //then
        assertNotNull(fetchedCar);
        assertNull(refetchedCar);
    }

    @Test
    public void should_return_null_when_park_given_parking_log_no_enough_position() {
        //given
        Car car = new Car("Car number 1");
        Car car2 = new Car("Car number 2");
        ParkingLot parkingLot = new ParkingLot(1);

        //when
        Ticket ticket = parkingLot.park(car);
        Ticket ticket2 = parkingLot.park(car2);

        //then
        assertNotNull(ticket);
        assertNull(ticket2);
    }

    // Given Customer uses the Unrecognized ticket When fetch Car Then, no car is fetched and prompt error message "Unrecognized parking ticket."
    @Test
    public void should_return_null_and_prompt_error_message_when_fetch_car_given_unrecognized_ticket() {
        //given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        Ticket parkedTicket = parkingLot.park(car);
        Ticket wrongTicket = new Ticket(parkedTicket.position() + 1, parkingLot);

        //when
        Car fetchedCar = parkingLot.fetchCar(wrongTicket);

        //then
        assertNull(fetchedCar);
        assertTrue(outputStream.toString().contains("Unrecognized parking ticket."));
    }

    // Given Customer uses the used ticket When fetch Car Then, no car is fetched and prompt error message "Unrecognized parking ticket."
    @Test
    public void should_return_null_and_prompt_error_message_when_fetch_car_given_used_ticket() {
        //given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        Ticket ticket = parkingLot.park(car);

        //when
        Car fetchedCar = parkingLot.fetchCar(ticket);
        Car refetchedCar = parkingLot.fetchCar(ticket);

        //then
        assertNotNull(fetchedCar);
        assertNull(refetchedCar);

        assertTrue(outputStream.toString().contains("Unrecognized parking ticket."));
    }

    // Given Customer parks a car & parking lot no enough capacity When park an car Then, no ticket is returned and prompt error message "No available position"
    @Test
    public void should_return_null_and_prompt_error_message_when_park_given_parking_log_no_enough_position() {
        //given
        Car car = new Car("Car number 1");
        Car car2 = new Car("Car number 2");
        ParkingLot parkingLot = new ParkingLot(1);

        //when
        Ticket ticket = parkingLot.park(car);
        Ticket ticket2 = parkingLot.park(car2);

        //then
        assertNotNull(ticket);
        assertNull(ticket2);
        assertTrue(outputStream.toString().contains("No available position."));
    }

}
