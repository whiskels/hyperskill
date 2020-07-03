package life.view;

import life.controller.Game;
import life.model.Cell;

public class TextView implements View {
    public void update(Game game) {
        Cell[][] currentState = game.getUNIVERSE().get();
        System.out.println(String.format("Generation: %d", game.getGeneration()));
        System.out.println(String.format("Alive: %d", game.getAliveCells()));

        for (int y = 0; y < game.getUNIVERSE().getSIDE(); y++) {
            for (int x = 0; x < game.getUNIVERSE().getSIDE(); x++) {
                printCell(currentState[y][x]);
            }
            System.out.println();
        }
    }

    public void printCell(Cell cell) {
        System.out.print(cell.isAlive() ? Cell.ALIVE_SYMBOL : Cell.DEAD_SYMBOL);
    }
}
