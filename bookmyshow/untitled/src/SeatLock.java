class SeatLock {
    String seatId;
    String userId;
    long expiryTime;

    public SeatLock(String seatId, String userId, long expiryTime) {
        this.seatId = seatId;
        this.userId = userId;
        this.expiryTime = expiryTime;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}