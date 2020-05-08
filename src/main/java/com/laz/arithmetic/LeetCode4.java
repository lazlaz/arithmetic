package com.laz.arithmetic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class LeetCode4 {
	// 丑数
	@Test
	public void test1() {
		System.out.println(isUgly(8));
	}

	public boolean isUgly(int num) {
		if (num == 0) {
			return false;
		}
		if (num == 1) {
			return true;
		}
		while (num % 2 == 0 || num % 3 == 0 || num % 5 == 0) {
			if (num % 2 == 0) {
				num = num / 2;
			} else if (num % 3 == 0) {
				num = num / 3;
			} else if (num % 5 == 0) {
				num = num / 5;
			}
		}
		if (num == 1) {
			return true;
		}
		return false;
	}

	// 单词规律
	@Test
	public void test2() {
		System.out.println(wordPattern("", "beef"));
	}

	public boolean wordPattern(String pattern, String str) {
		String[] p = pattern.split("");
		String[] s = str.split(" ");

		if (pattern.equals("") && !str.equals("")) {
			return false;
		}
		if (!pattern.equals("") && str.equals("")) {
			return false;
		}
		if (p.length != s.length) {
			return false;
		}
		Map<String, Integer> pMap = new HashMap<String, Integer>();
		Map<String, Integer> sMap = new HashMap<String, Integer>();
		int pNum = 0;
		int sNum = 0;
		String pString = "";
		String sString = "";
		for (String p1 : p) {
			if (pMap.get(p1) != null) {
				pString += pMap.get(p1);
			} else {
				pString += pNum;
				pMap.put(p1, pNum);
				pNum++;
			}
		}
		for (String s1 : s) {
			if (sMap.get(s1) != null) {
				sString += sMap.get(s1);
			} else {
				sString += sNum;
				sMap.put(s1, sNum);
				sNum++;
			}
		}
		if (pString.equals(sString)) {
			return true;
		}
		return false;
	}

	// 区域和检索 - 数组不可变
	@Test
	public void test3() {
		int[] nums = new int[] { -2, 0, 3, -5, 2, -1 };
		NumArray obj = new NumArray(nums);
		int param_1 = obj.sumRange(0, 2);
		System.out.println(param_1);
	}

	class NumArray {
		private int[] nums;

		public NumArray(int[] nums) {
			this.nums = nums;
		}

		public int sumRange(int i, int j) {
			int count = 0;
			for (int z = i; z <= j; z++) {
				count += nums[z];
			}
			return count;
		}
	}

	// 最低票价
	@Test
	public void test4() {
		int[] days = new int[] { 1, 4, 6, 7, 8, 20 };
		int[] costs = new int[] { 2, 7, 15 };
		System.out.println(mincostTickets(days, costs));
	}

	int[] costs;
	Integer[] memo;
	Set<Integer> dayset;

	// dp(i) 来表示从第 i 天开始到一年的结束，我们需要花的钱
	public int mincostTickets(int[] days, int[] costs) {
		this.costs = costs;
		memo = new Integer[366];
		dayset = new HashSet();
		for (int d : days) {
			dayset.add(d);
		}
		return dp(1);
	}

	public int dp(int i) {
		if (i > 365) {
			return 0;
		}
		if (memo[i] != null) {
			return memo[i];
		}
		if (dayset.contains(i)) {
			int value = Math.min(dp(i + 1) + costs[0], dp(i + 7) + costs[1]);
			memo[i] = Math.min(value, dp(i + 30) + costs[2]);
		} else {
			memo[i] = dp(i + 1);
		}
		return memo[i];
	}

	// 另一个树的子树
	@Test
	public void test5() {
		Integer[] sArr = new Integer[] { 1,2,3,4,5,6,7,8};
		TreeNode s = Utils.createTree(sArr, 0);
		Integer[] tArr = new Integer[] {4,8 };
		TreeNode t = Utils.createTree(tArr, 0);
		System.out.println(isSubtree(s, t));

	}
	 public boolean isSubtree(TreeNode s, TreeNode t) {
	        if (t == null) return true;   // t 为 null 一定都是 true
	        if (s == null) return false;  // 这里 t 一定不为 null, 只要 s 为 null，肯定是 false
	        return isSubtree(s.left, t) || isSubtree(s.right, t) || isSameTree(s,t);
	    }

	    /**
	     * 判断两棵树是否相同
	     */
	    public boolean isSameTree(TreeNode s, TreeNode t){
	        if (s == null && t == null) return true;
	        if (s == null || t == null) return false;
	        if (s.val != t.val) return false;
	        return isSameTree(s.left, t.left) && isSameTree(s.right, t.right);
	    }
	public boolean isSubtree_2(TreeNode s, TreeNode t) {
		return isSubtree2(s, t, false);
	}

	private boolean isSubtree2(TreeNode s, TreeNode t, boolean b) {
		if (s == null && t == null) {
			return true;
		}
		if (s == null && t != null) {
			return false;
		}
		if (s != null && t == null) {
			return false;
		}
		if (b) {
			if (s.val != t.val) {
				return false;
			}
		}
		if (s.val == t.val) {
			boolean falg = (isSubtree2(s.left, t.left, true) && isSubtree2(s.right, t.right, true));
			if (falg) {
				return falg;
			} else if(!falg&&b) {
				return falg;
			}else {
				return (isSubtree2(s.left, t, false) || isSubtree2(s.right, t, false));
			}
		} else {
			return (isSubtree2(s.left, t, false) || isSubtree2(s.right, t, false));
		}
	}
	
	
	//最大正方形
	@Test
	public void test6() {
		char[][] matrix = new char[][] {
			
		};
		System.out.println(maximalSquare(matrix));
	}
	public int maximalSquare(char[][] matrix) {
		if (matrix == null || matrix.length == 0) {
			return 0;
		}
		int row = matrix.length;
		int col = matrix[0].length;
		int len = row>col?col:row;
		for (int i=len;i>0;i--) {
			//System.out.println("--");
			for (int row1=0;row1<=(row-i);row1++) {
			    for (int col1=0;col1<=col-i;col1++) {
			    		boolean find = true;
			    		for (int j=row1; j<row1+i;j++) {
			    			for (int z=col1;z<col1+i;z++) {
			    				if (matrix[j][z]!='1') {
			    					find = false;
			    					break;
			    				}
			    			}
			    			if (!find) {
			    				break;
			    			}
			    		}
			    		if (find) {
			    			return i*i;
			    		}
			    	}
			    }
		}
		return 0;
    }
}
