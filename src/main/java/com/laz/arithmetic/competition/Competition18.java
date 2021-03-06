package com.laz.arithmetic.competition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-224/
public class Competition18 {
	@Test
	// 1725. 可以形成最大正方形的矩形数目
	public void test1() {
		Assert.assertEquals(3, countGoodRectangles(new int[][] { { 5, 8 }, { 3, 9 }, { 5, 12 }, { 16, 5 } }));
	}

	public int countGoodRectangles(int[][] rectangles) {
		int[] res = new int[rectangles.length];
		for (int i = 0; i < rectangles.length; i++) {
			res[i] = Math.min(rectangles[i][0], rectangles[i][1]);
		}
		int max = 0;
		for (int i = 0; i < res.length; i++) {
			max = Math.max(max, res[i]);
		}
		int count = 0;
		for (int i = 0; i < res.length; i++) {
			if (res[i] == max) {
				count++;
			}
		}
		return count;
	}

	// 1726. 同积元组
	@Test
	public void test2() {
//		Assert.assertEquals(8, new Solution2().tupleSameProduct(new int[] {
//				2,3,4,6
//		}));
//		
//		Assert.assertEquals(16, new Solution2().tupleSameProduct(new int[] {
//				1,2,4,5,10
//		}));

		Assert.assertEquals(40, new Solution2().tupleSameProduct(new int[] { 2, 3, 4, 6, 8, 12 }));

		Assert.assertEquals(12424, new Solution2().tupleSameProduct(new int[] { 336, 1771, 851, 1091, 3860, 89, 361,
				2382, 2000, 194, 60, 2093, 3844, 59, 967, 240, 570, 2642, 281, 2662, 3795, 2195, 2795, 1473, 2403, 1833,
				3398, 3554, 141, 781, 1359, 2498, 3918, 1783, 3514, 3193, 3732, 594, 952, 2883, 2162, 1738, 3174, 1412,
				1016, 335, 2277, 3764, 161, 1136, 1553, 1628, 528, 145, 3562, 786, 3549, 2622, 870, 380, 3827, 2580,
				879, 1308, 2587, 2309, 1607, 3858, 3916, 2270, 2064, 1045, 1487, 1848, 638, 3721, 777, 2594, 1770, 2746,
				3191, 2755, 2649, 2969, 1497, 1753, 474, 2179, 1933, 984, 1587, 2407, 2931, 3451, 3259, 1083, 1402,
				1556, 288, 2043, 1943, 1429, 2397, 322, 3896, 3234, 2132, 146, 2691, 2687, 2955, 1675, 2588, 1912, 310,
				2624, 785, 2501, 912, 616, 3876, 2011, 1522, 1294, 1346, 3567, 1847, 2196, 1357, 482, 2128, 855, 3108,
				2734, 3085, 3692, 3443, 3035, 3389, 3376, 1057, 1011, 353, 418, 3775, 1196, 1525, 2189, 3340, 3907,
				3609, 614, 1460, 468, 3044, 1526, 712, 916, 1085, 3252, 2329, 1199, 377, 2327, 1961, 3129, 3586, 675,
				2207, 3183, 3449, 1966, 2557, 555, 3421, 2967, 1882, 2298, 3622, 3683, 1463, 3283, 585, 400, 1757, 2429,
				3073, 1619, 3310, 478, 1023, 709, 2024, 2493, 3264, 3439, 2838, 1604, 3263, 1022, 1439, 3619, 2489, 84,
				452, 1984, 2436, 1622, 2170, 3367, 1950, 621, 3929, 3256, 3455, 3664, 2293, 574, 3636, 850, 1445, 112,
				1019, 2896, 3513, 2231, 1328, 1571, 3349, 1374, 2986, 2970, 1782, 732, 3980, 3529, 1502, 2881, 2950,
				435, 2848, 2048, 439, 3046, 3565, 1006, 1241, 447, 3232, 94, 2695, 2366, 1236, 3185, 173, 1281, 3371,
				157, 3227, 3144, 1488, 2888, 1361, 3248, 3125, 462, 1546, 3885, 1233, 987, 3082, 3230, 169, 230, 2866,
				2661, 1515, 3671, 1072, 394, 3221, 541, 3213, 1573, 1060, 2906, 3940, 317, 803, 2378, 3104, 1043, 868,
				1115, 2152, 665, 1741, 2497, 3685, 339, 1363, 2626, 2181, 2937, 600, 1536, 2514, 3276, 1867, 1101, 1325,
				1951, 3362, 1561, 688, 1828, 942, 2120, 1276, 214, 1727, 642, 1324, 2425, 1705, 1096, 76, 774, 694,
				2680, 1190, 1177, 2441, 746, 1520, 1709, 1348, 2591, 108, 2818, 2699, 1413, 2459, 1442, 540, 1065, 826,
				111, 3099, 1838, 3838, 2670, 2640, 1279, 2105, 2021, 3488, 1852, 1514, 323, 1397, 3016, 2644, 1028,
				2976, 3668, 2445, 2664, 828, 3425, 1484, 3761, 640, 1653, 962, 2432, 1296, 2971, 433, 3141, 2890, 672,
				3124, 1861, 1589, 1419, 706, 3131, 2136, 3033, 90, 3978, 308, 1696, 2160, 338, 954, 2529, 1844, 738 }));
	}

	// https://leetcode-cn.com/problems/tuple-with-same-product/solution/javaliang-chong-jie-fa-pai-xu-bian-li-vs-w7k5/
	class Solution2 {
		public int tupleSameProduct(int[] nums) {
			int ans = 0;
			int n = nums.length;
			Map<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < n - 1; i++) {
				for (int j = i + 1; j < n; j++) {
					int key = nums[i] * nums[j];
					// 应为a!=b!=c!=d,所以只要乘积重复就会组成元组
					Integer count = map.getOrDefault(key, 0);
					if (count > 0) {
						// 说明：之前已经有count组不同的(x,y)，满足nums[x]*nums[y]=key
						// 这count组分别外加当前的(i,j)即可构成count个4元组(x,y,i,j)
						ans += count;
					}
					count++;
					map.put(key, count);
				}
			}
			return ans << 3;

		}

	}

	// 1727. 重新排列后的最大子矩阵
	@Test
	public void test3() {
		Assert.assertEquals(4, largestSubmatrix(new int[][] { { 0, 0, 1 }, { 1, 1, 1 }, { 1, 0, 1 } }));
	}
	//https://leetcode-cn.com/problems/largest-submatrix-with-rearrangements/solution/java-yu-chu-li-shu-zu-bian-li-mei-xing-p-qpqu/
	public int largestSubmatrix(int[][] matrix) {
		int n=matrix.length;
        int m=matrix[0].length;
        int res=0;
        for(int i=1;i<n;i++){
            for(int j=0;j<m;j++){
                if(matrix[i][j]==1){
                    //记录向上连续1的个数
                    matrix[i][j]+=matrix[i-1][j];
                }
            }
        }
        for(int i=0;i<n;i++){
            Arrays.sort(matrix[i]);
            for(int j=m-1;j>=0;j--){
                //更新矩形的最大高度
                int height=matrix[i][j];
                //更新最大面积
                res=Math.max(res,height*(m-j));
            }
        }
        return res;
	}
}
