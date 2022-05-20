import java.awt.*;

public class Beam{
    private double px,py;
    private double vx,vy;
    public Beam(double x, double y){
        px=x;
        py=y;
        vx=0;
        vy=-8;
    }
    public boolean move(){
        px+=vx;
        py+=vy;
        if(py<-4){
            return false;
        }
        return true;
    }
    public void draw(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect((int)px-3,(int)py-4,6,8);
    }
}