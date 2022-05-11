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
    protected int monsterPosX;
    protected int monsterPosY;

    // Constructor
    public Monster (String name) {
        super(name);
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
    @Override
    public void movement(String direction) {
        if (moveOrNot(playerPosX, playerPosY) == true) {
            switch (moveLogic()) {
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
                case "check code": // for development only, remove before upload
                System.out.println("Movement logic goes wrong");
            }
        }
    }


    // Name setter
    public void setName (String x) {this.name = x;}
    // Name getter
    public String getName () {return this.name;}

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
    private void setMonsterLocation (int x, int y) {
        this.monsterPosX = x;
        this.monsterPosY = y;
    }

    // If the monster starts to move or not
    private boolean moveOrNot (int playerPosX, int playerPosY) {
        if (distWithPlayer(playerPosX, playerPosY) <= Math.sqrt(8)) {
            return true;
        }
        return false;
    }

    // Distance function with the player
    private double distWithPlayer (int playerPosX, int playerPosY) {
        double dist = Math.sqrt((monsterPosX - playerPosX) ^ 2 + (monsterPosY - playerPosY) ^ 2);
        return dist;
    }

    // determine which way to move
    // Traversable not considered
    private String moveLogic () {
        if (monsterPosX > playerPosX) {
            return "a";
        } else if (monsterPosX < playerPosX) {
            return "d";
        } else if (monsterPosX == playerPosX & monsterPosY > playerPosY) {
            return "w";
        } else if (monsterPosX == playerPosX & monsterPosY < playerPosY) {
            return "s";
        } else {
            return "check code";
        }
    }
}
