package com.laz.arithmetic;

public class Solution {
    public int reverse(int x) {
        String s=Integer.toString(x);
        char[] chars = s.toCharArray();
        int len = chars.length;
        for(int i=0;i<len;i++){
           char temp = chars[i];
           chars[i] = chars[len-i-1];
           chars[len-i-1] = temp;
           }
        String str = new String(chars);
        int val = Integer.parseInt(str);
        
        if (val > Math.pow(2,32)-1 && val < -Math.pow(2,32)) {
            return 0;
        }
        return val;
    }
}