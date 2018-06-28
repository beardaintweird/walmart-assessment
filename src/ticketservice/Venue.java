/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketservice;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author SAK
 */
public class Venue {
    int capacity;
    int seatsReserved;
    ArrayList<Row> arrangement;
    HashMap<String, SeatHold> holdRequests;
    
    Venue(int numSeats, int numRows) {
        this.capacity = numSeats * numRows;
        this.seatsReserved = 0;
        this.arrangement = new ArrayList<Row>(numRows);
        this.holdRequests = new HashMap<String, SeatHold>();
        
        for(int rowNum = 0; rowNum < numRows; rowNum++){
            Row row = new Row(numSeats);
            for(int seatNum = 0; seatNum < numSeats; seatNum++){
                row.seats[seatNum] = new Seat(rowNum,seatNum);
            }
            this.arrangement.add(row);
        }
    }
    
    public void addHold(SeatHold hold){
        holdRequests.put(hold.id,hold);
    }
    
    public void reserveSeats() {
        
    }
}
