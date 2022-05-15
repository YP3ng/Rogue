/**
 * There is a lot of functionality related with managing the "map" for the world (the look of the terrain, and whether or not it is traversable). 
 * It would make sense to group this functionality out into a separate class. 
 * The map can manage the rendering of the terrain, and allow functionality for checking whether a particular location on the map is traversable or not. 
 * It would also make sense for the map to hold the dimensions of the map / world.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;


public class Map implements Traversable {

    // Map size in assignment one, set as default map size if no file input exist
    public static final int DEFAULT_MAP_WIDTH = 6; 
	public static final int DEFAULT_MAP_HEIGHT = 4;
    private int mapWidth; 
	private int mapHeight;
    
    // Constructor
    public Map(int Width, int Height) {
        this.setHeight(Height);
        this.setWidth(Width);
    }

    public Map() {}

    @Override
    // Normal ground can be traversed, others can't
    public boolean canTraverse(String type) {
        if (type == ".") {return true;}
        return false;
    }

    public void mapping () {}


    // Print map function
    private void printMap (
        int mapWidth, int mapHeight, 
        int playerX, int playerY, 
        int monsterX, int monsterY,
        char pFirstChar, char mFirstChar
    ) {
		for (int i = 0; i < mapHeight; i++) {
			for (int j = 0; j < mapWidth; j++) {
				if (playerX == j && playerY == i) { 		  
					System.out.printf ("%c", pFirstChar);	  
				} else if (monsterX == j && monsterY == i) {
					System.out.printf ("%c", mFirstChar);
				} else {
					System.out.print (".");
				}
			}
			System.out.println ();
		}
		System.out.println ();
		
	}

    // Three different terrain
    public void normalGround() {
        System.out.print(".");
    }
    public void mountains() {
        System.out.print("#");
    }
    public void water() {
        System.out.print("~");
    }
    
    // Setter for map width
    public void setWidth (int wid) {
        this.mapWidth = wid;
    }

    // Setter for map Height
    public void setHeight (int hei) {
        this.mapHeight = hei;
    }
}
