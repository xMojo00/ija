import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje jedno vozidlo.
 * @author Petr Balazy, Mojmír Kyjonka
 */
public class Vehicle implements draw_map {
    private Line line;
    private Coordinate actual_position;
    private Coordinate destination;
    private List<Shape> bus;
    private int index = 1;
    private Shape my_shape;
    private LocalTime start_time;
    private LocalTime time_delay = LocalTime.of(0,0,0);

    /**
     * Vytváří vozidlo.
     * @param id ID vozidla.
     * @return Vrací novou instanci vozidla.
     */
    static Vehicle defaultVehicle(Line id){
        return new Vehicle(id);
    }

    /**
     * Konstruktor - vytváří vozdilo.
     * @param id ID vozidla.
     */
    public Vehicle(Line id){
        this.line = id;

        // due to overriding stop position
        try {
            this.actual_position = (Coordinate) id.getStartStop().clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("error with cloning");
        }

        this.destination = id.getStop(index).get_coordinates();

        bus = new ArrayList<>();
        bus.add(new Circle(0,0, 8, Color.BLUE));
        this.my_shape = bus.get(bus.size() - 1);
    }


    public int get_index() {
        return index;
    }

    /**
     * Vrací pravdivostí hodnotu, jestli se vozidlo nachází na poslední zastávce.
     * @return PRavdivostní hodnota.
     */
    boolean is_in_end() {
        return (index >= line.stop_count());
    }

    /**
     * Vypočítá o kolik se má vozidlo pohnout a natavi ho na vypočtenou pozici.
     * @param multiplier Násobek pohybu (pokud je čas zrychlen).
     */
    public void move(int multiplier) {

        while (true) {
            List<Street> street_list = this.line.getStop(0).get_street_list();

            double distance_to_travel;
            double x_remain;
            double y_remain;

            if (index >= line.stop_count()) return;
            if (actual_position.x == destination.x && actual_position.y == destination.y) {
                index++;
                if (index >= line.stop_count()) return;
                destination = line.getStop(index).get_coordinates();
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

            /// pokud je na ulici tak zpomalit

            for (Street street:street_list) {

                if (street.street_colapse_level() == 1) continue;
                List<Coordinate> temp = new ArrayList<>();

                temp.addAll(street.get_coordinates());

                for (Stop stop:street.getStops()) {
                    temp.add(stop.get_coordinates());
                }

                boolean found = false;

                for (Coordinate co1:temp) {
                    if(found) break;
                    for (Coordinate co2:temp) {
                        if(found) break;
                        if((this.line.getStop(index).get_coordinates().x == co1.x &&
                            this.line.getStop(index).get_coordinates().y == co1.y &&
                            this.line.getStop(index-1).get_coordinates().x == co2.x &&
                            this.line.getStop(index-1).get_coordinates().y == co2.y) ||
                            (this.line.getStop(index-1).get_coordinates().x == co2.x &&
                            this.line.getStop(index-1).get_coordinates().y == co2.y &&
                            this.line.getStop(index).get_coordinates().x== co1.x &&
                            this.line.getStop(index).get_coordinates().y == co1.y)) {
                            found = true;
                        }

                    }
                }

                if(found){
                    x_share /= street.street_colapse_level();
                    y_share /= street.street_colapse_level();
                    this.time_delay = this.time_delay.plusSeconds((street.street_colapse_level()-1)*multiplier);
                    break;
                }


            }


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

    /**
     * Nastaví čas výjezdu vozidla.
     * @param time Čas výjezdu.
     */
    public void setStart_time(LocalTime time) {
        start_time = time;
    }

    /**
     * Vrací čas výjezdu.
     * @return Čas výjezdu.
     */
    public LocalTime getStart_time() {
        return start_time;
    }

    /**
     * Vrací seznam objektů typu tvar.
     * @return Seznam objektů typu tvar.
     */
    public Shape getMy_shape(){
        return my_shape;
    }

    /**
     * Vrací objekt linky vozidla.
     * @return Objekt linky.
     */
    public Line getLine(){
        return this.line;
    }

    public LocalTime getTime_delay(){
        return this.time_delay;
    }

    /**
     * Vrací seznam vytvořených tvarů.
     * @return Seznam objetků typu tvar.
     */
    @Override
    public List<Shape> draw(){
        return bus;
    }
}

