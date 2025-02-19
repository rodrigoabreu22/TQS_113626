package tqs.euromillions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class CuponEuromillionsTest {

    private CuponEuromillions cupon;
    private Dip dip1;
    private Dip dip2;

    @BeforeEach
    void setUp() {
        cupon = new CuponEuromillions();
        dip1 = new Dip(); 
        dip2 = new Dip();
    }

    @Test
    void testAppendDip() {
        cupon.appendDip(dip1);
        assertEquals(1, cupon.countDips());
    }

    @Test
    void testCountDips() {
        assertEquals(0, cupon.countDips());
        cupon.appendDip(dip1);
        cupon.appendDip(dip2);
        assertEquals(2, cupon.countDips());
    }

    @Test
    void testGetDipByIndex() {
        cupon.appendDip(dip1);
        cupon.appendDip(dip2);
        assertSame(dip1, cupon.getDipByIndex(0));
        assertSame(dip2, cupon.getDipByIndex(1));
    }

    @Test
    void testFormat() {
        cupon.appendDip(dip1);
        cupon.appendDip(dip2);
        
        String expectedOutput = "Dip #1:" + dip1.format() + "\n" + "Dip #2:" + dip2.format() + "\n";
        assertEquals(expectedOutput, cupon.format());
    }

    @Test
    void testIterator() {
        cupon.appendDip(dip1);
        cupon.appendDip(dip2);
        
        Iterator<Dip> iterator = cupon.iterator();
        assertTrue(iterator.hasNext());
        assertSame(dip1, iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(dip2, iterator.next());
        assertFalse(iterator.hasNext());
    }
}

