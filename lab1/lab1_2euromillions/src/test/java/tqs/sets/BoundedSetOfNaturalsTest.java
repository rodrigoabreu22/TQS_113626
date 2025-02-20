/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;



    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        //added for tests
        setD = new BoundedSetOfNaturals(2);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    //@Disabled("TODO revise test logic")
    @Test
    public void testAddElement() {
        assertThrows(IllegalArgumentException.class, ()->setA.add(-11));

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());
        
        assertThrows(IllegalArgumentException.class, ()->setB.add(11));

        setD.add(1);
        assertThrows(IllegalArgumentException.class, ()->setD.add(1));
    }

    //@Disabled("TODO revise to test the construction from invalid arrays")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{100, -20, -30};
        int[] elems2 = new int[]{100, 100, 90};
        //added for tests

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setB.add(elems));
        assertThrows(IllegalArgumentException.class, () -> setB.add(elems2));
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems2));
    }

    @DisplayName("Test Intersection")
    @Test
    public void testIntersectionOfTwoBoundedSets(){
        BoundedSetOfNaturals setC_clone = BoundedSetOfNaturals.fromArray(new int[]{50, 60});

        assertTrue(setB.intersects(setC));
        assertFalse(setA.intersects(setB));
        assertTrue(setB.intersects(setB));
        assertTrue(setC.intersects(setC_clone));
    }

    @Test
    void testEqualsAndHashCode() {
        BoundedSetOfNaturals setX = BoundedSetOfNaturals.fromArray(new int[]{1, 2, 3});
        BoundedSetOfNaturals setY = BoundedSetOfNaturals.fromArray(new int[]{1, 2, 3});
        BoundedSetOfNaturals setZ = BoundedSetOfNaturals.fromArray(new int[]{3, 4, 5});

        assertEquals(setX, setY);
        assertNotEquals(setX, setZ);
        assertEquals(setX.hashCode(), setY.hashCode());
    }

}
