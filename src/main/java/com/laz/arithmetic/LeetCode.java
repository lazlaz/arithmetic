package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import javafx.util.Pair;

public class LeetCode {

	// 多数元素
	@Test
	public void test1() {
		System.out.println(majorityElement(new int[] { 2, 2, 1, 1, 1, 2, 2 }));
	}

	public int majorityElement(int[] nums) {
		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for (int i : nums) {
			if (counts.get(i) == null) {
				counts.put(i, 1);
			} else {
				int count = counts.get(i);
				counts.put(i, ++count);
			}
			if (counts.get(i) > nums.length / 2) {
				return i;
			}
		}
		return 0;

	}

	// 乘积最大子序列
	@Test
	public void test2() {
		System.out.println(maxProduct(new int[] { -3, 0, 1, -2 }));
	}

	public int maxProduct(int[] nums) {
		int max = nums[0];
		for (int j = 0; j < nums.length; j++) {
			int result = nums[j];
			if (result > max) {
				max = result;
			}
			for (int i = j + 1; i < nums.length; i++) {
				result = result * nums[i];
				if (result > max) {
					max = result;
				}
			}

		}
		return max;
	}

	// 单词拆分
	@Test
	public void test3() {
		List<String> wordDict = new ArrayList<String>();
		wordDict.add("leet");
		wordDict.add("code");
		System.out.println(wordBreak("leetcode", wordDict));
	}

	public boolean wordBreak(String s, List<String> wordDict) {
		Set<String> wordDictSet = new HashSet(wordDict);
		boolean[] dp = new boolean[s.length() + 1];
		dp[0] = true;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[s.length()];
	}

	// 单词拆分 II
	@Test
	public void test4() {
		List<String> wordDict = new ArrayList<String>();
		wordDict.add("apple");
		wordDict.add("pen");
		wordDict.add("applepen");
		wordDict.add("pine");
		wordDict.add("pineapple");
		System.out.println(wordBreak2("pineapplepenapple", wordDict));
	}

	public List<String> wordBreak2(String s, List<String> wordDict) {
		LinkedList<String>[] dp = new LinkedList[s.length() + 1];
		LinkedList<String> initial = new LinkedList<>();
		initial.add("");
		dp[0] = initial;
		int maxLength = 0;
		for (String str : wordDict) {
			if (maxLength < str.length()) {
				maxLength = str.length();
			}
		}
		for (int i = 1; i <= s.length(); i++) {
			LinkedList<String> list = new LinkedList<>();
			for (int j = 0; j < i; j++) {
				if (dp[j].size() > 0 && wordDict.contains(s.substring(j, i))) {
					for (String l : dp[j]) {
						list.add(l + (l.equals("") ? "" : " ") + s.substring(j, i));
					}
				}
			}
			dp[i] = list;
		}
		return dp[s.length()];

	}

	// 回文数
	@Test
	public void test5() {
		System.out.println(isPalindrome(202));
	}

	public boolean isPalindrome(int x) {
		if (x < 0) {
			return false;
		}
		int res = x;
		int res2 = 0;
		while (res != 0) {
			int z = res % 10;
			res2 = res2 * 10 + z;
			res = res / 10;
		}
		return res2 == x;
	}

	// 移除元素
	@Test
	public void test6() {
		int[] nums = new int[] { 3, 2, 2, 3 };

		System.out.println(removeElement(nums, 3));
		for (int i : nums) {
			System.out.print(i + " ");
		}
	}

	public int removeElement(int[] nums, int val) {
		Arrays.sort(nums);
		int count = 0;
		boolean flag = false;
		int index = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == val) {
				flag = true;
				count++;
				continue;
			}
			if (flag) {
				index = i;
				break;
			}
		}
		if (flag && index == 0) {
			index = nums.length;
		}
		// [0,0,1,2,2,2,3,4] index=6 count=3
		for (int i = (index - count); i < (nums.length - count); i++) {
			nums[i] = nums[i + count];
		}
		return (nums.length - count);
	}

	// 搜索插入位置
	@Test
	public void test7() {
		int[] nums = new int[] { 1, 3, 5, 6 };
		System.out.println(searchInsert(nums, 5));
	}

	public int searchInsert(int[] nums, int target) {
		int length = nums.length;
		int mid, low = 0, high = length;
		if (nums.length == 0) {
			return 0;
		}
		if (nums[nums.length - 1] < target) {
			return nums.length;
		}
		if (nums[0] > target) {
			return 0;
		}
		while (low <= high) {
			mid = (low + high) / 2;
			if (nums[mid] == target) {
				return mid;
			} else if (mid > 1 && nums[mid] > target && nums[mid - 1] < target) {
				return mid;
			} else if (mid < nums.length && nums[mid] < target && nums[mid + 1] > target) {
				return mid + 1;
			} else if (nums[mid] > target) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return 0;
	}

	// 最后一个单词的长度
	@Test
	public void test8() {
		System.out.println(lengthOfLastWord("Hello World"));
	}

	public int lengthOfLastWord(String s) {
		s = s.trim();
		int index = s.lastIndexOf(" ");
		System.out.println(index);
		return s.substring(index + 1, s.length()).length();
	}

	// 二进制求和
	@Test
	public void test9() {
		System.out.println(addBinary("1010", "1011"));
	}

	// Line 3: java.lang.NumberFormatException: For input string:
	// "10100000100100110110010000010101111011011001101110111111111101000000101111001110001111100001101"
	// under radix 2
	public String addBinary(String a, String b) {
		char[] ac = a.toCharArray();
		char[] bc = b.toCharArray();
		int max = ac.length > bc.length ? ac.length : bc.length;

		if (ac.length < bc.length) {
			int diff = max - ac.length;
			char[] newA = new char[max];
			for (int i = 0; i < max; i++) {
				if (i < diff) {
					newA[i] = '0';
				} else {
					newA[i] = ac[i - diff];
				}
			}
			ac = newA;

		}
		if (bc.length < ac.length) {
			int diff = max - bc.length;
			char[] newA = new char[max];
			for (int i = 0; i < max; i++) {
				if (i < diff) {
					newA[i] = '0';
				} else {
					newA[i] = bc[i - diff];
				}
			}
			bc = newA;

		}

		StringBuffer sb = new StringBuffer();
		boolean carry = false;
		for (int i = (max - 1); i >= 0; i--) {
			String res = binaryAdd(ac[i], bc[i], carry);
			if (res.length() == 2) {
				carry = true;
				sb.append(res.substring(1));
			} else {
				carry = false;
				sb.append(res.substring(0));
			}
		}
		if (carry) {
			sb.append("1");
		}
		return sb.reverse().toString();
	}

	private String binaryAdd(char c, char d, boolean carry) {
		// carry为true代表有上一进位
		int res = Integer.parseInt(new String(new char[] { c })) + Integer.parseInt(new String(new char[] { d }));
		if (carry) {
			res += 1;
		}
		if (res == 0) {
			return "0";
		}
		if (res == 1) {
			return "1";
		}
		if (res == 2) {
			return "10";
		}
		if (res == 3) {
			return "11";
		}
		return "0";
	}

	// 最后一个单词的长度
	@Test
	public void test10() {
		System.out.println(mySqrt(8));
	}

	// 借鉴牛顿公式法
	public int mySqrt(int x) {
		if (x < 2) {
			return x;
		}
		double x0 = x;
		double x1 = (x0 + x / x0) / 2.0;
		while (Math.abs(x0 - x1) >= 1) {
			x0 = x1;
			x1 = (x0 + x / x0) / 2.0;
		}
		System.out.println(x1);
		return (int) x1;
	}

	class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	// 删除排序链表中的重复元素
	@Test
	public void test11() {
		ListNode node = new ListNode(1);
		ListNode node1 = new ListNode(1);
		ListNode node2 = new ListNode(2);
		ListNode node3 = new ListNode(3);
		ListNode node4 = new ListNode(3);
		node.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		deleteDuplicates(node);
		while (node.next != null) {
			System.out.print(node.val + "->");
			node = node.next;
		}
		System.out.print(node.val);
	}

	public ListNode deleteDuplicates(ListNode head) {
		if (head == null) {
			return null;
		}
		ListNode first = head;
		ListNode newHead = first;
		while (head.next != null) {
			ListNode node = head.next;
			if (node.val == head.val) {
				head = node;
				continue;
			}
			first.next = node;
			first = first.next;
			head = node;
		}
		first.next = null;
		return newHead;

	}

	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	// 相同的树
	@Test
	public void test12() {
		TreeNode node = new TreeNode(1);
		TreeNode node1 = new TreeNode(2);
		TreeNode node2 = new TreeNode(3);
		node.left = node1;
		node.right = node2;
		
		TreeNode node11 = new TreeNode(1);
		TreeNode node111 = new TreeNode(2);
		TreeNode node211 = new TreeNode(3);
		node11.left = node111;
		node11.right = node211;
		System.out.println(isSameTree(node,node11));
	}

	public boolean isSameTree(TreeNode p, TreeNode q) {
		List<String> listP = new ArrayList<String>();
		List<String> listQ = new ArrayList<String>();
	    deep(p,listP);
	    deep(q,listQ);
		if (listP.size() == listQ.size()) {
			boolean same = true;
			for (int i=0; i<listP.size(); i++) {
				if (!listP.get(i).equals(listQ.get(i))) {
					same = false;
				}
			}
			return same;
		}
		return false;
	}

	private void deep(TreeNode p, List list) {
		if (p==null) {
			list.add("null");
			return;
		}
		list.add(p.val+"");
		deep(p.left,list);
		deep(p.right,list);
	}
	
	//二叉树的层次遍历 II
	@Test
	public void test13() {
		TreeNode root = new TreeNode(3);
		TreeNode node1 = new TreeNode(9);
		TreeNode node2 = new TreeNode(20);
		root.left = node1;
		root.right = node2;
		
		TreeNode node3 = new TreeNode(15);
		TreeNode node4 = new TreeNode(7);
		node2.left = node3;
		node2.right = node4;
		
		List<List<Integer>> result = levelOrderBottom(root);
		if (result!=null) {
			for (List<Integer> list : result) {
				for (Integer l : list) {
					System.out.print(l+" ");
				}
				System.out.println();
			}
		}
		
	}
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
    	Map<Integer,List<Integer>> map = new LinkedHashMap<Integer,List<Integer>>();
    	levelTraverse(root,map,0);
    	
    	if (root!=null) {
    		
    		int maxLevel = 0;
    		for (Integer i : map.keySet()) {
    			if (maxLevel<i) {
    				maxLevel =i;
    			}
    		}
    		for (int i=maxLevel;i>0;i--) {
    			result.add(map.get(i));
    		}
    		List rootLevel = new ArrayList();
    		rootLevel.add(root.val);
    		result.add(rootLevel);
    	}
    	return result;
    }
    
    private void levelTraverse(TreeNode p, Map<Integer,List<Integer>> map, int level) {
    	++level;
		if (p==null) {
			return;
		}
		List list = map.get(level);
		if (list ==null) {
			list = new ArrayList<Integer>();
			
		}
		if (p.left!=null ) {
			list.add(p.left.val);
			levelTraverse(p.left,map,level);
		}
		if (p.right!=null) {
			list.add(p.right.val);
			levelTraverse(p.right,map,level);
		}
		if (list.size()>0) {
			map.put(level,list);
		}
	}
    
    //二叉树的最小深度
    public void test14() {
    	TreeNode root = new TreeNode(3);
		TreeNode node1 = new TreeNode(9);
		TreeNode node2 = new TreeNode(20);
		root.left = node1;
		root.right = node2;
		
		TreeNode node3 = new TreeNode(15);
		TreeNode node4 = new TreeNode(7);
		node2.left = node3;
		node2.right = node4;
		System.out.println(minDepth(root));
    }
    public int minDepth(TreeNode root) {
    	LinkedList<Pair<TreeNode, Integer>> stack = new LinkedList<>();
        if (root == null) {
          return 0;
        }
        else {
          stack.add(new Pair(root, 1));
        }

        int min_depth = Integer.MAX_VALUE;
        while (!stack.isEmpty()) {
          Pair<TreeNode, Integer> current = stack.pollLast();
          root = current.getKey();
          int current_depth = current.getValue();
          if ((root.left == null) && (root.right == null)) {
            min_depth = Math.min(min_depth, current_depth);
          }
          if (root.left != null) {
            stack.add(new Pair(root.left, current_depth + 1));
          }
          if (root.right != null) {
            stack.add(new Pair(root.right, current_depth + 1));
          }
        }
        return min_depth;
    }
}
