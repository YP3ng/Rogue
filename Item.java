/**
 * Three items
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public abstract class Item extends Entity {
    
    public Item(String name) {
        super(name);
    }

    public abstract void effect(Player player);
}
