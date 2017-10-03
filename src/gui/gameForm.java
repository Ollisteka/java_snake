package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import logic.*;

public class gameForm extends JFrame{
  private int CellWidth = 30;
  private int CellHeight = 30;

  private int xGap = 1;
  private int yGap = 1;

  public GameState Game;
  public int KeyPressed;
  public cell[][] PaintedMap;
  private int magic = 3;

  public Timer timer;
  public int TimerTick;

  public gameForm(GameState game){
    setTitle("Snake");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Game = game;
    int width = Game.Map.Width() * (CellWidth + xGap);
    int height = Game.Map.Height() * (CellHeight + yGap);
    setSize(width, height);
    PaintedMap = new cell[Game.Map.Width()][Game.Map.Height()];
    MakePaintedMap(game.Map);


    addKeyListener(new KeyAdapter() {//Обработка клавиш
      @Override
      public void keyPressed(KeyEvent e) {
          KeyPressed = e.getKeyCode();
      }
    });
    ActionListener mover = this::MakeMove;
    timer = new Timer(100, mover);
    timer.start();
  }

  private void MakeMove(ActionEvent evt)
  {
    setTitle("Змейка. Очки: " + Game.Scores);
    if (Game.IsOver)
    {
      EndGame();
      return;
    }
    MoveSnake();
    repaint();
  }

  private void EndGame()
  {
    String text = "Игра окончена!\nВы набрали " + Game.Scores + " очк.";
    timer.stop();
  }

  private void MakePaintedMap(GameMap gameMap)
  {
    int x = 0;
    int y = 0;
    for (int i = 0; i < gameMap.Width(); i++)
      for (int j = 0; j < gameMap.Height(); j++)
      {
        MapObject originalCell = gameMap.getObject(i, j);
        cell cellToAdd = new cell(x, y, CellWidth, CellHeight);
        cellToAdd.UpdateColour(originalCell.Location, Game, this);
        PaintedMap[j][i] = cellToAdd;
        x += CellWidth + xGap;
        if (x > getWidth() - CellWidth)
        {
          x = 0;
          y += CellHeight + yGap;
        }
      }
  }

  private void MoveSnake()
  {
    int key = KeyPressed;
    Snake snake = Game.Snake;

    if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
    {
      snake.Move(Direction.Up, Game);
      TimerTick = 0;
    }
    else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
    {
      snake.Move(Direction.Down, Game);
      TimerTick = 0;
    }

    else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
    {
      snake.Move(Direction.Right, Game);
      TimerTick = 0;
    }

    else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
    {
      snake.Move(Direction.Left, Game);
      TimerTick = 0;
    }

    else if (snake.direction != Direction.Stop
        && key == KeyEvent.KEY_LOCATION_UNKNOWN && TimerTick != 0
        && TimerTick % magic == 0)
    {
      snake.Move(snake.direction, Game);
      TimerTick = 0;
    }
    else
    {
      TimerTick++;
    }
    KeyPressed = KeyEvent.KEY_LOCATION_UNKNOWN;
  }


  protected void OnPaint(Graphics e)
  {
    for (int i = 0; i < Game.Map.Width(); i++)
      for (int j = 0; j < Game.Map.Height(); j++)
      {
        cell cell = PaintedMap[i][j];
        cell.UpdateColour(new Location(i, j), Game, this);
        e.setColor(cell.color);
        e.fillRect(cell.X, cell.Y, cell.Width, cell.Height);
      }
  }


}
