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

  private void initializeDefaultMap() {
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        this.setObject(x, y, new MapObject());
      }
    }
  }

  public int getWidth() {
    return map.length;
  }

  public int getHeight() {
    return map[1].length;
  }

  public void setObject(int x, int y, MapObject object) {
    if (x >= getWidth() || y >= getHeight() || x < 0 || y < 0)
      throw new IndexOutOfBoundsException();
    map[x][y] = object;
  }

  public void setObject(Location location, MapObject object) {
    this.setObject(location.x, location.y, object);
  }

  public MapObject getObject(int x, int y) {
    if (x >= getWidth() || y >= getHeight() || x < 0 || y < 0)
      throw new IndexOutOfBoundsException();
    return map[x][y];
  }

  public MapObject getObject(Location location) {
    return getObject(location.x, location.y);
  }

  Location generateFood(boolean poison) {
    while (true) {
      int x = rnd.nextInt(getWidth() - 1);
      int y = rnd.nextInt(getHeight() - 1);
      if (this.getObject(x, y).isFree()) {
        Location result = new Location(x, y);
        this.setObject(x, y, new MapObject(new Food(result, 10, poison)));
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
    if (!obj.isFree())
        return;
    this.setObject(snake.getHead(), new MapObject(snake));
  }

  public void addFood(Food food) {
    Location location = food.location;
    if (!this.getObject(location).isFree()) {
      generateFood(food.isPoison());
      return;
    }
    this.setObject(location, new MapObject(food));
    if (!food.isPoison()) {
      foodCount++;
    } else {
      poisonCount++;
    }
  }
}
