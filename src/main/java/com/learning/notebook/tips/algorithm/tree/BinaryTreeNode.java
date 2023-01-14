package com.learning.notebook.tips.algorithm.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class BinaryTreeNode<T> {

    private T data;
    private BinaryTreeNode<T> left;
    private BinaryTreeNode<T> right;

    public BinaryTreeNode(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    public BinaryTreeNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }

    public static void main(String[] args) {
        BinaryTreeNode<String> binaryTreeNodeA = new BinaryTreeNode<>("A");
        BinaryTreeNode<String> binaryTreeNodeB = new BinaryTreeNode<>("B");
        BinaryTreeNode<String> binaryTreeNodeC = new BinaryTreeNode<>("C");
        BinaryTreeNode<String> binaryTreeNodeD = new BinaryTreeNode<>("D");
        BinaryTreeNode<String> binaryTreeNodeE = new BinaryTreeNode<>("E");
        BinaryTreeNode<String> binaryTreeNodeF = new BinaryTreeNode<>("F");

        binaryTreeNodeA.setLeft(binaryTreeNodeB);
        binaryTreeNodeA.setRight(binaryTreeNodeC);

        binaryTreeNodeB.setLeft(binaryTreeNodeD);
        binaryTreeNodeB.setRight(binaryTreeNodeE);

        binaryTreeNodeC.setRight(binaryTreeNodeF);

        System.out.println(pre(binaryTreeNodeA));
        System.out.println(in(binaryTreeNodeA));
        System.out.println(post(binaryTreeNodeA));
        System.out.println(level(binaryTreeNodeA));
    }

    public BinaryTreeNode<String> invertTree(BinaryTreeNode<String> root) {
        if (root == null) {
            return null;
        }
        BinaryTreeNode<String> left = invertTree(root.getLeft());
        BinaryTreeNode<String> right = invertTree(root.getRight());
        root.setLeft(right);
        root.setRight(left);
        return root;
    }

    public static List<String> pre(BinaryTreeNode<String> rootNode) {
        if (rootNode == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        Deque<BinaryTreeNode<String>> arrayDeque = new ArrayDeque<>();
        arrayDeque.push(rootNode);
        while (!arrayDeque.isEmpty()) {
            BinaryTreeNode<String> pop = arrayDeque.pop();
            result.add(pop.getData());

            if (pop.getRight() != null) {
                arrayDeque.push(pop.getRight());
            }
            if (pop.getLeft() != null) {
                arrayDeque.push(pop.getLeft());
            }
        }
        return result;
    }

    public static List<String> in(BinaryTreeNode<String> rootNode) {
        if (rootNode == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        Deque<BinaryTreeNode<String>> arrayDeque = new ArrayDeque<>();
        BinaryTreeNode<String> cur = rootNode;
        while (!arrayDeque.isEmpty() || cur != null) {
            while (cur != null) {
                arrayDeque.push(cur);
                cur = cur.getLeft();
            }

            // cur == null，arrayDeque.pop()即得到最左的的Node，记录data
            // 然后再看其右子树，
            // 如果没有右子树就会下一轮pop，得到的就是最左的的Node的父节点，再看右子树。
            // 如果有右子树，则相同操作操作子树。
            // 从下向上不断处理
            BinaryTreeNode<String> pop = arrayDeque.pop();
            result.add(pop.getData());
            cur = pop.getRight();
        }

        return result;
    }

    public static List<String> post(BinaryTreeNode<String> rootNode) {
        if (rootNode == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        Deque<BinaryTreeNode<String>> arrayDeque = new ArrayDeque<>();
        BinaryTreeNode<String> cur = rootNode;
        BinaryTreeNode<String> tmp = null;
        while (!arrayDeque.isEmpty() || cur != null) {
            if (cur != null) {
                arrayDeque.push(cur);
                cur = cur.getLeft();
            } else {
                BinaryTreeNode<String> peek = arrayDeque.peek();
                // 到这里已经到了最左边的节点 此时需要判断他的右节点有没有，或者等右节点打印之后
                if (peek.getRight() != null && peek.getRight() != tmp) {
                    // 如果右节点存在并且右节点未处理，处理右节点，并使cur指向右节点的左节点（总是优先处理子节点）
                    cur = peek.getRight();
                    arrayDeque.push(cur);
                    cur = cur.getLeft();
                } else {
                    // 如果右节点不存在，出栈当前节点（此节点肯定是左右都无节点的），并设置t = cur 和 cur = null
                    cur = arrayDeque.pop();
                    result.add(cur.getData());
                    tmp = cur;
                    cur = null;
                }
            }
        }
        return result;
    }

    public static List<String> level(BinaryTreeNode<String> rootNode) {
        if (rootNode == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        Deque<BinaryTreeNode<String>> arrayDeque = new ArrayDeque<>();
        arrayDeque.add(rootNode);
        while (!arrayDeque.isEmpty()) {
            BinaryTreeNode<String> pop = arrayDeque.pop();
            result.add(pop.getData());

            if (pop.getLeft() != null) {
                arrayDeque.add(pop.getLeft());
            }

            if (pop.getRight() != null) {
                arrayDeque.add(pop.getRight());
            }
        }
        return result;
    }

    public void preByRecursion(BinaryTreeNode<String> rootNode) {
        if (Objects.nonNull(rootNode)) {
            System.out.println(rootNode.getData());
            preByRecursion(rootNode.getLeft());
            preByRecursion(rootNode.getRight());
        }
    }

    public void inByRecursion(BinaryTreeNode<String> rootNode) {
        if (Objects.nonNull(rootNode)) {
            inByRecursion(rootNode.getLeft());
            System.out.println(rootNode.getData());
            inByRecursion(rootNode.getRight());
        }
    }

    public void postByRecursion(BinaryTreeNode<String> rootNode) {
        if (Objects.nonNull(rootNode)) {
            postByRecursion(rootNode.getLeft());
            postByRecursion(rootNode.getRight());
            System.out.println(rootNode.getData());
        }
    }

    public boolean isSameTree(BinaryTreeNode<String> p, BinaryTreeNode<String> q) {
        if (p == null && q == null) {//两棵树同时为空,那么它就是相同的(同为空树)
            return true;
        }
        if (p == null || q == null) {//一个为空,一个不为空,一定不是相同的
            return false;
        }
        if (p.getData() != q.getData()) {//根节点不同,一定不相同
            return false;
        }
        //根看完了,以相同的方式去看他俩的左子树和右子树
        //如果两棵树的左相同且右相同,那么他俩一定是相同的(根已经相同)
        return isSameTree(p.getLeft(), q.getLeft()) && isSameTree(p.getRight(), q.getRight());
    }

    /**
     * 判断是不是对称二叉树
     */
    public static boolean isSymmetry(BinaryTreeNode<String> rootNode) {
        if (rootNode == null) {
            return true;
        }
        Deque<BinaryTreeNode<String>> arrayDeque = new ArrayDeque<>();
        arrayDeque.push(rootNode.getLeft());
        arrayDeque.push(rootNode.getRight());
        while (!arrayDeque.isEmpty()) {
            BinaryTreeNode<String> right = arrayDeque.pop();
            BinaryTreeNode<String> left = arrayDeque.pop();
            if (right == null && left == null) {
                continue;
            } else {
                if (left == null) {
                    return false;
                }
                if (right == null) {
                    return false;
                }
                if (left.getData().compareTo(right.getData()) != 0) {
                    return false;
                }
            }
            arrayDeque.push(left.getLeft());
            arrayDeque.push(right.getRight());
            arrayDeque.push(left.getRight());
            arrayDeque.push(right.getLeft());
        }

        return true;
    }

    public static boolean isCompleteTree(BinaryTreeNode<String> aRoot) {
        if (aRoot == null) {//如果是空树，那么它是完全二叉树
            return true;
        }
        //****接下来是标准的二叉树层序遍历****
        Queue<BinaryTreeNode<String> > queue = new LinkedList<>();
        queue.add(aRoot);
        while (true) {
            BinaryTreeNode<String>  remove = queue.remove();
            if (remove == null) {
                break;
            }
            queue.add(remove.left);
            queue.add(remove.right);
        }
        //*********************************
        for (BinaryTreeNode<String>  oneElement : queue) {
            //如果遍历队列中剩余的元素，还有不是null的，
            //那么，它就不是完全二叉树，否则就是完全二叉树
            if (oneElement != null) {
                return false;
            }
        }
        return true;
    }


}
