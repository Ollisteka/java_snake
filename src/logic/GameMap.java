package logic;

import java.io.Serializable;
import java.util.Random;

public class GameMap implements Serializable {

  public boolean isCycled = true;
  public int foodCount;
  int poisonCount;
  private MapObject[][] map;
  private Random rnd = new Random();

  public GameMap(int width, int height) {
    map = new MapObject[width][height];
    initializeDefaultMap();
  }

  public GameMap(int width, int height, boolean cycled) {
    map = new MapObject[width][height];
    isCycled = cycled;
    initializeDefaultMap();
  }

  public void initializeDefaultMap() {
    for (int x = 0; x < width(); x++) {
      for (int y = 0; y < height(); y++) {
        this.setObject(x, y, new MapObject());
      }
    }
  }

  public int width() { return map.length; }

  public int height() {
    return map[1].length;
  }

  public void setObject(int x, int y, MapObject object) {
    if (x >= width() || y >= height() || x < 0 || y < 0)
      throw new IndexOutOfBoundsException();
    map[x][y] = object;
  }

  public void setObject(Location location, MapObject object) {
    this.setObject(location.x, location.y, object);
  }

  public MapObject getObject(int x, int y) {
    if (x >= width() || y >= height() || x < 0 || y < 0)
      throw new IndexOutOfBoundsException();
    return map[x][y];
  }

  public MapObject getObject(Location location) {
    return getObject(location.x, location.y);
  }

  Location generateFood(boolean poison) {
    while (true) {
      int x = rnd.nextInt(width() - 1);
      int y = rnd.nextInt(height() - 1);
      if (map[x][y].snake == null && map[x][y].wall == null && map[x][y].food == null) {
        Location result = new Location(x, y);
        map[x][y].food = new Food(result, 10, poison);
        if (!poison)
          foodCount++;
        else
          poisonCount++;
        return result;
      }
    }
  }

  public void addSnake(Snake snake) {
    MapObject obj = this.getObject(snake.getHead());
    if (obj.snake != null
        || obj.wall != null
        || obj.food != null)
        return;
    obj.snake = snake;
  }

  public void addFood(Food food) {
    if (this.getObject(food.location).snake != null
        || this.getObject(food.location).wall != null
        || this.getObject(food.location).food != null) {
      generateFood(false);
      return;
    }
    this.getObject(food.location).food = food;
    if (!food.poison) {
      foodCount++;
    } else {
      poisonCount++;
    }
  }
}
