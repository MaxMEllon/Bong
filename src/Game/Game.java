package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import Base.BongPanel;
import Base.SoundPlayer;

public class Game extends BongPanel {
    
    private static final long serialVersionUID = -332145187L;
    private Status statusPlayer1;
    private Status statusPlayer2;
    private static final int STATUS_BOX_WIDTH = 100;
    private Field field;
    private ArrayList<Status> statusList;
    private final ArrayList<Bar> barList;
    final private ArrayList<Life> lifeList;

    public Game(int width, int height) {
        super(width, height);
        statusPlayer1 = new Status(0, 25, STATUS_BOX_WIDTH, this.height - 25, PlayerType.Player1);
        statusPlayer2 = new Status(this.width - STATUS_BOX_WIDTH, 25, STATUS_BOX_WIDTH, this.height - 25, PlayerType.Player2);
        statusList = new ArrayList<Status>(2);
        statusList.add(statusPlayer1);
        statusList.add(statusPlayer2);
        lifeList = new ArrayList<Life>();
        Life lifePlayer1 = new Life(0, 0, this.width / 2, 20, PlayerType.Player1);
        Life lifePlayer2 = new Life(this.width / 2, 0, this.width / 2, 20, PlayerType.Player2);
        lifeList.add(lifePlayer1);
        lifeList.add(lifePlayer2);
        for (Life life: lifeList) this.add(life);
        for (Status status: statusList) this.add(status);
        Runnable decreaseLifeOfPlayer1 = () -> lifePlayer1.decreaseLifePoint();
        Runnable decreaseLifeOfPlayer2 = () -> lifePlayer2.decreaseLifePoint();
        field = new Field(120, 25, this.width - 240, this.height - 25, statusList,
                decreaseLifeOfPlayer1, decreaseLifeOfPlayer2);
        this.add(field);
        barList = this.field.getBarList();
    }

    public PlayerType getResult() {
        if (this.lifeList.get(0).getLife() == 0) return PlayerType.Player2;
        if (this.lifeList.get(1).getLife() == 0) return PlayerType.Player1;
        return PlayerType.DEFAULT;
    }

    public void recieveKeyCodeForPlayer1(final int keyCode) {
        // A: 65, S: 83, D: 68, W:87, Q:81
        // ←: 37, ↑: 38, →: 39, ↓:40
        switch (keyCode) {
        case 83:
            barList.get(0).moveToDown();
            break;
        case 65:
            barList.get(0).moveToLeft();
            break;
        case 68:
            barList.get(0).moveToRight();
            break;
        case 69:
            Runnable warpBall = () -> this.field.warpBallByPlayer1();
            statusList.get(0).warpBall(warpBall);
            break;
        case 87:
            barList.get(0).moveToUp();
            break;
        case 81:
            Runnable showBall = () -> this.field.showBall();
            if (statusList.get(0).executeHiddenBall(showBall)) {
                SoundPlayer hiddenSound = new SoundPlayer("./Sounds/hidden.wav");
                hiddenSound.play();
                this.field.hiddenBallByPlayer1();
            }
            break;
        }
    }
    public void recieveKeyCodeForPlayer2(final int keyCode) {
        switch (keyCode) {
        case 37:
            barList.get(1).moveToLeft();
            break;
        case 39:
            barList.get(1).moveToRight();
            break;
        case 38:
            barList.get(1).moveToUp();
            break;
        case 40:
            barList.get(1).moveToDown();
            break;
        case 46:
            Runnable warpBall = () -> this.field.warpBallByPlayer2();
            statusList.get(1).warpBall(warpBall);
            break;
        case 47:
            Runnable showBall = () -> this.field.showBall();
            if (statusList.get(1).executeHiddenBall(showBall)) {
                SoundPlayer hiddenSound = new SoundPlayer("./Sounds/hidden.wav");
                hiddenSound.play();
                this.field.hiddenBallByPlayer2();
            }
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