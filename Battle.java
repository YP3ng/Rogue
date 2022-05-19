
/**
 * This class is for battle loop, return the result of battle.
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */

public class Battle {

    private Player player;
    private Monster monster;
    
    // Default battle constructor
    public Battle (Player player, Monster monster) {

        this.player = player;
        this.monster = monster;
    }

    // Dummy constructor
    public Battle () {}
    
    public String battleLoop(Commands commands) {

        // Battle starts
        System.out.printf("%s encountered a %s!\n\n", player.getName(), monster.getName());

        // Loop starts
        boolean isEnd = false;
        while(!isEnd) {
            // Display the player and monster's health
            commands.displayPlayerMonster(commands.returnP(player), commands.returnM(monster));

            // Player starts the attack
            this.attack(player.getName(), player.getCurDamage(), monster.getName());
            // Update monster health
            monster.setCurHealth(monster.getCurHealth() - player.getCurDamage());

            // If player wins
            if(this.isLost(monster.getCurHealth())) {
                System.out.printf("%s wins!\n\n", player.getName());
                return "player";
            };

            // Monster starts the attack
            this.attack(monster.getName(), monster.getDamage(), player.getName());
            // Update player health
            player.setCurHealth(player.getCurHealth() - monster.getDamage());

            // If monster wins
            if(this.isLost(player.getCurHealth())) {
                System.out.printf("%s wins!\n\n", monster.getName());
                return "monster";
            }
        }
        return null;
    }

    private void attack(String player, int damage, String monster) {
        System.out.printf("%s attacks %s for %d damage.\n", player, monster, damage);
    }
        
    // Check if one side's health is lower or equal to 0
    private boolean isLost(int health) {
        if(health <= 0) {return true;}
        return false;
    }

    
}