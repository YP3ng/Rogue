import javax.swing.AbstractAction;

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
    // Set Item location
    public abstract void setItemLocation (int x, int y);
    // Get Item location
    public abstract int getItemPosX ();
    public abstract int getItemPosY ();
}
