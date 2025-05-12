/**
 * Goblin represents a goblin creature in the caves, extending EvilCreature.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
public class Goblin extends EvilCreature
{
    private static final int GOBLIN_POWER = 3;
    
    /**
     * Default constructor for Goblin.
     */
    public Goblin()
    {
        super("Goblin", GOBLIN_POWER);
    }
    
    /**
     * Non-default constructor for Goblin.
     * 
     * @param name The name of the goblin
     */
    public Goblin(String name)
    {
        super(name, GOBLIN_POWER);
    }
    
    /**
     * Displays information about the goblin.
     */
    @Override
    public void display()
    {
        super.display();
        System.out.println("Goblin is an evil creature with low power.");
    }
}