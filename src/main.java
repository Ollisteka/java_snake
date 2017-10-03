import gui.gameForm;
import logic.GameState;

public class main {
  public static void main(String[] args) {
    GameState gameState = new GameState(0);
    new gameForm(gameState);
  }
}
