import java.util.*;

public class Main2 {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = new ArrayList();
        if (nums.length == 0) {
            return lists;
        }
        List<Integer> neg = new ArrayList<>();
        List<Integer> pos = new ArrayList<>();
        Integer zeoNum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeoNum++;
                continue;
            }
            if (nums[i] > 0) {
                pos.add(nums[i]);
                continue;
            }
            if (nums[i] < 0) {
                neg.add(nums[i]);
            }
        }
        if (zeoNum >= 3) {
            lists.add(Arrays.asList(0, 0, 0));
        }
        if (pos.size() == 0 || neg.size() == 0) {
            return lists;
        }
        if (zeoNum > 0) {
            for (Integer posVal : pos) {
                for (Integer negVal : neg) {
                    if (posVal == -1 * (negVal)) {
                        lists.add(Arrays.asList(negVal, 0, posVal));
                    }
                }
            }

        }

        Integer[] posArr = new Integer[pos.size()];
        pos.toArray(posArr);
        Integer[] negArr = new Integer[neg.size()];
        neg.toArray(negArr);

        twoPosAndOneNeg(lists, posArr, negArr);
        twoNegAndOnePos(lists, posArr, negArr);
        return lists;
    }

    public void twoPosAndOneNeg(List<List<Integer>> lists, Integer[] pos, Integer[] neg) {
        for (int i = 0; i < pos.length; i++) {
            for (int j = i + 1; j < pos.length; j++) {
                for (int k = 0; k < neg.length; k++) {
                    if (pos[i] + pos[j] == -1 * neg[k]) {
                        lists.add(Arrays.asList(pos[i], pos[j], neg[k]));
                    }
                }
            }
        }
    }

    public void twoNegAndOnePos(List<List<Integer>> lists, Integer[] pos, Integer[] neg) {
        for (int i = 0; i < neg.length; i++) {
            for (int j = i + 1; j < neg.length; j++) {
                for (int k = 0; k < pos.length; k++) {
                    if (neg[i] + neg[j] == -1 * pos[k]) {
                        lists.add(Arrays.asList(neg[i], neg[j], pos[k]));
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        Main2 main2 = new Main2();

        /*ListNode l1 = main2.new ListNode(1);
        l1.next = main2.new ListNode(4);
        l1.next.next = main2.new ListNode(5);

        ListNode l2 = main2.new ListNode(1);
        l2.next = main2.new ListNode(3);
        l2.next.next = main2.new ListNode(4);

        ListNode l3 = main2.new ListNode(2);
        l3.next = main2.new ListNode(6);

        ListNode[] listNodes = new ListNode[]{l1,l2,l3};

        ListNode res = main2.mergeKLists(listNodes);*/

        //System.out.println(main2.maximalRectangle(new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}}));
        /*int[] num1 = new int[]{1, 2};
        int[] num2 = new int[]{1, 2};
        main2.findMedianSortedArrays(num1, num2);*/
        //main2.zigzig("ABCDE", 2);
        //main2.test1(-121);
        int[] a = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};
        main2.threeSum1(a);
    }

    /**
     * Maximal Rectangle
     * Input:
     * [
     * ["1","0","1","0","0"],
     * ["1","0","1","1","1"],
     * ["1","1","1","1","1"],
     * ["1","0","0","1","0"]
     * ]
     * Output: 6
     */
    public int maximalRectangle(char[][] matrix) {
        int width = matrix[0].length;
        int high = matrix.length;

        return 0;
    }


    /**
     * Simplify Path 简化路径
     */
    public String simplifyPath(String path) {
        if (path == null) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        String[] paths = path.split("/");
        String real = "";
        for (String s : paths) {
            if ("".equals(s) || ".".equals(s)) {
                continue;
            }
            if ("..".equals(s)) {
                if (stack.empty()) {
                    continue;
                } else {
                    stack.pop();
                }
            } else {
                stack.push(s);
            }
        }
        LinkedList<String> list = new LinkedList<>(stack);

        return "/" + String.join("/", list);
    }

    /**
     * 积水问题
     */
    public int trap(int[] height) {
        if (height == null || height.length <= 2) {
            return 0;
        }
        int maxR = height[height.length - 1];
        int maxL = height[0];
        int[] high = new int[height.length];
        int sum = 0;
        for (int i = height.length - 2; i > 0; i--) {
            if (height[i + 1] > maxR) {
                maxR = height[i + 1];
            }
            high[i] = maxR;
        }

        for (int j = 1; j < height.length - 1; j++) {
            if (height[j - 1] > maxL) {
                maxL = height[j - 1];
            }
            int cur = min(maxL, high[j]) - height[j];
            sum += (cur > 0 ? cur : 0);
        }

        return sum;
    }

    public int min(int a, int b) {
        return a < b ? a : b;
    }

    /**
     * 括号合法性
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        char[] chars = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '(') {
                stack.push(c);
            }
            if (c == ')') {
                if (!stack.empty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    return false;
                }
            }
            if (c == '[') {
                stack.push(c);
            }
            if (c == ']') {
                if (!stack.empty() && stack.peek() == '[') {
                    stack.pop();
                } else {
                    return false;
                }
            }
            if (c == '{') {
                stack.push(c);
            }
            if (c == '}') {
                if (!stack.empty() && stack.peek() == '{') {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }

        if (!stack.empty()) {
            return false;
        }

        return true;
    }

    public ListNode mergeKLists(ListNode[] lists) {

        if (lists.length == 0) {
            return null;
        }

        ListNode res = new ListNode(0);
        ListNode first = res;
        //非空链表数组
        ListNode[] notEmpP = lists;

        while (notEmpP.length > 0) {
            first.next = new ListNode(getMinValueP(notEmpP));
            first = first.next;
            notEmpP = reset(notEmpP);
        }
        return res.next;
    }

    public ListNode[] reset(ListNode[] ps) {
        int count = 0;
        for (int i = 0; i < ps.length; i++) {
            if (ps[i] != null) {
                count++;
            }
        }

        ListNode[] res = new ListNode[count];

        count = 0;
        for (ListNode p : ps) {
            if (p != null) {
                res[count++] = p;
            }
        }
        return res;
    }

    public int getMinValueP(ListNode[] ps) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < ps.length; i++) {
            if (ps[i].val < min) {
                min = ps[i].val;
                index = i;
            }
        }
        ps[index] = ps[index].next;

        return min;
    }

    /**
     * 寻找两个已排序数组的中位数 总的运行时复杂度应该是O(log (m+n))。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int cur1 = 0;
        int cur2 = 0;//指向当前数组的下标
        if (nums1.length == 0) {
            cur1 += 1;
        }
        if (nums2.length == 0) {
            cur2 += 1;
        }
        int odd_even = (nums1.length + nums2.length) % 2;
        int mid_index = odd_even == 0 ? (nums1.length + nums2.length) / 2 : (nums1.length + nums2.length) / 2 + 1;
        double result = 0;
        double a = 2;

        for (int i = 0; i < mid_index; i++) {
            if (cur1 > nums1.length - 1) {
                result = nums2[cur2];
                cur2++;
                continue;
            }
            if (cur2 > nums2.length - 1) {
                result = nums1[cur1];
                cur1++;
                continue;
            }
            if (nums1[cur1] <= nums2[cur2]) {
                result = nums1[cur1];
                cur1++;
            } else {
                result = nums2[cur2];
                cur2++;
            }
        }

        if (odd_even == 0) {//偶数
            if (cur1 > nums1.length - 1) {
                result = (result + nums2[cur2]) / a;
            } else if (cur2 > nums2.length - 1) {
                result = (result + nums1[cur1]) / a;
            } else {
                result = (result + (nums1[cur1] < nums2[cur2] ? nums1[cur1] : nums2[cur2])) / a;
            }
        }
        return result;

    }

    /**
     * 之子字符串
     */
    public String zigzig(String s, int numRows) {
        if (numRows == 1)
            return s;
        if (numRows == 2) {
            String[] s1 = s.split("");
            String result = "";
            for (int i = 0; i < s.length(); i += 2) {
                result += s1[i];
            }
            for (int j = 1; j < s.length(); j += 2) {
                result += s1[j];
            }
            return result;
        }
        int jiange = numRows - 2 + 1; //间隔长度
        int rn = 2 * numRows - 2;//每一个间隔能容纳的字符数量
        int jiange_num = s.length() / rn + 1;//间隔数量
        String[][] ss = new String[numRows][jiange_num * jiange];//创建矩阵

        int x = 0;
        int y = 0;

        int up = 0; //往上

        for (String c : s.split("")) {
            if (x < 0) {
                x += 2;
                ss[x][y] = c;
                up = 0;
                x += 1;
            } else if (x < numRows) {
                ss[x][y] = c;
                if (up == 1) {
                    x--;
                    if (x >= 0)
                        y++;
                } else {
                    x++;
                }
            } else if (x == numRows) {
                x -= 2;
                y += 1;
                ss[x][y] = c;
                up = 1;
                x--;
                y += 1;
            }
        }
        String result = "";
        for (String[] s1 : ss) {
            for (String s2 : s1) {
                if (s2 != null) {
                    result += s2;
                }
            }
        }
        return result;
    }

    /**
     * 确定整数是否是回文。当一个整数向后读取与向前读取相同的内容时，它就是一个回文。
     *
     * @return
     */
    public boolean test1(int x) {
        if (x < 0)
            return false;
        if (x < 10)
            return true;
        String[] strings = String.valueOf(x).split("");
        for (int i = 0, j = strings.length - 1; i < j; i++, j--) {
            if (!(strings[i].equals(strings[j]))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 荣积水问题 时间复杂度O(n*n)
     */
    public int maxArea(int[] height) {
        int result = 0;
        for (int i = 0; i < height.length; i++) {
            for (int j = i + 1; j < height.length; j++) {
                int cur = (j - i) * (height[i] < height[j] ? height[i] : height[j]);
                if (cur > result)
                    result = cur;
            }
        }
        return result;
    }

    /**
     * 字符串数组的最长公共子前缀
     * Input: ["flower","flow","flight"]
     * Output: "fl"
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0)
            return "";
        String result = strs[0]; //一开始认为第一个字符串是公共子前缀
        for (int i = 1; i < strs.length; ) {
            String Si = strs[i];
            if (Si.startsWith(result)) { //认可子串
                i++;
            } else
                result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    /**
     * 给定一个包含n个整数的数组，在数组中是否存在a、b、c元素使得a + b + c = 0?
     * 找出数组中所有唯一的三胞胎，它们的和为零。
     * Given array nums = [-1, 0, 1, 2, -1, -4],
     * <p>
     * A solution set is:
     * [
     * [-1, 0, 1],
     * [-1, -1, 2]
     * ]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum1(int[] nums) {
        return null;
    }

}
