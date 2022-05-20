import java.awt.*;

public class Enemy1 extends Enemy {
    private double vel_max=4;
    private double size_min=5,size_max=8;
    private double vx,vy;
    private double size;
    private double ds=1;

    public Enemy1(int w,int h) {
        size=(size_max+size_min)/2;
        setSize(2*size,2*size);
        px=Math.random()*w;
        py=0;
        double tx=Math.random()*w;
        double ty=h;
        double dx=tx-px;
        double dy=ty-py;
        double d=Math.sqrt(dx*dx+dy*dy);
        vx=dx*vel_max/d;
        vy=dy*vel_max/d;
    }
    public void draw(Graphics g){
        g.setColor(Color.green);
        g.fillRect((int)(px-size), (int)(py-size), (int)(2*size), (int)(2*size));
    }
    public boolean move(int w, int h, double tx, double ty){
        px+=vx;
        py+=vy;
        if(px<0||px>w){
            vx=-vx;
        }
        size+=ds;
        setSize(2*size,2*size);
        if(size<size_min||size>size_max){
            ds=-ds;
        }
        if(py>h){
            return false;
        }
        return true;
    }
    public void dropBomb(double sx,double sy,GameManager gm){
        if(Math.random()>0.05){
            return;
        }
        gm.addBomb(new Bomb(px,py,sx,sy));
    }
}
