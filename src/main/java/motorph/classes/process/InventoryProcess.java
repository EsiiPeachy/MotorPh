/**
 * VERSION CONTROL : 1.1
 */
package motorph.classes.process;

import java.time.LocalDate;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import motorph.models.objects.*;

public class InventoryProcess {

    /**
     * LINKEDLIST AS THE DATA STRUCTURE TO HOLD THE INVENTORY ITEMS UPGRADED TO
     * HASH MAP TO CATER HASH ALGORITHMS
     */
    private final Map<String, Motorcycle> inventory;
    private static final Scanner scanner = new Scanner(System.in); // INITIALIZE SCANNER TO TAKE USER INPUTS
    // AN ARRAY TO HOLD ALL THE AVAILABLE BRANDS
    private final String[] brands = {"honda", "kawasaki", "kymco", "suzuki", "yamaha"};

    public InventoryProcess() {
        // INITIALIZE HASH MAP
        this.inventory = new LinkedHashMap<>();
        // METHOD TO ADD SAMPLE DATA
        this.AddSampleData();
    }

    // METHOD TO DISPLAY THE WHOLE INVENTORY IN TABULAR FORMAT MAKING IT REUSABLE
    public void DisplayInventory() {
        // DIRECTLY USES THE MAIN INVENTORY HASHMAP
        PrintMotorcycleTable(inventory);
    }

    // METHOD TO ADD A NEW MOTORCYCLE ITEM TO THE INVENTORY
    public void AddItem() {
        String brand = SelectBrand(); // SELECT A VALID BRAND
        // VALIDATION CHECK: IF [brand] IS NULL, STOP THE METHOD FROM PROCEEDING
        if (brand == null) {
            return;
        }

        // IF [brand] IS NOT NULL, ASK USER TO INPUT ENGINE NUMBER
        System.out.print("Enter Engine Number: ");
        // STORE USER INPUT INTO VARIABLE
        String engineNo = scanner.nextLine();

        // HASH THE ENGINE NUMBER
        String hashedEngineNo = HashEngineNumber(engineNo);
        // VALIDATION CHECK: IF ENGINE NUMBER ALREADY EXISTS IN THE INVENTORY
        if (inventory.containsKey(hashedEngineNo)) {
            System.out.println("Error: Engine number already exists in the inventory.");
            return;
        }

        // IF [brand] IS NOT NULL, ASK USER TO QUANTITY
        System.out.print("Enter Quantity: ");
        // STORE USER INPUT INTO VARIABLE
        int quantity = scanner.nextInt();

        // CALL METHOD TO CREATE MOTORCYCLE OBJECT
        Motorcycle motorcycle = CreateMotorcycle(brand, engineNo, quantity);
        // ADD THE CREATED MOTORCYCLE OBJECT TO THE INVENTORY MAP
        inventory.put(hashedEngineNo, motorcycle);

        System.out.println(brand.substring(0, 1).toUpperCase() + brand.substring(1)
                + " item with engine number " + engineNo + " has been added.");
    }

    // DELETES A MOTORCYCLE FROM THE INVENTORY BY ENGINER NUMBER
    public void DeleteItem() {
        // PROMPTS USER TO INPUT THE ENGINE NUMBER OF THE MOTORCYCLE TO BE DELETED
        System.out.print("Enter Engine Number to delete: ");
        // PASS THE USER INPUT TO THE HASH ENGINE NUMBER METHOD TO HASH IT AND THEN STORE IT IN A VARIABLE 
        String engineNo = scanner.nextLine();
        String hashedEngineNo = HashEngineNumber(engineNo);

        // TRIES TO DELETE THE SUPPLIED ENGINE NUMBER OF THE MOTORCYCLE ITEM FROM THE INVENTORY USING THE HASHED ENGINE NUMBER AS THE KEY
        Motorcycle removedItem = inventory.remove(hashedEngineNo);

        // VALIDATION CHECK: IF THE ITEM WAS REMOVED
        if (removedItem != null) {
            // IF [removedItem] IS NOT NULL, DISPLAY CONFIRMATION MESSAGE
            System.out.println("Motorcycle with engine number " + engineNo + " has been deleted.");
        } else {
            // IF [removedItem] IS NULL, DISPLAY ERROR MESSAGE
            System.out.println("No matching motorcycle found.");
        }
    }

    public void SortInventory() {
        while (true) {
            System.out.println("\n===== Select Sort Method =====");
            System.out.println("1. Bubble Sort");
            System.out.println("2. Insertion Sort");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");

            int choice = GetValidChoice();
            switch (choice) {
                case 1 ->
                    BubbleSort();
                case 2 ->
                    InsertionSort();
                case 3 -> {
                    System.out.println("Returning to the main menu...");
                    return;
                }
                default ->
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public void BubbleSort() {
        int choice = SelectSortOrder();
        switch (choice) {
            case 1 ->
                AscendingBubbleSort();
            case 2 ->
                DescendingBubbleSort();
            case 3 -> {
                System.out.println("Returning to the main menu...");
                return;
            }
            default -> {
            }
        }
        DisplayInventory();
    }

    public void InsertionSort() {
        int choice = SelectSortOrder();
        switch (choice) {
            case 1 ->
                AscendingInsertionSort();
            case 2 ->
                DescendingInsertionSort();
            case 3 -> {
                System.out.println("Returning to the main menu...");
                return;
            }
            default -> {
            }
        }
        DisplayInventory();
    }

    /**
     * SORTS THE INVENTORY TO ASCENDING ORDER USING THE BUBBLE SORT ALGORITHM
     * THE ALGORITHM SORTS THE INVENTORY BY ENGINE NUMBER
     */
    public void AscendingBubbleSort() {
        // EXTRACT THE VALUES (MOTORCYCLE OBJECTS) INTO A LIST
        List<Motorcycle> motorcycles = new ArrayList<>(inventory.values());

        // STORES THE NUMBER OF ITEMS IN THE INVENTORY LINKEDLIST TO A VARIABLE
        int n = inventory.size();
        // OUTER LOOP CONTROLS THE PASSES THROUGH THE LIST
        for (int i = 0; i < n - 1; i++) {
            // INNER LOOP CONTROLS THE COMPARISON AND SWAPPING
            for (int j = 0; j < n - i - 1; j++) {
                /**
                 * COMPARES THE ENGINE NUMBERS OF THE CURRENT AND NEXT
                 * MOTORCYCLE IF motorcycles[j].engineNo >
                 * motorcycles[j+1].engineNo, SWAP THEM
                 */
                if (motorcycles.get(j).GetEngineNumber().compareTo(motorcycles.get(j + 1).GetEngineNumber()) > 0) {
                    // SWAP MOTORCYCLES
                    Motorcycle temp = motorcycles.get(j);
                    motorcycles.set(j, motorcycles.get(j + 1));
                    motorcycles.set(j + 1, temp);
                }
            }
        }
        // CLEAR THE ORIGINAL MAP AND REPOPULATE IT WITH THE SORTED LIST
        inventory.clear();
        for (Motorcycle m : motorcycles) {
            String hashedEngineNo = HashEngineNumber(m.GetEngineNumber());
            inventory.put(hashedEngineNo, m);
        }

        // PRINT CONFIRMATION MESSAGE
        System.out.println("Inventory sorted using Bubble Sort by Engine Number (Ascending).");
    }

    /**
     * SORTS THE INVENTORY TO DESCENDING ORDER USING THE BUBBLE SORT ALGORITHM
     * THE ALGORITHM SORTS THE INVENTORY BY ENGINE NUMBER
     */
    public void DescendingBubbleSort() {
        // EXTRACT THE VALUES (MOTORCYCLE OBJECTS) INTO A LIST
        List<Motorcycle> motorcycles = new ArrayList<>(inventory.values());

        // STORES THE NUMBER OF ITEMS IN THE INVENTORY LINKEDLIST TO A VARIABLE
        int n = inventory.size();
        // OUTER LOOP CONTROLS THE PASSES THROUGH THE LIST
        for (int i = 0; i < n - 1; i++) {
            // INNER LOOP CONTROLS THE COMPARISON AND SWAPPING
            for (int j = 0; j < n - i - 1; j++) {
                /**
                 * COMPARES THE ENGINE NUMBERS OF THE CURRENT AND NEXT
                 * MOTORCYCLE IF motorcycles[j].engineNo <
                 * motorcycles[j+1].engineNo, SWAP THEM
                 */
                if (motorcycles.get(j).GetEngineNumber().compareTo(motorcycles.get(j + 1).GetEngineNumber()) < 0) {
                    // SWAP MOTORCYCLES
                    Motorcycle temp = motorcycles.get(j);
                    motorcycles.set(j, motorcycles.get(j + 1));
                    motorcycles.set(j + 1, temp);
                }
            }
        }
        // CLEAR THE ORIGINAL MAP AND REPOPULATE IT WITH THE SORTED LIST
        inventory.clear();
        for (Motorcycle m : motorcycles) {
            String hashedEngineNo = HashEngineNumber(m.GetEngineNumber());
            inventory.put(hashedEngineNo, m);
        }
        // PRINT CONFIRMATION MESSAGE
        System.out.println("Inventory sorted using Bubble Sort by Engine Number (Descending).");
    }

    /**
     * SORTS THE INVENTORY TO ASCENDING ORDER USING THE INSERTION SORT ALGORITHM
     * ALGORITHM SORTS THE INVENTORY BY BRAND
     */
    public void AscendingInsertionSort() {
        // EXTRACT THE VALUES (MOTORCYCLE OBJECTS) INTO A LIST
        List<Motorcycle> motorcycles = new ArrayList<>(inventory.values());

        // STORES THE NUMBER OF ITEMS IN THE INVENTORY
        int n = motorcycles.size();

        // LOOP THROUGH THE WHOLE INVENTORY
        for (int i = 1; i < n; i++) {
            // GETS THE MOTORCYCLE AT INDEX [i] AND STORES IT TO A VARIABLE NAMED [key]
            Motorcycle key = motorcycles.get(i);
            // INITIALIZE A TEMPORARY INTEGER THAT WILL SERVE AS THE PREVIOUS INDEX
            int j = i - 1;

            // WHILE LOOP TO SHIFT THE ELEMENTS UNTIL [key] IS IN THE RIGHT PLACE
            while (j >= 0 && motorcycles.get(j).GetBrand().compareTo(key.GetBrand()) > 0) {
                // SHIFTS THE ITEM AT [j+1] TO THE RIGHT, MAKING ROOM FOR THE CORRECT ELEMENT
                motorcycles.set(j + 1, motorcycles.get(j));
                j--;
            }
            // INSERTS THE KEY TO INDEX [j+1]
            motorcycles.set(j + 1, key);
        }

        // CLEAR THE ORIGINAL MAP AND REPOPULATE IT WITH THE SORTED LIST
        inventory.clear();
        for (Motorcycle m : motorcycles) {
            String hashedEngineNo = HashEngineNumber(m.GetEngineNumber());
            inventory.put(hashedEngineNo, m);
        }

        // PRINT CONFIRMATION MESSAGE
        System.out.println("Inventory sorted using Insertion Sort by Brand (Ascending).");
    }

    /**
     * SORTS THE INVENTORY TO DESCENDING ORDER USING THE INSERTION SORT
     * ALGORITHM SORTS THE INVENTORY BY BRAND
     */
    public void DescendingInsertionSort() {
        // EXTRACT THE VALUES (MOTORCYCLE OBJECTS) INTO A LIST
        List<Motorcycle> motorcycles = new ArrayList<>(inventory.values());

        // STORES THE NUMBER OF ITEMS IN THE INVENTORY
        int n = motorcycles.size();

        // LOOP THROUGH THE WHOLE INVENTORY
        for (int i = 1; i < n; i++) {
            // GETS THE MOTORCYCLE AT INDEX [i] AND STORES IT TO A VARIABLE NAMED [key]
            Motorcycle key = motorcycles.get(i);
            // INITIALIZE A TEMPORARY INTEGER THAT WILL SERVE AS THE PREVIOUS INDEX
            int j = i - 1;

            // WHILE LOOP TO SHIFT THE ELEMENTS UNTIL [key] IS IN THE RIGHT PLACE
            while (j >= 0 && motorcycles.get(j).GetBrand().compareTo(key.GetBrand()) < 0) {
                // SHIFTS THE ITEM AT [j+1] TO THE RIGHT, MAKING ROOM FOR THE CORRECT ELEMENT
                motorcycles.set(j + 1, motorcycles.get(j));
                j--;
            }
            // INSERTS THE KEY TO INDEX [j+1]
            motorcycles.set(j + 1, key);
        }

        // CLEAR THE ORIGINAL MAP AND REPOPULATE IT WITH THE SORTED LIST
        inventory.clear();
        for (Motorcycle m : motorcycles) {
            String hashedEngineNo = HashEngineNumber(m.GetEngineNumber());
            inventory.put(hashedEngineNo, m);
        }

        // PRINT CONFIRMATION MESSAGE
        System.out.println("Inventory sorted using Insertion Sort by Brand (Descending).");
    }

    /**
     * SEARCHES FOR A MOTORCYCLE BASED ON INPUT CRITERIA.
     */
    public void SearchItem() {
        // PROMPT USER TO INPUT SEARCH CRITERIA
        System.out.print("Enter Search Criteria: ");
        // STORE USER INPUT TO A VARIABLE AND CONVERT TO LOWERCASE
        String criteria = scanner.nextLine().toLowerCase();

        // INITIALIZE A HASHMAP NAMED [result] THAT WILL HOLD THE SEARCH RESULTS
        Map<String, Motorcycle> result = new HashMap<>();

        // LOOP THROUGH ALL THE CONTENTS OF THE INVENTORY HASHMAP
        for (Map.Entry<String, Motorcycle> entry : inventory.entrySet()) {
            Motorcycle m = entry.getValue();

            // CHECK IF THE CRITERIA MATCHES ANY OF THE INVENTORY ITEMS
            if (MatchesCriteria(m, criteria)) {
                // ADD THE MATCHING ITEM TO THE RESULT HASHMAP USING THE HASHED ENGINE NUMBER AS KEY
                result.put(entry.getKey(), m);
            }
        }

        // CALLS THE METHOD TO DISPLAY RESULTS AND PASSES THE HASHMAP AS A PARAMETER
        DisplayResults(result);
    }

    private int GetValidChoice() {
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

    private int SelectSortOrder() {
        while (true) {
            System.out.println("\n===== Select Sort Order =====");
            System.out.println("1. Ascending");
            System.out.println("2. Descending");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");

            int choice = GetValidChoice();
            if (choice == 1 || choice == 2) {
                return choice;
            } else if (choice == 3) {
                return 3; // Signals exit
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // METHOD THAT ASKS THE USER TO SELECT A VALID BRAND
    private String SelectBrand() {
        // WHILE LOOP SET TO TRUE, SO THAT I WOULD JUST CONTINUE TO RUN UNTIL THE USER SUPPLIES A VALID VALUE
        while (true) {
            // DISPLAYS THE LIST OF ALL AVAILABLE BRANDS
            System.out.println("\n===== Select Motorcycle Brand =====");
            for (int i = 0; i < brands.length; i++) {
                /**
                 * TAKES THE INDEX [i] AND INCREMENTS IT BY ONE TO DISPLAY THE
                 * CORRECT NUMERICAL KEY SUBSTRING THE FIRST LETTER OF THE
                 * BRANDS ARRAY AND MAKES IT CAPITAL CASE PRINTS THE REST OF THE
                 * SUBSTRINGS
                 */
                System.out.println((i + 1) + ". " + brands[i].substring(0, 1).toUpperCase() + brands[i].substring(1));
            }

            // ASKS FOR USER INPUT
            System.out.print("Enter choice: ");
            // USER INPUT IS STORED IN A VARIABLE
            int choice = GetValidChoice();

            // VALIDATION CHECK: IF USER INPUT MATCHES ANY OF THE OPTIONS GIVEN
            if (choice >= 1 && choice <= 5) {
                return brands[choice - 1]; // Convert to zero-based index
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // METHOD THAT CREATES THE MOTORCYCLE OBJECT
    private Motorcycle CreateMotorcycle(String brand, String engineNo, int quantity) {
        return new Motorcycle(brand, LocalDate.now(), engineNo, "On-hand", "New", quantity);
    }

    // METHOD THAT WILL GET THE MOTORCYCLE THAT MATCH THE CRITERIA
    private boolean MatchesCriteria(Motorcycle m, String criteria) {
        return m.GetBrand().toLowerCase().contains(criteria)
                || m.GetCreatedOn().toString().contains(criteria)
                || m.GetEngineNumber().toLowerCase().contains(criteria)
                || m.GetStatus().toLowerCase().contains(criteria)
                || m.GetStockLabel().toLowerCase().contains(criteria);
    }

    // METHOD TO PRINT THE RESULTS OF THE SEARCH
    private void DisplayResults(Map<String, Motorcycle> result) {
        // REUSE THE METHOD TO PRINT THE TABLE FOR SEARCH RESULTS
        PrintMotorcycleTable(result);
    }

    // METHOD TO PRINT TABLE HEADER
    private void PrintTableHeader() {
        // PRINT TOP BORDER LINE
        System.out.println("-------------------------------------------------------------------------------");
        /**
         * FORMAT THE PRINTING: NEGATIVE VALUE MEANS LEFT ALIGN AND VICE VERSA
         * %-10S = LEFT ALIGN 10 CHARACTERS
         */
        System.out.printf("%-10s %-15s %-20s %-10s %-15s %-5s%n",
                "Brand", "Created On", "Engine Number", "Status", "Stock Label", "Quantity");
        System.out.println("-------------------------------------------------------------------------------");
    }

    // METHOD TO PRINT A TABLE OF MOTORCYCLES
    private void PrintMotorcycleTable(Map<String, Motorcycle> motorcycles) {
        // VALIDATION CHECK: IF MAP IS EMPTY, PRINT ERROR MESSAGE
        if (motorcycles.isEmpty()) {
            System.out.println("No motorcycles found.");
            return;
        }

        // PRINT TABLE HEADER
        this.PrintTableHeader();

        // ITERATE OVER THE HASHMAP AND PRINT EACH MOTORCYCLE
        for (Motorcycle m : motorcycles.values()) {
            System.out.printf("%-10s %-15s %-20s %-10s %-15s %-5s%n",
                    m.GetBrand(),
                    m.GetCreatedOn(),
                    m.GetEngineNumber(),
                    m.GetStatus(),
                    m.GetStockLabel(),
                    m.GetStockCount());
        }

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println();
    }

    /*
    //  METHOD TO HASH THE ENGINE NUMBER
    private String HashEngineNumber(String engineNumber) {
        // USE JAVA'S BUILT-IN HASHING MECHANISM
        return String.valueOf(engineNumber.hashCode());
    }
     */
    // METHOD TO HASH ENGINE NUMBER USING SHA-256 
    private String HashEngineNumber(String engineNo) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(engineNo.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error: Unable to hash engine number.", e);
        }
    }

    // ADDS MULTIPLE SAMPLE DATA TO THE INVENTORY
    private void AddSampleData() {
        // ADD SAMPLE HONDA ITEM
        inventory.put(HashEngineNumber("ENG123456"), new Motorcycle("Honda", LocalDate.now(), "ENG123456", "On-hand", "New", 3));
        // ADD SAMPLE KYMCO ITEM
        inventory.put(HashEngineNumber("ENG654321"), new Motorcycle("Kymco", LocalDate.now(), "ENG654321", "Sold", "Used", 1));
        // ADD SAMPLE YAMAHA ITEM
        inventory.put(HashEngineNumber("ENG789012"), new Motorcycle("Yamaha", LocalDate.now(), "ENG789012", "On-hand", "New", 5));
        // ADD SAMPLE SUZUKI ITEM
        inventory.put(HashEngineNumber("ENG456789"), new Motorcycle("Suzuki", LocalDate.now(), "ENG456789", "Sold", "Used", 2));
        // ADD SAMPLE KAWASAKI ITEM
        inventory.put(HashEngineNumber("ENG987654"), new Motorcycle("Kawasaki", LocalDate.now(), "ENG987654", "On-hand", "New", 4));
    }
}
