import java.util.Scanner;
import java.util.TooManyListenersException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static TicketBooker ticketBooker = new TicketBooker();
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while(!exit){
            menu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case 1: {
                    System.out.println("Enter name, age and berthPreference(Lower L, Upper U, Middle M)");
                    System.out.printf("name: ");
                    String name = scanner.nextLine();

                    System.out.printf("age:");
                    int age = scanner.nextInt();

                    System.out.println("Berth:");

                    scanner.nextLine();
                    String berthPreference = scanner.nextLine();
                    Passenger passenger = new Passenger(name, age, berthPreference);
                    ticketBooker.bookTicket(passenger);

                    break;
                }
                case 2: {
                    System.out.printf("Enter the Ticket id: ");
                    int id = scanner.nextInt();
                    ticketBooker.cancelTicket(id);
                    break;
                }
                case 3: {
                    ticketBooker.printBookedTickets();
                    break;
                }
                case 4: {
                    ticketBooker.availableTickets();
                    break;
                }
                case 5: {
                    exit = true;
                    System.out.println("Thanks for Booking");
                    break;
                }
                default: {
                    System.out.println("Choose the valid Option");
                }
            }
        }
    }

    public static void menu(){
        System.out.println("""
                    Menu:                
                    1. Book ticket
                    2. Cancel ticket
                    3. Print booked ticket
                    4. Print available ticket
                    5. Exit 
                    Choose the options from below:
                """);
    }
}