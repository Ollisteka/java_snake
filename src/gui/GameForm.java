package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
import logic.Direction;
import logic.GameMap;
import logic.GameState;
import logic.Location;
import logic.MapObject;
import logic.Snake;

public class GameForm extends JFrame {

  private int CellWidth = 30;
  private int CellHeight = 30;

  private int xGap = 1;
  private int yGap = 1;

  public GameState Game;
  public int KeyPressed;
  public Cell[][] PaintedMap;
  private int magic = 3;

  public Timer timer;
  public int TimerTick;

  public GameForm(GameState game) {
    setTitle("snake");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setVisible(true);

    Game = game;
    int width = Game.Map.width() * (CellWidth + xGap);
    int height = Game.Map.height() * (CellHeight + yGap);
    setSize(width, height);
    PaintedMap = new Cell[Game.Map.width()][Game.Map.height()];
    MakePaintedMap(game.Map);

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        KeyPressed = e.getKeyCode();
      }
    });
    ActionListener mover = this::MakeMove;
    timer = new Timer(100, mover);
    timer.start();
  }

  private void MakeMove(ActionEvent evt) {
    setTitle("snake. Score: " + Game.Scores);
    if (Game.IsOver) {
      EndGame();
      return;
    }
    MoveSnake();
    repaint();
  }

  private void EndGame() {
    setTitle("Game over. \n You scored " + Game.Scores);
    timer.stop();
  }

  private void MakePaintedMap(GameMap gameMap) {
    int x = 0;
    int y = 0;
    for (int i = 0; i < gameMap.width(); i++) {
      for (int j = 0; j < gameMap.height(); j++) {
        MapObject originalCell = gameMap.getObject(i, j);
        Cell cellToAdd = new Cell(x, y, CellWidth, CellHeight);
        cellToAdd.UpdateColour(originalCell.location, Game, this);
        PaintedMap[j][i] = cellToAdd;
        x += CellWidth + xGap;
        if (x > getWidth() - CellWidth) {
          x = 0;
          y += CellHeight + yGap;
        }
      }
    }
  }

  private void MoveSnake() {
    int key = KeyPressed;
    Snake snake = Game.Snake;

    if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
      snake.move(Direction.Up, Game);
      TimerTick = 0;
    } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
      snake.move(Direction.Down, Game);
      TimerTick = 0;
    } else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
      snake.move(Direction.Right, Game);
      TimerTick = 0;
    } else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
      snake.move(Direction.Left, Game);
      TimerTick = 0;
    } else if (snake.direction != Direction.Stop
        && key == KeyEvent.KEY_LOCATION_UNKNOWN && TimerTick != 0
        && TimerTick % magic == 0) {
      snake.move(snake.direction, Game);
      TimerTick = 0;
    } else {
      TimerTick++;
    }
    KeyPressed = KeyEvent.KEY_LOCATION_UNKNOWN;
  }

  @Override
  public void paint(Graphics e) {
    super.paint(e);
    for (int i = 0; i < Game.Map.width(); i++) {
      for (int j = 0; j < Game.Map.height(); j++) {
        Cell cell = PaintedMap[i][j];
        cell.UpdateColour(new Location(i, j), Game, this);
        e.setColor(cell.color);
        e.fillRect(cell.X, cell.Y, cell.Width, cell.Height);
      }
    }
  }


}
