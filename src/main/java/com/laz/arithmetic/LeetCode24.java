package com.laz.arithmetic;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode24 {

    @Test
    //965. 单值二叉树
    public void test1() {
        Solution1 solution1 = new Solution1();
        Assert.assertEquals(true, solution1.isUnivalTree(Utils.createTree(new Integer[]{
                1,1,1,1,1,null,1
        })));
    }

    class Solution1 {
        private int unival;
        private boolean unique = true;
        public boolean isUnivalTree(TreeNode root) {
                if (root == null) {
                    return false;
                }
                this.unival = root.val;
                dfs(root);
                return unique;
        }

        private void dfs(TreeNode root) {
            if (!this.unique || root == null ) {
                return ;
            }
            if (this.unival != root.val) {
                unique = false;
            }
            dfs(root.left);
            dfs(root.right);
        }
    }
}
