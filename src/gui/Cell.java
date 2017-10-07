package gui;

import java.awt.Color;
import logic.GameState;
import logic.Location;
import logic.MapObject;

class Cell {

  int x;
  int y;
  int width;
  int height;
  Color color;

  Cell(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  void UpdateColour(Location location, GameState game) {
    MapObject smth = game.map.getObject(location);

    if (smth.snake != null) {
      color = smth.snake.getHead().equals(location) ? Color.MAGENTA : Color.PINK;
    } else if (smth.food != null && !smth.food.poison) {
      color = Color.GREEN;
    } else if (smth.food != null && smth.food.poison) {
      color = Color.black;
    } else if (smth.wall != null && !smth.wall.canGoThrough) {
      color = Color.DARK_GRAY;
    } else if (smth.wall != null && smth.wall.canGoThrough) {
      color = Color.GRAY;
    } else {
      color = Color.LIGHT_GRAY;
    }
  }
}
