/**
 * Dwarf represents a dwarf creature in the Fellowship, extending GoodCreature.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
public class Dwarf extends GoodCreature
{
    private static final int DWARF_POWER = 7;
    
    /**
     * Default constructor for Dwarf.
     */
    public Dwarf()
    {
        super("Gimli", DWARF_POWER);
    }
    
    /**
     * Non-default constructor for Dwarf.
     * 
     * @param name The name of the dwarf
     */
    public Dwarf(String name)
    {
        super(name, DWARF_POWER);
    }
    
    /**
     * Displays information about the dwarf.
     */
    @Override
    public void display()
    {
        super.display();
        System.out.println("Dwarf is a member of the Fellowship with high power but no special weapon.");
    }
    
    /**
     * Checks if the dwarf has a special weapon.
     * 
     * @return false as dwarves don't have a special weapon
     */
    @Override
    public boolean hasSpecialWeapon()
    {
        return false;
    }
}