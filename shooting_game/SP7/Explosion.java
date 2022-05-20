
import java.awt.*;

public class Explosion extends GameChar {
  protected int exp_cnt;

  public Explosion(double x,double y){
    super(x,y);
    exp_cnt=0;
  }

  public void draw(Graphics g){
    int s=exp_cnt+5;
    int dx=exp_cnt*2+10;
    int len=exp_cnt*5;
    int y=255-exp_cnt*10;
    if(y<0) y=0;
    g.setColor(new Color(y,y,0));
    g.drawOval((int)px-s,(int)py-s,2*s,2*s);
    g.drawLine((int)px+dx,(int)py,(int)px+dx+len,(int)py);
    g.drawLine((int)px-dx,(int)py,(int)px-dx-len,(int)py);
  }

  public boolean spread(){
    return exp_cnt++<20;
  }
}
