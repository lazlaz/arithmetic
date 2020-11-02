package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

import junit.framework.TestCase;

public class LeetCode5 {
	// 拥有最多糖果的孩子
	@Test
	public void test1() {
		int[] candies = new int[] { 2, 3, 5, 1, 3 };
		int extraCandies = 3;
		List<Boolean> list = kidsWithCandies(candies, extraCandies);
		for (Boolean b : list) {
			System.out.println(b);
		}
	}

	public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
		int max = 0;
		for (int i = 0; i < candies.length; i++) {
			if (max < candies[i]) {
				max = candies[i];
			}
		}
		List<Boolean> list = new ArrayList<Boolean>();
		for (int i = 0; i < candies.length; i++) {
			if (candies[i] + extraCandies >= max) {
				list.add(true);
			} else {
				list.add(false);
			}
		}
		return list;
	}

	@Test
	// 除自身以外数组的乘积
	public void test2() {
		int[] nums = new int[] { 1, 2, 3, 4 };
		int[] res = productExceptSelf(nums);
		for (int i : res) {
			System.out.println(i);
		}
	}

	// 当前数左边的乘积 * 当前数右边的乘积
	public int[] productExceptSelf(int[] nums) {
		int length = nums.length;
		// L 和 R 分别表示左右两侧的乘积列表
		int[] L = new int[length];
		int[] R = new int[length];
		int[] answer = new int[length];
		L[0] = 1;
		for (int i = 1; i < length; i++) {
			L[i] = nums[i - 1] * L[i - 1];
		}
		R[length - 1] = 1;
		for (int i = length - 2; i >= 0; i--) {
			R[i] = nums[i + 1] * R[i + 1];
		}
		for (int i = 0; i < length; i++) {
			answer[i] = L[i] * R[i];
		}
		return answer;
	}

	@Test
	// 顺时针打印矩阵
	public void test3() {
		int[][] matrix = new int[][] { { 3 }, { 2 }, { 1 } };
		int[] res = spiralOrder(matrix);
		for (int i : res) {
			System.out.print(i + " ");
		}
	}

	public int[] spiralOrder(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return new int[0];
		}
		int rows = matrix.length, columns = matrix[0].length;
		int[] order = new int[rows * columns];
		int index = 0;
		int left = 0, right = columns - 1, top = 0, bottom = rows - 1;
		while (left <= right && top <= bottom) {
			// 遍历上方
			for (int column = left; column <= right; column++) {
				order[index++] = matrix[top][column];
			}
			// 遍历右方
			for (int row = top + 1; row <= bottom; row++) {
				order[index++] = matrix[row][right];
			}
			if (left < right && top < bottom) {
				// 遍历下方
				for (int column = right - 1; column > left; column--) {
					order[index++] = matrix[bottom][column];
				}
				// 遍历左方
				for (int row = bottom; row > top; row--) {
					order[index++] = matrix[row][left];
				}
			}
			left++;
			right--;
			top++;
			bottom--;
		}
		return order;
	}

	@Test
	// 反转字符串中的元音字母
	public void test4() {
		System.out.println(reverseVowels("hello"));
	}

	public String reverseVowels(String s) {
		String letter = "aeiouAEIOU";
		char[] res = new char[s.length()];
		LinkedList<Character> tmp = new LinkedList<Character>();
		int i = 0;
		for (char c : s.toCharArray()) {
			res[i++] = c;
			if (letter.indexOf(c) != -1) {
				tmp.push(c);
			}
		}
		for (int j = 0; j < res.length; j++) {
			if (letter.indexOf(res[j]) != -1) {
				res[j] = tmp.pop();
			}
		}
		return new String().valueOf(res);

	}

	public String reverseVowels2(String s) {
		String strs = "aeiouAEIOU";
		LinkedList<Character> stack = new LinkedList<Character>();
		for (char c : s.toCharArray()) {
			if (strs.indexOf(c) != -1) {
				stack.push(c);
			}
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.toCharArray().length; i++) {
			char c1 = s.charAt(i);
			if (strs.indexOf(c1) != -1) {
				sb.append(stack.poll());
			} else {
				sb.append(c1);
			}
		}
		return sb.toString();
	}

	@Test
	// 两个数组的交集
	public void test5() {
		int[] num1 = new int[] { 1, 2, 2, 1 };
		int[] num2 = new int[] { 2, 2 };
		int[] res = intersection2(num1, num2);
		for (int i : res) {
			System.out.println(i);
		}
	}
	//利用双指针提高速度
	public int[] intersection2(int[] nums1, int[] nums2) {
		Arrays.sort(nums1);
		Arrays.sort(nums2);
		Set<Integer> set = new HashSet<Integer>();
		int p1 = 0;
		int p2 =0;
		while (p1<nums1.length && p2<nums2.length) {
			if (nums1[p1] == nums2[p2]) {
				set.add(nums1[p1]);
				p1++;
				p2++;
			}else if (nums1[p1]>nums2[p2]) {
				p2++;
			}else if (nums1[p1]<nums2[p2]) {
				p1++;
			}
		}
		return set.stream().mapToInt(Integer::valueOf).toArray();
	}
	public int[] intersection(int[] nums1, int[] nums2) {
		Arrays.parallelSort(nums1);
		Arrays.parallelSort(nums2);
		int[] res = null;
		int[] res2 = null;
		if (nums1.length < nums2.length) {
			res = nums1;
			res2 = nums2;
		} else {
			res = nums2;
			res2 = nums1;
		}
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < res.length; i++) {
			if (list.contains(res[i])) {
				continue;
			}
			for (int j = 0; j < res2.length; j++) {
				if (res2[j] == res[i]) {
					list.add(res[i]);
					break;
				}
				if (res2[j] > res[i]) {
					break;
				}
			}
		}
		return list.stream().mapToInt(Integer::valueOf).toArray();
	}

	@Test
	// 等式方程的可满足性
	public void test6() {
		String[] equations = new String[] { "a==b", "b!=c", "c==a" };
		System.out.println(equationsPossible(equations));
	}

	public boolean equationsPossible(String[] equations) {
		int[] parent = new int[26];
		for (int i = 0; i < 26; i++) {
			parent[i] = i;
		}
		// 根据=构造连通分量
		for (String str : equations) {
			if (str.charAt(1) == '=') {
				int index1 = str.charAt(0) - 'a';
				int index2 = str.charAt(3) - 'a';
				union(parent, index1, index2);
			}
		}
		for (String str : equations) {
			if (str.charAt(1) == '!') {
				int index1 = str.charAt(0) - 'a';
				int index2 = str.charAt(3) - 'a';
				if (find(parent, index1) == find(parent, index2)) {
					return false;
				}
			}
		}
		return true;
	}

	private void union(int[] parent, int index1, int index2) {
		parent[find(parent, index1)] = find(parent, index2);

	}

	private int find(int[] parent, int index) {
		while (parent[index] != index) {
			parent[index] = parent[parent[index]];
			index = parent[index];
		}
		return index;
	}

	static Map<Integer, String> numsMap = new HashMap<Integer, String>();
	static {
		numsMap.put(0, "零");
		numsMap.put(1, "一");
		numsMap.put(2, "二");
		numsMap.put(3, "三");
		numsMap.put(4, "四");
		numsMap.put(5, "五");
		numsMap.put(6, "六");
		numsMap.put(7, "七");
		numsMap.put(8, "八");
		numsMap.put(9, "九");
	}

	@Test
	// 读数 将数字1111转为为中文数字
	public void test7() {
		// 最大支持2147483647
		String str = "1001010";
		for (Integer k : testmap.keySet()) {
			System.out.println(k + ":" + convert(k + ""));
			TestCase.assertEquals(testmap.get(k), convert(k + ""));
		}
	}

	// 本函数来自 自己做letcode 字符串转换整数 (atoi)答案，只支持32位整数内转换
	public int strToInt(String str) {
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		boolean falg = true;
		boolean cut = false;
		boolean pOrm = false;
		int count = 0;
		boolean zero = false;
		boolean findChar = false;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != 32) {
				falg = false;
			}
			if (!falg) {
				if (!findChar && sb.length() == 0 && chars[i] == 45) {
					count++;
					pOrm = true;
				} else if (!findChar && sb.length() == 0 && chars[i] == 43) {
					count++;
					pOrm = false;
				} else {
					if (count == 2) {
						return 0;
					}
					findChar = true;
					if (!zero && chars[i] == 48) {
						continue;
					}
					zero = true;
					if (48 <= chars[i] && chars[i] <= 57) {
						sb.append(chars[i]);
					} else {
						cut = true;
					}
				}
			}

			if (cut) {
				break;
			}
		}
		if (sb.length() == 0) {
			return 0;
		}
		if (sb.toString().length() > 10) {
			return pOrm ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		}
		long value = pOrm ? -Long.valueOf(sb.toString()) : Long.valueOf(sb.toString());
		if (value > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		if (value < Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		return (int) value;
	}

	public String convert(String str) {
		int value = strToInt(str);
		StringBuffer sb = new StringBuffer();
		int count = 1;
		int lastV = 0; // 判断是否持续为0
		boolean falg = false;
		while (value / 10 != 0) {
			int v = value % 10;
			// 万到千万连续为0，不插入单位
			if (count == 5 || count == 6 || count == 7 || count == 8) {
				if (v != 0) {
					falg = true;
				}
			}
			if (v != 0 || count % 4 == 1) {
				sb.insert(0, getUnit(count));
			}
			if (!(lastV == 0 && v == 0) && !(count >= 5 && count % 4 == 1)) {
				sb.insert(0, getChinese(v));
			}
			lastV = v;
			count++;
			value = value / 10;
		}
		// 万到千万连续为0，不插入单位
		if (count == 5 || count == 6 || count == 7 || count == 8) {
			if (value != 0) {
				falg = true;
			}
		}
		sb.insert(0, getUnit(count));
		sb.insert(0, getChinese(value));
		String res = sb.toString();
		// 如果万到千万数字全为0，移除万
		if (!falg) {
			res = res.replace("万", "");
		}
		return res;
	}

	private String getUnit(int count) {
		String unit = "";

		if (count == 5) {
			unit = "万";
		}
		if (count == 9) {
			unit = "亿";
		}
		if (count == 8) {
			unit = "千";
		}
		if (count == 7) {
			unit = "百";
		}
		if (count == 6) {
			unit = "十";
		}
		if (count == 2) {
			unit = "十";
		}
		if (count == 3) {
			unit = "百";
		}
		if (count == 4) {
			unit = "千";
		}
		return unit;
	}

	public String getChinese(int v) {
		return numsMap.get(v);
	}

	static Map<Integer, String> testmap = new HashMap<Integer, String>();
	{
		testmap.put(0, "零");
		testmap.put(1, "一");
		testmap.put(2, "二");
		testmap.put(3, "三");
		testmap.put(4, "四");
		testmap.put(1020, "一千零二十");
		testmap.put(100000000, "一亿");
		testmap.put(1001001, "一百万一千零一");
		testmap.put(20001007, "二千万一千零七");
		testmap.put(10000000, "一千万");
		testmap.put(1015, "一千零一十五");
	}

	@Test
	// 读数 将数字1111转为为中文数字 参考网上解法
	// 参考：https://blog.csdn.net/sleepingboy888/article/details/95160730
	public void test8() {
		for (Integer k : testmap.keySet()) {
			System.out.println(k + ":" + numberToChinese(k));
			TestCase.assertEquals(numberToChinese(k), testmap.get(k));
		}
	}

	public String numberToChinese(int num) {
		String result = "";
		if (num == 0) {
			return "零";
		}
		int _num = num;
		String[] chn_str = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String[] section_value = new String[] { "", "万", "亿", "万亿" };
		String[] unit_value = new String[] { "", "十", "百", "千" };
		int section = _num % 10000;
		for (int i = 0; _num != 0 && i < 4; i++) {
			if (section == 0) {
				// 0不需要考虑节权值，不能出现连续的“零”
				if (result.length() > 0 && !result.substring(0, 1).equals("零")) {
					result = "零" + result;
				}
				_num = _num / 10000;
				section = _num % 10000;
				continue;
			}
			result = section_value[i] + result;
			int unit = section % 10;
			for (int j = 0; j < 4; j++) {
				if (unit == 0) {
					// 0不需要考虑位权值，不能出现联系的“零”，每节最后的0不需要
					if (result.length() > 0 && !result.substring(0, 1).equals("零")
							&& !result.substring(0, 1).equals(section_value[i])) {
						result = "零" + result;
					}
				} else {
					result = chn_str[unit] + unit_value[j] + result;
				}
				section = section / 10;
				unit = section % 10;
			}
			_num = _num / 10000;
			section = _num % 10000;
		}
		if (result.length() > 0 && result.substring(0, 1).equals("零")) {
			// 清理最前面的"零"
			result = result.substring(1);
		}
		return result;
	}

	@Test
	// 有效的完全平方数
	public void test9() {
		int num = 2147395600;
		System.out.println(isPerfectSquare(num));
	}

	public boolean isPerfectSquare2(int num) {
		int max = (int) Math.sqrt(Integer.MAX_VALUE) + 1;
		for (int i = 1; i < max; i++) {
			if (i * i == num) {
				System.out.println(i);
				return true;
			}
		}
		return false;
	}

	/*
	 * 1 4=1+3 9=1+3+5 16=1+3+5+7以此类推，模仿它可以使用一个while循环，不断减去一个从1开始不断增大的奇数，
	 * 若最终减成了0，说明是完全平方数，否则，不是
	 */
	public boolean isPerfectSquare(int num) {
		int num1 = 1;
		while (num > 0) {
			num -= num1;
			num1 += 2;
		}
		return num == 0;
	}

	@Test
	// 两数相加
	public void test10() {
		ListNode l1 = Utils.createListNode(new Integer[] { 5 });
		ListNode l2 = Utils.createListNode(new Integer[] { 5, 9 });
		ListNode l = addTwoNumbers(l1, l2);
		Utils.printListNode(l);
	}

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode l = new ListNode(-1);
		ListNode head = l1;
		l.next = head;
		int cb = 0;// 进位
		while (l1 != null) {
			int a = l1.val;
			int b = l2 == null ? 0 : l2.val;
			int v = (a + b + cb) % 10;
			cb = (a + b + cb) / 10;
			l1 = l1.next;
			l2 = l2 == null ? null : l2.next;
			ListNode node = new ListNode(v);
			head.next = node;
			head = node;
		}
		if (l2 != null) {
			head.next = l2;
			while (l2 != null) {
				int a = l2.val;
				int v = (a + cb) % 10;
				cb = (a + cb) / 10;
				l2 = l2.next;
				ListNode node = new ListNode(v);
				head.next = node;
				head = node;
			}
		}
		if (cb == 1) {
			ListNode node = new ListNode(cb);
			head.next = node;
		}
		return l.next.next;
	}

	@Test
	// 转变数组后最接近目标值的数组和
	public void test11() {
		int[] arr = new int[] { 1, 3, 5 };
		System.out.println(findBestValue(arr, 55));
	}

	public int findBestValue(int[] arr, int target) {
		Arrays.sort(arr);
		int n = arr.length;
		int[] prefix = new int[n + 1];
		for (int i = 1; i <= n; i++) {
			prefix[i] = prefix[i - 1] + arr[i - 1];
		}
		int l = 0, r = arr[n - 1], ans = -1;
		while (l <= r) {
			int mid = (l + r) / 2;
			int index = Arrays.binarySearch(arr, mid);
			if (index < 0) {
				index = -index - 1;
			}
			int cur = prefix[index] + (n - index) * mid;
			if (cur <= target) {
				ans = mid;
				l = mid + 1;
			} else {
				r = mid - 1;
			}
		}
		int small = check(arr, ans);
		int big = check(arr, ans + 1);
		return Math.abs(small - target) <= Math.abs(big - target) ? ans : ans + 1;
	}

	public int check(int[] arr, int x) {
		int ret = 0;
		for (int num : arr) {
			ret += Math.min(num, x);
		}
		return ret;
	}

	@Test
	// 无重复字符的最长子串
	public void test12() {
		System.out.println(lengthOfLongestSubstring("dvdf"));
	}

	public int lengthOfLongestSubstring(String s) {
		Set<Character> occ = new HashSet<Character>();
		int n = s.length();
		int rk = -1, ans = 0;
		for (int i = 0; i < n; i++) {
			if (i > 0) {
				occ.remove(s.charAt(i - 1));
			}
			while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
				occ.add(s.charAt(rk + 1));
				rk++;
			}
			ans = Math.max(ans, rk - i + 1);
		}
		return ans;

	}

	public int lengthOfLongestSubstring2(String s) {
		if (s == null || s.length() <= 0) {
			return 0;
		}
		int res = 1;
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < s.length(); i++) {
			if (list.contains(s.charAt(i) + "")) {
				res = res > list.size() ? res : list.size();
				int index = list.indexOf(s.charAt(i) + "");
				list = list.subList(index + 1, list.size());
			}
			list.add(s.charAt(i) + "");

		}
		res = res > list.size() ? res : list.size();
		return res;
	}

	@Test
	// 寻找两个正序数组的中位数
	public void test13() {
		int[] nums1 = new int[] { 10000 };
		int[] nums2 = new int[] { 10001 };
		System.out.println(findMedianSortedArrays(nums1, nums2));
	}

	public double getMedian(int length, int[] nums2) {
		if (length % 2 == 0) {
			return (nums2[length / 2 - 1] + nums2[length / 2]) / 2.0d;
		} else {
			return nums2[length / 2];
		}
	}

	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		if (nums1 == null || nums1.length <= 0) {
			return getMedian(nums2.length, nums2);
		}
		if (nums2 == null || nums2.length <= 0) {
			return getMedian(nums1.length, nums1);
		}
		int i = 0, j = 0;
		int length = nums1.length + nums2.length;
		int count = 0;
		int[] newArr = new int[length];
		while (i < nums1.length || j < nums2.length) {
			if (i < nums1.length && j < nums2.length) {
				if (nums1[i] >= nums2[j]) {
					newArr[count] = nums2[j];
					j++;
				} else {
					newArr[count] = nums1[i];
					i++;
				}
			} else {
				if (i >= nums1.length && j < nums2.length) {
					newArr[count] = nums2[j];
					j++;
				}
				if (j >= nums2.length && i < nums1.length) {
					newArr[count] = nums1[i];
					i++;
				}
			}
			if (count == length / 2) {
				return getMedian(newArr.length, newArr);
			}
			count++;
		}
		return 0;
	}

	@Test
	// 二叉树的序列化与反序列化
	public void test14() {
		Integer[] arr = new Integer[] {};
		TreeNode root = Utils.createTree(arr, 0);
		Codec c = new Codec();
		String str = c.serialize(root);
		System.out.println(str);
		TreeNode n = c.deserialize(str);
		System.out.println(c.serialize(n));
	}

	class Codec {
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

	@Test
	// 最佳观光组合
	public void test15() {
		int[] A = new int[] { 8, 1, 5, 2, 6 };
		System.out.println(maxScoreSightseeingPair(A));
	}

	public int maxScoreSightseeingPair(int[] A) {
		if (A == null || A.length <= 0) {
			return 0;
		}
		int mx = A[0];
		int ans = 0;
		for (int j = 1; j < A.length; j++) {
			if (ans < mx + A[j] - j) {
				ans = mx + A[j] - j;
			}
			if (mx < A[j] + j) {
				mx = A[j] + j;
			}
		}
		return ans;
	}

	// Z 字形变换
	@Test
	public void test16() {
		String s = "LEETCODEISHIRING";
		int numRows = 4;
		System.out.println(convert2(s, numRows));
	}

	public String convert2(String s, int numRows) {
		if (numRows == 1)
			return s;

		List<StringBuilder> rows = new ArrayList<>();
		for (int i = 0; i < Math.min(numRows, s.length()); i++)
			rows.add(new StringBuilder());

		int curRow = 0;
		boolean goingDown = false;

		for (char c : s.toCharArray()) {
			rows.get(curRow).append(c);
			if (curRow == 0 || curRow == numRows - 1)
				goingDown = !goingDown;
			curRow += goingDown ? 1 : -1;
		}

		StringBuilder ret = new StringBuilder();
		for (StringBuilder row : rows)
			ret.append(row);
		return ret.toString();
	}

	public String convert(String s, int numRows) {
		if (s == null || numRows <= 1) {
			return s;
		}
		List<List<String>> arr = new ArrayList<List<String>>();
		int index = 0;
		int col = 0;
		while (index < s.length()) {
			List<String> cols = new ArrayList<String>();
			for (int row = 0; row < numRows; row++) {
				if (index < s.length()) {
					if (col % (numRows - 1) == 0) {
						cols.add(s.charAt(index++) + "");
					} else if (row == (numRows - 1 - col % (numRows - 1))) {
						cols.add(s.charAt(index++) + "");
					} else {
						cols.add("");
					}
				}
			}
			col++;
			arr.add(cols);
		}
		StringBuffer sb = new StringBuffer();
		for (int c = 0; c < numRows; c++) {
			for (List<String> l : arr) {
				if (l.size() > c) {
					String v = l.get(c);
					sb.append(v);
				}
			}
		}

		return sb.toString();
	}

	// 从先序遍历还原二叉树
	@Test
	public void test17() {
		String s = "1-2--3--4-5--6--7";
		TreeNode root = recoverFromPreorder(s);
	}

	public TreeNode recoverFromPreorder(String S) {
		if (S == null || S.length() <= 0) {
			return null;
		}
		int index = 0;
		Deque<TreeNode> stack = new LinkedList<TreeNode>();
		while (index < S.length()) {
			int level = 0;
			while (S.charAt(index) == '-') {
				level++;
				index++;
			}
			int value = 0;
			while (index < S.length() && Character.isDigit(S.charAt(index))) {
				value = value * 10 + (S.charAt(index) - '0');
				++index;
			}
			TreeNode node = new TreeNode(value);
			if (level == stack.size()) {
				if (!stack.isEmpty()) {
					stack.peek().left = node;
				}
			} else {
				while (level != stack.size()) {
					stack.pop();
				}
				stack.peek().right = node;
			}
			stack.push(node);
		}
		while (stack.size() > 1) {
			stack.pop();
		}
		return stack.peek();
	}

	// 盛最多水的容器
	@Test
	public void test18() {
		int[] height = new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
		System.out.println(maxArea(height));
	}

	public int maxArea(int[] height) {
		int left = 0, right = height.length - 1;
		int maxArea = 0;
		while (left < right) {
			int v = Math.min(height[left], height[right]) * (right - left);
			if (maxArea < v) {
				maxArea = v;
			}
			if (height[left] < height[right]) {
				left++;
			} else {
				right--;
			}
		}
		return maxArea;
	}

	// 正则表达式匹配
	@Test
	public void test19() {
		String s = "aab";
		String p = "c*a*b";
		System.out.println(isMatch(s, p));
	}

	public boolean isMatch(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		int m = s.length();
		int n = p.length();
		boolean[][] dp = new boolean[m + 1][n + 1];
		dp[0][0] = true;
		for (int i = 0; i < p.length(); i++) { // here's the p's length, not s's
			if (p.charAt(i) == '*' && dp[0][i - 1]) {
				dp[0][i + 1] = true; // here's y axis should be i+1
			}
		}
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.') {
					dp[i][j] = dp[i - 1][j - 1];
				}
				if (p.charAt(j - 1) == '*') {
					if (s.charAt(i - 1) != p.charAt(j - 2) && p.charAt(j - 2) != '.') {
						dp[i][j] = dp[i][j - 2];
					} else {
						dp[i][j] = dp[i][j - 1] || dp[i][j - 2] || dp[i - 1][j];
					}
				}
			}
		}
		return dp[m][n];
	}

	// 二叉树中的最大路径和
	@Test
	public void test20() {
		Integer[] arr = new Integer[] { 5,4,8,11,null,13,4,7,2,null,null,null,1};
		TreeNode root = Utils.createTree(arr);
		System.out.println(maxPathSum(root));
		System.out.println(new Codec().serialize(root));
	}

	private int maxPath = Integer.MIN_VALUE;

	public int maxPathSum(TreeNode root) {
		dfs(root);
		return maxPath ;
	}
	
	public int dfs(TreeNode root) {
		if (root == null) {
			return 0;
		}
		 //计算左边分支最大值，左边分支如果为负数还不如不选择
        int leftMax = Math.max(0, dfs(root.left));
        //计算右边分支最大值，右边分支如果为负数还不如不选择
        int rightMax = Math.max(0, dfs(root.right));
        //left->root->right 作为路径与历史最大值做比较
        maxPath = Math.max(maxPath, root.val + leftMax + rightMax);
        // 返回经过root的单边最大分支给上游
        return root.val + Math.max(leftMax, rightMax);
	}
}
