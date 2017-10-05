package gui;

import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import logic.GameState;

public class GameForm implements Runnable {

  private JFrame frame;
  private GameFieldPanel gameFieldPanel;
  private JMenuBar menuBar = new JMenuBar();

  private GameState game;


  public GameForm(GameState game) {
    this.game = game;

  }

  private void initializeMenu(){
    menuBar = new JMenuBar();
    JMenu menu = new JMenu("Menu");
    menuBar.add(menu);
    JMenuItem item = new JMenuItem("Test Item");
    menu.add(item);
  }

  @Override
  public void run() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    gameFieldPanel = new GameFieldPanel(game, frame);
    initializeMenu();

    frame.add(gameFieldPanel);
    frame.setJMenuBar(menuBar);

    frame.pack();
    frame.setLocationByPlatform(true);
    frame.setResizable(false);
    frame.setVisible(true);
  }
}
