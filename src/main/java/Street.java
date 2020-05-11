import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;

public class Street implements draw_map{
    private String street_id;
    ArrayList<Coordinate> co = new ArrayList<Coordinate>();
    ArrayList<Stop> stops = new ArrayList<Stop>();



    static Street defaultStreet​(java.lang.String id, Coordinate... coordinates){
        int counter = 0;

        while(counter != coordinates.length - 1){
            if(coordinates[counter].getX() != coordinates[counter + 1].getX() && coordinates[counter].getY() != coordinates[counter + 1].getY()){
                return null;
            }
            counter++;
        }
        return new Street(id, coordinates);
    }

    public Street(String id, Coordinate[] coordinates) {
       this.street_id = id;
       int counter = 0;

       while(counter != coordinates.length){
           co.add(coordinates[counter]);
           counter++;
       }
    }


    public java.lang.String getId() {
        return this.street_id;

    }

    public java.util.List<Coordinate> getCoordinates() {
        ArrayList<Coordinate> retco = new ArrayList<Coordinate>();
        int counter = 0;
        while (counter != co.size()) {
            retco.add(co.get(counter));
            counter++;
        }
        return retco;

    }

    public Coordinate begin() {
        return co.get(0);
    }

    public Coordinate end() {
        return co.get(co.size() - 1);
    }

    public java.util.List<Stop> getStops() {
        return stops;
    }

    public boolean addStop​(Stop stop) {
        Coordinate tmp = stop.getCoordinate();
        int counter = 0;
        while (counter + 1 != co.size()) {
            if ((co.get(counter).getX() == co.get(counter + 1).getX()) && (co.get(counter).getX() == tmp.getX())) {
                if ((tmp.getY() >= co.get(counter).getY()) && (tmp.getY() <= co.get(counter + 1).getY()) ||
                        (tmp.getY() <= co.get(counter).getY()) && (tmp.getY() >= co.get(counter + 1).getY())) {
                    stops.add(stop);
                    stop.setStreet​(this);
                    return true;
                }
            }
            if ((co.get(counter).getY() == co.get(counter + 1).getY()) && (co.get(counter).getY() == tmp.getY())) {
                if ((tmp.getX() >= co.get(counter).getX()) && (tmp.getX() <= co.get(counter + 1).getX()) ||
                        (tmp.getX() <= co.get(counter).getX()) && (tmp.getX() >= co.get(counter + 1).getX())) {
                    stops.add(stop);
                    stop.setStreet​(this);
                    return true;
                }
            }
            counter++;
        }
        return false;

    }

    public boolean follows​(Street s) {
        /*if(this.begin().equals(s.begin()) || this.begin().equals(s.end()) || this.end().equals(s.begin()) || this.end().equals(s.end())){
            return true;
        }
        return false;*/
        if ((this.begin().getX() == s.begin().getX() && this.begin().getY() == s.begin().getY()) ||
                (this.begin().getX() == s.end().getX() && this.begin().getY() == s.end().getY()) ||
                (this.end().getX() == s.begin().getX() && this.end().getY() == s.begin().getY()) ||
                (this.end().getX() == s.end().getX()) && this.end().getY() == s.end().getY()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Shape> draw(){
        List<Shape> sss = new ArrayList<Shape>();
        int counter = 0;
        while (counter != co.size() - 1) {
            sss.add(new Line(co.get(counter).getX(), co.get(counter).getY(), co.get(counter+1).getX(), co.get(counter+1).getY()));
            counter++;
        }
        return sss;
    }
}
