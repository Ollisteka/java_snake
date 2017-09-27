import java.util.Random;

public class GameMap {
    private MapObject [][] map = new MapObject[][]{};
    public void setObject(int x, int y, MapObject object){
        map[x][y] = object;
    }
    public MapObject getObject(int x, int y){
        return map[x][y];
    }
    public MapObject getObject(Location location){
        return getObject(location.x, location.y);
    }
    public int Width(){
        return map[0].length;
    }
    public int Height(){
        return map[1].length;
    }
    public boolean isCycled = true;
    public int FoodCount;
    public int PoisonCount;
    private Random rnd = new Random();

    public Location GenerateFood(boolean poison){
        while (true)
        {
            int x = rnd.nextInt(Width() - 1);
            int y = rnd.nextInt(Height() - 1);
            if (map[x][y].Snake == null && map[x][y].Wall == null && map[x][y].Food == null)
            {
                Location result = new Location(x, y);
                map[x][y].Food = new Food(result, 10, poison);
                if (!poison)
                    FoodCount++;
                else PoisonCount++;
                return result;
            }
        }
    }
}
