import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * Reprezentuje jednu ulici.
 * @author Petr Balazy, Mojmír Kyjonka
 */
public class Street implements draw_map {
    private int street_id;
    private String street_name;
    private ArrayList<Coordinate> co;
    private ArrayList<Stop> stops;
    private List<Shape> my_shape = new ArrayList<>();
    private boolean blocked = false;
    private int street_colapse = 1;

    /**
     * Vytvoří novou ulici.
     * @param id ID ulice.
     * @param name Název ulice.
     * @param coordinates List souřadnic ulice.
     * @param stops List zastávek na ulici.
     */
    public Street(int id, String name, ArrayList<Coordinate> coordinates, ArrayList<Stop> stops) {
       this.street_id = id;
       this.street_name = name;
       this.co = coordinates;
       this.stops = stops;
    }

    /**
     * Vrátí název ulice.
     * @return Název ulice.
     */
    public String getStreet_name() {
        return this.street_name;
    }

    /**
     * Vrací list zastávek na ulici.
     * @return List zastávek.
     */
    public List<Stop> getStops() {
        return this.stops;
    }

    /**
     * Vrací seznam objektů typu tvar.
     * @return Seznam objektů typu tvar.
     */
    public List<Shape> getMy_shape() {
        return this.my_shape;
    }

    /**
     * Vrací ID ulice.
     * @return ID ulice.
     */
    public int getStreet_id() {
        return this.street_id;
    }

    /**
     * Vrací číselnou úroven provozu na ulici.
     * @return Úroven provozu.
     */
    public int street_colapse_level() {
        return this.street_colapse;
    }

    /**
     * Nastaví úroven provozu na ulici.
     * @param i Úroven provozu.
     */
    public void set_colapse_level(int i) {
        this.street_colapse = i;
    }

    /**
     * Vraci pravda jestliže je ulice uzavřena.
     * @return Pravda, jetliže je ulice uzavřena.
     */
    public boolean getBlocked(){
        return blocked;
    }

    /**
     * Vrací souřadnice ulice.
     * @return Souřadnice ulice.
     */
    public ArrayList<Coordinate> get_coordinates() {
        return this.co;
    }

    /**
     * Vrací seznam vytvořených tvarů.
     * @return Seznam objetků typu tvar.
     */
    @Override
    public List<Shape> draw() {
        List<Shape> tmp_list = new ArrayList<>();
        int counter = 0;
        while (counter != co.size() - 1) {
            tmp_list.add(new javafx.scene.shape.Line(co.get(counter).getX(), co.get(counter).getY(), co.get(counter + 1).getX(), co.get(counter + 1).getY()));
            this.my_shape.add(tmp_list.get(tmp_list.size() - 1));
            tmp_list.get(tmp_list.size() - 1).setStrokeWidth(1.5);
            counter++;
        }
        return tmp_list;
    }

    /**
     * Uzavře ulici a vrací řetězec s linkama, které se musí upravit.
     * @param lines Seznam linek.
     * @param closed_lines Seznam uzavřených linek.
     * @return Retězec linek.
     */
    public String disable_street(List<Line> lines, List<Integer> closed_lines) {
        List<Coordinate> temp = new ArrayList<>();

        if(blocked){
            blocked = false;
            return "";
        }

        temp.addAll(this.co);

        for (Stop stop:this.stops) {
            temp.add(stop.get_coordinates());
        }

        String s = "";

        for (Line line:lines) {
            boolean found = false;
            for (int i = 0; i + 1 < line.stop_count(); i++) {
                if(found) break;
                for (Coordinate co1:temp) {
                    if(found) break;
                    for (Coordinate co2:temp) {
                        if(found) break;
                        if(((line).getStop(i).get_coordinates().x == co1.x &&
                                line.getStop(i).get_coordinates().y == co1.y &&
                                line.getStop(i+1).get_coordinates().x == co2.x &&
                                line.getStop(i+1).get_coordinates().y == co2.y) ||
                                ((line).getStop(i).get_coordinates().x == co2.x &&
                                line.getStop(i).get_coordinates().y == co2.y &&
                                line.getStop(i+1).get_coordinates().x == co1.x &&
                                line.getStop(i+1).get_coordinates().y == co1.y)) {
                            if (!closed_lines.contains(line.getLine_id())) {
                                closed_lines.add(line.getLine_id());
                            }
                            found = true;
                        }

                    }
                }
            }
        }
        for (Integer line: closed_lines) {
            s = s.concat("Linka ").concat(Integer.toString(line)).concat("\n");
        }

        blocked = true;
        return s;
    }
}
