package Entity.Item;
import Entity.Unit.Player;

/**
 *  "^" increases the player's attack damage by 1, per collected. Reset when the player leaves the world.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public class DamagePerk extends Item {

    private int damagePerkPosX;
    private int damagePerkPosY;
    
    // Constructor
    public DamagePerk(String name, String[] itemData) {
        super(name);
        setItemLocation(Integer.parseInt(itemData[1]), Integer.parseInt(itemData[2]));
    }

    /**
     * Increase player's attack damage by 1, per perk collected
     * Reset when player leaves the world (via home command or on death)
     */
    @Override
    public String effect(Player player) {

        System.out.println("Attack up!");
        player.setPerk();

        return "damage";
    }

    // Set Item location
    public void setItemLocation (int x, int y) {
        this.damagePerkPosX = x;
        this.damagePerkPosY = y;
    };
    // Get Item location
    public int getItemPosX () {return damagePerkPosX;};
    public int getItemPosY () {return damagePerkPosY;};

    // Get Item name
    public char getItemName () {return name.charAt(0);}
}
