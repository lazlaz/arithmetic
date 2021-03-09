package com.laz.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode12 {
	// 超级次方
	@Test
	public void test1() {
		Assert.assertEquals(1024, superPow(2, new int[] { 1, 0 }));
	}

	int base = 1337;

	int mypow(int a, int k) {
		// (a * b) % k = (a % k)(b % k) % k
		if (k == 0)
			return 1;
		a %= base;
		int res = 1;
		for (int i = 0; i < k; i++) {
			// 这里有乘法，是潜在的溢出点
			res *= a;
			// 对乘法结果求模
			res %= base;
		}
		return res;
	}

	public int superPow(int a, int[] b) {
		if (b.length == 0)
			return 1;
		int last = b[b.length - 1];
		int[] newB = Arrays.copyOf(b, b.length - 1);
		int part1 = mypow(a, last);
		int part2 = mypow(superPow(a, newB), 10);
		// 每次乘法都要求模
		return (part1 * part2) % base;
	}

	// 表示数值的字符串
	@Test
	public void test2() {
		Assert.assertEquals(false, isNumber("1a3.14"));
		Assert.assertEquals(true, isNumber("-1E-16"));
		Assert.assertEquals(false, isNumber("E3"));
	}

	// 有限状态机
	public boolean isNumber(String s) {
		Map<State, Map<CharType, State>> transfer = new HashMap<State, Map<CharType, State>>();
		Map<CharType, State> initialMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_SPACE, State.STATE_INITIAL);
				put(CharType.CHAR_NUMBER, State.STATE_INTEGER);
				put(CharType.CHAR_POINT, State.STATE_POINT_WITHOUT_INT);
				put(CharType.CHAR_SIGN, State.STATE_INT_SIGN);
			}
		};
		transfer.put(State.STATE_INITIAL, initialMap);
		Map<CharType, State> intSignMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_NUMBER, State.STATE_INTEGER);
				put(CharType.CHAR_POINT, State.STATE_POINT_WITHOUT_INT);
			}
		};
		transfer.put(State.STATE_INT_SIGN, intSignMap);
		Map<CharType, State> integerMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_NUMBER, State.STATE_INTEGER);
				put(CharType.CHAR_EXP, State.STATE_EXP);
				put(CharType.CHAR_POINT, State.STATE_POINT);
				put(CharType.CHAR_SPACE, State.STATE_END);
			}
		};
		transfer.put(State.STATE_INTEGER, integerMap);
		Map<CharType, State> pointMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
				put(CharType.CHAR_EXP, State.STATE_EXP);
				put(CharType.CHAR_SPACE, State.STATE_END);
			}
		};
		transfer.put(State.STATE_POINT, pointMap);
		Map<CharType, State> pointWithoutIntMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
			}
		};
		transfer.put(State.STATE_POINT_WITHOUT_INT, pointWithoutIntMap);
		Map<CharType, State> fractionMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_NUMBER, State.STATE_FRACTION);
				put(CharType.CHAR_EXP, State.STATE_EXP);
				put(CharType.CHAR_SPACE, State.STATE_END);
			}
		};
		transfer.put(State.STATE_FRACTION, fractionMap);
		Map<CharType, State> expMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_NUMBER, State.STATE_EXP_NUMBER);
				put(CharType.CHAR_SIGN, State.STATE_EXP_SIGN);
			}
		};
		transfer.put(State.STATE_EXP, expMap);
		Map<CharType, State> expSignMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_NUMBER, State.STATE_EXP_NUMBER);
			}
		};
		transfer.put(State.STATE_EXP_SIGN, expSignMap);
		Map<CharType, State> expNumberMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_NUMBER, State.STATE_EXP_NUMBER);
				put(CharType.CHAR_SPACE, State.STATE_END);
			}
		};
		transfer.put(State.STATE_EXP_NUMBER, expNumberMap);
		Map<CharType, State> endMap = new HashMap<CharType, State>() {
			{
				put(CharType.CHAR_SPACE, State.STATE_END);
			}
		};
		transfer.put(State.STATE_END, endMap);

		int length = s.length();
		State state = State.STATE_INITIAL;

		for (int i = 0; i < length; i++) {
			CharType type = toCharType(s.charAt(i));
			if (!transfer.get(state).containsKey(type)) {
				return false;
			} else {
				state = transfer.get(state).get(type);
			}
		}
		return state == State.STATE_INTEGER || state == State.STATE_POINT || state == State.STATE_FRACTION
				|| state == State.STATE_EXP_NUMBER || state == State.STATE_END;
	}

	public CharType toCharType(char ch) {
		if (ch >= '0' && ch <= '9') {
			return CharType.CHAR_NUMBER;
		} else if (ch == 'e' || ch == 'E') {
			return CharType.CHAR_EXP;
		} else if (ch == '.') {
			return CharType.CHAR_POINT;
		} else if (ch == '+' || ch == '-') {
			return CharType.CHAR_SIGN;
		} else if (ch == ' ') {
			return CharType.CHAR_SPACE;
		} else {
			return CharType.CHAR_ILLEGAL;
		}
	}

	enum State {
		STATE_INITIAL, STATE_INT_SIGN, STATE_INTEGER, STATE_POINT, STATE_POINT_WITHOUT_INT, STATE_FRACTION, STATE_EXP,
		STATE_EXP_SIGN, STATE_EXP_NUMBER, STATE_END,
	}

	enum CharType {
		CHAR_NUMBER, CHAR_EXP, CHAR_POINT, CHAR_SIGN, CHAR_SPACE, CHAR_ILLEGAL,
	}

	// 设计推特
	@Test
	public void test3() {
		{
			Twitter twitter = new Twitter();

			// 用户1发送了一条新推文 (用户id = 1, 推文id = 5).
			twitter.postTweet(1, 5);

			// 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
			List<Integer> list = twitter.getNewsFeed(1);
			System.out.println(Joiner.on(",").join(list));
			// 用户1关注了用户2.
			twitter.follow(1, 2);

			// 用户2发送了一个新推文 (推文id = 6).
			twitter.postTweet(2, 6);

			// 用户1的获取推文应当返回一个列表，其中包含两个推文，id分别为 -> [6, 5].
			// 推文id6应当在推文id5之前，因为它是在5之后发送的.
			list = twitter.getNewsFeed(1);
			System.out.println(Joiner.on(",").join(list));
			// 用户1取消关注了用户2.
			twitter.unfollow(1, 2);

			// 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
			// 因为用户1已经不再关注用户2.
			list = twitter.getNewsFeed(1);
			System.out.println(Joiner.on(",").join(list));
		}

		{
			System.out.println("-------------------");
			Twitter twitter = new Twitter();
			twitter.postTweet(1, 5);
			twitter.postTweet(1, 3);
			twitter.postTweet(1, 101);
			twitter.postTweet(1, 13);
			twitter.postTweet(1, 10);
			twitter.postTweet(1, 2);
			twitter.postTweet(1, 94);
			twitter.postTweet(1, 505);
			twitter.postTweet(1, 333);
			twitter.postTweet(1, 22);
			twitter.postTweet(1, 11);
			List<Integer> list = twitter.getNewsFeed(1);
			System.out.println(Joiner.on(",").join(list));
		}

	}

	// https://leetcode-cn.com/problems/design-twitter/solution/ha-xi-biao-lian-biao-you-xian-dui-lie-java-by-liwe/
	public static class Twitter {

		/**
		 * 用户 id 和推文（单链表）的对应关系
		 */
		private Map<Integer, Tweet> twitter;

		/**
		 * 用户 id 和他关注的用户列表的对应关系
		 */
		private Map<Integer, Set<Integer>> followings;

		/**
		 * 全局使用的时间戳字段，用户每发布一条推文之前 + 1
		 */
		private static int timestamp = 0;

		/**
		 * 合并 k 组推文使用的数据结构（可以在方法里创建使用），声明成全局变量非必需，视个人情况使用
		 */
		private static PriorityQueue<Tweet> maxHeap;

		/**
		 * Initialize your data structure here.
		 */
		public Twitter() {
			followings = new HashMap<>();
			twitter = new HashMap<>();
			maxHeap = new PriorityQueue<>((o1, o2) -> -o1.timestamp + o2.timestamp);
		}

		/**
		 * Compose a new tweet.
		 */
		public void postTweet(int userId, int tweetId) {
			timestamp++;
			if (twitter.containsKey(userId)) {
				Tweet oldHead = twitter.get(userId);
				Tweet newHead = new Tweet(tweetId, timestamp);
				newHead.next = oldHead;
				twitter.put(userId, newHead);
			} else {
				twitter.put(userId, new Tweet(tweetId, timestamp));
			}
		}

		/**
		 * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
		 * the news feed must be posted by users who the user followed or by the user
		 * herself. Tweets must be ordered from most recent to least recent.
		 */
		public List<Integer> getNewsFeed(int userId) {
			// 由于是全局使用的，使用之前需要清空
			maxHeap.clear();

			// 如果自己发了推文也要算上
			if (twitter.containsKey(userId)) {
				maxHeap.offer(twitter.get(userId));
			}

			Set<Integer> followingList = followings.get(userId);
			if (followingList != null && followingList.size() > 0) {
				for (Integer followingId : followingList) {
					Tweet tweet = twitter.get(followingId);
					if (tweet != null) {
						maxHeap.offer(tweet);
					}
				}
			}

			List<Integer> res = new ArrayList<>(10);
			int count = 0;
			while (!maxHeap.isEmpty() && count < 10) {
				Tweet head = maxHeap.poll();
				res.add(head.id);

				// 这里最好的操作应该是 replace，但是 Java 没有提供
				if (head.next != null) {
					maxHeap.offer(head.next);
				}
				count++;
			}
			return res;
		}

		/**
		 * Follower follows a followee. If the operation is invalid, it should be a
		 * no-op.
		 *
		 * @param followerId 发起关注者 id
		 * @param followeeId 被关注者 id
		 */
		public void follow(int followerId, int followeeId) {
			// 被关注人不能是自己
			if (followeeId == followerId) {
				return;
			}

			// 获取我自己的关注列表
			Set<Integer> followingList = followings.get(followerId);
			if (followingList == null) {
				Set<Integer> init = new HashSet<>();
				init.add(followeeId);
				followings.put(followerId, init);
			} else {
				if (followingList.contains(followeeId)) {
					return;
				}
				followingList.add(followeeId);
			}
		}

		/**
		 * Follower unfollows a followee. If the operation is invalid, it should be a
		 * no-op.
		 *
		 * @param followerId 发起取消关注的人的 id
		 * @param followeeId 被取消关注的人的 id
		 */
		public void unfollow(int followerId, int followeeId) {
			if (followeeId == followerId) {
				return;
			}

			// 获取我自己的关注列表
			Set<Integer> followingList = followings.get(followerId);

			if (followingList == null) {
				return;
			}
			// 这里删除之前无需做判断，因为查找是否存在以后，就可以删除，反正删除之前都要查找
			followingList.remove(followeeId);
		}

		/**
		 * 推文类，是一个单链表（结点视角）
		 */
		private class Tweet {
			/**
			 * 推文 id
			 */
			private int id;

			/**
			 * 发推文的时间戳
			 */
			private int timestamp;
			private Tweet next;

			public Tweet(int id, int timestamp) {
				this.id = id;
				this.timestamp = timestamp;
			}
		}

	}

	// 自己设计的
	class TwitterMy {
		// 推特信息列表
		private Map<Integer, List<Map<Integer, Long>>> tweets = new HashMap<Integer, List<Map<Integer, Long>>>();
		// 关注列表
		private Map<Integer, Set<Integer>> follows = new HashMap<Integer, Set<Integer>>();
		private AtomicLong times = new AtomicLong();

		/** Initialize your data structure here. */
		public TwitterMy() {

		}

		/** Compose a new tweet. */
		public void postTweet(int userId, int tweetId) {
			List<Map<Integer, Long>> tweetList = tweets.getOrDefault(userId, new LinkedList<Map<Integer, Long>>());
			Map<Integer, Long> info = new HashMap<Integer, Long>();
			Long time = times.addAndGet(1L);
			info.put(tweetId, time);
			tweetList.add(info);
			tweets.put(userId, tweetList);
		}

		/**
		 * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in
		 * the news feed must be posted by users who the user followed or by the user
		 * herself. Tweets must be ordered from most recent to least recent.
		 */
		public List<Integer> getNewsFeed(int userId) {
			Set<Integer> followList = follows.getOrDefault(userId, new HashSet<Integer>());
			Comparator comparator = new Comparator<Map<Integer, Long>>() {
				@Override
				public int compare(Map<Integer, Long> o1, Map<Integer, Long> o2) {
					Long t1 = (Long) o1.values().toArray()[0];
					Long t2 = (Long) o2.values().toArray()[0];
					if (t1 > t2) {
						return -1;
					} else if (t1 < t2) {
						return 1;
					}
					return 0;
				}
			};
			List<Map<Integer, Long>> tweetList = new ArrayList<Map<Integer, Long>>();
			tweetList.addAll(tweets.getOrDefault(userId, new LinkedList<Map<Integer, Long>>()));
			for (Integer followerId : followList) {
				if (followerId != userId) {
					tweetList.addAll(tweets.getOrDefault(followerId, new LinkedList<Map<Integer, Long>>()));
				}
			}
			Collections.sort(tweetList, comparator);
			List<Integer> list = new LinkedList<Integer>();
			for (int i = 0; i < tweetList.size(); i++) {
				Integer tweetId = (Integer) tweetList.get(i).keySet().toArray()[0];
				list.add(tweetId);
				if (list.size() == 10) {
					break;
				}
			}
			return list;
		}

		/**
		 * Follower follows a followee. If the operation is invalid, it should be a
		 * no-op.
		 */
		public void follow(int followerId, int followeeId) {
			Set set = follows.getOrDefault(followerId, new HashSet<Integer>());
			set.add(followeeId);
			follows.put(followerId, set);
		}

		/**
		 * Follower unfollows a followee. If the operation is invalid, it should be a
		 * no-op.
		 */
		public void unfollow(int followerId, int followeeId) {
			Set set = follows.getOrDefault(followerId, new HashSet<Integer>());
			if (set.contains(followeeId)) {
				set.remove((Integer) followeeId);
			}
		}
	}

	// 链表随机节点
	@Test
	public void test4() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		Solution4 solution = new Solution4(head);

		// getRandom()方法应随机返回1,2,3中的一个，保证每个元素被返回的概率相等。
		System.out.println(solution.getRandom());
	}

	class Solution4 {
		List<Integer> values = new ArrayList<Integer>();

		/**
		 * @param head The linked list's head. Note that the head is guaranteed to be
		 *             not null, so it contains at least one node.
		 */
		public Solution4(ListNode head) {
			while (head != null) {
				values.add(head.val);
				head = head.next;
			}
		}

		/** Returns a random node's value. */
		public int getRandom() {
			int index = new Random().nextInt(values.size());
			return values.get(index);
		}
	}

	// 随机数索引
	@Test
	public void test5() {
		int[] nums = new int[] { 1, 2, 3, 3, 3 };
		Solution5 solution = new Solution5(nums);

		// pick(3) 应该返回索引 2,3 或者 4。每个索引的返回概率应该相等。
		solution.pick(3);

		// pick(1) 应该返回 0。因为只有nums[0]等于1。
		solution.pick(1);
	}

	class Solution5 {

		int[] arr;
		Random rnd;
		int len;

		public Solution5(int[] nums) {
			arr = nums;
			rnd = new Random();
			len = arr.length;
		}

		public int pick(int target) {
			int cnt = 0, res = -1;
			for (int i = 0; i < len; i++) {
				if (arr[i] == target && rnd.nextInt(++cnt) == 0) {
					res = i;
				}
			}
			return res;
		}
	}

	// 分割等和子集
	@Test
	public void test6() {
		// Assert.assertEquals(true, canPartition(new int[] {1, 5, 11, 5}));
		Assert.assertEquals(false, canPartition(new int[] { 100 }));
	}

	// https://leetcode-cn.com/problems/partition-equal-subset-sum/solution/0-1-bei-bao-wen-ti-xiang-jie-zhen-dui-ben-ti-de-yo/
	public boolean canPartition(int[] nums) {
		int len = nums.length;
		if (len == 0) {
			return false;
		}
		int sum = 0;
		for (int num : nums) {
			sum += num;
		}
		// 特判：如果是奇数，就不符合要求
		if ((sum & 1) == 1) {
			return false;
		}
		int target = sum / 2;
		// 创建二维状态数组，行：物品索引，列：容量（包括 0）
		// 表示从数组的 [0, i] 这个子区间内挑选一些正整数，每个数只能用一次，使得这些数的和恰好等于 j
		boolean[][] dp = new boolean[len][target + 1];

		// 先填表格第 0 行，第 1 个数只能让容积为它自己的背包恰好装满
		if (nums[0] <= target) {
			dp[0][nums[0]] = true;
		}
		// 再填表格后面几行
		for (int i = 1; i < len; i++) {
			for (int j = 0; j <= target; j++) {
				// 直接从上一行先把结果抄下来，然后再修正
				dp[i][j] = dp[i - 1][j];

				if (nums[i] == j) {
					dp[i][j] = true;
					continue;
				}
				if (nums[i] < j) {
					dp[i][j] = dp[i - 1][j] || dp[i - 1][j - nums[i]];
				}
			}
		}
		return dp[len - 1][target];
	}

	// 前 K 个高频元素
	@Test
	public void test7() {
		Assert.assertArrayEquals(new int[] { 1, 2 }, topKFrequent(new int[] { 1, 1, 1, 2, 2 }, 2));
	}

	// https://leetcode-cn.com/problems/top-k-frequent-elements/solution/leetcode-di-347-hao-wen-ti-qian-k-ge-gao-pin-yuan-/
	public int[] topKFrequent(int[] nums, int k) {
		// 使用字典，统计每个元素出现的次数，元素为键，元素出现的次数为值
		HashMap<Integer, Integer> map = new HashMap();
		for (int num : nums) {
			if (map.containsKey(num)) {
				map.put(num, map.get(num) + 1);
			} else {
				map.put(num, 1);
			}
		}
		// 遍历map，用最小堆保存频率最大的k个元素
		PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				return map.get(a) - map.get(b);
			}
		});
		for (Integer key : map.keySet()) {
			if (pq.size() < k) {
				pq.add(key);
			} else if (map.get(key) > map.get(pq.peek())) {
				pq.remove();
				pq.add(key);
			}
		}
		// 取出最小堆中的元素
		int[] res = new int[pq.size()];
		int i = 0;
		while (!pq.isEmpty()) {
			i++;
			res[res.length - i] = pq.remove();
		}
		return res;
	}

	// 无重叠区间
	@Test
	public void test8() {
		Assert.assertEquals(1, eraseOverlapIntervals(new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 3 } }));
	}
	//https://leetcode-cn.com/problems/non-overlapping-intervals/solution/tan-xin-suan-fa-zhi-qu-jian-diao-du-wen-ti-by-labu/
	public int eraseOverlapIntervals(int[][] intervals) {
		int n = intervals.length;
		return n - intervalSchedule(intervals);
	}

	public int intervalSchedule(int[][] intvs) {
		if (intvs.length == 0)
			return 0;
		// 按 end 升序排序
		Arrays.sort(intvs, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[1] - b[1];
			}
		});
		// 至少有一个区间不相交
		int count = 1;
		// 排序后，第一个区间就是 x
		int x_end = intvs[0][1];
		for (int[] interval : intvs) {
			int start = interval[0];
			if (start >= x_end) {
				// 找到下一个选择的区间了
				count++;
				x_end = interval[1];
			}
		}
		return count;
	}

	// 找到字符串中所有字母异位词
	@Test
	public void test9() {
		List<Integer> ret = findAnagrams("abab", "ab");
		System.out.println(Joiner.on(",").join(ret));
	}

	public List<Integer> findAnagrams(String s, String p) {
		List<Integer> ret = new ArrayList<Integer>();
		if (s == null || p == null) {
			return ret;
		}
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < p.length(); i++) {
			map.put(p.charAt(i), map.getOrDefault(p.charAt(i), 0) + 1);
		}
		Map<Character, Integer> map2 = new HashMap<Character, Integer>();
		for (int i = 0; i < s.length(); i++) {
			map2.put(s.charAt(i), map2.getOrDefault(s.charAt(i), 0) + 1);
			if (i >= p.length() - 1) {
				if (isEctopicWord(map, map2)) {
					ret.add(i - p.length() + 1);
				}
				// 移除最前面的
				map2.put(s.charAt(i - p.length() + 1), map2.getOrDefault(s.charAt(i - p.length() + 1), 0) - 1);
			}
		}
		return ret;
	}

	private boolean isEctopicWord(Map<Character, Integer> map, Map<Character, Integer> map2) {
		Set<Character> set1 = map.keySet();
		for (Character c : set1) {
			if (map2.get(c) == null) {
				return false;
			}
			if (map.get(c).intValue() != map2.get(c).intValue()) {
				return false;
			}
		}
		return true;
	}

	// 找到所有数组中消失的数字
	@Test
	public void test10() {
		List<Integer> ret = findDisappearedNumbers(new int[] { 4, 3, 2, 7, 8, 2, 3, 1 });
		System.out.println(Joiner.on(",").join(ret));
	}

	// 原地数组修改，空间复杂度为O(1)
	public List<Integer> findDisappearedNumbers(int[] nums) {
		for (int i = 0; i < nums.length; i++) {
			int newIndex = Math.abs(nums[i]) - 1;
			nums[newIndex] = nums[newIndex] > 0 ? -nums[newIndex] : nums[newIndex];
		}
		List<Integer> result = new LinkedList<Integer>();
		for (int i = 1; i <= nums.length; i++) {
			if (nums[i - 1] > 0) {
				result.add(i);
			}
		}
		return result;
	}

	// 删除二叉搜索树中的节点
	@Test
	public void test11() {
		TreeNode node = Utils.createTree(new Integer[] { 5, 3, 6, 2, 4, null, 7 });
		new Solution11().deleteNode(node, 3);
		Utils.printTreeNode(node);
	}

	class Solution11 {
		/*
		 * One step right and then always left
		 */
		public int successor(TreeNode root) {
			root = root.right;
			while (root.left != null)
				root = root.left;
			return root.val;
		}

		/*
		 * One step left and then always right
		 */
		public int predecessor(TreeNode root) {
			root = root.left;
			while (root.right != null)
				root = root.right;
			return root.val;
		}

		public TreeNode deleteNode(TreeNode root, int key) {
			if (root == null)
				return null;

			// delete from the right subtree
			if (key > root.val)
				root.right = deleteNode(root.right, key);
			// delete from the left subtree
			else if (key < root.val)
				root.left = deleteNode(root.left, key);
			// delete the current node
			else {
				// the node is a leaf
				if (root.left == null && root.right == null)
					root = null;
				// the node is not a leaf and has a right child
				else if (root.right != null) {
					root.val = successor(root);
					root.right = deleteNode(root.right, root.val);
				}
				// the node is not a leaf, has no right child, and has a left child
				else {
					root.val = predecessor(root);
					root.left = deleteNode(root.left, root.val);
				}
			}
			return root;
		}
	}

	// 组合总和 III
	@Test
	public void test12() {
		List<List<Integer>> ret = combinationSum3(3, 9);
		for (List<Integer> list : ret) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	public List<List<Integer>> combinationSum3(int k, int n) {
		int[] candidates = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		List<List<Integer>> res = new ArrayList<>();
		int len = candidates.length;
		Arrays.sort(candidates);
		dfs(candidates, len, n, 0, k, new ArrayDeque<>(), res);
		return res;
	}

	private void dfs(int[] candidates, int len, int residue, int begin, int k, Deque<Integer> path,
			List<List<Integer>> res) {
		if (path.size() > k) {
			return;
		}
		if (residue == 0 && path.size() == k) {
			res.add(new ArrayList<>(path));
			return;
		}
		for (int i = begin; i < len; i++) {
			int aim = residue - candidates[i];
			// 在数组有序的前提下，剪枝
			if (aim < 0) {
				break;
			}
			// 当前值，等于了开始的值，剪枝
			if (i > begin && candidates[i] == candidates[i - 1]) {
				continue;
			}
			int index = i + 1;
			path.addLast(candidates[i]);
			dfs(candidates, len, aim, index, k, path, res);
			path.removeLast();
		}

	}

	// 二叉树的层平均值
	@Test
	public void test13() {
		TreeNode root = Utils.createTree(new Integer[] { 3, 9, 20, null, null, 15, 7 });
		List<Double> ret = averageOfLevels(root);
		System.out.println(Joiner.on(",").join(ret));
	}

	public List<Double> averageOfLevels(TreeNode root) {
		List<Double> ret = new ArrayList<Double>();
		Deque<TreeNode> queue = new LinkedList<TreeNode>();
		if (root != null) {
			queue.add(root);
		}
		while (!queue.isEmpty()) {
			int size = queue.size();
			double sum = 0;
			for (int i = 0; i < size; i++) {
				TreeNode node = queue.poll();
				sum += node.val;
				TreeNode left = node.left, right = node.right;
				if (left != null) {
					queue.offer(left);
				}
				if (right != null) {
					queue.offer(right);
				}
			}
			ret.add(sum / size);
		}
		return ret;
	}

	// 用最少数量的箭引爆气球
	@Test
	public void test14() {
		Assert.assertEquals(2, findMinArrowShots(new int[][] { { 10, 16 }, { 2, 8 }, { 1, 6 }, { 7, 12 } }));
		
		Assert.assertEquals(2, findMinArrowShots(new int[][] { {-2147483646,-2147483645},{2147483646,2147483647}}));
	}

	public int findMinArrowShots(int[][] points) {
		if (points.length == 0) {
			return 0;
		}
		Arrays.sort(points, new Comparator<int[]>() {

			@Override
			public int compare(int[] o1, int[] o2) {
				// return o1[1] - o2[1]; 不能用详见，当o1为2147483646 o2为-2147483646会超出范围变为负数
				if (o1[1]>o2[1]) {
					return 1;
				} else if (o1[1]<o2[1]) {
					return -1;
				} else {
					return 0;
				}
			}

		});
		int arrows = 1;
		int xStart, xEnd, firstEnd = points[0][1];
		for (int[] p : points) {
			xStart = p[0];
			xEnd = p[1];
			if (firstEnd < xStart) {
				arrows++;
				firstEnd = xEnd;
			}
		}
		return arrows;
	}

	// 二叉树的中序遍历
	@Test
	public void test15() {
		TreeNode root = Utils.createTree(new Integer[] { 1, null, 2, 3 });
		List<Integer> ret = inorderTraversal(root);
		System.out.println(Joiner.on(",").join(ret));
	}

	// 迭代遍历
	public List<Integer> inorderTraversal(TreeNode root) {
		List<Integer> ret = new ArrayList<Integer>();
		Deque<TreeNode> stk = new LinkedList<TreeNode>();
		while (root != null || !stk.isEmpty()) {
			while (root != null) {
				stk.push(root);
				root = root.left;
			}
			root = stk.pop();
			ret.add(root.val);
			root = root.right;
		}
		return ret;
	}

	// 下一个更大元素 I
	@Test
	public void test16() {
		int[] ret = nextGreaterElement(new int[] { 4, 1, 2 }, new int[] { 1, 3, 4, 2 });
		Assert.assertArrayEquals(new int[] { -1, 3, -1 }, ret);
	}

	public int[] nextGreaterElement(int[] nums1, int[] nums2) {
		int[] ret = new int[nums1.length];
		Arrays.fill(ret, -1);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums2.length; i++) {
			map.put(nums2[i], i);
		}
		int count = 0;
		for (int i = 0; i < nums1.length; i++) {
			int index = map.get(nums1[i]);
			for (int j = index + 1; j < nums2.length; j++) {
				if (nums2[j] > nums1[i]) {
					ret[count] = nums2[j];
					break;
				}
			}
			count++;
		}
		return ret;
	}

	// 下一个更大元素 II
	@Test
	public void test17() {
		Assert.assertArrayEquals(new int[] { 2, -1, 2 }, nextGreaterElements(new int[] { 1, 2, 1 }));
//		
		Assert.assertArrayEquals(new int[] { -1, 5, 5, 5, 5 }, nextGreaterElements(new int[] { 5, 4, 3, 2, 1 }));

		Assert.assertArrayEquals(new int[] { -1, -1, -1, -1, -1 }, nextGreaterElements(new int[] { 1, 1, 1, 1, 1 }));
	}
//https://leetcode-cn.com/problems/next-greater-element-ii/solution/xia-yi-ge-geng-da-yuan-su-ii-by-leetcode-bwam/
	public int[] nextGreaterElements(int[] nums) {
		int n = nums.length;
		int[] res = new int[n];
		Arrays.fill(res, -1);
		Deque<Integer> stack = new LinkedList();
		//数值扩大为2*n-1
		for (int i=0;i<2*n-1;i++) {
			while (!stack.isEmpty() && nums[i%n]>nums[stack.peek()]) {
				res[stack.pop()] = nums[i%n];
			}
			stack.push(i%n);
		}
		return res;
	}
	
	

	// 超时
	public int[] nextGreaterElements2(int[] nums) {
		int[] ret = new int[nums.length];
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		// 单调栈
		Deque<Integer> stack = new LinkedList<Integer>();
		for (int i = 0; i < nums.length; i++) {
			while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
				Integer value = stack.pop();
				map.put(value, nums[i]);
			}
			stack.push(i);
			// 找不到
			if (stack.size() == nums.length) {
				map.put(stack.getLast(), -1);
				stack.removeLast();
			}
			// 循环搜索
			if (i == nums.length - 1) {
				i = -1;
			}
			if (map.size() == nums.length) {
				break;
			}
		}
		for (int i = 0; i < nums.length; i++) {
			ret[i] = map.get(i);
		}
		return ret;
	}

	// 冗余连接 II
	@Test
	public void test18() {
		Assert.assertArrayEquals(new int[] { 4, 1 }, new Solution18()
				.findRedundantDirectedConnection(new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 1 }, { 1, 5 } }));
	}

	class Solution18 {
		// https://leetcode-cn.com/problems/redundant-connection-ii/solution/bing-cha-ji-java-by-liweiwei1419/
		public int[] findRedundantDirectedConnection(int[][] edges) {
			// 边的条数（在这个问题里等于结点个数）
			int len = edges.length;
			// 步骤 1：预处理入度数组（记录指向某个结点的边的条数）
			int[] inDegree = new int[len + 1];
			for (int[] edge : edges) {
				inDegree[edge[1]]++;
			}

			// 步骤 2：先尝试删除构成入度为 2 的边，看看是否形成环
			for (int i = len - 1; i >= 0; i--) {
				if (inDegree[edges[i][1]] == 2) {
					// 如果不构成环，这条边就是要去掉的那条边
					if (!judgeCircle(edges, len, i)) {

						return edges[i];
					}
				}
			}

			// 步骤 3：再尝试删除构成入度为 1 的边，看看是否形成环
			for (int i = len - 1; i >= 0; i--) {
				if (inDegree[edges[i][1]] == 1) {
					// 如果不构成环，这条边就是要去掉的那条边
					if (!judgeCircle(edges, len, i)) {
						return edges[i];
					}
				}
			}
			throw new IllegalArgumentException("输入不符合要求。");
		}

		/**
		 * 将 removeEdgeIndex 去掉以后，剩下的有向边是否构成环
		 *
		 * @param edges
		 * @param len             结点总数（从 1 开始，因此初始化的时候 + 1）
		 * @param removeEdgeIndex 删除的边的下标
		 * @return 构成环，返回 true
		 */
		private boolean judgeCircle(int[][] edges, int len, int removeEdgeIndex) {
			UnionFind unionFind = new UnionFind(len + 1);
			for (int i = 0; i < len; i++) {
				if (i == removeEdgeIndex) {
					continue;
				}
				if (!unionFind.union(edges[i][0], edges[i][1])) {
					// 合并失败，表示 edges[i][0] 和 edges[i][1] 在一个连通分量里，即构成了环
					return true;
				}
			}
			return false;
		}

		private class UnionFind {
			// 代表元法
			private int[] parent;

			public UnionFind(int n) {
				parent = new int[n];
				for (int i = 0; i < n; i++) {
					parent[i] = i;
				}
			}

			public int find(int x) {
				while (x != parent[x]) {
					// 路径压缩（隔代压缩）
					parent[x] = parent[parent[x]];
					x = parent[x];
				}
				return x;
			}

			/**
			 * @param x
			 * @param y
			 * @return 如果合并成功返回 true
			 */
			public boolean union(int x, int y) {
				int rootX = find(x);
				int rootY = find(y);

				if (rootX == rootY) {
					return false;
				}
				parent[rootX] = rootY;
				return true;
			}
		}
	}

	// 最长回文子序列
	@Test
	public void test19() {
		Assert.assertEquals(4, longestPalindromeSubseq("bbbab"));
	}

	public int longestPalindromeSubseq(String s) {
		int n = s.length();
		// dp[i][j] 表示 s 的第 i 个字符到第 j 个字符组成的子串中，最长的回文序列长度是多少
		int[][] dp = new int[n][n];
		for (int i = n - 1; i >= 0; i--) {
			dp[i][i] = 1;
			for (int j = i + 1; j < n; j++) {
				if (s.charAt(i) == s.charAt(j)) {
					dp[i][j] = dp[i + 1][j - 1] + 2;
				} else {
					dp[i][j] = Math.max(dp[i][j - 1], dp[i + 1][j]);
				}
			}
		}
		return dp[0][n - 1];
	}

	// 世界冰球锦标赛
	/**
	 * Bobek有M元钱，共有N场比赛，每场比赛的门票都有一个价格 [公式] 。 问在总票价不超过M元钱的情况下，Bobek共有多少种不同的观赛方案。
	 * 注1：若方案1中观看了某场比赛，方案2中未观看该场，则认为两种方案不同。 注2： [公式] 。
	 */
	//TODO 待测试验证
	@Test
	public void test20() {
		System.out.println(new Solution20().sj(100, new int[] { 10, 20, 30}));
	}

	class Solution20 {
		LinkedList<Long> a = new LinkedList<Long>();
		LinkedList<Long> b = new LinkedList<Long>();
		long m;
		int[] moy;
		public int sj(long m, int[] moy) {
			this.m = m;
			this.moy = moy;
			int nLen = moy.length;
			dfs(1, nLen / 2, 0, a);// 搜索第一部分
			dfs(nLen / 2 + 1, nLen, 0, b);// 搜索第二部分
			a.sort(new Comparator<Long>() {
				@Override
				public int compare(Long o1, Long o2) {
					return (int) (o1-o2);
				}
			});//将第一部分排序，使其有序
		    int ans=0;
		    int lenb=b.size();
		    for(int i=0;i<lenb;i++)//遍历第二部分
		        ans+=upperBound(a,m-b.get(i)-a.get(0));
		    //每次寻找花费比剩下的钱还要少的方案数，注意这里要使用upper_bound
		    //若使用lower_bound，则出现等于的情况时，方案数会有错误
			return ans;
		}
		public int upperBound(Long[] arr, long target) {
			int low = 0;
			int high = arr.length - 1;

			while (low <= high) { // 必须为 '<=' 否则无法匹配到指定的key；
				int mid = (low + high) / 2;
				if (arr[mid] == target) {
					return mid+1;
				} else if (arr[mid] > target) {
					high = mid - 1;
				} else {
					low = mid + 1;
				}
			}
			return arr.length;
		}
		private int upperBound(LinkedList<Long> a2, long l) {
			Long[] arr = a2.toArray(new Long[] {});
			return upperBound(arr,l);
		}

		private void dfs(int st, int en, long sum, LinkedList<Long> now) {
			if (sum > m)
				return;// 如果当前花费超过拥有的钱数，则返回
			if (st >= en)// 起点超过终点说明该部分已经全部搜索完毕
			{
				now.push(sum);// 则将可行的花费塞入vector中
				return;
			}
			dfs(st + 1, en, sum + moy[st], now);// 选择买这场比赛的门票
			dfs(st + 1, en, sum, now);// 选择不买这场比赛的门票
		}
	}

}
