package com.laz.arithmetic.datastructure;

/**
 * 线段树 (https://blog.csdn.net/feng964497595/article/details/100065313 ) 
 *题目：区域和检索 - 数组可修改
 */
public class SegmentTree {
	public static void main(String[] args) {
		int[] nums = new int[] { 1, 3, 5 };
		SegmentTree tree = new SegmentTree(nums);
		System.out.println(tree.sumRange(0, 2));
		tree.update(1, 2);
		System.out.println(tree.sumRange(0, 2));
	}

	int[] tree;
	int n;

	public SegmentTree(int[] nums) {
		if (nums.length > 0) {
			n = nums.length;
			tree = new int[n * 2];
			buildTree(nums);
		}
	}

	private void buildTree(int[] nums) {
		for (int i = n, j = 0; i < 2 * n; i++, j++)
			tree[i] = nums[j];
		for (int i = n - 1; i > 0; --i)
			tree[i] = tree[i * 2] + tree[i * 2 + 1];
	}

	void update(int pos, int val) {
		pos += n;
		tree[pos] = val;
		while (pos > 0) {
			int left = pos;
			int right = pos;
			if (pos % 2 == 0) {
				right = pos + 1;
			} else {
				left = pos - 1;
			}
			// parent is updated after child is updated
			tree[pos / 2] = tree[left] + tree[right];
			pos /= 2;
		}
	}

	public int sumRange(int l, int r) {
		// get leaf with value 'l'
		l += n;
		// get leaf with value 'r'
		r += n;
		int sum = 0;
		while (l <= r) {
			if ((l % 2) == 1) {
				sum += tree[l];
				l++;
			}
			if ((r % 2) == 0) {
				sum += tree[r];
				r--;
			}
			l /= 2;
			r /= 2;
		}
		return sum;
	}

}
