/**
 * "+" instantly heals the player to full health
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public class HealingItem extends Item {

    private int healItemPosX;
    private int healItemPosY;
    
    // Constructor
    public HealingItem(String name, String[] itemData) {
        super(name);
        setItemLocation(Integer.parseInt(itemData[0]), Integer.parseInt(itemData[1]));
    }

    // Health player's health to full
    @Override
    public void effect(Player player) {

        player.setCurHealth(player.getMaxHealth());
    }

    // Set Item location
    public void setItemLocation (int x, int y) {
        this.healItemPosX = x;
        this.healItemPosY = y;
    };
    // Get Item location
    public int getItemPosX () {return healItemPosX;};
    public int getItemPosY () {return healItemPosY;};

    // Get Item name
    public char getItemName () {return name.charAt(0);}
}
