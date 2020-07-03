package life.model;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    public static final String ALIVE_SYMBOL = "O";
    public static final String DEAD_SYMBOL = " ";
    private int x;
    private int y;
    private boolean isAlive;
    private int aliveNeighbors;
    private final List<Cell> neighbors;


    public Cell(int x, int y, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
        this.neighbors = new ArrayList<>();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void invertState() {
        isAlive = !isAlive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Cell> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Cell cell) {
        neighbors.add(cell);
    }

    public int getAliveNeighbors() {
        return aliveNeighbors;
    }

    public void setAliveNeighbors(int aliveNeighbors) {
        this.aliveNeighbors = aliveNeighbors;
    }
}
