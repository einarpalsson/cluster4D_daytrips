import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;


public class DayTripLookupTest {
    private static DTController controller = new DTController();

    @Test
    public static void testDayTripLookupAtVik() {
        Hashtable<String, String> params = new Hashtable<>();
        String vik = "Vík í Mýrdal";
        params.put("location", vik);

        ArrayList<DT> daytrips = controller.getDaytrips(params);
        
        assertEquals(3, daytrips.size());
    }

    @Test
    public static void testDayTripLookupFailure() {
        Hashtable<String, String> params = new Hashtable<>();
        String vik = "Bolungarvík";
        params.put("location", vik);

        ArrayList<DT> daytrips = controller.getDaytrips(params);
        
        assertEquals(0, daytrips.size());
    }

    public static void main(String[] args) {
        testDayTripLookupAtVik();
        testDayTripLookupFailure();
    }
}