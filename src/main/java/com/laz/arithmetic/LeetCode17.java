package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

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
	
	//对链表进行插入排序
	@Test
	public void test11() {
		ListNode head =new ListNode(4);
		ListNode head2 =new ListNode(2);
		ListNode head3 =new ListNode(1);
		ListNode head4 =new ListNode(3);
		
		head.next = head2;
		head2.next = head3;
		head3.next = head4;
		
		ListNode node = insertionSortList(head);
		Utils.printListNode(node);
	}
    public ListNode insertionSortList(ListNode head) {
    	if (head==null) {
    		return null;
    	}
    	ListNode tail = new ListNode(-1);
    	tail.next = null;
    	while(head!=null) {
    		ListNode curr = head;
    		//进行插入排序
    		ListNode h = tail.next;
    		ListNode last = tail;
    		while (h!=null) {
    			if (curr.val<h.val) {
    				head = curr.next;
    				ListNode tmp = last.next;
    				last.next = curr;
    				curr.next = tmp;
    				break;
    			}
    			last = h;
    			h = h.next;
    		}
    		//找完了还没有找到比curr大的，连接到后面
    		if (h==null) {
    			head = curr.next;
    			last.next = curr;
    			curr.next=null;
    		}
    	}
    	return tail.next;
    }
}