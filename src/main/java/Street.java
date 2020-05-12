import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;

public class Street implements draw_map{
    private int street_id;
    private String street_name;
    private ArrayList<Coordinate> co = new ArrayList<Coordinate>();
    private ArrayList<Stop> stops = new ArrayList<Stop>();


    public Street(int id, String name, ArrayList<Coordinate> coordinates, ArrayList<Stop> stops) {
       this.street_id = id;
       this.street_name = name;
       this.co = coordinates;
       this.stops = stops;
    }


    public int getId() {
        return this.street_id;
    }

    public List<Coordinate> getCoordinates() {
        return this.co;
    }

    public Coordinate begin() {
        return co.get(0);
    }

    public Coordinate end() {
        return co.get(co.size() - 1);
    }

    public List<Stop> getStops() {
        return this.stops;
    }


    @Override
    public List<Shape> draw(){
        List<Shape> sss = new ArrayList<Shape>();
        int counter = 0;
        while (counter != co.size() - 1) {
            sss.add(new Line(co.get(counter).getX(), co.get(counter).getY(), co.get(counter+1).getX(), co.get(counter+1).getY()));
            counter++;
        }
        return sss;
    }
}
