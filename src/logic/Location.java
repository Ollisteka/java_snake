package logic;

import java.io.Serializable;
import lombok.Getter;

public class Location implements Serializable {

  @Getter
  private int x;
  @Getter
  private int y;

  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }
  @Override
  public String toString(){
    return "" + x + ',' + y;
  }
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }
    if (!(other instanceof Location)) {
      return false;
    }
    Location otherLocation = (Location) other;
    return this.x == otherLocation.x && this.y == otherLocation.y;
  }
}
