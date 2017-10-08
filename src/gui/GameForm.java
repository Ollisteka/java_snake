package gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import logic.GameState;
import logic.Level;

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
    JMenu menu = new JMenu("Choose level");
    menuBar.add(menu);
    addLevelMenu(menu, Level.zero);
    addLevelMenu(menu, Level.one);
  }

  private void addLevelMenu(JMenu menu, Level level) {
    JMenuItem item = new JMenuItem(level.toString());
    item.addActionListener(arg0 -> {
      gameFieldPanel.startNewGame(level);
      frame.pack();
    });
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
    frame.setVisible(true);
    frame.setResizable(false);
  }
}
