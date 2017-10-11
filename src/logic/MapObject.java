package logic;

import java.io.Serializable;

public class MapObject implements Serializable {
  public Snake snake;
  public Food food;
  public Wall wall;

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
}