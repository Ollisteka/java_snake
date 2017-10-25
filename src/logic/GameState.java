package logic;

import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.swing.Timer;
import lombok.Getter;
import lombok.Setter;


public class GameState implements Serializable {

  @Getter @Setter
  private GameMap map;
  @Getter @Setter
  private Snake snake;
  @Getter @Setter
  private boolean isOver = false;
  @Getter @Setter
  private boolean noPoison = true;
  @Getter @Setter
  private int scores;
  @Getter @Setter
  private Level level;

  @Getter
  private final int delay = 100;
  private final int magic = 3;
  private Timer timer;
  private int timerTick;
  @Getter @Setter
  private boolean isPaused = false;
  @Getter @Setter
  private boolean readyToRestart = false;

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
    this.map.addFood(food, location.getX(), location.getY());
    this.map.addSnake(snake);
    this.snake = snake;
    isOver = false;
    noPoison = true;
  }

  public GameState(Level level) {
    switch (level) {
      case one:
        map = Level.one.drawMap(15, 15, this);
        this.level = Level.one;
        break;
      case two:
        map = Level.two.drawMap(12, 12, this);
        this.level = Level.two;
        break;
      default:
        map = Level.zero.drawMap(10, 10, this);
        this.level = Level.zero;
        break;
    }
    isOver = false;
  }

  public void startTimer() {
    timer = new Timer(delay, this::makeMove);
    timer.start();
  }

  public void makeMove(ActionEvent event) {
    if (this.isPaused || this.isOver) {
      return;
    }
    if (snake.getDirection() != Direction.Stop && timerTick != 0 && timerTick % magic == 0) {
      snake.move(snake.getDirection(), this);
      timerTick = 0;
    } else {
      timerTick++;
    }
  }
}
