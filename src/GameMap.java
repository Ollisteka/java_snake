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
    public void GenerateFood(boolean poison){
        throw new UnsupportedOperationException();
    }
}
