import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//sudo apt install openjfx=8u161-b12-1ubuntu2 openjfx-source=8u161-b12-1ubuntu2 libopenjfx-java=8u161-b12-1ubuntu2 libopenjfx-jni=8u161-b12-1ubuntu2

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/layout.fxml"));
        BorderPane root = load.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


        parseInputData parser = new parseInputData();
        controller my_controller = load.getController();
        List<Stop> stop_list = parser.get_stops();
        List<Street> street_list = parser.get_streets(stop_list);
        List<Line> lines_list = parser.get_lines(stop_list);

        List<draw_map> objects = new ArrayList<>();

        for (Street s: street_list) {
            objects.add(s);
        }
        for (Stop s: stop_list) {
            objects.add(s);
        }
        my_controller.draw_parts(objects);

        my_controller.start_timer(lines_list);
    }
}