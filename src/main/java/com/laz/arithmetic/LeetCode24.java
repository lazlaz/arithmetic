package com.laz.arithmetic;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

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

    //1080. 根到叶路径上的不足节点
    @Test
    public void test5() {
        TreeNode node = Utils.createTree(new Integer[]{
                1,2,3,4,-99,-99,7,8,9,-99,-99,12,13,-99,14
        });
        Solution5 solution5 = new Solution5();
        TreeNode treeNode = solution5.sufficientSubset(node, 1);
        Assert.assertEquals(1, treeNode.val);

        TreeNode node2 = Utils.createTree(new Integer[]{
               100,-99,-99
        });
        treeNode = solution5.sufficientSubset(node2, 5);
        Assert.assertEquals(null, treeNode);

        TreeNode node3 = Utils.createTree(new Integer[]{
                1,2,-3,-5,null,4,null
        });
        treeNode = solution5.sufficientSubset(node3, -1);
    }

    class Solution5 {
        public TreeNode sufficientSubset(TreeNode root, int limit) {
            boolean hasLeaf = checkDeficiencyNoe(root, 0, limit);
            return hasLeaf ? root : null;
        }

        private boolean checkDeficiencyNoe(TreeNode node, int sum, int limit) {
            if (node == null) {
                return false;
            }
            if (node.left == null && node.right == null) {
                return node.val+sum>=limit;
            }
            boolean checkLeft  = checkDeficiencyNoe(node.left, sum+node.val, limit);
            boolean checkRight  = checkDeficiencyNoe(node.right, sum+node.val, limit);
            if (!checkLeft) {
                node.left = null;
            }
            if (!checkRight) {
                node.right = null;
            }
            return checkLeft || checkRight;
        }
    }

    //1090. 受标签影响的最大值
    @Test
    public void test6() {
        Solution6 solution6 = new Solution6();
        int r1 = solution6.largestValsFromLabels(new int[] {
                5,4,3,2,1
        }, new int[] {
                1,1,2,2,3
        },3,1);
        Assert.assertEquals(9, r1);

        Assert.assertEquals(12, solution6.largestValsFromLabels(new int[] {
                5,4,3,2,1
        }, new int[] {
               1,3,3,3,2
        },3,2));

        Assert.assertEquals(16, solution6.largestValsFromLabels(new int[] {
                9,8,8,7,6
        }, new int[] {
                0,0,0,1,1
        },3,1));

        Assert.assertEquals(21, solution6.largestValsFromLabels(new int[] {
                4,6,3,9,2
        }, new int[] {
                2,0,0,0,2
        },5,2));
    }

    class Solution6 {
        public int largestValsFromLabels(int[] values, int[] labels, int numWanted, int useLimit) {
            List<Pair> list = new ArrayList();
            for (int i=0; i<values.length; i++) {
                Pair p = new Pair(values[i],i);
                list.add(p);
            }
            List<Pair> newList = list.stream().sorted(new Comparator<Pair>() {
                @Override
                public int compare(Pair o1, Pair o2) {
                    return o2.value-o1.value;
                }
            }).collect(Collectors.toList());
            int sum = 0;
            int num = 0;
            Map<Integer, Integer> hasLabel = new HashMap<>();
            for (Pair p: newList) {
                if (num >= numWanted) {
                    break;
                }
                int limit = hasLabel.getOrDefault(labels[p.index],0);
                if (limit < useLimit) {
                    sum += p.value;
                    num++;
                    hasLabel.put(labels[p.index], ++limit);
                }
            }
            return sum;

        }

        class Pair {
            private int value;
            private int index;

            public Pair(int value, int index) {
                this.value = value;
                this.index = index;
            }

            @Override
            public int hashCode() {
                return Objects.hash(value, index);
            }
        }
    }
}
