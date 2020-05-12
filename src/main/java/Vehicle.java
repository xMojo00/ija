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
        return new Vehicle(id);
    }
    public Vehicle(Line id){
        this.line = id;
        this.actual_position = id.start_stop();
        this.destination = id.get_stop(index).get_coordinates();

        bus = new ArrayList<>();
        bus.add(new Circle(0,0, 2, Color.BLUE));
    }

    boolean is_in_end() {
        return (index >= line.stop_count());
    }

    public void move(int multiplier){

        if (index >= line.stop_count()) return;
        if(actual_position.x == destination.x && actual_position.y == destination.y) {
            index++;
            if (index >= line.stop_count()) return;
            destination = line.get_stop(index).get_coordinates();
        }

        double x_distance = Math.abs(this.actual_position.x - this.destination.x);
        double y_distance = Math.abs(this.actual_position.y - this.destination.y);
        double x_share = Math.abs(x_distance / (x_distance + y_distance) * multiplier);
        double y_share = Math.abs(y_distance / (x_distance + y_distance) * multiplier);

        if ((this.actual_position.x > this.destination.x)) {
            this.actual_position.x -= (x_share);
        } else {
            this.actual_position.x += (x_share);
        }

        if ((this.actual_position.y > this.destination.y)) {
            this.actual_position.y -= (y_share);
        } else {
            this.actual_position.y += (y_share);
        }

        if (Math.abs(x_distance) < Math.abs(x_share)) {
            this.actual_position.x = this.destination.x;
        }
        if (Math.abs(y_distance) < Math.abs(y_share)) {
            this.actual_position.y = this.destination.y;
        }

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
