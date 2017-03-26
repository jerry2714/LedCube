package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.LinkedList;

/**
 *實作 雙緩衝區的繪圖區
 * Created by Jerry on 2017/1/31.
 */
public class AppCanvas extends Canvas {

    private BufferedImage outputBuffer = null;
    protected Graphics2D g2d;
    protected LinkedList<VisibleObject> observedList = new LinkedList<>();    //所有將會被畫在此繪圖區的物件


    /**
     * 初始化，指定這個繪圖區的大小
     * @param width  這個區域的寬度
     * @param height  這個區域的高度
     */
    public void init(int width, int height)
    {
        setSize(width, height);
    }


    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        outputBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = outputBuffer.createGraphics();
        System.out.println("size change");
        g2d.clearRect(0, 0, width, height);
        update(getGraphics());
        sendGraphics();
    }

    /**
     * 加入物件
     * @param o  欲加入的物件
     */
    public void add(VisibleObject o)
    {
        observedList.add(o);
        sendGraphics(o);
    }

    /**
     * 移除物件
     * @param o  欲移除的物件
     */
    public void remove(VisibleObject o)
    {
        observedList.remove(o);
    }

    private void sendGraphics()
    {
        for(VisibleObject o : observedList)
            o.setCanvasGraphics(g2d);
    }
    private void sendGraphics(VisibleObject o)
    {
        o.setCanvasGraphics(g2d);
    }

    public void sortList()
    {
        Collections.sort(observedList);
    }

    public void draw()
    {
        g2d.clearRect(0, 0, getWidth(), getHeight());
        for(VisibleObject o : observedList)
            o.draw(g2d);
        update(getGraphics());
    }

    @Override
    public void update(Graphics g){paint(g);}
    @Override
    public void paint(Graphics g) {
        g.drawImage(outputBuffer, 0, 0, getWidth(), getHeight(), this);
    }
}


