package graphicModel;
import LedCube.LedCubeStructure;
import gui.AppCanvas;
import gui.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * 以三維座標表示的模型
 * Created by Jerry on 2017/2/11.
 */
public class GraphicModel extends Frame{

    LedCubeStructure ledCubeStructure = null;

    Cube cube;

    static public void main(String args[])
    {
       GraphicModel graphicModel = new GraphicModel();
       graphicModel.init();
    }

    public GraphicModel()
    {
        this(6);
    }

    public GraphicModel(int n)
    {
       ledCubeStructure = new LedCubeStructure(n);
       cube = new Cube();

    }
    public void init()
    {
        Panel pCube = new Panel(new BorderLayout());
        setSize(800, 600);
        add(pCube);
        pCube.add(cube);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        setVisible(true);
        cube.init(cube.getWidth(), cube.getHeight());
        System.out.println("width: "+cube.getWidth()+ "  height: "+cube.getHeight());
        cube.start();
    }

}
class Pcontrol extends Panel
{
    Button btnX, btnY, btnZ;
    Label lAngleX, lAngleY, lAngleZ;

    public Pcontrol(){
        this.setLayout(new GridLayout(3,2));
        btnX = new Button("X++");
        btnY = new Button("Y++");
        btnZ = new Button("Z++");
        lAngleX = new Label("0", Label.CENTER);
        lAngleY = new Label("0", Label.CENTER);
        lAngleZ = new Label("0", Label.CENTER);
        btnX.addMouseListener(mouseAdapter);
        btnY.addMouseListener(mouseAdapter);
        btnZ.addMouseListener(mouseAdapter);
        this.add(btnX);
        this.add(lAngleX);
        this.add(btnY);
        this.add(lAngleY);
        this.add(btnZ);
        this.add(lAngleZ);
    }
    public int getAngleX(){return Integer.parseInt(lAngleX.getText());}
    public int getAngleY(){return Integer.parseInt(lAngleY.getText());}
    public int getAngleZ(){return Integer.parseInt(lAngleZ.getText());}

    MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            switch(((Button)e.getSource()).getLabel().charAt(0))
            {
                case 'X':
                    lAngleX.setText(""+(Integer.parseInt(lAngleX.getText()) + 1));
                    break;
                case 'Y':
                    lAngleY.setText(""+(Integer.parseInt(lAngleY.getText()) + 1));
                    break;
                case 'Z':
                    lAngleZ.setText(""+(Integer.parseInt(lAngleZ.getText()) + 1));
                    break;
            }

        }

    };
}