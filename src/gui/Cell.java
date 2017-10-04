package gui;

import java.awt.Color;
import javax.swing.JFrame;
import logic.GameState;
import logic.Location;
import logic.MapObject;

public class Cell {

  public int X;
  public int Y;
  public int Width;
  public int Height;
  public Color color;

  public Cell(int x, int y, int width, int height) {
    X = x;
    Y = y;
    Width = width;
    Height = height;
  }

  public void UpdateColour(Location location, GameState game, JFrame form) {
    MapObject smth = game.Map.getObject(location);

    if (smth.snake != null) {
      Location[] body = new Location[smth.snake.body.size()];
      int count = 0;
      for (Location aBody : smth.snake.body) {
        body[count++] = aBody;
      }
      color = smth.snake.getHead().equals(smth.location) ? Color.MAGENTA : Color.PINK;
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
