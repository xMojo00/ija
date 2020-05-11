import javafx.fxml.FXML;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class controller {
    private List<draw_map> part = new ArrayList<>();

    @FXML
    private Pane map;

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
}
