//////////////////////////////////////////////////////////////////////
//      Authors: Petr Balazy(xbalaz10), Mojmir Kyjonka(xkyjon00)    //
//      Poject: Java - public transport simulation                  //
//      Description: Class Street - represents street and its       //
//      information.                                                //
//////////////////////////////////////////////////////////////////////

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

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

    public String get_street_name() {
        return this.street_name;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

    public List<Shape> getMy_shape() {
        return this.my_shape;
    }

    @Override
    public List<Shape> draw(){
        List<Shape> tmp_list = new ArrayList<>();
        int counter = 0;
        while (counter != co.size() - 1) {
            tmp_list.add(new Line(co.get(counter).getX(), co.get(counter).getY(), co.get(counter + 1).getX(), co.get(counter + 1).getY()));
            this.my_shape.add(tmp_list.get(tmp_list.size() - 1));
            tmp_list.get(tmp_list.size() - 1).setStrokeWidth(1.5);
            counter++;
        }
        return tmp_list;
    }
}
