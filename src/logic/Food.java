package logic;

import java.io.Serializable;

public class Food implements Serializable {

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
