import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class parseInputData {

    private String get_data() {

        String path = parseInputData.class.getResource("parseInputData.class").toString().substring(6);
        path = path.substring(0, path.length() - 44);
        path = path + "data/data.txt";
        path = "/" + path;
        try (Scanner s = new Scanner(new File(path))) {
            String content = s.useDelimiter("\\Z").next();
            s.close();
            return content;
        } catch (java.io.FileNotFoundException e) {
            System.out.println("wrong file");
        }
        return null;
    }

    public parseInputData() {

    }

    public List<Street> get_streets(){

        List<Street> return_list = new ArrayList<>();
        String data = get_data();
        String line;
        data = data.substring(data.indexOf("#Street"));
        data = data.substring(data.indexOf("\n")+1);
        while (data.substring(0,1).compareTo("#") != 0) {
            line = data.substring(0,data.indexOf("\n"));
            int id = Integer.parseInt(line.substring(0,line.indexOf(";")));
            line = line.substring(line.indexOf(";")+1);
            String name = line.substring(0,line.indexOf(";"));
            line = line.substring(line.indexOf(";")+1);
            ArrayList<Coordinate> coordinates = new ArrayList<>();
            while (line.substring(0,1).compareTo(";") != 0) {

                int x = Integer.parseInt(line.substring(line.indexOf("[")+1,line.indexOf(",")));
                int y = Integer.parseInt(line.substring(line.indexOf(",")+1,line.indexOf("]")));
                line = line.substring(line.indexOf("]")+1);
                Coordinate coord = new Coordinate(x,y);
                coordinates.add(coord);
            }
            /////////////////////// stops dodelat zatim posilam prazdne
           // System.out.println(line);
           // System.out.println(name);
            ArrayList<Stop> return_stop = new ArrayList<>();
            data = data.substring(data.indexOf("\n")+1);
            Street return_street = new Street(id, name, coordinates, return_stop);
            return_list.add(return_street);
        }
        return return_list;
    }

    public List<Stop> get_stops(){

        List<Stop> return_list = new ArrayList<>();
        String data = get_data();
        String line;
        data = data.substring(data.indexOf("#Stop"));
        data = data.substring(data.indexOf("\n")+1);
        while (data.substring(0,1).compareTo("#") != 0) {
            line = data.substring(0,data.indexOf("\n"));
            int id = Integer.parseInt(line.substring(0,line.indexOf(";")));
            line = line.substring(line.indexOf(";")+1);
            String name = line.substring(0,line.indexOf(";"));
            line = line.substring(line.indexOf(";")+1);
            int x = Integer.parseInt(line.substring(line.indexOf("[")+1,line.indexOf(",")));
            int y = Integer.parseInt(line.substring(line.indexOf(",")+1,line.indexOf("]")));
            Coordinate coord = new Coordinate(x,y);
            data = data.substring(data.indexOf("\n")+1);
            Stop return_stop = new Stop(id, name, coord);
            return_list.add(return_stop);
        }
        return return_list;
    }

    public List<Line> get_lines(){
        return null;
    }
}
