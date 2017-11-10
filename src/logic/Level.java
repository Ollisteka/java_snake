package logic;

import java.io.Serializable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public enum Level implements Serializable {
  zero {
    @Override
    public GameMap drawMap(int width, int height, GameState game) {
      GameMap map = new GameMap(width, height, true);
      game.setMap(map);
      game.setSnake(new Snake(3, 3));
      map.addSnake(game.getSnake());
      map.generateFood(false);

      game.setLevel(Level.zero);
      game.setNoPoison(true);

      return map;
    }

    @Override
    public int getValue() {
      return 0;
    }
  },
  one {
    @Override
    public GameMap drawMap(int width, int height, GameState game) {
      GameMap map = new GameMap(width, height, false);

      map.setObject(new MapObject(new Wall(), 1, 1));

      game.setSnake(new Snake(3, 3));
      map.addSnake(game.getSnake());
      map.generateFood(false);

      game.setLevel(Level.one);

      return map;
    }

    @Override
    public int getValue() {
      return 1;
    }
  },
  two {
    @Override
    public GameMap drawMap(int width, int height, GameState game) {
      GameMap map = new GameMap(width, height, false);
      game.setNoPoison(false);

      game.setSnake(new Snake(3, 3));
      map.addSnake(game.getSnake());
      map.generateFood(false);

      game.setLevel(Level.two);

      return map;
    }

    @Override
    public int getValue() {
      return 2;
    }

  },
  three {
    @Override
    public GameMap drawMap(int width, int height, GameState game) {
      throw new NotImplementedException();
    }

    @Override
    public int getValue() {
      return 3;
    }
  };

  public abstract GameMap drawMap(int width, int height, GameState game);

  public abstract int getValue();
}
