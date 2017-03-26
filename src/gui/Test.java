package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Jerry on 2017/2/12.
 */
public class Test {

    Frame frm = new Frame("Test");
    AppCanvasTest appCanvasTest = new AppCanvasTest();
    Loop loop = new Loop();
    class Loop extends ThreadLoop
    {

        @Override
        public void execute() {
            appCanvasTest.draw();
        }
    }

    static public void main(String args[])
    {
        Test t = new Test();
        t.loop.start();
    }

    Test()
    {
        frm.setLayout(new BorderLayout());
        frm.setSize(800, 600);
        frm.add(appCanvasTest);
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);System.exit(0);
            }
        });
        frm.setVisible(true);
        appCanvasTest.init(appCanvasTest.getWidth(), appCanvasTest.getHeight());
        loop.setTimeInterval(1000000000 / 30);
    }
}



class AppCanvasTest extends AppCanvas {
    TripleLight t = new TripleLight();

    public AppCanvasTest() {
        this.add(t);
    }

    public void draw()
    {
        //g2d.setBackground(Color.BLUE);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        super.draw();
    }
}

class TripleLight extends VisibleObject
{
    int rgb = 0;
    @Override
    public void draw(Graphics g) {
        for(int i = 0; i < 3; i++)
        {
            rgb+=1000;
            System.out.println(rgb);
            g.setColor(new Color(rgb));
            g.fillArc(i*100, i*100, 30, 30, 0, 360);
        }
    }
}
