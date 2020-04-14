package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class LeetCode3 {
	// 最长回文串
	@Test
	public void test1() {
		System.out.println(longestPalindrome(
				"civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth"));
	}

	public int longestPalindrome(String s) {
		char[] chars = s.toCharArray();
		Map<Character, Integer> counts = new HashMap<Character, Integer>();
		for (int i = 0; i < chars.length; i++) {
			if (counts.get(chars[i]) == null) {
				counts.put(chars[i], 1);
			} else {
				int value = counts.get(chars[i]) + 1;
				counts.put(chars[i], value);
			}
		}
		int result = 0;
		boolean falg = false;
		for (Integer count : counts.values()) {
			if (count % 2 == 0) {
				result += count;
			} else {
				falg = true;
				result = result + count - 1;
			}
		}
		if (falg) {
			result += 1;
		}
		return result;
	}

	// 按摩师
	@Test
	public void test2() {
		int[] nums = new int[] { 2, 1, 4, 5, 3, 1, 1, 3 };
		System.out.println(massage(nums));
	}

	public int massage(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		if (nums.length == 2) {
			return nums[0] > nums[1] ? nums[0] : nums[1];
		}
		int[] maxs = new int[nums.length];
		maxs[0] = nums[0];
		maxs[1] = nums[0] > nums[1] ? nums[0] : nums[1];
		for (int i = 2; i < nums.length; i++) {
			if (maxs[i - 2] + nums[i] > maxs[i - 1]) {
				maxs[i] = maxs[i - 2] + nums[i];
			} else {
				maxs[i] = maxs[i - 1];
			}
		}
		return maxs[nums.length - 1];
	}

	// 排序数组
	@Test
	public void test3() {
		int[] res = sortArray(new int[] { 5, 1, 1, 2, 0, 0 });
		for (int i : res) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public int[] sortArray(int[] nums) {
		// 冒泡超时
//	        for (int i=0; i<nums.length; i++) {
//	        	for (int j=i+1; j<nums.length; j++) {
//	        		if (nums[i]>nums[j]) {
//	        			int temp = nums[i];
//	        			nums[i] = nums[j];
//	        			nums[j] = temp;
//	        		}
//	        	}
//	        }

		for (int i = 0; i < nums.length; i++) {
			int minIndex = i; // 从0索引开始，将索引赋给minIndex
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] < nums[minIndex]) {
					minIndex = j;// 找到最小值的索引
				}
			}
			// 将最小元素放到本次循环的前端
			int temp = nums[i];
			nums[i] = nums[minIndex];
			nums[minIndex] = temp;
		}
		return nums;
	}

	// 用队列实现栈
	@Test
	public void test4() {
		int x = 5;
		MyStack obj = new LeetCode3.MyStack();
		obj.push(x);
		int param_2 = obj.pop();
		int param_3 = obj.top();
		boolean param_4 = obj.empty();
		System.out.println(param_2 + " " + param_3 + " " + param_4);
	}

	class MyStack {
		private List list;

		/** Initialize your data structure here. */
		public MyStack() {
			list = new ArrayList();
		}

		/** Push element x onto stack. */
		public void push(int x) {
			list.add(x);
		}

		/** Removes the element on top of the stack and returns that element. */
		public int pop() {
			if (list.size() - 1 >= 0) {
				int x = (int) list.get(list.size() - 1);
				list.remove(list.size() - 1);
				return x;
			}
			return 0;
		}

		/** Get the top element. */
		public int top() {
			if (list.size() - 1 >= 0) {
				int x = (int) list.get(list.size() - 1);
				return x;
			}
			return 0;
		}

		/** Returns whether the stack is empty. */
		public boolean empty() {
			return list.isEmpty();
		}
	}

	// 合并排序的数组
	@Test
	public void test5() {
		int[] A = new int[] { 0 };
		int[] B = new int[] { 1 };
		merge(A, 0, B, B.length);
		for (int i = 0; i < A.length; i++)
			System.out.print(A[i] + ",");
	}

	public void merge(int[] A, int m, int[] B, int n) {
		int j = 0;
		for (int i = 0; i < n + m; i++) {
			if (i >= m) {
				A[i] = B[j];
				j++;
			}
		}
		Arrays.parallelSort(A);
	}

	// 字符串转换整数 (atoi)
	@Test
	public void test6() {
		System.out.println(myAtoi("-+1"));
	}

	public int myAtoi(String str) {
		str = str.trim();
		if (str.length() == 0)
			return 0;
		if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != '-' && str.charAt(0) != '+')
			return 0;
		long ans = 0L;
		boolean neg = str.charAt(0) == '-';
		int i = !Character.isDigit(str.charAt(0)) ? 1 : 0;
		while (i < str.length() && Character.isDigit(str.charAt(i))) {
			ans = ans * 10 + (str.charAt(i++) - '0');
			if (!neg && ans > Integer.MAX_VALUE) {
				ans = Integer.MAX_VALUE;
				break;
			}
			if (neg && ans > 1L + Integer.MAX_VALUE) {
				ans = 1L + Integer.MAX_VALUE;
				break;
			}
		}
		return neg ? (int) -ans : (int) ans;
	}

	public int myAtoi2(String str) {
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		boolean falg = true;
		boolean cut = false;
		boolean pOrm = false;
		int count = 0;
		boolean zero = false;
		boolean findChar = false;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != 32) {
				falg = false;
			}
			if (!falg) {
				if (!findChar && sb.length() == 0 && chars[i] == 45) {
					count++;
					pOrm = true;
				} else if (!findChar && sb.length() == 0 && chars[i] == 43) {
					count++;
					pOrm = false;
				} else {
					if (count == 2) {
						return 0;
					}
					findChar = true;
					if (!zero && chars[i] == 48) {
						continue;
					}
					zero = true;
					if (48 <= chars[i] && chars[i] <= 57) {
						sb.append(chars[i]);
					} else {
						cut = true;
					}
				}
			}

			if (cut) {
				break;
			}
		}
		if (sb.length() == 0) {
			return 0;
		}
		if (sb.toString().length() > 10) {
			return pOrm ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		}
		long value = pOrm ? -Long.valueOf(sb.toString()) : Long.valueOf(sb.toString());
		if (value > Integer.MAX_VALUE) {
			return Integer.MAX_VALUE;
		}
		if (value < Integer.MIN_VALUE) {
			return Integer.MIN_VALUE;
		}
		return (int) value;
	}

	// 旋转矩阵
	@Test
	public void test7() {
		int[][] matrix = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		rotate(matrix);
		for (int[] row : matrix) {
			System.out.println(Arrays.toString(row));
		}
	}

	public void rotate(int[][] matrix) {
		int N = matrix.length;
		for (int i = 0; i < N / 2; i++) {
			for (int j = i; j < N - i - 1; j++) {
				int temp = matrix[i][j];
				matrix[i][j] = matrix[N - j - 1][i];
				matrix[N - j - 1][i] = matrix[N - i - 1][N - j - 1];
				matrix[N - i - 1][N - j - 1] = matrix[j][N - i - 1];
				matrix[j][N - i - 1] = temp;
			}
		}

	}

	// 括号生成
	@Test
	public void test8() {
		List<String> list = generateParenthesis(2);
		for (String str : list) {
			System.out.println(str);
		}
	}
	   // 做减法

    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        // 特判
        if (n == 0) {
            return res;
        }

        // 执行深度优先遍历，搜索可能的结果
        dfs("", n, n, res);
        return res;
    }

    /**
     * @param curStr 当前递归得到的结果
     * @param left   左括号还有几个可以使用
     * @param right  右括号还有几个可以使用
     * @param res    结果集
     */
    private void dfs(String curStr, int left, int right, List<String> res) {
        // 因为每一次尝试，都使用新的字符串变量，所以无需回溯
        // 在递归终止的时候，直接把它添加到结果集即可，注意与「力扣」第 46 题、第 39 题区分
        if (left == 0 && right == 0) {
            res.add(curStr);
            return;
        }

        // 剪枝（如图，左括号可以使用的个数严格大于右括号可以使用的个数，才剪枝，注意这个细节）
        if (left > right) {
            return;
        }

        if (left > 0) {
            dfs(curStr + "(", left - 1, right, res);
        }

        if (right > 0) {
            dfs(curStr + ")", left, right - 1, res);
        }
    }

	public List<String> generateParenthesis2(int n) {
		List<String> combinations = new ArrayList();
		generateAll(new char[2 * n], 0, combinations);
		return combinations;
	}

	public void generateAll(char[] current, int pos, List<String> result) {
		if (pos == current.length) {
			if (valid(current))
				result.add(new String(current));
		} else {
			current[pos] = '(';
			generateAll(current, pos + 1, result);
			current[pos] = ')';
			generateAll(current, pos + 1, result);
		}
	}

	public boolean valid(char[] current) {
		int balance = 0;
		for (char c : current) {
			if (c == '(')
				balance++;
			else
				balance--;
			if (balance < 0)
				return false;
		}
		return (balance == 0);
	}
	

	// 两数相加 II
	@Test
	public void test9() {
		ListNode l1 = new ListNode(5);
		ListNode l11 = new ListNode(9);
		//l1.next = l11;
		ListNode l2 = new ListNode(5);
		ListNode res = addTwoNumbers(l1,l2);
		ListNode temp = res;
		while (temp!=null) {
			System.out.print(temp.val+"->");
			temp = temp.next;
		}
		
	}
	private ListNode reverseList(ListNode l) {
		ListNode resultList = new ListNode(-1);
        resultList.next= l;
        ListNode p = l;
        ListNode pNext = p.next;
        while (pNext!=null){
            p.next = pNext.next;
            pNext.next = resultList.next;
            resultList.next = pNext;
            pNext=p.next;
        }
        return resultList.next;
	}
	 public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
	
		ListNode l11 =reverseList(l1);
		ListNode l21 =reverseList(l2);
		
		ListNode temp1 = l11;
		ListNode temp11 = l11;
		ListNode temp2 = l21;
		
		ListNode lastNode = null;
		Integer last = 0;
		while (temp1!=null) {
			if (temp2!=null) {
				List value = customSum(last,temp1.val,temp2.val);
				temp1.val = (int) value.get(0);
				if (value.size()==2) {
					last = (Integer) value.get(1);
				}
				temp2 = temp2.next;
			}else {
				List value = customSum(last,temp1.val,0);
				temp1.val = (int) value.get(0);
				if (value.size()==2) {
					last = (Integer) value.get(1);
				}
			}
			temp11 = temp1;
			lastNode = temp1;
			temp1 = temp1.next;
		}
		if (temp2!=null) {
			temp11.next = temp2;
			while (temp2!=null) {
				List value = customSum(last,0,temp2.val);
				temp2.val = (int) value.get(0);
				if (value.size()==2) {
					last = (Integer) value.get(1);
				}
				lastNode = temp2;
				temp2 = temp2.next;
			}
		}
		if (last!=0) {
			lastNode.next = new ListNode(last);
		}
		l11 = reverseList(l11);
		return l11;
	 }

	private List customSum(Integer last, int val1, int val2) {
		List list = new ArrayList();
		int res = val1+val2+last;
		if(res>=10) {
			list.add(res%10);
			list.add(res/10);
			return list; 
		}
		list.add(res);
		list.add(0);
		return list;
	}
}
