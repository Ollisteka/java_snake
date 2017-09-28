package main;

import java.util.ArrayDeque;
import java.util.Random;

public class Snake {

  public ArrayDeque<Location> Body = new ArrayDeque<>();
  public Direction direction = Direction.Stop;
  private Random rnd = new Random();
  private int length = 1;

  public Snake(int x, int y) {
    Body.addFirst(new Location(x, y));
  }

  public Location getHead() {
    return Body.getFirst();
  }

  public int Length() {
    return length;
  }

  public void setLength(int value) {
    if (value <= 0) {
      return;
    }
    length = value;
  }

  public void Move(Direction dir, GameState game) {
    Location newHead = FindNextLocation(getHead(), dir, game); //позиция, на которую мы сдвинемся
    Body.addFirst(newHead); // добавили эту позицию в список
    if (WillLose(getHead(), game) || game.Map.Width() * game.Map.Height() == Body.size()) {
      game.IsOver = true;
      return;
    }
    game.Map.getObject(newHead).Snake = this; //добавили голову на карту
    if (CanEat(newHead, game)) {
      Eat(newHead, game);
    }
    DeleteTail(game);
  }

  private Location FindNextLocation(Location oldLocation, Direction dir, GameState game) {
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
    }
    if (game.Map.isCycled) {
      return new Location(
          (oldLocation.x + dX + game.Map.Width()) % game.Map.Width(),
          (oldLocation.y + dY + game.Map.Height()) % game.Map.Height());
    }
    return new Location(oldLocation.x + dX, oldLocation.y + dY);
  }

  private void DeleteTail(GameState game) {
    if (Body.size() > Length()) {
      Location leftover = Body.removeLast(); //убрали хвостик
      game.Map.getObject(leftover).Snake = null; //убрали хвостик с карты
    }
  }

  private boolean CanEat(Location location, GameState game) {
    return game.Map.getObject(location).Food != null;
  }

  private void Eat(Location location, GameState game) {
    Food food = game.Map.getObject(location).Food;
    game.Map.getObject(location).Food = null;
    if (!food.Poison) {
      game.Map.foodCount--;
      setLength(length + 1);
    } else {
      game.Map.poisonCount--;
      setLength(length - 1);
      DeleteTail(game);
    }

    MakeFood(game);
    game.Scores += food.Value;
  }

  private void MakeFood(GameState game) {
    while (game.Map.foodCount == 0) {
      int poison = rnd.nextInt();

      if (game.noPoison || game.Map.poisonCount == 1) {
        poison = 1;
      }
      game.Map.generateFood(poison % 3 == 0);
    }
  }

  private boolean WillLose(Location location, GameState game) {
    try {
      MapObject itemInNextPos = game.Map.getObject(location);
      if (itemInNextPos.Wall != null && !itemInNextPos.Wall.CanGoThrough
          || itemInNextPos.Snake != null) {
        return true;
      }
    } catch (IndexOutOfBoundsException exc) {
      return true;
    }
    return false;
  }
}
