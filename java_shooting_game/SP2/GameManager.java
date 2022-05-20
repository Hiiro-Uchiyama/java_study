import java.awt.*;
import java.util.*;

public class GameManager extends Thread {
    private DanmakuField df;
    private Ship ship;
    private ArrayList<Beam> beams;
    public GameManager(DanmakuField df){
        this.df=df;
        ship=new Ship(300,500);
        beams=new ArrayList<Beam>();
        df.addMouseListener(ship);
        df.addMouseMotionListener(ship);
    }
    public void run(){
        long t=0;
        while(true){
            if(t%5==0) moveAllBeams();
            if(t%10==0) ship.move();
            if(t%30==0) ship.shot(this);
            df.repaint();
            try{
                sleep(2);
            }catch(InterruptedException e){}
            t++;
        }
    }
    public void draw(Graphics g){
        for(Beam b:beams)b.draw(g);
        ship.draw(g);
        g.setColor(Color.white);
        g.drawString("Beams:"+beams.size(),10,550);
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
}