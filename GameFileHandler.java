/**
 * GameFileHandler handles file I/O operations for the Fellowship of Code game.
 * 
 * @author Nicholas Battle
 * @version 1.0
 */
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameFileHandler
{
    /**
     * Loads the labyrinth from the labyrinth.txt file.
     * 
     * @return ArrayList of Cave objects representing the labyrinth
     * @throws FileNotFoundException if the labyrinth file is not found
     * @throws IOException if there is an error reading the file
     */
    public static ArrayList<Cave> loadLabyrinth() throws FileNotFoundException, IOException
    {
        ArrayList<Cave> labyrinth = new ArrayList<>();
        String filename = "labyrinth.txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename)))
        {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null)
            {
                lineNumber++;
                try 
                {
                    String[] parts = line.split(",");
                    if (parts.length != 5)
                    {
                        GameUtils.typeText("Warning: Line " + lineNumber + " in " + filename + 
                                " has invalid format. Expected 5 comma-separated values, but found " + 
                                parts.length + ".");
                        continue;
                    }
                    
                    int id = Integer.parseInt(parts[0]);
                    int north = Integer.parseInt(parts[1]);
                    int east = Integer.parseInt(parts[2]);
                    int south = Integer.parseInt(parts[3]);
                    int west = Integer.parseInt(parts[4]);
                    
                    labyrinth.add(new Cave(id, north, east, south, west));
                }
                catch (NumberFormatException e)
                {
                    GameUtils.typeText("Warning: Line " + lineNumber + " in " + filename + 
                            " contains invalid numeric values: " + e.getMessage());
                }
            }
        }
        
        return labyrinth;
    }
    
    /**
     * Writes the game summary to the fellowship.txt file.
     * 
     * @param codeHolder The creature holding the code at the end
     * @param visitedCaves List of caves visited during the game
     * @param codeExchanges Number of times the code changed hands
     * @param deadCreatures List of creatures that died during the game
     * @param fellowship List of fellowship members
     * @param totalFights Total number of fights that occurred
     * @param fellowshipWins Number of fights won by the fellowship
     * @return true if the summary was written successfully, false otherwise
     */
    public static boolean writeSummaryToFile(Creature codeHolder, 
                                           ArrayList<Cave> visitedCaves,
                                           int codeExchanges,
                                           ArrayList<Creature> deadCreatures,
                                           ArrayList<Creature> fellowship,
                                           int totalFights,
                                           int fellowshipWins)
    {
        String filename = "fellowship.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
            writer.write("=== FELLOWSHIP OF CODE QUEST SUMMARY ===\n\n");

            // Write outcome
            boolean success = false;
            for (Creature member : fellowship)
            {
                if(!deadCreatures.contains(member) && member == codeHolder)
                {
                    success = true;
                    break;
                }
            }

            if (success)
            {
                writer.write("OUTCOME: SUCCESS! The code was delivered to the Java wizard on Mount Api.\n");
                writer.write("The code was delivered by: " + codeHolder.getName() + 
                        " (" + codeHolder.getClass().getSimpleName() + ")\n");
            }
            else
            {
                writer.write("OUTCOME: FAILURE! The Fellowship failed to deliver the code.\n");
                writer.write("The code is held by: " + codeHolder.getName() + 
                        " (" + codeHolder.getClass().getSimpleName() + ")\n");
            }

            // Write stats
            writer.write("\nNumber of caves visited: " + visitedCaves.size() + "\n");
            writer.write("Number of times the secret code changed hands: " + codeExchanges + "\n");

            // Write dead creatures
            writer.write("\nCreatures that died during the quest:\n");
            if (deadCreatures.isEmpty())
            {
                writer.write("None\n");
            }
            else
            {
                for (Creature creature : deadCreatures)
                {
                    writer.write("- " + creature.getName() + " (" + 
                            creature.getClass().getSimpleName() + ")\n");
                }
            }

            // Write fight success rate
            double successRate = (totalFights == 0) ? 0.0 : (double) fellowshipWins * 100 / totalFights;
            writer.write("\nFellowship fight success rate: " + 
                    String.format("%.2f", successRate) + "%\n");
            
            return true;
        }
        catch (IOException e)
        {
            GameUtils.typeText("Error writing summary to file: " + e.getMessage());
            return false;
        }
    }
} 