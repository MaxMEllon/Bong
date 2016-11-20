import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;

import Game.Game;
import Title.Title;

public class GameApplet extends JApplet implements Runnable, KeyListener {
    
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 600;
    private GameState state = GameState.TITLE;
    private GameState prevState = GameState.BOOT;
    private int activeKeyForPlayer1 = 0;
    private int activeKeyForPlayer2 = 0;
    private Game game;
    private Title title;

    public GameApplet() {
        super();
    }
   
    @Override
    public void run() {
        this.repaint();
        sleep(20);
    }

    private void sleep(int msec) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void init() {
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.title = new Title(WIDTH, HEIGHT);
        this.game = new Game(WIDTH, HEIGHT);
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.isDoubleBuffered();
        this.setFocusable(true);
        this.addKeyListener(this);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.paintComponentByState();
        if (this.state == GameState.GAME) {
            this.game.recieveKeyCodeForPlayer1(this.activeKeyForPlayer1);
            this.game.recieveKeyCodeForPlayer2(this.activeKeyForPlayer2);
        }
    }

    private void paintComponentByState() {
        if (this.state == this.prevState) return;
        switch (this.state) {
        case TITLE:
            this.add(this.title);
            this.prevState = GameState.TITLE;
            this.repaint();
            break;
        case GAME:
            this.remove(this.title);
            this.add(this.game);
            this.prevState = GameState.GAME;
            this.repaint();
            break;
        default:
            break;
        }
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("[DEBUG]\t " + e.getKeyCode());
        if (this.state == GameState.TITLE) {
            this.state = GameState.GAME;
        } else if (this.state == GameState.GAME) {
            // A: 65, S: 83, D: 68, W:87
            // ←: 37, ↑: 38, →: 39, ↓:40
            if (65 <= e.getKeyCode() && e.getKeyCode() <= 87) this.activeKeyForPlayer1 = e.getKeyCode();
            if (37 <= e.getKeyCode() && e.getKeyCode() <= 40) this.activeKeyForPlayer2 = e.getKeyCode();
        }
    }

    @Override public void keyTyped(KeyEvent e) { }
    @Override public void keyReleased(KeyEvent e) {
            if (65 <= e.getKeyCode() && e.getKeyCode() <= 87) this.activeKeyForPlayer1 = 0;
            if (37 <= e.getKeyCode() && e.getKeyCode() <= 40) this.activeKeyForPlayer2 = 0;
    }
}
