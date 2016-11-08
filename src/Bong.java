import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Bong extends JApplet implements Runnable {

    private static final long serialVersionUID = 1L;

    private Image img;
    private Graphics offsetGraphics;
    private Thread thread = null;
    private static final Dimension dimension = new Dimension(800, 400);
    
    @Override
    public void init() {
        this.setFocusable(true);
        this.img = this.createImage(dimension.width, dimension.height);
        this.offsetGraphics = this.img.getGraphics();
    }

    @Override
    public void paint(Graphics g) {
        this.offsetGraphics.setColor(Color.BLACK.darker());
        this.offsetGraphics.fillRect(0, 0, dimension.width, dimension.height);
        g.drawImage(img, 0, 0, this);
    }


    @Override
    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void stop() {
        thread = null;
    }
    
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (thread == thisThread) {
            this.requestFocus();
            this.repaint();
            System.out.println(dimension.width);
            this.sleep(10);
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        Runnable init = () -> {
            JFrame frame = new JFrame("Bong!");
            JApplet applet = new Bong();
            frame.setSize(dimension);
            applet.setSize(dimension);
            applet.setPreferredSize(dimension);
            frame.setBounds(0, 0, dimension.width, dimension.height);
            frame.add(applet);
            frame.pack();
            frame.setVisible(true);
            applet.init();
            applet.start();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        };

        SwingUtilities.invokeLater(() -> {
            init.run();
        });
    }
}
