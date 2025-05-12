/**
 * Elf represents an elf creature in the Fellowship, extending GoodCreature.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
public class Elf extends GoodCreature
{
    private static final int ELF_POWER = 5;
    
    /**
     * Default constructor for Elf.
     */
    public Elf()
    {
        super("Legolas", ELF_POWER);
    }
    
    /**
     * Non-default constructor for Elf.
     * 
     * @param name The name of the elf
     */
    public Elf(String name)
    {
        super(name, ELF_POWER);
    }
    
    /**
     * Displays information about the elf.
     */
    @Override
    public void display()
    {
        super.display();
        System.out.println("Elf is a member of the Fellowship with a special weapon.");
    }
    
    /**
     * Checks if the elf has a special weapon.
     * 
     * @return true as elves have a special weapon
     */
    @Override
    public boolean hasSpecialWeapon()
    {
        return true;
    }
}