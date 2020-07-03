package life.view;

import life.controller.Game;

@FunctionalInterface
public interface View {

    void update(Game game);
}
