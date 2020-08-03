package com.laz.arithmetic.search;

import org.junit.Assert;
import org.junit.Test;

public class BinarySearch {
	@Test
	public void test() {
		Assert.assertEquals(5, binarySearch(new int[] { 1, 2, 3, 4, 5, 6 }, 6));
	}

	public int binarySearch(int[] arr, int target) {
		int low = 0;
		int high = arr.length - 1;

		while (low <= high) { // 必须为 '<=' 否则无法匹配到指定的key；
			int mid = (low + high) / 2;
			if (arr[mid] == target) {
				return mid;
			} else if (arr[mid] > target) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return -1;
	}
}
