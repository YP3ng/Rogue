/**
 * "+" instantly heals the player to full health
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public class HealingItem extends Item {
    
    // Constructor
    public HealingItem(String name) {
        super(name);
    }

    // Health player's health to full
    @Override
    public void effect(Player player) {

        player.setCurHealth(player.getMaxHealth());
    }
}
