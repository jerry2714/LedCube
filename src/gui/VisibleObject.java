package gui;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jerry on 2017/2/1.
 */

public abstract class VisibleObject implements Comparable<VisibleObject>
{
    private int priority;//繪圖優先權(最大的最後繪製，所以蓋在最上面)
    BufferedImage img;	//此物件目前的完整圖形
    int ox, oy, x, y;	//上一次座標、新座標
    int width, height;	//長寬
    protected Graphics2D canvasGraph;
    protected Graphics2D g2d;

    /**
     * 設定此物件會被畫在哪一個繪圖區裡面
     * @param c  指定的繪圖區
     */
    public void addToCanvas(AppCanvas c)
    {
        c.add(this);
    }

    /**
     * 設定可繪圖區的Graphics，透過這個Graphics來將此物件畫上去
     * @param g
     */
    public void setCanvasGraphics(Graphics2D g){canvasGraph = g;}

    public void setPriority(int a){priority = a;}
    public void setPosition(int x, int y){this.x = x; this.y = y;}
    public int compareTo(VisibleObject go)//將繪圖優先權設為排序依據
    {
        return this.priority - go.priority;
    }

    public abstract void draw(Graphics g);

}