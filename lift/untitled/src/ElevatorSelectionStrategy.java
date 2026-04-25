import java.util.List;

interface ElevatorSelectionStrategy {
    Elevator selectElevator(List<Elevator> elevators, ExternalRequest request);
}