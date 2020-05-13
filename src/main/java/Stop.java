import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Stop implements draw_map{
    private int stop_id;         //street id
    private String stop_name;   //ulice
    private Coordinate xy;          //souradnice zastavky
    private Boolean is_corner = false;

    public Coordinate get_coordinates(){
        return this.xy;
    }

    public int get_id() {
        return this.stop_id;
    }

    public Stop(int id, String name, Coordinate c){
        this.stop_id = id;
        this.stop_name = name;
        this.xy = c;
    }
    
    @Override
    public List<Shape> draw(){
        List<Shape> tmp_list = new ArrayList<Shape>();
        Text t = new Text(xy.getX(), xy.getY(), stop_name);
        t.setFont(Font.font("Verdana", 4));
        tmp_list.add(t);
        tmp_list.add(new Circle(xy.getX(),xy.getY(), 1.5, Color.RED));
        return tmp_list;
        /*
        return Collections.singletonList(
                new Circle(xy.getX(),xy.getY(), 1.5, Color.RED)
        );*/
    }
}
