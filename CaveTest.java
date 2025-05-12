/**
* CaveTest is a test class for the Cave class.
*
* NOTE: This test class uses assertions. When running from the command line,
* use the -ea flag to enable assertions:
*       java -ea CaveTest
*
* @author Nicholas Battle
* @version 1.0
*/
public class CaveTest
{
    /**
    * Default constructor for CaveTest.
    */
    public CaveTest()
    {

    }

    /**
    * Main method to run tests for the Cave class.
    *
    * @param args Command line arguments
    */
    public static void main(String[] args)
    {
        // Check if assertions are enabled
        boolean assertionsEnabled = false;
        try
        {
            assert false;
        }
        catch (AssertionError e)
        {
            assertionsEnabled = true;
        }

        if (!assertionsEnabled)
        {
            System.out.println("WARNING: Assertions are disabled. Tests will run but not verify results");
            System.out.println("Please enable assertions with the -ea flag (java -ea CaveTest)");
            System.out.println();
        }

        System.out.println("=== Cave Class Test Suite ===");

        // Test constructor and getters
        testConstructorAndGetters();

        // Test setters
        testSetters();

        // Test creature operations
        testCreatureOperations();

        //Test hasExitToMountApi
        testHasExitToMountApi();

        // Test toString
        testToString();

        System.out.println("\nAll tests completed.");
    }

    /**
    * Tests the constructor and getter methods of the Cave class.
    */
    private static void testConstructorAndGetters()
    {
        System.out.println("\n--- Testing Constructor and Getters ---");

        // Test default constructor
        Cave cave1 = new Cave();
        System.out.println("Default constructor - Cave ID: " + cave1.getId());
        assert cave1.getId() == 0 : "Default ID should be 0";
        assert cave1.getNorth() == 0 : "Default north should be 0";
        assert cave1.getEast() == 0 : "Default east should be 0";
        assert cave1.getSouth() == 0 : "Default south should be 0";
        assert cave1.getWest() == 0 : "Default west should be 0";
        assert cave1.getCreature() == null : "Default creature should be null";

        // Test non-default constructor
        Cave cave2 = new Cave(5, 10, 15, 0, 100); //cave ID, ID of the caves to the north, east, south, west respectively. 100 represents Mount Api
        System.out.println("Non-default constructor - Cave ID: " + cave2.getId());
        assert cave2.getId() == 5 : "ID should be 5";
        assert cave2.getNorth() == 10 : "North should be 10";
        assert cave2.getEast() == 15 : "East should be 15";
        assert cave2.getSouth() == 0 : "South should be 0";
        assert cave2.getWest() == 100 : "West should be 100";
        assert cave2.getCreature() == null : "Creature should be null";

        System.out.println("Constructor and getter tests passed.");
    }

    /**
    * Tests the creature operations of the cave class.
    */
    private static void testCreatureOperations()
    {
        System.out.println("\n--- Testing Creature Operations ---");

        Cave cave = new Cave(1, 2, 3, 4, 5); //cave ID, ID of the caves to the north, east, south, west respectively.
        assert cave.getCreature() == null : "Initial creature should be null";

        // Add creature
        Orc orc = new Orc("TestOrc");
        cave.setCreature(orc);
        System.out.println("Set creature: " + cave.getCreature().getName());
        assert cave.getCreature() == orc : "Creature should be the orc";

        // Change creature
        Troll troll = new Troll("TestTroll");
        cave.setCreature(troll);
        System.out.println("Changed creature: " + cave.getCreature().getName());
        assert cave.getCreature() == troll : "Creature should be changed to troll";

        // Remove creature
        cave.setCreature(null);
        System.out.println("Removed creature");
        assert cave.getCreature() == null : "Creature should be null after removal";

        System.out.println("Creature operations tests passed.");
    }

    /**
    * Tests the hasExitToMountApi method of the cave class
    */
    private static void testHasExitToMountApi()
    {
        System.out.println("\n--- Testing hasExitToMountApi ---");

        // Test cave with no exit to Mount Api
        Cave cave1 = new Cave(1, 2, 3, 4, 5);
        System.out.println("Cave with no exit to Mount Api: " + cave1.hasExitToMountApi());
        assert !cave1.hasExitToMountApi() : "Cave should not have exit to Mount Api";

        // Test cave with north exit to Mount Api
        Cave cave2 = new Cave(2, 100, 3, 4, 5);
        System.out.println("Cave with north exit to Mount Api: " + cave2.hasExitToMountApi());
        assert cave2.hasExitToMountApi() : "Cave should have exit to Mount Api (north)";

        // Test cave with east exit to Mount Api
        Cave cave3 = new Cave(3, 1, 100, 4, 5);
        System.out.println("Cave with east exit to Mount Api: " + cave3.hasExitToMountApi());
        assert cave3.hasExitToMountApi() : "Cave should have exit to Mount Api (east)";

        // Test cave with south south to Mount Api
        Cave cave4 = new Cave(4, 1, 2, 100, 5);
        System.out.println("Cave with south exit to Mount Api: " + cave4.hasExitToMountApi());
        assert cave4.hasExitToMountApi() : "Cave should have exit to Mount Api (south)";

        // Test cave with west exit to Mount Api
        Cave cave5 = new Cave(5, 1, 2, 3, 100);
        System.out.println("Cave with west exit to Mount Api: " + cave5.hasExitToMountApi());
        assert cave5.hasExitToMountApi() : "Cave should have exit to Mount Api (west)";

        System.out.println("hasExitToMountApi tests passed.");
    }

    /**
    * Tests the setter methods of the Cave class
    */
    private static void testSetters()
    {
        System.out.println("\n--- Testing Setters ---");

        Cave cave = new Cave();

        // Test setId
        cave.setId(10);
        System.out.println("Set ID: " + cave.getId());
        assert cave.getId() == 10 : "ID should be 10 after setter";

        // Test invalid setId with negative value
        cave.setId(-5);
        System.out.println("After setting negative ID: " + cave.getId());
        assert cave.getId() == 10 : "ID should still be 10 after invalid setter";

        // Test setNorth
        cave.setNorth(20);
        System.out.println("Set north: " + cave.getNorth());
        assert cave.getNorth() == 20 : "North should be 20 after setter";

        // Test setEast
        cave.setEast(30);
        System.out.println("Set east: " + cave.getEast());
        assert cave.getEast() == 30 : "East should be 30 after setter";

        // Test setSouth
        cave.setSouth(40);
        System.out.println("Set south: " + cave.getSouth());
        assert cave.getSouth() == 40 : "South should be 40 after setter";

        // Test setWest
        cave.setWest(50);
        System.out.println("Set west: " + cave.getWest());
        assert cave.getWest() == 50 : "West should be 50 after setter";

        // Test invalid direction values
        cave.setNorth(-10);
        cave.setEast(-20);
        cave.setSouth(-30);
        cave.setWest(-40);

        System.out.println("After setting negative directions - North: " + cave.getNorth() + 
                ", East: " + cave.getEast() + ", South: " + cave.getSouth() + 
                ", West: " + cave.getWest());

        assert cave.getNorth() == 20 : "North should still be 20 after invalid setter";
        assert cave.getEast() == 30 : "East should still be 30 after invalid setter";
        assert cave.getSouth() == 40 : "South should still be 40 after invalid setter";
        assert cave.getWest() == 50 : "West should still be 50 after invalid setter";

        System.out.println("Setter tests passed.");
    }

    /**
    * Tests the toString method of the cave class.
    */
    private static void testToString()
    {
        System.out.println("\n--- Testing toString ---");

        // Test empty cave
        Cave cave1 = new Cave(1, 0, 0, 0, 0);
        String str1 = cave1.toString();
        System.out.println("Empty cave toString: " + str1);
        assert str1.contains("Cave 1") : "toString should contain cave ID";

        // Test cave with connections
        Cave cave2 = new Cave(2, 3, 0, 5, 100);
        String str2 = cave2.toString();
        System.out.println("Connected cave toString: " + str2);
        assert str2.contains("Cave 2") : "toString should contain cave ID";
        assert str2.contains("North → Cave 3") : "toString should contain north connection";
        assert str2.contains("South → Cave 5") : "toString should contain south connection";
        assert str2.contains("West → Mount Api") : "toString should contain west connection to Mount Api";

        // Test cave with creature
        Cave cave3 = new Cave (3, 0, 4, 0, 2);
        Goblin goblin = new Goblin("TestGoblin");
        cave3.setCreature(goblin);
        String str3 = cave3.toString();
        System.out.println("Cave with creature toString: " + str3);
        assert str3.contains("Cave 3") : "toString should contain cave ID";
        assert str3.contains("East → Cave 4") : "toString should contain east connection";
        assert str3.contains("West → Cave 2") : "toString should contain west connection";
        assert str3.contains("TestGoblin") : "toString should contain creature name";
        assert str3.contains("Goblin") : "toString should contain creature type";

        System.out.println("toString tests passed.");
    }
}