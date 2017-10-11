package gui;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import logic.GameState;
import logic.Location;
import logic.MapObject;

class Cell implements Serializable {

  int x;
  int y;
  int width;
  int height;
  Color color;

  private static Map<String, Color> colors = new HashMap<String, Color>();

  static {
    colors.put("snake", Color.PINK);
    colors.put("head", Color.MAGENTA);
    colors.put("food", Color.GREEN);
    colors.put("poison", Color.RED);
    colors.put("wall", Color.BLACK);
    colors.put("nothing", Color.LIGHT_GRAY);
  }

  Cell(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  void UpdateColour(Location location, GameState game) {
    MapObject smth = game.map.getObject(location);
    color = colors.get(smth.getDrawable(location));
  }
}
