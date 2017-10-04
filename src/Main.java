import gui.GameForm;
import logic.GameState;

public class Main {
  public static void main(String[] args) {
    GameState gameState = new GameState(0);
    new GameForm(gameState);
  }
}
