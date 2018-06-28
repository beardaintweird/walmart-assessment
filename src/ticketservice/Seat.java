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
class Seat {
    boolean available;
    int row;
    int seat;
    
    Seat(int row, int seat) {
        this.available = true;
        this.row = row;
        this.seat = seat;
    }
}
