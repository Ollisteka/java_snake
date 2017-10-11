package logic;

import java.io.Serializable;

public enum Level implements Serializable {
  zero {
    @Override
    public GameMap drawMap(int width, int height) {
      GameMap map = new GameMap(width, height, true);
      return map;
    }
  },
  one {
    @Override
    public GameMap drawMap(int width, int height) {
      GameMap map = new GameMap(width, height, false);

      MapObject obj = new MapObject();
      obj.wall = new Wall();
      map.setObject(1, 1, obj);

      return map;
    }
  },
  two {
    @Override
    public GameMap drawMap(int width, int height) {
      GameMap map = new GameMap(width, height, false);
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

  public abstract GameMap drawMap(int width, int height);
}
