import java.util.*;

class Elevator {

    private final int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;

    private final PriorityQueue<Integer> upQueue = new PriorityQueue<>();
    private final PriorityQueue<Integer> downQueue =
            new PriorityQueue<>(Collections.reverseOrder());

    private final Set<Integer> pendingStops = new HashSet<>();

    private final int capacity = 10;
    private int load = 0;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
    }

    // 🚀 Add request (handles duplicates)
    public synchronized void addRequest(int floor) {

        if (state == ElevatorState.OUT_OF_SERVICE) return;

        if (floor == currentFloor) {
            openDoor();
            return;
        }

        if (pendingStops.contains(floor)) return;

        pendingStops.add(floor);

        if (floor > currentFloor) {
            upQueue.offer(floor);
        } else {
            downQueue.offer(floor);
        }
    }

    // 🚀 Internal request
    public void selectFloor(int floor) {
        addRequest(floor);
    }

    // 🚀 Main loop step (LOOK logic)
    public synchronized void step() {

        if (state == ElevatorState.OUT_OF_SERVICE) return;

        if (direction == Direction.IDLE) {
            if (!upQueue.isEmpty()) direction = Direction.UP;
            else if (!downQueue.isEmpty()) direction = Direction.DOWN;
            else return;
        }

        if (direction == Direction.UP) {
            processUp();
        } else {
            processDown();
        }
    }

    private void processUp() {

        if (!upQueue.isEmpty()) {
            int next = upQueue.poll();
            moveTo(next);
        } else if (!downQueue.isEmpty()) {
            direction = Direction.DOWN;
        } else {
            direction = Direction.IDLE;
        }
    }

    private void processDown() {

        if (!downQueue.isEmpty()) {
            int next = downQueue.poll();
            moveTo(next);
        } else if (!upQueue.isEmpty()) {
            direction = Direction.UP;
        } else {
            direction = Direction.IDLE;
        }
    }

    private void moveTo(int floor) {

        state = ElevatorState.MOVING;

        System.out.println("Elevator " + id +
                " moving from " + currentFloor + " to " + floor);

        currentFloor = floor;

        state = ElevatorState.STOPPED;
        pendingStops.remove(floor);

        openDoor();
    }

    private void openDoor() {
        state = ElevatorState.DOOR_OPEN;
        System.out.println("Elevator " + id +
                " opening door at floor " + currentFloor);
        state = ElevatorState.IDLE;
    }

    // 🚨 Failure simulation
    public void markOutOfService() {
        state = ElevatorState.OUT_OF_SERVICE;
        upQueue.clear();
        downQueue.clear();
        pendingStops.clear();
    }

    // Getters
    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLoad() {
        return load;
    }

    public ElevatorState getState() {
        return state;
    }
}