import java.util.List;
import java.util.UUID;

class BookingService {
    private SeatLockProvider lockProvider;
    private PaymentService paymentService;
    private BookingRepository bookingRepository;

    public BookingService(SeatLockProvider lockProvider,
                          PaymentService paymentService,
                          BookingRepository bookingRepository) {
        this.lockProvider = lockProvider;
        this.paymentService = paymentService;
        this.bookingRepository = bookingRepository; // ✅ FIX
    }

    // ✅ Step 1: Create Booking (Lock only)
    public Booking createBooking(User user, Show show, List<Seat> seats) {

        // 1. Check if already booked (DB check)
        for (Seat seat : seats) {
            if (bookingRepository.isSeatAlreadyBooked(show.showId, seat.seatId)) {
                throw new RuntimeException("Seat already booked: " + seat.seatId);
            }
        }

        // 2. Lock seats (Redis simulation)
        boolean locked = lockProvider.lockSeats(show.showId, seats, user.userId);

        if (!locked) {
            throw new RuntimeException("Seats are currently locked by another user");
        }

        // 3. Create booking in LOCKED state
        Booking booking = new Booking(
                UUID.randomUUID().toString(),
                user,
                show,
                seats
        );

        booking.status = BookingStatus.LOCKED;

        return booking;
    }

    // ✅ Step 2: Confirm Booking (Payment + DB)
    public void confirmBooking(Booking booking) {

        // 1. Process payment
        boolean paymentSuccess = paymentService.processPayment(
                booking.user.userId,
                calculateAmount(booking)
        );

        if (!paymentSuccess) {
            // Release lock
            lockProvider.unlockSeats(booking.show.showId, booking.seats);
            booking.status = BookingStatus.CANCELLED;
            throw new RuntimeException("Payment failed");
        }

        // 2. Persist booking (FINAL check)
        try {
            bookingRepository.save(booking);  // 🔥 DB constraint simulation
            booking.status = BookingStatus.CONFIRMED;
        } catch (Exception e) {
            // Seat was already booked by someone else
            lockProvider.unlockSeats(booking.show.showId, booking.seats);
            booking.status = BookingStatus.CANCELLED;
            throw new RuntimeException("Seat already booked during confirmation");
        }

        // 3. Release locks after success
        lockProvider.unlockSeats(booking.show.showId, booking.seats);

        System.out.println("Booking Confirmed: " + booking.bookingId);
    }

    // Optional helper
    private double calculateAmount(Booking booking) {
        return booking.seats.size() * 100; // simple pricing
    }
}