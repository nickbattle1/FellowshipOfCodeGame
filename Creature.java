/**
 * Creature is the base abstract class for all living entities in the game,
 * both good and evil.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
import java.util.Random;

public abstract class Creature
{
    private String name;
    private int power;
    private int damagePoints;
    
    /**
     * Default constructor for Creature.
     */
    public Creature()
    {
        this.name = "Unknown";
        this.power = 0;
        this.damagePoints = 0;
    }
    
    /**
     * Non-default constructor for Creature.
     * 
     * @param name The name of the creature
     * @param power The power rating of the creature
     */
    public Creature(String name, int power)
    {
        this.name = name;
        this.power = power;
        this.damagePoints = 0;
    }
    
    /**
     * Abstract method for fighting another creature.
     * Each creature type can have its own implementation.
     * 
     * @param opponent The creature to fight against
     * @param random A random number generator for determining fight outcome
     * @return true if this creature wins, false otherwise
     */
    public abstract boolean fight(Creature opponent, Random random);
    
    /**
     * Calculates the win chance based on power difference.
     * 
     * @param powerDifference The power difference between creatures
     * @return The win chance as a percentage
     */
    protected int calculateWinChance(int powerDifference)
    {
        if (powerDifference >= 4)
        {
            return 90;
        }
        else if (powerDifference >= 3)
        {
            return 80;
        }
        else if (powerDifference >= 2)
        {
            return 70;
        }
        else if (powerDifference >= 1)
        {
            return 60;
        }
        else if (powerDifference == 0)
        {
            return 50;
        }
        else if (powerDifference >= -1)
        {
            return 40;
        }
        else if (powerDifference >= -2)
        {
            return 30;
        }
        else if (powerDifference >= -3)
        {
            return 20;
        }
        else
        {
            return 10;
        }
    }
    
    /**
     * Decreases the creature's damage points by the specified amount.
     * 
     * @param amount The amount to decrease the damage by
     */
    public void decreaseDamage(int amount)
    {
        if (amount > 0)
        {
            this.damagePoints = Math.max(0, this.damagePoints - amount);
        }
    }
    
    /**
     * Displays information about the creature.
     */
    public void display()
    {
        System.out.println("Name: " + this.name);
        System.out.println("Type: " + this.getClass().getSimpleName());
        System.out.println("Power: " + this.power);
        System.out.println("Damage: " + this.damagePoints);
    }
    
    /**
     * Gets the damage points of the creature.
     * 
     * @return The damage points
     */
    public int getDamagePoints()
    {
        return this.damagePoints;
    }
    
    /**
     * Gets the name of the creature.
     * 
     * @return The name
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Gets the power rating of the creature.
     * 
     * @return The power rating
     */
    public int getPower()
    {
        return this.power;
    }
    
    /**
     * Increases the creature's damage points by the specified amount.
     * 
     * @param amount The amount to increase the damage by
     */
    public void increaseDamage(int amount)
    {
        if (amount > 0)
        {
            this.damagePoints += amount;
        }
    }
    
    /**
     * Sets the damage points of the creature.
     * 
     * @param damagePoints The damage points to set
     */
    public void setDamagePoints(int damagePoints)
    {
        if (damagePoints >= 0)
        {
            this.damagePoints = damagePoints;
        }
    }
    
    /**
     * Sets the name of the creature.
     * 
     * @param name The name to set
     */
    public void setName(String name)
    {
        if (name != null && !name.isEmpty())
        {
            this.name = name;
        }
    }
    
    /**
     * Sets the power rating of the creature.
     * 
     * @param power The power rating to set
     */
    public void setPower(int power)
    {
        if (power >= 0)
        {
            this.power = power;
        }
    }
    
    /**
     * Returns a string representation of the creature.
     * 
     * @return String representation
     */
    @Override
    public String toString()
    {
        return this.name + " (" + this.getClass().getSimpleName() + 
                ", Power: " + this.power + 
                ", Damage: " + this.damagePoints + ")";
    }
}