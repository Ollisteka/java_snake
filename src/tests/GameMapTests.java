import logic.Food;
import logic.GameMap;
import logic.MapObject;
import logic.Wall;
import org.junit.Assert;
import org.junit.Test;

public class GameMapTests {

    @Test
    public void testCreate() {
        GameMap myMap = new GameMap(7,5);
        Assert.assertEquals(5, myMap.getHeight());
        Assert.assertEquals(7, myMap.getWidth());
    }

    @Test
    public void testSetGetObject(){
        GameMap myMap = new GameMap(5,7);
        myMap.setObject(new MapObject(new Wall(), 1, 1));
        Assert.assertTrue(myMap.getObject(1, 1).willKillTheSnake());
        Assert.assertFalse(myMap.getObject(1, 1).canEat());
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
    public void testAddFood(){
        GameMap myMap = new GameMap(5,7);
        myMap.addFood(new Food(10, false), 1, 1);
        Assert.assertTrue(myMap.getObject(1, 1).canEat());
        myMap.addFood(new Food(10, false), 1, 1);
        boolean generatedFood = false;
        for (int i = 0; i < myMap.getHeight(); i++)
            for (int j = 0; j < myMap.getWidth(); j++)
                if ((i != 1 || j != 1) && myMap.getObject(j, i).canEat())
                    generatedFood = true;
        Assert.assertTrue(generatedFood);
    }
}
