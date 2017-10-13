package logic;

import java.io.Serializable;

public class GameState implements Serializable {

  public GameMap map;
  public Snake snake;
  public boolean isOver = false;
  public boolean noPoison = true;
  public int scores;
  public Level level;

  public GameState(int width, int height, Snake snake) {
    map = new GameMap(width, height);
    this.snake = snake;
    map.addSnake(this.snake);
  }

  public GameState(int width, int height, Snake snake, boolean noPoison) {
    this.noPoison = noPoison;
    map = new GameMap(width, height);
    this.snake = snake;
    map.addSnake(this.snake);
  }

  // координаты x y нужны для добавления еды на эту локацию
  public GameState(GameMap map, Snake snake, Food food, Location location) {
    this.map = map;
    this.map.addFood(food, location.x, location.y);
    this.map.addSnake(snake);
    this.snake = snake;
    isOver = false;
    noPoison = true;
  }

  public GameState(Level level) {
    switch (level) {
      case one:
        map = Level.one.drawMap(15, 15, this);
        break;
      case two:
        map = Level.two.drawMap(12, 12, this);
        break;
      default:
        map = Level.zero.drawMap(10, 10, this);
        break;
    }
    isOver = false;
  }
}
