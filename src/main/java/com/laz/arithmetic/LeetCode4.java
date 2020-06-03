package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.junit.Test;

public class LeetCode4 {
	// 丑数
	@Test
	public void test1() {
		System.out.println(isUgly(8));
	}

	public boolean isUgly(int num) {
		if (num == 0) {
			return false;
		}
		if (num == 1) {
			return true;
		}
		while (num % 2 == 0 || num % 3 == 0 || num % 5 == 0) {
			if (num % 2 == 0) {
				num = num / 2;
			} else if (num % 3 == 0) {
				num = num / 3;
			} else if (num % 5 == 0) {
				num = num / 5;
			}
		}
		if (num == 1) {
			return true;
		}
		return false;
	}

	// 单词规律
	@Test
	public void test2() {
		System.out.println(wordPattern("", "beef"));
	}

	public boolean wordPattern(String pattern, String str) {
		String[] p = pattern.split("");
		String[] s = str.split(" ");

		if (pattern.equals("") && !str.equals("")) {
			return false;
		}
		if (!pattern.equals("") && str.equals("")) {
			return false;
		}
		if (p.length != s.length) {
			return false;
		}
		Map<String, Integer> pMap = new HashMap<String, Integer>();
		Map<String, Integer> sMap = new HashMap<String, Integer>();
		int pNum = 0;
		int sNum = 0;
		String pString = "";
		String sString = "";
		for (String p1 : p) {
			if (pMap.get(p1) != null) {
				pString += pMap.get(p1);
			} else {
				pString += pNum;
				pMap.put(p1, pNum);
				pNum++;
			}
		}
		for (String s1 : s) {
			if (sMap.get(s1) != null) {
				sString += sMap.get(s1);
			} else {
				sString += sNum;
				sMap.put(s1, sNum);
				sNum++;
			}
		}
		if (pString.equals(sString)) {
			return true;
		}
		return false;
	}

	// 区域和检索 - 数组不可变
	@Test
	public void test3() {
		int[] nums = new int[] { -2, 0, 3, -5, 2, -1 };
		NumArray obj = new NumArray(nums);
		int param_1 = obj.sumRange(0, 2);
		System.out.println(param_1);
	}

	class NumArray {
		private int[] nums;

		public NumArray(int[] nums) {
			this.nums = nums;
		}

		public int sumRange(int i, int j) {
			int count = 0;
			for (int z = i; z <= j; z++) {
				count += nums[z];
			}
			return count;
		}
	}

	// 最低票价
	@Test
	public void test4() {
		int[] days = new int[] { 1, 4, 6, 7, 8, 20 };
		int[] costs = new int[] { 2, 7, 15 };
		System.out.println(mincostTickets(days, costs));
	}

	int[] costs;
	Integer[] memo;
	Set<Integer> dayset;

	// dp(i) 来表示从第 i 天开始到一年的结束，我们需要花的钱
	public int mincostTickets(int[] days, int[] costs) {
		this.costs = costs;
		memo = new Integer[366];
		dayset = new HashSet();
		for (int d : days) {
			dayset.add(d);
		}
		return dp(1);
	}

	public int dp(int i) {
		if (i > 365) {
			return 0;
		}
		if (memo[i] != null) {
			return memo[i];
		}
		if (dayset.contains(i)) {
			int value = Math.min(dp(i + 1) + costs[0], dp(i + 7) + costs[1]);
			memo[i] = Math.min(value, dp(i + 30) + costs[2]);
		} else {
			memo[i] = dp(i + 1);
		}
		return memo[i];
	}

	// 另一个树的子树
	@Test
	public void test5() {
		Integer[] sArr = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		TreeNode s = Utils.createTree(sArr, 0);
		Integer[] tArr = new Integer[] { 4, 8 };
		TreeNode t = Utils.createTree(tArr, 0);
		System.out.println(isSubtree(s, t));

	}

	public boolean isSubtree(TreeNode s, TreeNode t) {
		if (t == null)
			return true; // t 为 null 一定都是 true
		if (s == null)
			return false; // 这里 t 一定不为 null, 只要 s 为 null，肯定是 false
		return isSubtree(s.left, t) || isSubtree(s.right, t) || isSameTree(s, t);
	}

	/**
	 * 判断两棵树是否相同
	 */
	public boolean isSameTree(TreeNode s, TreeNode t) {
		if (s == null && t == null)
			return true;
		if (s == null || t == null)
			return false;
		if (s.val != t.val)
			return false;
		return isSameTree(s.left, t.left) && isSameTree(s.right, t.right);
	}

	public boolean isSubtree_2(TreeNode s, TreeNode t) {
		return isSubtree2(s, t, false);
	}

	private boolean isSubtree2(TreeNode s, TreeNode t, boolean b) {
		if (s == null && t == null) {
			return true;
		}
		if (s == null && t != null) {
			return false;
		}
		if (s != null && t == null) {
			return false;
		}
		if (b) {
			if (s.val != t.val) {
				return false;
			}
		}
		if (s.val == t.val) {
			boolean falg = (isSubtree2(s.left, t.left, true) && isSubtree2(s.right, t.right, true));
			if (falg) {
				return falg;
			} else if (!falg && b) {
				return falg;
			} else {
				return (isSubtree2(s.left, t, false) || isSubtree2(s.right, t, false));
			}
		} else {
			return (isSubtree2(s.left, t, false) || isSubtree2(s.right, t, false));
		}
	}

	// 最大正方形
	@Test
	public void test6() {
		char[][] matrix = new char[][] {

		};
		System.out.println(maximalSquare(matrix));
	}

	public int maximalSquare(char[][] matrix) {
		if (matrix == null || matrix.length == 0) {
			return 0;
		}
		int row = matrix.length;
		int col = matrix[0].length;
		int len = row > col ? col : row;
		for (int i = len; i > 0; i--) {
			// System.out.println("--");
			for (int row1 = 0; row1 <= (row - i); row1++) {
				for (int col1 = 0; col1 <= col - i; col1++) {
					boolean find = true;
					for (int j = row1; j < row1 + i; j++) {
						for (int z = col1; z < col1 + i; z++) {
							if (matrix[j][z] != '1') {
								find = false;
								break;
							}
						}
						if (!find) {
							break;
						}
					}
					if (find) {
						return i * i;
					}
				}
			}
		}
		return 0;
	}

	// 二叉树的最近公共祖先
	@Test
	public void test7() {
		Integer[] rows = new Integer[] { 3, 5, 1, 6, 2, 0, 8, null, null, 7, 4 };
		TreeNode node = Utils.createTree(rows, 0);
		TreeNode p = node.left;
		TreeNode q = node.right;
		lowestCommonAncestor(node, p, q);
	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (root == null) {
			return null;
		}
		if (root == p || root == q) {
			return root;
		}
		TreeNode left = lowestCommonAncestor(root.left, p, q);
		TreeNode right = lowestCommonAncestor(root.right, p, q);
		// p q 都在右侧
		if (left == null)
			return right;
		// p q 都在左侧
		if (right == null)
			return left;
		if (left != null && right != null) // p和q在两侧
			return root;
		return null; // 必须有返回值
	}

	// 合并K个排序链表
	@Test
	public void test8() {
		ListNode node = Utils.createListNode(new Integer[] { 1, 4, 5 });
		ListNode node1 = Utils.createListNode(new Integer[] { 1, 3, 4 });
		ListNode node2 = Utils.createListNode(new Integer[] { 2, 6 });
		ListNode[] lists = new ListNode[] { node, node1, node2 };
		//分治算法
		ListNode n = mergeKLists(lists);
	}

	public ListNode mergeKLists(ListNode[] lists) {
		int n = lists.length;
		if (n == 0) {
			return null;
		}
		if (n == 1) {
			return lists[0];
		}
		ListNode p = lists[0];
		for (int i = 1; i < n; i++) {
			p = merge2Lists(p, lists[i]);
		}
		return p;

	}

	private ListNode merge2Lists(ListNode p, ListNode q) {
		ListNode ret = new ListNode(0);// 虚拟头节点
		ListNode temp = ret;
		while (p != null && q != null) {
			if (p.val > q.val) {
				temp.next = q;
				q = q.next;
			} else {
				temp.next = p;
				p = p.next;
			}
			temp = temp.next;
		}
		if (p != null)// 只有一个链表存在时，直接将剩下的链接到结果中去
			temp.next = p;
		else
			temp.next = q;

		return ret.next;
	}
	
	//分发饼干
	
	@Test
	public void test9() {
		int[] g = new int[] {1,2,3};
		int[] s = new int[] {1,1};
		System.out.println(findContentChildren(g,s));
	}
	//贪心算法
	public int findContentChildren(int[] g, int[] s) {
	   Arrays.sort(g);
	   Arrays.sort(s);
	   int count = 0;
	   for (int i=0;i<s.length;i++) {
		   if (count<g.length&&g[count]<=s[i]) {
			   count++;
		   }
	   }
	   return count;
	}
	
	//Pow(x, n)
	@Test
	public void test10() {
		long start = System.currentTimeMillis();
		System.out.println(myPow(2,-2147483647));
		//System.out.println(System.currentTimeMillis()-start);
	}
	
	//超出时间限制 输入：0.00001 2147483647
//	public double myPow2(double x, int n) {
//		if (n == 0 ) {
//			return 1.0;
//		}
//		int count = n>0?n:(-n);
//		double res = myPow2(x,count/2);
//		if (count%2==1) {
//			res = res*res*x;
//		} else {
//			res = res*res;
//		}
//		return n>0?res:(1.0/res);
//	}
//	
	 public double quickMul(double x, long N) {
	        if (N == 0) {
	            return 1.0;
	        }
	        double y = quickMul(x, N / 2);
	        return N % 2 == 0 ? y * y : y * y * x;
	    }

	    public double myPow(double x, int n) {
	        long N = n;
	        return N >= 0 ? quickMul(x, N) : 1.0 / quickMul(x, -N);
	    }
	    
	    //子集
	    @Test
	    public void test11() {
	    	int[] nums = new int[] {1,2,3};
	    	List<List<Integer>> list = subsets(nums);
	    	for (List<Integer> list2 : list) {
				for (Integer l : list2) {
					System.out.print(l+",");
				}
				System.out.println();
			}
	    	
	    }
	    List<List<Integer>> output = new ArrayList();
	    int n, k;

	    public void backtrack(int first, ArrayList<Integer> curr, int[] nums) {
	      // if the combination is done
	      if (curr.size() == k)
	        output.add(new ArrayList(curr));

	      for (int i = first; i < n; ++i) {
	        // add i into the current combination
	        curr.add(nums[i]);
	        // use next integers to complete the combination
	        backtrack(i + 1, curr, nums);
	        // backtrack
	        curr.remove(curr.size() - 1);
	      }
	    }

	    public List<List<Integer>> subsets(int[] nums) {
	      n = nums.length;
	      for (k = 0; k < n + 1; ++k) {
	        backtrack(0, new ArrayList<Integer>(), nums);
	      }
	      return output;
	    }

	    //课程表 II
	    @Test
	    public void test12() {
	    	int numCourses = 4;
	    	int[][] prerequisites =  new int[][] {
	    		{1,0},
	    		{2,0},
	    		{3,1},
	    		{3,2}
	    	};
	    	int[] res = findOrder(numCourses, prerequisites);
	    	for (int i : res) {
				System.out.print(i+" ");
			}
	    }
	    // 存储有向图
	    Map<Integer,List<Integer>> edges=null;
	    // 标记每个节点的状态：0=未搜索，1=搜索中，2=已完成
	    int[] visited;
	    Stack<Integer> result;
	    // 判断有向图中是否有环
	    boolean invalid;
	    public int[] findOrder(int numCourses, int[][] prerequisites) {
	    	 edges= new HashMap<Integer,List<Integer>>();
	         visited= new int[numCourses];
	         result = new Stack();
	         
	         Map<String,Object> keys = new HashMap<String,Object>();
	         for (int[] info: prerequisites) {
	        	 List<Integer> list = new ArrayList<Integer>();
	        	 if (edges.get(info[1])!=null) {
	        		 list = edges.get(info[1]);
	        	 }
	        	 list.add(info[0]);
	        	 edges.put(info[1], list);
	         }
	         // 每次挑选一个「未搜索」的节点，开始进行深度优先搜索
	         for (int i = 0; i < numCourses && !invalid; ++i) {
	             if (visited[i]==0) {
	                 dfs(i);
	             }
	         }
	         if (invalid) {
	             return new int[] {};
	         }
	         // 如果没有环，那么就有拓扑排序
	         // 注意下标 0 为栈底，因此需要将数组反序输出
	         int[]  rs = new int[result.size()];
	         for (int i=0;i<rs.length;i++) {
	        	 rs[i] = result.pop();
	         }
	         return rs;
	    }

		private void dfs(int u) {
			 // 将节点标记为「搜索中」
	        visited[u] = 1;
	        // 搜索其相邻节点
	        // 只要发现有环，立刻停止搜索
	        if (edges.get(u)!=null) {
	        	for (int v: edges.get(u)) {
	        		// 如果「未搜索」那么搜索相邻节点
	        		if (visited[v] == 0) {
	        			dfs(v);
	        			if (invalid) {
	        				return;
	        			}
	        		}
	        		// 如果「搜索中」说明找到了环
	        		else if (visited[v] == 1) {
	        			invalid = true;
	        			return;
	        		}
	        	}
	        }
	        // 将节点标记为「已完成」
	        visited[u] = 2;
	        // 将节点入栈
	        result.push(u);
		}
		
		  //课程表 II
	    @Test
	    public void test13() {
	    	System.out.println(findTheLongestSubstring("aa"));
	    }
	    /**
	     * 
	                   思路描述：官方题解的 前缀和+状态压缩 是真的没想到，这里稍微做个解释吧，

			首先题目中要求子字符串中每个元音字母恰好出现偶数次，我们就可以使用 0 和 1 来标识每个字母的状态(偶数次或奇数次)，我们不需要知道每个字母出现的完整次数，只需要知道这个次数的奇偶性
			
			那么我们可以注意到奇数次 + 1 = 偶数次，偶数次 + 1 = 奇数次，所以我们可以使用 异或 来参与运算： 比如 aba
			
			初始时 status = 00000，然后到 a 的时候 00000 ^ 00001 = 00001，1 说明 a 出现奇数次
			
			然后到 b 的时候 00001 ^ 00010 = 00011，两个 1 说明 a、b 都出现奇数次
			
			最后到 a 的时候 00011 ^ 00001 = 00010，说明只有 b 出现奇数次了。
			
			以上也说明我们确实是可以使用状态码去标识每个元音字母出现次数的奇偶性。
			
			那么我们怎么去统计最长子串的长度呢？
			
			首先我们先盘盘哪些子串符合要求，因为现在每个下标对应的状态码其实也就只有 0 和 1
			
			如果坐标 i 对应的状态码是 00011，坐标 j 对应的状态码是 00011，那么他们俩中间的元音字母数一定是偶数，如果某一位不相同，那么绝对不可能是偶数，因为偶数-奇数=奇数，奇数-偶数=奇数
			
			所以我们每次求出一个坐标的状态码的时候就去瞅瞅这个状态码前面是否存在，如果存在，那么就计算一下之间子字符串的长度就 ok 了，那么我们还需要啥？明显需要一个hash表，存储每个状态码对应的下标！当然因为我们状态码最长也就是 11111 = 2^5 - 1 = 31，开一个 32 大小的数组就好了。
			因为是学习官方题解写的，代码也差不多，这个代码确实是比较简洁清晰的，也没必要去复盘。
	     * @param s
	     * @return
	     */
	    public int findTheLongestSubstring(String s) {
	        int n = s.length();
	        int[] pos = new int[1 << 5];
	        Arrays.fill(pos, -1);
	        int ans = 0, status = 0;
	        pos[0] = 0;
	        for (int i = 0; i < n; i++) {
	            char ch = s.charAt(i);
	            if (ch == 'a') {
	                status ^= (1 << 0);
	            } else if (ch == 'e') {
	                status ^= (1 << 1);
	            } else if (ch == 'i') {
	                status ^= (1 << 2);
	            } else if (ch == 'o') {
	                status ^= (1 << 3);
	            } else if (ch == 'u') {
	                status ^= (1 << 4);
	            }
	            if (pos[status] >= 0) {
	                ans = Math.max(ans, i + 1 - pos[status]);
	            } else {
	                pos[status] = i + 1;
	            }
	        }
	        return ans;
	    }
	    
	    //最长回文子串
	    @Test
	    public void test14() {
	    	String s = "cddbaaa";
	    	System.out.println(longestPalindrome(s));
	    }
	    public String longestPalindrome(String s) {
	    	int n = s.length();
	        boolean[][] dp =new boolean[n][n];
	        String ans = "";
	    	for (int l = 0; l < n; ++l) {
	            for (int i = 0; i + l < n; ++i) {
	                int j = i + l;
	                System.out.print("i="+i+" ");
	                System.out.println("j="+j);
	                if (l == 0) {
	                    dp[i][j] = true;
	                }
	                else if (l == 1) {
	                	if (s.charAt(i) == s.charAt(j)) {
	                		dp[i][j] = true;
	                	} else {
	                		dp[i][j] = false;
	                	}
	                }
	                else {
	                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
	                  
	                }
	                if (dp[i][j] && l + 1 > ans.length()) {
	                    ans = s.substring(i, j + 1);
	                }
	            }
	        }
			return ans;
	    }

	    //猜数字游戏
	    @Test
	    public void test15() {
	    	String secret = "1122";
	    	String guess = "2211";
	    	System.out.println(getHint(secret, guess));
	    	
	    }
	    public String getHint(String secret, String guess) {
	    	int A = 0;
	    	int B = 0;
	    	Map<String,Integer>  mapSecret  = new HashMap<String,Integer>();
	    	Map<String,Integer>  mapGuess  = new HashMap<String,Integer>();
	    	for (int i=0;i<secret.length();i++) {
	    		if (secret.charAt(i) == guess.charAt(i)) {
	    			A++;
	    		} else {
	    			Integer c = mapSecret.get(secret.charAt(i)+"");
	    			if (c==null) {
	    				mapSecret.put(secret.charAt(i)+"", 1);
	    			} else {
	    				mapSecret.put(secret.charAt(i)+"", ++c);
	    			}
	    			
	    			Integer c1 = mapGuess.get(guess.charAt(i)+"");
	    			if (c1==null) {
	    				mapGuess.put(guess.charAt(i)+"", 1);
	    			} else {
	    				mapGuess.put(guess.charAt(i)+"", ++c1);
	    			}
	    		}
	    	}
	    	for (String key:mapSecret.keySet()) {
	    		Integer c1 = mapGuess.get(key);
    			if (c1!=null) {
    				B += Math.min(c1, mapSecret.get(key));
    			} 
	    	}
	    	return new StringBuffer().append(A+"A"+B+"B").toString();
	    }
	    
	  // LRU缓存机制
	    @Test
	    public void test16() {
	    	LRUCache cache = new LRUCache( 2 /* 缓存容量 */ );
	    	cache.put(2, 1);
	    	cache.put(1, 1);
	    	cache.put(2, 3);
	    	cache.put(4, 1);
	    	System.out.println(cache.get(1)); // 返回  1
	    	System.out.println(cache.get(2)); // 返回  1
	    }
	    class LRUCache {
	    	class DLinkedNode {
	            int key;
	            int value;
	            DLinkedNode prev;
	            DLinkedNode next;
	            public DLinkedNode() {}
	            public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
	        }

	        private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
	        private int size;
	        private int capacity;
	        private DLinkedNode head, tail;

	        public LRUCache(int capacity) {
	            this.size = 0;
	            this.capacity = capacity;
	            // 使用伪头部和伪尾部节点
	            head = new DLinkedNode();
	            tail = new DLinkedNode();
	            head.next = tail;
	            tail.prev = head;
	        }

	        public int get(int key) {
	            DLinkedNode node = cache.get(key);
	            if (node == null) {
	                return -1;
	            }
	            // 如果 key 存在，先通过哈希表定位，再移到头部
	            moveToHead(node);
	            return node.value;
	        }

	        public void put(int key, int value) {
	            DLinkedNode node = cache.get(key);
	            if (node == null) {
	                // 如果 key 不存在，创建一个新的节点
	                DLinkedNode newNode = new DLinkedNode(key, value);
	                // 添加进哈希表
	                cache.put(key, newNode);
	                // 添加至双向链表的头部
	                addToHead(newNode);
	                ++size;
	                if (size > capacity) {
	                    // 如果超出容量，删除双向链表的尾部节点
	                    DLinkedNode tail = removeTail();
	                    // 删除哈希表中对应的项
	                    cache.remove(tail.key);
	                    --size;
	                }
	            }
	            else {
	                // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
	                node.value = value;
	                moveToHead(node);
	            }
	        }

	        private void addToHead(DLinkedNode node) {
	            node.prev = head;
	            node.next = head.next;
	            head.next.prev = node;
	            head.next = node;
	        }

	        private void removeNode(DLinkedNode node) {
	            node.prev.next = node.next;
	            node.next.prev = node.prev;
	        }

	        private void moveToHead(DLinkedNode node) {
	            removeNode(node);
	            addToHead(node);
	        }

	        private DLinkedNode removeTail() {
	            DLinkedNode res = tail.prev;
	            removeNode(res);
	            return res;
	        }
	    }
	    
	    //和可被 K 整除的子数组
	    @Test
	    public void test17() {
	    	int[] A = new int[] {4,-5,0,-2,-3,1};
	    	System.out.println(subarraysDivByK(A, 5));
	    }
	    public int subarraysDivByK(int[] A, int K) {
	    	// key：前缀和，value：key 对应的前缀和的个数
	    	Map<Integer, Integer> preSumFreq = new HashMap<>();
	    	// 对于下标为 0 的元素，前缀和为 0，个数为 1
	    	preSumFreq.put(0, 1);
	    	
	    	int preSum = 0;
	    	int count = 0;
	    	for (int num : A) {
	    		  preSum += num;
	    		 // 注意 Java 取模的特殊性，当被除数为负数时取模结果为负数，需要纠正
	    		  int modulus = (preSum % K + K) % K;
	              int same = preSumFreq.getOrDefault(modulus, 0);
	              count += same;
	              preSumFreq.put(modulus, same + 1);
	    	}
	    	return count;
	    }
	    
	    //字符串解码
	    @Test
	    public void test18() {
	    	String s = "3[a2[c]]";
	    	System.out.println(decodeString(s));
	    }
	    
	    /**
	     * 
本题中可能出现括号嵌套的情况，比如 2[a2[bc]]，这种情况下我们可以先转化成 2[abcbc]，在转化成 abcbcabcbc。我们可以把字母、数字和括号看成是独立的 TOKEN，并用栈来维护这些 TOKEN。具体的做法是，遍历这个栈：
如果当前的字符为数位，解析出一个数字（连续的多个数位）并进栈
如果当前的字符为字母或者左括号，直接进栈
如果当前的字符为右括号，开始出栈，一直到左括号出栈，出栈序列反转后拼接成一个字符串，此时取出栈顶的数字（此时栈顶一定是数字，想想为什么？），就是这个字符串应该出现的次数，我们根据这个次数和字符串构造出新的字符串并进栈
	     * @param s
	     * @return
	     */
	    int ptr;

	    public String decodeString(String s) {
	        LinkedList<String> stk = new LinkedList<String>();
	        ptr = 0;

	        while (ptr < s.length()) {
	            char cur = s.charAt(ptr);
	            if (Character.isDigit(cur)) {
	                // 获取一个数字并进栈
	                String digits = getDigits(s);
	                stk.addLast(digits);
	            } else if (Character.isLetter(cur) || cur == '[') {
	                // 获取一个字母并进栈
	                stk.addLast(String.valueOf(s.charAt(ptr++))); 
	            } else {
	                ++ptr;
	                LinkedList<String> sub = new LinkedList<String>();
	                while (!"[".equals(stk.peekLast())) {
	                    sub.addLast(stk.removeLast());
	                }
	                Collections.reverse(sub);
	                // 左括号出栈
	                stk.removeLast();
	                // 此时栈顶为当前 sub 对应的字符串应该出现的次数
	                int repTime = Integer.parseInt(stk.removeLast());
	                StringBuffer t = new StringBuffer();
	                String o = getString(sub);
	                // 构造字符串
	                while (repTime-- > 0) {
	                    t.append(o);
	                }
	                // 将构造好的字符串入栈
	                stk.addLast(t.toString());
	            }
	        }

	        return getString(stk);
	    }

	    public String getDigits(String s) {
	        StringBuffer ret = new StringBuffer();
	        while (Character.isDigit(s.charAt(ptr))) {
	            ret.append(s.charAt(ptr++));
	        }
	        return ret.toString();
	    }

	    public String getString(LinkedList<String> v) {
	        StringBuffer ret = new StringBuffer();
	        for (String s : v) {
	            ret.append(s);
	        }
	        return ret.toString();
	    }
	    
	    //4的幂
	    @Test
	    public void test19() {
	    	System.out.println(isPowerOfFour(16));
	    }
	    //不適用递归或者循环
	    public boolean isPowerOfFour(int num) {
	    	 //公式：x > 0 and x & (x - 1) == 0 满足x 是否为 2 的幂
	    	//x%3=1 (2#2kmod3)=(4#k mod3)=((3+1)#kmod3)=1
	    	 return (num > 0) && ((num & (num - 1)) == 0) && (num % 3 == 1);
	    }
	  //新21点
	    @Test
	    public void test20() {
	    	System.out.println(new21Game(21,17,10));
	    }
	   
	    //动态规划，思路参考https://leetcode-cn.com/problems/new-21-game/solution/huan-you-bi-zhe-geng-jian-dan-de-ti-jie-ma-tian-ge/
	    public double new21Game(int N, int K, int W) {
	    	double[] dp = new double[K+W];
	    	double s = 0;
	    	for (int i=K; i<K+W;i++) {
	    		dp[i] = 1;
	    		if (i>N) {
	    			dp[i] = 0;
	    		} 
	    		s+=dp[i];
	    	}
	    	for (int i=K-1;i>=0;i--) {
	    		dp[i] = s/W;
	    		s = s-dp[i+W]+dp[i];
	    	}
	    	return dp[0];
	    }
}
