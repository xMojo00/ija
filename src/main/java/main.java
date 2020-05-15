//////////////////////////////////////////////////////////////////////
//      Authors: Petr Balazy(xbalaz10), Mojmir Kyjonka(xkyjon00)    //
//      Poject: Java - public transport simulation                  //
//      Description:

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//sudo apt install openjfx=8u161-b12-1ubuntu2 openjfx-source=8u161-b12-1ubuntu2 libopenjfx-java=8u161-b12-1ubuntu2 libopenjfx-jni=8u161-b12-1ubuntu2

public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader load = new FXMLLoader(getClass().getResource("/layout.fxml"));
        primaryStage.setTitle("Simulace hromadne dopravy");
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

        //// jizdni rady

        for (Line line: lines_list) {
            List<Integer> visited = new ArrayList<>();
            Coordinate old = line.get_stop(0).get_coordinates();
            Coordinate current =  line.get_stop(0).get_coordinates();
            int delay = 0;
                for (Stop stops: line.get_stops()) {
                    BusTimetable timetable = new BusTimetable(line.getName());
                    current = stops.get_coordinates();


                    double x_distance = Math.pow(Math.abs(old.x - current.x),2);
                    double y_distance = Math.pow(Math.abs(old.y - current.y),2);
                    double distance = Math.sqrt(x_distance + y_distance);
                    delay = (int) ((delay * 1.0004) + distance);

                    old = stops.get_coordinates();
                    for (LocalTime t : line.get_start_times()) {
                        timetable.addTime(t.plusSeconds(delay));
                    }
                    if (!visited.contains(stops.get_id())) {
                        stops.add_timetable(timetable);
                        visited.add(stops.get_id());
                    }
                }
        }

        ////

        for (Street s: street_list) {
            objects.add(s);
        }
        for (Stop s: stop_list) {
            if (!(s.is_corner())) {
                objects.add(s);
            }
        }
        my_controller.draw_parts(objects);

        for (Street s : street_list) {
            for (Shape shape:s.getMy_shape()) {
                shape.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        my_controller.setInfo_panel_street(s);
                    }
                });
            }
        }

        for (Stop stop : stop_list) {
            if(!(stop.is_corner())) {
                stop.getMy_shape().setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        my_controller.setInfo_panel_stop(stop);
                    }
                });
            }
        }

        my_controller.set_time_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                my_controller.setTo_user_time();
            }
        });

        my_controller.setLines(lines_list);
        my_controller.start_timer();
    }
}