import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Vehicle implements draw_map{
    private Line line;
    private Coordinate actual_position;
    private Coordinate destination;
    private List<Shape> bus;
    private int index = 1;

    static Vehicle defaultVehicle(Line id, Coordinate c){
        return new Vehicle(id, c);
    }
    public Vehicle(Line id, Coordinate c){
        this.line = id;
        this.actual_position = c;

        bus = new ArrayList<>();
        bus.add(new Circle(actual_position.getX(),actual_position.getY(), 2, Color.BLUE));
    }

    public Coordinate getCoordinate() {
        return this.actual_position;
    }

    public void set_destination ( Coordinate c){
        this.destination = c;
    }

    public void set_actual_position(Stop stop){
        this.actual_position = stop.get_coordinates();
    }

    public void move(){
        /////

        ///

        for(Shape shape : bus){
            shape.setLayoutX(this.actual_position.getX());
            shape.setLayoutY(this.actual_position.getY());
        }
    }

    @Override
    public List<Shape> draw(){
        return bus;
    }
}
