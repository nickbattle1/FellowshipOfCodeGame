/**
 * Cave represents a cave in the labyrinth of the game.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
public class Cave
{
    private int id;
    private int north;
    private int east;
    private int south;
    private int west;
    private Creature creature;
    
    /**
     * Default constructor for Cave.
     */
    public Cave()
    {
        this.id = 0;
        this.north = 0;
        this.east = 0;
        this.south = 0;
        this.west = 0;
        this.creature = null;
    }
    
    /**
     * Non-default constructor for Cave.
     * 
     * @param id The cave id
     * @param north The id of the cave to the north, or 0 if blocked
     * @param east The id of the cave to the east, or 0 if blocked
     * @param south The id of the cave to the south, or 0 if blocked
     * @param west The id of the cave to the west, or 0 if blocked
     */
    public Cave(int id, int north, int east, int south, int west)
    {
        this.id = id;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
        this.creature = null;
    }
    
    /**
     * Displays information about the cave.
     */
    public void display()
    {
        System.out.println("Cave ID: " + this.id);
        System.out.println("Connected caves: ");
        
        if (this.north != 0)
        {
            String destination = this.north == 100 ? "Mount Api" : "Cave " + this.north;
            System.out.println("  North: " + destination);
        }
        if (this.east != 0)
        {
            String destination = this.east == 100 ? "Mount Api" : "Cave " + this.east;
            System.out.println("  East: " + destination);
        }
        if (this.south != 0)
        {
            String destination = this.south == 100 ? "Mount Api" : "Cave " + this.south;
            System.out.println("  South: " + destination);
        }
        if (this.west != 0)
        {
            String destination = this.west == 100 ? "Mount Api" : "Cave " + this.west;
            System.out.println("  West: " + destination);
        }
        
        if (this.creature != null)
        {
            System.out.println("Creature in cave: " + this.creature.getName() + 
                    " (" + this.creature.getClass().getSimpleName() + ")");
        }
        else
        {
            System.out.println("No creature in this cave.");
        }
    }
    
    /**
     * Gets the creature in the cave.
     * 
     * @return The creature in the cave
     */
    public Creature getCreature()
    {
        return this.creature;
    }
    
    /**
     * Gets the id of the cave to the east.
     * 
     * @return The id of the cave to the east
     */
    public int getEast()
    {
        return this.east;
    }
    
    /**
     * Gets the id of the cave.
     * 
     * @return The id of the cave
     */
    public int getId()
    {
        return this.id;
    }
    
    /**
     * Gets the id of the cave to the north.
     * 
     * @return The id of the cave to the north
     */
    public int getNorth()
    {
        return this.north;
    }
    
    /**
     * Gets the id of the cave to the south.
     * 
     * @return The id of the cave to the south
     */
    public int getSouth()
    {
        return this.south;
    }
    
    /**
     * Gets the id of the cave to the west.
     * 
     * @return The id of the cave to the west
     */
    public int getWest()
    {
        return this.west;
    }
    
    /**
     * Checks if there is an exit to Mount Api from this cave.
     * 
     * @return true if there is an exit to Mount Api, false otherwise
     */
    public boolean hasExitToMountApi()
    {
        return this.north == 100 || this.east == 100 || 
                this.south == 100 || this.west == 100;
    }
    
    /**
     * Sets the creature in the cave.
     * 
     * @param creature The creature to set
     */
    public void setCreature(Creature creature)
    {
        this.creature = creature;
    }
    
    /**
     * Sets the id of the cave to the east.
     * 
     * @param east The id of the cave to the east
     */
    public void setEast(int east)
    {
        if (east >= 0)
        {
            this.east = east;
        }
    }
    
    /**
     * Sets the id of the cave.
     * 
     * @param id The id of the cave
     */
    public void setId(int id)
    {
        if (id >= 0)
        {
            this.id = id;
        }
    }
    
    /**
     * Sets the id of the cave to the north.
     * 
     * @param north The id of the cave to the north
     */
    public void setNorth(int north)
    {
        if (north >= 0)
        {
            this.north = north;
        }
    }
    
    /**
     * Sets the id of the cave to the south.
     * 
     * @param south The id of the cave to the south
     */
    public void setSouth(int south)
    {
        if (south >= 0)
        {
            this.south = south;
        }
    }
    
    /**
     * Sets the id of the cave to the west.
     * 
     * @param west The id of the cave to the west
     */
    public void setWest(int west)
    {
        if (west >= 0)
        {
            this.west = west;
        }
    }
    
    /**
     * Returns a string representation of the cave.
     * 
     * @return String representation
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Cave ").append(this.id).append(": ");
        
        // Add connections
        boolean hasConnection = false;
        
        if (this.north != 0)
        {
            String destination = this.north == 100 ? "Mount Api" : "Cave " + this.north;
            sb.append("North → ").append(destination);
            hasConnection = true;
        }
        
        if (this.east != 0)
        {
            if (hasConnection)
            {
                sb.append(", ");
            }
            String destination = this.east == 100 ? "Mount Api" : "Cave " + this.east;
            sb.append("East → ").append(destination);
            hasConnection = true;
        }
        
        if (this.south != 0)
        {
            if (hasConnection)
            {
                sb.append(", ");
            }
            String destination = this.south == 100 ? "Mount Api" : "Cave " + this.south;
            sb.append("South → ").append(destination);
            hasConnection = true;
        }
        
        if (this.west != 0)
        {
            if (hasConnection)
            {
                sb.append(", ");
            }
            String destination = this.west == 100 ? "Mount Api" : "Cave " + this.west;
            sb.append("West → ").append(destination);
        }
        
        // Add creature information
        if (this.creature != null)
        {
            sb.append(" [Contains: ").append(this.creature.getName()).append(" (")
              .append(this.creature.getClass().getSimpleName()).append(")]");
        }
        
        return sb.toString();
    }
}