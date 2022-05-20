import java.awt.*;

public class MsgScroll extends Msg{
    private String msg; //メッセージ文字列
    private Font font; //フォント
    private Color col; //色
    private int freeze; //静止時間

    //生成
    public MsgScroll(String m,Color c,int s){
        this(m,c,s,50,20);
    }
    public MsgScroll(String m,Color c,int s,int l,int f){
        msg=m;
        col=c;
        font=new Font("",Font.PLAIN,s);
        age=0;
        life=l; //寿命はl
        freeze=f; //静止時間はf
    }
    //描画
    public void draw(Graphics g,int w,int h){
        g.setColor(col);
        g.setFont(font);
        int sh=g.getFontMetrics().getHeight();
        int a=age;
        if(a>(life-freeze)/2){ //静止時間を加味してy座標を決める
            if(a>(life+freeze)/2)
                a=age-freeze;
            else
                a=(life-freeze)/2;
        }
        int y=h-a*(h+sh)/(life-freeze);
        drawCenter(g,msg,w,y);
    }
}
