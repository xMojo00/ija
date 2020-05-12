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

        Coordinate c1, c2, c3, c4, c5, st1;
        parseInputData parser = new parseInputData();
        c1 = Coordinate.create(50,80);
        c2 = Coordinate.create(200,80);
        c3 = Coordinate.create(80,150);
        c4 = Coordinate.create(80,30);
        c5 = Coordinate.create(200,100);
        st1 = Coordinate.create(30,80);
        Street s1 = Street.defaultStreet("ulice 1", c1, c2);
        Street s2 = Street.defaultStreet("adsadd", c3, c5);
        Stop stop1 = Stop.defaultStop("stop1", c1);
        Stop stop2 = Stop.defaultStop("stop1", c2);
        //Vehicle veh = Vehicle.defaultVehicle("audi", c2);
        //Vehicle veh1 = Vehicle.defaultVehicle("audi1", c3);
        //veh.set_destination(c5);
        controller my_controller = load.getController();

       //data
        List<draw_map> objects = new ArrayList<>();
        List<draw_map> objectss = new ArrayList<>();
        List<Line> Lines = new ArrayList<>();
        List<LocalTime> casy = new ArrayList<>();
        List<LocalTime> casyy = new ArrayList<>();
        List<Vehicle> vehs = new ArrayList<>();
        List<Stop> stopss = new ArrayList<>();

        LocalTime cas1 = LocalTime.of(0,0,0);
        LocalTime cas2 = LocalTime.of(0,0,10);
        casy.add(cas1);
        stopss.add(stop1);
        stopss.add(stop2);
        Line l1 = Line.defaltLine("c. 23", 1, casy, stopss);
        casyy.add(cas2);
        Line l2 = Line.defaltLine("c. 23", 1, casyy, stopss);
        Lines.add(l1);
        Lines.add(l2);
        objects.add(s1);
        objects.add(s2);
        objects.add(stop1);
        objects.add(stop2);


       my_controller.draw_parts(objects);

       List<Stop> stop_list = parser.get_stops();
       //System.out.print("stop_list");
       //System.out.print(stop_list);

       my_controller.start_timer(Lines);
    }
}