package main;

public class Location {
    public int x;
    public int y;
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Location))return false;
        Location otherLocation = (Location)other;
    return this.x == otherLocation.x && this.y == otherLocation.y;
    }
}
