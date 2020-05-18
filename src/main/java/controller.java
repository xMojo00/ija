import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Reprezentuje controler
 * @author Petr Balazy, Mojmír Kyjonka
 */
public class controller {
    private LocalTime time = LocalTime.now().withNano(0);
    private LocalTime lastTick = LocalTime.now().withNano(0);
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Line> lines;
    private int var_time_speed = 1;
    private Timer timer;
    private List<draw_map> mark_line = new ArrayList<>();
    private boolean first_start = true;
    private List<Shape> corner_list = new ArrayList<Shape>();

    @FXML
    private Pane map;

    @FXML
    private AnchorPane info_panel;

    @FXML
    private Label watch;

    @FXML
    private Label name;

    @FXML
    private Label stops;

    @FXML
    private TextArea text;

    @FXML
    Button set_time_button;

    @FXML
    private TextField hours;

    @FXML
    private TextField minutes;

    @FXML
    private TextField seconds;

    @FXML
    private Slider time_speed_slider;

    @FXML
    private TextField close_street;

    @FXML
    Button close_street_button;

    @FXML
    CheckBox corner_checkbox;




    /**
     * Zprostředkovává zoom v aplikaci.
     * @param event ScrollEvent
     */
    @FXML
    private void zoom(ScrollEvent event){
        event.consume();
        if(event.getDeltaY() > 0){
            Platform.runLater(() -> map.setScaleX(1.1 * map.getScaleX()));
            Platform.runLater(() -> map.setScaleY(1.1 * map.getScaleY()));
        }
        else{
            Platform.runLater(() -> map.setScaleX(0.9 * map.getScaleX()));
            Platform.runLater(() -> map.setScaleY(0.9 * map.getScaleY()));
        }
    }

    /**
     * Nastavuje hodnotu proměnné na hodnotu nastavenou na posuvníku.
     */
    @FXML
    private void speed_change(){
        var_time_speed = (int) time_speed_slider.getValue();
    }

    /**
     * Vykresluje tvary do okna aplikace.
     * @param part Seznam tvarů.
     */
    public void draw_parts(List<draw_map> part){
        for(draw_map draw_map : part){
            map.getChildren().addAll(draw_map.draw());
        }
    }

    /**
     * Odstraní všechna vozidla z mapy aplikace.
     */
    public void remove_all_vehicles(){
        int size = vehicles.size();
        for(int i = 0; i < size; i++) {
            List<draw_map> veh = new ArrayList<>();
            veh.add(vehicles.get(0));
            for (draw_map draw_map : veh) {
                Platform.runLater(() -> map.getChildren().removeAll(draw_map.draw()));
            }
            vehicles.remove(0);
        }
    }

    /**
     * Nastaví linky.
     * @param line Seznam linek.
     */
    public void setLines(List<Line> line){
        this.lines = line;
    }

    /**
     * Spustí časovač a začne vytvářet vozidla podle čassů jejich výjezdů. Po příjezdu na poslední zastávku je i odstraní.
     */
    public void start_timer() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setTime();
                if (first_start){
                    first_start = false;
                    Platform.runLater(() -> setTransport_by_time());
                }
                for (Line line : lines) {
                    List<LocalTime> start_times = line.getStart_times();
                    for (LocalTime start_time : start_times) {
                        if ((lastTick.isBefore(start_time) && time.isAfter(start_time)) || time.equals(start_time)) {
                            Vehicle v = Vehicle.defaultVehicle(line);
                            v.setStart_time(start_time);
                            vehicles.add(v);
                            List<draw_map> part = new ArrayList<>();
                            part.add(v);
                            for (draw_map draw_map : part) {
                                Platform.runLater(() -> map.getChildren().addAll(draw_map.draw()));
                            }
                            v.getMy_shape().setOnMouseClicked(event -> setInfo_panel_vehicle(v));
                        }
                    }
                }

                for(int i = 0; i < vehicles.size(); i++) {
                    if(vehicles.get(i).is_in_end()) {
                        List<draw_map> part = new ArrayList<>();
                        part.add(vehicles.get(i));
                        for (draw_map draw_map : part) {
                            Platform.runLater(() -> map.getChildren().removeAll(draw_map.draw()));
                        }
                        vehicles.remove(i);
                    }
                }

                for (Vehicle vehicle : vehicles) {
                    vehicle.move(var_time_speed);
                }

                lastTick = time;
                time = time.plusSeconds(var_time_speed);
            }
        }, 0, 1000);
    }

    /**
     * Nastaví a zobrazí čas v aplikaci.
     */
    public void setTime(){
        String time_to_text = String.format("%02d:%02d:%02d", time.getHour(),time.getMinute(),time.getSecond());
        Platform.runLater(() -> watch.setText(time_to_text));
    }

    /**
     * Zobrazí informace o ulici.
     * @param street Objekt ulice.
     */
    public void setInfo_panel_street(Street street){
        StringBuilder stops_string = new StringBuilder();
        name.setText(street.getStreet_name() + " - " + street.getStreet_id());
        for (Stop stop : street.getStops()) {
            if(!stop.is_corner()) {
                stops_string.append(stop.getStop_name()).append("\n");
            }
        }
        stops.setText("Zastavky:");
        text.setText(stops_string.toString());
    }

    /**
     * Zobrazí informace o zastávce.
     * @param stop Objekt zastávky.
     */
    public void setInfo_panel_stop(Stop stop){
        StringBuilder stops_string = new StringBuilder();

        name.setText(stop.getStop_name());

        for(BusTimetable t: stop.get_timetables()){
            stops_string.append(t.getLine()).append("\n").append(t.getTime()).append("\n");
        }

        stops.setText("Jizdni rad:");
        text.setText(stops_string.toString());
    }

    /**
     * Zobrazí informace o vozidle.
     * @param vehicle Objekt vozidla.
     */
    public void setInfo_panel_vehicle(Vehicle vehicle){
        StringBuilder stops_string = new StringBuilder();
        name.setText(vehicle.getLine().getName());
        int delay = 0;
        Coordinate old = vehicle.getLine().getStops().get(0).get_coordinates();
        Coordinate current;
        for (Stop stop : vehicle.getLine().getStops()) {
            current = stop.get_coordinates();
            double x_distance = Math.pow(Math.abs(old.x - current.x),2);
            double y_distance = Math.pow(Math.abs(old.y - current.y),2);
            double distance = Math.sqrt(x_distance + y_distance);
            delay = (int) ((delay * 1.0004) + distance);
            old = stop.get_coordinates();

            if(!stop.is_corner()) {
                stops_string.append(stop.getStop_name()).append("\n");
                stops_string.append(" ").append(vehicle.getStart_time().plusSeconds(delay)).append("\n");
            }
        }
        if(mark_line.size() < 1) {
            mark_line.add(vehicle.getLine());
            for (draw_map draw_map : mark_line) {
                map.getChildren().addAll(draw_map.draw());
            }
        }
        else{
            for (Vehicle veh : vehicles) {
                map.getChildren().removeAll(veh.getLine().getMy_shape());
            }
            mark_line.clear();
        }

        for (Vehicle value : vehicles) {
            List<draw_map> part = new ArrayList<>();
            part.add(value);
            for (draw_map draw_map : part) {
                Platform.runLater(() -> map.getChildren().removeAll(draw_map.draw()));
            }
            for (draw_map draw_map : part) {
                Platform.runLater(() -> map.getChildren().addAll(draw_map.draw()));
            }

        }
        stops.setText("Zastavky:");
        text.setText(stops_string.toString());
    }

    /**
     * Nastaví čas na čas zadaný uživatelem.
     */
    public void setTo_user_time(){
        if((hours.getText().matches("^[0-1]?[0-9]$|^[2]?[0-3]$"))){
            if(minutes.getText().matches("^[0-5]?[0-9]$")){
                if(seconds.getText().matches("^[0-5]?[0-9]$")){
                    remove_all_vehicles();
                    time = LocalTime.of(Integer.parseInt(hours.getText()),Integer.parseInt(minutes.getText()),Integer.parseInt(seconds.getText()));
                    timer.cancel();
                    setTransport_by_time();
                    start_timer();
                }
            }
        }
    }

    /**
     * Nastaví dopravní situaci, tak aby odpovídala času.
     */
    public void setTransport_by_time() {
        LocalTime tmp_time = LocalTime.of(time.getHour(),time.getMinute(),time.getSecond());
        tmp_time = tmp_time.minusHours(2);
        while(time.compareTo(tmp_time) != 0) {
            for (Line line : lines) {
                List<LocalTime> start_times = line.getStart_times();
                for (LocalTime start_time : start_times) {
                    if (start_time.getHour() == tmp_time.getHour() && start_time.getMinute() == tmp_time.getMinute() && start_time.getSecond() == tmp_time.getSecond()) {
                        Vehicle v = Vehicle.defaultVehicle(line);
                        v.setStart_time(start_time);
                        vehicles.add(v);
                    }
                }
            }

            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i).is_in_end()) {
                    vehicles.remove(i);
                }
            }

            for (Vehicle vehicle : vehicles) {
                vehicle.move(1);
            }

            tmp_time = tmp_time.plusSeconds(1);
        }
        lastTick = tmp_time;
        for (Vehicle vehicle : vehicles) {
            List<draw_map> part = new ArrayList<>();
            part.add(vehicle);
            for(draw_map draw_map : part){
                map.getChildren().addAll(draw_map.draw());
                vehicle.getMy_shape().setOnMouseClicked(event -> setInfo_panel_vehicle(vehicle));
            }
        }
    }

    void draw_corners(List<Stop> stops){
        if(corner_checkbox.isSelected()){
            for (Stop stop: stops) {
                Text t = new Text(stop.get_coordinates().getX() - 12, stop.get_coordinates().getY()+5, Integer.toString(stop.get_id()));
                t.setFont(Font.font("Verdana", FontWeight.BOLD, 19));
                map.getChildren().addAll(t);
                corner_list.add(t);
            }
        }
        else{
            map.getChildren().removeAll(corner_list);
        }
    }
}
