package tests;

import logic.Food;
import logic.GameMap;
import logic.Location;
import logic.MapObject;
import logic.Wall;
import org.junit.Assert;
import org.junit.Test;

public class GameMapTests {

    @Test
    public void testCreate() {
        GameMap myMap = new GameMap(7,5);
        Assert.assertEquals(5, myMap.height());
        Assert.assertEquals(7, myMap.width());
    }

    @Test
    public void testSetGetObject(){
        GameMap myMap = new GameMap(5,7);
        MapObject myObj = new MapObject();
        myObj.wall = new Wall();
        myMap.setObject(1,1,myObj);
        Assert.assertNotNull(myMap.getObject(1,1).wall);
        Assert.assertNull(myMap.getObject(1,1).snake);
        Assert.assertNull(myMap.getObject(1,1).food);
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
        MapObject myObj = new MapObject();
        myMap.addFood(new Food(new Location(1,1),10, false));
        Assert.assertNotNull(myMap.getObject(1,1).food);
        myMap.addFood(new Food(new Location(1,1),10, false));
        boolean generatedFood = false;
        for (int i=0;i<myMap.height();i++)
            for (int j = 0;j<myMap.width();j++)
                if ((i!= 1 || j!=1) && myMap.getObject(j,i).food != null)
                    generatedFood = true;
        Assert.assertTrue(generatedFood);
    }
}
