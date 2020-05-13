import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Street implements draw_map{
    private int street_id;
    private String street_name;
    private ArrayList<Coordinate> co;
    private ArrayList<Stop> stops;
    private List<Shape> my_shape = new ArrayList<>();


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

    public List<Shape> getMy_shape() {
        return this.my_shape;
    }

    @Override
    public List<Shape> draw(){
        List<Shape> tmp_list = new ArrayList<Shape>();
        int counter = 0;
        while (counter != co.size() - 1) {
            /////debug
            Text t = new Text((co.get(counter).getX() + co.get(counter+1).getX()) / 2, (co.get(counter).getY() + co.get(counter+1).getY()) /2+10, street_name);
            t.setFont(Font.font("Verdana", 4));
            tmp_list.add(t);
            ////
            tmp_list.add(new Line(co.get(counter).getX(), co.get(counter).getY(), co.get(counter+1).getX(), co.get(counter+1).getY()));
            this.my_shape.add(tmp_list.get(tmp_list.size()-1));
            tmp_list.get(tmp_list.size()-1).setStrokeWidth(1.5);
            counter++;
        }
        return tmp_list;
    }
}
