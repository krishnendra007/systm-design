import java.util.HashSet;
import java.util.Set;

class BookingRepository {
    private Set<String> bookedSeats = new HashSet<>();

    // Check if already booked (fast read)
    public synchronized boolean isSeatAlreadyBooked(String showId, String seatId) {
        String key = showId + "_" + seatId;
        return bookedSeats.contains(key);
    }

    // Final DB write (acts like UNIQUE constraint)
    public synchronized void save(Booking booking) {
        // Step 1: Check again (simulate DB constraint)
        for (Seat seat : booking.seats) {
            String key = booking.show.showId + "_" + seat.seatId;

            if (bookedSeats.contains(key)) {
                throw new RuntimeException("Seat already booked: " + seat.seatId);
            }
        }

        // Step 2: Persist
        for (Seat seat : booking.seats) {
            String key = booking.show.showId + "_" + seat.seatId;
            bookedSeats.add(key);
        }
    }
}