import java.awt.*;

public abstract class Msg{
    protected int age; //年齢
    protected int life; //寿命
    protected boolean alive; //生存フラグ
    
    //生成
    public Msg(){
        life=0; //寿命無し
        alive=true; //生きている
    }

    //描画
    public abstract void draw(Graphics g,int w,int h);

    //動作
    public boolean play(){
        age++; //年齢を増やす
        if(life>0) //寿命が設定してあるなら
            alive=(age<life); //寿命が来たら死ぬ
        return alive; //生きているがどうか
    }
    //死ぬ
    public void die(){
        alive=false;
    }
    //左右中央に描画
    //Fontはセットしてある
    protected void drawCenter(Graphics g,String s,int w, int y){
        FontMetrics fm=g.getFontMetrics();
        int sw=fm.stringWidth(s);
        int sh=fm.getHeight();
        int x=(w-sw)/2;
        y+=sh/2;
        g.drawString(s,x,y);
    }
}