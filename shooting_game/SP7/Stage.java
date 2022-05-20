
import java.awt.*;

public abstract class Stage {
  protected int lap;
  protected int max;
  protected GameManager gm;

  public Stage(GameManager gm){
    lap=0;
    max=0;
    this.gm=gm;
  }

  public void draw(Graphics g){
    if(max>0){
      if(lap>0){
        g.setColor(new Color(0,255,180,128));
        int w=400*lap/max;
        g.fillRect(150,20,w,10);
      }
      g.setColor(Color.white);
      g.drawRect(150,20,400,10);
    }
  }

  public abstract boolean proceed(int w,int h,double sx,double sy);

  public abstract Stage next();
}
