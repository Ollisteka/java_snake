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

  public GameState(GameMap map, Snake snake, Food food) {
    this.map = map;
    this.map.addFood(food);
    this.map.addSnake(snake);
    this.snake = snake;
    isOver = false;
    noPoison = true;
  }

  public GameState(Level level) {
    switch (level) {
      case one:
        makeLevelOne();
        break;
      default:
        noPoison = true;
        makeLevelZero();
        break;
    }
    isOver = false;
  }

  private void makeLevelZero() {
    //map = new GameMap(width, height, true);
    map = Level.zero.drawMap(10, 10);
    snake = new Snake(3, 3);
    map.addSnake(snake);
    map.generateFood(false);
    level = Level.zero;
  }

  private void makeLevelOne() {
    map = Level.one.drawMap(15, 15);
    snake = new Snake(3, 3);
    map.addSnake(snake);
    map.generateFood(false);
    level = Level.one;
  }
}
