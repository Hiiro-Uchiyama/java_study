import java.awt.*;

public class Enemy3 extends Enemy {
    private double vel_max=3;
    private int life_min=40;
    private int life_max=120;

    private double vx,vy;
    private double size;
    private double angle;
    private int life;

    public Enemy3(int w,int h,double tx,double ty) {
        size=20;
        setSize(1.8*size,1.8*size);
        angle=0;
        switch((int)(Math.random()*4)){
        case 0:
            px=Math.random()*(w-20)+10;
            py=0;
            break;
        case 1:
            px=Math.random()*(w-20)+10;
            py=h;
            break;
        case 2:
            px=0;
            py=Math.random()*(h-20)+10;
            break;
        case 3:
            px=w;
            py=Math.random()*(h-20)+10;
            break;
        }
        double dx=tx-px, dy=ty-py;
        double d=Math.sqrt(dx*dx+dy*dy);
        vx=dx*vel_max/d;
        vy=dy*vel_max/d;
        life=(int)(Math.random()*(life_max-life_min))+life_min;
    }

    public void draw(Graphics g){
        int[] x={ 0,0,0,0,0 };
        int[] y={ 0,0,0,0,0 };
        for(int i=0;i<5;i++){
            double a=i*2*Math.PI/5;
            x[i]=(int)(px+size*Math.cos(a+angle));
            y[i]=(int)(py+size*Math.sin(a+angle));
        }
        g.setColor(Color.cyan);
        g.fillPolygon(x,y,5);
    }

    public boolean move(int w, int h, double tx, double ty){
        if(!alive) return false;
        if(Math.random()<0.1){
            vx=tx-px;
            vy=ty-py;
            double d=Math.sqrt(vx*vx+vy*vy);
            vx=vx*vel_max/d;
            vy=vy*vel_max/d;
        }
        px+=vx;
        py+=vy;
        angle+=Math.PI/36;
        life--;
        return life>0;
    }
    public void dropBomb(double sx,double sy,GameManager gm){
        if(Math.random()>0.01) return;
        double t=2*Math.PI/16;
        for(int i=0;i<=16;i++){
            double tx=px+Math.cos(t*i);
            double ty=px+Math.sin(t*i);
            gm.addBomb(new Bomb(px,py,tx,ty));
        }
    }
}
