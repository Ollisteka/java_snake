package main;

public class GameState {
    GameMap Map;
    boolean IsOver = false;
    boolean noPoison = true;
    int Scores;

    public GameState(int width, int height) {
         Map = new GameMap(width, height);
    }

    public GameState(int width, int height, boolean noPoison){
        this.noPoison = noPoison;
        Map = new GameMap(width, height);
    }
}
