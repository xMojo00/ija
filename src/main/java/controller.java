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
    private LocalTime time = LocalTime.of(0,0,0);
    private LocalTime lastTick = LocalTime.of(0,0,0);
    private List<draw_map> part = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();
    private int var_time_speed = 1;

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
            map.setScaleX(1.1 * map.getScaleX());
            map.setScaleY(1.1 * map.getScaleY());
        }
        else{
            map.setScaleX(0.9 * map.getScaleX());
            map.setScaleY(0.9 * map.getScaleY());
        }
    }

    @FXML
    private void speed_change(){
        var_time_speed = (int) time_speed_slider.getValue();
    }

    public void draw_parts(List<draw_map> part){
        this.part = part;
        for(draw_map draw_map : part){
            map.getChildren().addAll(draw_map.draw());
        }
    }

    public void remove_parts(List<draw_map> part){
        this.part = part;
        for(draw_map draw_map : part){
            map.getChildren().removeAll(draw_map.draw());
        }
    }

    public void start_timer(List<Line> lines) {
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                set_time();
                for(int i = 0; i < lines.size(); i++){
                    List<LocalTime> start_times = lines.get(i).get_start_times();
                    for (LocalTime start_time : start_times) {
                        if ((lastTick.isBefore(start_time) && time.isAfter(start_time))|| time.equals(start_time)) {
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

                /*for (Vehicle vehicle : vehicles) {
                    System.out.println("asd");
                    vehicle.getMy_shape().setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            setInfo_panel_vehicle(vehicle);
                        }
                    });
                }*/
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
            stops_string = stops_string + stop.getStop_name() + "\n";
        }
        stops.setText("Zastávky:");
        text.setText(stops_string);
    }

    public void setInfo_panel_stop(Stop stop){
        String stops_string = "";

        name.setText(stop.getStop_name());
        /*for (Stop stop : street.getStops()) {
            stops_string = stops_string + stop.getStop_name() + "\n";
        }*/
        stops.setText("Jízdní řád:");
        text.setText(stops_string);
    }

    public void setInfo_panel_vehicle(Vehicle vehicle){
        String stops_string = "";

        name.setText(vehicle.getLine().getName());
        for (Stop stop : vehicle.getLine().get_stops()) {
            stops_string = stops_string + stop.getStop_name() + "\n";
        }
        stops.setText("Zastavky:");
        text.setText(stops_string);
    }

    public void setTo_user_time(){
        if((hours.getText().matches("^[0-1]?[0-9]$|^[2]?[0-3]$"))){
            if(minutes.getText().matches("^[0-5]?[0-9]$")){
                if(seconds.getText().matches("^[0-5]?[0-9]$")){
                    time = LocalTime.of(Integer.parseInt(hours.getText()),Integer.parseInt(minutes.getText()),Integer.parseInt(seconds.getText()));
                }
            }
        }
    }
}
