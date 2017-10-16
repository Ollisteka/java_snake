package logic;

import java.io.Serializable;
import java.util.Random;
import lombok.Getter;

public class GameMap implements Serializable {

  @Getter
  private boolean isCycled = true;
  @Getter
  private int foodCount;
  @Getter
  private int poisonCount;

  public void setFoodCount(int foodCount) {
    if (foodCount < 0) {
      return;
    }
    this.foodCount = foodCount;
  }

  public void setPoisonCount(int poisonCount) {
    if (poisonCount < 0) {
      return;
    }
    this.poisonCount = poisonCount;
  }

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
        this.setObject(new MapObject(x, y));
      }
    }
  }

  public int getWidth() {
    return map.length;
  }

  public int getHeight() {
    return map[1].length;
  }

  public void setObject(MapObject object) {
    int x = object.getLocation().getX();
    int y = object.getLocation().getY();
    if (x >= getWidth() || y >= getHeight() || x < 0 || y < 0)
      throw new IndexOutOfBoundsException();
    map[x][y] = object;
  }

  public MapObject getObject(int x, int y) {
    if (x >= getWidth() || y >= getHeight() || x < 0 || y < 0)
      throw new IndexOutOfBoundsException();
    return map[x][y];
  }

  public MapObject getObject(Location location) {
    return getObject(location.getX(), location.getY());
  }

  public Location generateFood(boolean poison) {
    while (true) {
      int x = rnd.nextInt(getWidth() - 1);
      int y = rnd.nextInt(getHeight() - 1);
      if (this.getObject(x, y).isFree()) {
        this.setObject(new MapObject(new Food(10, poison), x, y));
        if (!poison)
          foodCount++;
        else
          poisonCount++;
        return new Location(x, y);
      }
    }
  }

  public void addSnake(Snake snake) {
    Location location = snake.getHead();
    MapObject obj = this.getObject(location);
    if (!obj.isFree())
        return;
    this.setObject(new MapObject(snake, location.getX(), location.getY()));
  }

  public void addFood(Food food, int x, int y) {
    Location location = new Location(x, y);
    if (!this.getObject(location).isFree()) {
      generateFood(food.isPoison());
      return;
    }
    this.setObject(new MapObject(food, x, y));
    if (!food.isPoison()) {
      foodCount++;
    } else {
      poisonCount++;
    }
  }


}
