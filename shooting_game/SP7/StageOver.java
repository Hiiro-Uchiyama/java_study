
import java.awt.*;
import java.awt.event.*;

public class StageOver extends Stage implements KeyListener {
  private boolean demo;

  public StageOver(GameManager gm){
    super(gm);
    demo=false;
    gm.getDF().addKeyListener(this);
  }

  public boolean proceed(int w,int h,double sx,double sy){
    if(lap==0){
      gm.addMessage(new MsgBrink("G A M E   O V E R",200,Color.red,32));
      gm.addMessage(new MsgBrink("press R key to Demo",350,Color.white,24));
    }
    lap++;
    return !demo;
  }

  public Stage next(){
    gm.initGame();
    return new StageDemo(gm);
  }

  public void keyPressed(KeyEvent ev){
    if(ev.getKeyCode()==KeyEvent.VK_R){
      gm.initGame();
      demo=true;
    }
  }
  public void keyReleased(KeyEvent ev){}
  public void keyTyped(KeyEvent ev){}
}
