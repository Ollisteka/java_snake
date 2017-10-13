package logic;

import java.io.Serializable;

public class Food implements Serializable {

  private int value;
  private boolean poison = false;

  public boolean isPoison() {
    return poison;
  }

  public int getValue() {
    return value;
  }

  public Food(int val, boolean poison) {
    if (poison) {
      value = -val;
    } else {
      value = val;
    }
    this.poison = poison;
  }
}
