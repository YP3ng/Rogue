/**
 * "@" level up the player by 1 and exit the world (return to main menu)
 * @author Yi-Cheng Peng, yicpeng@student.unimelb.edu.au, 1319296
 *
 */
public class WarpStone extends Item {

    private int warpStonePosX;
    private int warpStonePosY;
    
    // Constructor
    public WarpStone(String name, String[] itemData) {
        super(name);
        setItemLocation(Integer.parseInt(itemData[1]), Integer.parseInt(itemData[2]));
    }

    /**
     * Level up player by 1 and exit world
     * Return back to the main menu
     */
    @Override
    public String effect(Player player) {
        
        System.out.println("World complete! (You leveled up!)");
        System.out.println();
        player.setLevel(player.getLevel() + 1);
       
        //return to main menu
        return "warp";

    }



    // Set Item location
    public void setItemLocation (int x, int y) {
        this.warpStonePosX = x;
        this.warpStonePosY = y;
    };
    // Get Item location
    public int getItemPosX () {return warpStonePosX;};
    public int getItemPosY () {return warpStonePosY;};

    // Get Item name
    public char getItemName () {return name.charAt(0);}
}
