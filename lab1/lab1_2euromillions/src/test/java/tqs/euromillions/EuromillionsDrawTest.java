package tqs.euromillions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.euromillions.CuponEuromillions;
import tqs.euromillions.Dip;
import tqs.euromillions.EuromillionsDraw;

public class EuromillionsDrawTest {

    private CuponEuromillions sampleCoupon;

    @BeforeEach
    public void setUp()  {
        sampleCoupon = new CuponEuromillions();
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(Dip.generateRandomDip());
        sampleCoupon.appendDip(new Dip(new int[]{1, 2, 3, 48, 49}, new int[]{1, 9}));
    }


    @DisplayName("reports correct matches in a coupon")
    @Test
    public void testCompareBetWithDrawToGetResults() {
        Dip winningDip, matchesFound;

        // test for full match, using the 3rd dip in the coupon as the Draw results
        winningDip = sampleCoupon.getDipByIndex(2);
        EuromillionsDraw testDraw = new EuromillionsDraw(winningDip);
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);

        assertEquals(winningDip, matchesFound, "expected the bet and the matches found to be equal");

        // test for no matches at all
        testDraw = new EuromillionsDraw(new Dip(new int[]{9, 10, 11, 12, 13}, new int[]{2, 3}));
        matchesFound = testDraw.findMatchesFor(sampleCoupon).get(2);
        // compare empty with the matches found
        assertEquals( new Dip(), matchesFound);
    }

    @DisplayName("Test getDrawResults")
    @Test
    void testGetDrawResults() {
        Dip expectedDip = new Dip();
        expectedDip.getNumbersColl().add(1);
        expectedDip.getNumbersColl().add(2);
        expectedDip.getNumbersColl().add(3);
        expectedDip.getNumbersColl().add(4);
        expectedDip.getNumbersColl().add(5);

        expectedDip.getStarsColl().add(1);
        expectedDip.getStarsColl().add(2);

        // Create a EuromillionsDraw with the expected Dip
        EuromillionsDraw draw = new EuromillionsDraw(expectedDip);

        // Verify the draw results match the expected Dip
        assertEquals(expectedDip, draw.getDrawResults());
    }

}
