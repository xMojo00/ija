import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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

    @FXML
    private Pane map;

    @FXML
    Label watch;

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

    public void start_timer(List<Vehicle> veh) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                set_time();
                veh.get(0).move();
                veh.get(1).move();
                time = time.plusSeconds(1);
            }
        }, 0, 1000);

    }

    public void set_time(){
        String time_to_text = String.format("%02d:%02d:%02d", time.getHour(),time.getMinute(),time.getSecond());
        Platform.runLater(() -> watch.setText(time_to_text));
    }
}
