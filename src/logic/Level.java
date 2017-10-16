package logic;

import java.io.Serializable;

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
  };

  public abstract GameMap drawMap(int width, int height, GameState game);
}
