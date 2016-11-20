import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        GameFrame gameFrame = new GameFrame();
        GameApplet gameApplet = new GameApplet();
        SwingUtilities.invokeLater(() -> {
            gameFrame.add(gameApplet);
            gameApplet.init();
            gameApplet.start();
            gameFrame.setVisible(true);
            gameFrame.pack();
            gameFrame.setBounds(0, 0, 1300, 650);
            sleep(1000);
            scheduler.scheduleAtFixedRate(gameApplet, 0, 30, TimeUnit.MILLISECONDS);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    private static void sleep(int msec) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
