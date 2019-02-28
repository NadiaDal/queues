import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head = null;
    private Node<Item> tail = null;
    private int size = 0;

    private static class Node<Item> {
        Item item;
        Node<Item> prev;
        Node<Item> next;

        public Node(Item item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("");
        Node<Item> oldHead = head;
        head = new Node<>(item);
        if (isEmpty()) {
            tail = head;
        }
        if (oldHead != null) {
            oldHead.prev = head;
        }
        head.next = oldHead;
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("");
        Node<Item> oldTail = tail;
        tail = new Node<>(item);
        if (isEmpty()) {
            head = tail;
        }
        if (oldTail != null) {
            oldTail.next = tail;
        }
        tail.prev = oldTail;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (head == null) throw new NoSuchElementException("Empty deque!");
        Node<Item> oldHead = head;
        head = oldHead.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
        return oldHead.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (tail == null) throw new NoSuchElementException("Empty deque!");
        Node<Item> oldTail = tail;
        tail = oldTail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        size--;
        return oldTail.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            Node<Item> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (current == null) throw new NoSuchElementException("");
                Node<Item> item = current;
                current = current.next;
                return item.item;
            }
        };
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        deque.addLast("tail1");
        String first = deque.removeFirst();
        String first2 = deque.removeFirst();
        System.out.println(first2);
        deque.size();
    }
}
