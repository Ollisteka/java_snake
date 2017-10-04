package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import logic.Direction;
import logic.GameMap;
import logic.GameState;
import logic.Location;
import logic.MapObject;
import logic.Snake;

public class GameFieldPanel extends JPanel {

  private int cellWidth = 30;
  private int cellHeight = 30;

  private int xGap = 1;
  private int yGap = 1;

  private Cell[][] paintedMap;
  private GameState game;

  private JFrame parent;

  private int keyPressed;

  private int magic = 3;

  private Timer timer;
  private int timerTick;

  GameFieldPanel(GameState game, JFrame parent) {
    this.game = game;
    this.parent = parent;

    paintedMap = new Cell[this.game.Map.width()][this.game.Map.height()];
    initializePaintedMap(game.Map);

    setPreferredSize(new Dimension(this.game.Map.width() * (cellWidth + xGap),
                                   this.game.Map.height() * (cellHeight + yGap)));
    setFocusable(true);
    requestFocusInWindow();

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        keyPressed = e.getKeyCode();
      }
    });
    timer = new Timer(100, this::makeMove);
    timer.start();

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int i = 0; i < game.Map.width(); i++) {
      for (int j = 0; j < game.Map.height(); j++) {
        Cell cell = paintedMap[i][j];
        cell.UpdateColour(new Location(i, j), game);
        g.setColor(cell.color);
        g.fillRect(cell.x, cell.y, cell.width, cell.height);
      }
    }
  }

  private void endGame() {
    parent.setTitle("Game over. \n You scored " + game.Scores);
    timer.stop();
  }

  private void makeMove(ActionEvent evt) {
    parent.setTitle("snake. Score: " + game.Scores);
    if (game.IsOver) {
      endGame();
      return;
    }
    moveSnake();
    repaint();
  }

  private void moveSnake() {
    int key = keyPressed;
    Snake snake = game.Snake;

    if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
      snake.move(Direction.Up, game);
      timerTick = 0;
    } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
      snake.move(Direction.Down, game);
      timerTick = 0;
    } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
      snake.move(Direction.Right, game);
      timerTick = 0;
    } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
      snake.move(Direction.Left, game);
      timerTick = 0;
    } else if (snake.direction != Direction.Stop
        && key == KeyEvent.KEY_LOCATION_UNKNOWN && timerTick != 0
        && timerTick % magic == 0) {
      snake.move(snake.direction, game);
      timerTick = 0;
    } else {
      timerTick++;
    }
    keyPressed = KeyEvent.KEY_LOCATION_UNKNOWN;
  }

  private void initializePaintedMap(GameMap gameMap) {
    int x = 0;
    int y = 0;
    for (int i = 0; i < gameMap.width(); i++) {
      for (int j = 0; j < gameMap.height(); j++) {
        MapObject originalCell = gameMap.getObject(i, j);
        Cell cellToAdd = new Cell(x, y, cellWidth, cellHeight);
        cellToAdd.UpdateColour(originalCell.location, game);
        paintedMap[j][i] = cellToAdd;
        x += cellWidth + xGap;
      }
      x = 0;
      y += cellHeight + yGap;
    }
  }
}
