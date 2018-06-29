/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticketservice;

/**
 *
 * @author SAK
 */
public class Row {
    boolean full;
    int seatsAvailable;
    Seat seats[];
    
    Row(int numSeats) {
        this.seatsAvailable = numSeats;
        this.seats = new Seat[numSeats];
    }
    
    public void decrementSeatsAvailable(){
        seatsAvailable--;
        if (seatsAvailable == 0) {
            this.full = true;
        }
    }
    
    public void incrementSeatsAvailable(){
        seatsAvailable++;
    }
}
