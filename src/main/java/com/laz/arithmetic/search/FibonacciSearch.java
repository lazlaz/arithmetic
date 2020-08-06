package com.laz.arithmetic.search;

import org.junit.Assert;
import org.junit.Test;

//斐波拉契查找
public class FibonacciSearch {
	@Test
	public void test() {
		Assert.assertEquals(2, fibonacciSearch(new int[] { 24, 44, 55, 66, 333 }, 55));
	}

	public final static int MAXSIZE = 20;

	/**
	 * 斐波那契数列
	 * 
	 * @return
	 */
	public static int[] fibonacci() {
		int[] f = new int[20];
		int i = 0;
		f[0] = 1;
		f[1] = 1;
		for (i = 2; i < MAXSIZE; i++) {
			f[i] = f[i - 1] + f[i - 2];
		}
		return f;
	}

	public int fibonacciSearch(int[] arr, int target) {
		int low = 0;
		int high = arr.length - 1;
		int mid = 0;

		// 斐波那契分割数值下标
		int k = 0;
		// 序列元素个数
		int i = 0;
		// 获取斐波那契数列
		int[] f = fibonacci();

		// 获取斐波那契分割数值下标
		while (arr.length > f[k] - 1) {
			k++;
		}

		// 创建临时数组
		int[] temp = new int[f[k] - 1];
		for (int j = 0; j < arr.length; j++) {
			temp[j] = arr[j];
		}

		// 序列补充至f[k]个元素
		// 补充的元素值为最后一个元素的值
		for (i = arr.length; i < f[k] - 1; i++) {
			temp[i] = temp[high];
		}

		while (low <= high) {
			// low：起始位置
			// 前半部分有f[k-1]个元素，由于下标从0开始
			// 则-1 获取 黄金分割位置元素的下标
			mid = low + f[k - 1] - 1;

			if (temp[mid] > target) {
				// 查找前半部分，高位指针移动
				high = mid - 1;
				// （全部元素） = （前半部分）+（后半部分）
				// f[k] = f[k-1] + f[k-1]
				// 因为前半部分有f[k-1]个元素，所以 k = k-1
				k = k - 1;
			} else if (temp[mid] < target) {
				// 查找后半部分，高位指针移动
				low = mid + 1;
				// （全部元素） = （前半部分）+（后半部分）
				// f[k] = f[k-1] + f[k-1]
				// 因为后半部分有f[k-1]个元素，所以 k = k-2
				k = k - 2;
			} else {
				// 如果为真则找到相应的位置
				if (mid <= high) {
					return mid;
				} else {
					// 出现这种情况是查找到补充的元素
					// 而补充的元素与high位置的元素一样
					return high;
				}
			}
		}
		return -1;
	}
}
