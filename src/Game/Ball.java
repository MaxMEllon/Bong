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

    public Ball(int x, int y, int width, int height, int fieldWidth, int fieldHeight, int size, ArrayList<Bar> barList) {
        super(x, y, width, height);
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.size = size;
        this.barList = barList;
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
  
    private void update() {
        int ballY = (this.y + this.height) /2;
        Bar bar1 = barList.get(0);
        if (bar1.Y() <= ballY && ballY <= bar1.Y() + bar1.Height()
            && bar1.X() <= this.x && this.x <= bar1.X() + bar1.Width() + 10)
        {
            System.out.printf("bar: %d %d\n", bar1.X(), bar1.Y());
            System.out.printf("ball: %d %d", this.X(), this.Y());
            SoundPlayer successSound = new SoundPlayer("./Sounds/shot.wav");
            successSound.play();
            this.dy *= -1;
            this.dx *= -1;
        }
        Bar bar2 = barList.get(1);
        int ballX = this.x + this.width;
        if (bar2.Y() <= ballY && ballY <= bar2.Y() + bar2.Height()
            && bar2.X() - 10 <=  ballX && ballX <= bar2.X())
        {
            SoundPlayer successSound = new SoundPlayer("./Sounds/shot.wav");
            successSound.play();
            this.dx = -1;
            this.dy *= -1;
        }

        if (this.x + this.width >= this.fieldWidth) {
            this.dx = -1;
            SoundPlayer missSound = new SoundPlayer("./Sounds/miss.wav");
            missSound.play();
        }
        else if (this.y + this.height >= this.fieldHeight) {
            this.dy = -1;
            SoundPlayer wallSound = new SoundPlayer("./Sounds/reflect.wav");
            wallSound.play();
        }
        else if (this.x <= 0) {
            this.dx = 1;
            SoundPlayer missSound = new SoundPlayer("./Sounds/miss.wav");
            missSound.play();
        }
        else if (this.y <= 0) {
            this.dy = 1;
            SoundPlayer wallSound = new SoundPlayer("./Sounds/reflect.wav");
            wallSound.play();
        }
        this.x += (dx * speed);
        this.y += (dy * speed);
    }
    
    @Override
    protected void paintBackground(Graphics2D g2d) {
        this.setBounds(x, y, this.width, this.height);
        this.update();
        g2d.setColor(color);
        g2d.fillOval(0, 0, this.width, this.height);
    }
}
