package com.learning.notebook.tips.algorithm.merge;

public class MergeList {

    public static ListNode merge(ListNode list1, ListNode list2) {
        ListNode newHead = new ListNode(0);
        ListNode curNode = newHead;
        while (list1 != null && list2 != null) {
            if (list1.getData() < list2.getData()) {
                curNode.setNext(list1);
                list1 = list1.getNext();
            } else {
                curNode.setNext(list2);
                list2 = list2.getNext();
            }
            curNode = curNode.getNext();
        }
        if (list1 != null) {
            curNode.setNext(list1);
        }
        if (list2 != null) {
            curNode.setNext(list2);
        }
        return newHead.getNext();
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode5 = new ListNode(5);

        listNode1.setNext(listNode3);
        listNode3.setNext(listNode5);

        ListNode listNode2 = new ListNode(2);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode6 = new ListNode(6);
        listNode2.setNext(listNode4);
        listNode4.setNext(listNode6);
        ListNode node = merge(listNode1, listNode2);
        while (node != null) {
            System.out.println(node.getData());
            node = node.getNext();
        }

    }


}
