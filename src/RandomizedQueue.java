import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size = 0;
    private int pointer = 0;
    private int capacity = 16;
    private Item[] queue = (Item[]) new Object[capacity];

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("");
        if (pointer == capacity) {
            if (size <= capacity / 2) {
                resize(capacity);
            } else {
                resize(capacity * 2);
            }
        }
        queue[pointer++] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("");
        int i = randomNotEmptyIndex();
        Item item = queue[i];
        queue[i] = null;
        size--;
        if (size <= capacity / 4) {
            resize(capacity / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("");
        int i = randomNotEmptyIndex();
        return queue[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

        return new QueueIterator<>(copy());
    }

    private static class QueueIterator<Item> implements Iterator<Item> {
        RandomizedQueue<Item> queue;

        QueueIterator(RandomizedQueue<Item> queue) {
            this.queue = queue;
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public Item next() {
            if (queue.isEmpty()) throw new NoSuchElementException("");
            return queue.dequeue();
        }
    }

    private RandomizedQueue<Item> copy() {
        RandomizedQueue<Item> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.queue = compressedQueue();
        randomizedQueue.size = size;
        randomizedQueue.pointer = size;
        randomizedQueue.capacity = size;
        return randomizedQueue;
    }

    private int randomNotEmptyIndex() {
        int index = StdRandom.uniform(capacity);
        if (queue[index] == null) return randomNotEmptyIndex();
        return index;
    }

    private void resize(int size) {
        capacity = size;
        Item[] copy = (Item[]) new Object[capacity];
        int i = 0;
        int newPointer = 0;
        while (i < pointer) {
            if (queue[i] != null) {
                copy[newPointer++] = queue[i];
            }
            i++;
        }
        pointer = newPointer;
        queue = copy;
    }

    private Item[] compressedQueue() {
        Item[] compressed = (Item[]) new Object[size];
        int i = 0;
        int j = 0;
        while (i < pointer) {
            if (queue[i] != null) {
                compressed[j++] = queue[i];
            }
            i++;
        }
        return compressed;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<>();
        test.enqueue("sd");
        test.enqueue("dv");
        test.enqueue("fd");
        test.dequeue();
        test.enqueue("sdd");
        test.enqueue("ddv");
        test.enqueue("fdd");
        Iterator<String> iterator = test.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
