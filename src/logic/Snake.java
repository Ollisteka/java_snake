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
    if (willLose(getHead(), game) || game.Map.width() * game.Map.height() == body.size()) {
      game.IsOver = true;
      return;
    }
    game.Map.getObject(newHead).snake = this; //добавили голову на карту
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
    if (game.Map.isCycled) {
      return new Location(
          (oldLocation.x + dX + game.Map.width()) % game.Map.width(),
          (oldLocation.y + dY + game.Map.height()) % game.Map.height());
    }
    return new Location(oldLocation.x + dX, oldLocation.y + dY);
  }

  private void deleteTail(GameState game) {
    if (body.size() > getLength()) {
      Location leftover = body.removeLast();
      game.Map.getObject(leftover).snake = null;
    }
  }

  private boolean canEat(Location location, GameState game) {
    return game.Map.getObject(location).food != null;
  }

  private void eat(Location location, GameState game) {
    Food food = game.Map.getObject(location).food;
    game.Map.getObject(location).food = null;
    if (!food.poison) {
      game.Map.foodCount--;
      setLength(length + 1);
    } else {
      game.Map.poisonCount--;
      setLength(length - 1);
      deleteTail(game);
    }

    makeFood(game);
    game.Scores += food.value;
  }

  private void makeFood(GameState game) {
    while (game.Map.foodCount == 0) {
      int poison = rnd.nextInt();

      if (game.noPoison || game.Map.poisonCount == 1) {
        poison = 1;
      }
      game.Map.generateFood(poison % 3 == 0);
    }
  }

  private boolean willLose(Location location, GameState game) {
    try {
      MapObject itemInNextPos = game.Map.getObject(location);
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
