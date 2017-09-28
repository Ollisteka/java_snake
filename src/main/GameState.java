package main;

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
}
