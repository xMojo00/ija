import java.time.LocalTime;
import java.util.ArrayList;

public class BusTimetable {
    private String Line;
    private ArrayList<LocalTime> Times = new ArrayList<>();

    public BusTimetable(String line) {
        this.Line = line;
    }

    public void addTime(LocalTime time) {
        this.Times.add(time);
    }

    public String getLine() {
        return Line;
    }

    public ArrayList<LocalTime> getTime() {
        return Times;
    }
}
