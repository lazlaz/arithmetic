package com.laz.arithmetic.datastructure.stack;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Test;

import org.junit.Assert;

//单调栈
//https://blog.csdn.net/lucky52529/article/details/89155694


public class MonotonousStack {
	@Test
	public void test() {
		Assert.assertEquals(10, maxRecFromBottom(new int[] {4,3,2,5,6}));
	}
	/**
     * 给定一个数组[4,3,2,5,6]，每一个数代表一个矩形的高度，组成的一个二维数组，求其中的最大矩形
     * 解法，用最大单调栈的结构来求解，用来求解一个连续的无规则面积中最大的矩形面积
     *
     * @return
     */
    public  int maxRecFromBottom(int[] height) {
        int maxArea = 0;
        if (height.length <= 0)
            return 0;
        //从小到大的单调栈
        Deque<Integer> stack = new LinkedList<>();
        //这一步是在求每次遇到不是单调递增的时候那个柱子的面积
        for (int i = 0; i < height.length; i++) {
            //如果栈不为空，且当前元素小于栈顶元素
            while (!stack.isEmpty() && height[i] <= height[stack.peek()]) {
                int j = stack.pop();
                //左边界
                int k = stack.isEmpty() ? -1 : stack.peek();
                //(右边界 - 左边界)*高度
                int curArea = (i - k - 1) * height[j];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        //求整个单调递增的面积
        while (!stack.isEmpty()) {
            int j = stack.pop();
            int k = stack.isEmpty() ? -1 : stack.peek();
            //当前的右边界就是数组长度
            int curArea = (height.length - k - 1) * height[j];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;

    }
}
