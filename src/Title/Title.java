package Title;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import Base.BongPanel;

public class Title extends BongPanel {

    public Title(int width, int height) { super(width, height); }

    private static final long serialVersionUID = 517814792629029091L;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize(new Dimension(this.width, this.height));
        Graphics2D g2d = (Graphics2D) g;
        this.drawLine(g2d);
        this.paintBackground(g2d);
        this.drawTitle(g2d);
        this.drawDescription(g2d);
    }
    
    protected void paintBackground(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.width, 20);
        g2d.setColor(Color.BLUE);
        g2d.fill3DRect(0, this.height - 20, this.width, 20, false);
        g2d.fill3DRect(0, 0, this.width, 20, false);
    }
    
    private int tilteHeightForAnimated = -100;
    private int titleColorForAnimated = 100;
    private Font font = new Font("SansSerif", Font.BOLD, 160);
    
    private void animateColor(Graphics2D g2d) {
        titleColorForAnimated += 10;
        titleColorForAnimated %= 250;
        if (titleColorForAnimated < 100) titleColorForAnimated = 100;
        Color color = new Color(20, 20, titleColorForAnimated);
        g2d.setColor(color);
    }
    
    private int animateLineHeight = 200;
    private int animateLineWidth = 50;
    private int dx = 1;
    private int dy = 1;
    private int speed = 5;
    private int baseX = 200;
    private int baseY = 180;

    private void drawLine(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.fillOval(this.width / 4, this.height / 4, animateLineWidth, animateLineHeight);
        g2d.setColor(Color.GREEN);
        g2d.fillOval(this.width / 4 + 300, this.height / 4 + 100, animateLineWidth, animateLineHeight);
        this.animateLineWidth += (dx * speed);
        this.animateLineHeight += (dy * speed);
        if (this.animateLineWidth >= this.width) dx *= -1;
        if (this.animateLineHeight >= this.width) dy *= -1;
        if (this.animateLineWidth <= 30) dx *= -1;
        if (this.animateLineHeight <= 30) dy *= -1;
    }

    private void drawTitle(Graphics2D g2d) {
        g2d.setFont(font);
        if (this.height / 2 > this.tilteHeightForAnimated) this.tilteHeightForAnimated += 8;
        else animateColor(g2d);
        g2d.drawString("NeoBong", this.width / 2 - 100, this.tilteHeightForAnimated);
    }
    
    private Font descriptionFont = new Font("SansSerif", Font.BOLD, 30);
    private void drawDescription(Graphics2D g2d) {
        g2d.setFont(descriptionFont);
        g2d.setColor(Color.BLACK);
        g2d.drawString("何かキーを押してください", 30, this.height - 30);
    }
}
