package Base;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public abstract class BongPanel extends JPanel {
    
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public BongPanel(int width, int height) {
        super();
        this.isDoubleBuffered();
        this.width = width;
        this.height = height;
        this.setBounds(0, 0, this.width, this.height);
        this.setDoubleBuffered(true);
    }
    
    public BongPanel(int x, int y, int width, int height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setBounds(x, y, this.width, this.height);
        this.setDoubleBuffered(true);
    }
    
    protected abstract void paintBackground(Graphics2D g2d);
}
