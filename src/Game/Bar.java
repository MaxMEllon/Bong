package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Base.BongPanel;

public class Bar extends BongPanel {

    private final PlayerType playerType;
    private final Dimension mapSize;
    
    public Bar(int x, int y, int width, int height, PlayerType playerType, final Dimension mapSize) {
        super(x, y, width, height);
        this.playerType = playerType;
        this.mapSize = mapSize;
    }

    private static final long serialVersionUID = 2749682621364044140L;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.paintBackground(g2d);
    }
    
    public void moveToUp() {
        if (this.y >= 0) {
            this.y -= 20;
        }
    }
    
    public int X() { return this.x; }
    public int Y() { return this.x; }
    public int Width() { return this.width; }
    public int Height() { return this.height; }
    public PlayerType PlayerType() { return this.playerType; }
    
    public void moveToDown() {
        if (this.y + this.height <= this.mapSize.height)  {
            this.y += 20;
        }
    }

    private final Color darkBlue = new Color(0, 0, 100);
    private final Color darkRed = new Color(100, 0, 0);
    @Override
    protected void paintBackground(Graphics2D g2d) {
        this.setBounds(this.x, this.y, this.width, this.height);
        Color color = playerType == PlayerType.Player1 ? darkBlue : darkRed;
        g2d.setColor(color);
        g2d.fill3DRect(0, 0, this.width, this.height, false);
        color = playerType == PlayerType.Player1 ? Color.BLUE : Color.RED;
        g2d.setColor(color);
        g2d.fill3DRect(4, 4, this.width - 8, this.height - 8, false);
    }
}
