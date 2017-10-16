import logic.Food;
import logic.GameMap;
import logic.Location;
import logic.MapObject;
import logic.Snake;
import logic.Wall;
import org.junit.Assert;
import org.junit.Test;

public class GameMapTests {

  @Test
  public void testCreate() {
    GameMap myMap = new GameMap(7, 5);
    Assert.assertEquals(5, myMap.getHeight());
    Assert.assertEquals(7, myMap.getWidth());
  }

  @Test
  public void testSetGetObject() {
    GameMap myMap = new GameMap(5, 7);
    myMap.setObject(new MapObject(new Wall(), 1, 1));
    Assert.assertTrue(myMap.getObject(1, 1).willKillTheSnake());
    Assert.assertFalse(myMap.getObject(1, 1).canEat());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void GameMapGetWrong() {
    GameMap gm = new GameMap(5, 5);
    gm.getObject(-1, -1);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void GameMapSetWrong() {
    GameMap gm = new GameMap(5, 5);
    gm.setObject(new MapObject(1, 6));
  }
//    @Test
//    public void testAddSnake(){
//        GameMap myMap = new GameMap(5,7);
//        Snake mySnake = new Snake(0,0);
//       // mySnake.body.add(new Location(1,0));
//        myMap.addSnake(mySnake);
//        Assert.assertNotNull(myMap.getObject(0,0).snake);
//        Assert.assertNotNull(myMap.getObject(1,0).snake);
//    }

  @Test
  public void testAddFood() {
    GameMap myMap = new GameMap(5, 7);
    myMap.addFood(new Food(10, false), 1, 1);
    Assert.assertTrue(myMap.getObject(1, 1).canEat());
    myMap.addFood(new Food(10, true), 2, 1);
    Assert.assertTrue(myMap.getObject(2, 1).getFood().isPoison());
    Assert.assertEquals(1, myMap.getPoisonCount());
    boolean generatedFood = false;
    for (int i = 0; i < myMap.getHeight(); i++) {
      for (int j = 0; j < myMap.getWidth(); j++) {
        if ((i != 1 || j != 1) && (i != 2 || j != 1) && myMap.getObject(j, i).canEat()) {
          generatedFood = true;
        }
      }
    }
    Assert.assertTrue(generatedFood);
  }

  @Test
  public void testGeneratePoison() {
    GameMap myMap = new GameMap(5, 7);
    Location poison = myMap.generateFood(true);
    Assert.assertEquals(1, myMap.getPoisonCount());
    Assert.assertEquals(0, myMap.getFoodCount());
    Assert.assertTrue(myMap.getObject(poison).getFood().isPoison());
  }

  @Test
  public void testAddFoodOnSomething() {
    GameMap myMap = new GameMap(5, 7);
    Snake snake = new Snake(1, 1);
    myMap.addSnake(snake);
    myMap.addFood(new Food(10, false), 1, 1);
    Assert.assertFalse(myMap.getObject(1, 1).canEat());
    Assert.assertEquals(snake, myMap.getObject(1, 1).getSnake());
    Assert.assertEquals(1, myMap.getFoodCount());
  }

  @Test
  public void GameMapSome() {
    GameMap gm = new GameMap(5, 5);
    gm.setObject(new MapObject(new Wall(), 1, 1));
    gm.addSnake(new Snake(1, 1));
    Assert.assertNotNull(gm.getObject(1, 1).getWall());
  }
}
