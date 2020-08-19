package com.laz.arithmetic.datastructure.heap;

/**
 * 堆排序（大根堆） https://my.oschina.net/LucasZhu/blog/1920147
 */
public class HeapSort {
	int parent(int i) {
		return (i - 1) / 2;
	}

	int left(int i) {
		return 2 * i + 1;
	}

	int right(int i) {
		return 2 * i + 2;
	}

	void maxHeapfy(int[] a, int i, int heapSize) { // 数组a，第i个结点，heapSize是数组种实际要排序的元素的长度
		int left = left(i); // 有的时候能够递归到叶子结点，叶子结点无后继，下面两个if都注意到了这一点
		int right = right(i);
		int largest = i;
		if (left < heapSize && a[left] > a[largest]) { //
			largest = left;
		}
		if (right < heapSize && a[right] > a[largest]) {
			largest = right;
		}
		if (largest != i) { // 把最大值给父结点
			a[largest] = a[largest] ^ a[i];
			a[i] = a[largest] ^ a[i];
			a[largest] = a[largest] ^ a[i];
			maxHeapfy(a, largest, heapSize); // 发生交换之后还要保证大根堆性质
		}
	}

	void buildMaxHeap(int[] a, int heapSize) {
		for (int i = (heapSize - 2) / 2; i >= 0; i--) {
			maxHeapfy(a, i, heapSize);
		}
	}

	void heapSort(int[] a) {
		int len = a.length;
		buildMaxHeap(a, len); // 初始建堆
		a[len - 1] = a[0] ^ a[len - 1]; // 交换
		a[0] = a[0] ^ a[a.length - 1];
		a[len - 1] = a[0] ^ a[len - 1];
		for (int i = 1; i < len - 1; i++) { // 初始建堆之后还要排a.length-2次
			maxHeapfy(a, 0, len - i);
			a[len - 1 - i] = a[0] ^ a[len - 1 - i]; // 交换
			a[0] = a[0] ^ a[len - 1 - i];
			a[len - 1 - i] = a[0] ^ a[len - 1 - i];
		}
	}
}
