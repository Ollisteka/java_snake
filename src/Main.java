import gui.GameForm;
import logic.GameState;
import logic.Level;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    GameState gameState = new GameState(Level.one);
    SwingUtilities.invokeLater(new GameForm(gameState));
  }
}
