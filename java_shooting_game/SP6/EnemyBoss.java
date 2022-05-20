import java.awt.*;

public class EnemyBoss extends Enemy {
    private double vel_max=2;
    private int[][] cannons={
        {-75,-10},{-25,-16},{25,-16},{75,-10},
        {-75,10},{-25,16},{25,16},{75,10},
    };
    private double vx,vy;
    private int lap;
    private int life;

    public EnemyBoss(int w,int h) {
        px=w/2;
        py=-30;
        setSize(50,20);
        double tx=Math.random()*w;
        double ty=Math.random()*h;
        double dx=tx-px;
        double dy=ty-py;
        double d=Math.sqrt(dx*dx+dy*dy);
        vx=dx*vel_max/d;
        vy=dy*vel_max/d;
        lap=0;
        life=100;
    }

    public void draw(Graphics g){
        g.setColor(new Color(200,200,0));
        for(int i=0;i<cannons.length;i++){
            int x1=(int)px+cannons[i][0]-10;
            int y1=(int)px+cannons[i][1]-10;
            g.fillOval(x1,y1,20,20);
        }
        g.setColor(new Color(0,200,0));
        g.fillOval((int)px-100,(int)py-20,200,40);
        int r=255-(lap%10)*20;
        g.setColor(new Color(r,0,0));
        g.fillOval((int)px-50,(int)py-10,100,20);

        if(life>0){
            if(life>50) g.setColor(new Color(0,255,0,128));
            else if(life>20) g.setColor(new Color(255,255,0,128));
            else g.setColor(new Color(255,0,0,128));
            int w=400*life/100;
            g.fillRect(150,20,400,10);
        }
        g.setColor(Color.white);
        g.drawRect(150,20,400,10);
    }

    public boolean move(int w, int h, double sx, double sy){
        if(!alive) return false;
        if(Math.random()<0.1){
            px+=vx;
            py+=vy;
            if(lap>50 && Math.random()<0.2){
                double tx=Math.random()*w;
                double ty=Math.random()*h;
                double dx=tx-px;
                double dy=ty-py;
                double d=Math.sqrt(dx*dx+dy*dy);
                vx=dx*vel_max/d;
                vy=dy*vel_max/d;
            }
        }
        lap++;
        return life>0;
    }

    private void dropBombs(double x,double y,double ta,double a,int n, GameManager gm){
        for(int i=0; i<n; i++){
            double da=a/n;
            double al=ta+(i-n/2.0)*da;
            double tx=x+Math.cos(al);
            double ty=y+Math.sin(al);
            gm.addBomb(new Bomb(x,y,tx,ty));
        }
    }

    public void dropBomb(double tx,double ty,GameManager gm){
        if(lap%20>0) return;
        double [] as={
            Math.PI*225/180,Math.PI*260/180,Math.PI*280/180,Math.PI*315/180,
            Math.PI*135/180,Math.PI*100/180,Math.PI*80/180,Math.PI*45/180,
        };
        for(int i=0;i<cannons.length;i++){
            dropBombs(px+cannons[i][0],py+cannons[i][1],as[i],Math.PI*45/180,5,gm);
        }
    }

    public void die(){
        life--;
    }
}
