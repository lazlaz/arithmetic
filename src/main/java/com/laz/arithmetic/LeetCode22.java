package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode22 {
	// 1738. 找出第 K 大的异或坐标值
	@Test
	public void test1() {
		Assert.assertEquals(7, new Solution1().kthLargestValue(new int[][] { { 5, 2 }, { 1, 6 } }, 1));
	}

	// https://leetcode-cn.com/problems/find-kth-largest-xor-coordinate-value/solution/zhao-chu-di-k-da-de-yi-huo-zuo-biao-zhi-mgick/
	class Solution1 {
		public int kthLargestValue(int[][] matrix, int k) {
			int m = matrix.length;
			int n = matrix[0].length;
			// 二维前缀和
			int[][] pre = new int[m + 1][n + 1];
			PriorityQueue<Integer> queue = new PriorityQueue<>(k);
			for (int i = 1; i <= m; ++i) {
				for (int j = 1; j <= n; ++j) {
					pre[i][j] = pre[i - 1][j] ^ pre[i][j - 1] ^ pre[i - 1][j - 1] ^ matrix[i - 1][j - 1];
					if (queue.size() < k) {
						queue.add(pre[i][j]);
					} else if (queue.peek() < pre[i][j]) {
						queue.poll();
						queue.add(pre[i][j]);
					}
				}
			}
			return queue.peek();
		}
	}

	// 692. 前K个高频单词
	@Test
	public void test2() {
		Assert.assertEquals("i,love", Joiner.on(",").join(
				new Solution2().topKFrequent(new String[] { "i", "love", "leetcode", "i", "love", "coding" }, 2)));
	}

	class Solution2 {
		class Word {
			String word;
			int count;
		}

		public List<String> topKFrequent(String[] words, int k) {
			Map<String, Integer> wordMap = new HashMap<String, Integer>();
			for (int i = 0; i < words.length; i++) {
				wordMap.put(words[i], wordMap.getOrDefault(words[i], 0) + 1);
			}
			PriorityQueue<Word> wordStack = new PriorityQueue<Word>(new Comparator<Word>() {

				@Override
				public int compare(Word o1, Word o2) {
					if (o1.count > o2.count) {
						return -1;
					}
					if (o1.count < o2.count) {
						return 1;
					}
					return o1.word.compareTo(o2.word);
				}
			});
			for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
				Word w = new Word();
				w.count = entry.getValue();
				w.word = entry.getKey();
				wordStack.add(w);
			}
			List<String> res = new ArrayList<>();
			int index = 0;
			while (!wordStack.isEmpty() && index < k) {
				res.add(wordStack.poll().word);
				index++;
			}
			return res;
		}
	}

	// 1035. 不相交的线
	@Test
	public void test3() {
		Assert.assertEquals(2, new Solution3().maxUncrossedLines(new int[] { 1, 4, 2 }, new int[] { 1, 2, 4 }));
	}

	// https://leetcode-cn.com/problems/uncrossed-lines/solution/bu-xiang-jiao-de-xian-by-leetcode-soluti-6tqz/
	class Solution3 {
		public int maxUncrossedLines(int[] nums1, int[] nums2) {
			// 最长公共子序列
			int m = nums1.length, n = nums2.length;
			int[][] dp = new int[m + 1][n + 1];
			for (int i = 1; i <= m; i++) {
				int num1 = nums1[i - 1];
				for (int j = 1; j <= n; j++) {
					int num2 = nums2[j - 1];
					if (num1 == num2) {
						dp[i][j] = dp[i - 1][j - 1] + 1;
					} else {
						dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
					}
				}
			}
			return dp[m][n];
		}
	}

	// 1190. 反转每对括号间的子串
	@Test
	public void test4() {
		Assert.assertEquals("iloveu", new Solution4().reverseParentheses("(u(love)i)"));
		Assert.assertEquals("dcba", new Solution4().reverseParentheses("(abcd)"));
		Assert.assertEquals("leetcode", new Solution4().reverseParentheses("(ed(et(oc))el)"));
		Assert.assertEquals("apmnolkjihgfedcbq", new Solution4().reverseParentheses("a(bcdefghijkl(mno)p)q"));
	}

	class Solution4 {
		public String reverseParentheses(String s) {
			if (s.length() == 0) {
				return s;
			}
			char[] chars = s.toCharArray();
			Deque<StringBuilder> stack = new LinkedList<>();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				if (c == '(') {
					stack.push(new StringBuilder(sb));
					sb = new StringBuilder();
				} else if (c == ')') {
					StringBuilder sb2 = stack.pop();
					sb2.append(sb.reverse().toString());
					sb = sb2;
				} else {
					sb.append(c);
				}
			}
			return sb.toString();
		}
	}

	// 477. 汉明距离总和
	@Test
	public void test5() {
		Assert.assertEquals(6, new Solution5().totalHammingDistance(new int[] { 4, 14, 2 }));
	}

	// https://leetcode-cn.com/problems/total-hamming-distance/solution/yi-ming-ju-chi-zong-he-by-leetcode-solut-t0ev/
	class Solution5 {
		public int totalHammingDistance(int[] nums) {
			int ans = 0, n = nums.length;
			for (int i = 0; i < 30; ++i) {
				int c = 0;
				for (int val : nums) {
					c += (val >> i) & 1;
				}
				ans += c * (n - c);
			}
			return ans;

		}
	}

	// 1744. 你能在你最喜欢的那天吃到你最喜欢的糖果吗？
	@Test
	public void test6() {
		Assert.assertArrayEquals(new boolean[] { true, false, true }, new Solution6()
				.canEat(new int[] { 7, 4, 5, 3, 8 }, new int[][] { { 0, 2, 2 }, { 4, 2, 4 }, { 2, 13, 1000000000 } }));
	}

	class Solution6 {
		public boolean[] canEat(int[] candiesCount, int[][] queries) {
			int n = candiesCount.length;
			// 前缀和 用long 范围
			long[] pre = new long[n + 1];
			for (int i = 1; i <= n; i++) {
				pre[i] = pre[i - 1] + candiesCount[i - 1];
			}
			int len = queries.length;
			boolean[] ans = new boolean[len];
			for (int i = 0; i < len; i++) {
				int[] query = queries[i];
				int favoriteType = query[0], favoriteDay = query[1], dailyCap = query[2];
				// 求query的区间
				long x1 = favoriteDay + 1;
				long y1 = (long) (favoriteDay + 1) * dailyCap;

				// 能够吃到i类糖果的区间
				long x2 = favoriteType == 0 ? 1 : pre[favoriteType] + 1;
				long y2 = pre[favoriteType + 1];
				ans[i] = !(x1 > y2 || y1 < x2);
			}
			return ans;
		}
	}

	// 523. 连续的子数组和
	@Test
	public void test7() {
//		Assert.assertEquals(true, new Solution7().checkSubarraySum(new int[] {
//				23,2,4,6,7
//		}, 6));
		Assert.assertEquals(true, new Solution7().checkSubarraySum(new int[] { 5, 0, 0, 0 }, 3));
	}

	class Solution7 {
		public boolean checkSubarraySum(int[] nums, int k) {
			// 提示：要使得两者除 k相减为整数，需要满足 sum[j] 和 sum[i - 1] 对 k取余相同。
			int n = nums.length;
			if (n < 2) {
				return false;
			}
			int[] preSum = new int[n + 1];
			for (int i = 1; i < preSum.length; i++) {
				preSum[i] = preSum[i - 1] + nums[i - 1];
			}
			Set<Integer> set = new HashSet<>();
			for (int i = 2; i < preSum.length; i++) {
				set.add(preSum[i - 2] % k); // 添加当前值左移2的位的值，因为不少于2 的长度
				if (set.contains(preSum[i] % k)) {
					return true;
				}
			}
			return false;
		}
	}

	// 518. 零钱兑换 II
	@Test
	public void test8() {
		Assert.assertEquals(4, new Solution8().change(5, new int[] { 1, 2, 5 }));

		Assert.assertEquals(0, new Solution8().change(3, new int[] { 2 }));

		Assert.assertEquals(1, new Solution8().change(0, new int[] { 7 }));
	}

	// https://leetcode-cn.com/problems/coin-change-2/solution/gong-shui-san-xie-xiang-jie-wan-quan-bei-6hxv/
	class Solution8 {
		public int change(int cnt, int[] cs) {
			int n = cs.length;
			int[] f = new int[cnt + 1];
			f[0] = 1;
			for (int i = 1; i <= n; i++) {
				int val = cs[i - 1];
				for (int j = val; j <= cnt; j++) {
					f[j] += f[j - val];
				}
			}
			return f[cnt];
		}
	}

	// 1449. 数位成本和为目标值的最大数字
	@Test
	public void test9() {
		Assert.assertEquals("7772", new Solution9().largestNumber(new int[] { 4, 3, 2, 5, 6, 7, 2, 5, 5 }, 9));
	}

	// https://leetcode-cn.com/problems/form-largest-integer-with-digits-that-add-up-to-target/solution/shu-wei-cheng-ben-he-wei-mu-biao-zhi-de-dnh86/
	class Solution9 {
		public String largestNumber(int[] cost, int target) {
			int[][] dp = new int[10][target + 1]; // 选择前i个cost，成本为j时最大的位数
			for (int i = 0; i < 10; ++i) {
				Arrays.fill(dp[i], Integer.MIN_VALUE);
			}
			int[][] from = new int[10][target + 1];
			dp[0][0] = 0;
			for (int i = 0; i < 9; ++i) {
				int c = cost[i];
				for (int j = 0; j <= target; ++j) {
					if (j < c) {
						dp[i + 1][j] = dp[i][j];
						from[i + 1][j] = j;
					} else {
						if (dp[i][j] > dp[i + 1][j - c] + 1) {
							dp[i + 1][j] = dp[i][j];
							from[i + 1][j] = j;
						} else {
							dp[i + 1][j] = dp[i + 1][j - c] + 1;
							from[i + 1][j] = j - c;
						}
					}
				}
			}
			if (dp[9][target] < 0) {
				return "0";
			}
			StringBuffer sb = new StringBuffer();
			int i = 9, j = target;
			while (i > 0) {
				if (j == from[i][j]) {
					// 说明该位置没有被选中
					--i;
				} else {
					sb.append(i);
					j = from[i][j];
				}
			}
			return sb.toString();
		}
	}

	// 909. 蛇梯棋
	@Test
	public void test10() {
		Assert.assertEquals(4,
				new Solution10().snakesAndLadders(new int[][] { { -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, -1, -1, -1 },
						{ -1, -1, -1, -1, -1, -1 }, { -1, 35, -1, -1, 13, -1 }, { -1, -1, -1, -1, -1, -1 },
						{ -1, 15, -1, -1, -1, -1 } }));
		
		Assert.assertEquals(2,
				new Solution10().snakesAndLadders(new int[][] { {-1,-1,19,10,-1 }, { 2,-1,-1,6,-1 },
						{ -1,17,-1,19,-1 }, { 25,-1,20,-1,-1 }, { -1,-1,-1,-1,15 } }));
	}

	class Solution10 {
		private int n;
		private int[][] board;

		public int snakesAndLadders(int[][] board) {
			n = board.length;
			this.board = board;
			Queue<Integer> queue = new LinkedList<>();
			Set<Integer> seen = new HashSet<>();
			queue.add(1);
			seen.add(1);
			int level = 0;
			int target = n*n;
			while (!queue.isEmpty()) {
				int size = queue.size();
				level++;
				for (int i = 0; i < size; i++) {
					Integer status = queue.poll();
					for (Integer coordinates : getNext(status)) {
						// 没有走过
						if (!seen.contains(coordinates)) {
							if (coordinates == target) {
								return level;
							}
							queue.offer(coordinates);
							seen.add(coordinates);
						}
					}
				}
			}
			return -1;
		}

		private List<Integer> getNext(Integer coord) {
			List<Integer> list = new ArrayList<>();
			for (int i = coord + 1; i <= n * n && i<= coord+6; i++) {
				int[] indexs = getCoordinates(i);
				if (this.board[indexs[0]][indexs[1]] != -1) {
					list.add(this.board[indexs[0]][indexs[1]]);
				} else {
					list.add(i);
				}
			}
			return list;
		}

		private int[] getCoordinates(int i) {
			int row = n - (int)Math.ceil((double)i / n); // 第几行
			int col = 0;// 第几列
			int times = (i-1)/n;
			if (times % 2 != 0) {
				col = n-((i-n*times)-1)-1;
			} else {
				col = i-n*times-1;
			}
			//System.out.println("i="+i+"row="+row+"col="+col);
			return new int[] { row, col };
		}
	}
	
	//LCP 07. 传递信息
	@Test
	public void test11() {
		Assert.assertEquals(3, new Solution11().numWays(5, new int[][] {
			{0,2},{2,1},{3,4},{2,3},{1,4},{2,0},{0,4}
		}, 3));
		
		Assert.assertEquals(0, new Solution11().numWays(3, new int[][] {
			{0,2},{2,1}
		}, 2));
		
		Assert.assertEquals(11, new Solution11().numWays(3, new int[][] {
			{0,1},{0,2},{2,1},{1,2},{1,0},{2,0}
		}, 5));
	}
	class Solution11 {
		private int result;
		private int end;
		private int k;
	    public int numWays(int n, int[][] relation, int k) {
	    	//注意，可以重复路径
	    	Map<Integer,List<Integer>> relationMap = new HashMap<>();
	    	for (int i=0;i<relation.length;i++) {
	    		int[] r = relation[i];
	    		List<Integer> list = relationMap.getOrDefault(r[0], new ArrayList<Integer>());
	    		list.add(r[1]);
	    		relationMap.put(r[0], list);
	    	}
	    	this.end = n-1;
	    	this.k = k;
	    	//dfs
	    	dfs(relationMap,0,new ArrayList<String>());
	    	return result;
	    }
		private void dfs(Map<Integer, List<Integer>> relationMap, int node, List<String> pathSet) {
			List<Integer> list = relationMap.get(node);
			if (pathSet.size()>this.k) {
				return;
			}
			if (node==end && pathSet.size()==this.k) {
				result++;
				return;
			}
			if (list == null) {
				return;
			}
			for (Integer n : list) {
				String path = node+">"+n;
				pathSet.add(path);
				dfs(relationMap,n,pathSet);
				int index = pathSet.size()-1;
				pathSet.remove(index);//删除
			}
		}
	}
}
