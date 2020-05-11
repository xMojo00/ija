import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.Collections;
import java.util.List;

public class Stop implements draw_map{
    private String Stop_id;  //street id
    private Street stop_street;   //ulice
    private Coordinate xy; // souradnice zastavky
    private Boolean is_corner = false;

    public java.lang.String getId() {
        return this.Stop_id;
    }

    public Coordinate getCoordinate() {
        if(null == this.xy){
            return null;
        }
        else{
            return this.xy;
        }
    }

    public void setStreet(Street s) {
        this.stop_street = s;
    }

    public Street getStreet() {
        return this.stop_street;
    }

    static Stop defaultStop(java.lang.String id, Coordinate c){
        return new Stop(id, c);
    }
    public Stop(String id, Coordinate c){
        this.Stop_id = id;
        this.xy = c;
    }
    
    @Override
    public List<Shape> draw(){
        return Collections.singletonList(
                new Circle(xy.getX(),xy.getY(), 8, Color.RED)
        );
    }
}
