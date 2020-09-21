package com.laz.arithmetic.search;

import org.junit.Assert;
import org.junit.Test;

/**
 * 三分搜索
 * 题目描述： 给定一个整数序列，该整数序列存在着这几种可能：先递增后递减、先递减后递增、全递减、全递增。 请找出那个最大值的点。 输入：
 * 输入的第一行包括一个整数N(1<=N<=10000)。 接下来的一行是N个满足题目描述条件的整数。 输出： 可能有多组测试数据，对于每组数据，
 * 输出这N个数中最大的那个数。 样例输入： 5 1 2 3 2 1 样例输出： 3
 *
 */
public class TrisectionSearch {
	@Test
	public void test() {
		int[] arr1 = new int[] { 1, 2, 3, 2, 1 };
		Assert.assertEquals(3, trisectionSearch(arr1, 0, arr1.length));

		int[] arr2 = new int[] { 1, 2, 3, 4, 5 };
		Assert.assertEquals(5, trisectionSearch(arr2, 0, arr2.length));
	}

	public int trisectionSearch(int[] arr, int begin, int end) {
		int mid = 0, midmid, max;

		/* mid靠近极值点，舍弃最右部分，midmid靠近极值点，舍弃最左部分 */
		while (begin < end) {
			if (begin + 1 == end) {
				max = arr[begin] > arr[end] ? arr[begin] : arr[end];
				return max;
			}
			mid = (begin + end) / 2;
			if (mid + 1 == end) {
				midmid = (mid + end) / 2 + 1;
			} else {
				midmid = (mid + end) / 2;
			}

			if (midmid == end) {
				max = arr[begin] > arr[mid] ? arr[begin] : arr[mid];
				max = max > arr[end] ? max : arr[end];
				return max;
			}

			if (arr[mid] >= arr[midmid])
				end = midmid;
			else
				begin = mid;
		}

		return arr[mid];
	}

}
