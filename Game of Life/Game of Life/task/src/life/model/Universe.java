package life.model;

import java.util.ArrayList;
import java.util.List;

public class Universe {
    private Cell[][] field;
    private final int SIDE;
    private final List<Cell> CELLS;

    public Universe(int SIDE) {
        this.field = new Cell[SIDE][SIDE];
        this.SIDE = SIDE;
        this.CELLS = new ArrayList<>(SIDE*SIDE);
    }

    public Cell[][] get() {
        return field;
    }

    public int getSIDE() {
        return SIDE;
    }

    public void setCell(int x, int y, boolean isAlive) {
        field[y][x] = new Cell(x, y, isAlive);
        CELLS.add(field[y][x]);
    }

    public List<Cell> getCELLS() {
        return CELLS;
    }
}
