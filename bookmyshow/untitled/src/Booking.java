import java.util.List;

class Booking {
    String bookingId;
    User user;
    Show show;
    List<Seat> seats;
    BookingStatus status;

    public Booking(String bookingId, User user, Show show, List<Seat> seats) {
        this.bookingId = bookingId;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = BookingStatus.CREATED;
    }
}