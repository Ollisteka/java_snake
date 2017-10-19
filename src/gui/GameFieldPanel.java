package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import logic.GameMap;
import logic.GameState;
import logic.Level;
import logic.Location;

public class GameFieldPanel extends JPanel {

  private int cellWidth = 30;
  private int cellHeight = 30;

  private int xGap = 2;
  private int yGap = 2;

  private Cell[][] paintedMap;
  public GameState game;

  private JFrame parent;

  private Timer timer;

  private Controller controller;

  GameFieldPanel(GameState game, JFrame parent) {
    this.game = game;
    this.parent = parent;
    initializeWindow(this.game);
    controller = new Controller(this.game);
    addKeyListener(controller);
    timer = new Timer(0, this::nextStep);
    timer.start();
    this.game.startTimer();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int i = 0; i < game.getMap().getWidth(); i++) {
      for (int j = 0; j < game.getMap().getHeight(); j++) {
        Cell cell = paintedMap[i][j];
        cell.UpdateColour(new Location(i, j), game);
        g.setColor(cell.getColor());
        g.fillRect(cell.getX(), cell.getY(), cell.getWidth(), cell.getHeight());
      }
    }
  }

  public void initializeWindow(GameState game) {
    paintedMap = new Cell[game.getMap().getWidth()][game.getMap().getHeight()];
    initializePaintedMap(game.getMap());

    setPreferredSize(new Dimension(game.getMap().getWidth() * (cellWidth + xGap),
        game.getMap().getHeight() * (cellHeight + yGap)));
    setFocusable(true);
    requestFocusInWindow();
  }

  private void endGame() {
    parent.setTitle("Game over. \n You scored " + game.getScores());
    game.setPaused(true);
  }

  private void nextStep(ActionEvent evt) {
    parent.setTitle("Snake. Score: " + game.getScores());
    if (game.isOver())
      endGame();
    else if (game.isReadyToRestart())
      restartGame();
    repaint();
  }

  private void restartGame() {
    game = new GameState(game.getLevel());
    initializeWindow(game);
    ChangeController();
    game.startTimer();
    repaint();
  }

  public void startNewGame(Level level) {
    game = new GameState(level);
    initializeWindow(game);
    ChangeController();
    game.startTimer();
    repaint();
  }

  public void startNewGame(GameState game) {
    this.game = game;
    initializeWindow(this.game);
    ChangeController();
    game.startTimer();
    repaint();
  }

  private void ChangeController() {
    removeKeyListener(controller);
    controller = new Controller(this.game);
    addKeyListener(controller);
  }

  private void initializePaintedMap(GameMap gameMap) {
    int x = 0;
    int y = 0;
    for (int i = 0; i < gameMap.getWidth(); i++) {
      for (int j = 0; j < gameMap.getHeight(); j++) {
        Cell cellToAdd = new Cell(x, y, cellWidth, cellHeight);
        cellToAdd.UpdateColour(new Location(i,j), game);
        paintedMap[j][i] = cellToAdd;
        x += cellWidth + xGap;
      }
      x = 0;
      y += cellHeight + yGap;
    }
  }
}
