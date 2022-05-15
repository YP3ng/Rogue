/**
 *  "^" increases the player's attack damage by 1, per collected. Reset when the player leaves the world.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public class DamagePerk extends Item {
    
    // Constructor
    public DamagePerk(String name) {
        super(name);
    }

    /**
     * Increase player's attack damage by 1, per perk collected
     * Reset when player leaves the world (via home command or on death)
     */
    @Override
    public void effect(Player player) {

        player.setDamage(player.getLevel());
    }
}
