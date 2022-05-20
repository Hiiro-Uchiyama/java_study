
import java.awt.*;

public class ExplosionShip extends Explosion {

  public ExplosionShip(double x,double y){
    super(x,y);
  }

  public void draw(Graphics g){
    g.setColor(Color.red);
    for(int i=exp_cnt;i>0;i--){
      int r=i*10;
      g.drawOval((int)px-r,(int)py-r,2*r,2*r);
    }
  }
}
