import gui.GameForm;
import javax.swing.SwingUtilities;
import logic.GameState;
import logic.Level;

public class Main {
  public static void main(String[] args) {
    GameState gameState = new GameState(Level.one);
    SwingUtilities.invokeLater(new GameForm(gameState));
  }
}
