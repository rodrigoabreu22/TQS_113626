package org.tqs.deti.ua;

import java.util.LinkedList;

public class TqsStack<T> {

    LinkedList<T> collection = new LinkedList<>();
    private int bound = -1;
    public TqsStack() {
        collection = new LinkedList<T>();
    }

    public TqsStack(int size) {
        if (size >= 0) {
            this.bound = size;
        }
        collection = new LinkedList<T>();
    }



    public T pop() {
        if (collection.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return collection.removeFirst();
    }

    public int size() {
        return collection.size();
    }

    public T peek() {
        if (collection.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return collection.getFirst();
    }

    public void push(T value) {
        if (this.bound > -1 ){
            if (this.bound > collection.size()) {
                collection.addFirst(value);
            }
            else {
                throw new IllegalStateException();
            }
        }
        else {
            collection.addFirst(value);
        }

    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }
    
    public T popTopN(int n){
        T top = null;
        for (int i = 0; i<n; i++){
            top = collection.removeFirst();
        }
        return top;
    }
}