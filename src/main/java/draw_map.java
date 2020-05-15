//////////////////////////////////////////////////////////////////////
//      Authors: Petr Balazy(xbalaz10), Mojmir Kyjonka(xkyjon00)    //
//      Poject: Java - public transport simulation                  //
//      Description: interface draw_map - genaralize creating shapes//
//      as stops, vehicle and streets.                              //
//////////////////////////////////////////////////////////////////////

import javafx.scene.shape.Shape;

import java.util.List;

public interface draw_map {
    List<Shape> draw();
}
