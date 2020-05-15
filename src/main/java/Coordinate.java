//////////////////////////////////////////////////////////////////////
//      Authors: Petr Balazy(xbalaz10), Mojmir Kyjonka(xkyjon00)    //
//      Poject: Java - public transport simulation                  //
//      Description: Class Coordinate - makes from x, y coordinate. //
//////////////////////////////////////////////////////////////////////

public class Coordinate implements Cloneable{
    protected double x = 0;
    protected double y = 0;


    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public static Coordinate create(double x, double y) {
        if(x < 0){
            return null;
        }
        if(y < 0){
            return null;
        }
        return new Coordinate(x,y);
    }

    public Coordinate (double x, double y){
        this.x = x;
        this.y = y;
    }

    public String print(){
        return x + "," + y;
    }


    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }
}
