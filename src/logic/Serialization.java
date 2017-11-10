package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Serialization {

  private static final int fieldAmount = 6;

  // dimensions
  // scores
  // cycled
  // level
  // noposion
  // snake
  public static GameState getGameStateFromTextSerialization(ArrayList<String> text) {
    String[] dimensions = text.get(0).split(" ");
    int width = Integer.parseInt(dimensions[0]);
    int height = Integer.parseInt(dimensions[1]);
    int scores = Integer.parseInt(text.get(1));
    Boolean cycled = Boolean.parseBoolean(text.get(2));
    int level = Integer.parseInt(text.get(3));
    Boolean noPoison = Boolean.parseBoolean(text.get(4));

    Snake snake = ParseSnake(text);
    GameMap map = FillMap(text, width, height, cycled);

    return new GameState(map, snake, noPoison, scores, Level.values()[level]);
  }

  private static GameMap FillMap(ArrayList<String> text, int width, int height, Boolean cycled) {
    GameMap map = new GameMap(width, height, cycled);
    for (int line = fieldAmount; line < text.size(); line++) {
      for (int xPos = 0; xPos < text.get(line).length(); xPos++) {
        int yPos = line - fieldAmount;
        switch (text.get(line).charAt(xPos)) {
          case '.':
            map.setObject(new MapObject(xPos, yPos));
            break;
          case 'w':
            map.setObject(new MapObject(new Wall(), xPos, yPos));
            break;
          case 'o':
            map.setObject(new MapObject(new Food(10, false), xPos, yPos));
            break;
          case 'x':
            map.setObject(new MapObject(new Food(10, true), xPos, yPos));
            break;
          case 's':
            map.setObject(new MapObject(new Snake(xPos, yPos), xPos, yPos));
            break;
        }
      }
    }
    return map;
  }

  private static Snake ParseSnake(ArrayList<String> text) {
    String[] snakeLocations = text.get(5).split(" ");
    Deque<Location> snakeBody = new ArrayDeque<Location>();
    for (String loc : snakeLocations) {
      String[] locations = loc.split(",");
      Location newLoc = new Location(Integer.parseInt(locations[0]),
          Integer.parseInt(locations[1]));
      snakeBody.add(newLoc);
    }
    return new Snake(snakeBody);
  }

  // dimensions
  // scores
  // cycled
  // level
  // noposion
  // snake
  public static String getTextFromGameMapSerialization(GameState gameState) {
    GameMap gm = gameState.getMap();

    int size = gm.getHeight() + fieldAmount;
    ArrayList<String> result = new ArrayList<String>(size);
    result.add("" + gm.getHeight() + " " + gm.getWidth());
    result.add("" + gameState.getScores());
    result.add("" + gm.isCycled());
    int level = gameState.getLevel().getValue();
    result.add("" + level);
    result.add("" + gameState.isNoPoison());
    result.add("");
    for (Location loc : gameState.getSnake().getBody()) {
      result.set(5, result.get(5) + loc.toString() + ' ');
    }

    for (int i = fieldAmount; i < gm.getHeight() + fieldAmount; i++) {
      result.add("");
      for (int j = 0; j < gm.getWidth(); j++) {
        MapObject obj = gm.getObject(j, i - fieldAmount);
        if (obj.getFood() != null) {
          if (obj.getFood().isPoison()) {
            result.set(i, result.get(i) + 'x');
          } else {
            result.set(i, result.get(i) + 'o');
          }
        } else if (obj.isFree()) {
          result.set(i, result.get(i) + '.');
        } else if (obj.getSnake() != null) {
          result.set(i, result.get(i) + 's');
        } else if (obj.getWall() != null) {
          result.set(i, result.get(i) + 'w');
        }
      }
    }
    StringBuilder strBuilder = new StringBuilder();
    for (String aText : result) {
      strBuilder.append(aText).append('\n');
    }
    return strBuilder.toString();
  }
}
