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

    // Given a parking lot, a standard parking boy, and a car, When park the car, Then return a parking ticket
    @Test
    public void should_return_ticket_when_park_given_a_parking_lot_and_a_standard_parking_boy_and_a_car() {
        // given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        // when
        Ticket ticket = parkingBoy.performPark(car);

        // then
        assertNotNull(ticket);
    }

    // Given a parking lot with a parked car, a standard parking boy, and a parking ticket, When fetch the car, Then return the parked car.
    @Test
    public void should_return_car_when_fetch_car_given_a_parking_lot_with_a_packed_car_and_a_standard_parking_boy_and_parking_ticket() {
        // given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Ticket ticket = parkingBoy.performPark(car);

        // when
        Car parkedCar = parkingBoy.performFetchCar(ticket);

        // then
        assertEquals(car, parkedCar);
    }

    // Given a parking lot with two parked cars, a standard parking boy, and two parking tickets, When fetch the car twice, Then return the right car with each ticket
    @Test
    public void should_return_2_cars_when_fetch_car_given_a_parking_lot_with_2_packed_cars_and_a_standard_parking_boy_and_2_packing_tickets() {
        // given
        Car car = new Car("Car number 1");
        Car car2 = new Car("Car number 2");
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Ticket ticket = parkingBoy.performPark(car);
        Ticket ticket2 = parkingBoy.performPark(car2);

        // when
        Car parkedCar = parkingBoy.performFetchCar(ticket);
        Car parkedCar2 = parkingBoy.performFetchCar(ticket2);

        // then
        assertEquals(car, parkedCar);
        assertEquals(car2, parkedCar2);
    }

    // Given a parking lot, a standard parking boy, and a wrong parking ticket, When fetch the car, Then return nothing with error message "Unrecognized parking ticket.”
    @Test
    public void should_return_null_and_prompt_error_message_when_fetch_car_given_a_parking_lot_and_a_standard_parking_boy_and_a_wrong_parking_ticket() {
        // given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Ticket ticket = parkingBoy.performPark(car);
        Ticket wrongTicket = new Ticket(ticket.position() + 1, parkingLot);

        // when
        Car parkedCar = parkingBoy.performFetchCar(wrongTicket);

        // then
        assertNull(parkedCar);
        assertTrue(outputStream.toString().contains("Unrecognized parking ticket."));
    }

    // Given a parking lot, a standard parking boy, and a used parking ticket, When fetch the car, Then return nothing with error message "Unrecognized parking ticket."
    @Test
    public void should_return_null_and_prompt_error_message_when_fetch_car_given_a_parking_lot_and_a_standard_parking_boy_and_a_used_parking_ticket() {
        // given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Ticket ticket = parkingBoy.performPark(car);
        Car parkedCar = parkingBoy.performFetchCar(ticket);

        // when
        Car refetchedCar = parkingBoy.performFetchCar(ticket);

        // then
        assertNotNull(parkedCar);
        assertNull(refetchedCar);
        assertTrue(outputStream.toString().contains("Unrecognized parking ticket."));
    }

    // Given a parking lot without any position, a standard parking boy, and a car, When park the car, Then return nothing with error message "No available position."
    @Test
    public void should_return_null_and_prompt_error_message_when_park_given_a_parking_lot_no_any_position_and_a_standard_parking_boy_and_a_car() {
        // given
        Car car = new Car("Car number 1");
        Car car2 = new Car("Car number 2");
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Ticket ticket = parkingBoy.performPark(car);

        // when
        Ticket ticket2 = parkingBoy.performPark(car2);

        // then
        assertNotNull(ticket);
        assertNull(ticket2);
        assertTrue(outputStream.toString().contains("No available position."));
    }


    // Given a standard parking boy, who manage two parking lots, both with available position, and a car, When park the car, Then the car will be parked to the first parking lot
    @Test
    public void should_return_ticket_from_first_parking_lots_when_park_given_2_parking_lots_and_a_standard_parking_boy_and_a_car() {
        // given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        parkingBoy.addParkingLot(parkingLot2);

        // when
        Ticket ticket = parkingBoy.performPark(car);

        // then
        assertEquals(1, parkingLot.getTicketCars().size());
        assertEquals(2, parkingBoy.getParkingLots().size());
        assertNotNull(ticket);
    }

    // Given a standard parking boy, who manage two parking lots, first is full and second with available position, and a car, When park the car, Then the car will be parked to the second parking lot
    @Test
    public void should_return_ticket_from_second_parking_lots_when_park_given_2_parking_lots_first_is_full_and_a_standard_parking_boy_and_a_car() {
        // given
        Car car = new Car("Car number 1");
        Car car2 = new Car("Car number 2");
        ParkingLot parkingLot = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        parkingBoy.addParkingLot(parkingLot2);
        Ticket ticket = parkingBoy.performPark(car);

        // when
        Ticket ticket2 = parkingBoy.performPark(car2);

        // then
        assertEquals(1, parkingLot.getTicketCars().size());
        assertEquals(1, parkingLot2.getTicketCars().size());
        assertEquals(2, parkingBoy.getParkingLots().size());
        assertNotNull(ticket);
        assertNotNull(ticket2);
    }

    // Given a standard parking boy, who manage two parking lots, both with a parked car, and two parking ticket, When fetch the car twice, Then return the right car with each ticket
    @Test
    public void should_return_cars_when_fetch_car_given_2_parking_lots_and_a_standard_parking_boy_and_tickets() {
        // given
        Car car = new Car("Car number 1");
        Car car2 = new Car("Car number 2");
        Car car3 = new Car("Car number 3");
        Car car4 = new Car("Car number 4");
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        parkingBoy.addParkingLot(parkingLot2);
        Ticket ticket = parkingBoy.performPark(car);
        Ticket ticket2 = parkingBoy.performPark(car2);
        Ticket ticket3 = parkingBoy.performPark(car3);
        Ticket ticket4 = parkingBoy.performPark(car4);

        // when
        Car parkedCar = parkingBoy.performFetchCar(ticket);
        Car parkedCar2 = parkingBoy.performFetchCar(ticket2);
        Car parkedCar3 = parkingBoy.performFetchCar(ticket3);
        Car parkedCar4 = parkingBoy.performFetchCar(ticket4);

        // then
        assertEquals(0, parkingLot.getTicketCars().size());
        assertEquals(0, parkingLot2.getTicketCars().size());
        assertEquals(2, parkingBoy.getParkingLots().size());
        assertEquals(car, parkedCar);
        assertEquals(car2, parkedCar2);
        assertEquals(car3, parkedCar3);
        assertEquals(car4, parkedCar4);
    }

    // Given a standard parking boy, who manage two parking lots, and an unrecognized ticket, When fetch the car, Then return nothing with error message "Unrecognized parking ticket.”
    @Test
    public void should_return_null_with_message_when_fetch_car_given_2_parking_lots_and_a_standard_parking_boy_and_unrecognized_ticket() {
        // given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        parkingBoy.addParkingLot(parkingLot2);
        Ticket ticket = parkingBoy.performPark(car);
        Ticket unrecognizedTicket = new Ticket(ticket.position() + 1, parkingLot);

        // when
        Car fetchedCar = parkingBoy.performFetchCar(unrecognizedTicket);

        // then
        assertEquals(1, parkingLot.getTicketCars().size());
        assertEquals(2, parkingBoy.getParkingLots().size());
        assertNotNull(ticket);
        assertNull(fetchedCar);
        assertTrue(outputStream.toString().contains("Unrecognized parking ticket."));
    }

    // Given a standard parking boy, who manage two parking lots, and a used ticket, When fetch the car, Then return nothing with error message "Unrecognized parking ticket."
    @Test
    public void should_return_null_with_message_when_fetch_car_given_2_parking_lots_and_a_standard_parking_boy_and_used_ticket() {
        // given
        Car car = new Car("Car number 1");
        ParkingLot parkingLot = new ParkingLot();
        ParkingLot parkingLot2 = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        parkingBoy.addParkingLot(parkingLot2);
        Ticket ticket = parkingBoy.performPark(car);
        Car fetchedCar = parkingBoy.performFetchCar(ticket);

        // when
        Car refetchedCar = parkingBoy.performFetchCar(ticket);

        // then
        assertEquals(0, parkingLot.getTicketCars().size());
        assertEquals(2, parkingBoy.getParkingLots().size());
        assertNotNull(ticket);
        assertNotNull(fetchedCar);
        assertNull(refetchedCar);
        assertTrue(outputStream.toString().contains("Unrecognized parking ticket."));
    }

    // Given a standard parking boy, who manage two parking lots, both without any position, and a car, When park the car, Then return nothing with error message "No available position."
    @Test
    public void should_return_null_with_message_when_park_given_2_parking_lots_are_full_and_a_standard_parking_boy_and_a_car() {
        // given
        Car car = new Car("Car number 1");
        Car car2 = new Car("Car number 2");
        Car car3 = new Car("Car number 3");
        Car car4 = new Car("Car number 4");
        Car car5 = new Car("Car number 5");
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(2);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        parkingBoy.addParkingLot(parkingLot2);
        Ticket ticket = parkingBoy.performPark(car);
        Ticket ticket2 = parkingBoy.performPark(car2);
        Ticket ticket3 = parkingBoy.performPark(car3);
        Ticket ticket4 = parkingBoy.performPark(car4);

        // when
        Ticket ticket5 = parkingBoy.performPark(car5);

        // then
        assertEquals(2, parkingLot.getTicketCars().size());
        assertEquals(2, parkingLot2.getTicketCars().size());
        assertEquals(2, parkingBoy.getParkingLots().size());
        assertNotNull(ticket);
        assertNotNull(ticket2);
        assertNotNull(ticket3);
        assertNotNull(ticket4);
        assertNull(ticket5);
        assertTrue(outputStream.toString().contains("No available position."));
    }

}
