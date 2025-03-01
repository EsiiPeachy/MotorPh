package motorph.classes.helper;

import java.util.*;
import motorph.models.objects.*;
import motorph.models.common.*;

public class MotorcycleBST {

    private TreeNode root;

    // INSERT MOTORCYCLE TO BST 
    public void Insert(Motorcycle motorcycle) {
        root = InsertRec(root, motorcycle);
    }

    // METHOD THAT HOLDS THE LOGIC OF THE INSERT AND UTILIZES RECURSION
    private TreeNode InsertRec(TreeNode root, Motorcycle motorcycle) {
        if (root == null) {
            return new TreeNode(motorcycle) {
            };
        }

        // SORT BASED ON ENGINER NUMBER (CAN BE CHANGED TO WHATEVER PROPERTY)
        if (motorcycle.GetEngineNumber().compareTo(root.motorcycle.GetEngineNumber()) < 0) {
            root.left = InsertRec(root.left, motorcycle);
        } else {
            root.right = InsertRec(root.right, motorcycle);
        }

        return root;
    }

    // DELETE A NODE FROM THE BST
    public void Delete(String engineNumber) {
        root = DeleteRec(root, engineNumber);
    }

    // METHOD THAT HOLDS THE LOGIC OF THE DELETE AND UTILIZES RECURSION
    private TreeNode DeleteRec(TreeNode root, String engineNumber) {
        if (root == null) {
            return root; // Node not found
        }

        // TRAVERSE TJE TREE TO FIND THE NODE TO BE DELETED
        if (engineNumber.compareTo(root.motorcycle.GetEngineNumber()) < 0) {
            // SEARCH THE LEFT SUB-TREE
            root.left = DeleteRec(root.left, engineNumber);
        } else if (engineNumber.compareTo(root.motorcycle.GetEngineNumber()) > 0) {
            // SEARCH THE RIGHT SUB-TREE
            root.right = DeleteRec(root.right, engineNumber);
        } else {
            // NODE TO BE DELETE - FOUND

            // CASE 1: NODE HAS NO CHILDREN (LEAF NODE)
            if (root.left == null && root.right == null) {
                root = null; // Remove node
            }  // CASE 2: NODE HAS ONE CHILD
            else if (root.left == null) {
                root = root.right; // REPLACE NODE WITH ITS RIGHT CHILD
            } else if (root.right == null) {
                root = root.left; // REPLACE NODE WITH ITS LEFT CHILD
            } // CASE 3: NODE HAS 2 CHILDREN
            else {
                // FIND THE IN-ORDER SUCCESSOR (SMALLEST NODE IN THE RIGHT SUB-TREE)
                TreeNode successor = FindMin(root.right);
                root.motorcycle = successor.motorcycle; // COPY THE SUCCESSOR'S DATA
                // DELETE THE IN-ORDER SUCCESSOR
                root.right = DeleteRec(root.right, successor.motorcycle.GetEngineNumber());
            }
        }

        return root;
    }

    // FIND THE NODE WITH THE MINIMUM VALUE (LEFT MOST NODE)
    private TreeNode FindMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // SEARCH FOR MOTORCYCLES MATCHING THE CRITERIA
    public Map<String, Motorcycle> Search(String criteria) {
        return SearchRec(root, criteria);
    }

    // RECURSIVE SEARCH
    private Map<String, Motorcycle> SearchRec(TreeNode node, String criteria) {
        Map<String, Motorcycle> results = new HashMap<>();

        if (node == null) {
            return results;
        }

        // Try parsing criteria as an integer for stock count comparison
        Integer stockCount = null;
        try {
            stockCount = Integer.parseInt(criteria);
        } catch (NumberFormatException ignored) {
            // Not a number, so continue searching normally
        }

        // Check if the motorcycle matches the criteria
        if (node.motorcycle.GetBrand().toLowerCase().contains(criteria)
                || node.motorcycle.GetCreatedOn().toString().contains(criteria)
                || node.motorcycle.GetEngineNumber().toLowerCase().contains(criteria)
                || node.motorcycle.GetStatus().toLowerCase().contains(criteria)
                || node.motorcycle.GetStockLabel().toLowerCase().contains(criteria)
                || (stockCount != null && node.motorcycle.GetStockCount() == stockCount)) {
            results.put(node.motorcycle.GetEngineNumber(), node.motorcycle);
        }

        // Recursively search left and right subtrees and merge results
        results.putAll(SearchRec(node.left, criteria));
        results.putAll(SearchRec(node.right, criteria));

        return results;
    }
}
