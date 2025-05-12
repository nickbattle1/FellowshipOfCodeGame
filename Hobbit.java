/**
 * Hobbit represents a hobbit creature in the Fellowship, extending GoodCreature.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
public class Hobbit extends GoodCreature
{
    private static final int HOBBIT_POWER = 3;
    
    /**
     * Default constructor for Hobbit.
     */
    public Hobbit()
    {
        super("Frodo", HOBBIT_POWER);
    }
    
    /**
     * Non-default constructor for Hobbit.
     * 
     * @param name The name of the hobbit
     */
    public Hobbit(String name)
    {
        super(name, HOBBIT_POWER);
    }
    
    /**
     * Displays information about the hobbit.
     */
    @Override
    public void display()
    {
        super.display();
        System.out.println("Hobbit is the leader of the Fellowship.");
    }
    
    /**
     * Checks if the hobbit has a special weapon.
     * 
     * @return true as hobbits have a special weapon
     */
    @Override
    public boolean hasSpecialWeapon()
    {
        return true;
    }
}