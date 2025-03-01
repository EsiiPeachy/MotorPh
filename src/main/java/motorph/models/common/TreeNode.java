package motorph.models.common;

import motorph.models.objects.Motorcycle;

public class TreeNode {

    public Motorcycle motorcycle;
    public TreeNode left, right;

    public TreeNode(Motorcycle motorcycle) {
        this.motorcycle = motorcycle;
        this.left = this.right = null;
    }
}
