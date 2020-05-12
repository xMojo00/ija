import java.io.*;
import java.util.List;
import java.util.Scanner;

public class parseInputData {

    private String get_data() {
        try {
            String path = parseInputData.class.getResource("parseInputData.class").toString().substring(6);
            path = path.substring(0, path.length() - 44);
            path = path + "data/data.json";
            path = "/" + path;
            String content = new Scanner(new File(path)).useDelimiter("\\Z").next();
            return content;
        } catch (java.io.FileNotFoundException e) {
            System.out.println("wrong file");
        }

        return null;
    }

    public parseInputData() {

    }

    public List<Stop> get_stops(){

        String data = get_data();
        System.out.print(data);

        return null;
    }
}
