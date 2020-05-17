
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Reprezentuje jeden seznam na zastávece s příjezdy.
 * @author Mojmír Kyjonka
 */
public class BusTimetable {
    private String Line;
    private ArrayList<LocalTime> Times = new ArrayList<>();

    /**
     * Nastaví linku.
     * @param line Objekt linky.
     */
    public BusTimetable(String line) {
        this.Line = line;
    }

    /**
     * Do seznamu přidá čas.
     * @param time Objekt reprezentující časový údaj.
     */
    public void addTime(LocalTime time) {
        this.Times.add(time);
    }

    /**
     * Vrací objekt linky.
     * @return objekt linky.
     */
    public String getLine() {
        return Line;
    }

    /**
     * Vrací seznam časů.
     * @return Seznam časů.
     */
    public ArrayList<LocalTime> getTime() {
        return Times;
    }
}
