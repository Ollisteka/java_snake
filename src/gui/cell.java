package gui;
import java.awt.Color;
import java.util.Iterator;
import javax.swing.JFrame;
import logic.GameState;
import logic.Location;
import logic.MapObject;

public class cell {
  public int X;
  public int Y;
  public int Width;
  public int Height;
  public Color color;

  public cell(int x, int y, int width, int height)
  {
    X = x;
    Y = y;
    Width = width;
    Height = height;
  }
  public void UpdateColour(Location location, GameState game, JFrame form)
  {
    MapObject smth = game.Map.getObject(location);

    if (smth.Snake != null)
    {
      Location[] body = new Location[smth.Snake.Body.size()];
      int count = 0;
      for (Location aBody : smth.Snake.Body) {
        body[count++] = aBody;
      }
      color = body[body.length - 1].equals(smth.Location) ? Color.PINK : Color.MAGENTA;
    }
    else if (smth.Food != null && !smth.Food.Poison)
      color = Color.GREEN;
    else if (smth.Food != null && smth.Food.Poison)
      color = Color.black;
    else if (smth.Wall != null && !smth.Wall.CanGoThrough)
      color = Color.DARK_GRAY;
    else if (smth.Wall != null && smth.Wall.CanGoThrough)
      color = Color.GRAY;
    else
    {
      color = Color.LIGHT_GRAY;
    }
  }
}
