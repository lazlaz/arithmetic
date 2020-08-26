package com.laz.arithmetic.sort;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;


/**
 * 基数排序 考虑负数的情况还可以参考： https://code.i-harness.com/zh-CN/q/e98fa9
 * https://www.runoob.com/w3cnote/radix-sort.html
 */
public class RadixSort {
	@Test
	public void test() throws Exception {
		{
			int[] arr = new int[] {1,11,111,2,22,222};
			int[] newArr = sort(arr);
			Assert.assertArrayEquals(new int[] {1,2,11,22,111,222}, newArr);
		}
	
		{
			int[] arr = new int[] {1,11,-11,111,2,22,222};
			int[] newArr = sort(arr);
			Assert.assertArrayEquals(new int[] {-11,1,2,11,22,111,222}, newArr);
		}
	
	}
	public int[] sort(int[] sourceArray) throws Exception {
		// 对 arr 进行拷贝，不改变参数内容
		int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
		int maxDigit = getMaxDigit(arr);
		return radixSort(arr, maxDigit);
	}

	/**
	 * 获取最高位数
	 */
	private int getMaxDigit(int[] arr) {
		int maxValue = getMaxValue(arr);
		return getNumLenght(maxValue);
	}

	private int getMaxValue(int[] arr) {
		int maxValue = arr[0];
		for (int value : arr) {
			if (maxValue < value) {
				maxValue = value;
			}
		}
		return maxValue;
	}

	protected int getNumLenght(long num) {
		if (num == 0) {
			return 1;
		}
		int lenght = 0;
		for (long temp = num; temp != 0; temp /= 10) {
			lenght++;
		}
		return lenght;
	}

	private int[] radixSort(int[] arr, int maxDigit) {
		int dev = 1;
		int mod = 10;
		for (int i = 0; i < maxDigit; i++, dev *= 10,mod*=10) {
			int[][] counter = new int[20][0];

			for (int j = 0; j < arr.length; j++) {
				int bucket = ((arr[j] % mod) /  dev);
				bucket = bucket>=0?bucket+10:-bucket;
				counter[bucket] = arrayAppend(counter[bucket], arr[j]);
			}

			int pos = 0;
			for (int[] bucket : counter) {
				for (int value : bucket) {
					arr[pos++] = value;
				}
			}
		}

		return arr;
	}

	/**
	 * 自动扩容，并保存数据
	 *
	 * @param arr
	 * @param value
	 */
	private int[] arrayAppend(int[] arr, int value) {
		arr = Arrays.copyOf(arr, arr.length + 1);
		arr[arr.length - 1] = value;
		return arr;
	}
}
