/**
 * This abstract class is a base class for all entities in Rogue game.
 * So far, we only have three derived classes, Player, Monster, ItemClass
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public abstract class Entity {

    protected String name;
    
    // Constructor of Entity
    public Entity(String name) {
        this.name = name;
    }

    // Display information of entity
    public abstract void displayInfo(String name);
}