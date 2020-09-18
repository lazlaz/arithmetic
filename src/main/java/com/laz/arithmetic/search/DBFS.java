package com.laz.arithmetic.search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;
import org.junit.Assert;

/**
 * 双向广度搜索（八数码问题） https://blog.csdn.net/zhangyiacm/article/details/20801505
 */
public class DBFS {
	@Test
	public void test() {
		String start = "12345678.";
		String end = "123.46758";
		char a[][] = new char[3][3];
		char b[][] = new char[3][3];
		int c = 0;
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				a[i][j] = start.charAt(c);
				b[i][j] = end.charAt(c);
				if (a[i][j] == '.') {
					x1 = i;
					y1 = j;
				}
				if (b[i][j] == '.') {
					x2 = i;
					y2 = j;
				}
				c++;
			}
		}
		Node node1 = new Node(a, 0, x1, y1);
		Node node2 = new Node(b, 0, x2, y2);
		Queue<Node> q1 = new LinkedList<Node>();
		Queue<Node> q2 = new LinkedList<Node>();
		hm1 = new HashMap<String, Integer>();
		hm2 = new HashMap<String, Integer>();
		hm1.put(start, 0);
		hm2.put(end, 0);
		q1.add(node1);
		q2.add(node2);
		Assert.assertEquals(3, bfs(q1, q2));
	}

	static class Node {
		char tu[][] = new char[3][3];
		int sum = 0;
		int x = 0, y = 0;

		public Node(char[][] tu, int sum, int x, int y) {
			super();
			this.tu = tu;
			this.sum = sum;
			this.x = x;
			this.y = y;
		}

		public char[][] getTuCopy() {
			char a[][] = new char[3][3];
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					a[i][j] = tu[i][j];
			return a;
		}

		public String getTuString() {
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 3; j++)
					sb.append(tu[i][j]);
			return sb.toString();
		}

		public void setTu(char[][] tu) {
			this.tu = tu;
		}

		public int getSum() {
			return sum;
		}

		public void setSum(int sum) {
			this.sum = sum;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}
	}

	private HashMap<String, Integer> hm1 = null, hm2 = null;

	private int bfs(Queue<Node> q1, Queue<Node> q2) {
		while (!q1.isEmpty() || !q2.isEmpty()) {
			if (!q1.isEmpty()) {
				Node node1 = q1.poll();
				// System.out.println(node1.getTuString()+"----1");
				if (hm2.containsKey(node1.getTuString())) {
					return node1.getSum() + hm2.get(node1.getTuString());
				}
				int x = node1.getX();
				int y = node1.getY();
				if (x > 0) {
					char a[][] = node1.getTuCopy();
					a[x][y] = a[x - 1][y];
					a[x - 1][y] = '.';
					Node n = new Node(a, node1.getSum() + 1, x - 1, y);
					String s = n.getTuString();
					if (hm2.containsKey(s)) {
						return n.getSum() + hm2.get(s);
					}
					if (!hm1.containsKey(s)) {
						hm1.put(s, n.getSum());
						q1.add(n);
					}
				}
				if (x < 2) {
					char a[][] = node1.getTuCopy();
					a[x][y] = a[x + 1][y];
					a[x + 1][y] = '.';
					Node n = new Node(a, node1.getSum() + 1, x + 1, y);
					String s = n.getTuString();
					if (hm2.containsKey(s)) {
						return n.getSum() + hm2.get(s);
					}
					if (!hm1.containsKey(s)) {
						hm1.put(s, n.getSum());
						q1.add(n);
					}
				}
				if (y > 0) {
					char a[][] = node1.getTuCopy();
					a[x][y] = a[x][y - 1];
					a[x][y - 1] = '.';
					Node n = new Node(a, node1.getSum() + 1, x, y - 1);
					String s = n.getTuString();
					if (hm2.containsKey(s)) {
						return n.getSum() + hm2.get(s);
					}
					if (!hm1.containsKey(s)) {
						hm1.put(s, n.getSum());
						q1.add(n);
					}
				}
				if (y < 2) {
					char a[][] = node1.getTuCopy();
					a[x][y] = a[x][y + 1];
					a[x][y + 1] = '.';
					Node n = new Node(a, node1.getSum() + 1, x, y + 1);
					String s = n.getTuString();
					if (hm2.containsKey(s)) {
						return n.getSum() + hm2.get(s);
					}
					if (!hm1.containsKey(s)) {
						hm1.put(s, n.getSum());
						q1.add(n);
					}
				}
			}

			if (!q2.isEmpty()) {

				Node node2 = q2.poll();
				// System.out.println(node2.getTuString()+"----2");
				if (hm1.containsKey(node2.getTuString())) {
					return node2.getSum() + hm1.get(node2.getTuString());
				}
				int x = node2.getX();
				int y = node2.getY();
				if (x > 0) {
					char a[][] = node2.getTuCopy();
					a[x][y] = a[x - 1][y];
					a[x - 1][y] = '.';
					Node n = new Node(a, node2.getSum() + 1, x - 1, y);
					String s = n.getTuString();
					if (hm1.containsKey(s)) {
						return n.getSum() + hm1.get(s);
					}
					if (!hm2.containsKey(s)) {
						hm2.put(s, n.getSum());
						q2.add(n);
					}
				}
				if (x < 2) {
					char a[][] = node2.getTuCopy();
					a[x][y] = a[x + 1][y];
					a[x + 1][y] = '.';
					Node n = new Node(a, node2.getSum() + 1, x + 1, y);
					String s = n.getTuString();
					if (hm1.containsKey(s)) {
						return n.getSum() + hm1.get(s);
					}
					if (!hm2.containsKey(s)) {
						hm2.put(s, n.getSum());
						q2.add(n);
					}
				}
				if (y > 0) {
					char a[][] = node2.getTuCopy();
					a[x][y] = a[x][y - 1];
					a[x][y - 1] = '.';
					Node n = new Node(a, node2.getSum() + 1, x, y - 1);
					String s = n.getTuString();
					if (hm1.containsKey(s)) {
						return n.getSum() + hm1.get(s);
					}
					if (!hm2.containsKey(s)) {
						hm2.put(s, n.getSum());
						q2.add(n);
					}
				}
				if (y < 2) {
					char a[][] = node2.getTuCopy();
					a[x][y] = a[x][y + 1];
					a[x][y + 1] = '.';
					Node n = new Node(a, node2.getSum() + 1, x, y + 1);
					String s = n.getTuString();
					if (hm1.containsKey(s)) {
						return n.getSum() + hm1.get(s);
					}
					if (!hm2.containsKey(s)) {
						hm2.put(s, n.getSum());
						q2.add(n);
					}
				}
			}
		}
		return -1;
	}

}
