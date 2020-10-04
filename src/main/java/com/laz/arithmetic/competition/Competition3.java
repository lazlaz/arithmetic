package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

import com.laz.arithmetic.TreeNode;
import com.laz.arithmetic.Utils;

public class Competition3 {
	// 特殊数组的特征值
	@Test
	public void test1() {
		Assert.assertEquals(2, specialArray(new int[] { 3, 5 }));

		Assert.assertEquals(-1, specialArray(new int[] { 3, 6, 7, 7, 0 }));
		Assert.assertEquals(3, specialArray(new int[] { 0, 4, 3, 0, 4 }));
	}

	public int specialArray(int[] nums) {
		int len = nums.length;
		int max = 0;
		for (int i = 0; i < len; i++) {
			if (max < nums[i]) {
				max = nums[i];
			}
		}
		for (int i = 0; i <= max; i++) {
			int count = 0;
			for (int j = 0; j < len; j++) {
				if (nums[j] >= i) {
					count++;
				}
			}
			if (count == i) {
				return i;
			}
		}
		return -1;
	}

	// 奇偶树
	@Test
	public void test2() {
		TreeNode root = Utils.createTree(new Integer[] { 1, 10, 4, 3, null, 7, 9, 12, 8, 6, null, null, 2 });
		System.out.println(isEvenOddTree(root));
		
		TreeNode root2 = Utils.createTree(new Integer[] { 5,4,2,3,3,7});
		System.out.println(isEvenOddTree(root2));
		
		TreeNode root3= Utils.createTree(new Integer[] { 11,8,6,1,3,9,11,30,20,18,16,12,10,4,2,17});
		System.out.println(isEvenOddTree(root3));
	}

	public boolean isEvenOddTree(TreeNode root) {

		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		int level = 0;
		while (!queue.isEmpty()) {
			int level_length = queue.size();
			int last = -1;
			for (int i = 0; i < level_length; ++i) {
				TreeNode node = queue.remove();
				int v = node.val;
				// 偶数层
				if (level % 2 == 0) {
					if (v % 2 == 0) {
						return false;
					}
					if (v - last <= 0 && last > 0) {
						return false;
					}
				}
				// 奇数层
				if (level % 2 == 1) {
					if (v % 2 == 1) {
						return false;
					}
					if (v - last >= 0 && last > 0) {
						return false;
					}
				}
				last = v;
				if (node.left != null)
					queue.add(node.left);
				if (node.right != null)
					queue.add(node.right);
			}
			level++;
		}
		return true;
	}
}
