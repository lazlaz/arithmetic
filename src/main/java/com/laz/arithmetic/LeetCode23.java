package com.laz.arithmetic;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LeetCode23 {
    //432. 全 O(1) 的数据结构
    @Test
    public void test1() {
        //case1
        AllOne obj = new AllOne();
        obj.inc("hello");
        obj.inc("goodbye");
        obj.inc("hello");
        obj.inc("hello");
        Assert.assertEquals("hello",obj.getMaxKey());
        obj.inc("leet");
        obj.inc("code");
        obj.inc("leet");
        obj.dec("hello");
        obj.inc("leet");
        obj.inc("code");
        obj.inc("code");
        Assert.assertEquals("leet",obj.getMaxKey());

        //case2
        AllOne obj2 = new AllOne();
        Assert.assertEquals("",obj2.getMaxKey());
        Assert.assertEquals("",obj2.getMinKey());

        //case3
        AllOne obj3 = new AllOne();
        obj3.inc("hello");
        obj3.inc("world");
        obj3.inc("hello");
        obj3.dec("world");
        obj3.inc("hello");
        obj3.inc("leet");
        Assert.assertEquals("hello",obj3.getMaxKey());
        obj3.dec("hello");
        obj3.dec("hello");
        obj3.dec("hello");
        Assert.assertEquals("leet",obj3.getMaxKey());
    }
    class AllOne {
        class Value {
            int v;
            String key;
            public Value(int v, String key) {
                this.v = v;
                this.key = key;
            }

            public String getKey() {
                return key;
            }

            public int getV() {
                return v;
            }

            public void setV(int v) {
                this.v = v;
            }
        }
        private Map<String,Value> keyMap;
        private TreeMap<Value,String> valueMap;
        public AllOne() {
            this.keyMap = new HashMap<String,Value>();
            this.valueMap = new TreeMap<Value,String>(new Comparator<Value>() {
                @Override
                public int compare(Value o1, Value o2) {
                    if (o1.getV() == o2.getV()) {
                        return o1.getKey().compareTo(o2.getKey());
                    }
                    return o1.getV()-o2.getV();
                }
            });
        }

        public void inc(String key) {
            Value v = this.keyMap.get(key);
            if (v == null) {
                v = new Value(1, key);
                this.keyMap.put(key,v);
            } else {
                this.valueMap.remove(v);
                v.setV(v.getV()+1);
            }
            this.valueMap.put(v,key);
        }

        public void dec(String key) {
            Value v = this.keyMap.get(key);
            if (v == null) {
                return;
            }
            int value = v.getV()-1;
            if (value == 0) {
                this.keyMap.remove(key);
                this.valueMap.remove(v);
            } else {
                this.valueMap.remove(v);
                v.setV(value);
                this.valueMap.put(v,key);
            }
        }

        public String getMaxKey() {
            if (this.valueMap.size()==0) {
                return "";
            }
            Value v= this.valueMap.lastKey();
            return this.valueMap.get(v);
        }

        public String getMinKey() {
            if (this.valueMap.size()==0) {
                return "";
            }
            Value v= this.valueMap.firstKey();
            return this.valueMap.get(v);
        }
    }
}
