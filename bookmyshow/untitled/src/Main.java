import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        SeatLockProvider lockProvider = new SeatLockProvider();
        PaymentService paymentService = new PaymentService();
        BookingRepository bookingRepository = new BookingRepository();

        BookingService bookingService =
                new BookingService(lockProvider, paymentService, bookingRepository);

        User user1 = new User("U1", "Krish");
        User user2 = new User("U2", "Rahul");

        List<Seat> seats = List.of(new Seat("A1"), new Seat("A2"));
        Show show = new Show("S1", seats);

        System.out.println("\n===== 1. HAPPY PATH =====");

        Booking booking1 = bookingService.createBooking(user1, show, seats);
        System.out.println("User1 locked seats");

        bookingService.confirmBooking(booking1);

        // -------------------------------------------------------

        System.out.println("\n===== 2. RE-BOOK SAME SEAT (Already Booked) =====");

        try {
            bookingService.createBooking(user2, show, seats);
        } catch (Exception e) {
            System.out.println("User2 failed: " + e.getMessage());
        }

        // -------------------------------------------------------

        System.out.println("\n===== 3. RACE CONDITION (Parallel Booking) =====");

        Seat seatB1 = new Seat("B1");
        List<Seat> raceSeats = List.of(seatB1);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task1 = () -> {
            try {
                Booking b1 = bookingService.createBooking(user1, show, raceSeats);
                Thread.sleep(100); // simulate delay
                bookingService.confirmBooking(b1);
                System.out.println("User1 SUCCESS");
            } catch (Exception e) {
                System.out.println("User1 FAILED: " + e.getMessage());
            }
        };

        Runnable task2 = () -> {
            try {
                Booking b2 = bookingService.createBooking(user2, show, raceSeats);
                Thread.sleep(100);
                bookingService.confirmBooking(b2);
                System.out.println("User2 SUCCESS");
            } catch (Exception e) {
                System.out.println("User2 FAILED: " + e.getMessage());
            }
        };

        executor.submit(task1);
        executor.submit(task2);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // -------------------------------------------------------

        System.out.println("\n===== 4. LOCK CONFLICT (Seat Locked but Not Booked Yet) =====");

        Seat seatC1 = new Seat("C1");
        List<Seat> lockSeats = List.of(seatC1);

        Booking tempBooking = bookingService.createBooking(user1, show, lockSeats);
        System.out.println("User1 locked C1 but not confirmed");

        try {
            bookingService.createBooking(user2, show, lockSeats);
        } catch (Exception e) {
            System.out.println("User2 blocked due to lock: " + e.getMessage());
        }

        // Cleanup
        bookingService.confirmBooking(tempBooking);

        // -------------------------------------------------------

        System.out.println("\n===== 5. PAYMENT FAILURE SCENARIO =====");

        // Override payment service temporarily
        PaymentService failingPaymentService = new PaymentService() {
            @Override
            public boolean processPayment(String userId, double amount) {
                return false;
            }
        };

        BookingService failingBookingService =
                new BookingService(lockProvider, failingPaymentService, bookingRepository);

        Seat seatD1 = new Seat("D1");
        List<Seat> failSeats = List.of(seatD1);

        try {
            Booking failBooking = failingBookingService.createBooking(user1, show, failSeats);
            failingBookingService.confirmBooking(failBooking);
        } catch (Exception e) {
            System.out.println("Payment failed handled: " + e.getMessage());
        }

        // Retry after failure (should succeed now)
        System.out.println("Retrying after failure...");

        Booking retryBooking = bookingService.createBooking(user1, show, failSeats);
        bookingService.confirmBooking(retryBooking);

        // -------------------------------------------------------

        System.out.println("\n===== ALL TESTS DONE =====");
    }
}