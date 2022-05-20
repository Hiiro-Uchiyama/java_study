import java.awt.*;

public abstract class GameChar {
    protected double px,py;
    private double cw,ch;
    public GameChar(){
        this(0,0);
    }
    public GameChar(double x, double y){
        px=x;py=y;
        cw=ch=0;
    }
    public void setSize(double w,double h){
        cw=w;
        ch=h;
    }
    public double getX(){return px;}
    public double getY(){return py;}
}
