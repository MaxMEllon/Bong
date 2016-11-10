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

import java.applet.*;	//wav?t?@?C???̍Đ??Ɏg?p

public class main extends JApplet implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    private static final int maxGageValue = 40;
    private Thread thread = null;
    private double x, dx, y, dy;
    private int xSize, ySize;
    private double paddleXL, paddleYL, paddleXR, paddleYR;
    private double paddleSize;
    private String message;
    private Font font;
    private int B_flag = 1;   //ブラインド用フラグ
    private int Llimit = 0;
    private int Rlimit = 0;
    private int L_flag, R_flag;

    private Image img;     
    private Graphics offg;
    private int width, height;
    

    @Override
    public void init() {
        Dimension size = getSize();
        width = size.width; height = size.height;
        xSize = width; ySize = height - 80;
        paddleSize = 20;
        message = "Game started!";
        font = new Font("Monospaced", Font.PLAIN, 12);
        setFocusable(true);
        addKeyListener(this);

        img  = createImage(width, height);
        offg = img.getGraphics();
    }

    private void initialize() {
        x = 100; y = 100;
        dx = 3.2; dy = 2.0;
        paddleYL = paddleYR = ySize / 2;
        paddleXL = 30; paddleXR = xSize - 30;
    }

    @Override
    public void paint(Graphics g) {
        offg.clearRect(0, 0, width, height); 

        offg.setColor(Color.BLACK);
        offg.drawRect(0, 0, xSize - 1, ySize - 1);
        offg.setColor(Color.MAGENTA.darker());

        // フラグ起動中のみボール表示
        if (B_flag == 1) {
            offg.fillOval((int)(x - 3), (int)(y - 3), 6, 6);
        }

        if ( Llimit < maxGageValue ) { offg.setColor(Color.BLACK); }
        else { offg.setColor(Color.RED); }
        offg.drawRect(5, (int)(ySize-5), maxGageValue, 3);
        offg.fillRect(5, (int)(ySize-5), Llimit, 3);
        if ( Rlimit < maxGageValue ) { offg.setColor(Color.BLACK); }
        else { offg.setColor(Color.RED); }
        offg.drawRect((int)(xSize-45), (int)(ySize-5), 40, 3);
        offg.fillRect((int)(xSize-45), (int)(ySize-5), Rlimit, 3);

        offg.setColor(Color.RED);
        offg.fillRect((int)(paddleXL - 2), (int)(paddleYL - paddleSize / 2), 4, (int)paddleSize);
        offg.setColor(Color.BLUE);
        offg.fillRect((int)(paddleXR - 2), (int)(paddleYR - paddleSize / 2), 4, (int)paddleSize);

        offg.setFont(font);
        offg.setColor(Color.GREEN.darker());
        offg.drawString(message, 5, ySize + 12);
        offg.setColor(Color.RED.darker());
        offg.drawString("Left:  Z(D), W(U), S(B)", 5, ySize + 24);
        offg.setColor(Color.BLUE.darker());
        offg.drawString("Right: M(D), I(U), K(B)", 5, ySize + 36);    

        g.drawImage(img, 0, 0, this);
    }

    public void run() {
        Thread thisThread = Thread.currentThread();
        while (thread == thisThread) {
            initialize();
            requestFocus();
            while (true) {
                x += dx;  y += dy;
                if (dx < 0 && (x - paddleXL) * (x - dx - paddleXL) <= 0) {
                    double rY = y + dy * (paddleXL - x) / dx;
                    if ((rY - paddleYL + paddleSize / 2) * (rY - paddleYL - paddleSize / 2) <= 0) {
                        x = 2 * paddleXL - x;
                        dx *= -1;
                        message = "";
                        // 返したらゲージ増
                        L_flag = 0;
                        if ( Llimit < 40 ) { Llimit += 4; }
                        SoundPlayer sp = new SoundPlayer("../sounds/shot.wav");
                        sp.play();
                    }
                }
                if (x < 0) {
                    x = -x;
                    dx *= -1;
                    message = "R won!";
                    R_flag = 0;
                    SoundPlayer sp = new SoundPlayer("../sounds/miss.wav");
                    sp.play();
                }
                if (dx > 0 && (x - paddleXR) * (x - dx - paddleXR) <= 0) {
                    double rY = y + dy * (paddleXR - x) / dx;
                    if ((rY - paddleYR + paddleSize / 2) * (rY - paddleYR - paddleSize / 2) <= 0) {
                        x = 2 * paddleXR - x;
                        dx *= -1;
                        message = "";
                        R_flag = 0;
                        if ( Rlimit < 40 ) { Rlimit += 40; }
                        SoundPlayer sp = new SoundPlayer("../sounds/shot.wav");
                        sp.play();
                    }
                }
                if (x > xSize) {
                    x = 2 * xSize - x;
                    dx *= -1;
                    message = "L won!";
                    L_flag = 0;
                    SoundPlayer sp = new SoundPlayer("../sounds/miss.wav");
                    sp.play();
                }
                if (y < 0) {
                    y = -y;
                    dy *= -1;
                    SoundPlayer sp = new SoundPlayer("../sounds/reflect.wav");
                    sp.play();
                }
                if (y > ySize) {
                    y = 2 * ySize - y;
                    dy *= -1;
                    SoundPlayer sp = new SoundPlayer("../sounds/reflect.wav");
                    sp.play();
                }
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                if ( Llimit == 0 ) { L_flag = 0; B_flag = 1; }
                if ( L_flag == 1 ) { Llimit--; B_flag = 0; }
                if ( Rlimit == 0 ) { R_flag = 0; B_flag = 1; }
                if ( R_flag == 1 ) { Rlimit--; B_flag = 0; }
            }
        }   
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

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
        case 'W':  paddleYL -= 10; break;
        case 'Z':  paddleYL += 10; break;
        case 'I':  paddleYR -= 10; break;
        case 'M':  paddleYR += 10; break;
        case 'S':  if ( Llimit == maxGageValue ) { L_flag = 1; } break;
        case 'K':  if ( Rlimit == maxGageValue ) { R_flag = 1; } break;
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    
    
    public class SoundPlay {
    	private AudioClip clip;
    	public void SoundPlay(String wavs){
    		//?????̓ǂݍ???
    		clip = Applet.newAudioClip(getClass().getResource(wavs));
    		clip.play();	//?????̍Đ?
    	}
    	
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bong!");       
            JApplet applet = new main();
            applet.setPreferredSize(new Dimension(481, 400));
            frame.add(applet);
            frame.pack();
            frame.setVisible(true);
            applet.init();
            applet.start();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
