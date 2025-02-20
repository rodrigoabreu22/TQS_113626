package org.tqs.lei.ua.pt;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Hello world!
 *
 */
public class TQSStack<T> {
    private int boundary;
    private LinkedList<T> stack;

    public TQSStack() {
        this.stack = new LinkedList<>();
        this.boundary = 10;
    }

    public TQSStack(int boundary) {
        this.stack = new LinkedList<>();
        this.boundary = boundary;
    }

    public T pop() {
        if (this.stack.isEmpty()) {
            throw new NoSuchElementException();
        }
        T poppedElement = this.stack.getFirst();
        this.stack.removeFirst();
        return poppedElement;
    }

    public int size() {
        return this.stack.size();
    }

    public T peek() {
        if (this.stack.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.stack.getFirst();
    }

    public void push(T i) {
        if (this.stack.size() == this.boundary) {
            throw new IllegalStateException();
        }
        this.stack.add(0, i);
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public T popTopN(int n) {
        T top = null;
        for (int i = 0; i < n; i++) {
            top = this.stack.removeFirst();
        }
        return top;
    }
    
}
