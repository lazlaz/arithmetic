package com.laz.arithmetic.competition;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-244
public class Competition32 {
	//5776. 判断矩阵经轮转后是否一致
	@Test
	public void test1() {
		Assert.assertEquals(true, new Solution1().findRotation(new int[][] {
			{0,1},{1,0}
		}, new int[][] {
			{1,0},{0,1}
		}));
		
		Assert.assertEquals(true, new Solution1().findRotation(new int[][] {
			{0,0,0},{0,1,0},{1,1,1}
		}, new int[][] {
			{1,1,1},{0,1,0},{0,0,0}
		}));
	}
	
	class Solution1 {
	    public boolean findRotation(int[][] mat, int[][] target) {
	    	//不旋转
	    	if (match(mat,target)) {
	    		return true;
	    	}
	    	//旋转90
	    	int[][] newMat90 = getNewMat(mat);
	    	if (match(newMat90,target)) {
	    		return true;
	    	}
	    	
	    	//旋转180
	    	int[][] newMat180 = getNewMat(newMat90);
	    	if (match(newMat180,target)) {
	    		return true;
	    	}
	    	//旋转270
	    	int[][] newMat270 = getNewMat(newMat180);
	    	if (match(newMat270,target)) {
	    		return true;
	    	}
	    	return false;
	    }

		private int[][] getNewMat(int[][] mat) {
			int n = mat.length;
			int[][]  newMat= new int[n][n];
			int row=0;
			int col=0;
			for (int j=0;j<n;j++) {
				for (int i=n-1;i>=0;i--) {
					newMat[row][col++] = mat[i][j];
				}
				col = 0;
				row++;
			}
	    	return newMat;
		}

		private boolean match(int[][] mat, int[][] target) {
			int n = mat.length;
			boolean can = true;
	    	for (int i=0;i<n;i++) {
	    		if (!can) {
	    			break;
	    		}
	    		for (int j=0;j<n;j++) {
	    			if (mat[i][j]!=target[i][j]) {
	    				can=false;
	    				break;
	    			}
	    		}
	    	}
	    	if (can) {
	    		return can;
	    	}
	    	return false;
			
		}
	}
	
	//5777. 使数组元素相等的减少操作次数
	@Test
	public void test2() {
		Assert.assertEquals(3, new Solution2().reductionOperations(new int[] {
				5,1,3
		}));
		
		Assert.assertEquals(4, new Solution2().reductionOperations(new int[] {
				1,1,2,2,3
		}));
		
		Assert.assertEquals(45, new Solution2().reductionOperations(new int[] {
				7,9,10,8,6,4,1,5,2,3
		}));
		
		
	}
	class Solution2 {
	    public int reductionOperations(int[] nums) {
	    	//根据key降序
	    	TreeMap<Integer,Integer> map = new TreeMap<>((o1,o2)->{
	    		return o2-o1;
	    	});
	    	for (int i=0;i<nums.length;i++) {
	    		int v = map.getOrDefault(nums[i], 0);
	    		map.put(nums[i], ++v);
	    	}
	    	int sum = 0;
	    	int lastV = 0;
	    	
	    	for (Map.Entry<Integer,Integer> entry:map.entrySet()) {
	    		//最后一个数不用管
	    		if (entry.getKey() == map.lastKey()) {
	    			break;
	    		}
	    		int total = lastV+entry.getValue(); 
	    		//该值变为下一个值的次数
	    		lastV  = total;
	    		sum += total;
	    	}
	    	return sum;
	    }
	}
}
