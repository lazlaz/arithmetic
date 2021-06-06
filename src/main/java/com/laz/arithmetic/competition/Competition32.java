package com.laz.arithmetic.competition;

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
}
