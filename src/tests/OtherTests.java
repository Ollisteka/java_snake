import logic.Direction;
import logic.Food;
import logic.GameMap;
import logic.GameState;
import logic.Location;
import logic.MapObject;
import logic.Snake;
import logic.Wall;
import org.junit.Assert;
import org.junit.Test;

public class OtherTests {

  @Test
  public void testLocationsEqual() {
    Location first = new Location(1, 1);
    Location second = new Location(1, 1);
    Assert.assertEquals(first, second);
    Assert.assertFalse(first.equals(null));
    Assert.assertTrue(first.equals(first));
    Assert.assertFalse(first.equals(new Wall()));
  }

  @Test
  public void testGetDrawable() {
    GameState game = new GameState(
        new GameMap(5, 5, false),
        new Snake(2, 2),
        new Food(10, false), new Location(2, 3));
    Assert.assertEquals("head", game.getMap().getObject(2, 2).getDrawable());
    Assert.assertEquals("Food", game.getMap().getObject(2, 3).getDrawable());
    Assert.assertEquals("nothing", game.getMap().getObject(4, 4).getDrawable());
    game.getMap().setObject(new MapObject(new Wall(), 4, 4));
    game.getSnake().move(Direction.Down, game);
    Assert.assertEquals("head", game.getMap().getObject(2, 3).getDrawable());
    Assert.assertEquals("snake", game.getMap().getObject(2, 2).getDrawable());
    Assert.assertEquals("wall", game.getMap().getObject(4, 4).getDrawable());
    game.getMap().setObject(new MapObject(new Food(10, true), 3, 3));
    Assert.assertEquals("poison", game.getMap().getObject(3, 3).getDrawable());
  }
}