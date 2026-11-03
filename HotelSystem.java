import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class HotelSystem {
    // -------------------------------------------------------------
    // Helper Class: Represents an individual hotel room
    // -------------------------------------------------------------
    static class Room {
        private int roomNumber;
        private String category; // Standard, Deluxe, Suite
        private double pricePerNight;
        private boolean isBooked;

        public Room(int roomNumber, String category, double pricePerNight) {
            this.roomNumber = roomNumber;
            this.category = category;
            this.pricePerNight = pricePerNight;
            this.isBooked = false;
        }

        public int getRoomNumber() { return roomNumber; }
        public String getCategory() { return category; }
        public double getPricePerNight() { return pricePerNight; }
        public boolean isBooked() { return isBooked; }
        public void setBooked(boolean booked) { this.isBooked = booked; }
    }

    // Global variables for data management
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static final String FILE_NAME = "bookings.txt";

    // -------------------------------------------------------------
    // Step-by-Step Method: Saves booking records into a local text file
    // -------------------------------------------------------------
    private static void saveBookingToFile(String guestName, Room room, int nights, double total) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write("Guest: " + guestName + " | Room: " + room.getRoomNumber() + 
                         " (" + room.getCategory() + ") | Nights: " + nights + 
                         " | Total Paid: $" + total + "\n");
        } catch (IOException e) {
            System.out.println("⚠️ Error saving booking history record to text file.");
        }
    }

    // -------------------------------------------------------------
    // Main Execution Flow & User Menu Dashboard
    // -------------------------------------------------------------
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Initialize hotel rooms inventory dataset
        rooms.add(new Room(101, "Standard", 80.00));
        rooms.add(new Room(102, "Standard", 80.00));
        rooms.add(new Room(201, "Deluxe", 150.00));
        rooms.add(new Room(301, "Suite", 300.00));

        while (true) {
            System.out.println("\n====== 🏨 HOTEL RESERVATION SYSTEM ======");
            System.out.println("1. View Available Rooms by Category");
            System.out.println("2. Book a Room & Process Payment");
            System.out.println("3. Exit Application");
            System.out.print("Select an action choice (1-3): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear keyboard input buffer junk
            
            if (choice == 3) {
                System.out.println("Closing reservation system. Goodbye!");
                break;
            }

            // SECTION 1: View rooms and filter their availability status
            if (choice == 1) {
                System.out.println("\n--- Current Rooms Inventory Status ---");
                for (Room r : rooms) {
                    String status = r.isBooked() ? "[❌ OCCUPIED]" : "[✅ AVAILABLE]";
                    System.out.printf("Room %d | Category: %-10s | Price: $%6.2f/night | %s\n",
                                      r.getRoomNumber(), r.getCategory(), r.getPricePerNight(), status);
                }
            }
            
            // SECTION 2: Search, validate availability, calculate price, and checkout
            else if (choice == 2) {
                System.out.print("Enter Guest full name: ");
                String name = scanner.nextLine();
                
                System.out.print("Enter preferred room number to book: ");
                int chosenRoomNum = scanner.nextInt();
                
                Room targetRoom = null;
                for (Room r : rooms) {
                    if (r.getRoomNumber() == chosenRoomNum) {
                        targetRoom = r;
                        break;
                    }
                }

                // Check if the requested room exists or is already taken
                if (targetRoom == null) {
                    System.out.println("❌ Error: Invalid room number. Please review the room list.");
                    continue;
                }
                if (targetRoom.isBooked()) {
                    System.out.println("❌ Error: This room is already occupied. Select a different room.");
                    continue;
                }

                System.out.print("Enter total staying duration (number of nights): ");
                int nights = scanner.nextInt();
                
                if (nights <= 0) {
                    System.out.println("❌ Error: Number of nights must be greater than 0.");
                    continue;
                }
                
                // Calculate billing total
                double totalBill = targetRoom.getPricePerNight() * nights;
                System.out.printf("Total calculated fees summary: $%.2f\n", totalBill);
                
                // Simulate Payment Processing Gateway Flow
                System.out.print("Process test payment transaction? (Type 1 for Yes, 2 for Cancel): ");
                int payConfirm = scanner.nextInt();
                
                if (payConfirm == 1) {
                    targetRoom.setBooked(true);
                    saveBookingToFile(name, targetRoom, nights, totalBill);
                    System.out.println("💳 Payment Approved! Booking successfully logged in 'bookings.txt'.");
                } else {
                    System.out.println("⚠️ Transaction canceled. Room reservation dropped.");
                }
            } else {
                System.out.println("❌ Invalid menu option. Please select 1, 2, or 3.");
            }
        }
        scanner.close();
    }
}

