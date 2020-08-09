package com.laz.arithmetic.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

//桶排序 https://blog.csdn.net/developer1024/article/details/79770240
public class BucketSort {
	@Test
	public void test() {
		int[] arr = new int[] { 3, 23, 45, 5, 2, 2, 5, 2, 5 };
		new BucketSort().bucketSort(arr);
		Assert.assertArrayEquals(new int[] { 2,2,2,3,5,5,5,23,45 }, arr);
	}

	public void bucketSort(int[] arr) {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
			min = Math.min(min, arr[i]);
		}
		// 桶数
		int bucketNum = (max - min) / arr.length + 1;
		List<LinkedList<Integer>> bucketList  = new ArrayList<>(bucketNum);
		for (int i = 0; i < bucketNum; i++) {
			bucketList .add(new LinkedList<Integer>());
		}

		// 将每个元素放入桶
		for (int i = 0; i < arr.length; i++) {
			int num = (arr[i] - min) / (arr.length);
			bucketList .get(num).add(arr[i]);
		}

		// 对每个桶进行排序
		for (int i = 0; i < bucketList .size(); i++) {
			Collections.sort(bucketList .get(i));
		}
		
		//5.输出全部元素
        int k = 0;
        for(LinkedList<Integer> inter : bucketList){
            for (Integer i : inter) {
                arr[k] = i;
                k++;
            }
        }
	}
}
