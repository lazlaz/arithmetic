package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-239/
public class Competition29 {
	// 1849. 将字符串拆分为递减的连续值
	@Test
	public void test2() {
//		Assert.assertEquals(false, new Solution2().splitString("1234"));
//		Assert.assertEquals(true, new Solution2().splitString("050043"));
//		Assert.assertEquals(false, new Solution2().splitString("9080701"));
//		Assert.assertEquals(true, new Solution2().splitString("10009998"));
//		Assert.assertEquals(true, new Solution2().splitString("53520515049"));
		Assert.assertEquals(false, new Solution2().splitString("94650723337775781477"));
		Assert.assertEquals(true, new Solution2().splitString("552551550549548547"));
	}

	class Solution2 {
		boolean ret = false;

		public boolean splitString(String s) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length() - 1; i++) {
				sb.append(s.charAt(i));
				long preV = Long.parseLong(sb.toString());
				if (preV >= Long.MAX_VALUE / 10000) {// 如果此值大于了long的多少，根据长度条件，后面不可能有超过的，跳过
					break;
				}
				dfs(s, new StringBuilder(), preV, (i + 1));
			}
			return ret;
		}

		private void dfs(String s, StringBuilder sb, long preV, int index) {
			if (index == s.length()) {
				ret = true;
				return;
			}
			for (int i = index; i < s.length(); i++) {
				sb.append(s.charAt(i));
				long currV = Long.parseLong(sb.toString());
				if (preV - currV == 1) {
					dfs(s, new StringBuilder(), currV, (i + 1));
				} else if (currV >= preV) { // 继续加是找不到合适的值了，直接break
					break;
				}
			}
		}
	}

	// 1850. 邻位交换的最小次数
	@Test
	public void test3() {
		Assert.assertEquals(2, new Solution3().getMinSwaps("5489355142", 4));
	}

	// https://leetcode-cn.com/problems/minimum-adjacent-swaps-to-reach-the-kth-smallest-number/solution/leetcodedi-31ti-de-bian-xing-xian-zhao-c-0b8b/
	class Solution3 {
		public int getMinSwaps(String num, int k) {
			// 1 得到第k个秒数的字符串numK
			int len = num.length();
			int[] intnum = new int[len];
			int[] beginnum = new int[len];// 起始数据
			for (int i = 0; i < num.length(); i++) {
				intnum[i] = num.charAt(i) - '0';
				beginnum[i] = num.charAt(i) - '0';
			}
			for (int i = 0; i < k; i++) {
				intnum = nextPermutation(intnum);
			}
			int[] numK = intnum;
			int res = 0;
			// 2 为了得到numK字符串原字符串交换的次数
			for (int i=0;i<len;i++) {
				//此位置不相等，需要进行交换
				if (beginnum[i] != numK[i]) {
					int j = i+1;
	                while(beginnum[j]!=numK[i]){j++;}//找到相同数据，开始交换
	                while(j != i){
	                    swap(beginnum, j-1, j);//只能两两交换
	                    res++;
	                    j--;
	                }
				}
			}
			return res;
		}

		// 获取下一个秒数
		private int[] nextPermutation(int[] nums) {
			int len = nums.length;
			for (int i = len - 1; i > 0; i--) {
				if (nums[i] > nums[i - 1]) {// nums[i-1]处的元素要进行位置调换
					int j = len - 1;
					while (nums[j] <= nums[i - 1]) {
						j--;
					}
					// 从i到j都比nums[i-1]大
					// nums[i-1]和nums[j]先调换位置
					swap(nums, i - 1, j);
					// 反转nums[i-1]之后的所有元素
					j = len - 1;
					while (i < j) {
						swap(nums, i++, j--);
					}
					break;
				}
			}
			return nums;

		}
		//交换nums数组第i和第j处的元素
	    public void swap(int[] nums, int i, int j){
	        int m = nums[i];
	        nums[i] = nums[j];
	        nums[j] = m;
	    }

	}
}
