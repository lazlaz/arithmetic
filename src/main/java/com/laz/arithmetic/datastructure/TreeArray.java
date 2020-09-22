package com.laz.arithmetic.datastructure;

/**
 * 树状数组 https://www.cnblogs.com/xenny/p/9739600.html
 */
public class TreeArray {

	public static void main(String[] args) {
		int a[] = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		TreeArray tree = new TreeArray();
		tree.init(a);
		// 区间求和
		int sum = tree.getsum(5);
		System.out.println(sum);
	}

	int n;
	int c[]; // 对应原数组和树状数组
	// 将数组转换为树状数组
	private void init(int[] a) {
		this.n = a.length;
		c = new int[a.length + 1];
		for (int i = 1; i <= n; i++) {
			updata(i, a[i - 1]); // 输入初值的时候，也相当于更新了值
		}
	}

	int lowbit(int x) {
		return x & (-x);
	}

	void updata(int i, int k) { // 在i位置加上k
		while (i <= n) {
			c[i] += k;
			i += lowbit(i);
		}
	}

	int getsum(int i) { 
		int res = 0;
		while (i > 0) {
			res += c[i];
			i -= lowbit(i);
		}
		return res;
	}
}
