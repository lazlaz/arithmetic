package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-230
public class Competition20 {
	// 5689. 统计匹配检索规则的物品数量
	@Test
	public void test1() {
		List<List<String>> items = new ArrayList<>();
		items.add(Arrays.asList("phone", "blue", "pixel"));
		items.add(Arrays.asList("computer", "silver", "lenovo"));
		items.add(Arrays.asList("phone", "gold", "iphone"));
		Assert.assertEquals(1, countMatches(items, "color", "silver"));
	}

	public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
		int ret = 0;
		for (List<String> list : items) {
			if (ruleKey.equals("type")) {
				if (list.get(0).equals(ruleValue)) {
					ret++;
				}
			}
			if (ruleKey.equals("color")) {
				if (list.get(1).equals(ruleValue)) {
					ret++;
				}
			}
			if (ruleKey.equals("name")) {
				if (list.get(2).equals(ruleValue)) {
					ret++;
				}
			}
		}
		return ret;
	}

	// 5690. 最接近目标价格的甜点成本
	@Test
	public void test2() {
		//	Assert.assertEquals(10, closestCost(new int[] { 1, 7 }, new int[] { 3, 4 }, 10));
		
		Assert.assertEquals(17, new Solution2().closestCost(new int[] { 2,3 }, new int[] {4,5,100 }, 18));
		
		Assert.assertEquals(100, new Solution2().closestCost(new int[] { 2,3 }, new int[] {1,40,45,97 }, 100));
	}
	class Solution2 {
		
		int ans=Integer.MAX_VALUE;
		public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
			for(int i=0;i<baseCosts.length;i++){
				int sum=baseCosts[i];
				if(sum==target) return target;
				dfs(toppingCosts,sum,target,0);
				if(ans==target) return target;
				
			}
			return ans;
		}
		public void dfs(int[] toppingCosts,int sum, int target,int index) {
			if(Math.abs(sum-target)<Math.abs(ans-target)){
				ans=sum;
			}else if(Math.abs(sum-target)==Math.abs(ans-target)&&sum<ans){
				ans=sum;
			}else if(sum>target){
				return;
			}
			if(sum==target) return;
			if(index==toppingCosts.length){
				return;
			}
			dfs(toppingCosts,sum,target,index+1);
			dfs(toppingCosts,sum+toppingCosts[index],target,index+1);
			dfs(toppingCosts,sum+2*toppingCosts[index],target,index+1);
		}
	}

}
