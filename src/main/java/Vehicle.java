//////////////////////////////////////////////////////////////////////
//      Authors: Petr Balazy(xbalaz10), Mojmir Kyjonka(xkyjon00)    //
//      Poject: Java - public transport simulation                  //
//      Description: Class vehicle - represents vehicle and its     //
//      information.                                                //
//////////////////////////////////////////////////////////////////////

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Vehicle implements draw_map{
    private Line line;
    private Coordinate actual_position;
    private Coordinate destination;
    private List<Shape> bus;
    private int index = 1;
    private Shape my_shape;
    private LocalTime start_time;

    static Vehicle defaultVehicle(Line id){
        return new Vehicle(id);
    }
    public Vehicle(Line id){
        this.line = id;

        // due to overriding stop position
        try {
            this.actual_position = (Coordinate) id.start_stop().clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("error with cloning");
        }

        this.destination = id.get_stop(index).get_coordinates();

        bus = new ArrayList<>();
        bus.add(new Circle(0,0, 8, Color.BLUE));
        this.my_shape = bus.get(bus.size() - 1);
    }

    boolean is_in_end() {
        return (index >= line.stop_count());
    }

    public void move(int multiplier) {

        while (true) {

            double distance_to_travel;
            double x_remain;
            double y_remain;

            if (index >= line.stop_count()) return;
            if (actual_position.x == destination.x && actual_position.y == destination.y) {
                index++;
                if (index >= line.stop_count()) return;
                destination = line.get_stop(index).get_coordinates();
            }

            double x_distance = Math.abs(this.actual_position.x - this.destination.x);
            double y_distance = Math.abs(this.actual_position.y - this.destination.y);
            double x_share = Math.abs(x_distance / (x_distance + y_distance));
            double y_share = Math.abs(y_distance / (x_distance + y_distance));

            double missing_to_one = 1 / (Math.pow(x_share, 2) + Math.pow(y_share, 2));
            x_share = Math.sqrt(x_share * x_share * missing_to_one);
            y_share = Math.sqrt(y_share * y_share * missing_to_one);

            x_share *= multiplier;
            y_share *= multiplier;

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
                x_remain = Math.abs(x_share) - Math.abs(x_distance);
            } else {
                x_remain = 0;
            }
            if (Math.abs(y_distance) < Math.abs(y_share)) {
                this.actual_position.y = this.destination.y;
                y_remain = Math.abs(y_share) - Math.abs(y_distance);
            } else {
                y_remain = 0;
            }

            distance_to_travel = Math.sqrt(x_remain * x_remain + y_remain * y_remain);
            if (distance_to_travel < 0.5) {
                break;
            } else {
                multiplier = (int) distance_to_travel;
            }
        }


        for (Shape shape : bus) {
            Platform.runLater(() -> shape.setLayoutX(this.actual_position.getX()));
            Platform.runLater(() -> shape.setLayoutY(this.actual_position.getY()));
        }

    }

    public void set_start_time(LocalTime time) {
        start_time = time;
    }

    public LocalTime get_start_time() {
        return start_time;
    }

    public Coordinate get_actual_position(){
        return this.actual_position;
    }

    public Shape getMy_shape(){
        return my_shape;
    }

    public Line getLine(){
        return this.line;
    }

    @Override
    public List<Shape> draw(){
        return bus;
    }
}

