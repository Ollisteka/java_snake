package logic;

public class MapObject {

  public Snake Snake;
  public Food Food;
  public Wall Wall;
  public Location Location;

  public MapObject(int x, int y) {
    Location = new Location(x, y);
  }

}