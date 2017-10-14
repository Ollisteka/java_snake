package logic;

import java.io.Serializable;
import lombok.Getter;

public class MapObject implements Serializable {

  private Snake snake;
  private Food food;
  private Wall wall;

  @Getter
  private Location location;

  public MapObject(int x, int y) {
    location = new Location(x, y);
  }

  public MapObject(Location loc) {
    location = loc;
  }

  public MapObject(Snake snake, int x, int y) {
    this.snake = snake;
    location = new Location(x, y);
  }

  public MapObject(Food food, int x, int y) {
    this.food = food;
    location = new Location(x, y);
  }

  public MapObject(Wall wall, int x, int y) {
    this.wall = wall;
    location = new Location(x, y);
  }

  public String getDrawable() {
    if (this.snake != null) {
      return this.snake.getHead().equals(location) ? "head" : "snake";
    } else if (this.food != null && !this.food.isPoison()) {
      return "food";
    } else if (this.food != null && this.food.isPoison()) {
      return "poison";
    } else if (this.wall != null) {
      return "wall";
    } else {
      return "nothing";
    }
  }

  public boolean willKillTheSnake() {
    return snake != null || wall != null;
  }

  public boolean canEat() {
    return food != null;
  }

  public boolean isFree() {
    return snake == null && food == null && wall == null;
  }

  public Food getFood() {
    return food;
  }
}