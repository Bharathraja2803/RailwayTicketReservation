import java.util.StringJoiner;

public class Passenger {
    private int id;
    private String name;
    private int age;
    private String berthPreference;
    private static int idGenerator = 0;
    private String allotedSeat;
    private int seatNumber;
    

    public Passenger(String name, int age, String berthPreference) {
        this.name = name;
        this.age = age;
        this.berthPreference = berthPreference;
        this.id = ++idGenerator;
        allotedSeat = "";
        seatNumber = -1;
        
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getBerthPreference() {
        return berthPreference;
    }

    public String getAllotedSeat() {
        return allotedSeat;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setAllotedSeat(String allotedSeat) {
        this.allotedSeat = allotedSeat;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Passenger.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("age=" + age)
                .add("seatNumber=" + seatNumber+allotedSeat)
                .toString();
    }
}
