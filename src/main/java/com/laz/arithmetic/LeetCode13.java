package com.laz.arithmetic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
}
