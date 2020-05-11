import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Vehicle implements draw_map{
    private String vehicle_id;
    private static Coordinate actual_position;
    private static Coordinate destination;
    private List<Shape> bus;

    static Vehicle defaultVehicle​(java.lang.String id, Coordinate c){
        return new Vehicle(id, c);
    }
    public Vehicle(String id, Coordinate c){
        this.vehicle_id = id;
        this.actual_position = c;

        bus = new ArrayList<>();
        bus.add(new Circle(actual_position.getX(),actual_position.getY(), 8, Color.BLUE));
    }



    public void set_destination ( Coordinate c){
        this.destination = c;
    }

    @Override
    public List<Shape> draw(){
        return bus;
    }
}
