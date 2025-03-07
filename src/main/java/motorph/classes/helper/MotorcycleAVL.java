package motorph.classes.helper;

import java.util.*;
import motorph.models.common.TreeNode;
import motorph.models.objects.*;

public class MotorcycleAVL {

    private TreeNode root;

    // Insert a motorcycle while maintaining balance
    public void Insert(Motorcycle motorcycle) {
        root = InsertRec(root, motorcycle);
    }

    private TreeNode InsertRec(TreeNode node, Motorcycle motorcycle) {
        if (node == null) {
            return new TreeNode(motorcycle);
        }

        // Sort based on engine number (can be changed to any other property)
        if (motorcycle.GetEngineNumber().compareTo(node.motorcycle.GetEngineNumber()) < 0) {
            node.left = InsertRec(node.left, motorcycle);
        } else {
            node.right = InsertRec(node.right, motorcycle);
        }

        // Update height
        node.height = 1 + Math.max(GetHeight(node.left), GetHeight(node.right));

        // Balance the tree
        return Balance(node);
    }

    // Delete a motorcycle while maintaining balance
    public void Delete(String engineNumber) {
        root = DeleteRec(root, engineNumber);
    }

    private TreeNode DeleteRec(TreeNode node, String engineNumber) {
        if (node == null) {
            return null;
        }

        if (engineNumber.compareTo(node.motorcycle.GetEngineNumber()) < 0) {
            node.left = DeleteRec(node.left, engineNumber);
        } else if (engineNumber.compareTo(node.motorcycle.GetEngineNumber()) > 0) {
            node.right = DeleteRec(node.right, engineNumber);
        } else {
            // Node found - perform deletion
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                TreeNode successor = FindMin(node.right);
                node.motorcycle = successor.motorcycle;
                node.right = DeleteRec(node.right, successor.motorcycle.GetEngineNumber());
            }
        }

        if (node == null) {
            return null;
        }

        // Update height
        node.height = 1 + Math.max(GetHeight(node.left), GetHeight(node.right));

        // Balance the tree
        return Balance(node);
    }

    // Search for a motorcycle by engine number
    public Set<String> Search(String criteria) {
        Set<String> results = new HashSet<>();
        SearchRec(root, criteria, results);
        return results;
    }

    private void SearchRec(TreeNode node, String criteria, Set<String> results) {
        if (node == null) {
            return;
        }
        
        // Try parsing criteria as an integer for stock count comparison
        Integer stockCount = null;
        try {
            stockCount = Integer.parseInt(criteria);
        } catch (NumberFormatException ignored) {
            // Not a number, so continue searching normally
        }

        if (node.motorcycle.GetBrand().toLowerCase().contains(criteria)
                || node.motorcycle.GetCreatedOn().toString().contains(criteria)
                || node.motorcycle.GetEngineNumber().toLowerCase().contains(criteria)
                || node.motorcycle.GetStatus().toLowerCase().contains(criteria)
                || node.motorcycle.GetStockLabel().toLowerCase().contains(criteria)
                || (stockCount != null && node.motorcycle.GetStockCount() == stockCount)) {
            results.add(node.motorcycle.GetEngineNumber());
        }

        SearchRec(node.left, criteria, results);
        SearchRec(node.right, criteria, results);
    }

    // BALANCING THE NODE
    private TreeNode Balance(TreeNode node) {
        int balanceFactor = GetBalanceFactor(node);

        // LEFT HEAVY
        if (balanceFactor > 1) {
            if (GetBalanceFactor(node.left) < 0) {
                node.left = LeftRotate(node.left);  // Left-Right Case
            }
            return RightRotate(node);  // Left-Left Case
        }

        // RIGHT HEAVY
        if (balanceFactor < -1) {
            if (GetBalanceFactor(node.right) > 0) {
                node.right = RightRotate(node.right);  // Right-Left Case
            }
            return LeftRotate(node);  // Right-Right Case
        }

        return node;
    }

    // RIGHT ROTATION
    private TreeNode RightRotate(TreeNode y) {
        TreeNode x = y.left;
        TreeNode T2 = x.right;

        x.right = y;
        y.left = T2;

        // UPDATE HEIGHTS
        y.height = 1 + Math.max(GetHeight(y.left), GetHeight(y.right));
        x.height = 1 + Math.max(GetHeight(x.left), GetHeight(x.right));

        return x;
    }

    // LEFT ROTATION
    private TreeNode LeftRotate(TreeNode x) {
        TreeNode y = x.right;
        TreeNode T2 = y.left;

        y.left = x;
        x.right = T2;

        // UPDATE HEIGHTS
        x.height = 1 + Math.max(GetHeight(x.left), GetHeight(x.right));
        y.height = 1 + Math.max(GetHeight(y.left), GetHeight(y.right));

        return y;
    }

    // GET THE HEIGHT OF A NODE
    private int GetHeight(TreeNode node) {
        return (node == null) ? 0 : node.height;
    }

    // GET THE BALANCE FACTOR OF THE NODE
    private int GetBalanceFactor(TreeNode node) {
        return (node == null) ? 0 : GetHeight(node.left) - GetHeight(node.right);
    }

    // FIND THE MINIMUM NODE IN A SUBTREE
    private TreeNode FindMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
