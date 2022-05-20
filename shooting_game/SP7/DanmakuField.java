
import java.awt.*;
import javax.swing.*;

public class DanmakuField extends JPanel {
  private GameManager gm;

  public DanmakuField(){
    setBackground(Color.black);
    setFocusable(true);
  }

  public void setGM(GameManager gm){
    this.gm=gm;
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    gm.draw(g);
  }
}
