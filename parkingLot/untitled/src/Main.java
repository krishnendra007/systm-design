import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Slot> slots = Arrays.asList(
                new Slot(1, VehicleType.CAR),
                new Slot(2, VehicleType.CAR),
                new Slot(3, VehicleType.BIKE)
        );

        ParkingLot parkingLot = new ParkingLot(
                slots,
                new HourlyPricingStrategy()
        );

        Vehicle car1 = new Vehicle("RJ14-1234", VehicleType.CAR);
        Vehicle car2 = new Vehicle("RJ14-5678", VehicleType.CAR);
        Vehicle bike1 = new Vehicle("RJ14-1111", VehicleType.BIKE);
        Vehicle bike2 = new Vehicle("RJ14-2222", VehicleType.BIKE);

        System.out.println("===== HAPPY PATH =====");
        Ticket t1 = parkingLot.parkVehicle(car1);
        Ticket t2 = parkingLot.parkVehicle(bike1);

        sleep(2000);
        parkingLot.unparkVehicle(t1.getId());

        // =============================
        System.out.println("\n===== PARKING FULL SCENARIO =====");
        Ticket t3 = parkingLot.parkVehicle(car2); // should take last CAR slot
        Ticket t4 = parkingLot.parkVehicle(bike2); // should take BIKE slot

        // Now full
        Vehicle car3 = new Vehicle("RJ14-9999", VehicleType.CAR);
        Ticket t5 = parkingLot.parkVehicle(car3); // should fail

        // =============================
        System.out.println("\n===== INVALID UNPARK =====");
        parkingLot.unparkVehicle("INVALID_TICKET");

        // =============================
        System.out.println("\n===== DUPLICATE VEHICLE ENTRY =====");
        parkingLot.parkVehicle(car1); // same vehicle again

        // =============================
        System.out.println("\n===== LONG DURATION PRICING =====");
        sleep(3000);
        parkingLot.unparkVehicle(t2.getId());

        // =============================
        System.out.println("\n===== DOUBLE UNPARK =====");
        parkingLot.unparkVehicle(t2.getId()); // already removed

        // =============================
        System.out.println("\n===== CONCURRENCY TEST =====");

        Runnable task = () -> {
            Vehicle v = new Vehicle(UUID.randomUUID().toString(), VehicleType.CAR);
            Ticket t = parkingLot.parkVehicle(v);
            if (t != null) {
                sleep(1000);
                parkingLot.unparkVehicle(t.getId());
            }
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread th = new Thread(task);
            threads.add(th);
            th.start();
        }

        for (Thread th : threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\n===== TEST COMPLETE =====");
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
        }
    }
}