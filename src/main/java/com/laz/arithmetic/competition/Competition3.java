package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
	
	//使整数变为 0 的最少操作次数
	@Test
	public void test3() {
		Assert.assertEquals(4, new Solution3().minimumOneBitOperations(6));
	}
	//https://leetcode-cn.com/problems/minimum-one-bit-operations-to-make-integers-zero/solution/gen-zhao-ti-shi-zhao-gui-lu-kan-liao-bie-ren-de-ge/
	class Solution3 {
		Map<Integer, Integer> map;
		public int minimumOneBitOperations(int n) {
	        map=new HashMap<>();
	        int i=1; map.put(i,1); map.put(0,0);
	        while(i<n){
	            map.put(i<<1, (map.get(i)<<1)+1);
	            i<<=1;
	        }
	        return dfs(n);
	    }
	    int dfs(int n){
	        if(map.containsKey(n)) return map.get(n);
	        int t=n;
	        t|=t>>1;
	        t|=t>>2;
	        t|=t>>4;
	        t|=t>>8;
	        t|=t>>16;
	        t+=1;
	        t>>=1;
	        int ans=dfs(t)-dfs(n-t);
	        map.put(n, ans);
	        return ans;
	    }

    }
	
	//可见点的最大数目
	@Test
	public void test4() {
		List<List<Integer>> points = new ArrayList<List<Integer>>();
		points.add(Arrays.asList(0,1));
		points.add(Arrays.asList(2,1));
		
		int angle = 13;
		List<Integer> location = new ArrayList<Integer>();
		location.addAll(Arrays.asList(1,1));
		Assert.assertEquals(1, visiblePoints(points,angle,location));
	}
	
	//https://leetcode-cn.com/problems/maximum-number-of-visible-points/solution/ji-zuo-biao-hua-dong-chuang-kou-zui-da-chang-du-zh/
	public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        final int pointCnt = points.size();
        List<Double> angles = new ArrayList<>();
        int extraAns = 0;
        for (int i = 0; i < pointCnt; ++i){
            int deltaY = points.get(i).get(1) - location.get(1);
            int deltaX = points.get(i).get(0) - location.get(0);
            if (deltaY == 0 && deltaX == 0){
                ++extraAns;
                continue;
            }
            double curAngle = Math.atan2(deltaY, deltaX);
            angles.add(curAngle);
            angles.add(curAngle + 2 * Math.PI);
        }
        Collections.sort(angles);
        int ans = 0;
        int left = 0;
        int right = 0;
        double maxAngle = angle * Math.PI / 180.0;
        while (true){
            if (right == angles.size()){
                return ans + extraAns;
            }
            if (angles.get(right) - angles.get(left) <= maxAngle){
                ans = Math.max(ans, right - left + 1);
                ++right;
            }
            else{
                ++left;
            }
        }
    }

}
