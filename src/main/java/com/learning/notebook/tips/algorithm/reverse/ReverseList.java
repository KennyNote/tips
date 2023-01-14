package com.learning.notebook.tips.algorithm.reverse;

public class ReverseList {

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);
        ListNode listNode6 = new ListNode(6);

        listNode1.setNext(listNode2);
        listNode2.setNext(listNode3);
        listNode3.setNext(listNode4);
        listNode4.setNext(listNode5);
        listNode5.setNext(listNode6);
        ListNode node = reverseBetween(listNode1, 3, 5);
        while (node != null) {
            System.out.println(node.getData());
            node = node.getNext();
        }
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        // 设置 dummyNode 是这一类问题的一般做法
        ListNode dummyNode = new ListNode(-1);
        dummyNode.setNext(head);
        ListNode pre = dummyNode;
        for (int i = 0; i < left - 1; i++) {
            pre = pre.getNext();
        }

        ListNode cur = pre.getNext();
        ListNode next;

        for (int i = 0; i < right - left; i++) {
            next = cur.getNext();
            cur.setNext(next.getNext());
            next.setNext(pre.getNext());
            pre.setNext(next);
        }
        return dummyNode.getNext();
    }
}
