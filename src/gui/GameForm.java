package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import logic.GameState;
import logic.Level;
import logic.Serialization;

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
    levels.put(Level.three, "lvl_3.snk");
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
    addLevelMenu(chooseLevelMenu, Level.three);

    JMenu gameMenu = new JMenu("game");
    menuBar.add(gameMenu);

    JMenuItem save = new JMenuItem("Save");
    save.addActionListener(arg0 -> {
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(saveName));
        writer.write(Serialization.getTextFromGameMapSerialization(gameFieldPanel.getGame()));
        writer.close();
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
      ArrayList<String> gameText = new ArrayList<String>();
      BufferedReader br = new BufferedReader(new FileReader(name));
      String line;
      while ((line = br.readLine()) != null) {
        gameText.add(line);
      }
      br.close();
      GameState newState = Serialization.getGameStateFromTextSerialization(gameText);
      this.game = newState;
      gameFieldPanel.startNewGame(game);
      frame.pack();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  private void addLevelMenu(JMenu menu, Level level) {
    JMenuItem item = new JMenuItem(level.toString());
    item.addActionListener(arg0 -> {
      openGameFromFile(levels.get(level));
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
