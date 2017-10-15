package logic;

import java.io.Serializable;
import java.util.Random;

public class GameMap implements Serializable {

  private boolean isCycled = true;
  private int foodCount;
  private int poisonCount;

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
    int x = object.getLocation().x;
    int y = object.getLocation().y;
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
    return getObject(location.x, location.y);
  }

  Location generateFood(boolean poison) {
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
    this.setObject(new MapObject(snake, location.x, location.y));
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

    public boolean isCycled() {
        return this.isCycled;
    }

    public int getFoodCount() {
        return this.foodCount;
    }

    public int getPoisonCount() {
        return this.poisonCount;
    }

    //    public MapObject[][] getMap() {
//        return this.map;
//    }

//    public Random getRnd() {
//        return this.rnd;
//    }
//
//    public void setCycled(boolean isCycled) {
//        this.isCycled = isCycled;
//    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public void setPoisonCount(int poisonCount) {
        this.poisonCount = poisonCount;
    }

//    public void setMap(MapObject[][] map) {
//        this.map = map;
//    }
//
//    public void setRnd(Random rnd) {
//        this.rnd = rnd;
//    }
}
