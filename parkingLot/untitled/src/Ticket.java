import java.time.LocalDateTime;

class Ticket {
    private String id;
    private Vehicle vehicle;
    private Slot slot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public Ticket(String id, Vehicle vehicle, Slot slot) {
        this.id = id;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = LocalDateTime.now();
    }

    public void closeTicket() {
        this.exitTime = LocalDateTime.now();
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public Slot getSlot() {
        return slot;
    }

    public String getId() {
        return id;
    }
}