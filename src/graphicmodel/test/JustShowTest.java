package graphicmodel.test;

import graphicmodel.Cube;
import gui.ThreadLoop;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Jerry on 2017/3/4.
 */
public class JustShowTest {
    public static void main(String args[])
    {
        Cube cube = new Cube();
        ThreadLoop loop = new ThreadLoop() {
            int angle =  0;
            @Override
            public void execute() {
                cube.setRotateX(angle*Math.PI/180);
                cube.setRotateY(angle*Math.PI/180);
                cube.setRotateZ(angle*Math.PI/180);
                cube.updateCube();
                cube.sortList();
                cube.draw();
                angle++;
            }
        };
        loop.setTimeInterval(1000000000/30);
        cube.replaceExecuteLoop(loop);
        Frame frm = new Frame();
        Panel panel = new Panel(new BorderLayout());
        frm.setSize(800, 600);
        frm.add(panel);
        panel.add(cube);
        frm.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        frm.setVisible(true);
        cube.init(cube.getWidth(), cube.getHeight());
        cube.start();
    }


}
