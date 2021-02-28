package com.laz.arithmetic;

import java.io.IOException;
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
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode17 {
	// 按奇偶排序数组 II
	@Test
	public void test1() {
		Assert.assertArrayEquals(new int[] { 4, 5, 2, 7 }, sortArrayByParityII(new int[] { 4, 2, 5, 7 }));
	}

	public int[] sortArrayByParityII(int[] A) {
		int[] res = new int[A.length];
		int p = 0, q = 1;
		for (int i = 0; i < A.length; i++) {
			if (A[i] % 2 == 0) {
				res[p] = A[i];
				p = p + 2;
			} else {
				res[q] = A[i];
				q = q + 2;
			}
		}
		return res;
	}

	// 移掉K位数字
	@Test
	public void test2() {
		Assert.assertEquals("1219", removeKdigits("1432219", 3));
	}

	// https://leetcode-cn.com/problems/remove-k-digits/solution/yi-zhao-chi-bian-li-kou-si-dao-ti-ma-ma-zai-ye-b-5/
	public String removeKdigits(String num, int k) {
		Deque<Character> stack = new LinkedList<Character>();
		for (int i = 0; i < num.length(); i++) {
			char c = num.charAt(i);
			while (!stack.isEmpty() && k > 0 && stack.peekLast() > c) {
				stack.pollLast();
				k--;
			}
			stack.offerLast(c);
		}
		// 如果K还没有减完，继续移除
		for (int i = 0; i < k; ++i) {
			stack.pollLast();
		}

		StringBuilder ret = new StringBuilder();
		boolean leadingZero = true;
		while (!stack.isEmpty()) {
			char digit = stack.pollFirst();
			if (leadingZero && digit == '0') {
				continue;
			}
			leadingZero = false;
			ret.append(digit);
		}
		return ret.length() == 0 ? "0" : ret.toString();

	}

	// 距离顺序排列矩阵单元格
	@Test
	public void test3() {
		Assert.assertArrayEquals(new int[][] { { 0, 1 }, { 0, 0 }, { 1, 1 }, { 1, 0 } }, allCellsDistOrder(2, 2, 0, 1));
	}

	public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
		List<int[]> list = new ArrayList<int[]>();
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				list.add(new int[] { i, j });
			}
		}
		Collections.sort(list, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int distance1 = Math.abs(o1[0] - r0) + Math.abs(o1[1] - c0);
				int distance2 = Math.abs(o2[0] - r0) + Math.abs(o2[1] - c0);
				return distance1 - distance2;
			}
		});
		int[][] res = new int[R * C][2];
		return list.toArray(res);
	}

	// 单词搜索 II
	@Test
	public void test4() {
		{
			List<String> list = new Solution4().findWords(new char[][] { { 'o', 'a', 'a', 'n' }, { 'e', 't', 'a', 'e' },
					{ 'i', 'h', 'k', 'r' }, { 'i', 'f', 'l', 'v' } }, new String[] { "oath", "pea", "eat", "rain" });
			Assert.assertArrayEquals(new String[] { "oath", "eat" }, list.toArray(new String[] {}));
		}
		{
			List<String> list = new Solution4().findWords(new char[][] { { 'a' }, { 'a' } }, new String[] { "aaa" });
			Assert.assertArrayEquals(new String[] {}, list.toArray(new String[] {}));
		}

	}

	class Solution4_2 {

		private Set<String> result = new HashSet<>();

		public List<String> findWords(char[][] board, String[] words) {
			Trie trie = new Trie();
			for (String word : words) {
				trie.insert(word);
			}
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					dfs(trie, board, new StringBuilder(), i, j, new int[board.length][board[0].length]);
				}
			}
			return new ArrayList<>(result);
		}

		private void dfs(Trie parent, char[][] board, StringBuilder prefix, int i, int j, int[][] used) {
			if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || used[i][j] == 1) {
				return;
			}
			Trie trie = parent.indexOf(board[i][j]);
			if (trie == null) {
				return;
			}
			used[i][j] = 1;
			prefix.append(board[i][j]);
			if (trie.isEnd()) {
				result.add(prefix.toString());
			}
			dfs(trie, board, prefix, i - 1, j, used);
			dfs(trie, board, prefix, i + 1, j, used);
			dfs(trie, board, prefix, i, j - 1, used);
			dfs(trie, board, prefix, i, j + 1, used);
			used[i][j] = 0;
			prefix.deleteCharAt(prefix.length() - 1);
		}

		class Trie {
			private Trie[] nexts;

			private boolean isEnd;

			public Trie() {
				nexts = new Trie[26];
				isEnd = false;
			}

			public void insert(String word) {
				Trie temp = this;
				for (int i = 0; i < word.length(); i++) {
					temp = temp.put(word.charAt(i));
				}
				temp.isEnd(true);
			}

			public Trie put(Character c) {
				if (nexts[c - 'a'] == null) {
					nexts[c - 'a'] = new Trie();
				}
				return nexts[c - 'a'];
			}

			private Trie indexOf(char c) {
				return nexts[c - 'a'];
			}

			public Trie indexOf(String prefix) {
				Trie temp = this;
				for (int i = 0; i < prefix.length(); i++) {
					if (temp == null) {
						return null;
					}
					temp = temp.indexOf(prefix.charAt(i));
				}
				return temp;
			}

			public void isEnd(boolean isEnd) {
				this.isEnd = isEnd;
			}

			public boolean isEnd() {
				return this.isEnd;
			}
		}
	}

	// 前缀树+剪枝版本
	class Solution4 {
		private int rows, cols;
		private char[][] board;
		// Trie的根节点
		private Node root;
		// DFS遍历方向
		private int[][] direct = { { 0, 1, 0, -1 }, { -1, 0, 1, 0 } };
		private Set<String> ans;
		// 标记是否已经访问
		private boolean[][] marked;

		// Trie需要的节点
		class Node {
			HashMap<Character, Node> children;
			String word;

			public Node(String word) {
				this.children = new HashMap<>();
				this.word = word;
			}

			public Node() {
				this.children = new HashMap<>();
				this.word = null;
			}
		}

		// Trie的操作
		private void delete(String str) {
			delete(root, str, 0);
		}

		private Node delete(Node current, String str, int d) {
			if (current == null)
				return null;
			if (d == str.length()) {
				if (current.children.isEmpty())
					return null;
				current.word = null;
				return current;
			}
			char c = str.charAt(d);
			Node next = delete(current.children.get(c), str, d + 1);
			current.children.put(c, next);
			if (next == null && current.word == null && current.children.size() <= 1)
				return null;
			return current;
		}

		private void insert(String str) {
			insert(root, str, 0);
		}

		private Node insert(Node current, String str, int d) {
			if (current == null)
				current = new Node();
			if (d == str.length()) {
				current.word = str;
				return current;
			}
			char c = str.charAt(d);
			current.children.put(c, insert(current.children.get(c), str, d + 1));
			return current;
		}

		public List<String> findWords(char[][] board, String[] words) {
			this.rows = board.length;
			this.cols = board[0].length;
			this.board = board;
			this.root = new Node();
			this.ans = new HashSet<>();
			this.marked = new boolean[rows][cols];
			int len = words.length;

			for (int i = 0; i < len; i++) {
				insert(words[i]);
			}
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					char c = board[i][j];
					if (root.children.get(c) != null)
						dfs(i, j, root.children.get(c));
				}
			}
			return new ArrayList<>(this.ans);
		}

		private void dfs(int row, int col, Node current) {
			if (current == null)
				return;
			if (current.word != null) {
				this.ans.add(current.word);
				delete(current.word);
			}
			if (current.children.isEmpty())
				return;
			marked[row][col] = true;
			for (int i = 0; i < 4; i++) {
				int newRow = row + direct[0][i];
				int newCol = col + direct[1][i];
				if (valid(newRow, newCol)) {
					char c = board[newRow][newCol];
					if (current.children.get(c) != null)
						dfs(newRow, newCol, current.children.get(c));
				}
			}
			marked[row][col] = false;
		}

		private boolean valid(int row, int col) {
			return row < rows && row >= 0 && col < cols && col >= 0 && !marked[row][col];
		}
	}

	// 摆动排序 II
	@Test
	public void test5() {
//		{
//			int[] res = new int[] { 1, 5, 1, 1, 6, 4 };
//			new Solution5_2().wiggleSort(res);
//			Assert.assertArrayEquals(new int[] { 1, 6, 1, 5, 1, 4 }, res);
//		}
//
//		{
//			int[] res = new int[] { 1, 3, 2, 2, 3, 1 };
//			new Solution5_2().wiggleSort(res);
//			Assert.assertArrayEquals(new int[] { 2, 3, 1, 3, 1, 2 }, res);
//		}
//		{
//			int[] res = new int[] { 4, 5, 5, 6 };
//			new Solution5_2().wiggleSort(res);
//			Assert.assertArrayEquals(new int[] { 5, 6, 4, 5 }, res);
//		}
		{
			int[] res = new int[] { 2, 1, 1, 2, 1, 3, 3, 3, 1, 3, 1, 3, 2 };
			new Solution5_2().wiggleSort(res);
			Assert.assertArrayEquals(new int[] { 2, 3, 2, 3, 1, 3, 1, 3, 1, 3, 1, 2, 1 }, res);
		}

	}

	// https://leetcode-cn.com/problems/wiggle-sort-ii/solution/yi-bu-yi-bu-jiang-shi-jian-fu-za-du-cong-onlognjia/
	// https://leetcode-cn.com/problems/wiggle-sort-ii/solution/bai-dong-pai-xu-ii-jie-ti-bao-gao-shi-jian-fu-za-d/
	class Solution5 {
		int n = -1;

		public void wiggleSort(int[] nums) {
			// 找到中位数索引
			int midIndex = this.quickSelect(nums, 0, nums.length - 1);
			// 找到中位数
			int mid = nums[midIndex];
			n = nums.length;
			// 三分法
			for (int i = 0, j = 0, k = nums.length - 1; j <= k;) {
				if (nums[V(j)] > mid) {
					swap(nums, V(j++), V(i++));
				} else if (nums[V(j)] < mid) {
					swap(nums, V(j), V(k--));
				} else {
					j++;
				}
			}
		}

		public int V(int i) {
			return (1 + 2 * (i)) % (n | 1);
		}

		public void swap(int[] nums, int i, int j) {
			int t = nums[i];
			nums[i] = nums[j];
			nums[j] = t;
		}

		public int quickSelect(int[] nums, int left, int right) {
			int pivot = nums[left];
			int l = left;
			int r = right;
			while (l < r) {
				while (l < r && nums[r] >= pivot) {
					r--;
				}
				if (l < r) {
					nums[l++] = nums[r];
				}
				while (l < r && nums[l] <= pivot) {
					l++;
				}
				if (l < r) {
					nums[r--] = nums[l];
				}
			}
			nums[l] = pivot;
			if (l == nums.length / 2) {
				return l;
			} else if (l > nums.length / 2) {
				return this.quickSelect(nums, left, l - 1);
			} else {
				return this.quickSelect(nums, l + 1, right);
			}
		}
	}

	class Solution5_2 {
		public void wiggleSort(int[] nums) {
			int n = nums.length;
			Arrays.sort(nums);
			int[] A = new int[n % 2 == 0 ? n / 2 : n / 2 + 1];
			int[] B = new int[n / 2];
			System.arraycopy(nums, 0, A, 0, A.length);
			System.arraycopy(nums, A.length, B, 0, B.length);
			int indexA = A.length - 1;
			int indexB = B.length - 1;
			for (int i = 0; i < n; i++) {
				nums[i] = i % 2 == 0 ? A[indexA--] : B[indexB--];
			}
		}
	}

	// 天际线问题
	@Test
	public void test6() {
		List<List<Integer>> res = new Solution6()
				.getSkyline(new int[][] { { 2, 9, 10 }, { 3, 7, 15 }, { 5, 12, 12 }, { 15, 20, 10 }, { 19, 24, 8 } });
		System.out.println(Joiner.on(",").join(res));
	}

	// https://leetcode-cn.com/problems/the-skyline-problem/solution/218tian-ji-xian-wen-ti-sao-miao-xian-fa-by-ivan_al/
	class Solution6 {
		public List<List<Integer>> getSkyline(int[][] buildings) {
			// x轴从小到大排序，如果x相等，则按照高度从低到高排序
			PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
			for (int[] building : buildings) {
				// 左端点和高度入队，高度为负值说明是左端点
				pq.offer(new int[] { building[0], -building[2] });
				// 右端点和高度入队
				pq.offer(new int[] { building[1], building[2] });
			}

			List<List<Integer>> res = new ArrayList<>();

			// 降序排列
			TreeMap<Integer, Integer> heights = new TreeMap<>((a, b) -> b - a);
			heights.put(0, 1);
			int left = 0, height = 0;

			while (!pq.isEmpty()) {
				int[] arr = pq.poll();
				// 如果是左端点
				if (arr[1] < 0) {
					// 高度 --> 高度 + 1
					heights.put(-arr[1], heights.getOrDefault(-arr[1], 0) + 1);
				}
				// 右端点
				else {
					// 高度 --> 高度 - 1
					heights.put(arr[1], heights.get(arr[1]) - 1);
					// 说明左右端点都已经遍历完
					if (heights.get(arr[1]) == 0)
						heights.remove(arr[1]);
				}
				// heights是以降序的方式排列的，所以以下会获得最大高度
				int maxHeight = heights.keySet().iterator().next();
				// 如果最大高度不变，则说明当前建筑高度在一个比它高的建筑下面，不做操作
				if (maxHeight != height) {
					left = arr[0];
					height = maxHeight;
					res.add(Arrays.asList(left, height));
				}
			}
			return res;
		}
	}

	// 移动零
	@Test
	public void test7() {
		int[] res = new int[] { 0, 1, 0, 3, 12 };
		new Solution7().moveZeroes(res);
		Assert.assertArrayEquals(new int[] { 1, 3, 12, 0, 0 }, res);
	}

	class Solution7 {
		public void moveZeroes(int[] nums) {
			int n = nums.length;
			int index = n - 1;
			for (int i = n - 1; i >= 0; i--) {
				if (nums[i] == 0) {
					// 移动
					for (int j = i; j < index; j++) {
						nums[j] = nums[j + 1];
					}
					nums[index] = 0;
					index--;
				}
			}
		}

	}

	// 数据流的中位数
	@Test
	public void test8() {
		MedianFinder2 mf = new MedianFinder2();
		mf.addNum(6);
		mf.addNum(10);
		Assert.assertEquals(8.0d, mf.findMedian(), 0.00001);
		mf.addNum(2);
		mf.addNum(1);
		mf.addNum(3);
		mf.addNum(4);
		Assert.assertEquals(3.5d, mf.findMedian(), 0.00001);
	}

	class MedianFinder {
		private List<Integer> res;

		/** initialize your data structure here. */
		public MedianFinder() {
			res = new ArrayList<Integer>();
		}

		public void addNum(int num) {
			int index = res.size();
			for (int i = res.size() - 1; i >= 0; i--) {
				if (num < res.get(i)) {
					index = i;
				}
			}
			res.add(index, num);
		}

		public double findMedian() {
			double v = 0;
			if (res.size() % 2 != 0) {
				v = res.get(res.size() / 2);
			} else {
				int a = res.size() / 2 - 1;
				int b = res.size() / 2;
				v = (res.get(a) + res.get(b)) / 2d;
			}
			return v;
		}
	}

	// https://leetcode-cn.com/problems/find-median-from-data-stream/solution/you-xian-dui-lie-python-dai-ma-java-dai-ma-by-liwe/
	class MedianFinder2 {
		/**
		 * 当前大顶堆和小顶堆的元素个数之和
		 */
		private int count;
		private PriorityQueue<Integer> maxheap;
		private PriorityQueue<Integer> minheap;

		/**
		 * initialize your data structure here.
		 */
		public MedianFinder2() {
			count = 0;
			maxheap = new PriorityQueue<>((x, y) -> y - x);
			minheap = new PriorityQueue<>();
		}

		public void addNum(int num) {
			count += 1;
			maxheap.add(num);
			minheap.add(maxheap.poll());
			// 如果两个堆合起来的元素个数是奇数，小顶堆要拿出堆顶元素给大顶堆
			if ((count & 1) != 0) {
				maxheap.add(minheap.poll());
			}
		}

		public double findMedian() {
			if ((count & 1) == 0) {
				// 如果两个堆合起来的元素个数是偶数，数据流的中位数就是各自堆顶元素的平均值
				return (double) (maxheap.peek() + minheap.peek()) / 2;
			} else {
				// 如果两个堆合起来的元素个数是奇数，数据流的中位数大顶堆的堆顶元素
				return (double) maxheap.peek();
			}
		}

	}

	// 递增的三元子序列(不一定连续)
	@Test
	public void test9() {
//		Assert.assertEquals(true, increasingTriplet(new int[] { 1, 2, 3, 4, 5 }));
//		Assert.assertEquals(false, increasingTriplet(new int[] { 5,4,3,2,1 }));
		// Assert.assertEquals(true, increasingTriplet(new int[] {2,1,5,0,4,6 }));
		Assert.assertEquals(true, increasingTriplet(new int[] { 5, 6, 1, 5, 5, 2, 5, 4 }));
	}

	// https://leetcode-cn.com/problems/increasing-triplet-subsequence/solution/c-xian-xing-shi-jian-fu-za-du-xiang-xi-jie-xi-da-b/
	public boolean increasingTriplet(int[] nums) {
		int small = Integer.MAX_VALUE, mid = Integer.MAX_VALUE;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] <= small) {
				small = nums[i];
			} else if (nums[i] <= mid) {
				mid = nums[i];
			} else {
				return true;
			}
		}
		return false;
	}

	// 反转链表
	@Test
	public void test10() {
		ListNode head = new ListNode(1);
		ListNode node1 = new ListNode(2);
		ListNode node2 = new ListNode(3);
		ListNode node3 = new ListNode(4);
		ListNode node4 = new ListNode(5);
		head.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		head = reverseList(head);
		while (head.next != null) {
			System.out.print(head.val + "->");
			head = head.next;
		}
		System.out.print(head.val);
	}

//https://leetcode-cn.com/problems/reverse-linked-list/solution/fan-zhuan-lian-biao-by-leetcode/
	public ListNode reverseList(ListNode head) {
		ListNode prev = null;
		ListNode curr = head;
		while (curr != null) {
			ListNode nextTemp = curr.next;
			curr.next = prev;
			prev = curr;
			curr = nextTemp;
		}
		return prev;
	}

	// 对链表进行插入排序
	@Test
	public void test11() {
		ListNode head = new ListNode(4);
		ListNode head2 = new ListNode(2);
		ListNode head3 = new ListNode(1);
		ListNode head4 = new ListNode(3);

		head.next = head2;
		head2.next = head3;
		head3.next = head4;

		ListNode node = insertionSortList(head);
		Utils.printListNode(node);
	}

	public ListNode insertionSortList(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode tail = new ListNode(-1);
		tail.next = null;
		while (head != null) {
			ListNode curr = head;
			// 进行插入排序
			ListNode h = tail.next;
			ListNode last = tail;
			while (h != null) {
				if (curr.val < h.val) {
					head = curr.next;
					ListNode tmp = last.next;
					last.next = curr;
					curr.next = tmp;
					break;
				}
				last = h;
				h = h.next;
			}
			// 找完了还没有找到比curr大的，连接到后面
			if (h == null) {
				head = curr.next;
				last.next = curr;
				curr.next = null;
			}
		}
		return tail.next;
	}

	// 扁平化嵌套列表迭代器
	@Test
	public void test12() {
		{

			List<NestedInteger> nestedList = new ArrayList<NestedInteger>();
			List<NestedInteger> nestedList1 = new ArrayList<NestedInteger>();
			NestedIntegerImpl n1 = new NestedIntegerImpl();
			n1.setValue(1);
			NestedIntegerImpl n2 = new NestedIntegerImpl();
			n2.setValue(1);
			nestedList1.add(n1);
			nestedList1.add(n2);
			NestedIntegerImpl v1 = new NestedIntegerImpl();
			v1.setResult(nestedList1);
			nestedList.add(v1);

			NestedIntegerImpl v2 = new NestedIntegerImpl();
			v2.setValue(2);
			nestedList.add(v2);

			List<NestedInteger> nestedList2 = new ArrayList<NestedInteger>();
			NestedIntegerImpl n12 = new NestedIntegerImpl();
			n12.setValue(1);
			NestedIntegerImpl n22 = new NestedIntegerImpl();
			n22.setValue(1);
			nestedList2.add(n12);
			nestedList2.add(n22);
			NestedIntegerImpl v3 = new NestedIntegerImpl();
			v3.setResult(nestedList2);
			nestedList.add(v3);

			NestedIterator it = new NestedIterator(nestedList);
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		}
		{
			List<NestedInteger> nestedList = new ArrayList<NestedInteger>();
			List<NestedInteger> nestedList1 = new ArrayList<NestedInteger>();
			NestedIntegerImpl v1 = new NestedIntegerImpl();
			v1.setResult(nestedList1);
			nestedList.add(v1);
			NestedIterator it = new NestedIterator(nestedList);
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		}
	}

	// https://leetcode-cn.com/problems/flatten-nested-list-iterator/solution/shu-de-bian-li-kuang-jia-ti-mu-bu-rang-wo-zuo-shi-/
	public class NestedIterator implements Iterator<Integer> {
		private LinkedList<NestedInteger> list;

		public NestedIterator(List<NestedInteger> nestedList) {
			// 不直接用 nestedList 的引用，是因为不能确定它的底层实现
			// 必须保证是 LinkedList，否则下面的 addFirst 会很低效
			list = new LinkedList<>(nestedList);
		}

		public Integer next() {
			// hasNext 方法保证了第一个元素一定是整数类型
			return list.remove(0).getInteger();
		}

		public boolean hasNext() {
			// 循环拆分列表元素，直到列表第一个元素是整数类型
			while (!list.isEmpty() && !list.get(0).isInteger()) {
				// 当列表开头第一个元素是列表类型时，进入循环
				List<NestedInteger> first = list.remove(0).getList();
				// 将第一个列表打平并按顺序添加到开头
				for (int i = first.size() - 1; i >= 0; i--) {
					list.addFirst(first.get(i));
				}
			}
			return !list.isEmpty();
		}
	}

	// 两整数之和
	@Test
	public void test13() {
		Assert.assertEquals(12, getSum(5, 7));
	}

	// https://leetcode-cn.com/problems/sum-of-two-integers/solution/li-yong-wei-cao-zuo-shi-xian-liang-shu-qiu-he-by-p/
	public int getSum(int a, int b) {
		while (b != 0) {
			int temp = a ^ b;
			b = (a & b) << 1;
			a = temp;
		}
		return a;
	}

	// 有序矩阵中第K小的元素
	@Test
	public void test14() {
		Assert.assertEquals(13,
				new Solution14().kthSmallest(new int[][] { { 1, 5, 9 }, { 10, 11, 13 }, { 12, 13, 15 } }, 8));
	}

	// https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix/solution/you-xu-ju-zhen-zhong-di-kxiao-de-yuan-su-by-leetco/
	class Solution14 {
		public int kthSmallest(int[][] matrix, int k) {
			int n = matrix.length;
			int left = matrix[0][0];
			int right = matrix[n - 1][n - 1];
			while (left < right) {
				int mid = left + ((right - left) >> 1);
				if (check(matrix, mid, k, n)) {
					right = mid;
				} else {
					left = mid + 1;
				}
			}
			return left;
		}

		public boolean check(int[][] matrix, int mid, int k, int n) {
			int i = n - 1;
			int j = 0;
			int num = 0;
			while (i >= 0 && j < n) {
				if (matrix[i][j] <= mid) {
					num += i + 1; // 比mid小的值数量
					j++;
				} else {
					i--;
				}
			}
			return num >= k;
		}
	}

	// 常数时间插入、删除和获取随机元素
	@Test
	public void test15() {
		RandomizedSet randomSet = new RandomizedSet();
		randomSet.insert(0);
		randomSet.insert(1);
		randomSet.remove(0);
		randomSet.insert(2);
		randomSet.remove(1);
		System.out.println(randomSet.getRandom());
	}

	class RandomizedSet {
		Map<Integer, Integer> idx;
		List<Integer> nums;
		Random rand = new Random();

		/** Initialize your data structure here. */
		public RandomizedSet() {
			idx = new HashMap<Integer, Integer>();
			nums = new ArrayList<Integer>();
		}

		/**
		 * Inserts a value to the set. Returns true if the set did not already contain
		 * the specified element.
		 */
		public boolean insert(int val) {
			if (idx.containsKey(val)) {
				return false;
			}
			nums.add(val);
			idx.put(val, nums.size() - 1);
			return true;
		}

		/**
		 * Removes a value from the set. Returns true if the set contained the specified
		 * element.
		 */
		public boolean remove(int val) {
			if (!idx.containsKey(val)) {
				return false;
			}
			int index = idx.get(val); // 获取要删除的值，在list中的下标
			int lastElement = nums.get(nums.size() - 1); // 得到最后一个元素的值
			nums.set(index, lastElement); // 将最后一个元素的值赋值到index处
			idx.put(lastElement, index);
			// 删除元素
			nums.remove(nums.size() - 1);
			idx.remove(val);
			return true;
		}

		/** Get a random element from the set. */
		public int getRandom() {
			int size = rand.nextInt(nums.size());
			return nums.get(size);
		}
	}

	// 有效的字母异位词
	@Test
	public void test16() {
		// Assert.assertEquals(true, isAnagram("anagram","nagaram"));
		List<String> str1;
		List<String> str2;
		try {
			str1 = IOUtils.readLines(this.getClass().getClassLoader().getResourceAsStream("test17_16.txt"));
			str2 = IOUtils.readLines(this.getClass().getClassLoader().getResourceAsStream("test17_16_2.txt"));
			Assert.assertEquals(true, isAnagram(str1.get(0), str2.get(0)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isAnagram(String s, String t) {
		Map<Character, Integer> map1 = new HashMap<Character, Integer>();
		Map<Character, Integer> map2 = new HashMap<Character, Integer>();
		for (int i = 0; i < s.length(); i++) {
			int v = map1.getOrDefault(s.charAt(i), 0);
			map1.put(s.charAt(i), ++v);
		}
		for (int i = 0; i < t.length(); i++) {
			int v = map2.getOrDefault(t.charAt(i), 0);
			map2.put(t.charAt(i), ++v);
		}
		if (map1.size() == map2.size()) {
			for (Character c : map1.keySet()) {
				if (!map1.get(c).equals(map2.get(c))) { // Integer需要用equals比较
					return false;
				}
			}
			return true;
		}
		return false;
	}

	// 完全二叉树的节点个数
	@Test
	public void test17() {
		TreeNode root = Utils.createTree(new Integer[] { 2, 0, 3, -4, 1 });
		Assert.assertEquals(5, new Solution17().countNodes(root));
	}

	// https://leetcode-cn.com/problems/count-complete-tree-nodes/solution/chang-gui-jie-fa-he-ji-bai-100de-javajie-fa-by-xia/
	class Solution17 {
		public int countNodes(TreeNode root) {
			if (root == null) {
				return 0;
			}
			int left = countLevel(root.left); // 左子树高度
			int right = countLevel(root.right);// 右子树高度
			if (left == right) {
				return countNodes(root.right) + (1 << left);
			} else {
				return countNodes(root.left) + (1 << right);
			}
		}

		private int countLevel(TreeNode root) {
			int level = 0;
			while (root != null) {
				level++;
				root = root.left;
			}
			return level;
		}
	}

	// 至少有K个重复字符的最长子串
	@Test
	public void test18() {
	//	Assert.assertEquals(3, new Solution18().longestSubstring("aaabb", 3));

		//	Assert.assertEquals(5, new Solution18_2().longestSubstring("ababbc", 2));

		Assert.assertEquals(5, new Solution18_2().longestSubstring("aaaaadabcbb", 3));

	//	Assert.assertEquals(3, new Solution18().longestSubstring("bbaaacddcaabdbd", 3));
	}

	// https://leetcode-cn.com/problems/longest-substring-with-at-least-k-repeating-characters/solution/395-zhi-shao-you-kge-zhong-fu-zi-fu-de-zui-chang-5/
	class Solution18_2 {
		public int longestSubstring(String s, int k) {
			if (s == null || s.length() == 0) {
				return 0;
			}

			Map<Character, Integer> map = new HashMap<>();
			for (char chs : s.toCharArray()) {
				map.put(chs, map.getOrDefault(chs, 0) + 1);
			}

			// 这里是先把 s 传进 sb 中来找出不满足 k 个的元素及其位置
			StringBuilder sb = new StringBuilder(s);
			for (int i = 0; i < s.length(); i++) {
				if (map.get(s.charAt(i)) < k) {
					sb.setCharAt(i, ',');
				}
			}

			// 然后以不符合要求的元素位置进行分割存储到字符串数组中
			String[] string = sb.toString().split(",");
			// 如果仅有一组，则说明只有一个字母，返回首字母既可
			if (string.length == 1) {
				return string[0].length();
			}
			int max = 0;
			// 如果有多组，就进行最大值比较
			// 例如 aaabcccc，通过上面的操作后 化为 aaa,cccc
			for (String str : string) {
				max = Math.max(max, longestSubstring(str, k));
			}
			return max;
		}
	}

	class Solution18 {
		public int longestSubstring(String s, int k) {
			int[] arr = new int[26];
			for (int i = 0; i < s.length(); i++) {
				arr[s.charAt(i) - 'a']++;
			}
			int p = 0, q = s.length() - 1;
			int max = 0;
			while (p <= q) {
				if (check(arr, k)) {
					break;
				}
				if (arr[s.charAt(p) - 'a'] < k) {
					arr[s.charAt(p) - 'a']--;
					p++;
				} else if (arr[s.charAt(q) - 'a'] < k) {
					arr[s.charAt(q) - 'a']--;
					q--;
				} else {
					// 找到最近小于k的字符
					int index1 = -1;

					for (int i = p; i <= q; i++) {
						if (arr[s.charAt(i) - 'a'] < k) {
							index1 = i;
							break;
						}
					}
					int index2 = -1;
					for (int i = q; i >= p; i--) {
						if (arr[s.charAt(i) - 'a'] < k) {
							index2 = i;
							break;
						}
					}
					if (index1 != -1 && index2 != -1) {
						if ((index1 - p) > (q - index2)) {
							for (int j = q; j >= index2; j--) {
								arr[s.charAt(j) - 'a']--;
							}
							max = Math.max(longestSubstring(s.substring(index2, q + 1), k), max);
							q = index2 - 1;
						} else {
							for (int j = p; j <= index1; j++) {
								arr[s.charAt(j) - 'a']--;
							}
							max = Math.max(longestSubstring(s.substring(p, index1 + 1), k), max);
							p = index1 + 1;
						}
					} else {
						break;
					}
				}
			}
			max = Math.max(max, q - p + 1);
			return max;
		}

		public boolean check(int[] arr, int k) {
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] > 0 && arr[i] < k) {
					return false;
				}
			}
			return true;
		}
	}

	// 上升下降字符串
	@Test
	public void test19() {
		Assert.assertEquals("abccbaabccba", sortString("aaaabbbbcccc"));

		Assert.assertEquals("art", sortString("rat"));

		Assert.assertEquals("cdelotee", sortString("leetcode"));

		Assert.assertEquals("ggggggg", sortString("ggggggg"));

		Assert.assertEquals("ops", sortString("spo"));
	}

	public String sortString(String s) {
		StringBuilder sb = new StringBuilder();
		int[] arr = new int[26];
		for (int i = 0; i < s.length(); i++) {
			arr[s.charAt(i) - 'a']++;
		}
		char curr = ' ';
		boolean max = false;
		while (sb.length() < s.length()) {
			if (!max) {

				int len = sb.length();
				for (int i = 0; i < arr.length; i++) {
					char c = (char) (i + 'a');
					if (arr[i] > 0) {
						if (curr == ' ') {
							sb.append(c);
							arr[i]--;
						} else if (c > curr) {
							sb.append(c);
							arr[i]--;
						}
					}
				}
				curr = sb.charAt(sb.length() - 1);
				if (sb.length() == len) {
					curr = ' ';
					max = true;
				}
			} else {
				int len = sb.length();
				for (int i = arr.length - 1; i >= 0; i--) {
					char c = (char) (i + 'a');
					if (arr[i] > 0) {
						if (curr == ' ') {
							sb.append(c);
							arr[i]--;
						} else if (c < curr) {
							sb.append(c);
							arr[i]--;
						}
					}
				}
				curr = sb.charAt(sb.length() - 1);
				if (sb.length() == len) {
					curr = ' ';
					max = false;
				}
			}

		}
		return sb.toString();
	}

	// 四数相加 II
	@Test
	public void test20() {
		Assert.assertEquals(2,
				fourSumCount(new int[] { 1, 2 }, new int[] { -2, -1 }, new int[] { -1, 2 }, new int[] { 0, 2 }));
	}
	//https://leetcode-cn.com/problems/4sum-ii/solution/chao-ji-rong-yi-li-jie-de-fang-fa-si-shu-xiang-jia/
	public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
		Map<Integer,Integer> map1 = new HashMap<Integer,Integer>();
		int n = A.length;
		for (int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				int count = map1.getOrDefault(A[i]+B[j], 0);
				map1.put(A[i]+B[j], ++count);
			}
		}
		Map<Integer,Integer> map2 = new HashMap<Integer,Integer>();
		for (int i=0;i<n;i++) {
			for (int j=0;j<n;j++) {
				int count = map2.getOrDefault(C[i]+D[j], 0);
				map2.put(C[i]+D[j], ++count);
			}
		}
		int res = 0;
		for (Integer key:map1.keySet()) {
			Integer key2 = 0-key;
			Integer v = map2.getOrDefault(key2, 0);
			res = res+(v*map1.get(key));
		}
		return res;
	}
}
