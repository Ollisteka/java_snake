package logic;

import java.io.Serializable;

public class Location implements Serializable {

  int x;
  int y;

  public Location(int x, int y) {
    this.x = x;
    this.y = y;
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
