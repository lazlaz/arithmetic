package com.laz.arithmetic;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode13 {
	// 字符串的排列
	@Test
	public void test1() {
		Assert.assertEquals(true, checkInclusion("ab", "eidbaooo"));
		Assert.assertEquals(false, checkInclusion("ab", "eidboaoo"));
		Assert.assertEquals(true, checkInclusion("aab", "eidaabaoo"));
	}

	//https://leetcode-cn.com/problems/permutation-in-string/solution/2ge-shu-zu-hua-dong-chuang-kou-si-xiang-by-gfu/
	public boolean checkInclusion(String s1, String s2) {
        int len1 = s1.length(), len2 = s2.length();
        if (len1 > len2) return false;
        int[] ch_count1 = new int[26], ch_count2 = new int[26];
        for (int i = 0; i < len1; ++i) {
            ++ch_count1[s1.charAt(i) - 'a'];
            ++ch_count2[s2.charAt(i) - 'a'];
        }
        for (int i = len1; i < len2; ++i) {
            if (isEqual(ch_count1, ch_count2)) return true;
            --ch_count2[s2.charAt(i - len1) - 'a'];
            ++ch_count2[s2.charAt(i) - 'a'];
        }
        return isEqual(ch_count1, ch_count2);
    }

    private boolean isEqual(int[] ch_count1, int[] ch_count2) {
        for (int i = 0; i < 26; ++i)
            if (ch_count1[i] != ch_count2[i])
                return false;
        return true;
    }
    
    //把二叉搜索树转换为累加树
    @Test
    public void test2() {
    	TreeNode root = Utils.createTree(new Integer[] {2,0,3,-4,1});
    	TreeNode n = convertBST(root);
    	Utils.printTreeNode(n);
    }
    
    int sum = 0;
    public TreeNode convertBST(TreeNode root) {
    	 if (root != null) {
             convertBST(root.right);
             sum += root.val;
             root.val = sum;
             convertBST(root.left);
         }
         return root;
    }
    
    //错误的集合
    @Test
    public void test3() {
    	Assert.assertArrayEquals(new int[] {2,3},findErrorNums(new int[] {1,2,2,4}));
    	Assert.assertArrayEquals(new int[] {3,1},findErrorNums(new int[] {3,2,3,4,6,5}));
    }
    public int[] findErrorNums(int[] nums) {
    	Set<Integer> set = new HashSet<Integer>();
    	int[] ret = new int[2];
    	boolean[] visted = new boolean[nums.length+1];
    	for (int i=0;i<nums.length;i++) {
    		if (set.contains(nums[i])) {
    			ret[0] = nums[i];
    		}
    		set.add(nums[i]);
    		visted[nums[i]] = true;
    	}
    	for (int i=1;i<=nums.length;i++) {
    		if (!visted[i]) {
    			ret[1] = i;
    		}
    	}
    	return ret;
    }
}
