import java.util.List;

class Show {
    String showId;
    List<Seat> seats;

    public Show(String showId, List<Seat> seats) {
        this.showId = showId;
        this.seats = seats;
    }
}