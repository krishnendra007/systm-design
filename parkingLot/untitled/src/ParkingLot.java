import java.util.*;

class ParkingLot {
    private SlotManager slotManager;
    private PricingStrategy pricingStrategy;
    private Map<String, Ticket> activeTickets = new HashMap<>();

    public ParkingLot(List<Slot> slots, PricingStrategy pricingStrategy) {
        this.slotManager = new SlotManager(slots);
        this.pricingStrategy = pricingStrategy;
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        Slot slot = slotManager.allocateSlot(vehicle);

        if (slot == null) {
            System.out.println("Parking Full");
            return null;
        }

        String ticketId = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(ticketId, vehicle, slot);

        activeTickets.put(ticketId, ticket);

        System.out.println("Parked at slot: " + slot.getId());
        return ticket;
    }

    public double unparkVehicle(String ticketId) {
        Ticket ticket = activeTickets.get(ticketId);

        if (ticket == null) {
            throw new RuntimeException("Invalid Ticket");
        }

        ticket.closeTicket();
        double amount = pricingStrategy.calculate(ticket);

        slotManager.freeSlot(ticket.getSlot());
        activeTickets.remove(ticketId);

        System.out.println("Amount: " + amount);
        return amount;
    }
}