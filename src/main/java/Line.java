import java.time.LocalTime;
import java.util.List;

public class Line {
    private String name;
    private int line_id;
    private List<LocalTime> start_times;
    private List<Stop> stops;

    static Line defaltLine( String name, int line_id, List<LocalTime> starts, List<Stop> stops) {
        return new Line(starts, stops, name, line_id);
    }

    public Line(List<LocalTime> starts, List<Stop> stops, String name, int line_id) {
        this.name = name;
        this.line_id = line_id;
        this.start_times = starts;
        this.stops = stops;
    }

    public List<LocalTime> get_start_times(){
        return this.start_times;
    }

    public int get_line_id(){
        return this.line_id;
    }

    public Coordinate start_stop(){
        return this.stops.get(0).get_coordinates();
    }
}
