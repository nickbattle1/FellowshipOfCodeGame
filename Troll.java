/**
 * Troll represents a troll creature in the caves, extending EvilCreature.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
public class Troll extends EvilCreature
{
    private static final int TROLL_POWER = 9;
    
    /**
     * Default constructor for Troll.
     */
    public Troll()
    {
        super("Troll", TROLL_POWER);
    }
    
    /**
     * Non-default constructor for Troll.
     * 
     * @param name The name of the troll
     */
    public Troll(String name)
    {
        super(name, TROLL_POWER);
    }
    
    /**
     * Displays information about the troll.
     */
    @Override
    public void display()
    {
        super.display();
        System.out.println("Troll is an evil creature with high power.");
    }
}