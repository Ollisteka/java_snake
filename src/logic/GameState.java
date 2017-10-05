package logic;

public class GameState {

  public GameMap map;
  public Snake snake;
  public boolean isOver = false;
  public boolean noPoison = true;
  public int scores;
  public int level;

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

  public GameState(int level) {
    switch (level) {
      case 1:
        makeLevelOne(15, 15);
        break;
      default:
        noPoison = true;
        makeLevelZero(10, 10);
        break;
    }
    isOver = false;
  }

  private void makeLevelZero(int width, int height) {
    map = new GameMap(width, height, true);
    snake = new Snake(3, 3);
    map.addSnake(snake);
    map.generateFood(false);
    level = 0;
  }

  private void makeLevelOne(int width, int height) {
    map = new GameMap(width, height, false);
    snake = new Snake(3, 3);
    map.addSnake(snake);
    map.generateFood(false);
    level = 1;
  }
}
