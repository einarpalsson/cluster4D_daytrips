import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class DayTripLookupTest {
    static DayTripMockUp[] fakeDayTrips = {
        new DayTripMockUp("Flugferðin mikla", "Hafnarfjörður", 1, 500),
        new DayTripMockUp("Klifrið hand Bjarna", "Akureyri", 5, 10),
        new DayTripMockUp("Sálfræðimeðferð Döbbu", "Akureyri", 5, 10000),
        new DayTripMockUp("Nördaheimsókn hjá Einari", "Akureyri", 3, 589),
        new DayTripMockUp("Tölvuferð", "Reykjavík", 2, 5000)
    };

    static DayTripMockUp[] expectedDT = {
        new DayTripMockUp("Klifrið hand Bjarna", "Akureyri", 5, 10),
        new DayTripMockUp("Sálfræðimeðferð Döbbu", "Akureyri", 5, 10000),
        new DayTripMockUp("Nördaheimsókn hjá Einari", "Akureyri", 3, 589),
    };

    // Before
    public void setUp() {

    }
    // After
    public void tearDown() {

    }

    public static DayTripMockUp[] getAllDayTripMockUps() {
        return fakeDayTrips;
    }

    // Test
    public static void testDayTripLookupAtAkureyri() {
        String akureyri = "Akureyri";
        DayTripMockUp[] daytrips = getAllDayTripMockUps();
        assertNotNull(daytrips);

        ArrayList<DayTripMockUp> withAkureyri = new ArrayList<>();

        for (DayTripMockUp daytrip: daytrips) {
            String dtLocation = daytrip.getLocation();
            if (dtLocation == akureyri) {
                withAkureyri.add(daytrip);
            }
        }
        assertNotNull(withAkureyri);
        
        withAkureyri.forEach(daytrip -> {
            assertTrue("gekk upp", daytrip.getLocation() == akureyri);
            // System.out.println(daytrip.getName() + " | " + daytrip.getLocation());
        });
    }
    // Test
    public void testDayTripLookupFailure() {

    }

    public static void main(String[] args) {
        testDayTripLookupAtAkureyri();
    }
}