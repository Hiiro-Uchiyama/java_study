import java.awt.*;
import java.util.*;

public class GameManager extends Thread {
    private DanmakuField df;
    private Ship ship;
    private ArrayList<Beam> beams;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bomb> bombs;
    public GameManager(DanmakuField df){
        this.df=df;
        ship=new Ship(300,500);
        beams=new ArrayList<Beam>();
        enemies=new ArrayList<Enemy>();
        bombs=new ArrayList<Bomb>();
        df.addMouseListener(ship);
        df.addMouseMotionListener(ship);
    }
    public void run(){
        long t=0;
        while(true){
            int w=df.getWidth(),h=df.getHeight();
            double sx=ship.getX(),sy=ship.getY();
            if(t%5==0) moveAllBeams();
            if(t%10==0) ship.move();
            if(t%30==0) ship.shot(this);
            if(t%20==0) moveAllEnemies(w,h,sx,sy);
            if(t%10==0) moveAllBombs(w,h);
            if(t%200==0){
                enemies.add(new Enemy1(w,h));
            }
            df.repaint();
            try{
                sleep(2);
            }catch(InterruptedException e){}
            t++;
        }
    }
    public void draw(Graphics g){
        for(Beam b:beams)b.draw(g);
        for(Bomb b:bombs)b.draw(g);
        for(Enemy e:enemies)e.draw(g);
        ship.draw(g);
        g.setColor(Color.white);
        g.drawString("Beams:"+beams.size(),10,535);
        g.drawString("Bombs:"+bombs.size(),10,565);
    }
    public void addBeam(Beam b){
        beams.add(b);
    }
    private void moveAllBeams(){
        Iterator<Beam> it=beams.iterator();
        while(it.hasNext()){
            Beam b=it.next();
            if(!b.move()){
                it.remove();
            }
        }
    }
    public void moveAllEnemies(int w, int h, double sx, double sy){
        Iterator<Enemy> it=enemies.iterator();
        while(it.hasNext()){
            Enemy e=it.next();
            e.dropBomb(sx,sy,this);
            if(!e.move(w,h,sx,sy)){
                it.remove();
            }
        }
    }
    public void moveAllBombs(int w, int h){
        Iterator<Bomb> it=bombs.iterator();
        while(it.hasNext()){
            Bomb b=it.next();
            if(!b.move(w,h)){
                it.remove();
            }
        }
    }
    public void addBomb(Bomb b){
        bombs.add(b);
    }
}