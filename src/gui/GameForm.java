package gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
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
  private static Map<Level, String> levels = new HashMap<Level, String>();

  static {
    levels.put(Level.zero, "lvl_0.snk");
    levels.put(Level.one, "lvl_1.snk");
    levels.put(Level.two, "lvl_2.snk");
  }

  private String saveName = "save.snk";

  public GameForm(GameState game) {
    this.game = game;
  }

  private void initializeMenu() {
    menuBar = new JMenuBar();
    JMenu chooseLevelMenu = new JMenu("Choose level");
    menuBar.add(chooseLevelMenu);
    addLevelMenu(chooseLevelMenu, Level.zero);
    addLevelMenu(chooseLevelMenu, Level.one);
    addLevelMenu(chooseLevelMenu, Level.two);

    JMenu gameMenu = new JMenu("game");
    menuBar.add(gameMenu);

    JMenuItem save = new JMenuItem("Save");
    save.addActionListener(arg0 -> {
      try {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(saveName));
        out.writeObject(gameFieldPanel.game); //Write byte stream to file system.
        out.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    });

    JMenuItem download = new JMenuItem("Download ");
    download.addActionListener(arg0 -> {
      openGameFromFile(saveName);
    });
    gameMenu.add(save);
    gameMenu.add(download);
  }

  private void openGameFromFile(String name) {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(name));
      GameState newGame = (GameState) in.readObject();
      in.close();
      this.game = newGame;
      gameFieldPanel.startNewGame(game);
      frame.pack();
    } catch (IOException | ClassNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  private void addLevelMenu(JMenu menu, Level level) {
    JMenuItem item = new JMenuItem(level.toString());
    item.addActionListener(arg0 -> {
      gameFieldPanel.startNewGame(level);
      //openGameFromFile(levels.get(level));
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
