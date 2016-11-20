package Result;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Base.BongPanel;
import Game.PlayerType;

public class Result extends BongPanel {

    private final PlayerType playerType;
    private final Color rightBlue = new Color(200,200,255);
    private final Color rightRed = new Color(255,200,200);
    public Result(int width, int height, PlayerType playerType) {
        super(width, height);
        this.playerType = playerType;
    }

    private static final long serialVersionUID = -6493405599668583533L;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.paintBackground(g2d);
    }
    
    private Font font = new Font("SansSerif", Font.BOLD, 160);
    @Override
    protected void paintBackground(Graphics2D g2d) {
        Color color = this.playerType == PlayerType.Player1 ? rightBlue : rightRed;
        g2d.setColor(color);
        g2d.fillRect(0, 0, this.width, this.height);
        String playerName = this.playerType == PlayerType.Player1 ? "Player1" : "Player2";
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(playerName + "の勝利", 0, this.height / 2 - 80);
    }

}
