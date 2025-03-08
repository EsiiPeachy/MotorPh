/**
 * VERSION CONTROL : 1.2
 */
package motorph.classes.process;

import java.time.LocalDate;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import motorph.classes.helper.*;
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
    private MotorcycleAVL avl = new MotorcycleAVL();

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
        if (brand == null) {
            return;
        }

        // VALIDATE ENGINE NUMBER INPUT (ENSURE NUMERIC & MAX 6 DIGITS)
        int engineNumber;
        while (true) {
            System.out.print("Enter Engine Number (max 6 digits, numeric): ");
            if (scanner.hasNextInt()) {
                engineNumber = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                // CHECK IF ENGINE NUMBER EXCEEDS 6 DIGITS
                if (engineNumber >= 0 && engineNumber <= 999999) {
                    break;
                } else {
                    System.out.println("Error: Engine number must be a numeric value with up to 6 digits.");
                }
            } else {
                System.out.println("Error: Engine number must be a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        String engineNo = FormatEngineNumber(engineNumber); // Format engine number
        String hashedEngineNo = HashEngineNumber(engineNo);

        if (inventory.containsKey(hashedEngineNo)) {
            System.out.println("Error: Engine number already exists in the inventory.");
            return;
        }

        // VALIDATE QUANTITY INPUT
        int quantity;
        while (true) {
            System.out.print("Enter Quantity: ");
            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                break;
            } else {
                System.out.println("Error: Quantity must be a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        // CREATE AND ADD MOTORCYCLE
        Motorcycle motorcycle = CreateMotorcycle(brand, engineNo, quantity);
        inventory.put(hashedEngineNo, motorcycle);
        avl.Insert(motorcycle);

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

        // DELETE THE SUPPLIED ENGINE NUMBER OF THE MOTORCYCLE ITEM FROM THE INVENTORY USING THE HASHED ENGINE NUMBER AS THE KEY
        Motorcycle removedItem = inventory.remove(hashedEngineNo);

        //  DELETE THE SUPPLIED ENGINE NUMBER OF THE MOTORCYCLE ITEM FROM THE BINARY SEARCH TREE USING THE HASHED ENGINE NUMBER AS THE KEY
        avl.Delete(engineNo);

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
        List<Motorcycle> motorcycles = new ArrayList<>(inventory.values());
        while (true) {
            System.out.println("\n===== Select Sort Order =====");
            System.out.println("1. Ascending");
            System.out.println("2. Descending");
            System.out.println("3. Back");
            System.out.print("Enter choice: ");

            int choice = GetValidChoice();
            switch (choice) {
                case 1 -> {
                    MergeSort(motorcycles, 0, motorcycles.size() - 1, true); // TRUE = ASCENDING ORDER
                    UpdateInventory(motorcycles);
                    return;
                }
                case 2 -> {
                    MergeSort(motorcycles, 0, motorcycles.size() - 1, false); // FALSE = DESCENDING ORDER
                    UpdateInventory(motorcycles);
                    return;
                }
                case 3 -> {
                    System.out.println("Returning to the main menu...");
                    return;
                }
                default ->
                    System.out.println("Invalid choice. Try again.");
            }
        }
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
        Set<String> keys = avl.Search(criteria);

        Map<String, Motorcycle> results = new HashMap<>();
        // LOOP THROUGH THE ENGINE NUMBERS AND RETRIEVE THE CORRESPONDING MOTORCYCLE OBJECTS
        for (String engineNumber : keys) {
            Motorcycle motorcycle = inventory.get(engineNumber);
            if (motorcycle != null) {
                results.put(engineNumber, motorcycle);
            }
        }

        // CALLS THE METHOD TO DISPLAY RESULTS AND PASSES THE HASHMAP AS A PARAMETER
        DisplayResults(results);
    }

    // METHOD TO FORMAT ENGINE NUMBER
    private String FormatEngineNumber(int number) {
        return String.format("ENG%06d", number);
    }

    // MERGE SORT RECURSIVE FUNCTION
    private void MergeSort(List<Motorcycle> motorcycles, int left, int right, boolean ascending) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            // RECURSIVELY SORT LEFT & RIGHT HALVES
            MergeSort(motorcycles, left, mid, ascending);
            MergeSort(motorcycles, mid + 1, right, ascending);

            // MERGE THE SORTED HALVES
            Merge(motorcycles, left, mid, right, ascending);
        }
    }

    // MERGES TWO SORTED HALVES
    private void Merge(List<Motorcycle> list, int left, int mid, int right, boolean ascending) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // TEMP ARRAYS
        List<Motorcycle> leftList = new ArrayList<>(list.subList(left, mid + 1));
        List<Motorcycle> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        // MERGE LOGIC - SORTING BY BRAND
        while (i < n1 && j < n2) {
            int comparison = leftList.get(i).GetBrand().compareToIgnoreCase(rightList.get(j).GetBrand());
            if ((ascending && comparison <= 0) || (!ascending && comparison > 0)) {
                list.set(k++, leftList.get(i++));
            } else {
                list.set(k++, rightList.get(j++));
            }
        }

        // COPY REMAINING ELEMENTS
        while (i < n1) {
            list.set(k++, leftList.get(i++));
        }
        while (j < n2) {
            list.set(k++, rightList.get(j++));
        }
    }

    // UPDATES THE INVENTORY AFTER SORTINGQ
    private void UpdateInventory(List<Motorcycle> sortedList) {
        inventory.clear();
        for (Motorcycle m : sortedList) {
            String hashedEngineNo = HashEngineNumber(m.GetEngineNumber());
            inventory.put(hashedEngineNo, m);
        }
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
                String brand = brands[choice - 1]; // GET BRAND FROM ARRAY
                return brand.substring(0, 1).toUpperCase() + brand.substring(1).toLowerCase();
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // METHOD THAT CREATES THE MOTORCYCLE OBJECT
    private Motorcycle CreateMotorcycle(String brand, String engineNo, int quantity) {
        return new Motorcycle(brand, LocalDate.now(), engineNo, "On-hand", "New", quantity);
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
        Motorcycle honda = new Motorcycle("Honda", LocalDate.now(), "ENG123456", "On-hand", "New", 3);
        inventory.put(HashEngineNumber("ENG123456"), honda); // ADD TO INVENTORY
        avl.Insert(honda); // ADD TO BST

        // ADD SAMPLE KYMCO ITEM
        Motorcycle kymco = new Motorcycle("Kymco", LocalDate.now(), "ENG654321", "Sold", "Used", 1);
        inventory.put(HashEngineNumber("ENG654321"), kymco); // ADD TO INVENTORY
        avl.Insert(kymco); // ADD TO BST

        // ADD SAMPLE YAMAHA ITEM
        Motorcycle yamaha = new Motorcycle("Yamaha", LocalDate.now(), "ENG789012", "On-hand", "New", 5);
        inventory.put(HashEngineNumber("ENG789012"), yamaha); // ADD TO INVENTORY
        avl.Insert(yamaha); // ADD TO BST

        // ADD SAMPLE SUZUKI ITEM
        Motorcycle suzuki = new Motorcycle("Suzuki", LocalDate.now(), "ENG456789", "Sold", "Used", 2);
        inventory.put(HashEngineNumber("ENG456789"), suzuki); // ADD TO INVENTORY
        avl.Insert(suzuki); // ADD TO BST

        // ADD SAMPLE KAWASAKI ITEM
        Motorcycle kawasaki = new Motorcycle("Kawasaki", LocalDate.now(), "ENG987654", "On-hand", "New", 4);
        inventory.put(HashEngineNumber("ENG987654"), kawasaki); // ADD TO INVENTORY
        avl.Insert(kawasaki); // ADD TO BST
    }
}
