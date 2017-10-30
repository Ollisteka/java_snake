package gui;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import logic.GameState;
import logic.Location;
import logic.MapObject;
import lombok.Getter;

@Getter
class Cell implements Serializable {

  private static Map<String, Color> colors = new HashMap<String, Color>();

  static{
    colors.put("snake", Color.PINK);
    colors.put("head", Color.MAGENTA);
    colors.put("Food", Color.GREEN);
    colors.put("poison", Color.RED);
    colors.put("wall", Color.BLACK);
    colors.put("nothing", Color.LIGHT_GRAY);
  }

  private int x;
  private int y;
  private int width;
  private int height;
  private Color color;

  public Cell(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public void UpdateColour(Location location, GameState game) {
    MapObject smth = game.getMap().getObject(location);
    color = colors.get(smth.getDrawable());
  }
}
