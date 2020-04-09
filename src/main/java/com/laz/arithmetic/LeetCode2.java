package com.laz.arithmetic;

import java.util.LinkedList;
import java.util.Queue;

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
}
