package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode16 {
	// 岛屿的周长
	@Test
	public void test1() {
		Assert.assertEquals(16,
				islandPerimeter(new int[][] { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 1, 0, 0 }, { 1, 1, 0, 0 } }));
	}

	public int islandPerimeter(int[][] grid) {
		int res = 0;
		int[][] dict = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 1) {
					int count = 4;
					for (int z = 0; z < dict.length; z++) {
						int row = i + dict[z][0];
						int col = j + dict[z][1];
						if (row >= 0 && row < grid.length && col >= 0 && col < grid[i].length) {
							if (grid[row][col] == 1) {
								count--;
							}
						}
					}
					res += count;
				}
			}
		}
		return res;
	}

	// O(1) 时间插入、删除和获取随机元素 - 允许重复
	@Test
	public void test2() {
		// 初始化一个空的集合。
		RandomizedCollection2 collection = new RandomizedCollection2();

		// 向集合中插入 1 。返回 true 表示集合不包含 1 。
		collection.insert(1);

		// 向集合中插入另一个 1 。返回 false 表示集合包含 1 。集合现在包含 [1,1] 。
		collection.insert(1);

		// 向集合中插入 2 ，返回 true 。集合现在包含 [1,1,2] 。
		collection.insert(2);

		// getRandom 应当有 2/3 的概率返回 1 ，1/3 的概率返回 2 。
		collection.getRandom();

		// 从集合中删除 1 ，返回 true 。集合现在包含 [1,2] 。
		collection.remove(1);

		// getRandom 应有相同概率返回 1 和 2 。
		collection.getRandom();
	}

	// https://leetcode-cn.com/problems/insert-delete-getrandom-o1-duplicates-allowed/solution/o1-shi-jian-cha-ru-shan-chu-he-huo-qu-sui-ji-yua-5/
	class RandomizedCollection2 {
		Map<Integer, Set<Integer>> idx;
		List<Integer> nums;

		/** Initialize your data structure here. */
		public RandomizedCollection2() {
			idx = new HashMap<Integer, Set<Integer>>();
			nums = new ArrayList<Integer>();
		}

		/**
		 * Inserts a value to the collection. Returns true if the collection did not
		 * already contain the specified element.
		 */
		public boolean insert(int val) {
			nums.add(val);
			Set<Integer> set = idx.getOrDefault(val, new HashSet<Integer>());
			// 记录当前值的下标
			set.add(nums.size() - 1);
			idx.put(val, set);
			return set.size() == 1;
		}

		/**
		 * Removes a value from the collection. Returns true if the collection contained
		 * the specified element.
		 */
		public boolean remove(int val) {
			if (!idx.containsKey(val)) {
				return false;
			}
			Iterator<Integer> it = idx.get(val).iterator();
			int i = it.next();
			int lastNum = nums.get(nums.size() - 1);
			nums.set(i, lastNum);
			idx.get(val).remove(i);
			idx.get(lastNum).remove(nums.size() - 1);
			if (i < nums.size() - 1) {
				idx.get(lastNum).add(i);
			}
			if (idx.get(val).size() == 0) {
				idx.remove(val);
			}
			nums.remove(nums.size() - 1);
			return true;
		}

		/** Get a random element from the collection. */
		public int getRandom() {
			return nums.get((int) (Math.random() * nums.size()));
		}
	}

	class RandomizedCollection {
		private Map<Integer, Integer> map = new HashMap();
		private int size = 0;

		/** Initialize your data structure here. */
		public RandomizedCollection() {

		}

		/**
		 * Inserts a value to the collection. Returns true if the collection did not
		 * already contain the specified element.
		 */
		public boolean insert(int val) {
			boolean flag = false;
			int v = map.getOrDefault(val, 0);
			if (v == 0) {
				flag = true;
			}
			map.put(val, ++v);
			size++;
			return flag;
		}

		/**
		 * Removes a value from the collection. Returns true if the collection contained
		 * the specified element.
		 */
		public boolean remove(int val) {
			if (map.get(val) == null) {
				return false;
			}
			int v = map.get(val);
			--v;
			if (v == 0) {
				map.remove(val);
			} else {
				map.put(val, v);
			}
			size--;
			return true;
		}

		/** Get a random element from the collection. */
		public int getRandom() {
			int v = new Random().nextInt(size) + 1;
			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				if (entry.getValue() - v >= 0) {
					return entry.getKey();
				}
				v = v - entry.getValue();
			}
			return 0;
		}
	}

	// 有效的山脉数组
	@Test
	public void test3() {
		Assert.assertEquals(true, validMountainArray(new int[] { 0, 3, 2, 1 }));
		Assert.assertEquals(false, validMountainArray(new int[] { 3, 5, 5 }));
		Assert.assertEquals(false, validMountainArray(new int[] { 1, 2, 3, 4, 5 }));
		Assert.assertEquals(false, validMountainArray(new int[] { 5, 4, 3, 2, 1 }));
		Assert.assertEquals(false, validMountainArray(new int[] { 1, 2, 3, 3, 2, 1 }));
	}

	public boolean validMountainArray(int[] A) {
		if (A == null || A.length < 3) {
			return false;
		}
		int index = 1;
		while (index < A.length) {
			if (A[index] < A[index - 1]) {
				index = index - 1;
				break;
			} else if (A[index] > A[index - 1]) {
				index++;
			} else {
				return false;
			}
		}
		if (index == 0 || index >= A.length) {
			return false;
		}
		while (index < A.length - 1) {
			if (A[index] > A[index + 1]) {
				index++;
			} else if (A[index] < A[index - 1]) {
				return false;
			}
		}
		return true;
	}

	// 每日温度
	@Test
	public void test4() {
		Assert.assertArrayEquals(new int[] { 1, 1, 4, 2, 1, 1, 0, 0 },
				dailyTemperatures(new int[] { 73, 74, 75, 71, 69, 72, 76, 73 }));
	}

	public int[] dailyTemperatures(int[] T) {
		int[] res = new int[T.length];
		int index = res.length - 1;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = T.length - 1; i >= 0; i--) {
			if (map.size() == 0) {
				res[index--] = 0;
			} else {
				int pos = Integer.MAX_VALUE;
				for (Integer key : map.keySet()) {
					if (key > T[i]) {
						pos = Math.min(pos, map.get(key) - i);
					}
				}
				res[index--] = pos == Integer.MAX_VALUE ? 0 : pos;
			}
			int v = map.getOrDefault(T[i], Integer.MAX_VALUE);
			if (v > i) {
				map.put(T[i], i);
			}
		}
		return res;
	}

	// 二叉搜索树中第K小的元素
	@Test
	public void test5() {
		Assert.assertEquals(1, new Solution5().kthSmallest(Utils.createTree(new Integer[] { 3, 1, 4, null, 2 }), 1));
	}

	class Solution5 {
		public int kthSmallest(TreeNode root, int k) {
			List<Integer> res = new ArrayList<Integer>();
			dfs(root, res);
			return res.get(k - 1);
		}

		private void dfs(TreeNode root, List<Integer> res) {
			if (root.left != null) {
				dfs(root.left, res);
			}
			res.add(root.val);
			if (root.right != null) {
				dfs(root.right, res);
			}
		}
	}

	// 二叉树的锯齿形层次遍历
	@Test
	public void test6() {
		List<List<Integer>> res = new Solution6()
				.zigzagLevelOrder(Utils.createTree(new Integer[] { 1, 2, 3, 4, null, null, 5 }));
		for (List<Integer> r : res) {
			System.out.println(Joiner.on(",").join(r));
		}
	}

	class Solution6 {
		public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
			List<List<Integer>> res = new ArrayList<List<Integer>>();
			Deque<TreeNode> queue = new LinkedList<TreeNode>();
			if (root == null) {
				return res;
			}
			queue.offer(root);
			int level = 0;
			while (!queue.isEmpty()) {
				level++;
				int len = queue.size();
				List<Integer> list = new ArrayList<Integer>();
				Deque<TreeNode> stack = new LinkedList<TreeNode>();
				for (int i = 0; i < len; i++) {
					TreeNode node = queue.poll();
					list.add(node.val);
					stack.push(node);

				}
				while (!stack.isEmpty()) {
					TreeNode node = stack.pop();
					if (level % 2 == 0) {
						if (node.left != null) {
							queue.offer(node.left);
						}
						if (node.right != null) {
							queue.offer(node.right);
						}
					} else {
						if (node.right != null) {
							queue.offer(node.right);
						}
						if (node.left != null) {
							queue.offer(node.left);
						}
					}
				}
				res.add(list);
			}

			return res;
		}
	}

	// 单词接龙
	@Test
	public void test7() {
		Assert.assertEquals(5,
				new Solution7().ladderLength("hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log", "cog")));
	}

	class Solution7 {
		class Pair<K, V> {
			private K k;
			private V v;

			public Pair(K k, V v) {
				this.k = k;
				this.v = v;
			}

			public K getKey() {
				return k;
			}

			public V getValue() {
				return v;
			}

		}

		// https://leetcode-cn.com/problems/word-ladder/solution/dan-ci-jie-long-by-leetcode/
		public int ladderLength(String beginWord, String endWord, List<String> wordList) {
			// Since all words are of same length.
			int L = beginWord.length();

			// Dictionary to hold combination of words that can be formed,
			// from any given word. By changing one letter at a time.
			Map<String, List<String>> allComboDict = new HashMap<>();

			wordList.forEach(word -> {
				for (int i = 0; i < L; i++) {
					// Key is the generic word
					// Value is a list of words which have the same intermediate generic word.
					String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);
					List<String> transformations = allComboDict.getOrDefault(newWord, new ArrayList<>());
					transformations.add(word);
					allComboDict.put(newWord, transformations);
				}
			});
			// Queue for BFS
			Queue<Pair<String, Integer>> Q = new LinkedList<>();
			Q.add(new Pair(beginWord, 1));

			// Visited to make sure we don't repeat processing same word.
			Map<String, Boolean> visited = new HashMap<>();
			visited.put(beginWord, true);

			while (!Q.isEmpty()) {
				Pair<String, Integer> node = Q.remove();
				String word = node.getKey();
				int level = node.getValue();
				for (int i = 0; i < L; i++) {

					// Intermediate words for current word
					String newWord = word.substring(0, i) + '*' + word.substring(i + 1, L);

					// Next states are all the words which share the same intermediate state.
					for (String adjacentWord : allComboDict.getOrDefault(newWord, new ArrayList<>())) {
						// If at any point if we find what we are looking for
						// i.e. the end word - we can return with the answer.
						if (adjacentWord.equals(endWord)) {
							return level + 1;
						}
						// Otherwise, add it to the BFS Queue. Also mark it visited
						if (!visited.containsKey(adjacentWord)) {
							visited.put(adjacentWord, true);
							Q.add(new Pair(adjacentWord, level + 1));
						}
					}
				}
			}

			return 0;
		}
	}

	// 加油站
	@Test
	public void test8() {
		Assert.assertEquals(3, canCompleteCircuit(new int[] { 1, 2, 3, 4, 5 }, new int[] { 3, 4, 5, 1, 2 }));
		Assert.assertEquals(-1, canCompleteCircuit(new int[] { 2, 3, 4 }, new int[] { 3, 4, 3 }));
		Assert.assertEquals(4, canCompleteCircuit(new int[] { 5, 1, 2, 3, 4 }, new int[] { 4, 4, 1, 5, 1 }));
	}

	public int canCompleteCircuit(int[] gas, int[] cost) {
		int[] sum = new int[gas.length];
		// 前缀和
		for (int i = 0; i < gas.length; i++) {
			if (i > 0) {
				sum[i] = sum[i - 1] + (gas[i] - cost[i]);
			} else {
				sum[i] = (gas[i] - cost[i]);
			}
		}
		int len = gas.length;
		for (int i = 0; i < gas.length; i++) {
			if (gas[i] - cost[i] >= 0) {
				int j = i;
				j++;
				while (j % len != i) {
					int diffI = gas[i] - cost[i];
					if (j < len) {
						// i-j差异和
						if (sum[j] - sum[i] + diffI < 0) {
							break;
						}
					} else {
						// i到len的差异和+0,j的差异和
						if (sum[len - 1] - sum[i] + diffI + sum[j % len] < 0) {
							break;
						}
					}
					j++;
				}
				if (j % len == i) {
					return i;
				}
			}
		}
		return -1;
	}

	// 复制带随机指针的链表
	@Test
	public void test9() {
		Node n = new Node(7);
		Node n2 = new Node(13);
		Node n3 = new Node(11);
		Node n4 = new Node(10);
		Node n5 = new Node(1);

		n.next = n2;
		n.random = null;

		n2.next = n3;
		n2.random = n;

		n3.next = n4;
		n3.random = n4;

		n4.next = n5;
		n4.random = n2;

		n5.random = n;

		Node copyN = new Solution9().copyRandomList(n);
		while (copyN != null) {
			int ran = copyN.random == null ? 0 : copyN.random.val;
			System.out.println(copyN.val + " " + ran);
			copyN = copyN.next;
		}
	}

	static class Node {
		int val;
		Node next;
		Node random;

		public Node(int val) {
			this.val = val;
			this.next = null;
			this.random = null;
		}
	}

	class Solution9 {

		public Node copyRandomList(Node head) {
			if (head == null) {
				return null;
			}
			Map<Node, Node> map = new HashMap<Node, Node>();
			Node tail = new Node(-1);
			Node newHead = copyNode(head);
			Node tmp = head;
			tail.next = newHead;
			while (head != null) {

				map.put(head, newHead);
				newHead.next = copyNode(head.next);
				head = head.next;
				newHead = newHead.next;
			}
			newHead = tail.next;
			while (tmp != null) {
				if (tmp.random != null) {
					newHead.random = map.get(tmp.random);
				}
				tmp = tmp.next;
				newHead = newHead.next;
			}

			return tail.next;
		}

		private Node copyNode(Node head) {
			if (head == null) {
				return null;
			}
			Node n = new Node(head.val);
			return n;
		}
	}

	// 直线上最多的点数
	@Test
	public void test10() {
		Assert.assertEquals(3, new Solution10().maxPoints(new int[][] { { 1, 1 }, { 2, 2 }, { 3, 3 } }));
	}

	// https://leetcode-cn.com/problems/max-points-on-a-line/solution/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by--35/
	class Solution10 {
		public int maxPoints(int[][] points) {
			if (points.length < 3) {
				return points.length;
			}
			int res = 0;
			// 遍历每个点
			for (int i = 0; i < points.length; i++) {
				int duplicate = 0;
				int max = 0;// 保存经过当前点的直线中，最多的点
				HashMap<String, Integer> map = new HashMap<>();
				for (int j = i + 1; j < points.length; j++) {
					// 求出分子分母
					int x = points[j][0] - points[i][0];
					int y = points[j][1] - points[i][1];
					if (x == 0 && y == 0) {
						duplicate++;
						continue;

					}
					// 进行约分
					int gcd = gcd(x, y);
					x = x / gcd;
					y = y / gcd;
					String key = x + "@" + y;
					map.put(key, map.getOrDefault(key, 0) + 1);
					max = Math.max(max, map.get(key));
				}
				// 1 代表当前考虑的点，duplicate 代表和当前的点重复的点
				res = Math.max(res, max + duplicate + 1);
			}
			return res;
		}

		private int gcd(int a, int b) {
			while (b != 0) {
				int temp = a % b;
				a = b;
				b = temp;
			}
			return a;
		}

	}

	// 逆波兰表达式求值
	@Test
	public void test11() {
		Assert.assertEquals(9, evalRPN(new String[] { "2", "1", "+", "3", "*" }));

		Assert.assertEquals(6, evalRPN(new String[] { "4", "13", "5", "/", "+" }));

		Assert.assertEquals(22,
				evalRPN(new String[] { "10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+" }));
	}

	public int evalRPN(String[] tokens) {
		Deque<Integer> stack = new LinkedList<Integer>();
		int res = 0;
		for (int i = 0; i < tokens.length; i++) {
			switch (tokens[i]) {
			case "+":
				int a = stack.pop();
				int b = stack.pop();
				int v = b + a;
				stack.push(v);
				break;
			case "-":
				a = stack.pop();
				b = stack.pop();
				v = b - a;
				stack.push(v);
				break;
			case "/":
				a = stack.pop();
				b = stack.pop();
				v = b / a;
				stack.push(v);
				break;
			case "*":
				a = stack.pop();
				b = stack.pop();
				v = b * a;
				stack.push(v);
				break;
			default:
				stack.push(Integer.valueOf(tokens[i]));
				break;
			}
		}
		if (!stack.isEmpty())
			res = stack.pop();
		return res;
	}

	// 寻找峰值
	@Test
	public void test12() {
		Assert.assertEquals(5, findPeakElement(new int[] { 1, 2, 1, 3, 5, 6, 4 }));
	}

	public int findPeakElement(int[] nums) {
		int l = 0, r = nums.length - 1;
		while (l < r) {
			int mid = (l + r) / 2;
			if (nums[mid] > nums[mid + 1])
				r = mid;
			else
				l = mid + 1;
		}
		return l;

	}

	// 根据数字二进制下 1 的数目排序
	@Test
	public void test13() {
		Assert.assertArrayEquals(new int[] { 0, 1, 2, 4, 8, 3, 5, 6, 7 },
				new Solution13().sortByBits(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }));
	}
	class Solution13 {
		
		public int[] sortByBits(int[] arr) {
			List<Integer> list = new ArrayList<Integer>();
			for (int i=0;i<arr.length;i++) {
				list.add(arr[i]);
			}
			Collections.sort(list, new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					if (getBinaryOneNum(o1)>getBinaryOneNum(o2)) {
						return 1;
					}
					if (getBinaryOneNum(o1)<getBinaryOneNum(o2)) {
						return -1;
					}
					return o1-o2;
				}
			});
			return list.stream().mapToInt(Integer::valueOf).toArray();
		}
		private int getBinaryOneNum(Integer v) {
			String str = Integer.toBinaryString(v);
			int num = 0;
			for (int i=0;i<str.length();i++) {
				if (str.charAt(i) == '1') {
					num++;
				}
			}
			return num;
		}
	}
	
	//区间和的个数
	@Test
	public void test14() {
		Assert.assertEquals(3, new Solution14().countRangeSum(new int[] {
				-2,5,-1
		}, -2, 2));
	}
	//https://leetcode-cn.com/problems/count-of-range-sum/solution/qu-jian-he-de-ge-shu-by-leetcode-solution/
	class Solution14{
		public int countRangeSum(int[] nums, int lower, int upper) {
	        long sum = 0;
	        long[] preSum = new long[nums.length + 1];
	        for (int i = 0; i < nums.length; ++i) {
	            sum += nums[i];
	            preSum[i + 1] = sum;
	        }
	        
	        Set<Long> allNumbers = new TreeSet<Long>();
	        for (long x : preSum) {
	            allNumbers.add(x);
	            allNumbers.add(x - lower);
	            allNumbers.add(x - upper);
	        }
	        // 利用哈希表进行离散化
	        Map<Long, Integer> values = new HashMap<Long, Integer>();
	        int idx = 0;
	        for (long x : allNumbers) {
	            values.put(x, idx);
	            idx++;
	        }

	        SegNode root = build(0, values.size() - 1);
	        int ret = 0;
	        for (long x : preSum) {
	            int left = values.get(x - upper), right = values.get(x - lower);
	            ret += count(root, left, right);
	            insert(root, values.get(x));
	        }
	        return ret;
	    }

	    public SegNode build(int left, int right) {
	        SegNode node = new SegNode(left, right);
	        if (left == right) {
	            return node;
	        }
	        int mid = (left + right) / 2;
	        node.lchild = build(left, mid);
	        node.rchild = build(mid + 1, right);
	        return node;
	    }

	    public int count(SegNode root, int left, int right) {
	        if (left > root.hi || right < root.lo) {
	            return 0;
	        }
	        if (left <= root.lo && root.hi <= right) {
	            return root.add;
	        }
	        return count(root.lchild, left, right) + count(root.rchild, left, right);
	    }

	    public void insert(SegNode root, int val) {
	        root.add++;
	        if (root.lo == root.hi) {
	            return;
	        }
	        int mid = (root.lo + root.hi) / 2;
	        if (val <= mid) {
	            insert(root.lchild, val);
	        } else {
	            insert(root.rchild, val);
	        }
	    }
	}

	class SegNode {
	    int lo, hi, add;
	    SegNode lchild, rchild;

	    public SegNode(int left, int right) {
	        lo = left;
	        hi = right;
	        add = 0;
	        lchild = null;
	        rchild = null;
	    }
	}
	
	//分数到小数
	@Test
	public void test15() {
		Assert.assertEquals("0.(6)", fractionToDecimal(2,3));
		Assert.assertEquals("2", fractionToDecimal(2,1));
		Assert.assertEquals("0.(012)", fractionToDecimal(4,333));
		Assert.assertEquals("0.2", fractionToDecimal(1,5));
		Assert.assertEquals("-6.25", fractionToDecimal(-50,8));
		Assert.assertEquals("11", fractionToDecimal(-22,-2));
	}
	public String fractionToDecimal(int numerator, int denominator) {
		if (numerator==0) {
			return "0";
		}
		boolean flag = true; //正为true,父为false;
		int v = numerator^denominator;
		if (v<0) { //符号不同
			flag = false;
		}
		long dividend =  Math.abs(Long.valueOf(numerator)); //防止除以最小的情况，扩大位数
		long divisor =  Math.abs(Long.valueOf(denominator));
		StringBuilder sb = new StringBuilder();
		if (dividend<divisor) {
			sb.append("0.");
		}else {
			while (dividend>=divisor) {
				sb.append(dividend/divisor);
				dividend = dividend%divisor;
			}
			if (dividend!=0 && sb.length()>0) {
				sb.append(".");
			} 
		}
		Map<Long,Integer> map = new HashMap<Long,Integer>();
		while (dividend!=0) {
			dividend = dividend*10;
			if (map.get(dividend)!=null) {
				int index = map.get(dividend);
				sb.insert(index-1, '(');
				sb.append(")");
				break;
			}
			sb.append(dividend/divisor);
			map.put(dividend, sb.length());
			dividend = dividend%divisor;
		}
		if (!flag) {
			sb.insert(0,"-");
		}
		return sb.toString();
    }
}
