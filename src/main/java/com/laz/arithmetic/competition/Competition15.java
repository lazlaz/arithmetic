package com.laz.arithmetic.competition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-221/
public class Competition15 {
	//1704. 判断字符串的两半是否相似
	@Test
	public void test1() {
		Assert.assertEquals(true, halvesAreAlike("book"));
		Assert.assertEquals(false, halvesAreAlike("textbook"));
		Assert.assertEquals(false, halvesAreAlike("MerryChristmas"));
		Assert.assertEquals(true, halvesAreAlike("AbCdEfGh"));
	}

	public boolean halvesAreAlike(String s) {
		int n  = s.length();
		char[] chars = new char[] {
				'a','e','i','o','u','A','E','I','O','U'
		};
		Set<Character> vowelSet = new HashSet<Character>();
		for (int i=0;i<chars.length;i++) {
			vowelSet.add(chars[i]);
		}
		int count1 = 0;
		int count2 = 0;
		for (int i=0;i<n/2;i++) {
			if (vowelSet.contains(s.charAt(i))) {
				count1++;
			}
		}
		for (int i=n/2;i<n;i++) {
			if (vowelSet.contains(s.charAt(i))) {
				count2++;
			}
		}
		return count1==count2;
	}
}
