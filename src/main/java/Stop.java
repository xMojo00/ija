import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Stop implements draw_map{
    private int stop_id;         //street id
    private String stop_name;   //ulice
    private Coordinate xy;          //souradnice zastavky
    private Boolean corner = false;
    private Shape my_shape;

    public Coordinate get_coordinates(){
        return this.xy;
    }

    public int get_id() {
        return this.stop_id;
    }

    public Stop(int id, String name, Coordinate c){
        this.stop_id = id;
        this.stop_name = name;
        if (name.substring(0,1).compareTo("#") == 0) corner = true;
        this.xy = c;
    }

    public String getStop_name(){
        return this.stop_name;
    }

    public Shape getMy_shape(){
        return my_shape;
    }

    public boolean is_corner() {
        return this.corner;
    }

    @Override
    public List<Shape> draw(){
        List<Shape> tmp_list = new ArrayList<Shape>();
        tmp_list.add(new Circle(xy.getX(),xy.getY(), 6, Color.RED));
        this.my_shape = tmp_list.get(tmp_list.size() - 1);
        return tmp_list;
    }
}
