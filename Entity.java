/**
 * The world manages a lot entities within it. 
 * It would make sense for entities to know how to render themselves, 
 * and hold positional information about where they are in the world.
 * So far, we only have two derived classes, Unit, Item
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public abstract class Entity {

    protected String name;
    
    // Constructor of Entity
    public Entity(String name) {
        this.name = name;
    }
    
}