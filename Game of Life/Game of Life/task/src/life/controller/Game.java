package life.controller;

import life.model.*;
import life.view.TextView;
import life.view.View;

import java.util.Random;

public class Game {
    private final Universe UNIVERSE;
    private final View VIEW;
    private final int SIDE;
    private final long SEED;
    private int generation, aliveCells;
    private static final int GENERATIONS_TO_CREATE = 10;
    private final Random random;

    public Game(View view) {
        SEED = new Random().nextInt();
        random = new Random(SEED);
        SIDE = new Random(25).nextInt();
        UNIVERSE = new Universe(SIDE);
        VIEW = view;
    }

    public void run() {
        createInitialState();
        printState();
        findNeighbors();


        if (GENERATIONS_TO_CREATE != 0) {
            while (generation <= GENERATIONS_TO_CREATE) {
                try {
                    createNextGeneration();
                    generation++;
                    printState();

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void printState() {
        VIEW.update(this);
    }

    private void createInitialState() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                UNIVERSE.setCell(x, y, random.nextBoolean());
            }
        }
        generation = 1;
        aliveCells = (int) UNIVERSE.getCELLS().stream()
                .filter(Cell::isAlive)
                .count();
    }


    private void findNeighbors() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                findNeighbors(UNIVERSE.get()[y][x]);
            }
        }
    }

    private void findNeighbors(Cell cell) {
        final int cellY = cell.getY();
        final int cellX = cell.getX();

        for (int y = cellY - 1; y <= cellY + 1; y++) {
            for (int x = cellX - 1; x <= cellX + 1; x++) {
                int i = y;
                int j = x;
                i = i < 0 ? SIDE - 1 : i;
                i = i >= SIDE ? 0 : i;
                j = j < 0 ? SIDE - 1 : j;
                j = j >= SIDE ? 0 : j;

                if (i == cellY && j == cellX) {
                    continue;
                } else {
                    cell.addNeighbor(UNIVERSE.get()[i][j]);
                }
            }
        }
    }

    private void createNextGeneration() {
        countAliveNeighors();
        evolve();
    }

    private void countAliveNeighors() {
        UNIVERSE.getCELLS().forEach(this::countAliveNeighbors);
    }

    private void evolve() {
        UNIVERSE.getCELLS().forEach(this::evolve);
    }

    private void countAliveNeighbors(Cell cell) {
        final int aliveNeighbors = (int) cell.getNeighbors().stream()
                .filter(Cell::isAlive)
                .count();

        cell.setAliveNeighbors(aliveNeighbors);
    }

    private void evolve(Cell cell) {
        final int aliveNeighbors = cell.getAliveNeighbors();

        if (cell.isAlive() && (aliveNeighbors > 3 || aliveNeighbors < 2)) {
            aliveCells--;
            cell.invertState();
        } else if (!cell.isAlive() && aliveNeighbors == 3) {
            aliveCells++;
            cell.invertState();
        }
    }

    public Universe getUNIVERSE() {
        return UNIVERSE;
    }

    public int getGeneration() {
        return generation;
    }

    public int getAliveCells() {
        return aliveCells;
    }
}
