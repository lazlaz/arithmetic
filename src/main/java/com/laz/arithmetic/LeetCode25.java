package com.laz.arithmetic;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode25 {
    @Test
    //1911. 最大子序列交替和
    public void test1() {
        Solution1 solution1 = new Solution1();
        Assert.assertEquals(7, solution1.maxAlternatingSum(new int[] {
                4,2,5,3
        }));
    }

    class Solution1 {
        public long maxAlternatingSum(int[] nums) {
            int n = nums.length;
            long[] even = new long[n]; //最后一位为偶数
            long[] odd = new long[n];//最后一位为奇数

            even[0] = nums[0];
            odd[0] = 0;
            for (int i=1; i<n; i++) {
                even[i] = Math.max(even[i-1], odd[i-1]+nums[i]);
                odd[i] = Math.max(odd[i-1], even[i-1]-nums[i]);
            }
            return Math.max(even[n-1], odd[n-1]);
        }
    }
}
