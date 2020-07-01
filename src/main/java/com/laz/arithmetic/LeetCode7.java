package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode7 {
	// 整数转罗马数字
	@Test
	public void test1() {
		int num = 19943;
		// 1994
		// 输出: "MCMXCIV"
		System.out.println(intToRoman(num));
	}

	public String intToRoman(int num) {
		int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length && num > 0; i++) {
			while (values[i] <= num) {
				num -= values[i];
				sb.append(symbols[i]);
			}
		}
		return sb.toString();
	}

	// 最接近的三数之和
	@Test
	public void test2() {
		int[] nums = new int[] { -1, 2, 1, -4 };
		System.out.println(threeSumClosest(nums, -4));
	}

	public int threeSumClosest(int[] nums, int target) {
		if (nums == null) {
			return 0;
		}
		Arrays.sort(nums);
		int result = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < nums.length - 2; i++) {
			int left = i + 1;
			int right = nums.length - 1;
			while (left < right) {
				int v = nums[i] + nums[left] + nums[right];
				if (Math.abs(v - target) < min) {
					min = Math.abs(v - target);
					result = v;
				}
				if (v > target) {
					right--;
				}
				if (v < target) {
					left++;
				}
				if (v == target) {
					return v;
				}
			}
		}
		return result;
	}

	// 电话号码的字母组合
	@Test
	public void test3() {
		List<String> list = letterCombinations("4");
		System.out.println(Joiner.on(",").join(list));
	}

	Map<String, String> phone = new HashMap<String, String>() {
		{
			put("2", "abc");
			put("3", "def");
			put("4", "ghi");
			put("5", "jkl");
			put("6", "mno");
			put("7", "pqrs");
			put("8", "tuv");
			put("9", "wxyz");
		}
	};
	List<String> output = new ArrayList<String>();

	public List<String> letterCombinations(String digits) {
		if (digits.length() != 0) {
			backtrack("", digits);
		}
		return output;
	}

	public void backtrack(String combination, String next_digits) {
		if (next_digits.length() == 0) {
			output.add(combination);
		} else {
			String digit = next_digits.substring(0, 1);
			String letters = phone.get(digit);
			for (int i = 0; i < letters.length(); i++) {
				String letter = phone.get(digit).substring(i, i + 1);
				backtrack(combination + letter, next_digits.substring(1));
			}
		}
	}

	// 四数之和
	@Test
	public void test4() {
		int[] nums = new int[] { 0, 0, 0, 0 };
		int target = 0;
		List<List<Integer>> list = fourSum(nums, target);
		for (List<Integer> l : list) {
			System.out.println(Joiner.on(",").join(l));
		}
	}

	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		Arrays.sort(nums);
		if (nums == null || nums.length < 4) {
			return res;
		}
		int left1 = 0, left2 = 1;
		int right1 = nums.length - 1, right2 = nums.length - 2;
		Map<String, Boolean> falg = new HashMap<String, Boolean>();
		while (left1 <= nums.length - 4 && left1 < left2 && right2 < right1 && left2 < right2) {
			List<Integer> list = new ArrayList<Integer>();
			int r = nums[left1] + nums[left2] + nums[right2] + nums[right1];
			if (r == target) {
				list.add(nums[left1]);
				list.add(nums[left2]);
				list.add(nums[right2]);
				list.add(nums[right1]);
				String key = new StringBuffer().append(nums[left1]).append(nums[left2]).append(nums[right2])
						.append(nums[right1]).toString();
				if (falg.get(key) == null || !falg.get(key)) {
					falg.put(key, true);
					res.add(list);
				}
				right2--;
				left2++;
			}
			if (r > target) {
				right2--;
			}
			if (r < target) {
				left2++;
			}
			if (left2 >= right2) {
				if (right1 - left1 < 4) {
					right1 = nums.length - 1;
					right2 = right1 - 1;
					left1++;
					left2 = left1 + 1;
				} else {
					right1--;
					right2 = right1 - 1;
					left2 = left1 + 1;
				}

			}
		}
		return res;
	}

	// 缺失的第一个正数
	@Test
	public void test5() {
		// Assert.assertEquals(3, firstMissingPositive(new int[] {1,2,0}));
		Assert.assertEquals(1, firstMissingPositive(new int[] { 3, 2, 3 }));
	}

	public int firstMissingPositive(int[] nums) {
		int len = nums.length;
		for (int i = 0; i < len; i++) {
			while (nums[i] > 0 && nums[i] <= len && nums[nums[i] - 1] != nums[i]) {
				swap(nums, nums[i] - 1, i);
			}
		}
		for (int i = 0; i < len; i++) {
			if (nums[i] != i + 1) {
				return i + 1;
			}
		}
		return len + 1;
	}

	// K 个一组翻转链表
	@Test
	public void test6() {
		ListNode head = Utils.createListNode(new Integer[] { 1, 2, 3, 4, 5 });
		ListNode newHead = reverseKGroup(head, 2);
		Utils.printListNode(newHead);
	}

	public ListNode reverseKGroup(ListNode head, int k) {
		ListNode hair = new ListNode(0);
		hair.next = head;
		ListNode pre = hair;
		while (head != null) {
			ListNode tail = pre;
			for (int i = 0; i < k; i++) {
				tail = tail.next;
				if (tail == null) {
					return hair.next;
				}
			}
			ListNode next = tail.next;
			Map<String, ListNode> map = reverse(head, tail);
			pre.next = map.get("head");
			map.get("tail").next = next;
			pre = map.get("tail");
			head = pre.next;
		}
		return hair.next;
	}

	public Map<String, ListNode> reverse(ListNode head, ListNode tail) {
		Map<String, ListNode> map = new HashMap<String, ListNode>();
		ListNode prev = tail.next;
		ListNode p = head;
		while (prev != tail) {
			ListNode next = p.next;
			p.next = prev;
			prev = p;
			p = next;
		}
		map.put("head", tail);
		map.put("tail", head);
		return map;
	}

	// 数组中的第K个最大元素
	@Test
	public void test7() {
		Assert.assertEquals(5, findKthLargest(new int[] { 3, 2, 1, 5, 6, 4 }, 2));
	}

	public int findKthLargest(int[] nums, int k) {
		if (nums == null || nums.length < k) {
			return 0;
		}
		Arrays.sort(nums);
		int count = 0;
		for (int i = nums.length - 1; i >= 0; i--) {
			count++;
			if (count == k) {
				return nums[i];
			}
		}
		return 0;
	}

	// 下一个排列
	@Test
	public void test8() {
		int[] nums = new int[] { 1, 5, 1 };
		nextPermutation(nums);
		for (int i : nums) {
			System.out.print(i + " ");
		}
	}

	public void nextPermutation(int[] nums) {
		if (nums == null || nums.length < 0) {
			return;
		}
		int i = nums.length - 2;
		while (i >= 0 && nums[i] >= nums[i + 1]) {
			i--;
		}
		if (i >= 0) {
			int j = nums.length - 1;
			while (j > i && nums[j] <= nums[i]) {
				j--;
			}
			swap(nums, i, j);
		}
		reverse(nums, i + 1);

	}

	public void reverse(int[] nums, int start) {
		int i = start, j = nums.length - 1;
		while (i < j) {
			swap(nums, i, j);
			i++;
			j--;
		}
	}

	public void swap(int[] nums, int index1, int index2) {
		int temp = nums[index1];
		nums[index1] = nums[index2];
		nums[index2] = temp;
	}

	// 串联所有单词的子串
	@Test
	public void test9() {
		String s = "wordgoodgoodgoodbestword";
		String[] words = new String[] { "word", "good", "best", "good" };
		List<Integer> res = findSubstring(s, words);
		System.out.println(Joiner.on(",").join(res));
	}

	public List<Integer> findSubstring(String s, String[] words) {
		List<Integer> res = new ArrayList<>();
		if (s == null || s.length() == 0 || words == null || words.length == 0)
			return res;
		HashMap<String, Integer> map = new HashMap<>();
		int one_word = words[0].length();
		int word_num = words.length;
		int all_len = one_word * word_num;
		for (String word : words) {
			map.put(word, map.getOrDefault(word, 0) + 1);
		}
		for (int i = 0; i < s.length() - all_len + 1; i++) {
			String tmp = s.substring(i, i + all_len);
			HashMap<String, Integer> tmp_map = new HashMap<>();
			for (int j = 0; j < all_len; j += one_word) {
				String w = tmp.substring(j, j + one_word);
				tmp_map.put(w, tmp_map.getOrDefault(w, 0) + 1);
			}
			if (map.equals(tmp_map))
				res.add(i);
		}
		return res;
	}
	
	//最长重复子数组
	@Test
	public void test10() {
		Assert.assertEquals(3,findLength(new int[] {1,2,3,2,1}, new int[] {3,2,1,4,7}));
		Assert.assertEquals(1,findLength(new int[] {1,2,3,4,5}, new int[] {3,2,1,4,7}));
		Assert.assertEquals(0,findLength(new int[] {}, new int[] {3,2,1,4,7}));
		Assert.assertEquals(5,findLength(new int[] {0,0,0,0,0}, new int[] {0,0,0,0,0}));
	}
	
	public int findLength(int[] A, int[] B) {
		int n = A.length,m = B.length;
		int ret = 0;
		for (int i=0; i<n;i++) {
			int len = Math.min(m, n-i);
			int maxLen = maxLength(A,B,i,0,len);
			ret = Math.max(maxLen, ret);
		}
		for (int i=0; i<n;i++) {
			int len = Math.min(n, m-i);
			int maxLen = maxLength(A,B,0,i,len);
			ret = Math.max(maxLen, ret);
		}
		return ret;
    }
	private int maxLength(int[] A, int[] B, int addA,int addB,int len) {
		int ret = 0,k=0;
		for (int i=0;i<len;i++) {
			if (A[addA+i]==B[addB+i]) {
				k++;
			}else {
				k=0;
			}
			ret = Math.max(ret, k);
		}
		return ret;
	}
	
	//搜索旋转排序数组
	@Test
	public void test11() {
		Assert.assertEquals(4, search(new int[] {4,5,6,7,0,1,2}, 0));
	}
	public int search(int[] nums, int target) {
		if (nums == null || nums.length<=0) {
			return -1;
		}
		int l=0,r=nums.length-1;
		while (l<=r) {
			int mid = (l+r)/2;
			if (nums[mid] ==  target) {
				return mid;
			}
			//l至mid有序
			if (nums[0] <= nums[mid]) {
				if (nums[0]<=target && target<nums[mid]) {
					//在 l至mid-1查找
					r = mid-1;
				} else {
					//在mid+1至r查找
					l = mid+1;
				}
			} else {
				if (nums[mid]<target && target<=nums[nums.length-1]) {
					//在 l至mid-1查找
					l = mid+1;
				} else {
					//在mid+1至r查找
					r = mid-1;
				}
			}
			
		}
		return -1;
	}
}
