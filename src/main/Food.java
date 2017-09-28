package main;

public class Food {

  public int Value;
  public boolean Poison;
  public Location Location;

  public Food(Location loc, int val, boolean poison) {
    Location = loc;
    if (poison) {
      Value = -val;
    } else {
      Value = val;
    }
    Poison = poison;
  }
}
