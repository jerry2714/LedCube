package graphicmodel;

import ledcube.LedSerialStructure;
import gui.AppCanvas;
import gui.ThreadLoop;
import gui.VisibleObject;

import java.awt.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * 此類別繼承至Canvas
 * Created by Jerry on 2017/2/27.
 */
public class Cube extends AppCanvas {
    int l = 100;    //邊長(螢幕上的長度)
    int sideNumber = 0; //一個邊有幾顆燈
    /*頂點座標*/
    //int originalVertex[][];

    /*頂點索引陣列*/
    int vertexIndex[][];

    /*LED*/
    Led leds[];

    /*旋轉矩陣*/
    double rotateMatrixX[][], rotateMatrixY[][], rotateMatrixZ[][];

    /*暫存向量*/
    double temp[];
    int angle = 0;
    int count = 0;
    ThreadLoop loop = new ThreadLoop() {
        int angle = 1;
        @Override
        public void execute() {
            setRotateY(20 * Math.PI / 180);
            setRotateX(20 * Math.PI / 180);
           // setRotateZ(90 * Math.PI/180);
            updateCube();
            //resetCoordinate();
            //rotate();
            //setCoordinateOffset();
            /*leds[0].setColor(Color.red.getRGB());
            leds[1].setColor(Color.green.getRGB());
            leds[11].setColor(Color.blue.getRGB());
            leds[36].setColor(Color.pink.getRGB());*/
            sortList();
            //System.out.println("Y");
            draw();
            //removeCoordinateOffset();
            angle++;
        }
        public void run()
        {
            resetCoordinate();
            setRotateZ(90*Math.PI/180);
            rotate();
            setRotateZ(0);
            super.run();
        }
    };

    public Cube() {
        this(6);
    }

    /**
     * @param sideLength 代表這是一個總燈數為sideLength的三次方的立方體
     */
    public Cube(int sideLength) {
        int s = sideLength;
        temp = new double[3];
        setRotateX(0);
        setRotateY(0);
        setRotateZ(0);
        loop.setTimeInterval(1000000000 / 30);
        sideNumber = sideLength;
        leds = new Led[sideLength * sideLength * sideLength];

        int top = s * s * s - s * s;
        vertexIndex = new int[][]{{0, s-1}, {s-1, s*(s-1)},
                                  {s*(s-1), s*s - 1}, {s*s - 1, 0},
                                  {top, top+s-1}, {top+s-1, top + s*(s-1)},
                                  {top + s*(s-1), top + s*s - 1}, {top + s*s - 1, top},
                                  {0, top}, {s-1, top+s-1}, {s*(s-1), top + s*(s-1)}, {s*s - 1, top + s*s - 1}};
        for (int i = 0; i < leds.length; i++) {
            leds[i] = new Led();
            this.add(leds[i]);
        }
    }

    /**
     * 設定以x軸為轉軸的旋轉矩陣
     *
     * @param theta 旋轉的角度
     */
    public void setRotateX(double theta) {
        double m[][] = rotateMatrixX;
        if (m == null) {
            m = new double[3][3];
            rotateMatrixX = m;
            m[0][0] = 1;
            m[0][1] = m[0][2] = m[1][0] = m[2][0] = 0;
        }
        m[1][1] = m[2][2] = cos(theta);
        m[2][1] = sin(theta);
        m[1][2] = -m[2][1];
    }

    /**
     * 設定以y軸為轉軸的旋轉矩陣
     *
     * @param theta 旋轉的角度
     */
    public void setRotateY(double theta) {
        double m[][] = rotateMatrixY;
        if (m == null) {
            m = new double[3][3];
            rotateMatrixY = m;
            m[1][1] = 1;
            m[0][1] = m[1][2] = m[1][0] = m[2][1] = 0;
        }
        m[0][0] = m[2][2] = cos(theta);
        m[0][2] = sin(theta);
        m[2][0] = -m[0][2];

    }

    /**
     * 設定以z軸為轉軸的旋轉矩陣
     *
     * @param theta 旋轉的角度
     */
    public void setRotateZ(double theta) {
        double m[][] = rotateMatrixZ;
        if (m == null) {
            m = new double[3][3];
            rotateMatrixZ = m;
            m[2][2] = 1;
            m[2][1] = m[0][2] = m[1][2] = m[2][0] = 0;
        }
        m[1][1] = m[0][0] = cos(theta);
        m[1][0] = sin(theta);
        m[0][1] = -m[1][0];
    }

    /**
     * 設定led立方體的邊長(在螢幕畫面中的長度)
     *
     * @param L led立方體的邊長的長度
     */
    public void setEdgeLength(int L) {
        this.l = L / 2;
        //originalVertex = new int[][]{{-l, -l, -l}, {l, -l, -l}, {l, l, -l}, {-l, l, -l}, {-l, l, l}, {-l, -l, l}, {l, -l, l}, {l, l, l}};
    }

    /**
     * 重置所有led燈的座標
     */
    public void resetCoordinate() {
        //originalVertex = new int[][]{{-l, -l, -l}, {l, -l, -l}, {l, l, -l}, {-l, l, -l}, {-l, l, l}, {-l, -l, l}, {l, -l, l}, {l, l, l}};
        int origin[] = {-l, -l, -l};
        for (int i = 0; i < sideNumber; i++)
            for (int j = 0; j < sideNumber; j++)
                for (int k = 0; k < sideNumber; k++) {
                    if (j % 2 == 1)
                        leds[i * sideNumber * sideNumber + j * sideNumber + k].setCoordinate
                                (origin[0] + k * (2 * l / (sideNumber - 1)),
                                        origin[1] + j * (2 * l / (sideNumber - 1)),
                                        origin[2] + i * (2 * l / (sideNumber - 1)));
                    else
                        leds[i * sideNumber * sideNumber + j * sideNumber + (sideNumber - k - 1)].setCoordinate
                                (origin[0] + k * (2 * l / (sideNumber - 1)),
                                        origin[1] + j * (2 * l / (sideNumber - 1)),
                                        origin[2] + i * (2 * l / (sideNumber - 1)));
                }
    }

    /**
     * 調正所有led燈的位置以便完整呈現在螢幕上
     */
    public void setCoordinateOffset() {
        for (int i = 0; i < leds.length; i++)
            leds[i].setCoordinate(leds[i].getX() + getWidth() / 2, leds[i].getY() + getHeight() / 2, leds[i].getZ());
    }
    private void removeCoordinateOffset(){
        for (int i = 0; i < leds.length; i++)
            leds[i].setCoordinate(leds[i].getX() - getWidth() / 2, leds[i].getY() - getHeight() / 2, leds[i].getZ());
    }

    /**
     * 當調整了Cube的位置、大小或旋轉之後，呼叫updateCube()以更新螢幕上顯示的狀況
     */
    public void updateCube()
    {
        resetCoordinate();
        rotate();
        setCoordinateOffset();
    }

    /**
     * 進行旋轉計算，分別以三個座標軸為轉軸旋轉三次
     */
    public void rotate() {
     //   resetCoordinate();

        for (int i = 0; i < leds.length; i++) {
            temp = leds[i].getCoordinate();
            mutiply(rotateMatrixX, temp);
            mutiply(rotateMatrixY, temp);
            mutiply(rotateMatrixZ, temp);
            leds[i].setCoordinate(temp[0], temp[1], temp[2]);
        }
    }

    private void mutiply(double[][] rotate, double[] vertex) {
        double[] temp = new double[vertex.length];
        for (double d : temp) d = 0;
        for (int j = 0; j < vertex.length; j++)
            for (int k = 0; k < vertex.length; k++)
                temp[j] += rotate[j][k] * vertex[k];
        for (int i = 0; i < vertex.length; i++)
            vertex[i] = temp[i];
    }

    public void init(int width, int height) {
        super.init(width, height);
        int n = width / 3;
        while ((((double) n) / sideNumber) - n / sideNumber != 0) n++;
        setEdgeLength(n);
        for (int i = 0; i < leds.length; i++)
            leds[i].setRadius(15);
    }

    public void start() {
        loop.start();
    }

    public void replaceExecuteLoop(ThreadLoop loop)
    {
        this.loop = loop;
    }

    public void setFrame(LedSerialStructure l)
    {
        for(int i = 0; i < leds.length; i++)
            leds[i].setColor(l.getColor(i));
    }

    public void draw()
    {
        g2d.clearRect(0, 0, getWidth(), getHeight());
        for(VisibleObject o : observedList)
            o.draw(g2d);
        g2d.setColor(Color.green);
        for(int i = 0; i < vertexIndex.length; i++)
            g2d.drawLine((int)(leds[vertexIndex[i][0]].getX()),
                    (int)(leds[vertexIndex[i][0]].getY()),
                    (int)(leds[vertexIndex[i][1]].getX()),
                    (int)(leds[vertexIndex[i][1]].getY()));
        update(getGraphics());
    }
}
