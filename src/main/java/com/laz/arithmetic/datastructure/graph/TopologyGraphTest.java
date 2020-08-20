package com.laz.arithmetic.datastructure.graph;

import org.junit.Test;

public class TopologyGraphTest {
	@Test
	public void test() {
		StringBuilder sb = new StringBuilder();
		sb.append("1,3");
		sb.append("\n");
		sb.append("2,4");
		sb.append("\n");
		sb.append("2,6");
		sb.append("\n");
		sb.append("3,5");
		sb.append("\n");
		sb.append("4,6");
		sb.append("\n");
		sb.append("4,5");
		sb.append("\n");
		sb.append("5,7");
		sb.append("\n");
		sb.append("6,9");
		sb.append("\n");
		sb.append("7,9");
		TopologyGraph directedGraph = new TopologyGraph(sb.toString());
		try {
			directedGraph.topoSort();
		} catch (Exception e) {
			System.out.println("graph has circle");
			e.printStackTrace();
		}
	}
}
