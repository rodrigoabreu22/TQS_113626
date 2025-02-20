package org.tqs.lei.ua.pt;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class TQSStackTest 
{
    @Test
    public void testSizeOnInit()
    {
        TQSStack<Integer> myStack = new TQSStack<>();
        assertEquals(true, myStack.isEmpty());
        assertEquals(0, myStack.size());
    }

    @Test
    public void testSizeOnPush() {
        TQSStack<Integer> myStack = new TQSStack<>();
        for (int i = 0; i < 5; i++) {
            myStack.push(i);
        }
        assertEquals(false, myStack.isEmpty());
        assertEquals(5, myStack.size());
    }

    @Test
    public void testPopAndPeek() {
        TQSStack<Integer> myStack = new TQSStack<>();
        int random_n = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < random_n; i++) {
            myStack.push((int) (Math.random() * 100));
        }
        Integer push_value = 100;
        myStack.push(push_value);
        assertEquals(push_value, myStack.pop());
        int currentSize = myStack.size();
        myStack.pop();
        assertEquals(currentSize - 1, myStack.size());
        push_value = 100;
        myStack.push(push_value);
        assertEquals(push_value, myStack.peek());
        currentSize = myStack.size();
        myStack.peek();
        assertEquals(currentSize, myStack.size());
    }

    @Test
    public void testMultiplePops() {
        TQSStack<Integer> myStack = new TQSStack<>();
        int random_n = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < random_n; i++) {
            myStack.push((int) (Math.random() * 100));
        }
        int currentSize = myStack.size();
        for (int i = 0; i < currentSize; i++) {
            myStack.pop();
        }
        assertEquals(true, myStack.isEmpty());
        assertEquals(0, myStack.size());
    }

    @Test
    public void testIllegalActions() {
        TQSStack<Integer> myStack = new TQSStack<>(5);
        assertThrows(NoSuchElementException.class, () -> myStack.pop());
        assertThrows(NoSuchElementException.class, () -> myStack.peek());
        for (int i = 0; i < 5; i++) {
            myStack.push((int) (Math.random() * 100));
        }
        assertThrows(IllegalStateException.class, () -> myStack.push(100));    
    }

    @Test
    public void testPopTopN() {
        TQSStack<Integer> myStack = new TQSStack<>();
        int random_n = (int) (Math.random() * 9 + 1);
        for (int i = 0; i < random_n; i++) {
            myStack.push((int) (Math.random() * 100));
        }
        int currentSize = myStack.size();
        int random_top_n = (int) (Math.random() * currentSize);
        int top = myStack.popTopN(random_top_n);
        assertEquals(currentSize - random_top_n, myStack.size());
    }


}
