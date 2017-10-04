package logic;

public class MapObject {

  public Snake snake;
  public Food food;
  public Wall wall;
  public Location location;

  public MapObject(int x, int y) {
    location = new Location(x, y);
  }

}