5public class Main {

    public static void main(String[] args) throws InterruptedException {

        ElevatorSystem system = new ElevatorSystem(3);

        // External requests
        system.externalRequest(5, Direction.UP);
        system.externalRequest(2, Direction.DOWN);
        system.externalRequest(8, Direction.UP);

        // Simulate system
        for (int i = 0; i < 10; i++) {
            System.out.println("\n--- Step " + i + " ---");
            system.stepAll();
            Thread.sleep(1000);
        }

        // Internal request
        system.internalRequest(0, 10);
    }
}