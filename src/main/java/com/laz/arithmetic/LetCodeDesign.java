package com.laz.arithmetic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import org.junit.Test;

public class LetCodeDesign {
	class Solution {
		private int[] nums;
		private int[] initNums;

		public Solution(int[] nums) {
			this.nums = nums;
			this.initNums = nums;
		}

		/** Resets the array to its original configuration and return it. */
		public int[] reset() {
			return this.initNums;
		}

		/** Returns a random shuffling of the array. */
		public int[] shuffle() {
			Random r = new Random();
			int[] shuffle = new int[this.nums.length];
			int i = 0;
			Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
			while (i < this.nums.length) {
				int count = r.nextInt(this.nums.length);
				if (map.get(count) != null && map.get(count)) {
				} else {
					shuffle[i] = this.nums[count];
					i++;
					map.put(count, true);
				}
			}
			return shuffle;
		}
	}

	// Shuffle an Array
	@Test
	public void test1() {
		int[] nums = { 1, 2, 3 };
		Solution obj = new Solution(nums);
		int[] param_1 = obj.reset();
		int[] param_2 = obj.shuffle();
	}

	// 最小栈
	@Test
	public void test2() {
		MinStack minStack = new MinStack();
		minStack.push(-2);
		minStack.push(0);
		minStack.push(-3);
		System.out.println(minStack.getMin());
		;
		minStack.pop();
		System.out.println(minStack.top());
		System.out.println(minStack.getMin());
	}

	// 最小栈(解法2)
	@Test
	public void test3() {
		MinStack2 minStack = new MinStack2();
		minStack.push(2);
		minStack.push(0);
		minStack.push(-3);
		minStack.push(-3);
		minStack.push(-3);
		minStack.push(-3);
		System.out.println(minStack.getMin());
		;
		minStack.pop();
		minStack.pop();
		minStack.pop();
		minStack.pop();
		
		System.out.println(minStack.top());
		System.out.println(minStack.getMin());
	}

	public class MinStack2 {

		// 数据栈
		private Stack<Integer> data;
		// 辅助栈
		private Stack<Integer> helper;

		/**
		 * initialize your data structure here.
		 */
		public MinStack2() {
			data = new Stack<>();
			helper = new Stack<>();
		}

		// 思路 2：辅助栈和数据栈不同步
		// 关键 1：辅助栈的元素空的时候，必须放入新进来的数
		// 关键 2：新来的数小于或者等于辅助栈栈顶元素的时候，才放入（特别注意这里等于要考虑进去）
		// 关键 3：出栈的时候，辅助栈的栈顶元素等于数据栈的栈顶元素，才出栈，即"出栈保持同步"就可以了

		public void push(int x) {
			// 辅助栈在必要的时候才增加
			data.add(x);
			// 关键 1 和 关键 2
			if (helper.isEmpty() || helper.peek() >= x) {
				helper.add(x);
			}
		}

		public void pop() {
			// 关键 3：data 一定得 pop()
			if (!data.isEmpty()) {
				// 注意：声明成 int 类型，这里完成了自动拆箱，从 Integer 转成了 int，因此下面的比较可以使用 "=="
				// 运算符
				// 参考资料：https://www.cnblogs.com/GuoYaxiang/p/6931264.html
				// 如果把 top 变量声明成 Integer 类型，下面的比较就得使用 equals 方法
				int top = data.pop();
				if (top == helper.peek()) {
					helper.pop();
				}
			}
		}

		public int top() {
			if (!data.isEmpty()) {
				return data.peek();
			}
			throw new RuntimeException("栈中元素为空，此操作非法");
		}

		public int getMin() {
			if (!helper.isEmpty()) {
				return helper.peek();
			}
			throw new RuntimeException("栈中元素为空，此操作非法");
		}

	}

	class MinStack {
		private int min = Integer.MIN_VALUE;
		private int[] stacks = new int[10];
		private int index = 0;

		/** initialize your data structure here. */
		public MinStack() {
		}

		public void push(int x) {
			if (index == 0) {
				min = x;
			}
			if (min > x) {
				min = x;
			}
			stacks[index] = x;
			index++;
			if (index >= stacks.length) {
				stacks = Arrays.copyOf(stacks, stacks.length + 10);
			}
		}

		public void pop() {
			if (index > 0) {
				if (min == stacks[index - 1]) {
					min = stacks[0];
					for (int i = 0; i < index - 1; i++) {
						if (min > stacks[i]) {
							min = stacks[i];
						}
					}
				}
				index--;
			}
		}

		public int top() {
			return stacks[index - 1];
		}

		public int getMin() {
			if (min == Integer.MIN_VALUE) {
				return stacks[index - 1];
			}
			return min;
		}
	}

}
