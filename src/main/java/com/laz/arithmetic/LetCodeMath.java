package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LetCodeMath {
	// Fizz Buzz
	@Test
	public void test1() {
		List<String> lists = fizzBuzz(7);
		for (String str : lists) {
			System.out.print(str + " ");
		}
	}

	public List<String> fizzBuzz(int n) {
		List<String> list = new ArrayList<String>();
		for (int i = 1; i <= n; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
				list.add("FizzBuzz");
			} else if (i % 3 == 0) {
				list.add("Fizz");
			} else if (i % 5 == 0) {
				list.add("Buzz");
			} else {
				list.add("" + i);
			}
		}
		return list;
	}

	// 计数质数
	@Test
	public void test2() {
		System.out.println(countPrimes(1500000));
	}

	// 厄拉多塞筛法
	public int countPrimes(int n) {
		int count = 0;
		boolean[] signs = new boolean[n];
		for (int i = 2; i < n; i++) {
			//如果 xx 是质数，那么大于 xx 的 xx 的倍数 2x,3x,\ldots2x,3x,… 一定不是质数
			if (!signs[i]) {
				count++;
				for (int j = i + i; j < n; j += i) {
					// 排除不是质数的数
					signs[j] = true;
				}
			}
		}
		return count;

	}

	// 3的幂
	@Test
	public void test3() {
		System.out.println(isPowerOfThree(45));
	}

	public boolean isPowerOfThree(int n) {
		int count = n;
		if (n == 1) {
			return true;
		}
		if (n < 3) {
			return false;
		}
		while (count != 1) {
			if (count % 3 == 0) {
				count = count / 3;
			} else {
				return false;
			}
		}
		return true;
	}

	// 罗马数字转整数
	@Test
	public void test4() {
		System.out.println(romanToInt("III"));
	}

	public int romanToInt(String s) {
		
		if (s != null && s.length() > 0) {
			char[] chars = s.toCharArray();
			
			int count = 0;

			for (int i = 0; i < chars.length; i++) {
				char letter = chars[i];
				if (i<chars.length-1) {
					char letter2 = chars[i+1];
					String val = new String().valueOf(new char[]{letter,letter2});
					if ("IV".equals(val)) {
						count+=4;
						i++;
						continue;
					}
					if ("IX".equals(val)) {
						count+=9;
						i++;
						continue;
					}
					if ("XL".equals(val)) {
						count+=40;
						i++;
						continue;
					}
					if ("XC".equals(val)) {
						count+=90;
						i++;
						continue;
					}
					if ("CD".equals(val)) {
						count+=400;
						i++;
						continue;
					}
					if ("CM".equals(val)) {
						count+=900;
						i++;
						continue;
					}
				}
				if ('I' == letter) {
					count+=1;
				}
				if ('V' == letter) {
					count+=5;
				}
				if ('X' == letter) {
					count+=10;
				}
				if ('L' == letter) {
					count+=50;
				}
				if ('C' == letter) {
					count+=100;
				}
				if ('D' == letter) {
					count+=500;
				}
				if ('M' == letter) {
					count+=1000;
				}
				
			}
			return count;
		}
		return 0;
	}
}
