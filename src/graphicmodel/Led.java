package graphicmodel;

import gui.VisibleObject;
import java.awt.*;

/**
 * Created by Jerry on 2017/3/2.
 */
public class Led extends VisibleObject{
    private double coordinate[] = new double[3];    //x, y, z
    private Color color = Color.black;
    private int radius = 0; //代表led燈的圓點半徑
    private int alphaMask = 0x00FFFFFF;
    private int defaultAlpha = 0xC0000000;


    /**
     * 設定座標
     * @param x x座標
     * @param y y座標
     * @param z z座標
     */
    public void setCoordinate(double x, double y, double z)
    {
        coordinate[0] = x;
        coordinate[1] = y;
        coordinate[2] = z;
    }

    /**
     * 取得座標
     * @return [0] [1] [2] -> [x] [y] [z]
     */
    public double[] getCoordinate(){return  coordinate;}

    /**
     * 取得x座標
     * @return x座標
     */
    public double getX(){return coordinate[0];}

    /**
     * 取得y座標
     * @return y座標
     */
    public double getY(){return coordinate[1];}

    /**
     * 取得z座標
     * @return z座標
     */
    public double getZ(){return coordinate[2];}

    /**
     * 設定led燈的顏色
     * @param c 以argb模型表示的顏色
     */
    public void setColor(int c)
    {
        color = new Color((c & alphaMask) | defaultAlpha, true);

    }

    /**
     *設定代表led燈的圓型的半徑
     * @param r 半徑
     */
    public void setRadius(int r){radius = r;}

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.gray);
        g.drawArc((int)getX()-radius, (int)getY()-radius, radius*2, radius*2, 0, 360);
        g.setColor(color);
        g.fillArc((int)getX()-radius, (int)getY()-radius, radius*2, radius*2, 0, 360);
    }

    public int compareTo(VisibleObject led)
    {
       if(this.getZ() - ((Led)led).getZ() > 0)
           return 1;
       else if(this.getZ() - ((Led)led).getZ() < 0)
           return  -1;
       else return 0;
    }


}
