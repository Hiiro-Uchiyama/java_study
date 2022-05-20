import java.awt.*;

public abstract class Enemy extends GameChar {
    public abstract void draw(Graphics g);
    public abstract boolean move(int w,int h,double tx, double ty);
    public abstract void dropBomb(double sx,double sy,GameManager gm);
}
