import java.util.List;

class CostBasedStrategy implements ElevatorSelectionStrategy {

    @Override
    public Elevator selectElevator(List<Elevator> elevators, ExternalRequest req) {

        Elevator best = null;
        int minCost = Integer.MAX_VALUE;

        for (Elevator e : elevators) {

            if (e.getState() == ElevatorState.OUT_OF_SERVICE) continue;

            int distance = Math.abs(e.getCurrentFloor() - req.floor);

            int directionPenalty = 0;
            if (e.getDirection() != Direction.IDLE &&
                    e.getDirection() != req.direction) {
                directionPenalty = 10;
            }

            int loadPenalty = e.getLoad();

            int cost = distance + directionPenalty + loadPenalty;

            if (cost < minCost) {
                minCost = cost;
                best = e;
            }
        }

        return best;
    }
}