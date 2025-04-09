package org.copy.redis.server.DataStructure;


public class RedisLinkedList {

        // 链表节点类
        private static class ListNode {
            Object value;  // 节点值
            ListNode prev; // 前一个节点
            ListNode next; // 下一个节点

            public ListNode(String value) {
                this.value = value;
            }
        }

        private ListNode head;  // 链表头
        private ListNode tail;  // 链表尾
        private int length;     // 链表长度

        public RedisLinkedList() {
            this.head = null;
            this.tail = null;
            this.length = 0;
        }

        // 在链表头部插入节点
        public void lpush(String value) {
            ListNode newNode = new ListNode(value);
            if (length == 0) {
                head = tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            length++;
        }

        // 在链表尾部插入节点
        public void rpush(String value) {
            ListNode newNode = new ListNode(value);
            if (length == 0) {
                head = tail = newNode;
            } else {
                newNode.prev = tail;
                tail.next = newNode;
                tail = newNode;
            }
            length++;
        }

        // 从链表头部删除节点
        public Object lpop() {
            if (length == 0) {
                return null;
            }

            Object value = head.value;
            if (length == 1) {
                head = tail = null;
            } else {
                head = head.next;
                head.prev = null;
            }
            length--;
            return value;
        }

        // 从链表尾部删除节点
        public Object rpop() {
            if (length == 0) {
                return null;
            }

            Object value = tail.value;
            if (length == 1) {
                head = tail = null;
            } else {
                tail = tail.prev;
                tail.next = null;
            }
            length--;
            return value;
        }

        // 获取链表长度
        public int llen() {
            return length;
        }

        // 获取链表中指定位置的元素（从 0 开始）
        public Object lindex(int index) {
            if (index < 0 || index >= length) {
                return null;
            }

            ListNode current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.value;
        }

        // 打印链表
        public void printList() {
            ListNode current = head;
            while (current != null) {
                System.out.print(current.value + " ");
                current = current.next;
            }
            System.out.println();
        }

    // LINSERT：在指定元素的前或后插入新元素
    public boolean linsert(Object pivot, String value, boolean after) {
        ListNode current = head;
        while (current != null) {
            if (current.value.equals(pivot)) {
                ListNode newNode = new ListNode(value);
                if (after) {
                    // 插入在 pivot 之后
                    newNode.next = current.next;
                    newNode.prev = current;
                    if (current.next != null) {
                        current.next.prev = newNode;
                    }
                    current.next = newNode;
                    if (current == tail) {
                        tail = newNode;
                    }
                } else {
                    // 插入在 pivot 之前
                    newNode.prev = current.prev;
                    newNode.next = current;
                    if (current.prev != null) {
                        current.prev.next = newNode;
                    }
                    current.prev = newNode;
                    if (current == head) {
                        head = newNode;
                    }
                }
                length++;
                return true;
            }
            current = current.next;
        }
        return false; // 没找到 pivot
    }

}


