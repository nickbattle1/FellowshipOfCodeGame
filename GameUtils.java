/**
 * GameUtils provides utility functions for the Fellowship of Code game.
 * 
 * @version 1.0
 */
public class GameUtils
{
    private static final int DEFAULT_TYPING_SPEED = 30; // milliseconds between characters
    
    /**
     * Types text character by character with a delay for a typing effect
     * to improve user experience and readability.
     *
     * @param text The text to display with typing effect
     * @param typingSpeed The delay in milliseconds between characters
     */
    public static void typeText(String text, int typingSpeed)
    {
        for (char c : text.toCharArray())
        {
            System.out.print(c);
            try
            {
                Thread.sleep(typingSpeed);
            }
            catch (InterruptedException e)
            {
                // Continue if interrupted
            }
        }
        System.out.println();
    }
    
    /**
     * Types text with default typing speed.
     *
     * @param text The text to display
     */
    public static void typeText(String text)
    {
        typeText(text, DEFAULT_TYPING_SPEED);
    }
    
    /**
     * Validates creature name to ensure it's not empty or just numeric.
     * 
     * @param name The name to validate
     * @return true if name is valid, false otherwise
     */
    public static boolean isValidCreatureName(String name)
    {
        if (name == null || name.trim().isEmpty())
        {
            return false;
        }
        
        // Check if the name is purely numeric
        try
        {
            Double.parseDouble(name);
            return false; // Name is purely numeric
        }
        catch (NumberFormatException e)
        {
            return true; // Name contains at least some non-numeric characters
        }
    }
    
    /**
     * Validates numeric input to ensure it's within range.
     * 
     * @param input The input string
     * @param min The minimum valid value (inclusive)
     * @param max The maximum valid value (inclusive)
     * @return The parsed integer if valid, or -1 if invalid
     */
    public static int validateNumericInput(String input, int min, int max)
    {
        try
        {
            int value = Integer.parseInt(input);
            if (value >= min && value <= max)
            {
                return value;
            }
        }
        catch (NumberFormatException e)
        {
            // Return -1 for invalid input
        }
        return -1;
    }
} 