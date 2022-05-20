import java.awt.*;
import java.util.*;

public class GameManager extends Thread {
    private DanmakuField df;
    private Ship ship;
    private ArrayList<Beam> beams;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bomb> bombs;
    private ArrayList<Explosion> explosions;
    private ArrayList<Shield> shields;
    private ArrayList<Msg> msgs;

    public GameManager(DanmakuField df){
        this.df=df;
        ship=new Ship(300,500);
        beams=new ArrayList<Beam>();
        enemies=new ArrayList<Enemy>();
        bombs=new ArrayList<Bomb>();
        explosions=new ArrayList<Explosion>();
        shields=new ArrayList<Shield>();
        msgs=new ArrayList<Msg>();
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
            if(t%30==0) spreadAllExplosons();
            if(t%10==0) spreadAllShields();
            if(t%5==0) collideBE();
            if(t%10==0) collideBS();
            if(t%30==0) playAllMessages();
            if(t%200==0){
                switch((int)(Math.random()*3)){
                case 0: enemies.add(new Enemy1(w,h));
                case 1: enemies.add(new Enemy2(w,h,sx,sy)); break;
                case 2: enemies.add(new Enemy3(w,h,sx,sy)); break;
                }
            }
            if(t==5000){
                enemies.add(new EnemyBoss(w,h));
            }
            df.repaint();
            try{
                sleep(2);
            }catch(InterruptedException e){}
            t++;
        }
    }

    public void draw(Graphics g){
        int w=df.getWidth(),h=df.getHeight();
        for(Beam b:beams)b.draw(g); //すべてのビームを描画
        for(Bomb b:bombs)b.draw(g); //すべての爆弾を描画
        for(Enemy e:enemies)e.draw(g); //すべての敵を描画
        for(Explosion e:explosions)e.draw(g); //すべての爆発を描画
        for(Shield s:shields)s.draw(g); //すべてのシールドを描画
        ship.draw(g); //シップを描画
        for(Msg m:msgs) m.draw(g,w,h); //すべてのメッセージを描画
        g.setColor(Color.white);
        g.drawString("Beams:"+beams.size(),10,550);
        g.drawString("Bombs:"+bombs.size(),10,540);
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
    private void spreadAllExplosons(){
        Iterator<Explosion> it=explosions.iterator();
        while(it.hasNext()){
            Explosion e=it.next();
            if(!e.spread()){
                it.remove();
            }
        }
    }
    private void spreadAllShields() {
        Iterator<Shield> it=shields.iterator();
        while(it.hasNext()){
            Shield s=it.next();
            if(!s.spread()){
                it.remove();
            }
        }
    }
    private void collideBE() {
        for(Beam b:beams){
            for(Enemy e:enemies){
                if(b.overlap(e)){
                    e.die();
                    b.die();
                    double x=e.getX(),y=e.getY();
                    explosions.add(new Explosion(x,y));
                }
            }
        }
    }
    private void collideBS(){
        for(Bomb b:bombs){
            if(b.overlap(ship)){
                b.die();
                double x=ship.getX(),y=ship.getY();
                shields.add(new Shield(x,y));
            }
        }
    }
    //すべてのメッセージを動作
    private void playAllMessages(){
        Iterator<Msg> it=msgs.iterator();
        while(it.hasNext()){
            Msg m=it.next();
            if(!m.play()){
                it.remove();
            }
        }
    }
}