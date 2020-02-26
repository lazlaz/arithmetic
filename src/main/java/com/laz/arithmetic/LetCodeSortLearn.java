package com.laz.arithmetic;

import org.junit.Test;

import com.laz.arithmetic.LetCodeTreeLearn.TreeNode;

public class LetCodeSortLearn {
	// 合并两个有序数组
		@Test
		public void test1() {
			int[] nums1 = new int[]{1,2,3,0,0,0};
			int[] nums2 = new int[]{2,5,6};
			merge(nums1,3,nums2,3);
			for (int num : nums1) {
				System.out.print(num+" ");
			}
		}

		public void merge(int[] nums1, int m, int[] nums2, int n) {
			for (int i=0;i<nums2.length;i++) {
				nums1[m+i] = nums2[i];
			}
			for (int i=0;i<nums1.length-1;i++) {
				for (int j=0;j<nums1.length-1;j++) {
					if(nums1[j]>nums1[j+1])
	                {
	                    int temp=nums1[j];
	                    nums1[j]=nums1[j+1];
	                    nums1[j+1]=temp;
	                }
				}
			}
		}
		
		
}
