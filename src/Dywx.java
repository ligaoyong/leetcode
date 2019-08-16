
import java.util.ArrayList;

/**
 * 这些算法题很有意思，期望能收到审批官的回复！！
 */
public class Dywx {

    /**
     * 【1】hello world（10分）
     * 描述：判断从一个字符串中是否能抓取出helloworld，可以不连续但是一定要保持顺序。如helllllllo wwwwwwworld 但不能是helolllllworld。
     * 实现程序前请尽可能的描述清楚思路
     * <p>
     * 思路：
     * 取helloworld中的每一个字符 ，在text文本找到第一个出现该字符的位置，
     * 此时表示该字符已经查找成功，并标记该位置，下次从该位置往后查找；
     * 循环上述过程查找helloworld的所有字符，全部查找成功表示能够抓取并保持顺序
     */
    public boolean isHelloWorld(String text) {
        String hw = "helloworld";
        char[] hws = hw.toCharArray();
        int textIndex = 0;  //遍历到的位置 只处理之后的字符串
        int helloIndex = 0; //待处理的字符位置
        ArrayList<Character> finded = new ArrayList<>();//已经找到的字符
        for (; helloIndex < hws.length; ) {
            char finding = hws[helloIndex]; //正在查找的字符
            boolean _ok = false; //是否找到字符
            for (; textIndex < text.length(); textIndex++) {
                char t = text.charAt(textIndex);    // 字典(text)中的字符
                if (t == finding) { //查找成功
                    helloIndex++;
                    textIndex++;    //重新标记字典位置，寻找下一个字符时从此处往后开始查找
                    finded.add(finding);
                    _ok = true;
                    break;  //字符按照正确的顺序找到 跳出 继续查找下一个
                }
            }
            if (!_ok)
                return false;
        }
        return finded.size() == hw.length();
    }

    /**
     * 【2】删除元素（10分）
     * 给定一个数组 和需要删除的元素 返回删除值相同的元素之后 数组的长度，并且修改原数组 只用到额外 O(1) 的空间.元素的顺序不能改变
     * 但是你在新的长度之后留下了什么并不重要. 要求时间复杂度做到 O(n)，0 < n <= 100000
     * Given nums = [3,2,2,3], val = 3, answer 2  nums ==> [2,2,0,0]
     * <p>
     * 思路：
     *      使用双指针实现
     *      第一个指针指向需要被替换的位置(往后第一个等于val)
     *      第二个指针指向需要替换的位置(往后第一个不等于val)
     *      将第一个指针的值用第二个指针替换，第二个指针的值用特定值填充
     *      两个指针分别往后移动，重复上述过程
     */
    public int removeElement(int[] nums, int val) {
        int a = 0;  //指向等于val的下标
        int b = 0;  //指向第一个不等于val的下边 --> 用b位置的值填充到a位置
        boolean a_ok = false; //找到a的位置了
        boolean b_ok = false; // 找到b的位置了
        int tmp;
        int pad = Integer.MIN_VALUE; //填充值
        while (a < nums.length && b < nums.length) {
            if (a_ok && b_ok) {
                tmp = nums[b];
                nums[b] = pad;
                nums[a] = tmp;
                a++;
                b++;
                a_ok = false;
                b_ok = false;
            } else {
                if (!a_ok)
                    if (nums[a] == val || nums[a] == pad)
                        a_ok = true;
                    else
                        a++;
                if (!b_ok)
                    if ((nums[b] != val && nums[b] != pad) && b > a)
                        b_ok = true;
                    else
                        b++;
            }
        }
        //b指针已经走完
        if (b >= nums.length && a < nums.length)
            while (a < nums.length){
                if (nums[a] == val)
                    nums[a] = pad;
                a++;
            }
        //计算长度
        int len = 0;
        for (int num : nums) {
            if (num != pad)
                len++;
        }
        return len;
    }

    /**
     * 【3】翻转 bits（10分）
     * 给定一个32位整数 . 输出二进制表示反转后的值.例如 input 43261596 , return 964176192
     * 实现程序前请尽可能的描述清楚思路
     * <p>
     * 思路：
     * 用value & 1取最低为保存，value右移一位取次低位，往复取玩，可以得到二进制表示
     * 在讲二进制还原为10进制即可
     */
    public int reverse(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 32; i++) {
            list.add(n & 1);
            n = n >>> 1;    //带符号右移
        }
        int value = 0;
        int x = 30;
        for (int i = 1; x >= 0; i++) {   //第一位是符号位
            if (list.get(i) == 1) {
                value += Math.pow(2, x);    //还原为十进制
            }
            x--;
        }
        return value;
    }

    /**
     * 4】程序设计与实现（20分）
     * 实现可以求栈中最大最小值的栈，需要支持 pop，push，max，min 四个方法。
     * 要求自己实现数据结构，要求所有 function 请求的时间复杂度是 O(1)，整体空间复杂度是 O(n)
     * 实现程序前请尽可能的描述清楚思路
     * <p>
     * 思路：
     * 使用3个单链表来实现栈结构，分别用来保存正常栈、最大值栈、最小值栈
     * push时检查值是否大于等于最大值栈 则入最大值栈  小于等于同理
     * pop时检查值是否等于最大值栈、最小值栈栈顶值，等于则移除最大值栈、最小值栈栈顶
     */
    public class Stack {
        /**
         * 简单栈节点
         */
        class Node {
            int value;
            Node next;

            Node(int value) {
                this.value = value;
                this.next = null;
            }
        }

        //栈顶指针
        Node top = new Node(Integer.MIN_VALUE);
        //最大值栈
        Node maxTop = new Node(Integer.MAX_VALUE);
        //最小值栈
        Node minTop = new Node(Integer.MIN_VALUE);

        public void push(int value) {
            // 压入正常栈
            Node node = new Node(value);
            node.next = top.next;
            top.next = node;
            //处理最大值栈:大于等于最大值栈的栈顶则入栈
            if (maxTop.next == null || value >= maxTop.next.value) {
                Node maxNode = new Node(value);
                maxNode.next = maxTop.next;
                maxTop.next = maxNode;
            }
            //处理最小值栈：同上
            if (minTop.next == null || value <= minTop.next.value) {
                Node minNode = new Node(value);
                minNode.next = minTop.next;
                minTop.next = minNode;
            }
        }

        public int pop() {
            Node t = top.next;
            top.next = t.next;
            t.next = null;
            //处理最大值栈:出栈的值与最大值栈栈顶值相等 则最大值栈出栈一次
            if (maxTop.next != null && t.value == maxTop.next.value) {
                Node maxNode = maxTop.next;
                maxTop.next = maxNode.next;
                maxNode.next = null;
            }
            //处理最小值栈：同上
            if (maxTop.next != null && t.value == minTop.next.value) {
                Node minNode = minTop.next;
                minTop.next = minNode.next;
                minNode.next = null;
            }
            return t.value;
        }

        public int getMaxValue() {
            return maxTop.next != null ? maxTop.next.value : Integer.MAX_VALUE;
        }

        public int getMinValue() {
            return minTop.next != null ? minTop.next.value : Integer.MIN_VALUE;
        }
    }
}
