//////////////////////////////////////////////////////////////////////
//      Authors: Petr Balazy(xbalaz10), Mojmir Kyjonka(xkyjon00)    //
//      Poject: Java - public transport simulation                  //
//      Description: Class Line - represents line and its           //
//      information.                                                //
//////////////////////////////////////////////////////////////////////


import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Line implements draw_map{
    private String name;
    private int line_id;
    private List<LocalTime> start_times;
    private List<Stop> stops;
    private List<Shape> my_shape = new ArrayList<>();

    public Line(List<LocalTime> starts, List<Stop> stops, String name, int line_id) {
        this.name = name;
        this.line_id = line_id;
        this.start_times = starts;
        this.stops = stops;
    }

    public List<Shape> getMy_shape(){
        return this.my_shape;
    }

    public Stop get_stop(int index) {
        return this.stops.get(index);
    }

    public List<Stop> get_stops() {
        return this.stops;
    }

    public int stop_count() {
        return stops.size();
    }

    public List<LocalTime> get_start_times(){
        return this.start_times;
    }

    public int getLine_id(){
        return this.line_id;
    }

    public Coordinate start_stop(){
        return this.stops.get(0).get_coordinates();
    }

    public Coordinate last_stop(){
        return this.stops.get(stops.size()-1).get_coordinates();
    }

    public String getName(){
        return this.name;
    }

    @Override
    public List<Shape> draw(){
        my_shape.clear();
        for (int i = 0; i < get_stops().size() - 1; i++){
            javafx.scene.shape.Line l = new javafx.scene.shape.Line(get_stops().get(i).get_coordinates().getX(),
                    get_stops().get(i).get_coordinates().getY(),
                    get_stops().get(i+1).get_coordinates().getX(),
                    get_stops().get(i+1).get_coordinates().getY());
            l.setStrokeWidth(2);
            l.setStroke(Color.YELLOW);
            my_shape.add(l);
        }
        return my_shape;
    }
}
