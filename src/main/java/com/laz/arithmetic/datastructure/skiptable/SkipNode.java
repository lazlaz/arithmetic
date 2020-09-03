package com.laz.arithmetic.datastructure.skiptable;

public class SkipNode<E extends Comparable<? super E>> {
	 /*
     * 节点存储的值Val
     */
    public E val;
    /*
     * 节点指向第i层的节点next[i]
     */
    public SkipNode<E>[] next;

    @SuppressWarnings("unchecked")
    public SkipNode(int MAX_LEVEL, E val) {
        this.next = new SkipNode[MAX_LEVEL];
        this.val = val;
    }

}
