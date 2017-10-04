package logic;

public class GameState {

  public GameMap Map;
  public Snake Snake;
  public boolean IsOver = false;
  public boolean noPoison = true;
  public int Scores;

  public GameState(int width, int height, Snake snake) {
    Map = new GameMap(width, height);
    Snake = snake;
    Map.addSnake(Snake);
  }

  public GameState(int width, int height, Snake snake, boolean noPoison) {
    this.noPoison = noPoison;
    Map = new GameMap(width, height);
    Snake = snake;
    Map.addSnake(Snake);
  }

  public GameState(GameMap map, Snake snake, Food food) {
    Map = map;
    Map.addFood(food);
    Map.addSnake(snake);
    Snake = snake;
    IsOver = false;
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
    IsOver = false;
  }

  private void makeLevelZero(int width, int height) {
    Map = new GameMap(width, height, true);
    Snake = new Snake(3, 3);
    Map.addSnake(Snake);
    Map.generateFood(false);
  }

  private void makeLevelOne(int width, int height) {
    Map = new GameMap(width, height, false);
    Snake = new Snake(3, 3);
    Map.addSnake(Snake);
    Map.generateFood(false);
  }
}
