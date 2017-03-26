package graphicModel.test;

import LedCube.LedCubeStructure;
import LedCube.LedSerialStructure;
import graphicModel.Cube;
import gui.ThreadLoop;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Jerry on 2017/3/9.
 */
public class rainbowTest {
    public static void main(String args[])
    {
        Cube cube = new Cube(6);
        LedCubeStructure ledCubeStructure = new LedCubeStructure(6);
        LedSerialStructure ledSerialStructure = new LedSerialStructure(ledCubeStructure);

        ThreadLoop loop = new ThreadLoop() {
            //int angle =  0;
            public void run()
            {
                cube.resetCoordinate();
                cube.setRotateZ(-10*Math.PI/180);
                cube.rotate();
                cube.setRotateZ(0);
                cube.setRotateX(80*Math.PI/180);
                cube.rotate();
                cube.setRotateX(0);
                cube.setRotateY(45*Math.PI/180);
                cube.rotate();
                cube.setRotateY(0);
                cube.setCoordinateOffset();
                super.run();
            }
            @Override
            public void execute() {

                //cube.setRotateY(20*Math.PI/180);
               // cube.updateCube();

                //angle++;
            }
        };

        Thread frameThread = new Thread(){
            Color[] colors = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.magenta};
            public void run()
            {
                for(int i = 0; i < ledCubeStructure.getSideLength(); i++)
                    for(int j = 0; j < ledCubeStructure.getSideLength(); j++)
                        for(int k = 0; k < ledCubeStructure.getSideLength(); k++)
                        {
                            ledCubeStructure.setColor(k, j, i, colors[i].getRGB());
                            ledSerialStructure.setStructure(ledCubeStructure);
                            cube.setFrame(ledSerialStructure);
                            long time = System.nanoTime();
                            while((System.nanoTime() - time) < 1000000000/10);
                            cube.sortList();
                            cube.draw();
                        }
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
        frameThread.start();
    }
}
