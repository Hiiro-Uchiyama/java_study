import java.awt.*;

public class GameManager extends Thread {
    private DanmakuField df;
    private Ship ship;
    public GameManager(DanmakuField df){
        this.df=df;
        ship=new Ship(300,500);
        df.addMouseListener(ship);
        df.addMouseMotionListener(ship);
    }
    public void run(){
        long t=0;
        while(true){
            if(t%10==0) ship.move();
            df.repaint();
            try{
                sleep(2);
            }catch(InterruptedException e){}
            t++;
        }
    }
    public void draw(Graphics g){
        ship.draw(g);
    }
}
