package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.Test;

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
}
