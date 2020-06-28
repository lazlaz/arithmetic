package com.laz.arithmetic;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) throws UnsupportedEncodingException {
		int a=0,b=0,c=0;
		System.out.printf("%d %d %n",a,b,c);
	
		String s = "代";
		byte[] bx = s.getBytes(Charset.forName("utf-8"));
		for (byte d : bx) {
			System.out.println(Integer.toBinaryString(d));
		}
		System.out.println(new String(bx,"utf-8"));
	}
}
