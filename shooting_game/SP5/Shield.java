import java.awt.*;

public class Shield extends GameChar {
    private int sld_cnt;
    public Shield(double x, double y){
        super(x,y);
        sld_cnt=0;
    }
    public void draw(Graphics g){
        if(sld_cnt%3>0)return;
        int s=sld_cnt*2;
        g.setColor(Color.red);
        g.drawOval((int)px-s,(int)py-s,2*s,2*s);
    }
    public boolean spread() {
        return sld_cnt++<30;
    }
}