package logic;

import java.util.Vector;

public class Serialization {
    public GameMap GetMap_FromText_Serialization(String[] text)
    {
        String[] dimension = text[0].split(" ");
        int n = Integer.parseInt(dimension[0]);
        int m = Integer.parseInt(dimension[1]);
        Boolean cycled = Boolean.parseBoolean(text[1]);

        GameMap gm = new GameMap(n,m,cycled);
        for (int line = 0;line<text.length;line++) {
            if (line < 2) continue;
            for (int i = 0; i < text[line].length(); i++) {
                switch (text[line].charAt(i)) {
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
        return gm;
    }

    public GameMap GetText_FromGameMap_Serialization(GameMap gm)
    {
        String[] text = new String[gm.getHeight()+2];
        text[0] = "" + gm.getHeight() + " " + gm.getWidth();
        text[1] = "" + gm.isCycled();
        for (int i = 2;i<gm.getHeight(); i++)
        {
            text[i] = "";
            for (int j = 0;j<gm.getWidth(); j++)
            {
                MapObject obj = gm.getObject(i-2,j);
                if (obj.getFood() != null)
                    if (obj.getFood().isPoison())
                        text[i] += 'x';
                else
                    text[i] += 'o';
                else if (obj.isFree())
                    text[i] += '.';
                else if (obj.getSnake() != null)
                    text[i] += 's';
                else if (obj.getWall() != null)
                    text[i] += 'w';
            }
        }

    }
}
