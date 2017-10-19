package gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import logic.Direction;
import logic.GameState;

public class Controller extends KeyAdapter {

  private GameState game;

  public Controller(GameState game) {
    this.game = game;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
      case KeyEvent.VK_UP:
        if (game.getSnake().getDirection() != Direction.Down) {
          game.getSnake().setDirection(Direction.Up);
        }
        break;

      case KeyEvent.VK_S:
      case KeyEvent.VK_DOWN:
        if (game.getSnake().getDirection() != Direction.Up) {
          game.getSnake().setDirection(Direction.Down);
        }
        break;

      case KeyEvent.VK_A:
      case KeyEvent.VK_LEFT:
        if (game.getSnake().getDirection() != Direction.Right) {
          game.getSnake().setDirection(Direction.Left);
        }
        break;

      case KeyEvent.VK_D:
      case KeyEvent.VK_RIGHT:
        if (game.getSnake().getDirection() != Direction.Left) {
          game.getSnake().setDirection(Direction.Right);
        }
        break;

      case KeyEvent.VK_P:
        game.setPaused(!game.isPaused());
        break;

      case KeyEvent.VK_R:
        game.setReadyToRestart(true);
        break;
    }

  }
}
