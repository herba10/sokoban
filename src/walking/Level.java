package walking;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Level {
    int width, height, playerX, playerY, targetsAmount;
    Tile[][] levelTiles;
    char type;
    String levelLine;
    public void getLevelInfo(String file){
        try {
            File myObj = new File(file);
            Scanner levelReader = new Scanner(myObj);
            width = Integer.parseInt(levelReader.nextLine());
            height = Integer.parseInt(levelReader.nextLine());
            levelTiles = new Tile[width][height];

            for (int h=0;h<height;h++){
                levelLine = levelReader.nextLine();
                for (int w=0;w<width;w++){

                    if (levelLine.charAt(w) == 'T'){
                        targetsAmount++;
                        levelTiles[w][h] = new Tile(levelLine.charAt(w), w, h, true);
                    }
                    else{
                        levelTiles[w][h] = new Tile(levelLine.charAt(w), w, h, false);
                    }

                    if (levelTiles[w][h].type == 'P') {
                        playerX = w;
                        playerY = h;
                    }
                }
            }
            System.out.println();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public String getLevelInfo(){
        return ("player x: "+playerX+", y: "+playerY+", level width: "+width+", height: "+height+", targets and cubes amount: "+targetsAmount);
    }
}
