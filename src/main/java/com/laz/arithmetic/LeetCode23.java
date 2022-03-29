package com.laz.arithmetic;

import org.junit.Assert;
import org.junit.Test;

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
}
