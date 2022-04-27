/**
 * This class is for setting and getting monster infomation
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public class Monster {
    // All of the variables are from user inputs
    private String name;
    private int maxHealth;
    private int damage;
    private int curHealth;

    // Constructor
    public Monster () {
        this.name = null;
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

    // Print monster info
    public void info (String name, int health, int damage) {
        System.out.println ("Monster name: " +  name );
        System.out.println ("Monster health: " + health);
        System.out.println ("Monster damage: " + damage);
        System.out.printf ("Monster '%s' created.\n\n", name);
    }

}
