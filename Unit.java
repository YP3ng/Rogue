/**
 * 
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public abstract class Unit extends Entity {
    
    public Unit(String name) {
        super(name);
    }
    public Unit () {}

    // Display information of entity
    public abstract void displayInfo(String name);

}
