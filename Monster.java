/**
 * This class is for setting and getting monster infomation
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public class Monster extends Unit {
    // All of the variables are from user inputs
    private String name;
    private int maxHealth;
    private int damage;
    private int curHealth;
    private int monsterPosX;
    private int monsterPosY;

    // Constructor
    public Monster (String name) {
        super(name);
    }

    public Monster (String[] infoSet) {
        super(infoSet[2]);
        this.setMaxHealth(Integer.parseInt(infoSet[3]));
        this.setCurHealth(getMaxHealth());
        this.setDamage(Integer.parseInt(infoSet[4]));
        this.setMonsterLocation(Integer.parseInt(infoSet[0]), Integer.parseInt(infoSet[1]));
    }

    // Default
    public Monster () {}

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
    public void movement(Map map) {
        if (distCheck(playerPosX, playerPosY)) {
            switch (moveLogic(map)) {
                case "w":
                setMonsterLocation(monsterPosX, monsterPosY -1);
                break;
                case "a":
                setMonsterLocation(monsterPosX - 1, monsterPosY);
                break;
                case "s":
                setMonsterLocation(monsterPosX, monsterPosY + 1);
                break;
                case "d":
                setMonsterLocation(monsterPosX + 1, monsterPosY);
                break;
                case "stay":
                setMonsterLocation(monsterPosX, monsterPosY);
                break;

            }
        }
    }

    // If the monster starts to move or not
    private boolean distCheck (int playerPosX, int playerPosY) {
        if (distWithPlayer(monsterPosX, monsterPosY, playerPosX, playerPosY) <= 4) {
            return true;
        }
        
        return false;
    }

    // Distance function with the player
    private double distWithPlayer (int monsterPosX, int monsterPosY, int playerPosX, int playerPosY) {
        double dist = Math.abs(monsterPosX - playerPosX) + Math.abs(monsterPosY - playerPosY);
        return dist;
    }

    // determine which way to move
    private String moveLogic (Map map) {
        
        // Determine relative location
        // Player on monster's left hand side
        if (monsterPosX - playerPosX > 0) {
            // Check if future movement is traversable
            if (map.canTraverse(monsterPosX, monsterPosY, "left")) {
                return "a";
            }
        // Player on monster's right hand side
        } else if (monsterPosX - playerPosX < 0) {
            // Check if future movement is traversable
            if (map.canTraverse(monsterPosX, monsterPosY, "right")) {
                return "d";
            }
        }

        // Player above monster
        if (monsterPosY - playerPosY > 0) {
            // Check if future movement is traversable
            if (map.canTraverse(monsterPosX, monsterPosY, "up")) {
                return "w";
            }
        // Player under monster
        } else if (monsterPosY - playerPosY < 0) {
            // Check if future movement is traversable
            if (map.canTraverse(monsterPosX, monsterPosY, "down")) {
                return "s";
            }
        }
        // else stay
        return "stay";
        
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
