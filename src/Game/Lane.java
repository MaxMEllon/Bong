package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Base.BongPanel;

public class Lane extends BongPanel {

    
    public Lane(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    private static final long serialVersionUID = 461333407229905683L;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.paintBackground(g2d);
    }

    private boolean isActive = false;

    public void updateIfActive(final Ball ball) {
        final int herfSize = ball.Size() / 2;
        final int y = ball.Y() + herfSize;
        this.isActive = this.y <= y && y <= this.y + this.height ? true : false;
    }

    @Override
    protected void paintBackground(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.drawRect(0, 0, this.width, this.height);
        Color color = this.isActive ? Color.DARK_GRAY : Color.black;
        g2d.setColor(color);
        g2d.fillRect(1, 1, this.width - 2, this.height - 2);
    }

}
