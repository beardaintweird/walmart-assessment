#Ticket Service

##Assumptions
- That the venue is 250 seats
- That the best seats are arranged from the front to the back
- That the reservations do not need to be stored anywhere because that is out of scope of the requirements
- That the expiration time for a hold request is 5 minutes
- That I could adjust the interface to accept the SeatHold id as a String and not a int. This allowed me to use a more secure and unique ID (UUID)

##Build instructions
- Git clone `TicketService`
- Simply use `mvn clean install` at the path `TicketService/my-app/`
