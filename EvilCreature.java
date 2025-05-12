/**
 * EvilCreature represents evil creatures in the caves, extending the Creature class.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
import java.util.Random;

public abstract class EvilCreature extends Creature
{
    /**
     * Default constructor for EvilCreature.
     */
    public EvilCreature()
    {
        super();
    }
    
    /**
     * Non-default constructor for EvilCreature.
     * 
     * @param name The name of the evil creature
     * @param power The power rating of the evil creature
     */
    public EvilCreature(String name, int power)
    {
        super(name, power);
    }
    
    /**
     * Implements the fight method for evil creatures.
     * Evil creatures rely purely on their power rating.
     * 
     * @param opponent The creature to fight against
     * @param random A random number generator for determining fight outcome
     * @return true if this creature wins, false otherwise
     */
    @Override
    public boolean fight(Creature opponent, Random random)
    {
        int powerDifference = this.getPower() - opponent.getPower();
        int winChance = calculateWinChance(powerDifference);
        
        int roll = random.nextInt(100) + 1;
        boolean wins = roll <= winChance;
        
        GameUtils.typeText("Power difference: " + powerDifference);
        GameUtils.typeText("Win chance: " + winChance + "%");
        GameUtils.typeText("Roll: " + roll);
        
        return wins;
    }
    
    /**
     * Displays information about the evil creature.
     */
    @Override
    public void display()
    {
        super.display();
        System.out.println("Evil creature that tries to steal the code.");
    }
}