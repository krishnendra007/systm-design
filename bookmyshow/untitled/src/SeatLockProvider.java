import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SeatLockProvider {
    private Map<String, SeatLock> locks = new HashMap<>();
    private static final long LOCK_TIMEOUT = 5 * 60 * 1000; // 5 min

    public synchronized boolean lockSeats(String showId, List<Seat> seats, String userId) {
        long now = System.currentTimeMillis();

        // Check availability
        for (Seat seat : seats) {
            String key = showId + "_" + seat.seatId;

            if (locks.containsKey(key)) {
                SeatLock lock = locks.get(key);
                if (!lock.isExpired()) {
                    return false; // already locked
                }
            }
        }

        // Lock all
        for (Seat seat : seats) {
            String key = showId + "_" + seat.seatId;
            locks.put(key, new SeatLock(seat.seatId, userId, now + LOCK_TIMEOUT));
        }

        return true;
    }

    public synchronized void unlockSeats(String showId, List<Seat> seats) {
        for (Seat seat : seats) {
            locks.remove(showId + "_" + seat.seatId);
        }
    }
}