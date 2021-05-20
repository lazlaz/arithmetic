package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
}
