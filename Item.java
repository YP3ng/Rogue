public abstract class Item extends Entity {
    
    public Item(String name) {
        super(name);
    }

    public abstract void effect();
}
