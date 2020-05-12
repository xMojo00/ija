import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Collections;
import java.util.List;

public class Stop implements draw_map{
    private int stop_id;         //street id
    private String stop_name;   //ulice
    private Coordinate xy;          //souradnice zastavky
    private Boolean is_corner = false;

    public Coordinate get_coordinates(){
        return this.xy;
    }

    public Stop(int id, String name, Coordinate c){
        this.stop_id = id;
        this.stop_name = name;
        this.xy = c;
    }
    
    @Override
    public List<Shape> draw(){
        return Collections.singletonList(
                new Circle(xy.getX(),xy.getY(), 1.5, Color.RED)
        );
    }
}
