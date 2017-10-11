package logic;

import java.io.Serializable;

public class MapObject implements Serializable {

  private Snake snake;
  private Food food;
  private Wall wall;

  public MapObject() {

  }

  public MapObject(Snake snake) {
    this.snake = snake;
  }

  public MapObject(Food food) {
    this.food = food;
  }

  public MapObject(Wall wall) {
    this.wall = wall;
  }

  public String getDrawable(Location location) {
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