package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Base.BongPanel;

public class Status extends BongPanel {

    private int power = 0;
    private PlayerType playerType = PlayerType.DEFAULT;
    private static final int GAGE_MAX_RANGE = 5;
    
    public Status(int x, int y, int width, int height, PlayerType playerType) {
        super(x, y, width, height);
        this.playerType = playerType;
    }

    private static final long serialVersionUID = -2672033167846790985L;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBounds(this.x, this.y, this.width, this.height);
        Graphics2D g2d = (Graphics2D) g;
        this.paintBackground(g2d);
        this.paintGauge(g2d);
    }
    
    @Override
    protected void paintBackground(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0, 0, this.width, this.height);
    }
    
    private int colorForAnimation = 200;
    private void paintGauge(Graphics2D g2d) {
        Color color = playerType == PlayerType.Player1 ? Color.BLUE : Color.RED;
        if (power == GAGE_MAX_RANGE) {
            colorForAnimation += 2;
            colorForAnimation %= 255;
            if (colorForAnimation < 200) colorForAnimation = 200;
            color = playerType == PlayerType.Player1
                    ? new Color(0,0,colorForAnimation)
                    : new Color(colorForAnimation,0,0);
        }
        g2d.setColor(color);
        final int powerHeight = power * (this.height / GAGE_MAX_RANGE);
        g2d.fillRect(0, this.height - powerHeight, this.width, powerHeight);
    }
}
