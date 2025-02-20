package org.tqs.lei.ua.pt;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AITQSStackTest {

    private TQSStack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new TQSStack<>();
    }

    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty(), "Stack should be empty");
    }

    @Test
    public void testPush() {
        stack.push(1);
        assertFalse(stack.isEmpty(), "Stack should not be empty after push");
        assertEquals(1, stack.peek(), "Top element should be 1");
    }

    @Test
    public void testPop() {
        stack.push(1);
        int element = stack.pop();
        assertEquals(1, element, "Popped element should be 1");
        assertTrue(stack.isEmpty(), "Stack should be empty after pop");
    }

    @Test
    public void testPeek() {
        stack.push(1);
        int element = stack.peek();
        assertEquals(1, element, "Peeked element should be 1");
        assertFalse(stack.isEmpty(), "Stack should not be empty after peek");
    }

    @Test
    public void testPopEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> stack.pop(), "Popping from empty stack should throw NoSuchElementException");
    }

    @Test
    public void testPeekEmptyStack() {
        assertThrows(NoSuchElementException.class, () -> stack.peek(), "Peeking into empty stack should throw NoSuchElementException");
    }
}
