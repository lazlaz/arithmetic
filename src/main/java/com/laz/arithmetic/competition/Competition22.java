package com.laz.arithmetic.competition;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-232/
public class Competition22 {
	// 1792. 最大平均通过率
	@Test
	public void test3() {
		Assert.assertEquals(0.78333, maxAverageRatio(new int[][] {
			{1,2},{3,5},{2,2}
		}, 2),0.00001);
	}
	//https://leetcode-cn.com/problems/maximum-average-pass-ratio/solution/zui-da-ping-jun-tong-guo-lu-by-zerotrac2-84br/
	public double maxAverageRatio(int[][] classes, int extraStudents) {
		//大根堆，谁+1人增长率最大，选谁
		PriorityQueue<double[]> stack = new PriorityQueue<double[]>(new Comparator<double[]>() {

			@Override
			public int compare(double[] o1, double[] o2) {
				double addRate1 = (o1[0]+1)/(o1[1]+1)-o1[0]/o1[1];
				double addRate2 = (o2[0]+1)/(o2[1]+1)-o2[0]/o2[1];
				return Double.compare(addRate2,addRate1);
			}
		});
		
		for (int[] cls :classes) {
			stack.offer(new double[]{cls[0],cls[1]});
		}
		for (int i=0;i<extraStudents;i++) {
			//选择增长率最大的出来
			double[] cls = stack.poll();
			stack.offer(new double[] {cls[0]+1,cls[1]+1});
		}
		//计算增长率
		double sum=0;
		while (!stack.isEmpty()) {
			double[] v = stack.poll();
			sum+=v[0]/v[1];
		}
		return sum/classes.length;
	}
}
