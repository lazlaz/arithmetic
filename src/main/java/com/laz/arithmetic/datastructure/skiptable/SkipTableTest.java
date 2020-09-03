package com.laz.arithmetic.datastructure.skiptable;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class SkipTableTest {
	@Test
	public void test() {
        SkipTable  skipList = new SkipTable();
        skipList.insert(1);
        skipList.insert(2);
        System.out.println(skipList.contains(1));
	}
}
