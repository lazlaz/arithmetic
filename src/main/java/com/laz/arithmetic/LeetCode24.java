package com.laz.arithmetic;

import com.sun.xml.internal.ws.policy.AssertionSet;
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

    //1377. T 秒后青蛙的位置
    @Test
    public void test7() {
        Solution7 solution7 = new Solution7();
        Assert.assertEquals(0.16666666666666666, solution7.frogPosition(
                7, new int[][]{
            {1,2},{1,3},{1,7},{2,4},{2,6},{3,5}
        },2,4), 5);
    }

    class Solution7 {
        private double ans = 0;
        public double frogPosition(int n, int[][] edges, int t, int target) {
            List<Integer>[] g = new ArrayList[n+1];
            Arrays.setAll(g, e->new ArrayList<>());
            g[1].add(0); //减少额外的判断
            for (int[] edge: edges) {
                int x=  edge[0];
                int y = edge[1];
                g[x].add(y);
                g[y].add(x); //构建树
            }
            int value = 1; //初始节点值为1
            int parent = 0;
            int prod = 1;
            dfs(g, target, value, parent, t, prod);
            return ans;

        }

        private boolean dfs(List<Integer>[] g, int target, int value, int parent, int t, long prod) {
            // t 秒后必须在 target（恰好到达，或者 target 是叶子停在原地）
            if (value == target && (t==0 || (g[value].size()-1)==0)) {
                ans = 1.0/prod;
                return true;
            }
            //如果找到了目标但不是叶子节点，或者时间过了返回false
            if (value == target || t == 0) {
                return false;
            }
            for (int child: g[value]) {
                if (child!=parent && dfs(g, target, child, value, t-1, prod*(g[value].size()-1))) {
                    return true;
                }
            }
            return false;
        }
    }

    //2517. 礼盒的最大甜蜜度
    @Test
    public void test8() {
        Solution8 solution8 = new Solution8();
        Assert.assertEquals(8, solution8.maximumTastiness(new int[]{
                13,5,1,8,21,2
        }, 3));
    }

    class Solution8 {
        public int maximumTastiness(int[] price, int k) {
            Arrays.sort(price);
            int left = 0;
            int right = price[price.length-1]-price[0];
            int mid;
            int ans = 0;
            while (left<=right) {
                mid = left + (right-left)/2; //不用+，反正溢出整形
                if (check(mid, price, k)) {
                    ans = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return ans;
        }

        private boolean check(int sweetness, int[] price, int k) {
            //1,2,5,8,13,21 k=8
            int pre = Integer.MAX_VALUE;
            int count = 0;
            for (int p: price) {
                if (Math.abs(p-pre)>=sweetness) {
                    pre = p;
                    count++;
                }
            }
            return count>=k;
        }
    }

    //2559. 统计范围内的元音字符串数
    @Test
    public void test9() {
        Solution9 solution9 = new Solution9();
        Assert.assertArrayEquals(new int[] {
                2,3,0
        }, solution9.vowelStrings(new String[] {
                "aba","bcb","ece","aa","e"
        }, new int[][] {
                {0,2},{1,4},{1,1}
        }));

        Assert.assertArrayEquals(new int[] {
                3,2,1
        }, solution9.vowelStrings(new String[] {
                "a","e","i"
        }, new int[][] {
                {0,2},{0,1},{2,2}
        }));
    }

    class Solution9 {
        private String str = "aeiou";
        public int[] vowelStrings(String[] words, int[][] queries) {
            //前缀和 preSum[i] words前i个字符串元音数
            int[] preSum = new int[words.length+1];
            for (int i=0; i<words.length; i++) {
                int v = isMatch(words[i]) ? 1 : 0;
                preSum[i+1] = preSum[i] + v;
            }
            int queryLen = queries.length;
            int[] ans = new int[queryLen];
            for (int i=0; i<queryLen; i++) {
                int start = queries[i][0];
                int end = queries[i][1];
                ans[i] = preSum[end+1]-preSum[start];
            }
            return ans;
        }

        private boolean isMatch(String word) {
            String start = String.valueOf(word.charAt(0));
            String end = String.valueOf(word.charAt(word.length()-1));
            return str.contains(start) && str.contains(end);
        }
    }

    //1156. 单字符重复子串的最大长度
    @Test
    public void test10() {
        Solution10 solution10 = new Solution10();
//        Assert.assertEquals(3, solution10.maxRepOpt1("ababa"));
//        Assert.assertEquals(6, solution10.maxRepOpt1("aaabaaa"));
//        Assert.assertEquals(4, solution10.maxRepOpt1("aaabbaaa"));
//        Assert.assertEquals(5, solution10.maxRepOpt1("aaaaa"));
//        Assert.assertEquals(6, solution10.maxRepOpt1("babbaaabbbbbaa"));
        Assert.assertEquals(1, solution10.maxRepOpt1("abcdef"));
    }

    class Solution10 {
        public int maxRepOpt1(String text) {
            Map<Character, Integer> count = new HashMap<>();
            for (int i=0; i<text.length(); i++) {
                char c= text.charAt(i);
                count.put(c, count.getOrDefault(c,0)+1);
            }
            int res=0;
            for (int i=0; i<text.length();) {
                //找出第一段[i,j)
                int j=i;
                while (j<text.length() && text.charAt(j) == text.charAt(i)) {
                    j++;
                }
                //长度小于最长&前后有其他空余
                int curCount = j-i;
                if (curCount < count.getOrDefault(text.charAt(i), 0) && ((j<text.length() || i>0))) {
                    res = Math.max(res, curCount+1);
                }
                //找出第二段[j+1,k)
                int k = j+1;
                while (k<text.length() && text.charAt(k) == text.charAt(i)) {
                    k++;
                }
                res = Math.max(res, Math.min(k-i, count.getOrDefault(text.charAt(i), 0)));
                //跳过已经找出的第一段
                i=j;
            }
            return res;
        }
    }

    //2352. 相等行列对
    @Test
    public void test11() {
        Solution11 solution11  = new Solution11();
        Assert.assertEquals(1, solution11.equalPairs(new int[][]{
                {3,2,1},{1,7,6},{2,7,7}
        }));
    }

    class Solution11 {
            public int equalPairs(int[][] grid) {
                Map<String,Integer> map = new HashMap<>();
                for (int i=0; i<grid.length; i++) {
                    //拼接每一行的数字，为了保证歧义，添加特殊符号分割&
                    StringBuffer sb = new StringBuffer();
                    for (int j=0; j<grid[i].length; j++) {
                        sb.append(grid[i][j]);
                        sb.append("&");
                    }
                    Integer count = map.getOrDefault(sb.toString(), 0);
                    map.put(sb.toString(), ++count);
                }
                int ans = 0;
                //遍历列，找出重复的
                for (int i=0; i<grid.length; i++) {
                    StringBuffer sb = new StringBuffer();
                    for (int j=0; j<grid[i].length; j++) {
                        sb.append(grid[j][i]);
                        sb.append("&");
                    }
                    ans += map.getOrDefault(sb.toString(), 0);
                }
                return ans;
            }

    }

    @Test
    //2611. 老鼠和奶酪
    public void test12() {
        Solution12 solution12 = new Solution12();
        Assert.assertEquals(15, solution12.miceAndCheese(new int[] {
                1,1,3,4
        }, new int[] {4,4,1,1}, 2));
    }

    class Solution12 {
        public int miceAndCheese(int[] reward1, int[] reward2, int k) {
            PriorityQueue<Integer> queue = new PriorityQueue();
            int n = reward1.length;
            int ans = 0;
            for (int i=0; i<n; i++) {
                ans += reward2[i];
                //如果1吃了，2就没法吃了，所以是1-2
                int diff = reward1[i] - reward2[i];
                queue.offer(diff);
                if (queue.size() > k) {
                    queue.poll(); //移除最小的
                }
            }
            while (!queue.isEmpty()) {
                ans += queue.poll();
            }
            return ans;

        }
    }

    //1483. 树节点的第 K 个祖先
    @Test
    public void test13(){
        TreeAncestor treeAncestor = new TreeAncestor(7, new int[] {
                -1, 0, 0, 1, 1, 2, 2
        });
        Assert.assertEquals(1, treeAncestor.getKthAncestor(3,1));

        Assert.assertEquals(0, treeAncestor.getKthAncestor(5,2));

        Assert.assertEquals(-1, treeAncestor.getKthAncestor(6,3));
    }

    class TreeAncestor {
        static final int LOG = 16; //因为条件是50000，所以2的16次方就可以了
        int[][] ancestors;
        public TreeAncestor(int n, int[] parent) {
            ancestors = new int[n][LOG];
            for (int i=0; i<n; i++) {
                Arrays.fill(ancestors[i], -1); //初始化默认没有父为-1
            }
            for (int i=0; i<n; i++) {
                ancestors[i][0] = parent[i];
            }
            for (int j=1; j<LOG; j++) {
                for (int i=0; i<n; i++) {
                    if (ancestors[i][j-1] != -1) {
                        ancestors[i][j] = ancestors[ancestors[i][j-1]][j-1]; //转移方程式，倍增
                    }
                }
            }
        }

        public int getKthAncestor(int node, int k) {
            for (int j=0; j<LOG; j++) {
                if (((k >> j) & 1) != 0) {
                     node = ancestors[node][j];
                     if (node == -1) {
                         return -1;
                     }
                }
            }
            return node;
        }
    }

    //1375. 二进制字符串前缀一致的次数
    @Test
    public void test14() {
        Solution14 solution14 = new Solution14();
        Assert.assertEquals(2, solution14.numTimesAllBlue(new int[] {
                3,2,4,1,5
        }));
        Assert.assertEquals(1, solution14.numTimesAllBlue(new int[] {
                4,1,2,3
        }));
    }

    class Solution14 {
        public int numTimesAllBlue(int[] flips) {
            int ans = 0, right = 0;
            for (int i=0; i<flips.length; i++) {
                right = Math.max(flips[i], right); //如果前面i次，反转的最大值下标是i，则前面都是1
                if (right == (i+1)) {
                    ans++;
                }
            }
            return ans;
        }
    }

    //1177. 构建回文串检测
    @Test
    public void test15() {
        Solution15 solution15 = new Solution15();
        List<Boolean> list = solution15.canMakePaliQueries("abcda", new int[][]{
                {3,3,0},{1,2,0},{0,3,1},{0,3,2},{0,4,1}
        });
        System.out.println(list);
    }

    class Solution15 {
        public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
            List<Boolean> anss = new ArrayList<>();
            int n = s.length();
            int[][] sum = new int[n+1][26]; //26个字母
            int i=0;
            for (char c: s.toCharArray()) {
                sum[i+1] = sum[i].clone(); //复制前一次的结果
                sum[i+1][c-'a']++;
                i++;
            }
            for (int j=0; j<queries.length; j++) {
                int l = queries[j][0];
                int r = queries[j][1];
                int k = queries[j][2];
                int m = 0; //奇数字母数量
                for (int z=0; z<26; z++) {
                    m += (sum[r+1][z]-sum[l][z]) % 2;
                }
                boolean ans = m/2<=k;
                anss.add(ans);
            }
            return anss;
        }

    }

    //1262. 可被三整除的最大和
    @Test
    public void test16() {
        Solution16 solution16 =new Solution16();
        Assert.assertEquals(18, solution16.maxSumDivThree(new int[] {
                3,6,5,1,8
        }));
    }

    class Solution16 {
        public int maxSumDivThree(int[] nums) {
            int n = nums.length;
            int[][] fn = new int[n+1][3];
            fn[0][0] = 0;
            fn[0][1] = Integer.MIN_VALUE;
            fn[0][2] = Integer.MIN_VALUE;
            for (int i=0; i<n; i++) {
                int v = nums[i];
                for (int j=0; j<3; j++) {
                    fn[i+1][j] = Math.max(fn[i][j], fn[i][(j-v%3+3)%3]+nums[i]);
                }
            }
            return fn[n][0];
        }
    }

    //1186. 删除一次得到子数组最大和
    @Test
    public void test17(){
        Solution17 solution17 = new Solution17();
        Assert.assertEquals(4, solution17.maximumSum(new int[] {
                1,-2,0,3
        }));
        Assert.assertEquals(3, solution17.maximumSum(new int[] {
                1,-2,-2,3
        }));
        Assert.assertEquals(-1, solution17.maximumSum(new int[] {
                -1,-1,-1,-1
        }));
        Assert.assertEquals(-50, solution17.maximumSum(new int[] {
                -50
        }));
    }

    class Solution17 {
        public int maximumSum(int[] arr) {
            int n = arr.length;
            int[][] dp = new int[n][2];
            dp[0][0] = arr[0];
            dp[0][1] = 0; //无效
            int ans = arr[0];
            for (int i=1; i<n; i++) {
                dp[i][0] = Math.max(dp[i-1][0],0)+arr[i];
                dp[i][1] = Math.max(dp[i-1][1]+arr[i],dp[i-1][0]);
                ans = Math.max(Math.max(ans, dp[i][0]),dp[i][1]);
            }
            return ans;
        }
    }
}
