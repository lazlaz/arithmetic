package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import javafx.util.Pair;

public class TestMain {
	@Test
	public void test() {
		List<Pair<String, Double>> pairArrayList = new ArrayList<>(3);
		pairArrayList.add(new Pair<>("version", 12.10));
		pairArrayList.add(new Pair<>("version", 12.19));
		pairArrayList.add(new Pair<>("version", 6.28));
		Map<String, Double> map = pairArrayList.stream().collect(
		// 生成的 map 集合中只有一个键值对：{version=6.28}
		Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v1));
		System.out.println(map);
	}
}
