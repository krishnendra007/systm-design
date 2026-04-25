import java.util.*;

class SlotManager {
    private Map<VehicleType, Queue<Slot>> freeSlots = new HashMap<>();

    public SlotManager(List<Slot> slots) {
        for (Slot slot : slots) {
            VehicleType type = slot.getType();

            Queue<Slot> queue = freeSlots.get(type);
            if (queue == null) {
                queue = new LinkedList<>();
                freeSlots.put(type, queue);
            }

            queue.offer(slot);
        }
    }

    public synchronized Slot allocateSlot(Vehicle vehicle) {
        Queue<Slot> queue = freeSlots.get(vehicle.getType());
        if (queue == null || queue.isEmpty()) {
            return null;
        }

        Slot slot = queue.poll();
        slot.park();
        return slot;
    }

    public synchronized void freeSlot(Slot slot) {
        slot.unpark();
        freeSlots.get(slot.type).offer(slot);
    }
}