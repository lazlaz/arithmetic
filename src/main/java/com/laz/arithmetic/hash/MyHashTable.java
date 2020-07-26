package com.laz.arithmetic.hash;

import java.util.LinkedList;
import java.util.List;

public class MyHashTable<T> {
	private static final int DEFAULT_TABLE_SIZE = 100;

	public MyHashTable() {
		this(DEFAULT_TABLE_SIZE);
	}

	private List[] theLists;

	public MyHashTable(int size) {
		theLists = new LinkedList[nextPrime(size)];
		for (int i = 0; i < theLists.length; i++) {
			theLists[i] = new LinkedList<>();// 初始化链表数组
		}
	}

	/*
	 * 哈希表插入元素
	 */
	public void insert(T x) {
		List<T> whichList = theLists[myhash(x)];
		/*
		 * 如果当前哈希地址的链表不含有元素，则链表中添加该元素
		 */
		if (!whichList.contains(x)) {
			whichList.add(x);
			if (++currentSize > theLists.length)// 如果表长度不够，则扩容
				rehash();
		}
	}

	public void remove(T x) {
		List<T> whichList = theLists[myhash(x)];
		if (whichList.contains(x)) {
			whichList.remove(x);
			currentSize--;
		}
	}

	public boolean contains(T x) {
		List<T> whilchList = theLists[myhash(x)];
		return whilchList.contains(x);
	}

	public void makeEmpty() {
		for (int i = 0; i < theLists.length; i++)
			theLists[i].clear();
		currentSize = 0;
	}

	private int currentSize;

	/*
	 * 哈希表扩容，表长度为下一个素数
	 */
	private void rehash() {
		List<T>[] oldLists = theLists;
		theLists = new List[nextPrime(2 * theLists.length)];
		for (int j = 0; j < theLists.length; j++)
			theLists[j] = new LinkedList<>();

		currentSize = 0;
		/*
		 * 更新哈希表
		 */
		for (List<T> list : oldLists)
			for (T item : list)
				insert(item);
	}

	/*
	 * myhash()方法获得哈希表的地址
	 */
	private int myhash(T x) {
		int hashVal = x.hashCode();// hashCode()方法返回该对象的哈希码值
		hashVal %= theLists.length;// 对哈希表长度取余数
		if (hashVal < 0)
			hashVal += theLists.length;
		return hashVal;
	}

	// 下一个素数
	private static int nextPrime(int n) {
		if (n % 2 == 0)
			n++;

		for (; !isPrime(n); n += 2)
			;

		return n;
	}

	// 判断是否是素数
	private static boolean isPrime(int n) {
		if (n == 2 || n == 3)
			return true;

		if (n == 1 || n % 2 == 0)
			return false;

		for (int i = 3; i * i <= n; i += 2)
			if (n % i == 0)
				return false;

		return true;
	}
}
