package logic;

public class Food {

  public int value;
  public boolean poison = false;
  public Location location;

  public Food(Location loc, int val, boolean poison) {
    location = loc;
    if (poison) {
      value = -val;
    } else {
      value = val;
    }
    this.poison = poison;
  }
}
