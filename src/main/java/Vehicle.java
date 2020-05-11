import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Vehicle implements draw_map{
    private String vehicle_id;
    private Coordinate actual_position;
    private Coordinate destination;
    private List<Shape> bus;

    static Vehicle defaultVehicle(java.lang.String id, Coordinate c){
        return new Vehicle(id, c);
    }
    public Vehicle(String id, Coordinate c){
        this.vehicle_id = id;
        this.actual_position = c;

        bus = new ArrayList<>();
        bus.add(new Circle(actual_position.getX(),actual_position.getY(), 8, Color.BLUE));
    }

    public Coordinate getCoordinate() {
        return this.actual_position;
    }

    public void set_destination ( Coordinate c){
        this.destination = c;
    }

    @Override
    public List<Shape> draw(){
        return bus;
    }
}
