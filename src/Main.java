import gui.GameForm;
import javax.swing.SwingUtilities;
import logic.GameState;

public class Main {
  public static void main(String[] args) {
    GameState gameState = new GameState(0);
    SwingUtilities.invokeLater(new GameForm(gameState));
  }
}
