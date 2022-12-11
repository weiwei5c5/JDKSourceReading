package test.atomic.ABA;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Chenkunwei
 * @version 1.0.0
 * @ClassName ABATest2.java
 * @Description TODO
 * @createTime 2021年09月29日 20:21:00
 */
public class ABATest2 {

    static class Stack {
        // 将top放在原子类中
        private AtomicReference<Node> top = new AtomicReference<>();
        // 栈中节点信息
        static class Node {
            int value;
            Node next;

            public Node(int value) {
                this.value = value;
            }
        }
        // 出栈操作
        public Node pop() {
            for (;;) {
                // 获取栈顶节点
                Node t = top.get();
                if (t == null) {
                    return null;
                }
                // 栈顶下一个节点
                Node next = t.next;
                // CAS更新top指向其next节点
                if (top.compareAndSet(t, next)) {
                    // 把栈顶元素弹出，应该把next清空防止外面直接操作栈
                    t.next = null;
                    return t;
                }
            }
        }
        // 入栈操作
        public void push(Node node) {
            for (;;) {
                // 获取栈顶节点
                Node next = top.get();
                // 设置栈顶节点为新节点的next节点
                node.next = next;
                // CAS更新top指向新节点
                if (top.compareAndSet(next, node)) {
                    return;
                }
            }
        }
    }

    private static void testStack() {
        // 初始化栈为 top->1->2->3
        Stack stack = new Stack();
        stack.push(new Stack.Node(3));
        stack.push(new Stack.Node(2));
        stack.push(new Stack.Node(1));

        new Thread(()->{
            // 线程1出栈一个元素
            stack.pop();
        }).start();

        new Thread(()->{
            // 线程2出栈两个元素
            Stack.Node A = stack.pop();
            Stack.Node B = stack.pop();
            // 线程2又把A入栈了
            stack.push(A);
        }).start();
    }

    public static void main(String[] args) {
        testStack();
    }
}