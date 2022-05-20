
import java.awt.*;
import java.awt.event.*;

public class Ship extends GameChar implements MouseListener,MouseMotionListener {
  private double vel_max=8;
  private int mx,my;
  private boolean m_down;
  private int shield_cnt;
  private boolean won;

  public Ship(int x,int y){
    super(x,y);
    setSize(16,16);
    mx=x;
    my=y;
    m_down=false;
    shield_cnt=10;
    won=false;
  }

  public void move(){
    double vx=mx-px;
    double vy=my-py;
    double vel=Math.sqrt(vx*vx+vy*vy);
    if(vel>vel_max){
      vx=vx*vel_max/vel;
      vy=vy*vel_max/vel;
    }
    px+=vx;
    py+=vy;
  }

  public void shot(GameManager gm){
    if(alive && m_down)
      gm.addBeam(new Beam(px,py));
  }

  public void draw(Graphics g){
    if(!alive) return;
    int[] x={ (int)px,(int)px-10,(int)px+10 };
    int[] y={ (int)py-15,(int)py+10,(int)py+10 };
    g.setColor(new Color(255,255,100));
    g.fillPolygon(x,y,3);
    g.setColor(new Color(255,200,20));
    g.drawPolygon(x,y,3);

    g.setColor(new Color(0,200,255,128));
    for(int i=0;i<shield_cnt;i++){
      int x1=i*22+20;
      g.fillRect(x1,530,20,10);
    }
  }

  public boolean damage(){
    if(shield_cnt>0) shield_cnt--;
    alive=(shield_cnt>0);
    return !alive;
  }

  public boolean overlap(GameChar c){
    if(!alive || won) return false;
    return super.overlap(c);
  }

  public void win(){
    won=true;
  }

  public void mousePressed(MouseEvent ev){
    m_down=true;
  }
  public void mouseReleased(MouseEvent ev){
    m_down=false;
  }
  public void mouseClicked(MouseEvent ev){}
  public void mouseEntered(MouseEvent ev){}
  public void mouseExited(MouseEvent ev){}
  public void mouseMoved(MouseEvent ev){
    mx=ev.getX();
    my=ev.getY();
  }
  public void mouseDragged(MouseEvent ev){
    mx=ev.getX();
    my=ev.getY();
  }
}
