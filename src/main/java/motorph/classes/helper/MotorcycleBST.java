package motorph.classes.helper;

import java.util.*;
import motorph.models.objects.*;
import motorph.models.common.*;

public class MotorcycleBST {

    private TreeNode root;

    // Insert motorcycles into BST
    public void Insert(Motorcycle motorcycle) {
        root = InsertRec(root, motorcycle);
    }

    private TreeNode InsertRec(TreeNode root, Motorcycle motorcycle) {
        if (root == null) {
            return new TreeNode(motorcycle) {
            };
        }

        // Sort based on engine number (or other criteria)
        if (motorcycle.GetEngineNumber().compareTo(root.motorcycle.GetEngineNumber()) < 0) {
            root.left = InsertRec(root.left, motorcycle);
        } else {
            root.right = InsertRec(root.right, motorcycle);
        }

        return root;
    }

    // SEARCH FOR MOTORCYCLES MATCHING THE CRITERIA
    public List<Motorcycle> Search(String criteria) {
        List<Motorcycle> results = new ArrayList<>();
        SearchRec(root, criteria, results);
        return results;
    }

    // RECURSIVE SEARCH
    private void SearchRec(TreeNode node, String criteria, List<Motorcycle> results) {
        if (node == null) {
            return;
        }

        // CONVERT CRITERIA TO LOWERCASE FOR CASE-INSENSITIVE MATCHING
        criteria = criteria.toLowerCase();

        // CHECK IF CRITERIA IS A NUMBER (SUPPORTS NEGATIVE NUMBERS)
        boolean isNumeric = criteria.matches("-?\\d+"); 

        // MATCH THE CRITERIA
        if (node.motorcycle.GetBrand().toLowerCase().contains(criteria)
                || node.motorcycle.GetCreatedOn().toString().contains(criteria)
                || node.motorcycle.GetEngineNumber().toLowerCase().contains(criteria)
                || node.motorcycle.GetStatus().toLowerCase().contains(criteria)
                || node.motorcycle.GetStockLabel().toLowerCase().contains(criteria)
                || (isNumeric && node.motorcycle.GetStockCount() == Integer.parseInt(criteria))) {
            results.add(node.motorcycle);
        }

        // SEARCH LEFT AND RIGHT SUB-TREES
        SearchRec(node.left, criteria, results);
        SearchRec(node.right, criteria, results);
    }

}
