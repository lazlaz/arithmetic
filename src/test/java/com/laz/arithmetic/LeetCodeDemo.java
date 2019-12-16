package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

public class LeetCodeDemo {
	// 旋转图像
	@Test
	public void test1() {
		int matrix[][] = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		int n = matrix.length;

		// transpose matrix
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				int tmp = matrix[j][i];
				matrix[j][i] = matrix[i][j];
				matrix[i][j] = tmp;
			}
		}

		// reverse each row
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n / 2; j++) {
				int tmp = matrix[i][j];
				matrix[i][j] = matrix[i][n - j - 1];
				matrix[i][n - j - 1] = tmp;
			}
		}

		System.out.println(matrix.length);
		printArray(matrix);

	}

	// Line 18: java.lang.NumberFormatException: For input string: "9646324351"
	@Test
	public void test2() {
		int x = -123;
		System.out.println(reverse(x));
	}

	// 字符串中的第一个唯一字符
	@Test
	public void test3() {
		String s = "cc";
		System.out.println(firstUniqChar(s));
	}

	// 验证回文字符串
	@Test
	public void test4() {
		String s = "A man, a plan, a canal: Panama";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 100000; i++) {
			sb.append(s);
		}
		long start = System.currentTimeMillis();
		System.out.println(isPalindrome(sb.toString()));
		long end = System.currentTimeMillis();
		System.out.println("耗时：" + (end - start));
	}

	// 字符串转换整数 (atoi)
	@Test
	public void test5() {
		String s = "4193 with words";
		System.out.println(myAtoi(s));
	}

	// 实现 strStr()
	@Test
	public void test6() {
		System.out.println(strStr("hello", "ll"));
	}

	// 最长公共前缀
	@Test
	public void test7() {
		System.out.println(longestCommonPrefix(new String[] { "aca", "cba" }));
	}

	// 删除链表中的节点
	@Test
	public void test8() {
		ListNode node = new ListNode(4);
		ListNode node1 = new ListNode(5);
		ListNode node2 = new ListNode(1);
		ListNode node3 = new ListNode(9);

		node.next = node1;
		node1.next = node2;
		node2.next = node3;
		deleteNode(node1);
		while (node.next != null) {
			System.out.print(node.val + "->");
			node = node.next;
		}
		System.out.print(node.val);
	}

	// 删除链表的倒数第N个节点
	@Test
	public void test9() {
		ListNode head = new ListNode(1);
		ListNode node1 = new ListNode(2);
		ListNode node2 = new ListNode(3);
		ListNode node3 = new ListNode(4);
		ListNode node4 = new ListNode(5);
		head.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		head = removeNthFromEnd(head, 2);
		while (head.next != null) {
			System.out.print(head.val + "->");
			head = head.next;
		}
		System.out.print(head.val);
	}

	// 反转一个单链表
	@Test
	public void test10() {
		ListNode head = new ListNode(1);
		ListNode node1 = new ListNode(2);
		ListNode node2 = new ListNode(3);
		ListNode node3 = new ListNode(4);
		ListNode node4 = new ListNode(5);
		head.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		head = reverseList(head);
		while (head.next != null) {
			System.out.print(head.val + "->");
			head = head.next;
		}
		System.out.print(head.val);
	}

	// 合并两个有序链表
	@Test
	public void test11() {
		
		ListNode head = new ListNode(1);
		ListNode node1 = new ListNode(2);
		ListNode node2 = new ListNode(3);
		ListNode node3 = new ListNode(4);
		ListNode node4 = new ListNode(5);
		head.next = node1;
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		
		ListNode head2 = new ListNode(1);
		ListNode node12 = new ListNode(5);
		ListNode node22 = new ListNode(7);
		ListNode node32= new ListNode(8);
		ListNode node42 = new ListNode(9);
		head2.next = node12;
		node12.next = node22;
		node22.next = node32;
		node32.next = node42;
		head = mergeTwoLists(head,head2);
		while (head.next != null) {
			System.out.print(head.val + "->");
			head = head.next;
		}
		System.out.print(head.val);
	}

	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		ListNode newNode = l1;
		if (l1==null) {
			return l2;
		}
		if (l2==null) {
			return l1;
		}
		List<ListNode> list = new ArrayList<ListNode>();
		ListNode head = l1;
		list.add(head);
		while (head.next != null) {
			head = head.next;
			list.add(head);
		}
		head = l2;
		list.add(head);
		while (head.next != null) {
			head = head.next;
			list.add(head);
		}
		//链排序
		list.sort(new Comparator<ListNode>() {

			@Override
			public int compare(ListNode o1, ListNode o2) {
				if (o1.val<o2.val) {
					return -1;
				}
				if (o1.val>o2.val) {
					return 1;
				}
				return 0;
			}
		});
		 newNode = list.get(0);
		for (int i=0; i<list.size()-1; i++) {
			ListNode node = list.get(i);
			node.next = list.get(i+1);
		}
		return newNode;
	}

	public ListNode reverseList(ListNode head) {
		if (head == null) {
			return head;
		}
		ListNode tail = head;
		ListNode node = head.next;
		while (node != null) {
			ListNode ln = node;
			node = ln.next;
			ln.next = head;
			head = ln;
		}
		tail.next = null;
		return head;
	}

	public ListNode removeNthFromEnd(ListNode head, int n) {
		int count = 0;
		ListNode temp = head;
		while (temp.next != null) {
			count++;
			temp = temp.next;
		}
		count++;
		if (count == n) {
			return head.next;
		}
		int i = 0;

		temp = head;
		while (temp.next != null) {
			if (count - i - 1 == n) {
				temp.next = temp.next.next;
				break;
			}
			i++;
			temp = temp.next;
		}
		return head;
	}

	class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public void deleteNode(ListNode node) {
		node.val = node.next.val;
		node.next = node.next.next;
	}

	public String longestCommonPrefix(String[] strs) {
		if (strs.length == 0) {
			return "";
		}
		int count = 0;
		boolean same = true;
		for (int z = 0; z < strs[0].length(); z++) {
			char index = strs[0].toCharArray()[z];
			for (int i = 1; i < strs.length; i++) {
				if (strs[i].length() <= z) {
					same = false;
					break;
				}
				if (strs[i].toCharArray()[count] != index) {
					same = false;
					break;
				}
			}
			if (!same) {
				break;
			} else {
				count++;

			}
		}
		if (count == 0) {
			return "";
		}
		return strs[0].substring(0, count);
	}

	public int strStr(String haystack, String needle) {
		if (needle.equals("")) {
			return 0;
		}
		if (needle.length() > haystack.length()) {
			return -1;
		}
		char[] sourcs = haystack.toCharArray();
		char[] target = needle.toCharArray();
		int index = -1;
		for (int j = 0; j < sourcs.length; j++) {
			if (target[0] == sourcs[j]) {
				index = j;
			}
			if (sourcs.length - j < target.length) {
				return -1;
			}

			for (int i = 0; i < target.length; i++) {
				if (target[i] != sourcs[j + i]) {
					index = -1;
				}
			}
			if (index != -1) {
				return index;
			}
		}
		return index;
	}

	public int myAtoi(String str) {
		char[] chars = str.toCharArray();
		int minusAscii = 45;
		int frist = 0;
		List<Character> cList = new ArrayList<>();
		boolean minus = false;
		boolean abort = false;
		// 数字ascii码 48 57
		for (int i = 0; i < chars.length; i++) {
			if (frist == 0) {
				if ((int) chars[i] == 32) {

				} else {
					frist++;
					if (frist == 1) {
						if ((int) chars[i] == minusAscii) {
							minus = true;
						} else if ((int) chars[i] == 43) {
							minus = false;
						} else if (((int) chars[i] < 48 || (int) chars[i] > 57)) {
							return 0;
						} else {
							cList.add(chars[i]);
						}
					} else {
						if (((int) chars[i] < 48 || (int) chars[i] > 57)) {
							abort = true;
						} else {
							if (!abort) {
								cList.add(chars[i]);
							}
						}
					}
				}
			} else {
				frist++;
				if (frist == 1) {
					if ((int) chars[i] == minusAscii) {
						minus = true;
					} else if ((int) chars[i] == 43) {
						minus = false;
					} else if (((int) chars[i] < 48 || (int) chars[i] > 57)) {
						return 0;
					} else {
						cList.add(chars[i]);
					}
				} else {
					if (((int) chars[i] < 48 || (int) chars[i] > 57)) {
						abort = true;
					} else {
						if (!abort) {
							cList.add(chars[i]);
						}
					}
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		boolean a = true;
		for (Character cs : cList) {
			if (cs != '0') {
				a = false;
			}
			if (!a) {
				sb.append(cs);
			}
		}
		if (sb.toString().isEmpty()) {
			return 0;
		}
		if (minus) {
			if (sb.toString().length() > 10) {
				return (int) -Math.pow(2, 31);

			}
		} else {
			if (sb.toString().length() > 10) {
				return (int) (Math.pow(2, 31) - 1);
			}
		}
		long val = Long.parseLong(sb.toString());
		if (minus) {
			val = -val;
			if (val < -Math.pow(2, 31)) {
				return (int) -Math.pow(2, 31);
			}
		} else {
			if (val > Math.pow(2, 31) - 1) {
				return (int) (Math.pow(2, 31) - 1);
			}
		}

		return (int) val;
	}

	// 超时
	private boolean isPalindrome2(String s) {
		char[] cs = s.toCharArray();
		List<Character> cList = new ArrayList<Character>();
		for (int i = 0; i < cs.length; i++) {
			if ((cs[i] >= 48 && cs[i] <= 57) || (cs[i] >= 65 && cs[i] <= 90)
					|| (cs[i] >= 97 && cs[i] <= 122)) {
				cList.add(cs[i]);
			}
		}
		Character[] newC = cList.toArray(new Character[] {});
		StringBuffer value = new StringBuffer();
		for (Character character : newC) {
			value.append(character);
		}

		if (value.toString().equalsIgnoreCase(
				new StringBuffer(value).reverse().toString())) {
			return true;
		}
		return false;
	}

	private boolean isPalindrome(String s) {
		s = s.toLowerCase();
		char[] cs = s.toCharArray();
		List<Character> cList = new ArrayList<Character>();
		for (int i = 0; i < cs.length; i++) {
			if ((cs[i] >= 48 && cs[i] <= 57) || (cs[i] >= 65 && cs[i] <= 90)
					|| (cs[i] >= 97 && cs[i] <= 122)) {
				cList.add(cs[i]);
			}
		}
		Character[] newC = cList.toArray(new Character[] {});
		boolean plalindrome = true;
		for (int i = 0; i < newC.length / 2; i++) {

			if (newC[i] != newC[newC.length - 1 - i]) {
				plalindrome = false;
				break;
			}
		}
		return plalindrome;
	}

	public int firstUniqChar(String s) {
		char[] c = s.toCharArray();
		int res = -1;

		List<Character> repeatChar = new ArrayList<Character>();
		for (int i = 0; i < c.length; i++) {
			if (repeatChar.contains(c[i])) {
				continue;
			}
			boolean repeat = false;
			for (int j = i + 1; j < c.length; j++) {
				if (c[j] == c[i]) {
					repeat = true;
				}
			}
			if (!repeat) {
				res = i;
				break;
			} else {
				repeatChar.add(c[i]);
			}
		}
		return res;
	}

	private int reverse(int x) {
		boolean minus = false;

		String s = Integer.toString(x);
		char[] chars = s.toCharArray();
		char[] newsChar = chars;
		if (chars[0] == '-') {
			minus = true;
			newsChar = new char[chars.length - 1];
			System.arraycopy(chars, 1, newsChar, 0, chars.length - 1);

		}
		int len = newsChar.length;
		for (int i = 0; i < len / 2; i++) {
			char temp = newsChar[i];
			newsChar[i] = newsChar[len - i - 1];
			newsChar[len - i - 1] = temp;
		}
		String str = new String(newsChar);
		;
		if (minus) {
			System.arraycopy(newsChar, 0, chars, 1, newsChar.length);
			chars[0] = '-';
			str = new String(chars);
		}

		long val = Long.parseLong(str);
		if (val > Math.pow(2, 31) - 1 || val < -Math.pow(2, 31)) {
			return 0;
		}

		return (int) val;

	}

	private void printArray(int[][] matrix) {
		for (int[] is : matrix) {
			for (int i : is) {
				System.out.print(i + " ");
			}
			System.out.println();
		}

	}
}
