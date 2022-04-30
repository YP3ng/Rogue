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
    public void effect() {

    }
}
