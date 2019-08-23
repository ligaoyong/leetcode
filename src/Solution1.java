import com.sun.deploy.util.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

//Definition for binary tree
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

//求给定二叉树的最小深度。最小深度是指树的根结点到最近叶子结点的最短路径上结点的数量。
public class Solution1 {
    public int run(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int minDeep = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            minDeep++;
            for (int i = 0; i < len; i++) {
                TreeNode treeNode = queue.poll();
                if (treeNode.left == null && treeNode.right == null) {
                    return minDeep;
                }
                if (treeNode.left != null)
                    queue.offer(treeNode.left);
                if (treeNode.right != null)
                    queue.offer(treeNode.right);
            }
        }
        return 0;
    }

    /**
     * 计算逆波兰式（后缀表达式）的值
     * 运算符仅包含"+","-","*"和"/"，被操作数可能是整数或其他表达式
     * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9↵  ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
     *
     * @param tokens
     * @return
     */
    public int evalRPN(String[] tokens) {
        Stack<String> stack = new Stack<>();
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("+");
        hashSet.add("-");
        hashSet.add("/");
        hashSet.add("*");
        for (String str : tokens) {
            if (hashSet.contains(str)) {
                String s1 = stack.pop();
                String s2 = stack.pop();
                if (str.equals("+"))
                    stack.push(String.valueOf(Integer.valueOf(s2) + Integer.valueOf(s1)));
                if (str.equals("-"))
                    stack.push(String.valueOf(Integer.valueOf(s2) - Integer.valueOf(s1)));
                if (str.equals("*"))
                    stack.push(String.valueOf(Integer.valueOf(s2) * Integer.valueOf(s1)));
                if (str.equals("/"))
                    stack.push(String.valueOf(Integer.valueOf(s2) / Integer.valueOf(s1)));
            } else {
                stack.push(str);
            }
        }

        return Integer.valueOf(stack.pop());
    }

    /**
     * 对于给定的n个位于同一二维平面上的点，求最多能有多少个点位于同一直线上
     */
    class Point {
        int x;
        int y;

        Point() {
            x = 0;
            y = 0;
        }

        Point(int a, int b) {
            x = a;
            y = b;
        }
    }

    public int maxPoints(Point[] points) {
        int size = points.length;
        if (size < 3)
            return size;
        int max = 0;
        Map<String, Integer> aeqb = new HashMap<>();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) { //怎么处理ab在同一点的问题
                Point a = points[i];
                Point b = points[j];
                if (a.x == b.x && a.y == b.y) { //单独处理ab在同一点的情况
                    if (aeqb.containsKey("" + a + "-" + b)) {
                        aeqb.computeIfPresent("" + a + "-" + b, (key, value) -> value++);
                    } else
                        aeqb.put("" + a + "-" + b, 1);
                }
                int len = 2;
                for (int k = 0; k < size; k++) {
                    if (k == i || k == j)
                        continue;
                    Point c = points[k];
                    if ((c.x == a.x && c.y == a.y) || (c.x == b.x && c.y == b.y)) {
                        len++;
                        continue;
                    }
                    if (b.x == a.x || c.x == b.x) {
                        if (b.x == a.x && c.x == b.x) {
                            len++;
                            continue;
                        }
                        continue;
                    }
                    double ab = Math.abs(b.y - a.y) / Math.abs(b.x - a.x);
                    double bc = Math.abs(c.y - b.y) / Math.abs(c.x - b.x);
                    if (ab == bc)
                        len++;
                }
                max = Math.max(max, len);
            }
        }
        Optional<Integer> max1 = aeqb.values().stream().max(Comparator.naturalOrder());
        return Math.max(max, max1.orElse(0));
    }


    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    @Test
    public void test1() {
        ListNode head = new ListNode(0);
        ListNode node1 = new ListNode(2);
        ListNode node2 = new ListNode(1);
        head.next = node1;
        node1.next = node2;
        ListNode list = sortList(head);
        System.out.println(list);
    }

    //链表排序 //快慢指针 归并排序
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return head;
        ListNode midhead = getMid(head);
        ListNode left = sortList(head);
        ListNode right = sortList(midhead);
        return merge(left, right);
    }

    private ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;
        ListNode head = new ListNode(0);
        ListNode p = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }
        if (l1 == null) {
            p.next = l2;
        }
        if (l2 == null) {
            p.next = l1;
        }
        return head;
    }

    private ListNode getMid(ListNode head) {
        if (head == null)
            return null;
        ListNode p = head;
        ListNode q = head.next;
        while (p != null && q.next != null) {
            p = p.next;
            q = q.next.next;
        }
        return p;
    }

    //插入排序
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return head;
        ListNode result = new ListNode(0);
        ListNode p;
        ListNode h = head.next; //取第一个节点

        while (h != null) {
            p = result; //重置p节点
            if (p.next == null) {
                p.next = h;
            }
            while (p.next != null && h.val > p.next.val)
                p = p.next;
            h.next = p.next;
            p.next = h;
            h = h.next;
        }
        return result;
    }

    // 树的中序遍历  不用递归
    public ArrayList<Integer> zhongorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null)
            return list;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;
        while (!stack.empty() || p != null) {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
            if (!stack.empty()) {
                TreeNode pop = stack.pop();
                list.add(pop.val);
                p = pop.right;
            }
        }
        return list;
    }

    @Test
    public void testpreorderTraversal() {
        TreeNode node = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        node.right = node1;
        ArrayList<Integer> integers = zhongorderTraversal(node);
        System.out.println(integers);
    }

    //// 树的前序遍历  不用递归

    /**
     * 核心思路：必须找到每次循环需要处理的节点或子树(P) 可以借助栈的结构保存待处理的节点
     *
     * @param root
     * @return
     */
    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null)
            return list;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;
        while (!stack.empty() || p != null) {
            if (p != null) {
                list.add(p.val);
                stack.push(p.right);
                p = p.left;
            } else
                p = stack.pop();
        }
        return list;
    }

    // 树的后序遍历  不用递归
    //巧妙的方法：根据前序遍历 根->左->右 变成 根->右->左 结果再reverse一下
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null)
            return list;
        Stack<TreeNode> stack = new Stack<>();
        TreeNode p = root;
        while (!stack.empty() || p != null) {
            if (p != null) {
                list.add(p.val);
                stack.push(p.left);
                p = p.right;
            } else
                p = stack.pop();
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 将给定的单链表L： L 0→L 1→…→L n-1→L n,
     * 重新排序为： L 0→L n →L 1→L n-1→L 2→L n-2→…
     * 要求使用原地算法，并且不改变节点的值
     * 例如：
     * 对于给定的单链表{1,2,3,4}，将其重新排序为{1,4,2,3}.
     *
     * @param head 思路：// 快满指针找到中间节点 // 拆分链表，并反转中间节点之后的链表    // 合并两个链表
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null)
            return;
        ListNode fast = head;
        ListNode slow = head;

        // 快满指针找到中间节点
        while (slow.next != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // 拆分链表，并反转中间节点之后的链表
        ListNode l2 = slow;
        ListNode head2 = new ListNode(0);
        while (l2.next != null) {
            ListNode p = l2.next;
            l2.next = p.next;
            p.next = head2.next;
            head2.next = p;
        }
        slow.next = null;
        // 合并两个链表
        ListNode l1 = head.next;
        ListNode l11 = head;
        while (l1 != null && head2.next != null) {
            ListNode q = head2.next;
            q.next = l1.next;
            l1.next = q;
            l1 = q.next;
            l11 = q;
        }
        if (l1 == null) {
            l11.next = head2.next;
            head2.next = null;
        }
    }

    @Test
    public void testreorderList() {
        ListNode head = new ListNode(0);
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        head.next = node1;
        node1.next = node2;

        reorderList(head);
        System.out.println(head);
    }

    /**
     * 对于一个给定的链表，返回环的入口节点，如果没有环，返回null
     * 思路 ：利用快慢指针相遇  指针相差的步数就是环的长度
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null)
            return null;
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow)
                break;
        }
        if (fast == null || fast.next == null)
            return null;
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    @Test
    public void test2() {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("a");
        hashSet.add("b");
        ArrayList<String> list = wordBreak("ab", hashSet);
        System.out.println(list);
    }

    public ArrayList<String> wordBreak(String s, Set<String> dict) {
        ArrayList<String> result = new ArrayList<>();
        DFS(s, dict, result);
        return result;
    }

    private void DFS(String s, Set<String> dict, ArrayList<String> result) {
        if (s.equals(""))
            return;
        for (String d : dict) {
            if (s.startsWith(d)) {
                result.add(d);
                int length = s.length();
                String s1 = s.substring(d.length(), length);
                DFS(s1, dict, result);
            }
        }
    }

    class RandomListNode {
        int label;
        RandomListNode next, random;

        RandomListNode(int x) {
            this.label = x;
        }
    }

    /**
     * 现在有一个这样的链表：链表的每一个节点都附加了一个随机指针，随机指针可能指向链表中的任意一个节点或者指向空。
     * 请对这个链表进行深拷贝。
     * <p>
     * 主要思想还是先拷贝新节点，插入到原节点的后边；然后再 拷贝随机指针；最后将新节点从原链表中分离出，注意要保证原链表正常。
     *
     * @param head
     * @return
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null)
            return null;
        RandomListNode p, copy;
        //复制节点
        for (p = head; p != null; p = p.next) {
            copy = new RandomListNode(p.label);
            copy.next = p.next;
            p = p.next = copy;
        }
        //扫描随机节点处理
        for (p = head; p != null; p = copy.next){
            copy = p.next;
            copy.random = p.random != null ? p.random.next : null;

        }
        //从链表中分离出来
        for (p = head, head = copy = p.next; p != null; ) {
            p = p.next = copy.next;
            copy = copy.next = p
                    != null ? p.next : null;
        }
        return head;
    }

    //求二叉树的最大路径和
    Integer max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if (root == null)
            return 0;
        maxPath(root);
        return max;
    }
    private Integer maxPath(TreeNode root) {
        if (root == null)
            return 0;
        int left = Math.max(maxPath(root.left),0);
        int right = Math.max(maxPath(root.right),0);
        if (left+right+root.val > max)
            max = left + right + root.val;
        return Math.max(left,right)+root.val;
    }

    @Test
    public void test3(){
        TreeNode root = new TreeNode(2);
        TreeNode left = new TreeNode(-1);
        root.left = left;
        System.out.println(maxPathSum(root));
    }
}