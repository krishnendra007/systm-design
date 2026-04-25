import java.util.List;

class Dispatcher {

    private final List<Elevator> elevators;
    private ElevatorSelectionStrategy strategy;

    public Dispatcher(List<Elevator> elevators,
                      ElevatorSelectionStrategy strategy) {
        this.elevators = elevators;
        this.strategy = strategy;
    }

    public void requestLift(int floor, Direction direction) {

        ExternalRequest request = new ExternalRequest(floor, direction);

        Elevator best = strategy.selectElevator(elevators, request);

        if (best != null) {
            best.addRequest(floor);
        } else {
            System.out.println("No available elevator");
        }
    }
}