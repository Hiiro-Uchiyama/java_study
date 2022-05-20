import java.awt.*;

public class MsgBrink extends Msg{
    private String msg; //メッセージ文字列
    private Color col; //色
    private Font font; //フォント
    private int py; //y座標
    private int on, off; //点灯・消灯時間

    //生成
    public MsgBrink(String m,int y,Color c,int s){
        this(m,y,c,s,10,5,0);
    }
    public MsgBrink(String m,int y,Color c,int s, int on,int off, int l){
        msg=m;
        py=y;
        col=c;
        font=new Font("",Font.PLAIN,s);
        this.on=on;
        this.off=off;
        life=l;
        age=0;
    }
    //描画
    public void draw(Graphics g,int w,int h){
        if(age%(on+off)<on){ //点灯しているタイミングなら
            g.setColor(col); //色と
            g.setFont(font); //フォントをセットして
            drawCenter(g,msg,w,py); //左右中央に描画
        }
    }
}
