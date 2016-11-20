package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import Base.BongPanel;
import Game.PlayerType;

public class Life extends BongPanel {

    private static final long serialVersionUID = 5775254050751173030L;
    private static final int MAX_LIFE = 15;
    private int life = MAX_LIFE;
    private final int DEFAULT_WIDTH;
    private Color rightBlue = new Color(80, 80, 255);
    private Color rightRed = new Color(255, 80, 80);
    private PlayerType playerType;

    public Life(int x, int y, int width, int height, PlayerType player1) {
        super(x, y, width, height);
        DEFAULT_WIDTH = width;
        this.playerType = player1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.paintBackground(g2d);
    }
    
    public void decreaseLifePoint() {
        if (this.life > 0) this.life--;
    }
    
    public int getLife() {
        return this.life;
    }
    
    @Override
    protected void paintBackground(Graphics2D g2d) {
        final int lifeRange = DEFAULT_WIDTH / MAX_LIFE;
        if (this.width > lifeRange * life) { this.width -= 5; }
        Color color = playerType == PlayerType.Player1 ? rightBlue : rightRed;
        g2d.setColor(color);
        g2d.fillRect(0,0,this.width, this.height);
    }
}
