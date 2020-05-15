//////////////////////////////////////////////////////////////////////
//      Authors: Petr Balazy(xbalaz10), Mojmir Kyjonka(xkyjon00)    //
//      Poject: Java - public transport simulation                  //
//      Description: Class Coordinate - makes from x, y coordinate. //
//////////////////////////////////////////////////////////////////////

public class Coordinate implements Cloneable{
    protected double x = 0;
    protected double y = 0;

    /**
     * Vrací hodnotu na ose x.
     * @return Souřadnice x.
     */
    public double getX(){
        return this.x;
    }

    /**
     * Vrací hodnotu na ose y.
     * @return Souřadnice y.
     */
    public double getY(){
        return this.y;
    }

    /**
     * Vytváří novou souřadnici.
     * @param x Hodnota x osy.
     * @param y Hodnota y osy.
     */
    public Coordinate (double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
