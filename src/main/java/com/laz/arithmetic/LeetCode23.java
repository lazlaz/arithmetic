package com.laz.arithmetic;

import com.google.common.base.Joiner;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.event.TreeSelectionEvent;
import java.util.*;

public class LeetCode23 {
    //432. 全 O(1) 的数据结构
    @Test
    public void test1() {
        //case1
        AllOne obj = new AllOne();
        obj.inc("hello");
        obj.inc("goodbye");
        obj.inc("hello");
        obj.inc("hello");
        Assert.assertEquals("hello", obj.getMaxKey());
        obj.inc("leet");
        obj.inc("code");
        obj.inc("leet");
        obj.dec("hello");
        obj.inc("leet");
        obj.inc("code");
        obj.inc("code");
        Assert.assertEquals("leet", obj.getMaxKey());

        //case2
        AllOne obj2 = new AllOne();
        Assert.assertEquals("", obj2.getMaxKey());
        Assert.assertEquals("", obj2.getMinKey());

        //case3
        AllOne obj3 = new AllOne();
        obj3.inc("hello");
        obj3.inc("world");
        obj3.inc("hello");
        obj3.dec("world");
        obj3.inc("hello");
        obj3.inc("leet");
        Assert.assertEquals("hello", obj3.getMaxKey());
        obj3.dec("hello");
        obj3.dec("hello");
        obj3.dec("hello");
        Assert.assertEquals("leet", obj3.getMaxKey());
    }

    class AllOne {
        class Value {
            int v;
            String key;

            public Value(int v, String key) {
                this.v = v;
                this.key = key;
            }

            public String getKey() {
                return key;
            }

            public int getV() {
                return v;
            }

            public void setV(int v) {
                this.v = v;
            }
        }

        private Map<String, Value> keyMap;
        private TreeMap<Value, String> valueMap;

        public AllOne() {
            this.keyMap = new HashMap<String, Value>();
            this.valueMap = new TreeMap<Value, String>(new Comparator<Value>() {
                @Override
                public int compare(Value o1, Value o2) {
                    if (o1.getV() == o2.getV()) {
                        return o1.getKey().compareTo(o2.getKey());
                    }
                    return o1.getV() - o2.getV();
                }
            });
        }

        public void inc(String key) {
            Value v = this.keyMap.get(key);
            if (v == null) {
                v = new Value(1, key);
                this.keyMap.put(key, v);
            } else {
                this.valueMap.remove(v);
                v.setV(v.getV() + 1);
            }
            this.valueMap.put(v, key);
        }

        public void dec(String key) {
            Value v = this.keyMap.get(key);
            if (v == null) {
                return;
            }
            int value = v.getV() - 1;
            if (value == 0) {
                this.keyMap.remove(key);
                this.valueMap.remove(v);
            } else {
                this.valueMap.remove(v);
                v.setV(value);
                this.valueMap.put(v, key);
            }
        }

        public String getMaxKey() {
            if (this.valueMap.size() == 0) {
                return "";
            }
            Value v = this.valueMap.lastKey();
            return this.valueMap.get(v);
        }

        public String getMinKey() {
            if (this.valueMap.size() == 0) {
                return "";
            }
            Value v = this.valueMap.firstKey();
            return this.valueMap.get(v);
        }
    }

    //720. 词典中最长的单词
    @Test
    public void test2() {
        Solution2_2 solution2 = new Solution2_2();
        String[] words = new String[]{"w", "wo", "wor", "worl", "world"};
        Assert.assertEquals("world", solution2.longestWord(words));


        Assert.assertEquals("apple", solution2.longestWord(new String[]{"a", "banana", "app",
                "appl", "ap", "apply", "apple"}));

        Assert.assertEquals("", new Solution2_3().longestWord(new String[]{"banana"}));
    }

    class Solution2 {
        public String longestWord(String[] words) {
            Map<String, Integer> map = new HashMap<>();
            Arrays.sort(words);
            for (int i = 0; i < words.length; i++) {
                map.put(words[i], i);
            }
            int maxLen = 0;
            String maxWord = "";
            for (int i = words.length - 1; i >= 0; i--) {
                if (maxLen > words.length) {
                    break;
                }
                String word = words[i];
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < word.length(); j++) {
                    sb.append(word.charAt(j));
                    if (map.get(sb.toString()) == null) {
                        //不存在子集，排除
                        break;
                    }
                }
                if (maxLen <= word.length() && sb.toString().equals(word)) {
                    maxLen = word.length();
                    maxWord = word;
                }
            }
            return maxWord;
        }
    }


    class Solution2_2 {
        public String longestWord(String[] words) {
            Arrays.sort(words, (a, b) ->  {
                if (a.length() != b.length()) {
                    return a.length() - b.length();
                } else {
                    return b.compareTo(a);
                }
            });
            String longest = "";
            Set<String> candidates = new HashSet<String>();
            candidates.add("");
            int n = words.length;
            for (int i = 0; i < n; i++) {
                String word = words[i];
                if (candidates.contains(word.substring(0, word.length() - 1))) {
                    candidates.add(word);
                    longest = word;
                }
            }
            return longest;
        }
    }

    class Solution2_3 {
        public String longestWord(String[] words) {
            Trie trie = new Trie();
            for (String word : words) {
                trie.insert(word);
            }
            String longest = "";
            for (String word : words) {
                if (trie.search(word)) {
                    if (word.length() > longest.length() || (word.length() == longest.length() && word.compareTo(longest) < 0)) {
                        longest = word;
                    }
                }
            }
            return longest;
        }

        class Trie {
            Trie[] children;
            boolean isEnd;
            char chaz;

            public Trie() {
                children = new Trie[26];
                isEnd = false;
            }

            public void insert(String word) {
                Trie node = this;
                for (int i = 0; i < word.length(); i++) {
                    char ch = word.charAt(i);
                    int index = ch - 'a';
                    if (node.children[index] == null) {
                        node.children[index] = new Trie();
                    }
                    node.chaz = ch;
                    node = node.children[index];
                }
                node.isEnd = true;
            }

            public boolean search(String word) {
                Trie node = this;
                for (int i = 0; i < word.length(); i++) {
                    char ch = word.charAt(i);
                    int index = ch - 'a';
                    if (node.children[index] == null || !node.children[index].isEnd) {
                        return false;
                    }
                    node = node.children[index];
                }
                return node != null && node.isEnd;
            }
        }
    }


    //两数之和 IV - 输入 BST
    @Test
    public void test3() {
        TreeNode treeNode1 = Utils.createTree(new Integer[]{
                5,3,6,2,4,null,7
        });
        Solution3 solution3 = new Solution3();
        Assert.assertEquals(true, solution3.findTarget(treeNode1,9));

        Assert.assertEquals(false, solution3.findTarget(treeNode1,28));
    }

    class Solution3 {
        Set<Integer> set = new HashSet();
        private boolean find = false;
        private int target;
        public boolean findTarget(TreeNode root, int k) {
            this.find = false;
            this.target = k;
            dfs(root);
            return find;
        }

        private void dfs(TreeNode root) {
            if (root == null || find) {
                return ;
            }
            int v = this.target-root.val;
            if (set.contains(v)) {
                find = true;
                return ;
            }
            set.add(root.val);
            dfs(root.left);
            dfs(root.right);
        }
    }

    //2038. 如果相邻两个颜色均相同则删除当前颜色
    @Test
    public void test4() {
        Solution4 solution4 = new Solution4();
        //三个同时的字符才能删除
        Assert.assertEquals(false,solution4.winnerOfGame("ABAAB"));
        Assert.assertEquals(true,solution4.winnerOfGame("AAABABB"));
    }
    //https://leetcode-cn.com/problems/remove-colored-pieces-if-both-neighbors-are-the-same-color/solution/ru-guo-xiang-lin-liang-ge-yan-se-jun-xia-rfbk/
    class Solution4 {
        public boolean winnerOfGame(String colors) {
            int[] freq = {0, 0};
            char cur = 'C';
            int cnt = 0;
            for (int i = 0; i < colors.length(); i++) {
                char c = colors.charAt(i);
                if (c != cur) {
                    cur = c;
                    cnt = 1;
                } else {
                    cnt += 1;
                    if (cnt >= 3) {
                        freq[cur - 'A'] += 1;
                    }
                }
            }
            return freq[0] > freq[1];
        }
    }

    //661. 图片平滑器
    @Test
    public void test5() {
        Solution5 solution5 = new Solution5();
        Assert.assertArrayEquals(new int[][]{
                {137,141,137},
                {141,138,141},
                {137,141,137}
        },solution5.imageSmoother(new int[][]{
                {100,200,100},
                {200,50,200},
                {100,200,100}
        }));
    }

    class Solution5 {
        public int[][] imageSmoother(int[][] img) {
            int m = img.length;
            int n = img[0].length;
            int[][] newImg =  new int[m][n];
            for (int i=0;i<m;i++) {
                for (int j=0;j<n;j++) {
                    int count = 0;
                    double sum = 0;
                    for (int z=-1;z<=1;z++) {
                        for (int x=-1;x<=1;x++) {
                            int newX = i+z;
                            int newY = j+x;
                            if (newX>=0 && newY>=0 && newX<m && newY<n) {
                                count++;
                                sum += img[newX][newY];
                            }
                        }
                    }
                    newImg[i][j] = (int) Math.floor(sum/count);
                }
            }
            return newImg;
        }
    }

    //2028. 找出缺失的观测数据
    @Test
    public void test6() {
        Solution6 solution6 = new Solution6();
        Assert.assertArrayEquals(new int[]{6,6},solution6.missingRolls(new int[]{
                3,2,4,3
        },4,2));

        Assert.assertArrayEquals(new int[]{},solution6.missingRolls(new int[]{
                1,2,3,4
        },6,4));
    }

    class Solution6 {
        public int[] missingRolls(int[] rolls, int mean, int n) {
            int m = rolls.length;
            int sum = (m+n)*mean;
            int existSum = 0;
            for (int i=0;i<rolls.length;i++) {
                existSum += rolls[i];
            }
            int missSum = sum-existSum;
            if (missSum>(6*n) || missSum<(n)) {
                return new int[]{};
            }
            int a = missSum/n;
            int b = missSum%n;
            int[] res = new int[n];
            for (int i=0;i<n;i++) {
                if ((b-i)>0) { //还有余数
                    res[i] = a+1;
                } else {
                    res[i] = a;
                }
            }
            return res;
        }
    }

    //2024. 考试的最大困扰度
    @Test
    public void test7(){
        Solution7 solution7 =  new Solution7();
        Assert.assertEquals(4,solution7.maxConsecutiveAnswers("TTFF", 2));
        Assert.assertEquals(3,solution7.maxConsecutiveAnswers("TFFT", 1));
        Assert.assertEquals(5,solution7.maxConsecutiveAnswers("TTFTTFTT", 1));
    }

    class Solution7 {
        public int maxConsecutiveAnswers(String answerKey, int k) {
            //考虑T最长的情况下
            int maxT = maxChar(answerKey,k,'T','F');
            int maxF = maxChar(answerKey,k,'F','T');
            return Math.max(maxT,maxF);
        }

        private int maxChar(String answerKey, int k, char target, char other) {
            int max = 0;
            int l=0,r=0;
            int len = answerKey.length();
            int num = 0;
            int remain = k;
            while (r<len && l<=r) {
                if (answerKey.charAt(r) == target) {
                    num++;
                    r++;
                    continue;
                }
                if (answerKey.charAt(r) == other && remain>0) {
                    num++;
                    remain--;
                    r++;
                    continue;
                }
                if (max<num) {
                    max = num;
                }
                if (answerKey.charAt(l) == other) {
                    remain++;
                }
                l++;
                num--;
            }
            if (max<num) {
                max = num;
            }
            return max;
        }

    }

    class Solution7_2 {
        public int maxConsecutiveAnswers(String answerKey, int k) {
            return Math.max(maxConsecutiveChar(answerKey, k, 'T'), maxConsecutiveChar(answerKey, k, 'F'));
        }

        public int maxConsecutiveChar(String answerKey, int k, char ch) {
            int n = answerKey.length();
            int ans = 0;
            for (int left = 0, right = 0, sum = 0; right < n; right++) {
                sum += answerKey.charAt(right) != ch ? 1 : 0;
                while (sum > k) {
                    sum -= answerKey.charAt(left++) != ch ? 1 : 0;
                }
                ans = Math.max(ans, right - left + 1);
            }
            return ans;
        }

    }

    //310. 最小高度树
    @Test
    public void test8() {
//        Assert.assertEquals("1", Joiner.on(",").join(new Solution8().findMinHeightTrees(4, new int[][]{
//                {1,0},
//                {1,2},
//                {1,3}
//        })));

        Assert.assertEquals("3,4",Joiner.on(",").join(new Solution8().findMinHeightTrees(6, new int[][]{
                {3,0},
                {3,1},
                {3,2},
                {3,4},
                {5,4}
        })));
    }
    //https://leetcode-cn.com/problems/minimum-height-trees/solution/zui-xiao-gao-du-shu-by-leetcode-solution-6v6f/ 拓扑排序
    class Solution8 {
        public List<Integer> findMinHeightTrees(int n, int[][] edges) {
            List<Integer> ans = new ArrayList<Integer>();
            if (n == 1) {
                ans.add(0);
                return ans;
            }
            int[] degree = new int[n];
            List<Integer>[] adj = new List[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<Integer>();
            }
            for (int[] edge : edges) {
                adj[edge[0]].add(edge[1]);
                adj[edge[1]].add(edge[0]);
                degree[edge[0]]++;
                degree[edge[1]]++;
            }
            Queue<Integer> queue = new ArrayDeque<Integer>();
            for (int i = 0; i < n; i++) {
                if (degree[i] == 1) {
                    queue.offer(i);
                }
            }
            int remainNodes = n;
            while (remainNodes > 2) {
                int sz = queue.size();
                remainNodes -= sz;
                for (int i = 0; i < sz; i++) {
                    int curr = queue.poll();
                    for (int v : adj[curr]) {
                        degree[v]--;
                        if (degree[v] == 1) {
                            queue.offer(v);
                        }
                    }
                }
            }
            while (!queue.isEmpty()) {
                ans.add(queue.poll());
            }
            return ans;
        }

    }

    //796. 旋转字符串
    @Test
    public void test9() {
        Solution9 solution9 = new Solution9();
//        Assert.assertEquals(true, solution9.rotateString("abcde", "cdeab"));
//        Assert.assertEquals(false, solution9.rotateString("abcde", "abced"));
            Assert.assertEquals(false, solution9.rotateString("bbbacddceeb", "ceebbbbacdd"));
    }

    class Solution9 {
        public boolean rotateString(String s, String goal) {
            if (s.length() != goal.length()) {
                return false;
            }
            int l1 = 0;
            char c = s.charAt(l1);
            int start = goal.indexOf(c);
            if (start == -1) {
                return false;
            }
            int l2 = start;
            int count = 1;
            while (l1<(s.length()-1) && l2!=-1) {
                l1++;
                start++;
                if (start>=goal.length()) {
                    start = 0;
                }
                char a= s.charAt(l1);
                char b = goal.charAt(start);

                if (a!=b) {
                    //复位重新找
                    l1 = 0;
                    start = goal.indexOf(c,l2+1);
                    l2 = start;
                    count = 1;
                    continue;
                }
                count++;
            }
            return count==s.length();
        }
    }

    class Solution9_2 {
        //子串
        public boolean rotateString(String s, String goal) {
            return s.length() == goal.length() && (s + s).contains(goal);
        }
    }

    //806. 写字符串需要的行数
    @Test
    public void test10() {
        Solution10 solution10 = new Solution10();
        Assert.assertArrayEquals(new int[]{2, 4},solution10.numberOfLines(new int[]{
                4,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10
        }, "bbbcccdddaaa"));

        Assert.assertArrayEquals(new int[]{3, 60},solution10.numberOfLines(new int[]{
                10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10
        }, "abcdefghijklmnopqrstuvwxyz"));
    }

    class Solution10 {
        public int[] numberOfLines(int[] widths, String s) {
            int[] res = new int[2];
            int maxWidth = 100;
            int row = 1;
            int sum = 0;
            for (int i=0; i<s.length(); i++) {
                int index = s.charAt(i) - 'a';
                int width = widths[index];
                if (sum+width > 100) {
                    row++;
                    sum = width;
                } else {
                    sum += width;
                }
            }
            res[0] = row;
            res[1] = sum;
            return res;
        }
    }


    //386. 字典序排数
    @Test
    public void test11() {
        Solution11 solution11 = new Solution11();
        Assert.assertEquals("1,10,11,12,13,2,3,4,5,6,7,8,9", Joiner.on(",").join(solution11.lexicalOrder(13)));
    }

    //https://leetcode-cn.com/problems/lexicographical-numbers/solution/by-ac_oier-ktn7/
    class Solution11 {
        private List<Integer> ans = new ArrayList<>();
        public List<Integer> lexicalOrder(int n) {
            for (int i=1; i<=9; i++) {
                dfs(i, n);
            }
            return ans;
        }

        private void dfs(int cur, int limit) {
            if (cur > limit )
                return ;
            ans.add(cur);
            for (int i=0; i<=9; i++)
                dfs(cur*10+i, limit);
        }
    }

    //821. 字符的最短距离
    @Test
    public void test12() {
        Solution12 solution2 = new Solution12();
        Assert.assertArrayEquals(new int[] {
                3,2,1,0,1,0,0,1,2,2,1,0
        }, solution2.shortestToChar("loveleetcode", 'e'));

        Assert.assertArrayEquals(new int[] {
                3,2,1,0
        }, solution2.shortestToChar("aaab", 'b'));
    }

    class Solution12 {
        public int[] shortestToChar(String s, char c) {
            TreeSet<Integer> set = new TreeSet<>();
            for (int i=0; i<s.length(); i++) {
                if (s.charAt(i) == c) {
                    set.add(i);
                }
            }
            int[] res = new int[s.length()];
            for (int i=0; i<s.length(); i++) {
                int big = (set.floor(i)==null?Integer.MAX_VALUE:set.floor(i));
                int small = (set.ceiling(i)==null?Integer.MAX_VALUE:set.ceiling(i));
                int v = Math.min(Math.abs(i-small),Math.abs(i-big));
                res[i] = v;
            }
            return res;
        }
    }

    //388. 文件的最长绝对路径
    @Test
    public void test13() {
        Solution13 solution13 = new Solution13();
        Assert.assertEquals(20, solution13.lengthLongestPath("dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"));
    }

    class Solution13 {
        public int lengthLongestPath(String input) {
            int n = input.length();
            int pos = 0;
            int ans = 0;
            Deque<Integer> stack = new ArrayDeque<Integer>();

            while (pos < n) {
                /* 检测当前文件的深度 */
                int depth = 1;
                while (pos < n && input.charAt(pos) == '\t') {
                    pos++;
                    depth++;
                }
                /* 统计当前文件名的长度 */
                boolean isFile = false;
                int len = 0;
                while (pos < n && input.charAt(pos) != '\n') {
                    if (input.charAt(pos) == '.') {
                        isFile = true;
                    }
                    len++;
                    pos++;
                }
                /* 跳过当前的换行符 */
                pos++;

                while (stack.size() >= depth) {
                    stack.pop();
                }
                if (!stack.isEmpty()) {
                    len += stack.peek() + 1;
                }
                if (isFile) {
                    ans = Math.max(ans, len);
                } else {
                    stack.push(len);
                }
            }
            return ans;
        }
    }

    //398. 随机数索引
    @Test
    public void test14() {
        Solution14 solution14 = new Solution14(new int[] {
                1, 2, 3, 3, 3
        });
        solution14.pick(3); // 随机返回索引 2, 3 或者 4 之一。每个索引的返回概率应该相等。
        solution14.pick(1); // 返回 0 。因为只有 nums[0] 等于 1 。
        solution14.pick(3); // 随机返回索引 2, 3 或者 4 之一。每个索引的返回概率应该相等。
    }

    class Solution14 {
        Map<Integer,List<Integer>> map = new HashMap<>();
        Random random = new Random();
        public Solution14(int[] nums) {
            for (int  i=0; i<nums.length; i++) {
                List<Integer> list = map.getOrDefault(nums[i], new ArrayList<>());
                list.add(i);
                map.put(nums[i], list);
            }
        }

        public int pick(int target) {
            List<Integer> list = map.get(target);
            if (list.size() == 1) {
                return list.get(0);
            }
            return list.get(random.nextInt(list.size()));
        }
    }

    //933. 最近的请求次数
    @Test
    public void test15() {
        RecentCounter recentCounter = new RecentCounter();
        Assert.assertEquals(1,recentCounter.ping(1)) ;     // requests = [1]，范围是 [-2999,1]，返回 1
        Assert.assertEquals(2,recentCounter.ping(100));   // requests = [1, 100]，范围是 [-2900,100]，返回 2
        Assert.assertEquals(3,recentCounter.ping(3001));  // requests = [1, 100, 3001]，范围是 [1,3001]，返回 3
        Assert.assertEquals(3,recentCounter.ping(3002));  // requests = [1, 100, 3001, 3002]，范围是 [2,3002]，返回 3

    }

    class RecentCounter {
        private Queue<Integer> queue;
        public RecentCounter() {
            queue = new ArrayDeque<>();
        }

        public int ping(int t) {
            queue.offer(t);
            while (queue.peek() < t-3000) {
                queue.poll();
            }
            return queue.size();
        }
    }

    //942. 增减字符串匹配
    @Test
    public void test16() {
        Solution16 solution16 = new Solution16();
        Assert.assertArrayEquals(new int[]{
                0,4,1,3,2
        }, solution16.diStringMatch("IDID"));
    }

    class Solution16 {
        public int[] diStringMatch(String s) {
            int min = 0;
            int max = s.length();
            int[] res = new int[s.length()+1];
            for (int i=0; i<s.length(); i++) {
                if (s.charAt(i) == 'I') {
                    res[i] = min;
                    min++;
                }
                if (s.charAt(i) == 'D') {
                    res[i] = max;
                    max--;
                }
            }
            res[s.length()] = min;
            return res;
        }
    }

    //449. 序列化和反序列化二叉搜索树
    @Test
    public void test17() {
        Codec codec = new Codec();

    }


    public class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return "";
            }
            StringBuffer sb = new StringBuffer();
            Queue<TreeNode> q = new LinkedList();
            q.add(root);
            while (!q.isEmpty()) {
                TreeNode temp = q.poll();
                if (temp == null) {
                    sb.append("null,");
                    continue;
                } else {
                    sb.append(temp.val + ",");
                }
                if (temp.left != null) {
                    q.offer(temp.left); // 迭代操作，向左探索
                } else {
                    q.offer(null);
                }
                if (temp.right != null) {
                    q.offer(temp.right);
                } else {
                    q.offer(null);
                }
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            String str = data;
            String[] arr = str.split(",");
            return createTree(arr, 0);
        }

        private TreeNode createTree(String[] arr, int index) {
            Queue<String> q = new LinkedList<String>(Arrays.asList(arr));
            if (q.peek() == null) {
                return null;
            }
            return rdeserialize(q);
        }

        private TreeNode rdeserialize(Queue<String> q) {
            if (q.peek() == null || q.peek().equals("")) {
                q.poll();
                return null;
            }
            TreeNode root = new TreeNode(Integer.valueOf(q.poll()));
            Queue<TreeNode> qTree = new LinkedList<TreeNode>();
            TreeNode node = root;
            while (!q.isEmpty()) {
                if (q.peek() != null && !q.peek().equals("null")) {
                    node.left = new TreeNode(Integer.valueOf(q.poll()));
                    qTree.offer(node.left);
                } else {
                    q.poll();
                }
                if (q.peek() != null && !q.peek().equals("null")) {
                    node.right = new TreeNode(Integer.valueOf(q.poll()));
                    qTree.offer(node.right);
                } else {
                    q.poll();
                }
                node = qTree.poll();
            }
            return root;

        }
    }

    //944. 删列造序
    @Test
    public void test18() {
        Solution18 solution18 = new Solution18();
//        Assert.assertEquals(1, solution18.minDeletionSize(new String[]{
//                "cba","daf","ghi"
//        }));
        Assert.assertEquals(2, solution18.minDeletionSize(new String[]{
                "rrjk","furt","guzm"
        }));
    }

    class Solution18 {
        public int minDeletionSize(String[] strs) {
            int num = 0;
            int row = strs.length;
            int col = strs[0].length();
            for (int i=0; i<col; i++) {
                boolean increase = true;
                char pre = strs[0].charAt(i);
                for (int j=1; j<row; j++) {
                    if (strs[j].charAt(i) < pre) {
                        increase = false;
                        break;
                    }
                    pre = strs[j].charAt(i);
                }
                if (!increase) {
                    num++;
                }
            }

            return num;
        }
    }

    //462. 最少移动次数使数组元素相等 II
    @Test
    public void test19() {
        Solution19  solution19 = new Solution19();
        Assert.assertEquals(2, solution19.minMoves2(new int[] {
                1,2,3
        }));
    }
    //【负雪明烛】图解算法：为什么选择中位数
    class Solution19 {
        public int minMoves2(int[] nums) {
            Arrays.sort(nums);
            int n = nums.length;
            int mid = nums[n/2];
            int res = 0;
            for (int num: nums) {
                res += Math.abs(num-mid);
            }
            return res;
        }
    }
}
