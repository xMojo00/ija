import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje jednu zastávku
 * @author Petr Balazy, Mojmír Kyjonka
 */
public class Stop implements draw_map {
    private int stop_id;         //street id
    private String stop_name;   //ulice
    private Coordinate xy;          //souradnice zastavky
    private Boolean corner = false;
    private Shape my_shape;
    private ArrayList<BusTimetable> timetables = new ArrayList<>();
    private List<Street> streets;

    /**
     * Vrací souřadnici na, kterých zastávka leží.
     * @return Souřadnici zastávky.
     */
    public Coordinate get_coordinates(){
        return this.xy;
    }

    /**
     * Vrací ID zastávky.
     * @return ID zastávky.
     */
    public int get_id() {
        return this.stop_id;
    }

    /**
     * Vytváří novou zastávku.
     * @param id ID zastávky.
     * @param name Název zastávky.
     * @param c Souřadnice zastávky.
     */
    public Stop(int id, String name, Coordinate c){
        this.stop_id = id;
        this.stop_name = name;
        if (name.substring(0,1).compareTo("#") == 0) corner = true;
        this.xy = c;
    }

    /**
     * Vloží objekt t do seznamu timetable.
     * @param t Objekt reprezentující časy příjezdů na zastávku.
     */
    public void add_timetable(BusTimetable t){
        timetables.add(t);
    }

    public void clear_timetable(){
        timetables.clear();
    }

    /**
     * Vrací seznam příjezdů linek na stopce.
     * @return Seznam příjezdů linek
     */
    public ArrayList<BusTimetable> get_timetables() {
        return timetables;
    }

    /**
     * Vrací název zastávky.
     * @return Název zastávky.
     */
    public String getStop_name(){
        return this.stop_name;
    }

    /**
     * Vrací seznam objektů typu tvar.
     * @return Seznam objektů typu tvar.
     */
    public Shape getMy_shape(){
        return my_shape;
    }

    /**
     * Vrací pravdivostní hodnotu jestli se jedná o roh.
     * @return Pravdivostní hodnotu.
     */
    public boolean is_corner() {
        return this.corner;
    }

    /**
     * Vrací seznam vytvořených tvarů.
     * @return Seznam objetků typu tvar.
     */
    @Override
    public List<Shape> draw(){
        List<Shape> tmp_list = new ArrayList<>();
        tmp_list.add(new Circle(xy.getX(),xy.getY(), 6, Color.RED));
        this.my_shape = tmp_list.get(tmp_list.size() - 1);
        return tmp_list;
    }

    public void set_street_list(List<Street> list) {
        this.streets = list;
    }

    public List<Street> get_street_list() {
        return this.streets;
    }
}
