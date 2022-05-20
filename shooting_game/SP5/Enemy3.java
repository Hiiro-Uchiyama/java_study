import java.awt.*;

public class Enemy3 extends Enemy {
    private double vel_max=3; //最大移動速度
    private int life_min=40; //最小寿命
    private int life_max=120; //最大寿命

    private double vx,vy; //速度
    private double size; //大きさ
    private double angle; //回転角度
    private int life; //寿命

    //生成
    public Enemy3(int w,int h,double tx,double ty) {
        size=20;
        setSize(1.8*size,1.8*size); //
        angle=0;
        switch((int)(Math.random()*4)){
        case 0:
            px=Math.random()*(w-20)+10; //画面上のどこか
            py=0;
            break;
        case 1:
            px=Math.random()*(w-20)+10; //画面下のどこか
            py=h;
            break;
        case 2:
            px=0;
            py=Math.random()*(h-20)+10; //画面左のどこか
            break;
        case 3:
            px=w;
            py=Math.random()*(h-20)+10; //画面右のどこか
            break;
        }
        double dx=tx-px, dy=ty-py;
        double d=Math.sqrt(dx*dx+dy*dy);
        vx=dx*vel_max/d;
        vy=dy*vel_max/d;
        life=(int)(Math.random()*(life_max-life_min))+life_min;
    }
    //描画
    public void draw(Graphics g){
        int[] x={ 0,0,0,0,0 }; //五角形の頂点座標配列
        int[] y={ 0,0,0,0,0 };
        for(int i=0;i<5;i++){ //拡張点を計算
            double a=i*2*Math.PI/5;
            x[i]=(int)(px+size*Math.cos(a+angle));
            y[i]=(int)(py+size*Math.sin(a+angle));
        }
        g.setColor(Color.cyan);
        g.fillPolygon(x,y,5); //五角形を描く
    }
    //移動
    //(tx,ty)へ向かう
    public boolean move(int w, int h, double tx, double ty){
        if(!alive) return false; //死んだら消滅
        if(Math.random()<0.1){ //10%の確率で
            vx=tx-px;
            vy=ty-py;
            double d=Math.sqrt(vx*vx+vy*vy);
            vx=vx*vel_max/d; //Shipの方を向く
            vy=vy*vel_max/d;
        }
        px+=vx; //移動
        py+=vy;
        angle+=Math.PI/36; //10度ずつ回す
        life--; //寿命を減らす
        return life>0; //寿命が無くなったら消滅
    }
    //爆弾を落とす
    public void dropBomb(double sx,double sy,GameManager gm){
        if(Math.random()>0.01) return;
        double t=2*Math.PI/16;
        for(int i=0;i<=16;i++){
            double tx=px+Math.cos(t*i);
            double ty=px+Math.sin(t*i);
            gm.addBomb(new Bomb(px,py,tx,ty));
        }
    }
}
