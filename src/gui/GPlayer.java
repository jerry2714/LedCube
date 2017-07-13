package gui;



import javazoom.jl.player.JavaSoundAudioDevice;
import ledcubeproject.models.musicprocessor.Player;
import ledcubeproject.models.musicprocessor.processor.SimpleSpectrumAnalyzer;
import ledcubeproject.util.Callback;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Jerry on 2017/1/30.
 */
public class GPlayer extends Thread{

    private Frame frm;
    private SpectrumArea spectrumArea;
    private Player player;
    private SimpleSpectrumAnalyzer simpleSpectrumAnalyzer = new SimpleSpectrumAnalyzer();
    private Callback spectrumMovement = new Callback(){
        boolean draw = true;
        long t = System.nanoTime();
        @Override
        public void run() {
            try {
                if(System.nanoTime() - t >= player.getMsPerFrame()*1000000)
                {
                    System.out.println((System.nanoTime() - t) / 1000000000.0);
                    EventQueue.invokeAndWait(() ->
                    {
                        spectrumArea.setSpectrum(simpleSpectrumAnalyzer.getOutput(player.getCurrentPCM(), player.getSampleRate()));
                        spectrumArea.draw();
                        spectrumArea.repaint();
                    });
                    t = System.nanoTime();
                }
                //draw = !draw;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    public static void main(String args[])
    {
        GPlayer gPlayer = new GPlayer();
        gPlayer.init(args[0]);

        gPlayer.start();
    }

    public void init(String fileName)
    {
        frm = new Frame("GPlayer");
        frm.setSize(1920,800);
        frm.setLayout(new BorderLayout());
        frm.addWindowListener(GeneralWinListener.getInstance());

        spectrumArea = new SpectrumArea();
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        frm.add(p);
        p.add(spectrumArea);
        frm.setBackground(Color.blue);
        frm.setVisible(true);
        spectrumArea.init(p.getWidth(), p.getHeight());

        player = new Player(new JavaSoundAudioDevice(), fileName);
        player.setPlayingAction(spectrumMovement);
    }

    public void run()
    {
        player.play();
    }
}

class GeneralWinListener extends WindowAdapter {

    static  GeneralWinListener gwl = new GeneralWinListener();

    static public GeneralWinListener getInstance(){return gwl;}
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
