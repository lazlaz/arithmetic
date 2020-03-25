package com.laz.arithmetic;

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
}
