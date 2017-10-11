package logic;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class Snake implements Serializable {

  private Deque<Location> body = new ArrayDeque<>();
  private Direction direction = Direction.Stop;

  public Direction getDirection() {
    return direction;
  }
  private Random rnd = new Random();
  private int length = 1;

  public Snake(int x, int y) {
    body.addFirst(new Location(x, y));
  }

  public Location getHead() {
    return body.getFirst();
  }

  public int getLength() {
    return length;
  }

  private void setLength(int value) {
    if (value > 0)
      length = value;
  }

  public void move(Direction dir, GameState game) {
    Location newHead = findNextLocation(getHead(), dir, game);
    if (willLose(newHead, game) || game.map.getWidth() * game.map.getHeight() == body.size()) {
      game.isOver = true;
      return;
    }
    body.addFirst(newHead);
    if (canEat(newHead, game)) {
      eat(newHead, game);
    }
    game.map.setObject(newHead, new MapObject(this)); //добавили голову на карту
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
    if (game.map.isCycled) {
      return new Location(
          (oldLocation.x + dX + game.map.getWidth()) % game.map.getWidth(),
          (oldLocation.y + dY + game.map.getHeight()) % game.map.getHeight());
    }
    return new Location(oldLocation.x + dX, oldLocation.y + dY);
  }

  private void deleteTail(GameState game) {
    if (body.size() > getLength()) {
      Location leftover = body.removeLast();
      game.map.setObject(leftover, new MapObject());
    }
  }

  private boolean canEat(Location location, GameState game) {
    return game.map.getObject(location).canEat();
  }

  private void eat(Location location, GameState game) {
    Food food = game.map.getObject(location).getFood();
    game.map.setObject(location, new MapObject());
    if (!food.isPoison()) {
      game.map.foodCount--;
      setLength(length + 1);
    } else {
      game.map.poisonCount--;
      setLength(length - 1);
      deleteTail(game);
    }
    makeFood(game);
    game.scores += food.getValue();
  }

  private void makeFood(GameState game) {
    while (game.map.foodCount == 0) {
      int poison = rnd.nextInt();

      if (game.noPoison || game.map.poisonCount == 1) {
        poison = 1;
      }
      game.map.generateFood(poison % 3 == 0);
    }
  }

  private boolean willLose(Location location, GameState game) {
    try {
      MapObject itemInNextPos = game.map.getObject(location);
      if (itemInNextPos.willKillTheSnake()) {
        return true;
      }
    } catch (IndexOutOfBoundsException exc) {
      return true;
    }
    return false;
  }
}
