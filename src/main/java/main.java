import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        c1 = Coordinate.create(15,80);
        c2 = Coordinate.create(80,80);
        c3 = Coordinate.create(80,150);
        c4 = Coordinate.create(80,30);
        c5 = Coordinate.create(200,30);
        st1 = Coordinate.create(30,80);
        Street s1 = Street.defaultStreet​("adsad", c1, c2, c3);
        Street s2 = Street.defaultStreet​("adsadd", c3, c4, c5);
        Stop stop1 = Stop.defaultStop​("stop1", st1);
        Vehicle veh = Vehicle.defaultVehicle​("asdddd", c4);
        veh.set_destination(c5);

       controller my_controller = load.getController();
       List<draw_map> objects = new ArrayList<>();
       objects.add(s1);
       objects.add(s2);
       objects.add(stop1);
       objects.add(veh);
       my_controller.draw_parts(objects);
    }

}