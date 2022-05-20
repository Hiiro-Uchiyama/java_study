
import java.awt.*;

public class Stage1 extends Stage {

  public Stage1(GameManager gm){
    super(gm);
    max=100;
  }

  public boolean proceed(int w,int h,double sx,double sy){
    if(lap==0)
      gm.addMessage(new MsgScroll("Stage 1",Color.cyan,32));
    if(lap>10 && lap%3==0)
      gm.addEnemy(new Enemy1(w,h));
    if(lap>40 && lap%4==0)
      gm.addEnemy(new Enemy2(w,h,sx,sy));
    lap++;
    return lap<max;
  }

  public Stage next(){
    return new Stage2(gm);
  }
}
