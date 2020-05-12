import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class controller {
    private LocalTime time = LocalTime.of(0,0,0);
    private List<draw_map> part = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();
    private int var_time_speed = 1;

    @FXML
    private Pane map;

    @FXML
    Label watch;

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
        System.out.println(var_time_speed);
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
                    for(int j = 0; j < start_times.size(); j++){
                        if(time.getHour() == start_times.get(j).getHour() && time.getMinute() == start_times.get(j).getMinute() && time.getSecond() == start_times.get(j).getSecond()){
                            vehicles.add(Vehicle.defaultVehicle(lines.get(i).get_line_id(), lines.get(i).start_stop()));
                        }
                    }
                }

                time = time.plusSeconds(var_time_speed);
            }
        }, 0, 1000);
    }

    public void set_time(){
        String time_to_text = String.format("%02d:%02d:%02d", time.getHour(),time.getMinute(),time.getSecond());
        Platform.runLater(() -> watch.setText(time_to_text));
    }
}
