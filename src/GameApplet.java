import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JApplet;

import Base.SoundPlayer;
import Game.Game;
import Game.PlayerType;
import Result.Result;
import Title.Title;

public class GameApplet extends JApplet implements Runnable, KeyListener {
    
    private static final long serialVersionUID = 7771283932571607453L;
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 600;
    private GameState state = GameState.TITLE;
    private GameState prevState = GameState.BOOT;
    private int activeKeyForPlayer1 = 0;
    private int activeKeyForPlayer2 = 0;
    private Game game;
    private Title title;
    private Result result;

    public GameApplet() {
        super();
    }
  
    private void transitionResultIfNeed() {
        if (this.state == GameState.GAME && this.game.getResult() != PlayerType.DEFAULT) {
            result = new Result(WIDTH, HEIGHT, this.game.getResult());
            this.state = GameState.RESULT;
            sleep(1000);
        }
    }
    
    @Override
    public void run() {
        this.repaint();
        this.transitionResultIfNeed();
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
        this.setBounds(0, 0, WIDTH, HEIGHT);
        this.isDoubleBuffered();
        this.game = new Game(WIDTH, HEIGHT);
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
            if (this.result != null) this.remove(result);
            this.add(this.title);
            this.prevState = GameState.TITLE;
            this.repaint();
            break;
        case GAME:
            this.remove(this.title);
            this.add(this.game);
            this.title = new Title(WIDTH, HEIGHT);
            this.prevState = GameState.GAME;
            this.repaint();
            break;
        case RESULT:
            this.remove(this.game);
            this.add(this.result);
            this.game = new Game(WIDTH, HEIGHT);
            this.prevState = GameState.RESULT;
            this.repaint();
            break;
        default:
            break;
        }
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        if (this.state == GameState.TITLE) {
            this.state = GameState.GAME;
            SoundPlayer missSound = new SoundPlayer("./Sounds/op.wav");
            missSound.play();
            sleep(500);
        } else if (this.state == GameState.GAME) {
            // A: 65, S: 83, D: 68, W:87, E:69
            // ←: 37, ↑: 38, →: 39, ↓:40, /:47, .:46
            if (65 <= e.getKeyCode() && e.getKeyCode() <= 87) this.activeKeyForPlayer1 = e.getKeyCode();
            if (37 <= e.getKeyCode() && e.getKeyCode() <= 47) this.activeKeyForPlayer2 = e.getKeyCode();
        } else if (this.state == GameState.RESULT) {
            if (e.getKeyCode() == 10) {
                sleep(1000);
                this.state = GameState.TITLE;
            }
        }
    }

    @Override public void keyTyped(KeyEvent e) { }

    @Override public void keyReleased(KeyEvent e) {
        if (this.state == GameState.GAME) {
            if (65 <= e.getKeyCode() && e.getKeyCode() <= 87) this.activeKeyForPlayer1 = 0;
            if (37 <= e.getKeyCode() && e.getKeyCode() <= 47) this.activeKeyForPlayer2 = 0;
        }
    }
}
