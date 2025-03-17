package motorph;

import motorph.classes.process.InventoryProcess;
import java.util.Scanner;

public class MotorPh {

    public static void main(String[] args) {
        // THE METHOD THAT STARTS THE SYSTEM
        RunSystem();
    }

    // INITIALIZE SCANNER TO TAKE USER INPUTS
    private static final Scanner scanner = new Scanner(System.in);
    // INITIALIZES INVENTORYPROCESS CLASS THAT HOLDS ALL THE LOGIC OF THE SYSTEM
    private static final InventoryProcess inventoryProcess = new InventoryProcess();

    // PRIVATE METHOD SO THAT IT IS NOT ACCESSIBLE OUTSIDE THIS CLASS
    private static void RunSystem() {
        while (true) {
            // DISPLAYS THE INVENTORY IN A TABULAR FORMAT
            DisplayInventory();
            // DISPLAYS THE MENU OF ACTIONS
            DisplayMenu();
            /**
             * TAKES USER INPUT AND VALIDATES IT. AFTER VALIDATION THE VALUE IS
             * STORED IN A VARIABLE
             */
            int choice = GetIntInput();
            // CONSUME NEWLINE
            scanner.nextLine();

            // SWITCH CASE TO CALL THE METHOD APPROPRIATE BASED ON THE USER INPUT
            switch (choice) {
                case 1 ->
                    inventoryProcess.AddItem();
                case 2 ->
                    inventoryProcess.DeleteItem();
                case 3 ->
                    inventoryProcess.SortInventory();
                case 4 ->
                    inventoryProcess.SearchItem();
                case 5 -> {
                    ExitProgram();
                    return;
                }

                default -> {
                }
            }
        }
    }

    // METHOD TO DISPLAY THE WHOLE INVENTORY IN TABULAR FORMAT
    private static void DisplayInventory() {
        // CALLS THE PRINT METHOD INSIDE THE INVENTORYPROCESS CLASS
        inventoryProcess.DisplayInventory();
    }

    // DISPLAYS THE MENU FROM WHICH THE USER WILL PICK AN ACTION TO DO
    private static void DisplayMenu() {
        System.out.println("\n===== Inventory Menu =====");
        System.out.println("1. Add Item");
        System.out.println("2. Delete Item");
        System.out.println("3. Sort Inventory");
        System.out.println("4. Search Item");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
    }

    // METHOD THAT VALIDATES THE USER INPUT
    private static int GetIntInput() {
        // WHILE LOOP WITH AN ARGUEMENT [.hasNextInt()] CHECKS IF THE USER INPUT IS A NUMBER
        while (!scanner.hasNextInt()) {
            // IF USER INPUT IS NOT A NUMBER, PRINT ERROR MESSAGE
            System.out.println("Invalid input. Enter a number.");
            scanner.next(); // CLEAR THE INVALID INPUT
            System.out.print("Enter choice: ");
        }
        // IF THE USER INPUT IS A NUMBER, RETURN THE VALUE BACK TO WHERE THE METHOD IS CALLED FROM/ 
        return scanner.nextInt();
    }

    // METHOD TO CLOSE THE PROGRAM
    private static void ExitProgram() {
        System.out.println("Exiting...");
        scanner.close();
    }
}
