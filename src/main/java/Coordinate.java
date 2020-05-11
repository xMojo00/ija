public class Coordinate {
    protected int x = 0;
    protected int y = 0;


    public int getX(){
        return this.x;
    }

    public int getY(){
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
}
