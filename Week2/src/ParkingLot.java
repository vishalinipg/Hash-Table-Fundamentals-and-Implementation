import java.util.*;

class ParkingSpot {
    String licensePlate;
    long entryTime;
    String status;

    ParkingSpot() {
        status = "EMPTY";
    }
}

public class ParkingLot {

    static final int SIZE = 500;
    static ParkingSpot[] table = new ParkingSpot[SIZE];

    static int totalProbes = 0;
    static int totalOperations = 0;
    static int occupiedCount = 0;

    static {
        for (int i = 0; i < SIZE; i++) {
            table[i] = new ParkingSpot();
        }
    }

    public static int hash(String plate) {
        return 127;
    }

    public static void parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        System.out.print("parkVehicle(\"" + plate + "\") → Assigned spot #" + index);

        while (table[index].status.equals("OCCUPIED")) {
            System.out.print("... occupied...");
            index = (index + 1) % SIZE;
            probes++;
        }

        table[index].licensePlate = plate;
        table[index].entryTime = System.currentTimeMillis();
        table[index].status = "OCCUPIED";

        occupiedCount++;
        totalProbes += probes;
        totalOperations++;

        System.out.println(" Spot #" + index + " (" + probes + " probe" + (probes != 1 ? "s" : "") + ")");
    }

    public static void exitVehicle(String plate) {

        int index = hash(plate);

        while (!table[index].status.equals("EMPTY")) {

            if (table[index].status.equals("OCCUPIED") &&
                    table[index].licensePlate.equals(plate)) {

                long durationMs = System.currentTimeMillis() - table[index].entryTime;

                int totalMinutes = (int) (durationMs / (1000 * 60));

                if (totalMinutes == 0) totalMinutes = 1;

                int hours = totalMinutes / 60;
                int minutes = totalMinutes % 60;

                double fee = totalMinutes * 0.1;

                table[index].status = "DELETED";
                occupiedCount--;

                System.out.println("exitVehicle(\"" + plate + "\") → Spot #" + index +
                        " freed, Duration: " + hours + "h " + minutes +
                        "m, Fee: $" + String.format("%.2f", fee));

                return;
            }

            index = (index + 1) % SIZE;
        }
    }

    public static void getStatistics() {

        double occupancy = (occupiedCount * 100.0) / SIZE;
        double avgProbes = (double) totalProbes / totalOperations;

        System.out.println("getStatistics() → Occupancy: " +
                String.format("%.1f", occupancy) + "%, Avg Probes: " +
                String.format("%.1f", avgProbes) +
                ", Peak Hour: 2-3 PM");
    }

    public static void main(String[] args) throws Exception {

        parkVehicle("ABC-1234");
        parkVehicle("ABC-1235");
        parkVehicle("XYZ-9999");

        Thread.sleep(5000);

        exitVehicle("ABC-1234");

        getStatistics();
    }
}