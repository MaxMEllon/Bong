package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Base.BongPanel;
import Base.SoundPlayer;

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
    
    public void increaseSkillPoint() {
        if (this.power < GAGE_MAX_RANGE) this.power++;
    }
    
    public void warpBall(Runnable callback) {
        if (power >= 3) {
            power -= 3;
            callback.run();
        }
    }
    
    private boolean isExecuteSkill = false;
    private Runnable showBall;
    public boolean executeHiddenBall(Runnable showBall) {
        if (this.power == GAGE_MAX_RANGE && !this.isExecuteSkill) this.isExecuteSkill = true;
        this.showBall = showBall;
        return this.isExecuteSkill;
    }
    
    private int colorForAnimation = 50;
    private int powerHeight = 0;
    private void paintGauge(Graphics2D g2d) {
        Color color = playerType == PlayerType.Player1 ? Color.BLUE : Color.RED;
        if (power == GAGE_MAX_RANGE) {
            colorForAnimation += 6;
            colorForAnimation %= 255;
            if (colorForAnimation < 50) colorForAnimation = 50;
            color = playerType == PlayerType.Player1
                    ? new Color(50, 50 ,colorForAnimation)
                    : new Color(colorForAnimation, 50, 50);
        }
        if (this.isExecuteSkill) {
            this.powerHeight -= 2;
            if (this.powerHeight <= 0) {
                this.power = 0;
                this.isExecuteSkill = false;
                if (this.showBall != null) showBall.run();
            }
        } else {
            powerHeight = power * (this.height / GAGE_MAX_RANGE);
        }
        g2d.setColor(color);
        g2d.fillRect(0, this.height - powerHeight, this.width, powerHeight);
    }
}
