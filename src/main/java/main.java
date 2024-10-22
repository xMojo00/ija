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

/**
 * Spouští běh programu.
 * @author Petr Balazy, Mojmír Kyjonka
 */
public class main extends Application {
    List<Integer> unaviable_lines = new ArrayList<>();
    /**
     * Zapíná GUI.
     * @param primaryStage Hlavní stage.
     * @throws Exception Vyjimka.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader load = new FXMLLoader(getClass().getResource("layout.fxml"));
        primaryStage.setTitle("Simulace hromadne dopravy");
        BorderPane root = load.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        parseInputData parser = new parseInputData();
        controller my_controller = load.getController();
        List<Stop> stop_list = parser.getStops();
        List<Street> street_list = parser.getStreets(stop_list);
        List<Line> lines_list = parser.getLines(stop_list);
        List<draw_map> objects = new ArrayList<>();

        scheadule(lines_list);

        objects.addAll(street_list);

        for (Stop s: stop_list) {
            s.set_street_list(street_list);
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

        my_controller.corner_checkbox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                my_controller.draw_corners(stop_list);
            }
        });

        my_controller.update_line_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!my_controller.update_line_text.getText().isEmpty()) {
                    String tmp = my_controller.update_line_text.getText();
                    String data = tmp.substring(tmp.indexOf(";")+1);
                    tmp = tmp.substring(0,tmp.indexOf(";"));
                    for ( Line line : lines_list) {
                        if(Integer.parseInt(tmp) == line.getLine_id()){
                            line.change_route(stop_list ,data);
                        }
                    }
                for (Stop stops : stop_list) {
                    stops.clear_timetable();
                }
                scheadule(lines_list);

                unaviable_lines.remove(unaviable_lines.indexOf(Integer.parseInt(tmp)));
                my_controller.lines_to_update.setText("");
                for (int line:unaviable_lines) {
                    my_controller.lines_to_update.appendText("Linka " + line + "\n");
                }

                }
                if(unaviable_lines.isEmpty()){
                    my_controller.time_speed_slider.setValue(1);
                    my_controller.speed_change();
                }
            }
        });

        my_controller.close_street_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!my_controller.close_street.getText().isEmpty()) {
                    for (Street street : street_list) {
                        if(Integer.parseInt(my_controller.close_street.getText()) == street.getStreet_id()){
                            String info = street.disable_street(lines_list, unaviable_lines);
                            my_controller.lines_to_update.setText(info);
                            my_controller.time_speed_slider.setValue(0);
                            my_controller.speed_change();
                        }
                    }
                }
            }
        });

        my_controller.collapse_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(my_controller.collapse_level.getText().matches("^[1-5]$") && my_controller.collapse_street.getText().matches("^[0-9]+$")) {
                    for (Street street : street_list) {
                        if (Integer.parseInt(my_controller.collapse_street.getText()) == street.getStreet_id()) {
                            street.set_colapse_level(Integer.parseInt(my_controller.collapse_level.getText()));
                        }
                    }
                }
            }
        });

        my_controller.setLines(lines_list);
        my_controller.start_timer();
    }

    /**
     * Metoda main - nedělá nic.
     * @param args argumenty aplikace.
     */
    public static void main(String[] args){
        launch(args);
    }

    /**
     * Vypočítá čas příjezdu na zastávku.
     * @param lines_list Seznam linek.
     */
    public void scheadule(List<Line> lines_list){
        for (Line line: lines_list) {
            List<Integer> visited = new ArrayList<>();
            Coordinate old = line.getStop(0).get_coordinates();
            Coordinate current;
            int delay = 0;
            for (Stop stops : line.getStops()) {
                BusTimetable timetable = new BusTimetable(line.getName());
                current = stops.get_coordinates();

                double x_distance = Math.pow(Math.abs(old.x - current.x),2);
                double y_distance = Math.pow(Math.abs(old.y - current.y),2);
                double distance = Math.sqrt(x_distance + y_distance);
                delay = (int) ((delay * 1.0004) + distance);

                old = stops.get_coordinates();
                for (LocalTime t : line.getStart_times()) {
                    timetable.addTime(t.plusSeconds(delay));
                }
                if (!visited.contains(stops.get_id())) {
                    stops.add_timetable(timetable);
                    visited.add(stops.get_id());
                }
            }
        }
    }
}