import java.util.*;

class ElevatorSystem {

    private final List<Elevator> elevators = new ArrayList<>();
    private final Dispatcher dispatcher;

    public ElevatorSystem(int n) {

        for (int i = 0; i < n; i++) {
            elevators.add(new Elevator(i));
        }

        dispatcher = new Dispatcher(elevators, new CostBasedStrategy());
    }

    public void externalRequest(int floor, Direction direction) {
        dispatcher.requestLift(floor, direction);
    }

    public void internalRequest(int elevatorId, int floor) {
        elevators.get(elevatorId).selectFloor(floor);
    }

    public void stepAll() {
        for (Elevator e : elevators) {
            e.step();
        }
    }
}