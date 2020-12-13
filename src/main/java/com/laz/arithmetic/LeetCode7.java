package com.laz.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode7 {
	// 整数转罗马数字
	@Test
	public void test1() {
		int num = 19943;
		// 1994
		// 输出: "MCMXCIV"
		System.out.println(intToRoman(num));
	}

	public String intToRoman(int num) {
		int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length && num > 0; i++) {
			while (values[i] <= num) {
				num -= values[i];
				sb.append(symbols[i]);
			}
		}
		return sb.toString();
	}

	// 最接近的三数之和
	@Test
	public void test2() {
		int[] nums = new int[] { -1, 2, 1, -4 };
		System.out.println(threeSumClosest(nums, -4));
	}

	public int threeSumClosest(int[] nums, int target) {
		if (nums == null) {
			return 0;
		}
		Arrays.sort(nums);
		int result = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < nums.length - 2; i++) {
			int left = i + 1;
			int right = nums.length - 1;
			while (left < right) {
				int v = nums[i] + nums[left] + nums[right];
				if (Math.abs(v - target) < min) {
					min = Math.abs(v - target);
					result = v;
				}
				if (v > target) {
					right--;
				}
				if (v < target) {
					left++;
				}
				if (v == target) {
					return v;
				}
			}
		}
		return result;
	}

	// 电话号码的字母组合
	@Test
	public void test3() {
		List<String> list = letterCombinations("4");
		System.out.println(Joiner.on(",").join(list));
	}

	Map<String, String> phone = new HashMap<String, String>() {
		{
			put("2", "abc");
			put("3", "def");
			put("4", "ghi");
			put("5", "jkl");
			put("6", "mno");
			put("7", "pqrs");
			put("8", "tuv");
			put("9", "wxyz");
		}
	};
	List<String> output = new ArrayList<String>();

	public List<String> letterCombinations(String digits) {
		if (digits.length() != 0) {
			backtrack("", digits);
		}
		return output;
	}

	public void backtrack(String combination, String next_digits) {
		if (next_digits.length() == 0) {
			output.add(combination);
		} else {
			String digit = next_digits.substring(0, 1);
			String letters = phone.get(digit);
			for (int i = 0; i < letters.length(); i++) {
				String letter = phone.get(digit).substring(i, i + 1);
				backtrack(combination + letter, next_digits.substring(1));
			}
		}
	}

	// 四数之和
	@Test
	public void test4() {
		int[] nums = new int[] { 0, 0, 0, 0 };
		int target = 0;
		List<List<Integer>> list = fourSum(nums, target);
		for (List<Integer> l : list) {
			System.out.println(Joiner.on(",").join(l));
		}
	}

	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		Arrays.sort(nums);
		if (nums == null || nums.length < 4) {
			return res;
		}
		int left1 = 0, left2 = 1;
		int right1 = nums.length - 1, right2 = nums.length - 2;
		Map<String, Boolean> falg = new HashMap<String, Boolean>();
		while (left1 <= nums.length - 4 && left1 < left2 && right2 < right1 && left2 < right2) {
			List<Integer> list = new ArrayList<Integer>();
			int r = nums[left1] + nums[left2] + nums[right2] + nums[right1];
			if (r == target) {
				list.add(nums[left1]);
				list.add(nums[left2]);
				list.add(nums[right2]);
				list.add(nums[right1]);
				String key = new StringBuffer().append(nums[left1]).append(nums[left2]).append(nums[right2])
						.append(nums[right1]).toString();
				if (falg.get(key) == null || !falg.get(key)) {
					falg.put(key, true);
					res.add(list);
				}
				right2--;
				left2++;
			}
			if (r > target) {
				right2--;
			}
			if (r < target) {
				left2++;
			}
			if (left2 >= right2) {
				if (right1 - left1 < 4) {
					right1 = nums.length - 1;
					right2 = right1 - 1;
					left1++;
					left2 = left1 + 1;
				} else {
					right1--;
					right2 = right1 - 1;
					left2 = left1 + 1;
				}

			}
		}
		return res;
	}

	// 缺失的第一个正数
	@Test
	public void test5() {
		// Assert.assertEquals(3, firstMissingPositive(new int[] {1,2,0}));
		Assert.assertEquals(1, new Solution5().firstMissingPositive(new int[] { 3, 2, 3 }));
	}
	
	class Solution5 {
		public int firstMissingPositive(int[] nums) {
			int len = nums.length;
			for (int i = 0; i < len; i++) {
				while (nums[i] > 0 && nums[i] <= len && nums[nums[i] - 1] != nums[i]) {
					swap(nums, nums[i] - 1, i);
				}
			}
			for (int i = 0; i < len; i++) {
				if (nums[i] != i + 1) {
					return i + 1;
				}
			}
			return len + 1;
		}
		public void swap(int[] nums, int index1, int index2) {
			int temp = nums[index1];
			nums[index1] = nums[index2];
			nums[index2] = temp;
		}
	}

	// K 个一组翻转链表
	@Test
	public void test6() {
		ListNode head = Utils.createListNode(new Integer[] { 1, 2, 3, 4, 5 });
		ListNode newHead = reverseKGroup(head, 2);
		Utils.printListNode(newHead);
	}

	public ListNode reverseKGroup(ListNode head, int k) {
		ListNode hair = new ListNode(0);
		hair.next = head;
		ListNode pre = hair;
		while (head != null) {
			ListNode tail = pre;
			for (int i = 0; i < k; i++) {
				tail = tail.next;
				if (tail == null) {
					return hair.next;
				}
			}
			ListNode next = tail.next;
			Map<String, ListNode> map = reverse(head, tail);
			pre.next = map.get("head");
			map.get("tail").next = next;
			pre = map.get("tail");
			head = pre.next;
		}
		return hair.next;
	}

	public Map<String, ListNode> reverse(ListNode head, ListNode tail) {
		Map<String, ListNode> map = new HashMap<String, ListNode>();
		ListNode prev = tail.next;
		ListNode p = head;
		while (prev != tail) {
			ListNode next = p.next;
			p.next = prev;
			prev = p;
			p = next;
		}
		map.put("head", tail);
		map.put("tail", head);
		return map;
	}

	// 数组中的第K个最大元素
	@Test
	public void test7() {
		Assert.assertEquals(5, findKthLargest(new int[] { 3, 2, 1, 5, 6, 4 }, 2));
	}

	public int findKthLargest(int[] nums, int k) {
		if (nums == null || nums.length < k) {
			return 0;
		}
		Arrays.sort(nums);
		int count = 0;
		for (int i = nums.length - 1; i >= 0; i--) {
			count++;
			if (count == k) {
				return nums[i];
			}
		}
		return 0;
	}

	// 下一个排列
	@Test
	public void test8() {
		int[] nums = new int[] { 1, 5, 1 };
		new Solution8().nextPermutation(nums);
		for (int i : nums) {
			System.out.print(i + " ");
		}
	}
	//https://leetcode-cn.com/problems/next-permutation/solution/xia-yi-ge-pai-lie-by-leetcode-solution/
	class Solution8 {
		public void nextPermutation(int[] nums) {
			if (nums == null || nums.length < 0) {
				return;
			}
			int i = nums.length - 2;
			while (i >= 0 && nums[i] >= nums[i + 1]) {
				i--;
			}
			if (i >= 0) {
				int j = nums.length - 1;
				while (j > i && nums[j] <= nums[i]) {
					j--;
				}
				swap(nums, i, j);
			}
			reverse(nums, i + 1);
			
		}
		
		public void reverse(int[] nums, int start) {
			int i = start, j = nums.length - 1;
			while (i < j) {
				swap(nums, i, j);
				i++;
				j--;
			}
		}
		
		public void swap(int[] nums, int index1, int index2) {
			int temp = nums[index1];
			nums[index1] = nums[index2];
			nums[index2] = temp;
		}
	}

	// 串联所有单词的子串
	@Test
	public void test9() {
		String s = "wordgoodgoodgoodbestword";
		String[] words = new String[] { "word", "good", "best", "good" };
		List<Integer> res = findSubstring(s, words);
		System.out.println(Joiner.on(",").join(res));
	}

	public List<Integer> findSubstring(String s, String[] words) {
		List<Integer> res = new ArrayList<>();
		if (s == null || s.length() == 0 || words == null || words.length == 0)
			return res;
		HashMap<String, Integer> map = new HashMap<>();
		int one_word = words[0].length();
		int word_num = words.length;
		int all_len = one_word * word_num;
		for (String word : words) {
			map.put(word, map.getOrDefault(word, 0) + 1);
		}
		for (int i = 0; i < s.length() - all_len + 1; i++) {
			String tmp = s.substring(i, i + all_len);
			HashMap<String, Integer> tmp_map = new HashMap<>();
			for (int j = 0; j < all_len; j += one_word) {
				String w = tmp.substring(j, j + one_word);
				tmp_map.put(w, tmp_map.getOrDefault(w, 0) + 1);
			}
			if (map.equals(tmp_map))
				res.add(i);
		}
		return res;
	}

	// 最长重复子数组
	@Test
	public void test10() {
		Assert.assertEquals(3, new Solution10().findLength(new int[] { 1, 2, 3, 2, 1 }, new int[] { 3, 2, 1, 4, 7 }));
		Assert.assertEquals(1, new Solution10().findLength(new int[] { 1, 2, 3, 4, 5 }, new int[] { 3, 2, 1, 4, 7 }));
		Assert.assertEquals(0, new Solution10().findLength(new int[] {}, new int[] { 3, 2, 1, 4, 7 }));
		Assert.assertEquals(5, new Solution10().findLength(new int[] { 0, 0, 0, 0, 0 }, new int[] { 0, 0, 0, 0, 0 }));
	}
//https://leetcode-cn.com/problems/maximum-length-of-repeated-subarray/solution/zui-chang-zhong-fu-zi-shu-zu-by-leetcode-solution/
	class Solution10 {
		public int findLength(int[] A, int[] B) {
			int n = A.length, m = B.length;
			int ret = 0;
			for (int i = 0; i < n; i++) {
				int len = Math.min(m, n - i);
				int maxLen = maxLength(A, B, i, 0, len);
				ret = Math.max(maxLen, ret);
			}
			for (int i = 0; i < n; i++) {
				int len = Math.min(n, m - i);
				int maxLen = maxLength(A, B, 0, i, len);
				ret = Math.max(maxLen, ret);
			}
			return ret;
		}
		
		private int maxLength(int[] A, int[] B, int addA, int addB, int len) {
			int ret = 0, k = 0;
			for (int i = 0; i < len; i++) {
				if (A[addA + i] == B[addB + i]) {
					k++;
				} else {
					k = 0;
				}
				ret = Math.max(ret, k);
			}
			return ret;
		}
	}

	// 搜索旋转排序数组
	@Test
	public void test11() {
		Assert.assertEquals(4, search(new int[] { 4, 5, 6, 7, 0, 1, 2 }, 0));
	}

	public int search(int[] nums, int target) {
		if (nums == null || nums.length <= 0) {
			return -1;
		}
		int l = 0, r = nums.length - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (nums[mid] == target) {
				return mid;
			}
			// l至mid有序
			if (nums[0] <= nums[mid]) {
				if (nums[0] <= target && target < nums[mid]) {
					// 在 l至mid-1查找
					r = mid - 1;
				} else {
					// 在mid+1至r查找
					l = mid + 1;
				}
			} else {
				if (nums[mid] < target && target <= nums[nums.length - 1]) {
					// 在 l至mid-1查找
					l = mid + 1;
				} else {
					// 在mid+1至r查找
					r = mid - 1;
				}
			}

		}
		return -1;
	}

	// 将有序数组转换为二叉搜索树
	@Test
	public void test12() {
		int[] nums = new int[] { -10, -3, 0, 5, 9 };
		TreeNode node = sortedArrayToBST(nums);
		System.out.println(new Utils().new Codec().serialize(node));
	}

	public TreeNode sortedArrayToBST(int[] nums) {
		if (nums == null || nums.length <= 0) {
			return null;
		}
		int l = 0, r = nums.length - 1;
		TreeNode root = createTree(nums, l, r);
		return root;
	}

	public TreeNode createTree(int[] nums, int l, int r) {
		if (l < 0 || l > r || r >= nums.length) {
			return null;
		}
		int mid = (l + r) / 2;
		TreeNode root = new TreeNode(nums[mid]);
		root.left = createTree(nums, l, mid - 1);
		root.right = createTree(nums, mid + 1, r);
		return root;
	}

	// 在排序数组中查找元素的第一个和最后一个位置
	@Test
	public void test13() {
		int[] res = searchRange(new int[] { 5, 7, 7, 8, 8, 10 }, 6);
		for (int i : res) {
			System.out.print(i + " ");
		}
	}

	public int[] searchRange(int[] nums, int target) {
		int[] res = new int[] { -1, -1 };
		if (nums == null || nums.length <= 0) {
			return res;
		}
		int l = 0, r = nums.length - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			if (nums[mid] == target) {
				int index = mid;
				while (index >= 0) {
					if (nums[index] != target) {
						break;
					}
					index--;
				}
				res[0] = index + 1;
				index = mid;
				while (index < nums.length) {
					if (nums[index] != target) {
						break;
					}
					index++;
				}
				res[1] = index - 1;
				break;
			}
			if (nums[mid] < target) {
				l = mid + 1;
			}
			if (nums[mid] > target) {
				r = mid - 1;
			}
		}
		return res;
	}

	// 最长有效括号
	@Test
	public void test14() {
		Assert.assertEquals(4, longestValidParentheses(")()())"));
//		Assert.assertEquals(10, longestValidParentheses("((((()))))("));
//		Assert.assertEquals(6, longestValidParentheses("(()()))"));
//		Assert.assertEquals(2, longestValidParentheses("()((()"));
	}

	public int longestValidParentheses(String s) {
		if (s == null || s.length() <= 0) {
			return 0;
		}
		Deque<Integer> stack = new LinkedList<Integer>();
		stack.push(-1);
		int max = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '(') {
				stack.push(i);
			}
			if (c == ')') {
				stack.pop();
				if (stack.size() == 0) {
					stack.push(i);
				} else {
					max = Math.max(max, i - stack.peek());
				}
			}
		}
		return max;
	}

	// 组合总和
	@Test
	public void test15() {
		int[] candidates = new int[] {10,1,2,7,6,1,5 };
		int target = 8;
		List<List<Integer>> list = combinationSum(candidates, target);
		for (List<Integer> l : list) {
			System.out.println(Joiner.on(",").join(l));
		}
	}

	public List<List<Integer>> combinationSum(int[] candidates, int target) {
		List<List<Integer>> res = new ArrayList<>();
		int len = candidates.length;
		// 排序是为了提前终止搜索
		Arrays.sort(candidates);

		dfs(candidates, len, target, 0, new ArrayDeque<>(), res);
		return res;
	}

	private void dfs(int[] candidates, int len, int residue, int begin, Deque<Integer> path, List<List<Integer>> res) {
		// 减到0
		if (residue == 0) {
			// 由于 path 全局只使用一份，到叶子结点的时候需要做一个拷贝
			res.add(new ArrayList<>(path));
			return;
		}
		for (int i = begin; i < len; i++) {
			int aim = residue - candidates[i];
			// 在数组有序的前提下，剪枝
			if (aim < 0) {
				break;
			}
			path.addLast(candidates[i]);
			dfs(candidates, len, aim, i, path, res);
			path.removeLast();

		}
	}

	// 不同路径 II
	@Test
	public void test16() {
		Assert.assertEquals(2, uniquePathsWithObstacles(new int[][] { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } }));
	}

	public int uniquePathsWithObstacles(int[][] obstacleGrid) {
		if (obstacleGrid[0][0] == 1) {
			return 0;
		}
		int m = obstacleGrid.length;
		int n = obstacleGrid[0].length;
		int[][] dp = new int[m][n];
		dp[0][0] = 1;
		for (int i = 1; i < m; i++) {
			// 第一列，有1后为0
			dp[i][0] = obstacleGrid[i][0] == 1 || dp[i - 1][0] == 0 ? 0 : 1;
		}
		for (int i = 1; i < n; i++) {
			// 第一行，有1后为0
			dp[0][i] = obstacleGrid[0][i] == 1 || dp[0][i - 1] == 0 ? 0 : 1;
			;
		}
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				dp[i][j] = obstacleGrid[i][j] == 1 ? 0 : dp[i - 1][j] + dp[i][j - 1];
			}
		}
		return dp[m - 1][n - 1];
	}

	// 路径总和
	@Test
	public void test17() {
		TreeNode root = Utils.createTree(new Integer[] { 5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1 });
		System.out.println(hasPathSum(root, 22));
	}

	public boolean hasPathSum(TreeNode root, int sum) {
		if (root == null) {
			return false;
		}
		if (root.left == null && root.right == null) {
			return sum == root.val;
		}
		return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
	}

	// 解数独
	@Test
	public void test18() {
		char[][] board = new char[][] { { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
				{ '6', '.', '.', '1', '9', '5', '.', '.', '.' }, { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
				{ '8', '.', '.', '.', '6', '.', '.', '.', '3' }, { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
				{ '7', '.', '.', '.', '2', '.', '.', '.', '6' }, { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
				{ '.', '.', '.', '4', '1', '9', '.', '.', '5' }, { '.', '.', '.', '.', '8', '.', '.', '7', '9' } };
		solveSudoku(board);
		System.out.println(board);
	}

	public void solveSudoku(char[][] board) {
		if (board == null || board[0] == null) {
			return;
		}
		backTrace(board, 0, 0);
	}

	private boolean backTrace(char[][] board, int row, int col) {
		int n = board.length;
		//// 当前行已全部试探过，换到下一行第一个位置
		if (col == 9) {
			return backTrace(board, row + 1, 0);
		}
		// 满足结束条件，全部行全部位置都已试探过
		if (row == n) {
			// 最后一行最后一个位置[8][8]试探过后会试探[8][9]，会执行[9][0]，返回
			return true;
		}
		 // 这个位置数字已给出，不需要试探，直接试探下一个位置
		if (board[row][col] != '.') {
			return backTrace(board, row, col+1);
		}
		for (char c = '1'; c<='9';c++) {
			if (!isValid(board,row,col,c)) {
				continue;
			}
			board[row][col]  = c;
			if (backTrace(board, row, col+1)) {
				return true;
			}
			//如果不能放，取消赋值
			board[row][col] = '.';
		}
		return false;
	}
	private boolean isValid(char[][] board, int row, int col, char ch) {
        // 三个方向，任一方向重复，ch就不能放在这个位置
        for (int k = 0; k < 9; k++) {
            // 同一行九个位置已出现 ch
            if (board[row][k] == ch) return false;
            // 同一列九个位置中已出现 ch
            if (board[k][col] == ch) return false;
            // 同一个子数独九个位置中已出现 ch
            if (board[(row / 3) * 3 + k / 3][(col / 3) * 3 + k % 3] == ch) return false;
        }
        return true;
    }
	
	//组合总和 II
	@Test
	public void test19() {
		int[] candidates = new int[] {10,1,2,7,6,1,5};
		int target = 8;
		List<List<Integer>> res = combinationSum2(candidates, target);
		for (List<Integer> list : res) {
			System.out.println(Joiner.on(",").join(list));
		}
	}
	
	public List<List<Integer>> combinationSum2(int[] candidates, int target) {
		List<List<Integer>> res = new ArrayList<>();
		int len = candidates.length;
		Arrays.sort(candidates);
		dfs2(candidates, len, target, 0, new ArrayDeque<>(), res);
		return res;
	}
	private void dfs2(int[] candidates, int len, int residue, int begin, Deque<Integer> path, List<List<Integer>> res) {
		if (residue == 0) {
			res.add(new ArrayList<>(path));
			return ;
		}
		for (int i = begin; i < len; i++) {
			int aim = residue - candidates[i];
			// 在数组有序的前提下，剪枝
			if (aim < 0) {
				break;
			}
			//当前值，等于了开始的值，剪枝
			if (i>begin && candidates[i]==candidates[i-1]) {
				continue;
			}
			int index = i+1;
			path.addLast(candidates[i]);
			dfs2(candidates, len, aim, index, path, res);
			path.removeLast();
		}
	}
	
	//恢复空格
	@Test
	public void test20() {
		Assert.assertEquals(7,respace(new String[] {"looked","just","like","her","brother"}, "jesslookedjustliketimherbrother"));
	}
	public int respace(String[] dictionary, String sentence) {
		int n = sentence.length();
		Trie root = new Trie();
		for (String word:dictionary) {
			root.insert(word);
		}
		int[] dp = new int[n+1];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 0;
		for (int i=1;i<=n;i++) {
			dp[i] = dp[i-1]+1;
			
			Trie curPos = root;
			for (int j=i;j>=1;j--) {
				int t = sentence.charAt(j-1)-'a';
				//没有找到前缀，说明该单词不在字典中
				if (curPos.next[t] == null) {
					break;
				} else if(curPos.next[t].isEnd) {
					dp[i] = Math.min(dp[i], dp[j-1]);
				}
				if (dp[i] == 0) {
					break;
				}
				curPos = curPos.next[t];
			}
		}
		return dp[n];
	}
	//字典树
	class Trie {
		public Trie[] next; // 子 节点
		public boolean isEnd;  //是否到了单词卫
		
		public Trie() {
			next = new Trie[26];//不区分大小写，26个字母
			isEnd = false;
		}
		
		public void insert(String s) {
			Trie curPos = this;
			//倒着插入字母
			for (int i=s.length()-1;i>=0;--i) {
				int t = s.charAt(i)-'a';
				if (curPos.next[t]==null) {
					curPos.next[t] = new Trie();
				}
				curPos = curPos.next[t];
			}
			//标记该位置为单词结束
			curPos.isEnd = true;
		}
	}
}
