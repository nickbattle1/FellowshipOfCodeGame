/**
 * Orc represents an orc creature in the caves, extending EvilCreature.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
public class Orc extends EvilCreature
{
    private static final int ORC_POWER = 5;
    
    /**
     * Default constructor for Orc.
     */
    public Orc()
    {
        super("Orc", ORC_POWER);
    }
    
    /**
     * Non-default constructor for Orc.
     * 
     * @param name The name of the orc
     */
    public Orc(String name)
    {
        super(name, ORC_POWER);
    }
    
    /**
     * Displays information about the orc.
     */
    @Override
    public void display()
    {
        super.display();
        System.out.println("Orc is an evil creature with medium power.");
    }
}