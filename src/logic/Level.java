package logic;

import java.io.Serializable;

public enum Level implements Serializable {
  zero {
    @Override
    public GameMap drawMap(int width, int height, GameState game) {
      GameMap map = new GameMap(width, height, true);
      game.map = map;
      game.snake = new Snake(3, 3);
      map.addSnake(game.snake);
      map.generateFood(false);

      game.level = Level.zero;
      game.noPoison = true;

      return map;
    }
  },
  one {
    @Override
    public GameMap drawMap(int width, int height, GameState game) {
      GameMap map = new GameMap(width, height, false);

      map.setObject(new MapObject(new Wall(), 1, 1));

      game.snake = new Snake(3, 3);
      map.addSnake(game.snake);
      map.generateFood(false);

      game.level = Level.one;

      return map;
    }
  },
  two {
    @Override
    public GameMap drawMap(int width, int height, GameState game) {
      GameMap map = new GameMap(width, height, false);
      game.noPoison = false;

      game.snake = new Snake(3, 3);
      map.addSnake(game.snake);
      map.generateFood(false);

      game.level = Level.two;

      return map;
    }
  };
//  zero,
//  one

//  private Drawer drawer;
//  Level(Drawer drawer) {
//    this.drawer = drawer;
//  }
//
//  private static class Drawer{}

  public abstract GameMap drawMap(int width, int height, GameState game);
}
