public class Coordinate implements Cloneable{
    protected double x = 0;
    protected double y = 0;


    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public static Coordinate create(int x, int y) {
        if(x < 0){
            return null;
        }
        if(y < 0){
            return null;
        }
        return new Coordinate(x,y);
    }

    public Coordinate (int x, int y){
        this.x = x;
        this.y = y;
    }

    public String print(){
        return x + "," + y;
    }

    //@Override
    public boolean equals(java.lang.Object obj) {
        Coordinate temp = (Coordinate) obj;
        if(temp.getX() == this.getX() && temp.getY() == this.getY()){
            return true;
        }
        else{
            return false;
        }
    }

    public Object clone()throws CloneNotSupportedException{
        return super.clone();
    }
}
