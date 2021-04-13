package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-236/
public class Competition26 {
	// 1823. 找出游戏的获胜者
	@Test
	public void test2() {
		Assert.assertEquals(3, new Solution2().findTheWinner(5, 2));
		Assert.assertEquals(1, new Solution2().findTheWinner(6, 5));
	}

	class Solution2 {
		public int findTheWinner(int n, int k) {
			List<Integer> list = new ArrayList<>();
			for (int i=1;i<=n;i++) {
				list.add(i);
			}
//			int index = 0;
//			while (list.size()>1) {
//				int len = list.size();
//				int limit = len-index;
//				if (k>limit) {
//					//超过了长度，先到末尾，然后从头开始，在用剩余的长度%(len+1)
//					int go = k-limit;
//					index = (go-1)%len;
//				} else {
//					index = index+k-1;
//				}
//				list.remove(index);
//			}
			int idx = 0;
			while (n > 1) {
	            idx = (idx + k - 1) % n;
	            list.remove(idx);
	            n--;
	        }
			return list.get(0);
		}
	}
}
