package ticketservice;

import org.junit.BeforeClass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TicketServiceTest {
	private static TicketService ticketService;
	private String email = "test@test.com";
	
	@Before 
	public void initTicketService() {
		ticketService = new TicketService();
	}
	
	
	@Test
	public void TestNumSeatsAvailable(){
		int originalValue = ticketService.numSeatsAvailable();
		ticketService.findAndHoldSeats(10, email);
		int finalValue = ticketService.numSeatsAvailable();
		assertEquals(250, originalValue);
		assertEquals(240, finalValue);
	}
	
	@Test
	public void TestHoldSeatsHappyPath(){
		SeatHold hold = ticketService.findAndHoldSeats(5, email);
		String id = hold.getId();
		int numSeats = hold.seats.size();
		SeatHold reservation = ticketService.venue.holdRequests.get(id);
		
		assertEquals(5, numSeats);
		assertEquals(email, hold.getEmail());
		assertEquals(reservation.seats.size(), numSeats);
		assertEquals(reservation.getId(), id);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestHoldSeatsExceptionOne(){
		ticketService.findAndHoldSeats(251, email);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void TestHoldSeatsExceptionTwo(){
		ticketService.findAndHoldSeats(245, email);
		ticketService.findAndHoldSeats(6, email);
	}
	
	@Test
	public void TestReserveSeatsExpirationException(){
		SeatHold hold = ticketService.findAndHoldSeats(5, email);
		Timestamp faultyTimestamp = new Timestamp(System.currentTimeMillis());
		hold.setExpiration(faultyTimestamp);
		
	    try {
			ticketService.reserveSeats(hold.getId(), email);
	    } catch (IllegalArgumentException exception) {
	        assertEquals(exception.getMessage(), "This hold has expired.");
	    }
	}
	
	@Test
	public void TestReserveSeatsHappyPath(){
		SeatHold hold = ticketService.findAndHoldSeats(5, email);
		String holdId = hold.getId();
		String reservationId = ticketService.reserveSeats(holdId, email);
		
		assertEquals(ticketService.venue.holdRequests.get(holdId), null);
	}
	
}
