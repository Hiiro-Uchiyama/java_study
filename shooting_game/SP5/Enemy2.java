import java.awt.*;

public class Enemy2 extends Enemy {
    private double vel_max=8; //最大移動速度
    private double size_min=5,size_max=8; //大きさの範囲
    private int cd_cnt_max=7; //向きの最大変更回数
    private int mv_cnt_min=10; //向きを変えるまでの最小移動回数
    private int mv_cnt_max=30; //向きを変えるまでの最大移動回数

    private double vx,vy; //速度
    private double size; //大きさ
    private double ds=1; //大きさの変化量
    private int cd_cnt; //向きを変えた回数
    private int mv_cnt; //向きを変えるまでの移動回数
    
    //生成
    //ゲーム画面外のどこかで発生
    public Enemy2(int w,int h,double tx,double ty) {
        size=(size_max+size_min)/2;
        setSize(1.6*size,1.6*size);
        switch((int)(Math.random()*3)){
        case 0:
            px=Math.random()*w;
            py=0;
            break;
        case 1:
            px=0;
            py=Math.random()*h/2;
            break;
        case 2:
            px=w;
            py=Math.random()*h/2;
            break;
        }
        double dx=tx-px, dy=ty-py;
        double d=Math.sqrt(dx*dx+dy*dy);
        vx=dx*vel_max/d;
        vy=dy*vel_max/d;
        cd_cnt=0;
        mv_cnt=(int)(Math.random()*(mv_cnt_max-mv_cnt_min))+mv_cnt_min;
    }
    //描画
    public void draw(Graphics g){
        g.setColor(Color.pink);
        g.fillOval((int)(px-size), (int)(py-size), (int)(2*size), (int)(2*size));
        g.setColor(Color.red);
        g.drawOval((int)(px-size), (int)(py-size), (int)(2*size), (int)(2*size));
    }
    //移動
    public boolean move(int w, int h, double tx, double ty){
        if(!alive) return false; //死んだら消滅
        px+=vx;
        py+=vy;
        if(px<0||px>w) vx=-vx;
        if(py<0||py>w) vy=-vy;
        if(mv_cnt==0){
            double rx=Math.random()*w;
            double ry=Math.random()*h;
            double dx=rx-px, dy=ry-py;
            double d=Math.sqrt(dx*dx+dy*dy);
            vx=dx*vel_max/d;
            vy=dy*vel_max/d;
            cd_cnt++;
            mv_cnt=(int)(Math.random())*(mv_cnt_max-mv_cnt_min)+mv_cnt_min;
        }
        size+=ds;
        if(size<size_min||size>size_max) ds=-ds;
        mv_cnt--;
        if(py>h) return false;
        return true;
    }
    //爆弾を落とす
    public void dropBomb(double sx,double sy,GameManager gm){
        if(Math.random()>0.01) return;
        double dx=sx-px,dy=sy-py;
        double d=Math.sqrt(dx*dx+dy*dy);
        double al=Math.atan2(dy,dx);
        double t=Math.PI/10;
        for(int i=-2;i<=2;i++){
            double tx=px+d*Math.cos(al+t*i);
            double ty=px+d*Math.sin(al+t*i);
            gm.addBomb(new Bomb(px,py,tx,ty));
        }
    }
}
