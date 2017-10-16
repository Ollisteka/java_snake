package logic;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import lombok.Getter;

public class Snake implements Serializable {

  private Deque<Location> body = new ArrayDeque<>();
  @Getter
  private Direction direction = Direction.Stop;

  private Random rnd = new Random();
  @Getter
  private int length = 1;

  public Snake(int x, int y) {
    body.addFirst(new Location(x, y));
  }

  public Location getHead() {
    return body.getFirst();
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
    game.map
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
    if (game.map.isCycled()) {
      return new Location(
          (oldLocation.getX() + dX + game.map.getWidth()) % game.map.getWidth(),
          (oldLocation.getY() + dY + game.map.getHeight()) % game.map.getHeight());
    }
    return new Location(oldLocation.getX() + dX, oldLocation.getY() + dY);
  }

  private void deleteTail(GameState game) {
    if (body.size() > getLength()) {
      Location leftover = body.removeLast();
      game.map.setObject(new MapObject(leftover));
    }
  }

  private boolean canEat(Location location, GameState game) {
    return game.map.getObject(location).canEat();
  }

  private void eat(Location location, GameState game) {
    Food food = game.map.getObject(location).getFood();
    game.map.setObject(new MapObject(location));
    if (!food.isPoison()) {
      game.map.setFoodCount(game.map.getFoodCount() - 1);
      setLength(length + 1);
    } else {
      game.map.setPoisonCount(game.map.getPoisonCount() - 1);
      setLength(length - 1);
      deleteTail(game);
    }
    makeFood(game);
    game.scores += food.getValue();
  }

  private void makeFood(GameState game) {
    while (game.map.getFoodCount() == 0) {
      int poison = rnd.nextInt();

      if (game.noPoison || game.map.getPoisonCount() == 1) {
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
