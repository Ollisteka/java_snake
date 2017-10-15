import logic.*;
import org.junit.Assert;
import org.junit.Test;

public class OtherTests {

  @Test
  public void testGameStateCreate() {
    Snake mySnake= new Snake(1,4);
    GameState myState = new GameState(5,6,mySnake);
    // Assert.assertNotNull(myState.map.getObject(1,4).snake);
    Assert.assertEquals(6, myState.map.getHeight());
    Assert.assertEquals(5, myState.map.getWidth());
  }

  @Test
  public void testGameStateWithLevelOne() {
    GameState myState = new GameState(Level.one);
    // Assert.assertNotNull(myState.map.getObject(3,3).snake);
    Assert.assertEquals(15, myState.map.getHeight());
    Assert.assertEquals(15, myState.map.getWidth());
  }

  @Test
  public void testGameStateWithLevelZero() {
    GameState myState = new GameState(Level.zero);
    // Assert.assertNotNull(myState.map.getObject(3,3).snake);
    Assert.assertEquals(10, myState.map.getHeight());
    Assert.assertEquals(10, myState.map.getWidth());
  }

  @Test
  public void testGameStateWithLevelTwo() {
    GameState myState = new GameState(Level.two);
    // Assert.assertNotNull(myState.map.getObject(3,3).snake);
    Assert.assertEquals(12, myState.map.getHeight());
    Assert.assertEquals(12, myState.map.getWidth());
  }

  @Test
  public void testLocationsEqual() {
    Location first = new Location(1,1);
    Location second = new Location(1,1);
    Assert.assertEquals(first,second);
  }

  @Test
  public void testGetDrawable() {
    GameState game = new GameState(
        new GameMap(5, 5, false),
        new Snake(2, 2),
        new Food(10, false), new Location(2, 3));
    Assert.assertEquals("head", game.map.getObject(2, 2).getDrawable());
    Assert.assertEquals("food", game.map.getObject(2, 3).getDrawable());
    Assert.assertEquals("nothing", game.map.getObject(4, 4).getDrawable());
    game.map.setObject(new MapObject(new Wall(), 4, 4));
    game.snake.move(Direction.Down, game);
    Assert.assertEquals("head", game.map.getObject(2, 3).getDrawable());
    Assert.assertEquals("snake", game.map.getObject(2, 2).getDrawable());
    Assert.assertEquals("wall", game.map.getObject(4, 4).getDrawable());
    game.map.setObject(new MapObject(new Food(10, true), 3, 3));
    Assert.assertEquals("poison", game.map.getObject(3, 3).getDrawable());


  }


}