package logic;

import java.io.Serializable;

public class Food implements Serializable {

  private int value;
  private boolean poison = false;
  public Location location;

  public boolean isPoison() {
    return poison;
  }

  public int getValue() {
    return value;
  }

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
