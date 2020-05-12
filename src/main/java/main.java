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
        List<Street> street_list = parser.get_streets();
        List<Line> lines_list = parser.get_lines();

        List<draw_map> objects = new ArrayList<>();

        for (Street s: street_list) {
            objects.add(s);
        }
        for (Stop s: stop_list) {
            objects.add(s);
        }

        my_controller.draw_parts(objects);


        List<Line> Lines = new ArrayList<>();
        List<LocalTime> casy = new ArrayList<>();
        LocalTime cas1 = LocalTime.of(0,0,0);
        casy.add(cas1);
        cas1 = LocalTime.of(0,0,5);
        casy.add(cas1);
        cas1 = LocalTime.of(0,0,10);
        casy.add(cas1);
        Line l1 = Line.defaltLine("c. 23", 1, casy, stop_list);
        Lines.add(l1);

        List<LocalTime> casy2 = new ArrayList<>();
        LocalTime cas2 = LocalTime.of(0,0,5);
        casy.add(cas2);
        Line l2 = Line.defaltLine("c. 23", 1, casy2, stop_list);
        Lines.add(l2);

        my_controller.start_timer(Lines);
    }
}