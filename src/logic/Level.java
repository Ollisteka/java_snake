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
      return map;
    }
  },
  one {
    @Override
    public GameMap drawMap(int width, int height, GameState game) {
      GameMap map = new GameMap(width, height, false);

      MapObject obj = new MapObject();
      obj.wall = new Wall();
      map.setObject(1, 1, obj);

      game.snake = new Snake(3, 3);
      map.addSnake(game.snake);
      map.generateFood(false);

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
