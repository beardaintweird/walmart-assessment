/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketservice;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author SAK
 */
public class TicketService implements TicketServiceInterface {
    static Venue venue;
    TicketService() {
        this.venue = new Venue(25, 10);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TicketService ticketService = new TicketService();
        System.out.println(ticketService.numSeatsAvailable());
        SeatHold hold = ticketService.findAndHoldSeats(5, "test@test.com");
        System.out.println(ticketService.numSeatsAvailable());
        System.out.println(venue.seatsReserved);
        String reservationId = ticketService.reserveSeats(hold.id, "test@test.com");
        System.out.println(reservationId);
    }

    @Override
    public int numSeatsAvailable() {
        return venue.capacity - venue.seatsReserved;
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        if (numSeats > venue.capacity){
            throw new IllegalArgumentException("Number of seats requested is larger than the capacity of the venue.");
        }
        if (numSeats > numSeatsAvailable()){
            throw new IllegalArgumentException("There are not enough seats to accommodate this request.");
        }
        
        //Finding and adding the seats to the hold
        SeatHold hold = new SeatHold(numSeats, customerEmail);
        hold.seats = addSeats(venue, numSeats);
        
        venue.seatsReserved += numSeats;
        venue.addHold(hold);
        return hold;
    }

    @Override
    public String reserveSeats(String seatHoldId, String customerEmail) {
        SeatHold hold = venue.holdRequests.get(seatHoldId);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (now.after(hold.expiration)){
            throw new IllegalArgumentException("The hold id is expired.");
        }
        venue.holdRequests.remove(seatHoldId);
        for (Seat s:hold.seats){
            s.available = false;
        }
        
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
    // helper method to simplify code
    private ArrayList<Seat> addSeats(Venue venue, int numSeats){
        ArrayList list = new ArrayList<Seat>(numSeats);
        int seatsLeft = numSeats;
        for (Row r:venue.arrangement) {
            if (r.full) {
                continue;
            }
            int index = r.seats.length - r.seatsAvailable;
            while (index < r.seats.length && seatsLeft > 0) {
                list.add(r.seats[index]);
                r.decrement();
                index++;
                seatsLeft--;
            }
        }
        return list;
    }
}
