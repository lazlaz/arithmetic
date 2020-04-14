package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import org.junit.Test;


class ListNode {
	      int val;
	      ListNode next;
	      ListNode(int x) { val = x; }
}
public class LeetCode2 {
	//链表的中间结点
	@Test
	public void test1() {
		ListNode headA = new ListNode(0);
		ListNode headA1 = new ListNode(9);
		ListNode headA2 = new ListNode(1);
		ListNode headA3 = new ListNode(2);
		ListNode headA4 = new ListNode(4);
		headA.next = headA1;
		headA1.next = headA2;
		headA2.next = headA3;
		headA3.next = headA4;
		ListNode node = middleNode(headA);
		System.out.println(node.val);
	}
    public ListNode middleNode(ListNode head) {
	   if(head==null) {
			return null;
	   }
       ListNode temp  = head;
       int count  = 0;
       while (temp!=null) {
    	   count++;
    	   temp = temp.next;
       }
       temp = head;
       
       int count2=0;
       while (temp!=null) {
    	   count2++;
    	   if (count2 == (count/2)+1) {
    		   return temp;
    	   }
    	   temp = temp.next;
    	  
    	  
       }
       return temp;
    }
    
    //两数之和 II - 输入有序数组
    @Test
    public void test2() {
    	int[] numbers = new int[] {2,7,11,15};
    	int target = 9;
    	int[] indexs = twoSum(numbers,target);
    	for (int i : indexs) {
			System.out.print(i+" ");
		}
    }
    public int[] twoSum(int[] numbers, int target) {
    	int[] values = new int[2];
    	for (int i=0;i<numbers.length; i++) {
    		for (int j=i+1; j<numbers.length; j ++) {
    			if (numbers[i]+numbers[j] == target) {
    				values[0] = i+1;
    				values[1] = j+1;
    				return values;
    			}
    		}
    	}
    	return values;
    }
    
    //Excel表列名称
    @Test
    public void test3() {
    	System.out.println(convertToTitle(26));
    }
    public String convertToTitle(int n) {
    	StringBuilder sb = new StringBuilder();
        while (n > 0) {
            int c = n % 26;
            if(c == 0){
    			c = 26;
    			n -= 1;
    		}
            sb.insert(0, (char) ('A' + c - 1));
            n /= 26;
        }
        return sb.toString();

    }
    
    //Excel表列序号
    @Test
    public void test4() {
    	System.out.println(titleToNumber("AAA"));
    }
    public int titleToNumber(String s) {
    	int count = 0;
    	int result = 0;
    	for (int i=s.length()-1;i>=0;i--) {
    		char c = s.charAt(i);
    		result+=(c-65+1)*Math.pow(26,count)  ;
    		count++;
    	}
    	return result;
    }
    
    //阶乘的零
    @Test
    public void test5() {
    	System.out.println(trailingZeroes(6));
    }
    public int trailingZeroes(int n) {
    	int count = 0;
        while (n > 0) {
            count += n / 5;
            n = n / 5;
        }
        return count;
    }	
    
    //超过经理收入的员工
    
    //select e1.Name as Employee from Employee e1, Employee e2 where e1.ManagerId=e2.Id and e1.Salary>e2.Salary
    
    //生命游戏
    @Test
    public void test7() {
    	int[][] board = new int[][]{
    		{0,1,0},
    		{0,0,1},
    		{1,1,1},
    		{0,0,0}
    	};
    	gameOfLife(board);
    }
    
    public void gameOfLife(int[][] board) {
    	 int[] neighbors = {0, 1, -1};

         int rows = board.length;
         int cols = board[0].length;

         // 创建复制数组 copyBoard
         int[][] copyBoard = new int[rows][cols];

         // 从原数组复制一份到 copyBoard 中
         for (int row = 0; row < rows; row++) {
             for (int col = 0; col < cols; col++) {
                 copyBoard[row][col] = board[row][col];
             }
         }

         // 遍历面板每一个格子里的细胞
         for (int row = 0; row < rows; row++) {
             for (int col = 0; col < cols; col++) {

                 // 对于每一个细胞统计其八个相邻位置里的活细胞数量
                 int liveNeighbors = 0;

                 for (int i = 0; i < 3; i++) {
                     for (int j = 0; j < 3; j++) {

                         if (!(neighbors[i] == 0 && neighbors[j] == 0)) {
                             int r = (row + neighbors[i]);
                             int c = (col + neighbors[j]);

                             // 查看相邻的细胞是否是活细胞
                             if ((r < rows && r >= 0) && (c < cols && c >= 0) && (copyBoard[r][c] == 1)) {
                                 liveNeighbors += 1;
                             }
                         }
                     }
                 }

                 // 规则 1 或规则 3      
                 if ((copyBoard[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                     board[row][col] = 0;
                 }
                 // 规则 4
                 if (copyBoard[row][col] == 0 && liveNeighbors == 3) {
                     board[row][col] = 1;
                 }
             }
         }
    }
    
  //机器人的运动范围
    @Test
    public void test8() {
    	System.out.println(movingCount(2, 3, 1));
    }
    public int movingCount(int m, int n, int k) {
    	 boolean[][] visited = new boolean[m][n];
         int res = 0;
         Queue<int[]> queue= new LinkedList<int[]>();
         queue.add(new int[] { 0, 0, 0, 0 });
         while(queue.size() > 0) {
             int[] x = queue.poll();
             int i = x[0], j = x[1], si = x[2], sj = x[3];
             if(i >= m || j >= n || k < si + sj || visited[i][j]) continue;
             visited[i][j] = true;
             res ++;
             queue.add(new int[] { i + 1, j, (i + 1) % 10 != 0 ? si + 1 : si - 8, sj });
             queue.add(new int[] { i, j + 1, si, (j + 1) % 10 != 0 ? sj + 1 : sj - 8 });
         }
         return res;
    }
    
    //快乐数
    @Test
    public void test9() {
    	System.out.println(isHappy(3));
    }
    private int bitSquareSum(int n) {
        int sum = 0;
        while(n > 0)
        {
            int bit = n % 10;
            sum += bit * bit;
            n = n / 10;
        }
        return sum;
    }
    public boolean isHappy(int n) {
    	  int slow = n, fast = n;
          do{
              slow = bitSquareSum(slow);
              fast = bitSquareSum(fast);
              fast = bitSquareSum(fast);
          }while(slow != fast);
          System.out.println(slow+"  "+fast);
          return slow == 1;
    }
    public boolean isHappy2(int n) {
    	
    	int value = getValue(n);
    	int count = 0;
    	while (value != 1) {
    		value = getValue(value);
    		count++;
    		if (count > 100) {
    			return false;
    		}
    	}
    	return true;
    	
    }
	private int getValue(int n) {
		int count = 0;
        while (n/10!=0) {
        	int v=n%10;
        	count+=v*v;
        	n = n /10;
        }
        count+=n%10*(n%10);
        return count;
	}
	
	//移除链表元素
	 @Test
    public void test10() {
		 ListNode headA = new ListNode(0);
			ListNode headA1 = new ListNode(9);
			ListNode headA2 = new ListNode(2);
			ListNode headA3 = new ListNode(2);
			ListNode headA4 = new ListNode(4);
			headA.next = headA1;
			headA1.next = headA2;
			headA2.next = headA3;
			headA3.next = headA4;
			ListNode n = removeElements(headA,0);
			while (n!=null) {
				System.out.print(n.val+" ");
				n = n.next;
			}
    }
  public ListNode removeElements(ListNode head, int val) {
	    ListNode sentinel = new ListNode(0);
	    sentinel.next = head;

	    ListNode prev = sentinel, curr = head;
	    while (curr != null) {
	      if (curr.val == val) prev.next = curr.next;
	      else prev = curr;
	      curr = curr.next;
	    }
	    return sentinel.next;
	  }

   public ListNode removeElements2(ListNode head, int val) {
	    ListNode temp = head;
	    ListNode newHead = null;
	    head = null;
		while (temp!=null) {
			if (temp.val != val) {
				if (head == null) {
					head = temp;
					newHead = head;
				}else {
					head.next = temp;
					head = head.next;
				}
			} 
			temp= temp.next;
		}
		if (head!=null) {
			head.next=null;
		}
		return newHead;
    }
   
 // 同构字符串
 	 @Test
     public void test11() {
 		System.out.println(isIsomorphic("foo","bar"));
     }
     public boolean isIsomorphic(String s, String t) {
    	//设s为集合A，t为集合B
         if(s.length()!=t.length()){
             return false;
         }
         HashMap<Character,Character> map=new HashMap<>();//关于A->B的映射
         for (int i = 0; i < s.length(); i++) {
             char sc=s.charAt(i);
             char tc=t.charAt(i);
             if(map.get(sc)==null){//保证A中当前元素未建立映射
                 if(map.containsValue(tc)){//保证B中当前元素未建立映射
                     return false;
                 }
                 map.put(sc,tc);//建立A中当前元素与B中当前元素一一映射关系
             }else if(map.get(sc)!=tc){
                 return false;
             }
         }
         return true;
     }
     
     // 存在重复元素 II
 	 @Test
     public void test12() {
 		int[] nums = new int[] {99,99};
 		System.out.println(containsNearbyDuplicate(nums,2));
     }
 	  public boolean containsNearbyDuplicate(int[] nums, int k) {
 		  Map<Integer,List<Integer>> counts = new HashMap<Integer,List<Integer>>();
 		  for (int i=0;i<nums.length;i++) {
 			  if (counts.get(nums[i])!=null) {
 				   List<Integer> list = counts.get(nums[i]);
 				   for (Integer integer : list) {
 					   if (i-integer <= k) {
 	 						return true;
 	 					}
				   }
 				  list.add(i);
 			  } else {
 				 List<Integer> list = new ArrayList<Integer>();
 				 list.add(i);
 				 counts.put(nums[i],list);
 			  }
 		  }
 		  return false;
 	  }
 	 public class TreeNode {
 		      int val;
 		      TreeNode left;
 		      TreeNode right;
 		      TreeNode(int x) { val = x; }
 	 }
 	  // 翻转二叉树
 	 @Test
     public void test13() {
 		TreeNode root = new TreeNode(4);
 		TreeNode node1 = new TreeNode(2);
 		TreeNode node2 = new TreeNode(7);
 		TreeNode node3 = new TreeNode(1);
 		TreeNode node4 = new TreeNode(3);
 		
 		root.left = node1;
 		root.right = node2;
 		node1.left = node3;
 		node1.right = node4;
 		
 		TreeNode n = invertTree(root);
 		System.out.println(n);
     }
    public TreeNode invertTree(TreeNode root) {
       swapNode(root);
	   return root;
    }
	private void swapNode(TreeNode root) {
		if (root==null) {
			return;
		}
		TreeNode n = root.left;
		root.left = root.right;
		root.right = n;
		swapNode(root.left);
		swapNode(root.right);
	}
	
	//2的幂
	 @Test
     public void test14() {
		 System.out.println(Math.pow(2, 1000));
		 System.out.println(isPowerOfTwo(8));
	 }
	 public boolean isPowerOfTwo(int n) {
		 if (n==1) {
			 return true;
		 }
		 int count = 1000;
		 int c  = 2;
		 for (int i=1;i<=count;i++) {
			 if (c>n) {
				 return false;
			 }
			 if (c==n) {
				 return true;
			 }
			 c = c*2;
		 }
		 return false;
	 }
	 
	 //用栈实现队列
	 @Test
	 public void test15() {
	  MyQueue obj = new MyQueue();
	  obj.push(1);
	  obj.push(2);
	  int param_2 = obj.pop();
	  int param_3 = obj.peek();
	  boolean param_4 = obj.empty();
	 }
	 
	 class MyQueue {
		private Stack<Integer> stack;
	    /** Initialize your data structure here. */
	    public MyQueue() {
			stack = new Stack<Integer>();
	    }
	    
	    /** Push element x to the back of queue. */
	    public void push(int x) {
	    	int size = stack.size();
	    	int[] array = new int[size];
	    	for (int i=0;i<size;i++) {
	    		array[i]=stack.pop();
	    	}
	    	stack.push(x);
	    	for (int i=array.length-1;i>=0;i--) {
	    		stack.push(array[i]);
	    	}
	    }
	    
	    /** Removes the element from in front of queue and returns that element. */
	    public int pop() {
	    	return stack.pop();
	    }
	    
	    /** Get the front element. */
	    public int peek() {
	    	return stack.peek();
	    }
	    
	    /** Returns whether the queue is empty. */
	    public boolean empty() {
	    	return stack.isEmpty();
	    }
	}
}
