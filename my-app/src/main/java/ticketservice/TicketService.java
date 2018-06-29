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
        
    }

    @Override
    public int numSeatsAvailable() {
        return venue.getCapacity() - venue.getSeatsReserved();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
        if (numSeats > venue.getCapacity()){
            throw new IllegalArgumentException("Number of seats requested is larger than the capacity of the venue.");
        }
        if (numSeats > numSeatsAvailable()){
            throw new IllegalArgumentException("There are not enough seats to accommodate this request.");
        }
        
        //Finding and adding the seats to the hold
        SeatHold hold = new SeatHold(numSeats, customerEmail);
        hold.seats = addSeats(numSeats);
        
        venue.incrementSeatsReserved(numSeats);
        venue.addHold(hold);
        return hold;
    }

    @Override
    public String reserveSeats(String seatHoldId, String customerEmail) {
        SeatHold hold = venue.holdRequests.get(seatHoldId);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (now.after(hold.getExpiration())){
            removeSeats(hold.seats);
            throw new IllegalArgumentException("This hold has expired.");
        }
        venue.removeHoldRequest(hold.getId());
        
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
    
    // helper method to remove seats
    private void removeSeats(ArrayList<Seat> seats){
    	for (Seat s:seats){
    		Row row = venue.arrangement.get(s.row);
    		row.decrementSeatsAvailable();
            s.available = true;
        }
    }
    // helper method to add seats to SeatHold
    private ArrayList<Seat> addSeats(int numSeats){
        ArrayList list = new ArrayList<Seat>(numSeats);
        int seatsLeft = numSeats;
        for (Row r:venue.arrangement) {
            if (r.full) {
                continue;
            }
            int index = r.seats.length - r.seatsAvailable;
            while (index < r.seats.length && seatsLeft > 0) {
            	Seat seat = r.seats[index];
                list.add(seat);
                seat.available = false;
                r.decrementSeatsAvailable();
                index++;
                seatsLeft--;
            }
        }
        return list;
    }
}
