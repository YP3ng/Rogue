/**
 * 
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public abstract class Unit extends Entity {
    
    protected int playerPosX;
    protected int playerPosY;
    protected int monsterPosX;
    protected int monsterPosY;

    public Unit(String name) {
        super(name);
    }

    // Display information of entity
    public abstract void displayInfo(String name);
    public abstract void movement(String direction);

}
