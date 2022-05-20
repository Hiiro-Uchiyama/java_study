
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
  private int score;
  private Font score_font;
  private Stage stage;

  public GameManager(DanmakuField df){
    this.df=df;
    beams=new ArrayList<Beam>();
    enemies=new ArrayList<Enemy>();
    bombs=new ArrayList<Bomb>();
    explosions=new ArrayList<Explosion>();
    shields=new ArrayList<Shield>();
    msgs=new ArrayList<Msg>();
    stage=new StageDemo(this);
    score_font=new Font("",Font.PLAIN,24);
    initGame();
  }

  public void initGame(){
    ship=new Ship(300,500);
    clearLists();
    df.addMouseListener(ship);
    df.addMouseMotionListener(ship);
    score=0;
  }

  public void run(){
    long t=0;
    while(true){
      if(ship==null) continue;
      int w=df.getWidth(),h=df.getHeight();
      double sx=ship.getX(),sy=ship.getY();

      if(t%5==0) moveAllBeams();
      if(t%10==0) ship.move();
      if(t%30==0) ship.shot(this);
      if(t%20==0) moveAllEnemies(w,h,sx,sy);
      if(t%10==0) moveAllBombs(w,h);
      if(t%30==0) spreadAllExplosions();
      if(t%10==0) spreadAllShields();
      if(t%5==0) collideBE();
      if(t%10==0) collideBS();
      if(t%30==0) playAllMessages();

      if(t%200==0){
        if(!stage.proceed(w,h,sx,sy))
          stage=stage.next();
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
    for(Beam b:beams) b.draw(g);
    for(Bomb b:bombs) b.draw(g);
    for(Enemy e:enemies) e.draw(g);
    for(Explosion e:explosions) e.draw(g);
    for(Shield s:shields) s.draw(g);
    ship.draw(g);
    stage.draw(g);
    for(Msg m:msgs) m.draw(g,w,h);
    g.setFont(score_font);
    g.setColor(Color.white);
    g.drawString(""+score,10,30);
  }

  public void addBeam(Beam b){
    beams.add(b);
  }

  private void moveAllBeams(){
    Iterator<Beam> it=beams.iterator();
    while(it.hasNext()){
      Beam b=it.next();
      if(!b.move())
        it.remove();
    }
  }

  public void addEnemy(Enemy e){
    enemies.add(e);
  }

  private void moveAllEnemies(int w,int h,double sx,double sy){
    Iterator<Enemy> it=enemies.iterator();
    while(it.hasNext()){
      Enemy e=it.next();
      e.dropBomb(sx,sy,this);
      if(!e.move(w,h,sx,sy))
        it.remove();
    }
  }

  private void moveAllBombs(int w,int h){
    Iterator<Bomb> it=bombs.iterator();
    while(it.hasNext()){
      Bomb b=it.next();
      if(!b.move(w,h))
        it.remove();
    }
  }

  public void addBomb(Bomb b){
    bombs.add(b);
  }

  private void spreadAllExplosions(){
    Iterator<Explosion> it=explosions.iterator();
    while(it.hasNext()){
      Explosion e=it.next();
      if(!e.spread())
        it.remove();
    }
  }

  private void spreadAllShields(){
    Iterator<Shield> it=shields.iterator();
    while(it.hasNext()){
      Shield s=it.next();
      if(!s.spread())
        it.remove();
    }
  }

  private void collideBE(){
    for(Beam b:beams){
      for(Enemy e:enemies){
        if(b.overlap(e)){ 
          score+=e.getScore();
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
      if(ship.overlap(b)){
        b.die();
        double sx=ship.getX(),sy=ship.getY();
        if(ship.damage()){
          explosions.add(new ExplosionShip(sx,sy));
          stage=new StageOver(this);
        }
        else{
          shields.add(new Shield(sx,sy));
        }
      }
    }
  }

  public void addMessage(Msg m){
    msgs.add(m);
  }

  private void playAllMessages(){
    Iterator<Msg> it=msgs.iterator();
    while(it.hasNext()){
      Msg m=it.next();
      if(!m.play()){
        it.remove();
      }
    }
  }

  public DanmakuField getDF(){
    return df;
  }

  public void clearLists(){
    beams.clear();
    bombs.clear();
    enemies.clear();
    explosions.clear();
    shields.clear();
    msgs.clear();
  }

  public void gameClear(){
    ship.win();
    explosions.add(new ExplosionBoss());
    stage=new StageClear(this);
  }
}
