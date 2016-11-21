package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Base.BongPanel;
import Base.SoundPlayer;

public class Ball extends BongPanel {

    private final int fieldWidth;
    private final int fieldHeight;
    private final int size;
    private ArrayList<Bar> barList;
    private Runnable addSkilPointOfPlayer1;
    private Runnable addSkilPointOfPlayer2;
    private Runnable decreaseLifeOfPlayer1;
    private Runnable decreaseLifeOfPlayer2;
    private PlayerType currentBallOwner = PlayerType.DEFAULT;
    private PlayerType hidden = PlayerType.DEFAULT;

    public Ball(int x, int y, int width, int height, int fieldWidth,
            int fieldHeight, int size, ArrayList<Bar> barList,
            Runnable addSkillPointOfPlayer1, Runnable addSkillPointOfPlayer2,
            Runnable decreaseLifeOfPlayer1, Runnable decreaseLifeOfPlayer2
            )
    {
        super(x, y, width, height);
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.size = size;
        this.barList = barList;
        this.addSkilPointOfPlayer1 = addSkillPointOfPlayer1;
        this.addSkilPointOfPlayer2 = addSkillPointOfPlayer2;
        this.decreaseLifeOfPlayer1 = decreaseLifeOfPlayer1;
        this.decreaseLifeOfPlayer2 = decreaseLifeOfPlayer2;
    }

    private static final long serialVersionUID = 519036451420326212L;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        this.paintBackground(g2d);
    }
    
    private int dx = 1;
    private int dy = 1;
    private int speed = 1;
    private Color color = new Color(100, 255, 100);
  
    public int X() { return x; }
    public int Y() { return y; }
    public int Size() { return size; }

    private void refrectBallByBar() {
        int ballY = this.y + this.height / 2;
        int ballX = this.x + this.width / 2;
        Bar bar1 = barList.get(0);
        if (bar1.Y() <= ballY && ballY <= bar1.Y() + bar1.Height()
            && bar1.X() <= ballX && ballX <= bar1.X() + bar1.Width())
        {
            SoundPlayer successSound = new SoundPlayer("./Sounds/shot.wav");
            if (this.currentBallOwner != PlayerType.Player1) {
                this.addSkilPointOfPlayer1.run();
                this.currentBallOwner = PlayerType.Player1;
            }
            successSound.play();
            this.dy *= -1;
            this.dx *= -2;
        }
        Bar bar2 = barList.get(1);
        if (bar2.Y() <= ballY && ballY <= bar2.Y() + bar2.Height()
            && bar2.X() <= ballX && ballX <= bar2.X() + bar2.Width())
        {
            SoundPlayer successSound = new SoundPlayer("./Sounds/shot.wav");
            if (this.currentBallOwner != PlayerType.Player2) {
                this.addSkilPointOfPlayer2.run();
                this.currentBallOwner = PlayerType.Player2;
            }
            successSound.play();
            this.dy *= -1;
            this.dx *= -2;
        }
    }
    
    private void refrectBallByWall() {
        if (this.x + this.width >= this.fieldWidth) {
            this.dx = -1;
            if (this.currentBallOwner != PlayerType.Player2) {
                this.decreaseLifeOfPlayer2.run();
                SoundPlayer missSound = new SoundPlayer("./Sounds/miss.wav");
                missSound.play();
            } else {
                SoundPlayer wallSound = new SoundPlayer("./Sounds/reflect.wav");
                wallSound.play();
            }
        }
        else if (this.y + this.height >= this.fieldHeight) {
            this.dy = -1;
            SoundPlayer wallSound = new SoundPlayer("./Sounds/reflect.wav");
            wallSound.play();
        }
        else if (this.x <= 0) {
            this.dx = 1;
            if (this.currentBallOwner != PlayerType.Player1) {
                this.decreaseLifeOfPlayer1.run();
                SoundPlayer missSound = new SoundPlayer("./Sounds/miss.wav");
                missSound.play();
            } else {
                SoundPlayer wallSound = new SoundPlayer("./Sounds/reflect.wav");
                wallSound.play();
            }
        }
        else if (this.y <= 0) {
            this.dy = 1;
            SoundPlayer wallSound = new SoundPlayer("./Sounds/reflect.wav");
            wallSound.play();
        }
    }
    
    public void warp(PlayerType playerType) {
        SoundPlayer warpSound = new SoundPlayer("./Sounds/warp.wav");
        warpSound.play();
        if (PlayerType.Player1 == playerType) {
            this.dx = 4;
            this.dy *= -2;
            this.x += 100;
            this.x %= (this.width - 200);
        }
        if (PlayerType.Player2 == playerType) {
            this.dx = -4;
            this.dy *= -2;
            this.x -= 100;
            if (this.x <= 200) this.x = 100;
        }
    }
    public void hiddenIfNeed(PlayerType playerType) {
        this.hidden = playerType;
    }
    
    public void showIfNeed() {
        this.hidden = PlayerType.DEFAULT;
    }

    private void sleep(int msec) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private synchronized void update() {
        this.refrectBallByBar();
        this.refrectBallByWall();
        if (Math.abs(dx) >= 6) this.dx = dx < 0 ? -1 : 1;
        if (Math.abs(dy) >= 2) this.dy = dy < 0 ? -1 : 1;
        this.x += (dx * speed);
        this.y += (dy * speed);
        this.sleep(10);
    }
    
    @Override
    protected void paintBackground(Graphics2D g2d) {
        this.update();
        this.setBounds(x, y, this.width, this.height);
        Color centorColor = this.currentBallOwner == PlayerType.Player1 ? Color.BLUE : Color.RED;
        if ((this.hidden == PlayerType.Player1 && this.currentBallOwner == PlayerType.Player1)
            || (this.hidden == PlayerType.Player2 && this.currentBallOwner == PlayerType.Player2))
        {
            centorColor = Color.DARK_GRAY;
        }
        Color color = this.hidden == PlayerType.DEFAULT ? this.color : Color.DARK_GRAY;
        g2d.setColor(color);
        g2d.fillOval(0, 0, this.width, this.height);
        g2d.setColor(centorColor);
        g2d.fillOval(4, 4, this.width -8, this.height -8);
    }
}
