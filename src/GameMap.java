import java.util.Random;

public class GameMap {
    private MapObject [][] map;
    boolean isCycled = true;
    int foodCount;
    int poisonCount;
    private Random rnd = new Random();

    public GameMap(int width, int height){
        map = new MapObject[width][height];
    }

    int Width(){
        return map[0].length;
    }
    int Height(){
        return map[1].length;
    }

    public void setObject(int x, int y, MapObject object){
        map[x][y] = object;
    }

    MapObject getObject(int x, int y){
        return map[x][y];
    }

    MapObject getObject(Location location){
        return getObject(location.x, location.y);
    }

    Location generateFood(boolean poison){
        while (true)
        {
            int x = rnd.nextInt(Width() - 1);
            int y = rnd.nextInt(Height() - 1);
            if (map[x][y].Snake == null && map[x][y].Wall == null && map[x][y].Food == null)
            {
                Location result = new Location(x, y);
                map[x][y].Food = new Food(result, 10, poison);
                if (!poison)
                    foodCount++;
                else poisonCount++;
                return result;
            }
        }
    }
}
