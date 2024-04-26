import java.util.*;
import java.util.stream.Stream;

public class TicketBooker {
     static int availableLowerBerth = 1;
     static int availableMiddleBerth = 1;
     static int availableUpperBerth = 1;
     static int availableRac = 1;
     static int availableWaiting = 1;

    private static List<Passenger> bookedTicketList = new LinkedList<>();
    private static Queue<Integer> waitingList = new LinkedList<>();
    private static Queue<Integer> racList = new LinkedList<>();

     static List<Integer> lowerBerthPosition = new ArrayList<>(Stream.generate(() -> {int i =1; return i++;}).limit(1).toList());
     static List<Integer> upperBerthPosition = new ArrayList<>(Stream.generate(() -> {int i =1; return i++;}).limit(1).toList());
     static List<Integer> middleBerthPosition = new ArrayList<>(Stream.generate(() -> {int i =1; return i++;}).limit(1).toList());
     static List<Integer> racPosition = new ArrayList<>(Stream.generate(() -> {int i =1; return i++;}).limit(1).toList());
     static List<Integer> waitingPosition = new ArrayList<>(Stream.generate(() -> {int i =1; return i++;}).limit(1).toList());

    private static Map<Integer, Passenger> passengerMap = new HashMap<>();


    public void bookTicket(Passenger passenger){

        if(availableWaiting == 0){
            System.out.println("No tickets available");
            return;
        }



        if((passenger.getBerthPreference().equals("L") && availableLowerBerth > 0)
                ||(passenger.getBerthPreference().equals("U") && availableUpperBerth>0)
                ||(passenger.getBerthPreference().equals("M") && availableMiddleBerth > 0)
        ){
            System.out.println("Preferred Berth Available");


            if(passenger.getBerthPreference().equals("L")){
                System.out.println("Lower Berth Given");
                passenger.setAllotedSeat(passenger.getBerthPreference());
                passenger.setSeatNumber(lowerBerthPosition.get(0));
                lowerBerthPosition.remove(0);
                availableLowerBerth--;
            }

            if(passenger.getBerthPreference().equals("U")){
                System.out.println("Upper Berth Given");
                passenger.setAllotedSeat(passenger.getBerthPreference());
                passenger.setSeatNumber(upperBerthPosition.get(0));
                upperBerthPosition.remove(0);
                availableUpperBerth--;
            }

            if(passenger.getBerthPreference().equals("M")){
                System.out.println("Middle Berth Given");
                passenger.setAllotedSeat(passenger.getBerthPreference());
                passenger.setSeatNumber(middleBerthPosition.get(0));
                middleBerthPosition.remove(0);
                availableMiddleBerth--;
            }

        }else if(availableLowerBerth>0){
            System.out.println("Lower Berth Given");
            passenger.setAllotedSeat("L");
            passenger.setSeatNumber(lowerBerthPosition.get(0));
            lowerBerthPosition.remove(0);
            availableLowerBerth--;
        }else if(availableMiddleBerth>0){
            System.out.println("Middle Berth Given");
            passenger.setAllotedSeat("M");
            passenger.setSeatNumber(middleBerthPosition.get(0));
            middleBerthPosition.remove(0);
            availableMiddleBerth--;
        }else if(availableUpperBerth>0){
            System.out.println("Upper Berth Given");
            passenger.setAllotedSeat("U");
            passenger.setSeatNumber(upperBerthPosition.get(0));
            upperBerthPosition.remove(0);
            availableUpperBerth--;
        }else if(availableRac > 0){
            System.out.println("RAC Given");
            passenger.setAllotedSeat("RAC");
            passenger.setSeatNumber(racPosition.get(0));
            racPosition.remove(0);
            racList.add(passenger.getId());
            availableRac--;
        }else if(availableWaiting > 0){
            System.out.println("In Waiting list");
            passenger.setAllotedSeat("WL");
            passenger.setSeatNumber(waitingPosition.get(0));
            waitingPosition.remove(0);
            waitingList.add(passenger.getId());
            availableWaiting--;
        }

        passengerMap.put(passenger.getId(),passenger);
        bookedTicketList.add(passenger);
        System.out.println("=".repeat(20)+"Ticket booked successfully");

    }

    public void cancelTicket(int id){
        if(passengerMap.containsKey(id)){
            Passenger passenger = passengerMap.get(id);
            passengerMap.remove(id);
            bookedTicketList.remove(passenger);
            if(passenger.getAllotedSeat().equals("L")){
                lowerBerthPosition.add(passenger.getSeatNumber());
                availableLowerBerth++;
            }else if(passenger.getAllotedSeat().equals("M")){
                middleBerthPosition.add(passenger.getSeatNumber());
                availableMiddleBerth++;
            }else if(passenger.getAllotedSeat().equals("U")){
                upperBerthPosition.add(passenger.getSeatNumber());
                availableUpperBerth++;
            }else if(passenger.getAllotedSeat().equals("RAC")){
                racList.remove(passenger.getId());
                racPosition.add(passenger.getSeatNumber());
                availableRac++;
                if(waitingList.size()>0){
                    Passenger waitingPassenger = passengerMap.get(waitingList.poll());
                    waitingList.remove(waitingPassenger.getId());
                    waitingPosition.add(waitingPassenger.getSeatNumber());
                    availableWaiting++;

                    waitingPassenger.setAllotedSeat("RAC");
                    waitingPassenger.setSeatNumber(racPosition.get(0));
                    racPosition.remove(0);
                    racList.add(waitingPassenger.getId());
                    availableRac--;
                }
                return;
            }else if(passenger.getAllotedSeat().equals("WL")){
                waitingList.remove(passenger.getId());
                waitingPosition.add(passenger.getSeatNumber());
                availableWaiting++;
                return;
            }

            Passenger racPassenger = passengerMap.get(racList.poll());
            passengerMap.remove(racPassenger.getId());
            racList.remove(racPassenger.getId());
            racPosition.add(racPassenger.getSeatNumber());
            availableRac++;
            if(waitingList.size()>0){
                Passenger waitingPassenger = passengerMap.get(waitingList.poll());
                waitingList.remove(waitingPassenger.getId());
                waitingPosition.add(waitingPassenger.getSeatNumber());
                availableWaiting++;

                waitingPassenger.setAllotedSeat("RAC");
                waitingPassenger.setSeatNumber(racPosition.get(0));
                racPosition.remove(0);
                racList.add(waitingPassenger.getId());
                availableRac--;
            }
            bookTicket(racPassenger);
            System.out.println("Ticket cancelled successfully");
        }
    }

    public void printBookedTickets(){
        passengerMap.values().forEach(System.out::println);
    }

    public void availableTickets(){
        System.out.println("""
                availableLowerBerth: %d
                availableMiddleBerth: %d
                availableUpperBerth: %d
                availableRac: %d
                availableWaiting: %d
                """.formatted(availableLowerBerth,availableMiddleBerth,availableUpperBerth,availableRac,availableWaiting));
    }
}
