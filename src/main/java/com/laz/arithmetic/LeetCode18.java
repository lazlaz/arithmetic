package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode18 {
	// 最大间距
	@Test
	public void test1() {
		Assert.assertEquals(3, maximumGap(new int[] { 3, 6, 9, 1 }));
	}

	public int maximumGap(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		Arrays.sort(nums);
		int max = Integer.MIN_VALUE;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] - nums[i - 1] > max) {
				max = nums[i] - nums[i - 1];
			}
		}
		return max;
	}

	// 翻转对
	@Test
	public void test2() {
		Assert.assertEquals(2, new Solution2().reversePairs(new int[] { 1, 3, 2, 3, 1 }));

		Assert.assertEquals(3, new Solution2().reversePairs(new int[] { 2, 4, 3, 5, 1 }));

		Assert.assertEquals(1, new Solution2().reversePairs(new int[] { -5, -5 }));
		Assert.assertEquals(0, new Solution2()
				.reversePairs(new int[] { 2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647 }));
	}

	// https://leetcode-cn.com/problems/reverse-pairs/solution/fan-zhuan-dui-by-leetcode-solution/
	class Solution2 {
		public int reversePairs(int[] nums) {
			if (nums.length == 0) {
				return 0;
			}
			return reversePairsRecursive(nums, 0, nums.length - 1);
		}

		public int reversePairsRecursive(int[] nums, int left, int right) {
			if (left == right) {
				return 0;
			} else {
				int mid = (left + right) / 2;
				int n1 = reversePairsRecursive(nums, left, mid);
				int n2 = reversePairsRecursive(nums, mid + 1, right);
				int ret = n1 + n2;

				// 首先统计下标对的数量
				int i = left;
				int j = mid + 1;
				while (i <= mid) {
					while (j <= right && (long) nums[i] > 2 * (long) nums[j]) {
						j++;
					}
					ret += j - mid - 1;
					i++;
				}

				// 随后合并两个排序数组
				int[] sorted = new int[right - left + 1];
				int p1 = left, p2 = mid + 1;
				int p = 0;
				while (p1 <= mid || p2 <= right) {
					if (p1 > mid) {
						sorted[p++] = nums[p2++];
					} else if (p2 > right) {
						sorted[p++] = nums[p1++];
					} else {
						if (nums[p1] < nums[p2]) {
							sorted[p++] = nums[p1++];
						} else {
							sorted[p++] = nums[p2++];
						}
					}
				}
				for (int k = 0; k < sorted.length; k++) {
					nums[left + k] = sorted[k];
				}
				return ret;
			}
		}
	}

	// 三角形的最大周长
	@Test
	public void test3() {
		Assert.assertEquals(5, largestPerimeter(new int[] { 2, 1, 2 }));
	}

	// https://leetcode-cn.com/problems/largest-perimeter-triangle/solution/san-jiao-xing-de-zui-da-zhou-chang-by-leetcode-sol/
	public int largestPerimeter(int[] A) {
		Arrays.sort(A);
		for (int i = A.length - 1; i >= 2; --i) {
			if (A[i - 2] + A[i - 1] > A[i]) {
				return A[i - 2] + A[i - 1] + A[i];
			}
		}
		return 0;
	}

	// 重构字符串
	@Test
	public void test4() {
		Assert.assertEquals("aba", new Solution4().reorganizeString("aab"));
		Assert.assertEquals("", new Solution4().reorganizeString("aaab"));
		Assert.assertEquals("acabababab", new Solution4().reorganizeString("aaaaabbbbc"));
		Assert.assertEquals("vovlv", new Solution4().reorganizeString("vvvlo"));
		Assert.assertEquals("ababababab", new Solution4().reorganizeString("abbabbaaab"));
		Assert.assertEquals("czcxmwbtcsmlombockgi", new Solution4().reorganizeString("ogccckcwmbmxtsbmozli"));
	}

	class Solution4 {
		public String reorganizeString(String S) {
			int[] arr = new int[26];
			for (int i = 0; i < S.length(); i++) {
				char c = S.charAt(i);
				arr[c - 'a']++;
			}

			StringBuilder sb = new StringBuilder();
			while (sb.length() < S.length()) {
				int max = 0;
				int maxIndex = -1;
				int min = Integer.MAX_VALUE;
				int minIndex = -1;
				// 找到最多的字符
				for (int i = 0; i < arr.length; i++) {
					if (arr[i] > max) {
						max = arr[i];
						maxIndex = i;
					}

				}
				// 找到最少的字符
				for (int i = 0; i < arr.length; i++) {
					if (arr[i] > 0 && arr[i] <= min && i != maxIndex) {
						min = arr[i];
						minIndex = i;
					}
				}
				// 依次最多的字符最少的字符，循环
				char maxC = (char) (maxIndex + 'a');
				sb.append(maxC);
				arr[maxIndex]--;
				max--;
				if (minIndex != -1 && maxIndex != minIndex) {
					char minC = (char) (minIndex + 'a');
					sb.append(minC);
					min--;
					arr[minIndex]--;
				}
				int len = sb.length();
				if (len > 1 && (sb.charAt(len - 1) == sb.charAt(len - 2))) {
					return "";
				}
			}
			return sb.toString();
		}

	}

	// 拼接最大数
	@Test
	public void test5() {
		Assert.assertArrayEquals(new int[] { 9, 8, 6, 5, 3 },
				new Solution5().maxNumber(new int[] { 3, 4, 6, 5 }, new int[] { 9, 1, 2, 5, 8, 3 }, 5));
	}

	// https://leetcode-cn.com/problems/create-maximum-number/solution/pin-jie-zui-da-shu-by-leetcode-solution/
	class Solution5 {
		public int[] maxNumber(int[] nums1, int[] nums2, int k) {
			int m = nums1.length, n = nums2.length;
			int[] maxSubsequence = new int[k];
			int start = Math.max(0, k - n), end = Math.min(k, m);
			for (int i = start; i <= end; i++) {
				int[] subsequence1 = maxSubsequence(nums1, i);
				int[] subsequence2 = maxSubsequence(nums2, k - i);
				int[] curMaxSubsequence = merge(subsequence1, subsequence2);
				if (compare(curMaxSubsequence, 0, maxSubsequence, 0) > 0) {
					System.arraycopy(curMaxSubsequence, 0, maxSubsequence, 0, k);
				}
			}
			return maxSubsequence;
		}

		public int[] maxSubsequence(int[] nums, int k) {
			int length = nums.length;
			int[] stack = new int[k];
			int top = -1;
			int remain = length - k;
			for (int i = 0; i < length; i++) {
				int num = nums[i];
				while (top >= 0 && stack[top] < num && remain > 0) {
					top--;
					remain--;
				}
				if (top < k - 1) {
					stack[++top] = num;
				} else {
					remain--;
				}
			}
			return stack;
		}

		public int[] merge(int[] subsequence1, int[] subsequence2) {
			int x = subsequence1.length, y = subsequence2.length;
			if (x == 0) {
				return subsequence2;
			}
			if (y == 0) {
				return subsequence1;
			}
			int mergeLength = x + y;
			int[] merged = new int[mergeLength];
			int index1 = 0, index2 = 0;
			for (int i = 0; i < mergeLength; i++) {
				if (compare(subsequence1, index1, subsequence2, index2) > 0) {
					merged[i] = subsequence1[index1++];
				} else {
					merged[i] = subsequence2[index2++];
				}
			}
			return merged;
		}

		public int compare(int[] subsequence1, int index1, int[] subsequence2, int index2) {
			int x = subsequence1.length, y = subsequence2.length;
			while (index1 < x && index2 < y) {
				int difference = subsequence1[index1] - subsequence2[index2];
				if (difference != 0) {
					return difference;
				}
				index1++;
				index2++;
			}
			return (x - index1) - (y - index2);
		}
	}

	// 翻转矩阵后的得分
	@Test
	public void test6() {
		Assert.assertEquals(39, matrixScore(new int[][] { { 0, 0, 1, 1 }, { 1, 0, 1, 0 }, { 1, 1, 0, 0 } }));
	}

	// https://leetcode-cn.com/problems/score-after-flipping-matrix/solution/fan-zhuan-ju-zhen-hou-de-de-fen-by-leetc-cxma/
	public int matrixScore(int[][] A) {
		int m = A.length, n = A[0].length;
		int ret = m * (1 << (n - 1));

		for (int j = 1; j < n; j++) {
			int nOnes = 0;
			for (int i = 0; i < m; i++) {
				if (A[i][0] == 1) {
					nOnes += A[i][j];
				} else {
					nOnes += (1 - A[i][j]); // 如果这一行进行了行反转，则该元素的实际取值为 1 - A[i][j]
				}
			}
			int k = Math.max(nOnes, m - nOnes);
			ret += k * (1 << (n - j - 1));
		}
		return ret;
	}

	// 将数组拆分成斐波那契序列
	@Test
	public void test7() {
		{
			List<Integer> res = new Solution7().splitIntoFibonacci("123456579");
			Assert.assertEquals("123,456,579", Joiner.on(",").join(res));
		}
	}

	// https://leetcode-cn.com/problems/split-array-into-fibonacci-sequence/solution/javahui-su-suan-fa-tu-wen-xiang-jie-ji-b-vg5z/
	class Solution7 {
		public List<Integer> splitIntoFibonacci(String S) {
			List<Integer> res = new ArrayList<>();
			backtrack(S.toCharArray(), res, 0);
			return res;
		}

		public boolean backtrack(char[] digit, List<Integer> res, int index) {
			// 边界条件判断，如果截取完了，并且res长度大于等于3，表示找到了一个组合。
			if (index == digit.length && res.size() >= 3) {
				return true;
			}
			for (int i = index; i < digit.length; i++) {
				// 两位以上的数字不能以0开头
				if (digit[index] == '0' && i > index) {
					break;
				}
				// 截取字符串转化为数字
				long num = subDigit(digit, index, i + 1);
				// 如果截取的数字大于int的最大值，则终止截取
				if (num > Integer.MAX_VALUE) {
					break;
				}
				int size = res.size();
				// 如果截取的数字大于res中前两个数字的和，说明这次截取的太大，直接终止，因为后面越截取越大
				if (size >= 2 && num > res.get(size - 1) + res.get(size - 2)) {
					break;
				}
				if (size <= 1 || num == res.get(size - 1) + res.get(size - 2)) {
					// 把数字num添加到集合res中
					res.add((int) num);
					// 如果找到了就直接返回
					if (backtrack(digit, res, i + 1))
						return true;
					// 如果没找到，就会走回溯这一步，然后把上一步添加到集合res中的数字给移除掉
					res.remove(res.size() - 1);
				}
			}
			return false;
		}

		// 相当于截取字符串S中的子串然后转换为十进制数字
		private long subDigit(char[] digit, int start, int end) {
			long res = 0;
			for (int i = start; i < end; i++) {
				res = res * 10 + digit[i] - '0';
			}
			return res;
		}
	}

	// 柠檬水找零
	@Test
	public void test8() {
		Assert.assertEquals(true, lemonadeChange2(new int[] { 5, 5, 5, 10, 20 }));

		Assert.assertEquals(true, lemonadeChange2(new int[] { 5, 5, 10 }));

		Assert.assertEquals(false, lemonadeChange2(new int[] { 10, 10 }));
		Assert.assertEquals(false, lemonadeChange2(new int[] { 5, 5, 10, 10, 20 }));
	}

	// https://leetcode-cn.com/problems/lemonade-change/solution/ning-meng-shui-zhao-ling-by-leetcode-sol-nvp7/
	public boolean lemonadeChange2(int[] bills) {
		int five = 0, ten = 0;
		for (int bill : bills) {
			if (bill == 5) {
				five++;
			} else if (bill == 10) {
				if (five == 0) {
					return false;
				}
				five--;
				ten++;
			} else {
				if (five > 0 && ten > 0) {
					five--;
					ten--;
				} else if (five >= 3) {
					five -= 3;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	public boolean lemonadeChange(int[] bills) {
		Map<Integer, Integer> value = new HashMap<Integer, Integer>();
		boolean res = true;
		for (int i = 0; i < bills.length; i++) {
			if (bills[i] == 5) {
				int v = value.getOrDefault(5, 0);
				value.put(5, ++v);
			} else if (bills[i] == 10) {
				int v = value.getOrDefault(5, 0);
				if (v == 0) {
					return false;
				}
				value.put(5, --v);
				int v1 = value.getOrDefault(10, 0);
				value.put(10, ++v1);
			} else if (bills[i] == 20) {
				// 先招10元的，在招5元
				int v = value.getOrDefault(10, 0);
				if (v == 0) {
					// 招3个5元的
					v = value.getOrDefault(5, 0);
					if (v < 3) {
						return false;
					}
					value.put(5, (v - 3));
				} else {
					int v1 = value.getOrDefault(5, 0);
					if (v1 == 0) {
						return false;
					}
					value.put(5, --v1);
					value.put(10, --v);
				}
			}
		}
		return res;
	}

	//// Dota2 参议院
	@Test
	public void test9() {
		// Assert.assertEquals("Radiant", predictPartyVictory("RD"));
		Assert.assertEquals("Dire", predictPartyVictory("RDD"));
		Assert.assertEquals("Dire", predictPartyVictory("DDRRR"));
	}

	// https://leetcode-cn.com/problems/dota2-senate/solution/dota2-can-yi-yuan-by-leetcode-solution-jb7l/
	public String predictPartyVictory(String senate) {
		int n = senate.length();
		Queue<Integer> rQueue = new LinkedList<Integer>();
		Queue<Integer> dQueue = new LinkedList<Integer>();
		for (int i = 0; i < n; i++) {
			if (senate.charAt(i) == 'R') {
				rQueue.offer(i);
			} else {
				dQueue.offer(i);
			}
		}
		while (!rQueue.isEmpty() && !dQueue.isEmpty()) {
			int rIndex = rQueue.poll();
			int dIndex = dQueue.poll();
			if (rIndex < dIndex) {
				rQueue.offer(rIndex + n);
			} else {
				dQueue.offer(dIndex + n);
			}
		}
		return rQueue.isEmpty() ? "Dire" : "Radiant";
	}

	// 摆动序列
	@Test
	public void test10() {
		Assert.assertEquals(6, wiggleMaxLength(new int[] { 1, 7, 4, 9, 2, 5 }));
	}

	// https://leetcode-cn.com/problems/wiggle-subsequence/solution/bai-dong-xu-lie-by-leetcode-solution-yh2m/
	public int wiggleMaxLength(int[] nums) {
		int n = nums.length;
		if (n < 2) {
			return n;
		}
		int[] down = new int[n];
		int[] up = new int[n];
		down[0] = 1;
		up[0] = 1;
		for (int i = 1; i < n; i++) {
			if (nums[i] > nums[i - 1]) {
				up[i] = Math.max(up[i - 1], down[i - 1] + 1);
				down[i] = down[i - 1];
			} else if (nums[i] < nums[i - 1]) {
				up[i] = up[i - 1];
				down[i] = Math.max(up[i - 1] + 1, down[i - 1]);
			} else {
				up[i] = up[i - 1];
				down[i] = down[i - 1];
			}
		}
		return Math.max(up[n - 1], down[n - 1]);
	}

	// 单调递增的数字
	@Test
	public void test11() {
		Assert.assertEquals(9, monotoneIncreasingDigits(10));
		Assert.assertEquals(1234, monotoneIncreasingDigits(1234));
		Assert.assertEquals(299, monotoneIncreasingDigits(332));
	}

	public int monotoneIncreasingDigits(int N) {
		String nStr = String.valueOf(N);
		int idx = 0, max = -1;
		char[] chars = nStr.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			int v = chars[i] - '0';
			if (max < v) {
				idx = i;
				max = v;
			}
			if (i > 0 && chars[i] < chars[i - 1]) {
				chars[idx] = (char) (chars[idx] - 1);
				for (int j = idx + 1; j < chars.length; j++) {
					chars[j] = '9';
				}
				break;
			}
		}
		return Integer.valueOf(new String(chars));
	}

	// 389. 找不同
	@Test
	public void test12() {
		Assert.assertEquals('e', findTheDifference("abcd", "abcde"));
	}

	public char findTheDifference(String s, String t) {
		int[] sArr = new int[26];
		int[] tArr = new int[26];
		for (int i = 0; i < s.length(); i++) {
			sArr[s.charAt(i) - 'a']++;
		}
		for (int i = 0; i < t.length(); i++) {
			tArr[t.charAt(i) - 'a']++;
		}
		for (int i = 0; i < sArr.length; i++) {
			if (tArr[i] > sArr[i]) {
				return (char) (i + 'a');
			}
		}
		return ' ';
	}

	// 316. 去除重复字母
	@Test
	public void test13() {
		Assert.assertEquals("abc", removeDuplicateLetters("bcabc"));
		Assert.assertEquals("acdb", removeDuplicateLetters("cbacdcbc"));
	}

	// https://leetcode-cn.com/problems/remove-duplicate-letters/solution/zhan-by-liweiwei1419/
	public String removeDuplicateLetters(String s) {
		int len = s.length();
		int[] lastIndex = new int[26];
		for (int i = 0; i < len; i++) {
			lastIndex[s.charAt(i) - 'a'] = i;
		}
		Deque<Character> stack = new LinkedList<Character>();
		for (int i = 0; i < len; i++) {
			char currentChar = s.charAt(i);
			if (stack.contains(currentChar)) {
				continue;
			}
			// 如果当前字符，小于栈中的值出栈。且出栈的值后面还需要出现
			while (!stack.isEmpty() && currentChar < stack.peek() && lastIndex[stack.peek() - 'a'] > i) {
				stack.pop();
			}
			stack.push(currentChar);

		}
		StringBuilder stringBuilder = new StringBuilder();
		while (!stack.isEmpty()) {
			stringBuilder.append(stack.pollLast());
		}
		return stringBuilder.toString();
	}

	// 使用最小花费爬楼梯
	@Test
	public void test14() {
		Assert.assertEquals(15, minCostClimbingStairs(new int[] { 10, 15, 20 }));
		Assert.assertEquals(6, minCostClimbingStairs(new int[] { 1, 100, 1, 1, 1, 100, 1, 1, 100, 1 }));
	}

	public int minCostClimbingStairs(int[] cost) {
		int n = cost.length;
		int[] dp = new int[n + 1];
		dp[0] = cost[0];
		dp[1] = cost[1];
		for (int i = 2; i < n; i++) {
			dp[i] = Math.min(dp[i - 1] + cost[i], dp[i - 2] + cost[i]);
		}
		dp[n] = Math.min(dp[n - 1], dp[n - 2]);
		return dp[n];
	}

	// 135. 分发糖果
	@Test
	public void test15() {
		Assert.assertEquals(13, candy(new int[] { 5, 7, 8, 3, 4, 2, 1 }));
	}

	// https://leetcode-cn.com/problems/candy/solution/candy-cong-zuo-zhi-you-cong-you-zhi-zuo-qu-zui-da-/
	public int candy(int[] ratings) {

		int n = ratings.length;
		int[] left = new int[n];
		int[] right = new int[n];
		int ret = 0;
		Arrays.fill(left, 1);
		Arrays.fill(right, 1);
		for (int i = 1; i < n; i++) {
			if (ratings[i] > ratings[i - 1]) {
				left[i] = left[i - 1] + 1;
			}
		}
		for (int i = n - 2; i >= 0; i--) {
			if (ratings[i] > ratings[i + 1]) {
				right[i] = right[i + 1] + 1;
			}
		}
		for (int i = 0; i < n; i++) {
			ret += Math.max(left[i], right[i]);
		}
		return ret;
	}

	// 330. 按要求补齐数组
	@Test
	public void test16() {
		// Assert.assertEquals(1, minPatches(new int[] {1,3}, 6));
		Assert.assertEquals(2, minPatches(new int[] { 1, 5, 10 }, 20));
	}

	// https://leetcode-cn.com/problems/patching-array/solution/an-yao-qiu-bu-qi-shu-zu-by-leetcode-solu-klp1/
	public int minPatches(int[] nums, int n) {
		int patches = 0;
		long x = 1;
		int length = nums.length, index = 0;
		while (x <= n) {
			if (index < length && nums[index] <= x) {
				x += nums[index];
				index++;
			} else {
				x *= 2;
				patches++;
			}
		}
		return patches;
	}

	// 1046. 最后一块石头的重量
	@Test
	public void test17() {
		Assert.assertEquals(1, lastStoneWeight(new int[] { 2, 7, 4, 1, 8, 1 }));
	}

	public int lastStoneWeight(int[] stones) {
		Arrays.sort(stones);
		while (stones.length > 1) {
			int n = stones.length;
			int x = stones[n - 1];
			int y = stones[n - 2];
			if (x != y) {
				int newV = x - y;
				stones[n - 2] = newV;
				int[] newArr = new int[n - 1];
				System.arraycopy(stones, 0, newArr, 0, n - 1);
				Arrays.sort(newArr);
				stones = newArr;
			} else {
				int[] newArr = new int[n - 2];
				System.arraycopy(stones, 0, newArr, 0, n - 2);
				stones = newArr;
			}
		}
		return stones.length == 1 ? stones[0] : 0;
	}

	// 605. 种花问题
	@Test
	public void test18() {
		Assert.assertEquals(true, canPlaceFlowers(new int[] { 1, 0, 0, 0, 1 }, 1));
		Assert.assertEquals(false, canPlaceFlowers(new int[] { 1, 0, 0, 0, 1 }, 2));
	}

	public boolean canPlaceFlowers(int[] flowerbed, int n) {
		if (n == 0) {
			return true;
		}
		if (flowerbed == null || flowerbed.length == 0) {
			return false;
		}
		if (flowerbed.length == 1)
			if (flowerbed[0] == 1) {
				return false;
			} else {
				if (n == 1) {
					return true;
				} else {
					return false;
				}
			}
		int count = 0;
		for (int i = 0; i < flowerbed.length; i++) {
			if (i == 0) {
				if (flowerbed[i] == 0 && flowerbed[i + 1] == 0) {
					count++;
					flowerbed[i] = 1;
				}
			} else if (i == flowerbed.length - 1) {
				if (flowerbed[i - 1] == 0 && flowerbed[i] == 0) {
					count++;
					flowerbed[i] = 1;
				}
			} else {
				if (flowerbed[i - 1] == 0 && flowerbed[i] == 0 && flowerbed[i + 1] == 0) {
					count++;
					flowerbed[i] = 1;
				}
			}
			if (count >= n) {
				return true;
			}
		}
		return false;
	}

	// 239. 滑动窗口最大值
	@Test
	public void test19() {
		Assert.assertArrayEquals(new int[] { 3, 3, 5, 5, 6, 7 },
				maxSlidingWindow(new int[] { 1, 3, -1, -3, 5, 3, 6, 7 }, 3));
	}

	// https://leetcode-cn.com/problems/sliding-window-maximum/solution/hua-dong-chuang-kou-zui-da-zhi-by-leetco-ki6m/
	public int[] maxSlidingWindow(int[] nums, int k) {
		int n = nums.length;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] pair1, int[] pair2) {
                return pair1[0] != pair2[0] ? pair2[0] - pair1[0] : pair2[1] - pair1[1];
            }
        });
        for (int i = 0; i < k; ++i) {
            pq.offer(new int[]{nums[i], i});
        }
        int[] ans = new int[n - k + 1];
        ans[0] = pq.peek()[0];
        for (int i = k; i < n; ++i) {
            pq.offer(new int[]{nums[i], i});
            while (pq.peek()[1] <= i - k) {
                pq.poll();
            }
            ans[i - k + 1] = pq.peek()[0];
        }
        return ans;
	}
}
