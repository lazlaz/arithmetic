package com.laz.arithmetic;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

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

    //1037. 有效的回旋镖
    @Test
    public void test2() {
        Solution2 solution2 = new Solution2();
        solution2.isBoomerang(new int[][]{
                {1,1},{2,3},{3,2}
        });
    }

    class Solution2 {
        public boolean isBoomerang(int[][] points) {
            int[] v1 = {points[1][0] - points[0][0], points[1][1] - points[0][1]};
            int[] v2 = {points[2][0] - points[0][0], points[2][1] - points[0][1]};
            return v1[0] * v2[1] - v1[1] * v2[0] != 0;
        }
    }

    //513. 找树左下角的值
    @Test
    public void test3() {
        Solution3 solution3 = new Solution3();
        Assert.assertEquals(7, solution3.findBottomLeftValue(Utils.createTree(new Integer[] {
                1,2,3,4,null,5,6,null,null,7}
                )));
    }

    class Solution3 {
        public int findBottomLeftValue(TreeNode root) {
            Deque<TreeNode> deque = new ArrayDeque();
            deque.offer(root);
            int res = 0;
            while (!deque.isEmpty()) {
                boolean first = true;
                int size = deque.size();
                for (int i=0; i<size; i++) {
                    TreeNode node = deque.pop();
                    if (first) {
                        res = node.val;
                        first = false;
                    }

                    if (node.left!=null) {
                        deque.offer(node.left);
                    }
                    if (node.right!=null) {
                        deque.offer(node.right);
                    }
                }
            }
            return res;
         }
    }

    //535. TinyURL 的加密与解密
    @Test
    public void test4() {
        Codec codec = new Codec();
        String str = codec.encode("http://tinyurl.com/dsfsd");
        Assert.assertEquals("http://tinyurl.com/dsfsd",codec.decode(str));
    }

    class Codec {
        private Map<Integer, String> dataBase = new HashMap<Integer, String>();
        private int id;

        public String encode(String longUrl) {
            id++;
            dataBase.put(id, longUrl);
            return "http://tinyurl.com/" + id;
        }

        public String decode(String shortUrl) {
            int p = shortUrl.lastIndexOf('/') + 1;
            int key = Integer.parseInt(shortUrl.substring(p));
            return dataBase.get(key);
        }
    }
}
