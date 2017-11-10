package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Vector;

public class Serialization {

  // dimensions
  // scores
  // cycled
  // level
  // noposion
  // snake

  public static GameState getGameStateFromTextSerialization(ArrayList<String> text) {
    String[] dimension = text.get(0).split(" ");
    int n = Integer.parseInt(dimension[0]);
    int m = Integer.parseInt(dimension[1]);
    int scores = Integer.parseInt(text.get(1));
    Boolean cycled = Boolean.parseBoolean(text.get(2));
    int level = Integer.parseInt(text.get(3));
    Boolean noPoison = Boolean.parseBoolean(text.get(4));
    String[] snakeLocations = text.get(5).split(" ");
    Deque<Location> snakeBody = new ArrayDeque<Location>();
    for (String loc : snakeLocations)
    {
      String[] coords = loc.split(",");
      Location newLoc = new Location(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
      snakeBody.add(newLoc);
    }
    Snake snake = new Snake(snakeBody);

    GameMap gm = new GameMap(n, m, cycled);
    for (int line = 0; line < text.size(); line++) {
      if (line < 2) {
        continue;
      }
      for (int i = 0; i < text.get(line).length(); i++) {
        switch (text.get(line).charAt(i)) {
          case '.':
            gm.setObject(new MapObject(line, i));
            break;
          case 'w':
            gm.setObject(new MapObject(new Wall(), line, i));
            break;
          case 'o':
            gm.setObject(new MapObject(new Food(10, false), line, i));
            break;
          case 'x':
            gm.setObject(new MapObject(new Food(10, true), line, i));
            break;
          case 's':
            gm.setObject(new MapObject(new Snake(line, i), line, i));
            break;
        }
      }
    }
    GameState gameState = new GameState(gm, snake);
    gameState.setLevel(Level.values()[level]);
    gameState.setNoPoison(noPoison);
    gameState.setScores(scores);
    gameState.setSnake(snake);
    return gameState;
  }

  // dimensions
  // scores
  // cycled
  // level
  // noposion
  // snake
  public static String getTextFromGameMapSerialization(GameState gameState) {
    GameMap gm = gameState.getMap();

    int fieldAmount = 6;
    int size = gm.getHeight() + fieldAmount;
    ArrayList<String> text = new ArrayList<String>(size);
    text.add("" + gm.getHeight() + " " + gm.getWidth());
    text.add("" + gameState.getScores());
    text.add("" + gm.isCycled());
    int level = 0;
    if (gameState.getLevel() == Level.one) level = 1;
    if (gameState.getLevel() == Level.two) level = 2;
    text.add("" +  level);
    text.add("" + gameState.isNoPoison());
    text.add("");
    for (Location loc :gameState.getSnake().getBody())
      text.set(5, text.get(5) + loc.toString() + ' ');

    for (int i = fieldAmount; i < gm.getHeight()+fieldAmount; i++) {
      text.add("");
      for (int j = 0; j < gm.getWidth(); j++) {
        MapObject obj = gm.getObject(j, i - fieldAmount);
        if (obj.getFood() != null) {
          if (obj.getFood().isPoison()) {
            text.set(i, text.get(i) + 'x');
          } else {
            text.set(i, text.get(i) + 'o');
          }
        } else if (obj.isFree()) {
          text.set(i, text.get(i) + '.');
        } else if (obj.getSnake() != null) {
          text.set(i, text.get(i) + 's');
        } else if (obj.getWall() != null) {
          text.set(i, text.get(i) + 'w');
        }
      }
    }
    StringBuilder strBuilder = new StringBuilder();
    for (String aText : text) {
      strBuilder.append(aText).append('\n');
    }
    return strBuilder.toString();
  }
}
