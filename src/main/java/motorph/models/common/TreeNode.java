package motorph.models.common;

import motorph.models.objects.Motorcycle;

public class TreeNode {
    public Motorcycle motorcycle;
    public TreeNode left, right;
    public int height;  // Added height attribute

    public TreeNode(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
        this.left = this.right = null;
        this.height = 1;  // Initialize height to 1 (leaf node)
    }
    
    // GETTER
    public int GetHeight(TreeNode node) {
        return (node == null) ? 0 : node.height;
    }
}
