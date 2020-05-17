import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje jednu linku
 * @author Petr Balazy, Mojmír Kyjonka
 */
public class Line implements draw_map {
    private String name;
    private int line_id;
    private List<LocalTime> start_times;
    private List<Stop> stops;
    private List<Shape> my_shape = new ArrayList<>();

    /**
     * Vytvoří linku.
     * @param starts Seznam s časy, ve kterých linka vyjíždí.
     * @param stops Seznam zastávek.
     * @param name Název linky.
     * @param line_id ID linky.
     */
    public Line(List<LocalTime> starts, List<Stop> stops, String name, int line_id) {
        this.name = name;
        this.line_id = line_id;
        this.start_times = starts;
        this.stops = stops;
    }

    /**
     * Vrací seznam objektů typu tvar.
     * @return Seznam objektů typu tvar.
     */
    public List<Shape> getMy_shape(){
        return this.my_shape;
    }

    /**
     * Vrací stopku na pozici indexu.
     * @param index Pozice stopky v seznamu.
     * @return Vrací stopku na indexu.
     */
    public Stop get_stop(int index) {
        return this.stops.get(index);
    }

    /**
     * Vrací seznam zastávek na lince.
     * @return Seznam zastávek.
     */
    public List<Stop> get_stops() {
        return this.stops;
    }

    /**
     * Vrací velikost seznamu, ve kterém jsou zastávky.
     * @return Velikost seznamu zastávek.
     */
    public int stop_count() {
        return stops.size();
    }

    /**
     * Vrací seznam s časy, kdy linka vyjíždí.
     * @return Seznam s časy.
     */
    public List<LocalTime> get_start_times(){
        return this.start_times;
    }

    /**
     * Vrací souřadnice zastávky, na které lisnka začíná.
     * @return Souřadnice první zastávky.
     */
    public Coordinate start_stop(){
        return this.stops.get(0).get_coordinates();
    }

    /**
     * Vrací název zastávky.
     * @return Název zastávky.
     */
    public String getName(){
        return this.name;
    }

    /**
     * Vrací seznam vytvořených tvarů.
     * @return Seznam objetků typu tvar.
     */
    @Override
    public List<Shape> draw(){
        my_shape.clear();
        for (int i = 0; i < get_stops().size() - 1; i++){
            javafx.scene.shape.Line l = new javafx.scene.shape.Line(get_stops().get(i).get_coordinates().getX(),
                    get_stops().get(i).get_coordinates().getY(),
                    get_stops().get(i+1).get_coordinates().getX(),
                    get_stops().get(i+1).get_coordinates().getY());
            l.setStrokeWidth(3);
            l.setStroke(Color.YELLOW);
            my_shape.add(l);
        }
        return my_shape;
    }
}
