package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

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
					if (queue.size()<k) {
						queue.add(pre[i][j]);
					} else if (queue.peek()<pre[i][j]) {
						queue.poll();
						queue.add(pre[i][j]);
					}
				}
			}
			return queue.peek();
		}
	}
	
	//692. 前K个高频单词
	@Test
	public void test2() {
		Assert.assertEquals("i,love", Joiner.on(",").join(new Solution2().topKFrequent(new String[] {
				"i", "love", "leetcode", "i", "love", "coding"
		}, 2)));
	}
	class Solution2 {
		class Word {
			String word;
			int count;
		}
	    public List<String> topKFrequent(String[] words, int k) {
	    	Map<String,Integer> wordMap = new HashMap<String,Integer>();
	    	for (int i=0;i<words.length;i++) {
	    		wordMap.put(words[i], wordMap.getOrDefault(words[i], 0)+1);
	    	}
	    	PriorityQueue<Word> wordStack = new PriorityQueue<Word>(new Comparator<Word>() {

				@Override
				public int compare(Word o1, Word o2) {
					if (o1.count>o2.count) {
						return -1;
					}
					if (o1.count<o2.count) {
						return 1;
					}
					return o1.word.compareTo(o2.word);
				}
			});
	    	for (Map.Entry<String,Integer> entry : wordMap.entrySet()) {
				Word w = new Word();
				w.count = entry.getValue();
				w.word = entry.getKey();
				wordStack.add(w);
			}
	    	List<String> res = new ArrayList<>();
	    	int index = 0;
	    	while (!wordStack.isEmpty() && index<k) {
	    		res.add(wordStack.poll().word);
	    		index++;
	    	}
	    	return res;
	    }
	}
	
	//1035. 不相交的线
	@Test
	public void test3() {
		Assert.assertEquals(2, new Solution3().maxUncrossedLines(new int[] {
				1,4,2
		}, new int[] {
				1,2,4
		}));
	}
	//https://leetcode-cn.com/problems/uncrossed-lines/solution/bu-xiang-jiao-de-xian-by-leetcode-soluti-6tqz/
	class Solution3 {
	    public int maxUncrossedLines(int[] nums1, int[] nums2) {
	    	//最长公共子序列
	    	int m = nums1.length,n = nums2.length;
	    	int[][]dp = new int[m+1][n+1];
	    	for (int i=1;i<=m;i++) {
	    		int num1 = nums1[i-1];
	    		for (int j=1;j<=n;j++) {
	    			int num2 = nums2[j-1];
	    			if (num1 == num2) {
	    				dp[i][j] = dp[i-1][j-1]+1;
	    			} else {
	    				dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
	    			}
	    		}
	    	}
	    	return dp[m][n];
	    }
	}
	
	//1190. 反转每对括号间的子串
	@Test
	public void test4() {
		Assert.assertEquals("iloveu", new Solution4().reverseParentheses("(u(love)i)"));
		Assert.assertEquals("dcba", new Solution4().reverseParentheses("(abcd)"));
		Assert.assertEquals("leetcode", new Solution4().reverseParentheses("(ed(et(oc))el)"));
		Assert.assertEquals("apmnolkjihgfedcbq", new Solution4().reverseParentheses("a(bcdefghijkl(mno)p)q"));
	}
	class Solution4 {
	    public String reverseParentheses(String s) {
	    	if (s.length()==0) {
	    		return s;
	    	}
	    	char[] chars = s.toCharArray();
	    	Deque<StringBuilder> stack = new LinkedList<>();
	    	StringBuilder sb = new StringBuilder();
	    	for (int i=0;i<chars.length;i++) {
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
	//477. 汉明距离总和
	@Test
	public void test5() {
		Assert.assertEquals(6, new Solution5().totalHammingDistance(new int[] {
				4, 14, 2
		}));
	}
	//https://leetcode-cn.com/problems/total-hamming-distance/solution/yi-ming-ju-chi-zong-he-by-leetcode-solut-t0ev/
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
}
