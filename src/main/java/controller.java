import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class controller {
    private LocalTime time = LocalTime.now();
    private LocalTime lastTick = LocalTime.now();
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Line> lines;
    private int var_time_speed = 1;
    private Timer timer;
    private List<draw_map> mark_line = new ArrayList<>();

    @FXML
    private Pane map;

    @FXML
    private AnchorPane info_panel;

    @FXML
    Label watch;

    @FXML
    Label name;

    @FXML
    Label stops;

    @FXML
    TextArea text;

    @FXML
    Button set_time_button;

    @FXML
    TextField hours;

    @FXML
    TextField minutes;

    @FXML
    TextField seconds;

    @FXML
    Slider time_speed_slider;

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

    @FXML
    private void speed_change(){
        var_time_speed = (int) time_speed_slider.getValue();
    }

    public void draw_parts(List<draw_map> part){
        for(draw_map draw_map : part){
            map.getChildren().addAll(draw_map.draw());
        }
    }


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

    public void setLines(List<Line> line){
        this.lines = line;
    }

    public void start_timer() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                set_time();
                for(int i = 0; i < lines.size(); i++){
                    List<LocalTime> start_times = lines.get(i).get_start_times();
                    for (LocalTime start_time : start_times) {
                        if ((lastTick.isBefore(start_time) && time.isAfter(start_time)) || time.equals(start_time)) {
                            Vehicle v = Vehicle.defaultVehicle(lines.get(i));
                            vehicles.add(v);
                            List<draw_map> part = new ArrayList<>();
                            part.add(v);
                            for(draw_map draw_map : part){
                                Platform.runLater(() -> map.getChildren().addAll(draw_map.draw()));
                            }
                            v.getMy_shape().setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    setInfo_panel_vehicle(v);
                                }
                            });
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

                for(int i = 0; i < vehicles.size(); i++){
                    vehicles.get(i).move(var_time_speed);
                }

                lastTick = time;
                time = time.plusSeconds(var_time_speed);
            }
        }, 0, 1000);
    }

    public void set_time(){
        String time_to_text = String.format("%02d:%02d:%02d", time.getHour(),time.getMinute(),time.getSecond());
        Platform.runLater(() -> watch.setText(time_to_text));
    }

    public void setInfo_panel_street(Street street){
        String stops_string = "";
        name.setText(street.get_street_name());
        for (Stop stop : street.getStops()) {
            if(!stop.is_corner()) {
                stops_string = stops_string + stop.getStop_name() + "\n";
            }
        }
        stops.setText("Zastavky:");
        text.setText(stops_string);
    }

    public void setInfo_panel_stop(Stop stop){
        String stops_string = "";

        name.setText(stop.getStop_name());

        for(BusTimetable t: stop.get_timetables()){
            stops_string = stops_string + t.getLine() + "\n" + t.getTime() + "\n";
        }

        stops.setText("Jizdni rad:");
        text.setText(stops_string);
    }

    public void setInfo_panel_vehicle(Vehicle vehicle){
        String stops_string = "";
        name.setText(vehicle.getLine().getName());
        for (Stop stop : vehicle.getLine().get_stops()) {
            if(!stop.is_corner()) {
                stops_string = stops_string + stop.getStop_name() + "\n";
            }
        }
        if(mark_line.size() < 1) {
            mark_line.add(vehicle.getLine());
            for (draw_map draw_map : mark_line) {
                map.getChildren().addAll(draw_map.draw());
            }
        }
        else{
            mark_line.add(vehicle.getLine());
            map.getChildren().removeAll(vehicle.getLine().getMy_shape());
            mark_line.clear();
        }

        for(int i = 0; i < vehicles.size(); i++) {
            List<draw_map> part = new ArrayList<>();
            part.add(vehicles.get(i));
            for (draw_map draw_map : part) {
                Platform.runLater(() -> map.getChildren().removeAll(draw_map.draw()));
            }
            for (draw_map draw_map : part) {
                Platform.runLater(() -> map.getChildren().addAll(draw_map.draw()));
            }

        }
        stops.setText("Zastavky:");
        text.setText(stops_string);
    }

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

    public void setTransport_by_time(){
        LocalTime tmp_time = LocalTime.of(time.getHour(),time.getMinute(),time.getSecond());
        tmp_time = tmp_time.minusHours(2);
        while(time.compareTo(tmp_time) != 0) {
            for (int i = 0; i < lines.size(); i++) {
                List<LocalTime> start_times = lines.get(i).get_start_times();
                for (LocalTime start_time : start_times) {
                    if(start_time.getHour() == tmp_time.getHour() && start_time.getMinute() == tmp_time.getMinute() && start_time.getSecond() == tmp_time.getSecond()) {
                        Vehicle v = Vehicle.defaultVehicle(lines.get(i));
                        vehicles.add(v);
                    }
                }
            }

            for (int i = 0; i < vehicles.size(); i++) {
                if (vehicles.get(i).is_in_end()) {
                    vehicles.remove(i);
                }
            }

            for (int i = 0; i < vehicles.size(); i++) {
                vehicles.get(i).move(1);
            }

            tmp_time = tmp_time.plusSeconds(1);
        }
        lastTick = tmp_time;
        for (Vehicle vehicle : vehicles) {
            List<draw_map> part = new ArrayList<>();
            part.add(vehicle);
            for(draw_map draw_map : part){
                map.getChildren().addAll(draw_map.draw());
                vehicle.getMy_shape().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        setInfo_panel_vehicle(vehicle);
                    }
                });
            }
        }
    }
}
