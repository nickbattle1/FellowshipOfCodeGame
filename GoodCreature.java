/**
 * GoodCreature represents good creatures in the Fellowship, extending the Creature class.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
import java.util.Scanner;
import java.util.Random;

public abstract class GoodCreature extends Creature
{
    private boolean hasUsedSpecialWeapon;
    
    /**
     * Default constructor for GoodCreature.
     */
    public GoodCreature()
    {
        super();
        this.hasUsedSpecialWeapon = false;
    }
    
    /**
     * Non-default constructor for GoodCreature.
     * 
     * @param name The name of the good creature
     * @param power The power rating of the good creature
     */
    public GoodCreature(String name, int power)
    {
        super(name, power);
        this.hasUsedSpecialWeapon = false;
    }
    
    /**
     * Implements the fight method for good creatures, handling special weapons.
     * 
     * @param opponent The creature to fight against
     * @param random A random number generator for determining fight outcome
     * @param scanner Scanner for user input
     * @return true if this creature wins, false otherwise
     */
    public boolean fight(Creature opponent, Random random, Scanner scanner)
    {
        // Check if special weapon can be used
        boolean useSpecialWeapon = false;
        if (this.hasSpecialWeapon() && !this.hasUsedSpecialWeapon)
        {
            GameUtils.typeText(this.getName() + " has a special weapon available!");
            GameUtils.typeText("Use special weapon? (yes/no)");
            
            // Validation for the yes/no response
            String choice = "";
            boolean validInput = false;

            while(!validInput)
            {
                choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("exit"))
                {
                    GameUtils.typeText("\nExiting the game. Thanks for playing!");
                    System.exit(0);
                }
                
                if(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("no"))
                {
                    validInput = true;
                }
                else
                {
                    System.out.println("Please enter 'yes' or 'no':");
                }
            }

            if (choice.equalsIgnoreCase("yes"))
            {
                useSpecialWeapon = true;
                this.setHasUsedSpecialWeapon(true);
                GameUtils.typeText(this.getName() + " uses their special weapon!");
            }
        }
        
        // Determine outcome
        if (useSpecialWeapon)
        {
            GameUtils.typeText("The special weapon instantly defeats " + opponent.getName() + "!");
            return true; // Special weapon always wins
        }
        else
        {
            // Normal fight logic
            int powerDifference = this.getPower() - opponent.getPower();
            int winChance = calculateWinChance(powerDifference);
            
            int roll = random.nextInt(100) + 1;
            boolean wins = roll <= winChance;
            
            GameUtils.typeText("Power difference: " + powerDifference);
            GameUtils.typeText("Win chance: " + winChance + "%");
            GameUtils.typeText("Roll: " + roll);
            
            return wins;
        }
    }
    
    /**
     * Implementation of the abstract fight method from Creature.
     * This is a simplified version when no scanner is available.
     * 
     * @param opponent The creature to fight against
     * @param random A random number generator for determining fight outcome
     * @return true if this creature wins, false otherwise
     */
    @Override
    public boolean fight(Creature opponent, Random random)
    {
        // For this version, never use special weapon
        int powerDifference = this.getPower() - opponent.getPower();
        int winChance = calculateWinChance(powerDifference);
        
        int roll = random.nextInt(100) + 1;
        return roll <= winChance;
    }
    
    /**
     * Displays information about the good creature.
     */
    @Override
    public void display()
    {
        super.display();
        System.out.println("Has used special weapon: " + this.hasUsedSpecialWeapon);
    }
    
    /**
     * Checks if the creature has a special weapon.
     * 
     * @return true if the creature has a special weapon, false otherwise
     */
    public abstract boolean hasSpecialWeapon();
    
    /**
     * Checks if the creature has used their special weapon.
     * 
     * @return true if the special weapon has been used, false otherwise
     */
    public boolean hasUsedSpecialWeapon()
    {
        return this.hasUsedSpecialWeapon;
    }
    
    /**
     * Sets whether the creature has used their special weapon.
     * 
     * @param hasUsedSpecialWeapon true if the special weapon has been used
     */
    public void setHasUsedSpecialWeapon(boolean hasUsedSpecialWeapon)
    {
        this.hasUsedSpecialWeapon = hasUsedSpecialWeapon;
    }
    
    /**
     * Returns a string representation of the good creature.
     * 
     * @return String representation
     */
    @Override
    public String toString()
    {
        return super.toString() + (this.hasSpecialWeapon() ? 
                " [Has " + (this.hasUsedSpecialWeapon ? "used" : "not used") + 
                " special weapon]" : "");
    }
}