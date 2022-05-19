/**
 * This class is for setting and getting monster infomation
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public class Monster extends Unit {
    private String name;
    private int maxHealth;
    private int damage;
    private int curHealth;
    private int monsterPosX;
    private int monsterPosY;

    // Inheritance constructor
    public Monster (String name) {
        super(name);
    }

    // File constructor
    public Monster (String[] infoSet) {
        this.name = infoSet[3];
        this.setMaxHealth(Integer.parseInt(infoSet[4]));
        this.setCurHealth(getMaxHealth());
        this.setDamage(Integer.parseInt(infoSet[5]));
        this.setMonsterLocation(Integer.parseInt(infoSet[1]), Integer.parseInt(infoSet[2]));
    }

    // Default constructor
    public Monster () {
        this.name = null;
    }

    // Print monster info
    @Override
    public void displayInfo(String name) {
        System.out.println ("Monster name: " +  name );
        System.out.println ("Monster health: " + curHealth);
        System.out.println ("Monster damage: " + damage);
        System.out.printf ("Monster '%s' created.\n\n", name);
    }

    // Define the movement of monster
    /**
     * When the player is within 2 cells of the monster (i.e., anywhere in a 5x5 area around the monster's location), the monster will try to move toward the player to attack.
     * Version 1
     * 
     * This version dosn't consider traversable issue and the option of not moving
     */

    // Monster moving AI
    public void movement(Map map, Player player) {
        if (distCheck(player)) {
            switch (moveLogic(map, player)) {
                case "w":
                map.resetRow(monsterPosX, monsterPosY);
                setMonsterLocation(monsterPosX, monsterPosY -1);
                break;
                case "a":
                map.resetRow(monsterPosX, monsterPosY);
                setMonsterLocation(monsterPosX - 1, monsterPosY);
                break;
                case "s":
                map.resetRow(monsterPosX, monsterPosY);
                setMonsterLocation(monsterPosX, monsterPosY + 1);
                break;
                case "d":
                map.resetRow(monsterPosX, monsterPosY);
                setMonsterLocation(monsterPosX + 1, monsterPosY);
                break;
                case "stay":
                setMonsterLocation(monsterPosX, monsterPosY);
                break;

            }
        }
    }

    // If the monster starts to move or not
    private boolean distCheck (Player player) {
        if (distXWithPlayer(monsterPosX, player) <= 2 && distYWithPlayer(monsterPosY, player) <= 2) {
            return true;
        }
        
        return false;
    }

    // Distance function with the player
    private double distXWithPlayer (int monsterPosX, Player player) {
        double distX = Math.abs(monsterPosX - player.getPlayerPosX());
        return distX;
    }
    // Distance function with the player
    private double distYWithPlayer (int monsterPosY, Player player) {
        double distY = Math.abs(monsterPosY - player.getPlayerPosY());
        return distY;
    }

    // determine which way to move
    private String moveLogic (Map map, Player player) {
        
        // Determine relative location
        // Player on monster's left hand side
        if (monsterPosX - player.getPlayerPosX() > 0) {
            // Check if future movement is traversable
            if (map.canTraverse(monsterPosX, monsterPosY, "left")) {
                return "a";
            }
        // Player on monster's right hand side
        } else if (monsterPosX - player.getPlayerPosX() < 0) {
            // Check if future movement is traversable
            if (map.canTraverse(monsterPosX, monsterPosY, "right")) {
                return "d";
            }
        }

        // Player above monster
        if (monsterPosY - player.getPlayerPosY() > 0) {
            // Check if future movement is traversable
            if (map.canTraverse(monsterPosX, monsterPosY, "up")) {
                return "w";
            }
        // Player under monster
        } else if (monsterPosY - player.getPlayerPosY() < 0) {
            // Check if future movement is traversable
            if (map.canTraverse(monsterPosX, monsterPosY, "down")) {
                return "s";
            }
        } else {
            return "stay";
        }
        return "other";
    }

    // Name setter
    public void setName (String x) {this.name = x;}
    // Name getter
    public String getName () {return this.name;}
    // First char getter
    public char getNameChar () {
        String name = this.name.toLowerCase();
        char n = name.charAt(0);
        return n;
    }

    // Max health setter
    public void setMaxHealth (int x) {this.maxHealth = x;}
    // Max health getter
    public int getMaxHealth () {return this.maxHealth;}

    // Health setter
    public void setCurHealth (int x) {this.curHealth = x;}
    // Health getter
    public int getCurHealth () {return this.curHealth;}

    // Damage setter
    public void setDamage (int x) {this.damage = x;}
    // Damage getter
    public int getDamage () {return this.damage;}

    // Set monster location
    public void setMonsterLocation (int x, int y) {
        this.monsterPosX = x;
        this.monsterPosY = y;
    }
    // Get monster location
    public int getMonsterPosX () {return monsterPosX;}
    public int getMonsterPosY () {return monsterPosY;}

}
