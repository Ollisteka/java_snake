package logic;

import java.io.Serializable;
import lombok.Getter;

public class Food implements Serializable {

  @Getter
  private int value;
  @Getter
  private boolean poison = false;

  public Food(int val, boolean poison) {
    if (poison) {
      value = -val;
    } else {
      value = val;
    }
    this.poison = poison;
  }
}
