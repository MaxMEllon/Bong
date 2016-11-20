package Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Base.BongPanel;

public class Field extends BongPanel {

    private Ball ball;
    final private ArrayList<Bar> barList;
    final private ArrayList<Lane> laneList;
    private final static int BALL_SIZE = 40;
    private final static int LANE_NUMBER = 5;

    public Field(int x, int y, int width, int height, ArrayList<Status> statusList,
            Runnable decreaseLifeOfPlayer1, Runnable decreaseLifeOfPlayer2) {
        super(x, y, width, height);
        this.barList = new ArrayList<Bar>();
        final Dimension fieldSize = new Dimension(this.width, this.height);
        Bar barPlayer1 = new Bar(80, this.height / 2 - 50, 20, 150, PlayerType.Player1, fieldSize);
        Bar barPlayer2 = new Bar(this.width - 100, this.height / 2 - 50, 20, 150, PlayerType.Player2, fieldSize);
        barList.add(barPlayer1);
        barList.add(barPlayer2);
        for (Bar bar: barList) this.add(bar);
        Runnable addSkillPointOfPlayer1 = () -> statusList.get(0).increaseSkillPoint();
        Runnable addSkillPointOfPlayer2 = () -> statusList.get(1).increaseSkillPoint();
        this.ball = new Ball(this.width / 2 - BALL_SIZE / 2, this.height / 2 - BALL_SIZE / 2,
                BALL_SIZE, BALL_SIZE, this.width, this.height, BALL_SIZE, barList,
                addSkillPointOfPlayer1, addSkillPointOfPlayer2,
                decreaseLifeOfPlayer1, decreaseLifeOfPlayer2);
        this.add(this.ball);
        this.laneList = new ArrayList<Lane>();
        final int laneRange = this.height / LANE_NUMBER;
        for (int i = 0; i < LANE_NUMBER; i++) {
            Lane lane = new Lane(0, laneRange * i, this.width, laneRange);
            laneList.add(lane);
        }
        for (Lane lane: laneList) this.add(lane);
    }

    private static final long serialVersionUID = -5896210034085805208L;

    public ArrayList<Bar> getBarList() {
        return barList;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        for (Lane lane: laneList) lane.updateIfActive(this.ball);
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        this.paintBackground(g2d);
    }
    
    public void showBall() {
        this.ball.showIfNeed();
    }

    public void hiddenBallByPlayer1() {
        this.ball.hiddenIfNeed(PlayerType.Player1);
    }

    public void hiddenBallByPlayer2() {
        this.ball.hiddenIfNeed(PlayerType.Player2);
    }
    
    @Override
    protected void paintBackground(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, this.width, this.height);
    }
}
