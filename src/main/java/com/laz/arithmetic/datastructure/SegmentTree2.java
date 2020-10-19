package com.laz.arithmetic.datastructure;

import java.util.Arrays;

//线段树版本2
public class SegmentTree2 {
	public static void main(String[] args) {
		int[] num = new int[] { 1, 2, 3, 4};
		SegmentTree2 fancySegmentTree = new SegmentTree2(num.length);
		for (int i = 0; i < num.length; i++) {
			fancySegmentTree.update(i, i, 1, num[i]);
		}
		System.out.println(fancySegmentTree.query(0, 3));
		fancySegmentTree.update(0, 3, 2, 0);
		fancySegmentTree.update(0, 3, 1, 2);
		System.out.println(fancySegmentTree.query(0, 3));
		System.out.println(fancySegmentTree.query(0, 2));
	}

	public static int getLeftSon(int x) {
		return x << 1;
	}

	public static int getRightSon(int x) {
		return (x << 1) + 1;
	}

	public final static int SEGMENT_TREE_FACTOR = 4; // 线段树大小为初始数组大小的4倍
	public final static int SEGMENT_TREE_ROOT = 1;

	private class Node {
		public long tree;
		public long mulLazy, addLazy;
		public final int left;
		public final int right;

		public Node(long tree, long mulLazy, long addlazy, int left, int right) {
			this.tree = tree;
			this.mulLazy = mulLazy; // x标记
			this.addLazy = addlazy; // +标记
			this.left = left;
			this.right = right;
		}

		public Node(long tree, long mulLazy, long addLazy) {
			this(tree, mulLazy, addLazy, INVALID_POSITION_PLACEHOLDER, INVALID_POSITION_PLACEHOLDER);
		}

		@Override
		public String toString() {
			return String.format("\"tree = %d, mulLazy = %d, addLazy = %d, interval = [%d, %d]\"", tree, mulLazy,
					addLazy, left, right);
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			return new Node(this.tree, this.mulLazy, this.addLazy, this.left, this.right);
		}
	}

	private int length;
	private static final int INVALID_POSITION_PLACEHOLDER = -1;
	private static final int MOD = (int) 1e9 + 7;
	private Node[] SegmentTreeArray;

	public SegmentTree2(int n) {
		this(new int[n]);
	}

	public SegmentTree2(final int[] arr) {
		if (arr == null || arr.length == 0) {
			throw new IllegalArgumentException();
		}
		length = arr.length;
		SegmentTreeArray = new Node[SEGMENT_TREE_FACTOR * length];
		buildTree(arr, 0, length - 1, SEGMENT_TREE_ROOT); // 数组从1开始存储
	}

	private void buildTree(final int[] arr, int fromIndex, int toIndex, int treeIndex) {
		SegmentTreeArray[treeIndex] = new Node(0, 1, 0, fromIndex, toIndex);
		if (fromIndex == toIndex) {
			SegmentTreeArray[treeIndex].tree = arr[fromIndex];
			return;
		}
		int midIndex = (fromIndex + toIndex) >>> 1;
		buildTree(arr, fromIndex, midIndex, getLeftSon(treeIndex));
		buildTree(arr, midIndex + 1, toIndex, getRightSon(treeIndex));
		collectSubMsgFromChildren(treeIndex);
	}

	// 返回该段区域和
	private void collectSubMsgFromChildren(int treeIndex) {
		SegmentTreeArray[treeIndex].tree = SegmentTreeArray[getLeftSon(treeIndex)].tree
				+ SegmentTreeArray[getRightSon(treeIndex)].tree;
	}

	private void rangeCheck(int index) {
		if (index < 0 || index >= length) {
			throw new IndexOutOfBoundsException(index + "");
		}
	}

	public void update(int updateFromIndex, int updateToIndex, long mul, long add) {
		rangeCheck(updateFromIndex);
		rangeCheck(updateToIndex);
		Node diffNode = new Node(INVALID_POSITION_PLACEHOLDER, mul, add);
		update(updateFromIndex, updateToIndex, 1, diffNode);
	}

	private void getLazyStatus(int fromIndex, int toIndex, int destIdx, Node diff) {
		long mul = diff.mulLazy;
		long add = diff.addLazy;
		SegmentTreeArray[destIdx].mulLazy *= mul;
		SegmentTreeArray[destIdx].mulLazy %= MOD;
		SegmentTreeArray[destIdx].addLazy *= mul;
		SegmentTreeArray[destIdx].addLazy += add;
		SegmentTreeArray[destIdx].addLazy %= MOD;
		SegmentTreeArray[destIdx].tree *= mul;
		SegmentTreeArray[destIdx].tree += (SegmentTreeArray[destIdx].right - SegmentTreeArray[destIdx].left + 1) * add;
		SegmentTreeArray[destIdx].tree %= MOD;
	}

	// 判断改区间是否有lazy标记
	private boolean isNodeNotLazy(Node node) {
		return node.mulLazy != 1 || node.addLazy != 0;
	}

	private void update(int updateFromIndex, int updateToIndex, int treeIndex, Node diff) {
		int fromIndex = SegmentTreeArray[treeIndex].left;
		int toIndex = SegmentTreeArray[treeIndex].right;
		if (updateFromIndex <= fromIndex && toIndex <= updateToIndex) { // 覆盖了该区间
			getLazyStatus(fromIndex, toIndex, treeIndex, diff);
			return;
		}
		if (isNodeNotLazy(SegmentTreeArray[treeIndex])) {
			push_down(fromIndex, toIndex, treeIndex);
		}
		int midIndex = (fromIndex + toIndex) >>> 1;
		if (updateFromIndex <= midIndex) {
			update(updateFromIndex, updateToIndex, getLeftSon(treeIndex), diff);
		}
		if (updateToIndex > midIndex) {
			update(updateFromIndex, updateToIndex, getRightSon(treeIndex), diff);
		}
		collectSubMsgFromChildren(treeIndex);
	}

	private void push_down(int fromIndex, int toIndex, int treeIndex) {
		int midIndex = (fromIndex + toIndex) >>> 1;
		getLazyStatus(fromIndex, midIndex, getLeftSon(treeIndex), SegmentTreeArray[treeIndex]);
		getLazyStatus(midIndex + 1, toIndex, getRightSon(treeIndex), SegmentTreeArray[treeIndex]);
		resetLazy(SegmentTreeArray[treeIndex]);
	}

	private void resetLazy(Node node) {
		node.mulLazy = 1;
		node.addLazy = 0;
	}

	public long query(int queryFromIndex, int queryToIndex) {
		rangeCheck(queryFromIndex);
		rangeCheck(queryToIndex);
		if (queryFromIndex > queryToIndex) {
			throw new IllegalArgumentException();
		}
		return query(queryFromIndex, queryToIndex, SEGMENT_TREE_ROOT);
	}

	private long query(int queryFromIndex, int queryToIndex, int treeIndex) {
		int fromIndex = SegmentTreeArray[treeIndex].left;
		int toIndex = SegmentTreeArray[treeIndex].right;
		if (queryFromIndex <= fromIndex && toIndex <= queryToIndex) {
			return SegmentTreeArray[treeIndex].tree;
		}
		int midIndex = (fromIndex + toIndex) >>> 1;
		if (isNodeNotLazy(SegmentTreeArray[treeIndex])) {
			push_down(fromIndex, toIndex, treeIndex);
		}
		long left = queryFromIndex <= midIndex ? query(queryFromIndex, queryToIndex, getLeftSon(treeIndex)) : 0;
		long right = queryToIndex >= midIndex + 1 ? query(queryFromIndex, queryToIndex, getRightSon(treeIndex)) : 0;
		// if we want to maintain the range sum
		return (left + right) % MOD;
	}

	@Override
	public String toString() {
		return Arrays.toString(SegmentTreeArray);
	}
}
