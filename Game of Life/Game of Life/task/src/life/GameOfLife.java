package life;

import life.controller.Game;
import life.view.View;

import javax.swing.*;

public class GameOfLife extends JFrame implements View {
    private final Game GAME;

    public GameOfLife() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        GAME = new Game(this);
    }

    public void update(Game game) {

    }
}