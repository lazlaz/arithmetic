package com.laz.arithmetic;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

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

    //874. 模拟行走机器人
    @Test
    public void test2() {
        Solution2 solution2 = new Solution2();
        Assert.assertEquals(solution2.robotSim(new int[] {
                4,-1,4,-2,4
        }, new int[][]{
                {2,4}
        }), 65);

        Assert.assertEquals(solution2.robotSim(new int[] {
              7,-2,-2,7,5
        }, new int[][]{
                {-3,2},{-2,1},{0,1},{-2,4},{-1,0},{-2,-3},{0,-3},{4,4},{-3,3},{2,2}
        }), 4);
//
        Assert.assertEquals(solution2.robotSim(new int[] {
               2,-1,8,-1,6
        }, new int[][]{
                {1,5},{-5,-5},{0,4},{-1,-1},{4,5},{-5,-3},{-2,1},{0,5},{0,-1}
        }), 80);
        Assert.assertEquals(solution2.robotSim(new int[] {
              -2,3,-1,-2,4
        }, new int[][]{
                {-2,2},{4,-3},{2,3},{-3,-2},{-2,-4},{0,-4},{2,2},{3,-2},{2,-5},{2,1}
        }), 49);
    }

    class Solution2 {
        public int robotSim(int[] commands, int[][] obstacles) {
            int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            int px = 0, py = 0, d = 1;
            Set<Integer> set = new HashSet<Integer>();
            for (int[] obstacle : obstacles) {
                set.add(obstacle[0] * 60001 + obstacle[1]);
            }
            int res = 0;
            for (int c : commands) {
                if (c < 0) {
                    d += c == -1 ? 1 : -1;
                    d %= 4;
                    if (d < 0) {
                        d += 4;
                    }
                } else {
                    for (int i = 0; i < c; i++) {
                        if (set.contains((px + dirs[d][0]) * 60001 + py + dirs[d][1])) {
                            break;
                        }
                        px += dirs[d][0];
                        py += dirs[d][1];
                        res = Math.max(res, px * px + py * py);
                    }
                }
            }
            return res;
        }
    }

    //2208. 将数组和减半的最少操作次数
    @Test
    public void test3() {
        Solution3 solution3 = new Solution3();
        Assert.assertEquals(3, solution3.halveArray(new int[] {
                5,19,8,1
        }));

        Assert.assertEquals(3, solution3.halveArray(new int[] {
                3,8,20
        }));
    }

    class Solution3 {
        public int halveArray(int[] nums) {
            int ans = 0;
            double sum = 0;
            for (int num : nums) {
                sum += num;
            }
            double average = sum/2;
            PriorityQueue<Double> queue = new PriorityQueue<>((o1,o2)->{return o2.compareTo(o1);});
            for (int i=0; i<nums.length; i++) {
                queue.offer((double)nums[i]);
            }
            while (sum>average) {
                Double v = queue.poll();
                double newV = v/2;
                sum -= newV;
                queue.offer(newV);
                ans++;
            }
            return ans;
        }
    }
}
