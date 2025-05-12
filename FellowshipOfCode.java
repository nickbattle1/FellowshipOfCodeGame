/**
* FellowshipOfCode is the main class that runs the game:
* Fellowship of Code: A Java Adventure in Middle Earth.
*
* @author Nicholas Battle
* @version 1.0
*/
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.io.FileNotFoundException;

public class FellowshipOfCode
{
    private ArrayList<Creature> fellowship;
    private ArrayList<Cave> labyrinth;
    private ArrayList<Cave> visitedCaves;
    private ArrayList<Creature> deadCreatures;
    private Creature codeHolder;
    private int codeExchanges;
    private int totalFights;
    private int fellowshipWins;
    private boolean gameActive;
    private Scanner scanner;
    private Random random;
    private int typingSpeed = 30; //milliseconds between characters, adjust as desired (higher number = slower typing)
    private static final int MOUNT_API_ID = 100; // ID for Mount Api
    private static final int MAX_DAMAGE_POINTS = 10; // Maximum damage points before death
    
    // Array of evil creature types - demonstrates use of arrays alongside ArrayLists
    private static final String[] EVIL_CREATURE_TYPES = {"Orc", "Troll", "Goblin"};
    
    // Array of direction names - demonstrates use of arrays
    private static final String[] DIRECTIONS = {"North", "East", "South", "West"};

    /**
    * Default constructor for FellowshipOfCode
    */
    public FellowshipOfCode()
    {
        this.fellowship = new ArrayList<>();
        this.labyrinth = new ArrayList<>();
        this.visitedCaves = new ArrayList<>();
        this.deadCreatures = new ArrayList<>();
        this.codeExchanges = 0;
        this.totalFights = 0;
        this.fellowshipWins = 0;
        this.gameActive = true;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    /**
    * Types text character with a delay for a typing effect to improve user experience and readability
    *
    * @param text The text to display with typing effect
    */
    private void typeText(String text)
    {
        GameUtils.typeText(text, this.typingSpeed);
    }

    /**
    * Main method to run the game.
    *
    * @param args Command line arguments (not used)
    */
    public static void main(String[] args)
    {
        FellowshipOfCode game = new FellowshipOfCode();
        game.displayWelcome();
        game.createFellowship();
        game.loadLabyrinth();
        game.startQuest();
        game.displaySummary();
        game.writeSummaryToFile();
    }

    /**
    * Adds a creature to the dead creatures list
    *
    * @param creature The creature that died :(
    */
    public void addDeadCreature(Creature creature)
    {
        if (creature != null)
        {
            this.deadCreatures.add(creature);
        }
    }

    /**
    * Adds a visited cave to the list of visited caves.
    *
    * @param cave The cave that was visited
    */
    public void addVisitedCave(Cave cave)
    {
        if (cave != null && !this.visitedCaves.contains(cave))
        {
            this.visitedCaves.add(cave);
        }
    }

    /**
    * Checks if the input is "exit" and exits the program if it is.
    *
    * @param input The input string to check
    * @return True if the program should exit, false otherwise
    */
    private boolean checkForExit(String input)
    {
        if (input.equalsIgnoreCase("exit"))
        {
            typeText("\nExiting the game. Thanks for playing!");
            System.exit(0);
            return true;
        }
        return false;
    }

    /**
    * Creates the fellowship based on user input
    */
    private void createFellowship()
    {
        typeText("\nNow you must choose the members of your Fellowship (maximum of 4).");
        typeText("The leader will be a hobbit and the rest can be elves or dwarves.");

        //Add hobbit leader
        typeText("\nFirst, let's name your hobbit leader:");
        String name = "";
        boolean validName = false;
        
        while (!validName)
        {
            name = this.scanner.nextLine();
            if (checkForExit(name)) return; //to exit the game.
            
            if (GameUtils.isValidCreatureName(name))
            {
                validName = true;
            }
            else
            {
                typeText("Please enter a valid name (not empty and not just a number):");
            }
        }
        
        Hobbit hobbit = new Hobbit(name);
        this.fellowship.add(hobbit);
        this.codeHolder = hobbit; // Hobbit starts with the code :)

        int remainingMembers = 3;
        while (remainingMembers > 0)
        {
            typeText("\nChoose member type (or enter 'done' to continue with current Fellowship):");
            typeText("1. Elf (Power: 5. has special weapon)");
            typeText("2. Dwarf (Power: 7, no special weapon)");

            String choice = this.scanner.nextLine();
            if (checkForExit(choice)) return; //to exit the game.

            if (choice.equalsIgnoreCase("done"))
            {
                break;
            }

            //add validations for numeric input
            int memberChoice = GameUtils.validateNumericInput(choice, 1, 2);
            if (memberChoice == -1)
            {
                typeText("Please enter a valid number (1-2) or 'done':");
                continue;
            }

            //ask for name
            typeText("Enter name for this " + (memberChoice == 1 ? "elf" : "dwarf") + ":");
            validName = false;
            
            while (!validName)
            {
                name = this.scanner.nextLine();
                if (checkForExit(name)) return; //to exit the game.
                
                if (GameUtils.isValidCreatureName(name))
                {
                    validName = true;
                }
                else
                {
                    typeText("Please enter a valid name (not empty and not just a number):");
                }
            }
            
            if (memberChoice == 1)
            {
                this.fellowship.add(new Elf(name));
                typeText("Elf " + name + " added to the Fellowship!");
            }
            else
            {
                this.fellowship.add(new Dwarf(name));
                typeText("Dwarf " + name + " added to the Fellowship!");
            }

            remainingMembers--;
        }

        typeText("\nYour Fellowship consists of:");
        for (Creature member : this.fellowship)
        {
            typeText("- " + member.getName() + " (" + member.getClass().getSimpleName() + ")");
        }
    }

    /**
    * Displays the fight success rate of the fellowship
    *
    * @return Fight success rate
    */
    public double displayFightSuccessRate()
    {
        if (this.totalFights == 0)
        {
            return 0.0;
        }

        return (double) this.fellowshipWins * 100 / this.totalFights;
    }

    /**
    * Displays the game status during play
    *
    * @param currentCave The current cave
    * @param nextCave The next cave to enter
    */
    private void displayGameStatus(Cave currentCave, Cave nextCave)
    {
        typeText("\n--- CURRENT STATUS ---");

        //display visited caves
        typeText("\nVisited Caves:");
        for (Cave cave : this.visitedCaves)
        {
            String creatureInfo = cave.getCreature() != null ?
                    " (Contains " + cave.getCreature().getClass().getSimpleName() + " - "
                    + cave.getCreature().getName() + ")" : " (Empty)";
            typeText("Cave " + cave.getId() + creatureInfo);
        }

        //display code holder
        typeText("\nSecret Code is held by: " + this.codeHolder.getName() + 
                " (" + this.codeHolder.getClass().getSimpleName() + ")");
        
        //display damage points
        typeText("\nDamage Points:");
        for (Creature member : this.fellowship)
        {
            if (!this.deadCreatures.contains(member))
            {
                typeText(member.getName() + " (" + 
                        member.getClass().getSimpleName() + "): " + member.getDamagePoints());
            }
        }

        for (Cave cave : this.visitedCaves)
        {
            if (cave.getCreature() != null && !this.deadCreatures.contains(cave.getCreature()))
            {
                typeText(cave.getCreature().getName() + " (" + 
                        cave.getCreature().getClass().getSimpleName() + "): " + 
                        cave.getCreature().getDamagePoints());
            }
        }

        //display next cave
        typeText("\nNext cave to enter: Cave " + nextCave.getId());
    }

    /**
    * Gets the active fellowship members as an array.
    * This method demonstrates converting from ArrayList to array.
    * 
    * @return Array of active fellowship members
    */
    public Creature[] getActiveFellowshipMembersArray()
    {
        return getActiveFellowshipMembers().toArray(new Creature[0]);
    }

    /**
    * Displays summary at the end of the game
    */
    private void displaySummary()
    {
        typeText("\n=== QUEST SUMMARY ===");

        //display outcome
        boolean success = false;
        for (Creature member : this.fellowship)
        {
            if (!this.deadCreatures.contains(member) && member == this.codeHolder)
            {
                success = true;
                break;
            }
        }

        if (success)
        {
            typeText("OUTCOME: SUCCESS! The code was delivered to the Java Wizard on Mount Api.");
            typeText("The code was delivered by: " + this.codeHolder.getName() + 
                    " (" + this.codeHolder.getClass().getSimpleName() + ")");
        }
        else
        {
            typeText("OUTCOME: FAILURE! The Fellowship failed to deliver the code.");
            typeText("The code is held by: " + this.codeHolder.getName() + 
                    " (" + this.codeHolder.getClass().getSimpleName() + ")");
        }

        //display stats
        typeText("\nNumber of caves visited: " + this.visitedCaves.size());
        typeText("Number of times the secret code changed hands: " + this.codeExchanges);

        //display dead creatures
        typeText("\nCreatures that died during the quest:");
        if (this.deadCreatures.isEmpty())
        {
            typeText("None");
        }
        else
        {
            for (Creature creature : this.deadCreatures)
            {
                typeText("- " + creature.getName() + " (" + 
                        creature.getClass().getSimpleName() + ")");
            }
        }
        
        //display surviving fellowship members using an array
        typeText("\nSurviving Fellowship members:");
        Creature[] survivingMembers = getActiveFellowshipMembersArray();
        if (survivingMembers.length == 0)
        {
            typeText("None - all Fellowship members perished");
        }
        else
        {
            for (int i = 0; i < survivingMembers.length; i++)
            {
                typeText((i + 1) + ". " + survivingMembers[i].getName() + 
                        " (" + survivingMembers[i].getClass().getSimpleName() + 
                        ", Damage: " + survivingMembers[i].getDamagePoints() + ")");
            }
        }

        //display fight success rate
        typeText("\nFellowship fight success rate: " + 
                String.format("%.2f", this.displayFightSuccessRate()) + "%");
    }

    /**
    * Displays welcome message and instructions.
    */
    private void displayWelcome()
    {
        System.out.println("=======================================================");
        typeText("   FELLOWSHIP OF CODE: A JAVA ADVENTURE IN MIDDLE EARTH");
        System.out.println("=======================================================");
        typeText("\nWelcome brave adventurer!");
        typeText("Your quest is to lead a Fellowship through a labyrinth in Middle Earth");
        typeText("to deliver a secret code to the Java Wizard on Mount Api.");

        typeText("\nINSTRUCTIONS:");
        typeText("- Your fellowship will be led by a hobbit and can include elves and dwarves");
        typeText("- Navigate through caves and fight evil creatures (orcs, trolls and goblins)");
        typeText("- Protect the secret code from being stolen");
        typeText("- Reach Mount Api with the code to complete your quest");
        typeText("- If all Fellowship members die, the quest fails");
        typeText("- Good luck on your journey!");
        typeText("- Type 'exit' at any prompt to exit the program.");
    }

    /**
    * Gets the active fellowship members (not dead).
    *
    * @return ArrayList of active fellowship members
    */
    public ArrayList<Creature> getActiveFellowshipMembers()
    {
        ArrayList<Creature> activeMembers = new ArrayList<>();

        for (Creature member : this.fellowship)
        {
            if (!this.deadCreatures.contains(member))
            {
                activeMembers.add(member);
            }
        }

        return activeMembers;
    }

    /**
    * Gets the fellowship members list
    *
    * @return ArrayList of fellowship members
    */
    public ArrayList<Creature> getFellowship()
    {
        return this.fellowship;
    }

    /**
    * Handles a fight between a Fellowship member and a cave creature.
    *
    * @param fellowshipMember The fellowship member fighting
    * @param caveCreature The cave creature being fought
    */
    private void handleFight(Creature fellowshipMember, Creature caveCreature)
    {
        typeText("\n--- FIGHT BEGINS ---");
        typeText(fellowshipMember.getName() + " (" + 
                fellowshipMember.getClass().getSimpleName() + ") vs " + 
                caveCreature.getName() + " (" + caveCreature.getClass().getSimpleName() + ")");
        
        this.totalFights++;
        boolean fellowshipWins;
        
        // Use the polymorphic fight method
        if (fellowshipMember instanceof GoodCreature)
        {
            // For good creatures, use the version with scanner for special weapon option
            fellowshipWins = ((GoodCreature) fellowshipMember).fight(caveCreature, this.random, this.scanner);
        }
        else
        {
            // Just in case, though fellowship members should always be GoodCreature
            fellowshipWins = fellowshipMember.fight(caveCreature, this.random);
        }
        
        // Apply outcome
        if (fellowshipWins)
        {
            typeText(fellowshipMember.getName() + " wins the fight!");
            this.fellowshipWins++;
            
            // Check if special weapon was used (for damage points calculation)
            boolean usedSpecialWeapon = false;
            if (fellowshipMember instanceof GoodCreature && 
                    ((GoodCreature) fellowshipMember).hasSpecialWeapon() && 
                    ((GoodCreature) fellowshipMember).hasUsedSpecialWeapon())
            {
                usedSpecialWeapon = true;
                this.deadCreatures.add(caveCreature);
            }

            // Update damage points
            if (!usedSpecialWeapon)
            {
                fellowshipMember.setDamagePoints(fellowshipMember.getDamagePoints() + 1);
                caveCreature.setDamagePoints(caveCreature.getDamagePoints() + 4);
            }

            // Update code holder (if necessary)
            if (this.codeHolder == caveCreature)
            {
                this.codeHolder = fellowshipMember;
                this.codeExchanges++;
                typeText(fellowshipMember.getName() + " has recovered the secret code!");
            }
        }
        else
        {
            typeText(caveCreature.getName() + " wins the fight!");

            // Update damage points
            fellowshipMember.setDamagePoints(fellowshipMember.getDamagePoints() + 4);
            caveCreature.setDamagePoints(caveCreature.getDamagePoints() + 1);

            // Update code holder (if necessary)
            if (this.codeHolder == fellowshipMember)
            {
                this.codeHolder = caveCreature;
                this.codeExchanges++;
                typeText(caveCreature.getName() + " has stolen the secret code!");
            }
        }

        // Check if anyone died :(
        if (fellowshipMember.getDamagePoints() >= MAX_DAMAGE_POINTS)
        {
            typeText(fellowshipMember.getName() + " has died from their wounds!");
            this.deadCreatures.add(fellowshipMember);

            // Check if the Fellowship is all dead (this will end the game)
            if (this.getActiveFellowshipMembers().isEmpty())
            {
                typeText("All members of the fellowship have perished! The quest has failed.");
                this.gameActive = false;
            }
        }

        if (caveCreature.getDamagePoints() >= MAX_DAMAGE_POINTS)
        {
            typeText(caveCreature.getName() + " has died from their wounds!");
            this.deadCreatures.add(caveCreature);
        }

        typeText("--- FIGHT ENDS ---");
    }

    /**
    * Loads the labyrinth from the labyrinth.txt file.
    */
    private void loadLabyrinth()
    {
        try 
        {
            this.labyrinth = GameFileHandler.loadLabyrinth();
            
            if (this.labyrinth.isEmpty())
            {
                typeText("\nError: No valid caves were loaded from labyrinth.txt.");
                typeText("The game cannot continue without a labyrinth. Exiting...");
                System.exit(1);
            }

            typeText("\nLabyrinth loaded successfully with " + 
                    this.labyrinth.size() + " caves.");
        }
        catch (FileNotFoundException e)
        {
            typeText("Error: Could not find the labyrinth file: labyrinth.txt");
            typeText("Please make sure the file exists in the current directory.");
            typeText("The game cannot continue without a labyrinth. Exiting...");
            System.exit(1);
        }
        catch (IOException e)
        {
            typeText("Error loading labyrinth: " + e.getMessage());
            typeText("The game cannot continue without a labyrinth. Exiting...");
            System.exit(1);
        }
    }

    /**
    * Sets the code holder creature.
    *
    * @param creature The creature who now holds the code
    */
    public void setCodeHolder(Creature creature)
    {
        if (creature != null)
        {
            this.codeHolder = creature;
        }
    }

    /**
    * Sets the fellowship wins counter.
    *
    * @param wins The number of fellowship wins
    */
    public void setFellowshipWins(int wins)
    {
        if (wins >= 0)
        {
            this.fellowshipWins = wins;
        }
    }

    /**
    * Sets the total fights counter.
    *
    * @param fights The total number of fights
    */
    public void setTotalFights(int fights)
    {
        if (fights >= 0)
        {
            this.totalFights = fights;
        }
    }

    /**
    * Starts the quest through the labyrinth.
    */
    private void startQuest()
    {
        Cave currentCave = this.labyrinth.get(0); //start at the first cave
        this.addVisitedCave(currentCave);

        typeText("\nThe quest begins! The Fellowship enters the first cave.");

        while (this.gameActive)
        {
            typeText("\n--- CAVE " + currentCave.getId() + " ---");
            
            // Display available passages from current cave
            ArrayList<String> availableDirections = new ArrayList<>();
            if (currentCave.getNorth() != 0) availableDirections.add("North");
            if (currentCave.getEast() != 0) availableDirections.add("East"); 
            if (currentCave.getSouth() != 0) availableDirections.add("South");
            if (currentCave.getWest() != 0) availableDirections.add("West");
            
            typeText("You are in cave " + currentCave.getId() + ". Passages lead " + 
                    String.join(" and ", availableDirections) + ".");
            
            // Check and display if this cave has a direct path to Mount Api
            if (currentCave.hasExitToMountApi()) 
            {
                typeText("There is a passage leading directly to Mount Api from this cave!");
            }
            // If not, check if nearby caves lead to Mount Api
            else 
            {
                boolean nearbyPathToMountApi = false;
                ArrayList<String> directionsToMountApi = new ArrayList<>();
                
                // Check each nearby cave for a path to Mount Api
                for (Cave cave : this.labyrinth) 
                {
                    if (cave.hasExitToMountApi()) 
                    {
                        // Check if this cave is adjacent to the current cave
                        if (currentCave.getNorth() == cave.getId()) 
                        {
                            directionsToMountApi.add("North to Cave " + cave.getId());
                            nearbyPathToMountApi = true;
                        } 
                        else if (currentCave.getEast() == cave.getId()) 
                        {
                            directionsToMountApi.add("East to Cave " + cave.getId());
                            nearbyPathToMountApi = true;
                        } 
                        else if (currentCave.getSouth() == cave.getId()) 
                        {
                            directionsToMountApi.add("South to Cave " + cave.getId());
                            nearbyPathToMountApi = true;
                        } 
                        else if (currentCave.getWest() == cave.getId()) 
                        {
                            directionsToMountApi.add("West to Cave " + cave.getId());
                            nearbyPathToMountApi = true;
                        }
                    }
                }
                
                if (nearbyPathToMountApi) 
                {
                    typeText("From here, you can reach a cave with a direct path to Mount Api: " + 
                             String.join(" or ", directionsToMountApi) + ".");
                }
            }

            //check is there is a creature in the cave
            if (currentCave.getCreature() == null)
            {
                //75% chance of generating a creature
                if (this.random.nextInt(100) < 75)
                {
                    // Generate a random evil creature using the EVIL_CREATURE_TYPES array
                    int creatureTypeIndex = this.random.nextInt(EVIL_CREATURE_TYPES.length);
                    String creatureType = EVIL_CREATURE_TYPES[creatureTypeIndex];
                    Creature caveCreature;

                    // Create the appropriate creature based on the type
                    switch (creatureType)
                    {
                        case "Orc":
                            caveCreature = new Orc("Orc " + this.random.nextInt(100));
                            break;
                        case "Troll":
                            caveCreature = new Troll("Troll " + this.random.nextInt(100));
                            break;
                        case "Goblin":
                        default:
                            caveCreature = new Goblin("Goblin " + this.random.nextInt(100));
                            break;
                    }

                    currentCave.setCreature(caveCreature);
                    typeText("The Fellowship encounters " + caveCreature.getName() + 
                            ", a " + caveCreature.getClass().getSimpleName() + "!");
                }
            }

            //handle cave logic
            if (currentCave.getCreature() == null || 
                    this.deadCreatures.contains(currentCave.getCreature()))
            {
                typeText("The cave is empty or the creature is already dead.");

                // Fellowship recovers
                for (Creature member : this.getActiveFellowshipMembers())
                {
                    if (member.getDamagePoints() > 0)
                    {
                        member.setDamagePoints(member.getDamagePoints() - 1);
                    }
                }
                typeText("The Fellowship members recover 1 damage point each.");
            }
            else
            {
                Creature caveCreature = currentCave.getCreature();

                typeText("The Fellowship must fight " + caveCreature.getName() + 
                        ", a " + caveCreature.getClass().getSimpleName() + "!");
                    
                //choose fellowship member to fight
                ArrayList<Creature> activeMembers = this.getActiveFellowshipMembers();

                typeText("\nChoose a Fellowship member to fight:");
                for (int i = 0; i < activeMembers.size(); i++)
                {
                    Creature member = activeMembers.get(i);
                    String specialWeaponInfo = "";

                    if (member instanceof GoodCreature)
                    {
                        GoodCreature goodMember = (GoodCreature)member;
                        if (goodMember.hasSpecialWeapon())
                        {
                            specialWeaponInfo = goodMember.hasUsedSpecialWeapon() ?
                                ", Special weapon: used" : ", Special weapon: available";
                        }
                    }

                    typeText((i + 1) + ". " + member.getName() + 
                            " (" + member.getClass().getSimpleName() + 
                            ", Power: " + member.getPower() + 
                            ", Damage: " + member.getDamagePoints() + 
                            specialWeaponInfo + ")");
                }

                int choice = -1;
                while (choice < 0 || choice >= activeMembers.size())
                {
                    try
                    {
                        System.out.print("Enter your choice (1-" + activeMembers.size() + "): ");
                        String input = this.scanner.nextLine();
                        if (checkForExit(input)) return; //to exit the game.

                        choice = Integer.parseInt(input) - 1;

                        if (choice < 0 || choice >= activeMembers.size())
                        {
                            System.out.println("Invalid choice. Please try again.");
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Please enter a valid number.");
                    }
                }

                Creature selectedMember = activeMembers.get(choice);
                this.handleFight(selectedMember, caveCreature);
            }

            //check if the game is still active
            if (!this.gameActive)
            {
                break;
            }

            // Check if all evil creatures are dead for safe navigation
            boolean allEvilCreaturesDead = true;
            for (Cave cave : this.visitedCaves)
            {
                if (cave.getCreature() != null && 
                        cave.getCreature() instanceof EvilCreature && 
                        !this.deadCreatures.contains(cave.getCreature()))
                {
                    allEvilCreaturesDead = false;
                    break;
                }
            }

            if (allEvilCreaturesDead)
            {
                typeText("\nAll evil creatures have been defeated! The Fellowship can now navigate the labyrinth safely.");
            }

            //choose next cave
            Cave nextCave = this.chooseNextCave(currentCave);

            //check if the next cave is Mount Api
            if (nextCave.getId() == MOUNT_API_ID)
            {
                //check if the Fellowship has the code
                boolean fellowshipHasCode = false;
                for (Creature member : this.getActiveFellowshipMembers())
                {
                    if (member == this.codeHolder)
                    {
                        fellowshipHasCode = true;
                        break;
                    }
                }

                if (fellowshipHasCode)
                {
                    typeText("\nThe Fellowship has reached Mount Api with the secret code!");
                    typeText("The code is delivered to the Java wizard by " + 
                            this.codeHolder.getName() + ".");
                    this.gameActive = false;
                    this.addVisitedCave(nextCave); //add Mount Api to visited caves
                    break;
                }
                else
                {
                    typeText("\nThe Fellowship has reached Mount Api, but doesn't have the code!");
                    typeText("\nHobbit: All right, then. Keep your secrets.");
                    typeText("\nThe Fellowship watches helplessly.");
                    typeText("The fellowship must go back and recover the code.");
                    nextCave = this.chooseNextCave(currentCave, true); //force different choice.
                }
            }

            // Display status and move to the next cave
            this.displayGameStatus(currentCave, nextCave);
            currentCave = nextCave;
            this.addVisitedCave(currentCave);
        }
    }

    /**
    * Chooses the next cave to enter.
    *
    * @param currentCave The current cave
    * @return The next cave to enter
    */
    private Cave chooseNextCave(Cave currentCave)
    {
        return this.chooseNextCave(currentCave, false);
    }

    /**
    * Chooses the next cave to enter with an option to force a different choice.
    *
    * @param currentCave The current cave
    * @param forceNewChoice Whether to force a different choice
    * @return The next cave to enter
    */
    private Cave chooseNextCave(Cave currentCave, boolean forceNewChoice)
    {
        ArrayList<Integer> options = new ArrayList<>();
        ArrayList<String> directions = new ArrayList<>();

        // Get available cave IDs and corresponding directions using the DIRECTIONS array
        int[] caveIDs = {currentCave.getNorth(), currentCave.getEast(), 
                        currentCave.getSouth(), currentCave.getWest()};
        
        // Loop through directions using array indices
        for (int i = 0; i < DIRECTIONS.length; i++)
        {
            if (caveIDs[i] != 0)
            {
                options.add(caveIDs[i]);
                directions.add(DIRECTIONS[i]);
            }
        }

        // If only one option, return that cave
        if (options.size() == 1)
        {
            typeText("\nThere is only one way forward (" + directions.get(0) + ").");

            // Find the cave with the matching ID
            for (Cave cave : this.labyrinth)
            {
                if (cave.getId() == options.get(0))
                {
                    return cave;
                }
            }
        }

        // Otherwise, let the player choose
        typeText("\nYou are in cave " + currentCave.getId() + ". Passages lead " + 
                String.join(" and ", directions) + ".");
         
        // Check and inform if any options lead directly to Mount Api
        boolean directPathToMountApi = false;
        for (int i = 0; i < options.size(); i++) 
        {
            if (options.get(i) == MOUNT_API_ID) 
            {
                typeText("The " + directions.get(i) + " passage leads directly to Mount Api!");
                directPathToMountApi = true;
                break;
            }
        }
         
        // If no direct path to Mount Api, check if any adjacent caves have a path to Mount Api
        if (!directPathToMountApi && !forceNewChoice) 
        {
            ArrayList<String> potentialPaths = new ArrayList<>();
             
            for (int i = 0; i < options.size(); i++) 
            {
                if (options.get(i) != MOUNT_API_ID) 
                {
                    // Find the cave object for this option
                    for (Cave cave : this.labyrinth) 
                    {
                        if (cave.getId() == options.get(i) && cave.hasExitToMountApi()) 
                        {
                            potentialPaths.add("The " + directions.get(i) + " passage leads to Cave " + 
                                    options.get(i) + ", which has a direct path to Mount Api");
                            break;
                        }
                    }
                }
            }
             
            if (!potentialPaths.isEmpty()) 
            {
                for (String path : potentialPaths) 
                {
                    typeText(path);
                }
            }
        }
         
        typeText("\nChoose a direction to move:");
        for (int i = 0; i < options.size(); i++)
        {
            String destination = options.get(i) == MOUNT_API_ID ? "Mount Api" : "Cave " + options.get(i);
            typeText((i + 1) + ". " + directions.get(i) + " to " + destination);
        }

        int previousChoice = -1;
        if (forceNewChoice)
        {
            // Remember the previous choice (Mount Api) to avoid it
            previousChoice = 0;
            for (int i = 0; i < options.size(); i++)
            {
                if (options.get(i) == MOUNT_API_ID)
                {
                    previousChoice = i;
                    break;
                }
            }
        }

        int choice = -1;
        while (choice < 0 || choice >= options.size() || choice == previousChoice)
        {
            try
            {
                System.out.print("Enter your choice (1-" + options.size() + "): ");
                String input = this.scanner.nextLine();
                if (checkForExit(input)) return currentCave; //return current cave as this won't be used

                choice = Integer.parseInt(input) - 1;

                if (choice < 0 || choice >= options.size())
                {
                    System.out.println("Invalid choice. Please try again.");
                    choice = -1; // Reset choice to say in the loop
                    continue;
                }
                else if (choice == previousChoice)
                {
                    typeText("You cannot go to Mount Api without the code. Choose another direction.");
                    choice = -1; // Reset choice to stay in the loop
                    continue;
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter a valid number.");
                choice = -1; // Reset choice to stay in the loop
            }
        }

        int nextCaveId = options.get(choice);

        //find the cave with the matching ID
        for (Cave cave : this.labyrinth)
        {
            if (cave.getId() == nextCaveId)
            {
                return cave;
            }
        }

        //should never reach here
        return currentCave;
    }

    /**
    * Writes the game summary to the fellowship.txt file.
    */
    private void writeSummaryToFile()
    {
        boolean success = GameFileHandler.writeSummaryToFile(
            this.codeHolder,
            this.visitedCaves,
            this.codeExchanges,
            this.deadCreatures,
            this.fellowship,
            this.totalFights,
            this.fellowshipWins
        );
        
        if (success)
        {
            typeText("\nSummary has been written to fellowship.txt");
        }
        else
        {
            typeText("The summary could not be saved, but the game has completed successfully.");
        }
    }
}