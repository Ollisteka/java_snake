package gui;

import javax.swing.JFrame;
import logic.GameState;

public class GameForm implements Runnable {

  private JFrame frame;
  private GameFieldPanel gameFieldPanel;

  private GameState game;


  public GameForm(GameState game) {
    this.game = game;

  }

  @Override
  public void run() {
    frame = new JFrame("Snake");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gameFieldPanel = new GameFieldPanel(game, frame);
    frame.add(gameFieldPanel);
    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setVisible(true);
  }
}
