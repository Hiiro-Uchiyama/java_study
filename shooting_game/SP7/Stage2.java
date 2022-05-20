
import java.awt.*;

public class Stage2 extends Stage {

  public Stage2(GameManager gm){
    super(gm);
    max=100;
  }

  public boolean proceed(int w,int h,double sx,double sy){
    if(lap==0)
      gm.addMessage(new MsgScroll("Stage 2",Color.cyan,32));
    if(lap>15 && lap%2==0)
      gm.addEnemy(new Enemy2(w,h,sx,sy));
    if(lap>40 && lap%3==0)
      gm.addEnemy(new Enemy3(w,h,sx,sy));
    lap++;
    return lap<max;
  }

  public Stage next(){
    return new Stage3(gm);
  }
}
