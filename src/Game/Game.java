package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import Base.BongPanel;

public class Game extends BongPanel {
    
    private static final long serialVersionUID = -332145187L;
    private Status statusPlayer1;
    private Status statusPlayer2;
    private static final int STATUS_BOX_WIDTH = 100;
    private Field field;
    private ArrayList<Status> statusList;
    private final ArrayList<Bar> barList;

    public Game(int width, int height) {
        super(width, height);
        statusPlayer1 = new Status(0, 25, STATUS_BOX_WIDTH, this.height - 25, PlayerType.Player1);
        statusPlayer2 = new Status(this.width - STATUS_BOX_WIDTH, 25, STATUS_BOX_WIDTH, this.height - 25, PlayerType.Player2);
        field = new Field(120, 25, this.width - 240, this.height - 25);
        statusList = new ArrayList<Status>(2);
        statusList.add(statusPlayer1);
        statusList.add(statusPlayer2);
        for (Status status: statusList) this.add(status);
        this.add(field);
        barList = this.field.getBarList();
    }

    public void recieveKeyCodeForPlayer1(final int keyCode) {
        // A: 65, S: 83, D: 68, W:87
        // ←: 37, ↑: 38, →: 39, ↓:40
        switch (keyCode) {
        case 83:
            barList.get(0).moveToDown();
            break;
        case 87:
            barList.get(0).moveToUp();
            break;
        }
    }
    public void recieveKeyCodeForPlayer2(final int keyCode) {
        switch (keyCode) {
        case 38:
            barList.get(1).moveToUp();
            break;
        case 40:
            barList.get(1).moveToDown();
            break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize(new Dimension(this.width, this.height));
        Graphics2D g2d = (Graphics2D) g;
        this.paintBackground(g2d);
    }
    
    protected void paintBackground(Graphics2D g2d) {
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, this.width, this.height);
    }
}