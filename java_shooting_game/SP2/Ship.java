import java.awt.*;
import java.awt.event.*;

public class Ship implements MouseListener, MouseMotionListener {
    private double vel_max=8;
    private double px,py;
    private int mx,my;
    private boolean m_down;
    public Ship(int x, int y){
        px=mx=x;
        py=my=y;
        m_down=false;
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
        if(m_down){
            gm.addBeam(new Beam(px,py));
        }
    }
    public void draw(Graphics g){
        int[] x={(int)px,(int)px-10,(int)px+10};
        int[] y={(int)py-15,(int)py+10,(int)py+10};
        g.setColor(new Color(255,255,100));
        g.fillPolygon(x,y,3);
        g.setColor(new Color(255,200,20));
        g.drawPolygon(x,y,3);
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
