package logic;

import java.util.Random;

public class GameMap {

  boolean isCycled = true;
  int foodCount;
  int poisonCount;
  private MapObject[][] map;
  private Random rnd = new Random();

  public GameMap(int width, int height, boolean cycled=false) {
    int[][] a = new int[width][height];
    map = new MapObject[width][height];
    isCycled = cycled;
    makeLevelOne();
  }
  // trying to work with git :)

  public int Width() {
    return map[0].length;
  }

  public int Height() {
    return map[1].length;
  }

  public void setObject(int x, int y, MapObject object) { // maybe better to use Location than "int x, int y"
    if (x >= Width() || y >= Height() || x < 0 || y < 0) {
      throw new IndexOutOfBoundsException();
    }
    map[x][y] = object;
  }

  public MapObject getObject(int x, int y) {
    if (x >= Width() || y >= Height() || x < 0 || y < 0) {
      throw new IndexOutOfBoundsException();
    }
    return map[x][y];
  }

  public MapObject getObject(Location location) {
    return getObject(location.x, location.y);
  }

  Location generateFood(boolean poison) {
    while (true) {
      int x = rnd.nextInt(Width() - 1);
      int y = rnd.nextInt(Height() - 1);
      if (map[x][y].Snake == null && map[x][y].Wall == null && map[x][y].Food == null) {
        Location result = new Location(x, y);
        map[x][y].Food = new Food(result, 10, poison);
        if (!poison)
          foodCount++;
        else
          poisonCount++;
        return result;
      }
    }

  }

  public void addSnake(Snake snake) {
    if (this.getObject(snake.getHead()).Snake != null
        || this.getObject(snake.getHead()).Wall != null
        || this.getObject(snake.getHead()).Food != null) {
      return;
    }
    this.getObject(snake.getHead()).Snake = snake;
  }

  public void addFood(Food food) {
    if (this.getObject(food.Location).Snake != null
        || this.getObject(food.Location).Wall != null
        || this.getObject(food.Location).Food != null) {
      generateFood(false);
      return;
    }
    this.getObject(food.Location).Food = food;
    if (!food.Poison) {
      foodCount++;
    } else {
      poisonCount++;
    }
  }

  void makeLevelOne() {
    for (int x = 0; x < Width(); x++) {
      for (int y = 0; y < Height(); y++) {
        this.setObject(x, y, new MapObject(x, y));
      }

    }
  }
}
