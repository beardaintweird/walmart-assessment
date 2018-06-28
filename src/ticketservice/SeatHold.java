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
class SeatHold {
    ArrayList<Seat> seats;
    String email;
    String id;
    Timestamp expiration;
    
    SeatHold(int numSeats, String email) {
        this.seats = new ArrayList<Seat>(numSeats);
        this.email = email;
        
        UUID uuid = UUID.randomUUID();
        this.id   = uuid.toString();
        
        int fiveMinutes = 1000 * 500;
        this.expiration = new Timestamp(System.currentTimeMillis() + fiveMinutes);
    }
}
