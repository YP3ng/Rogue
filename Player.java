/**
 * Player's functionalities are all stored here. Included name, level, maxHealth, attackDamage, summary
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public class Player extends Unit {
    private String name;
    private int level;
    private int curHealth;
    // protected int playerPosX;
    // protected int playerPosY;

    //constructor
    public Player (String name) {
        super(name);
        this.level = 1;
        this.curHealth = this.maxHealth (level);
        // initialise player playerPosX, Y
        this.setPlayerLocation(1, 1);
    }

    public Player () {
        this.level = 1;
        this.curHealth = this.maxHealth (level);
    }

    // Display player's information
    @Override
    public void displayInfo (String name) {
        System.out.print (name + " (Lv. " + level + ")\n");
        System.out.print ("Damage: " + this.getDamage() +"\n");
        System.out.print ("Health: " + this.getCurHealth() + "/" + this.getMaxHealth() + "\n\n");
    }

    // Define the movement of player
    @Override
    public void movement (String direction) {
        switch (direction) {
            case "w":
                setPlayerLocation (playerPosX, playerPosY - 1);
                break;
            case "a":
                setPlayerLocation (playerPosX - 1, playerPosY);
                break;
            case "s":
                setPlayerLocation (playerPosX, playerPosY + 1);
                break;
            case "d":
                setPlayerLocation (playerPosX + 1, playerPosY);
                break;
        }
    }

    // Name setter
    public void setName (String x) {this.name = x;}
    // Name getter
    public String getName () {return this.name;}

    // Level setter
    public void setLevel (int x) {this.level = x;}
    // Level getter
    public int getLevel () {return this.level;}

    // CurHealth setter
    public void setCurHealth (int x) {this.curHealth = x;}
    // CurHealth getter
    public int getCurHealth () {return this.curHealth;}

    // MaxHealth getter
    public int getMaxHealth () {return this.maxHealth (this.level);}

    // Attack damage getter
    public int getDamage () {return this.attackDamage (this.level);}

    // Set player location after user's input
    public void setPlayerLocation (int x, int y) {
        this.playerPosX = x;
        this.playerPosY = y;
    }

    // MaxHealth method
    private int maxHealth (int level) {
        int maxHealth = 17 + level * 3;
        return maxHealth;
    }
    
    // Attack damage method
    private int attackDamage (int level) {
        int damage = 1 + level;
        return damage;
    }

    
}
