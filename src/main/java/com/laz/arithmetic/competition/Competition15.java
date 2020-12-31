package com.laz.arithmetic.competition;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-221/
public class Competition15 {
	// 1704. 判断字符串的两半是否相似
	@Test
	public void test1() {
		Assert.assertEquals(true, halvesAreAlike("book"));
		Assert.assertEquals(false, halvesAreAlike("textbook"));
		Assert.assertEquals(false, halvesAreAlike("MerryChristmas"));
		Assert.assertEquals(true, halvesAreAlike("AbCdEfGh"));
	}

	public boolean halvesAreAlike(String s) {
		int n = s.length();
		char[] chars = new char[] { 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U' };
		Set<Character> vowelSet = new HashSet<Character>();
		for (int i = 0; i < chars.length; i++) {
			vowelSet.add(chars[i]);
		}
		int count1 = 0;
		int count2 = 0;
		for (int i = 0; i < n / 2; i++) {
			if (vowelSet.contains(s.charAt(i))) {
				count1++;
			}
		}
		for (int i = n / 2; i < n; i++) {
			if (vowelSet.contains(s.charAt(i))) {
				count2++;
			}
		}
		return count1 == count2;
	}

	// 1705. 吃苹果的最大数目
	@Test
	public void test2() {
		Assert.assertEquals(7, new Solution2().eatenApples(new int[] { 1, 2, 3, 5, 2 }, new int[] { 3, 2, 1, 4, 2 }));
		Assert.assertEquals(5,
				new Solution2().eatenApples(new int[] { 3, 0, 0, 0, 0, 2 }, new int[] { 3, 0, 0, 0, 0, 2 }));
		Assert.assertEquals(1, new Solution2().eatenApples(new int[] { 1 }, new int[] { 2 }));
		Assert.assertEquals(31,
				new Solution2().eatenApples(
						new int[] { 9, 10, 1, 7, 0, 2, 1, 4, 1, 7, 0, 11, 0, 11, 0, 0, 9, 11, 11, 2, 0, 5, 5 },
						new int[] { 3, 19, 1, 14, 0, 4, 1, 8, 2, 7, 0, 13, 0, 13, 0, 0, 2, 2, 13, 1, 0, 3, 7 }));
	}

	class Solution2 {
		public int eatenApples(int[] apples, int[] days) {
			int index = 1;
			int n = apples.length;
			int count = 0;
			int[] arr = new int[100000];
			int findKey = hasApple(arr, index);
			while (findKey != -1 || index <= n) {
				if (index <= n) {
					// 将苹果根据最大坚持的天数放入
					int day = days[index - 1] + index - 1;
					int num = arr[day];
					arr[day] = num + apples[index - 1];
				}
				findKey = hasApple(arr, index);
				if (findKey != -1) {
					// 从最近取出苹果进行吃
					int nowNum = arr[findKey];
					if (nowNum > 0) {
						nowNum--;
						count++; // 吃到苹果
						arr[findKey] = nowNum;
					}
				}
				index++;
			}
			return count;
		}

		private int hasApple(int[] arr, int index) {
			for (int i = index; i < arr.length; i++) {
				if (i >= index && arr[i] > 0) {
					return i;
				}
			}
			return -1;
		}
	}

	// 1706. 球会落何处
	@Test
	public void test3() {
		Assert.assertArrayEquals(new int[] { 1, -1, -1, -1, -1 },
				new Solution3().findBall(new int[][] { { 1, 1, 1, -1, -1 }, { 1, 1, 1, -1, -1 }, { -1, -1, -1, 1, 1 },
						{ 1, 1, 1, 1, -1 }, { -1, -1, -1, -1, -1 } }));
	}

	// https://leetcode-cn.com/problems/where-will-the-ball-fall/solution/java-shuang-bai-di-gui-by-ethan-jx-yvx6/
	class Solution3 {
		public int[] findBall(int[][] grid) {
			int row = grid.length;
			int col = grid[0].length;
			int[] ans = new int[col];
			for (int i = 0; i < col; i++) {
				ans[i] = out(grid, row, col, i, 0);
			}
			return ans;
		}

		private int out(int[][] grid, int row, int col, int x, int y) {
			// 到达底部
			if (y == row) {
				System.out.println(x);
				return 1;
			}

			// 卡在边缘
			if (x == 0 && grid[y][x] == -1) {
				return -1;
			}
			if (x == col - 1 && grid[y][x] == 1) {
				return -1;
			}

			// 卡在中间
			if (grid[y][x] == 1 && grid[y][x + 1] == -1) {
				return -1;
			}
			if (grid[y][x] == -1 && grid[y][x - 1] == 1) {
				return -1;
			}

			// 到达下一层
			return out(grid, row, col, x + grid[y][x], y + 1);
		}

	}

	// 1707. 与数组中元素的最大异或值
	@Test
	public void test14() {
		Assert.assertArrayEquals(new int[] { 3, 3, 7 }, new Solution4().maximizeXor(new int[] { 0, 1, 2, 3, 4 },
				new int[][] { { 3, 1 }, { 1, 3 }, { 5, 6 } }));

		Assert.assertArrayEquals(new int[] { 15, -1, 5 }, new Solution4().maximizeXor(new int[] { 5, 2, 4, 6, 6, 3 },
				new int[][] { { 12, 4 }, { 8, 1 }, { 6, 3 } }));
	}

	// https://leetcode-cn.com/problems/maximum-xor-with-an-element-from-array/solution/java-zi-dian-shu-chun-tao-mo-ban-si-lu-j-hk51/
	class Solution4 {
	    public int[] maximizeXor(int[] nums, int[][] queries) {
	        int n = queries.length;
	        int[] res = new int[n];
	        // 对 nums 从小到大排序
	        Arrays.sort(nums);
	        // 对 queries 的索引按照 mi 排序
	        Integer[] queries_idx = new Integer[n];
	        for (int i = 0; i < n; i ++) {
	            queries_idx[i] = i;
	        }
	        Arrays.sort(queries_idx, (a, b) -> queries[a][1]- queries[b][1]);
	        // 构造字典树
	        Trie trie = new Trie();
	        int num_idx = 0;
	        for (int i = 0; i < n; i ++) {   
	            // 将比当前 mi 小的数添加到字典树中
	            while (num_idx < nums.length && nums[num_idx] <= queries[queries_idx[i]][1]) {
	                trie.insert(nums[num_idx], nums[nums.length - 1]);
	                num_idx ++;
	            }
	            // 从字典树中查询
	            res[queries_idx[i]] = trie.get(queries[queries_idx[i]][0], nums[nums.length - 1]);
	        }
	        return res;

	    }    
	}
	class TrieNode {
		int val; // 当前节点存储的字符
	    TrieNode[] next = new TrieNode[2]; // 节点下的孩子数组
	    public TrieNode() {}
	    public TrieNode(int val) {
	    	this.val = val;
	    }
	}
	class Trie {
		TrieNode root;

		public Trie() {
			root = new TrieNode();
			root.val = -1;
		}
		
		// 1.插入一个整数到Trie树
		public void insert(int num, int max_num) {
			TrieNode cur_node = root;
	        // 按照 nums 中最大的数的二进制位数构造字典树
			char[] chars = binary2decimal(num, Integer.toBinaryString(max_num).length()).toCharArray();
			int len = chars.length;
			for (int i = 0; i < len; i ++) {
				char c = chars[i];
				if (c == '0') {
	                if (cur_node.next[0] == null) {
	                    cur_node.next[0] = new TrieNode();
	                }
	                cur_node = cur_node.next[0];
	            }
	            else if (c == '1') {
	                if (cur_node.next[1] == null) {
	                    cur_node.next[1] = new TrieNode();
	                }
	                cur_node = cur_node.next[1];
	            }			
			}
			cur_node.val = num;
		}
		
		// 2.判断某个单词是否在Trie树之中，由于需要最大异或值，那么在遍历字典树时，高位应尽量选择与需异或数字该位置二进制数不同的树节点
		public int get(int num, int max_num) {
			TrieNode cur_node = root;
	        // 可能 nums 中所有数全都小于 mi，此时字典树为空
	        if (cur_node.next[0] == null && cur_node.next[1] == null) {
	        	return -1;
	        }
			char[] chars = binary2decimal(num, Integer.toBinaryString(max_num).length()).toCharArray();
			int len = chars.length;
			for (int i = 0; i < len; i ++) {
				char c = chars[i];
	            // 比如高位为0，对应的树中节点存1时才有异或最大值
				if (c == '0') {
	                if (cur_node.next[1] != null) {
	                   cur_node = cur_node.next[1];
	                }
	                else {
	                    cur_node = cur_node.next[0];
	                }                
	            }
	            else if (c == '1') {
	                if (cur_node.next[0] != null) {
	                    cur_node = cur_node.next[0];
	                }
	                else {
	                    cur_node = cur_node.next[1];
	                }
	            }			
			}
			return cur_node.val ^ num;
		}
	    private String binary2decimal(int decNum, int digit) {
	        StringBuilder sb = new StringBuilder();
	        for(int i = digit - 1; i >= 0; i --) {
	            sb.append((decNum >> i) & 1);
	        }
	        return sb.toString();
	    }		
	}


}
