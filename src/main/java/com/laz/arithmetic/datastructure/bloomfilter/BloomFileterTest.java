package com.laz.arithmetic.datastructure.bloomfilter;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import org.junit.Test;

public class BloomFileterTest {
	@Test
	public void test() {
		BloomFileter fileter = new BloomFileter(1000000);
		for (int i=0;i<=10000000;i++) {
			fileter.addIfNotExist(i+"");
		}
		System.out.println(fileter.getUseRate());
		System.out.println(fileter.addIfNotExist("1111111"));
	}

}
