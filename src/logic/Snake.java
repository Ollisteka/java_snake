package logic;

import java.util.ArrayDeque;
import java.util.Random;

public class Snake {

  public ArrayDeque<Location> body = new ArrayDeque<>();
  public Direction direction = Direction.Stop;
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

  public void setLength(int value) {
    if (value <= 0) {
      return;
    }
    length = value;
  }

  public void move(Direction dir, GameState game) {
    Location newHead = findNextLocation(getHead(), dir, game);
    body.addFirst(newHead);
    if (willLose(getHead(), game) || game.map.width() * game.map.height() == body.size()) {
      game.isOver = true;
      return;
    }
    game.map.getObject(newHead).snake = this; //добавили голову на карту
    if (canEat(newHead, game)) {
      eat(newHead, game);
    }
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
          (oldLocation.x + dX + game.map.width()) % game.map.width(),
          (oldLocation.y + dY + game.map.height()) % game.map.height());
    }
    return new Location(oldLocation.x + dX, oldLocation.y + dY);
  }

  private void deleteTail(GameState game) {
    if (body.size() > getLength()) {
      Location leftover = body.removeLast();
      game.map.getObject(leftover).snake = null;
    }
  }

  private boolean canEat(Location location, GameState game) {
    return game.map.getObject(location).food != null;
  }

  private void eat(Location location, GameState game) {
    Food food = game.map.getObject(location).food;
    game.map.getObject(location).food = null;
    if (!food.poison) {
      game.map.foodCount--;
      setLength(length + 1);
    } else {
      game.map.poisonCount--;
      setLength(length - 1);
      deleteTail(game);
    }

    makeFood(game);
    game.scores += food.value;
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
      if (itemInNextPos.wall != null && !itemInNextPos.wall.canGoThrough
          || itemInNextPos.snake != null) {
        return true;
      }
    } catch (IndexOutOfBoundsException exc) {
      return true;
    }
    return false;
  }
}
