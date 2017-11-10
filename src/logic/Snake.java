package logic;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;

public class Snake implements Serializable {

  @Getter
  private Deque<Location> body = new ArrayDeque<>();
  @Getter
  @Setter
  private Direction direction = Direction.Stop;

  private Random rnd = new Random();
  @Getter
  private int length = 1;

  public Snake(int x, int y) {
    body.addFirst(new Location(x, y));
  }

  public Snake(Deque<Location> body){
    this.body = body;
  }

  public Location getHead() {
    return body.getFirst();
  }

  private void setLength(int value) {
    if (value > 0) {
      length = value;
    }
  }

  public void move(Direction dir, GameState game) {
    Location newHead = findNextLocation(getHead(), dir, game);
    if (willLose(newHead, game) || game.getMap().getWidth() * game.getMap().getHeight() == body
        .size()) {
      game.setOver(true);
      return;
    }
    body.addFirst(newHead);
    if (canEat(newHead, game)) {
      eat(newHead, game);
    }
    game.getMap()
        .setObject(new MapObject(this, newHead.getX(), newHead.getY())); //добавили голову на карту
    deleteTail(game);
  }

  private Location findNextLocation(Location oldLocation, Direction dir, GameState game) {
    int dX = 0;
    int dY = 0;
    switch (dir) {
      case Up:
        dY--;
        direction = Direction.Up;
        break;
      case Down:
        dY++;
        direction = Direction.Down;
        break;
      case Left:
        dX--;
        direction = Direction.Left;
        break;
      case Right:
        dX++;
        direction = Direction.Right;
        break;
      default:
        break;
    }
    if (game.getMap().isCycled()) {
      return new Location(
          (oldLocation.getX() + dX + game.getMap().getWidth()) % game.getMap().getWidth(),
          (oldLocation.getY() + dY + game.getMap().getHeight()) % game.getMap().getHeight());
    }
    return new Location(oldLocation.getX() + dX, oldLocation.getY() + dY);
  }

  private void deleteTail(GameState game) {
    if (body.size() > getLength()) {
      Location leftover = body.removeLast();
      game.getMap().setObject(new MapObject(leftover));
    }
  }

  private boolean canEat(Location location, GameState game) {
    return game.getMap().getObject(location).canEat();
  }

  private void eat(Location location, GameState game) {
    Food food = game.getMap().getObject(location).getFood();
    game.getMap().setObject(new MapObject(location));
    if (!food.isPoison()) {
      game.getMap().setFoodCount(game.getMap().getFoodCount() - 1);
      setLength(length + 1);
    } else {
      game.getMap().setPoisonCount(game.getMap().getPoisonCount() - 1);
      setLength(length - 1);
      deleteTail(game);
    }
    makeFood(game);
    game.setScores(game.getScores() + food.getValue());
  }

  private void makeFood(GameState game) {
    while (game.getMap().getFoodCount() == 0) {
      int poison = rnd.nextInt();

      if (game.isNoPoison() || game.getMap().getPoisonCount() == 1) {
        poison = 1;
      }
      game.getMap().generateFood(poison % 3 == 0);
    }
  }

  private boolean willLose(Location location, GameState game) {
    try {
      MapObject itemInNextPos = game.getMap().getObject(location);
      if (itemInNextPos.willKillTheSnake()) {
        return true;
      }
    } catch (IndexOutOfBoundsException exc) {
      return true;
    }
    return false;
  }
}
