package org.tqs.deti.ua;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;


public class TqsStackTest {
    static final Logger log = getLogger(lookup().lookupClass());

    TqsStack stack;

    @BeforeEach
    void setup() {
        this.stack = new TqsStack();
    }

    @Test
    void StackIsEmptyOnConstructionTest() {
        log.debug("Testing if stack is empty on contruction.");

        Assertions.assertTrue(stack.isEmpty());
    }

    @Test
    void StackHasSizeZeroOnConstructionTest() {
        log.debug("Testing if stack has size 0 on contruction.");

        Assertions.assertTrue(stack.size() == 0);
    }

    @Test
    void StackNSizeOnNPushesTest() {
        log.debug("Testing if stack size matches the number of pushes.");

        for (int i = 1; i <= 5; i++) {
            stack.push(i);
        }
        Assertions.assertTrue(stack.size() == 5);
    }

    @Test
    void StackPopReturnsTheLastValuePushedTest() {
        log.debug("Testing if stack returns the last value pushed.");

        stack.push(2);

        Assertions.assertEquals(stack.pop(),2);
    }

    @Test
    void StackPeekMaintainsTheStackSizeTest(){
        log.debug("Testing if stack peek maintains the stack size.");

        stack.push(2);
        stack.push(3);

        int size_before = stack.size();
        stack.peek();

        Assertions.assertEquals(size_before,stack.size());
    }

    @Test
    void NPopsToStackWithSizeNTest(){
        log.debug("Testing if stack peek maintains the stack size.");

        for (int i = 0; i < 5; i++) {
            stack.push(i);
        }

        for (int i = 0; i < 5; i++) {
            stack.pop();
        }

        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertEquals(0, stack.size());
    }

    @Test

    void testPopException() {
        log.debug("Popping from an empty stack does throw a NoSuchElementException");
        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertThrows(java.util.NoSuchElementException.class, () -> {
            stack.pop();
        });
    }

    @Test
    void testPeekException() {
        log.debug("Peeking into an empty stack does throw a NoSuchElementException");
        Assertions.assertTrue(stack.isEmpty());
        Assertions.assertThrows(java.util.NoSuchElementException.class, () -> {
            stack.peek();
        });
    }

    @Test

    void testPushException() {
        log.debug("For bounded stacks only: pushing onto a full stack does throw an IllegalStateException");
        TqsStack<Integer> boundedStack = new TqsStack<Integer>(3);

        boundedStack.push(1);
        boundedStack.push(2);
        boundedStack.push(3);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            boundedStack.push(4);
        });
    }

    @Test
    void testPopTopN() {
        log.debug("Test pop top N stack elements.");

        TqsStack<Integer> stack = new TqsStack<>();

        int first = (int) (Math.random() * 9 + 1);
        stack.push(first);

        for (int i=1; i<5; i++){
            stack.push(i);
        }

        Assertions.assertEquals(first, stack.popTopN(5));
    }
}
