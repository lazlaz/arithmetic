package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode15 {
	// 比特位计数
	@Test
	public void test() {
		Assert.assertArrayEquals(new int[] { 0, 1, 1, 2, 1, 2 }, countBits(5));
	}

	public int[] countBits(int num) {
		int[] ans = new int[num + 1];
		int i = 0, b = 1;
		// [0, b) is calculated
		while (b <= num) {
			// generate [b, 2b) or [b, num) from [0, b)
			while (i < b && i + b <= num) {
				ans[i + b] = ans[i] + 1;
				++i;
			}
			i = 0; // reset i
			b <<= 1; // b = 2b
		}
		return ans;
	}

	// 有序数组的平方
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] { 4, 9, 9, 49, 121 }, sortedSquares(new int[] { -7, -3, 2, 3, 11 }));
	}

	public int[] sortedSquares(int[] A) {
		int[] res = new int[A.length];
		for (int i = 0; i < A.length; i++) {
			res[i] = A[i] * A[i];
		}
		Arrays.sort(res);
		return res;
	}

	// 比较含退格的字符串
	@Test
	public void test3() {
		Assert.assertEquals(true, backspaceCompare("ab#c", "ad#c"));
		Assert.assertEquals(true, backspaceCompare("y#fo##f", "y#f#o##f"));
		Assert.assertEquals(false, backspaceCompare("abcd", "bbcd"));
	}

	public boolean backspaceCompare(String S, String T) {
		Deque<Character> stack1 = new LinkedList<Character>();
		Deque<Character> stack2 = new LinkedList<Character>();
		for (int i = 0; i < S.length(); i++) {
			if (S.charAt(i) == '#') {
				if (stack1.size() > 0) {
					stack1.pop();
				}
			} else {
				stack1.push(S.charAt(i));
			}
		}
		for (int i = 0; i < T.length(); i++) {
			if (T.charAt(i) == '#') {
				if (stack2.size() > 0) {
					stack2.pop();
				}
			} else {
				stack2.push(T.charAt(i));
			}
		}
		if (stack1.size() != stack2.size()) {
			return false;
		}
		int len = stack1.size();
		for (int i = 0; i < len; i++) {
			if (stack1.pop() != stack2.pop()) {
				return false;
			}
		}
		return true;
	}

	// 删除无效的括号
	@Test
	public void test4() {
		List<String> res = new Solution4().removeInvalidParentheses("()())()");
		System.out.println(Joiner.on(",").join(res));
	}

	// https://leetcode-cn.com/problems/remove-invalid-parentheses/solution/301-shan-chu-wu-xiao-de-gua-hao-java-hui-su-xiang-/
	class Solution4 {
		// 用集合存储所有正确的字符串，可避免重复
		private Set<String> set = new HashSet<>();

		public List<String> removeInvalidParentheses(String s) {
			char[] ss = s.toCharArray();
			int open = 0, close = 0;
			// 获取应该去除的左右括号数
			for (char c : ss) {
				if (c == '(')
					open++;
				else if (c == ')')
					if (open > 0)
						open--;
					else
						close++;
			}
			// 回溯
			backTracking(ss, new StringBuilder(), 0, 0, 0, open, close);
			return new ArrayList(set);
		}

		public void backTracking(char[] ss, StringBuilder sb, int index, int open, int close, int openRem,
				int closeRem) {
			/**
			 * 回溯函数 分别对字符串中的每一位置的字符进行处理，最终获得符合要求的字符串加入集合中
			 * 
			 * @param ss       字符串对应的字符数组
			 * @param sb       储存当前处理过且未去除字符的字符串
			 * @param index    当前处理的字符位置
			 * @param open     当前sb中储存的左括号数
			 * @param close    当前sb中储存的右括号数
			 * @param openRem  当前需要去除的左括号数
			 * @param closeRem 当前需要去除的右括号数
			 */
			// 所有字符都处理完毕
			if (index == ss.length) {
				// 如果应去除的左右括号数都变为0，则将sb插入set
				if (openRem == 0 && closeRem == 0)
					set.add(sb.toString());
				return;
			}
			// 去掉当前位置的字符（括号），并处理下一个字符
			if (ss[index] == '(' && openRem > 0 || ss[index] == ')' && closeRem > 0)
				backTracking(ss, sb, index + 1, open, close, openRem - (ss[index] == '(' ? 1 : 0),
						closeRem - (ss[index] == ')' ? 1 : 0));
			// 不去掉当前位置字符
			// 将当前位置字符插入sb
			sb.append(ss[index]);
			// 当前位置不为括号，则直接处理下一个字符
			if (ss[index] != '(' && ss[index] != ')')
				backTracking(ss, sb, index + 1, open, close, openRem, closeRem);
			// 当前位置为左括号，增加左括号计数，处理下一个字符
			else if (ss[index] == '(')
				backTracking(ss, sb, index + 1, open + 1, close, openRem, closeRem);
			// 当前位置为右括号，且当前左括号计数大于右括号计数，则增加右括号计数，处理下一个字符
			else if (open > close)
				backTracking(ss, sb, index + 1, open, close + 1, openRem, closeRem);
			// 去除当前加入sb的字符
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	// 重排链表
	@Test
	public void test5() {
		ListNode head = Utils.createListNode(new Integer[] { 1, 2, 3, 4, 5, 6 });
		reorderList(head);
		Utils.printListNode(head);
	}

	public void reorderList(ListNode head) {
		if (head == null) {
			return;
		}
		ListNode p = head;
		Deque<ListNode> stack = new LinkedList<ListNode>();
		while (p.next != null) {
			stack.push(p);
			p = p.next;
		}
		ListNode q = head;
		while (q != p && q.next != p) {
			ListNode tmp = q.next;
			q.next = p;
			q = tmp;
			p.next = q;
			p = stack.pop();
		}
		if (q == p) {
			q.next = null;
		}
		if (q.next == p) {
			q.next = p;
			p.next = null;
		}
	}

	// 长按键入
	@Test
	public void test6() {
		Assert.assertEquals(true, isLongPressedName("alex", "aaleex"));
		Assert.assertEquals(false, isLongPressedName("saeed", "ssaaedd"));
		Assert.assertEquals(false, isLongPressedName("alex", "aaleexaaaaaaa"));
		Assert.assertEquals(false, isLongPressedName("alex", "aal"));
		Assert.assertEquals(false, isLongPressedName("dfuyalc", "fuuyallc"));
	}

	public boolean isLongPressedName(String name, String typed) {
		if (typed == null || typed.length() == 0) {
			return false;
		}
		int p = 0;
		int q = 0;
		while (p < typed.length() && q < name.length()) {
			if (typed.charAt(p) == name.charAt(q)) {
				p++;
				q++;
			} else if (p > 0 && typed.charAt(p) == typed.charAt(p - 1)) {
				p++;
			} else if (p > 0 && typed.charAt(p) != typed.charAt(p - 1)) {
				return false;
			} else if (typed.charAt(p) != name.charAt(q)) {
				return false;
			}
		}
		if (p == typed.length() && q != name.length()) {
			return false;
		}
		if (p != typed.length() && q == name.length()) {
			while (p < typed.length()) {
				if (p > 0 && typed.charAt(p) == typed.charAt(p - 1)) {
					p++;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	// 除法求值
	@Test
	public void test7() {
		List<List<String>> equations = new ArrayList<List<String>>();
		equations.add(Arrays.asList("a", "b"));
		equations.add(Arrays.asList("b", "c"));
		equations.add(Arrays.asList("d", "a"));
		double[] values = new double[] { 2.0, 3.0,2.0 };
		List<List<String>> queries = new ArrayList<List<String>>();
		queries.add(Arrays.asList("a", "c"));
		queries.add(Arrays.asList("b", "a"));
		queries.add(Arrays.asList("a", "e"));
		queries.add(Arrays.asList("a", "a"));
		queries.add(Arrays.asList("x", "x"));
		queries.add(Arrays.asList("d", "b"));
		double[] res = new Solution7().calcEquation(equations, values, queries);
		for (double d : res) {
			System.out.print(d + ",");
		}
	}
	//https://leetcode-cn.com/problems/evaluate-division/solution/zhen-zheng-de-xiao-bai-du-neng-kan-dong-de-bing-ch/
	class Solution7 {
		Map<String, String> parents;
		Map<String, Double> val;

		public String find(String x) {

			if (!x.equals(parents.get(x))) {
				String tmpParent = parents.get(x);
				String root = find(tmpParent);
				double oldVal = val.get(x);
				val.put(x, oldVal * val.get(tmpParent));
				parents.put(x, root);
			}
			return parents.get(x);
		}

		public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
			parents = new HashMap<>();
			val = new HashMap<>();
			int i = 0;
			for (List<String> equation : equations) {
				String from = equation.get(0);
				String to = equation.get(1);
				double cur = values[i];
				if (!parents.containsKey(from) && !parents.containsKey(to)) {
					parents.put(from, to);
					val.put(from, cur);
					parents.put(to, to);
					val.put(to, 1.0);
				} else if (!parents.containsKey(from)) {
					parents.put(from, to);
					val.put(from, cur);
				} else if (!parents.containsKey(to)) {
					parents.put(to, from);
					val.put(to, 1 / cur);
				} else {
					String pa = find(from);
					String pb = find(to);
					if (!pa.equals(pb)) {
						parents.put(pa, pb);
						val.put(pa, cur * val.get(to) / val.get(from));
					}
				}
				i++;
			}
			i = 0;
			double[] res = new double[queries.size()];
			for (List<String> query : queries) {
				String from = query.get(0);
				String to = query.get(1);
				if (!parents.containsKey(from) || !parents.containsKey(to)) {
					res[i++] = -1;
					continue;
				}
				String pa = find(from);
				String pb = find(to);
				if (!pa.equals(pb))
					res[i] = -1;
				else {
					res[i] = val.get(from) / val.get(to);
				}
				i++;
			}
			return res;
		}
	}
	
	//划分字母区间
	@Test
	public void test8() {
		List<Integer> res = partitionLabels("ababcbacadefegdehijhklij");
		System.out.println(Joiner.on(",").join(res));
	}
	public List<Integer> partitionLabels(String S) {
		int[] last =new int[26];
		for (int i=0;i<S.length();i++) {
			last[S.charAt(i)-'a'] = i;
		}
		int start=0,end=0;
		List<Integer> res = new ArrayList<Integer>();
		for (int i=0;i<S.length();i++) {
			 end = Math.max(end, last[S.charAt(i)-'a']);
			if (i==end) {
				res.add(end-start+1);
				start = end+1;
			}
		}
		return res;
    }
}
