import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class DayTripMockUp {

    public ArrayList<DT> getDaytrips(String query) {

        if (query.isEmpty()) {
            return new ArrayList<DT>();
        }

        DT[] dt = {
            new DT("Jöklaferð", LocalDate.of(2022, 5, 1), LocalDateTime.of(2022, 5, 1, 9, 0), LocalDateTime.of(2022, 5, 1, 12, 0), 3, "Jöklaferð uppá Vatnajökul", 10, 5000, 1, "Vík í Mýrdal", 10),
            new DT("Hestatúr", LocalDate.of(2022, 7, 15), LocalDateTime.of(2022, 7, 11, 12, 0), LocalDateTime.of(2022, 7, 11, 17, 0), 1, "5 klst reiðferð um sveitina", 10, 7000, 1,"Vík í Mýrdal", 15),
            new DT("Buggy ferð", LocalDate.of(2022, 10, 10), LocalDateTime.of(2022, 7, 11, 12, 0), LocalDateTime.of(2022, 10, 10, 17, 0), 1, "3 klst buggy ferð", 17, 6000, 1,"Vík í Mýrdal", 5),
        };
        
        return new ArrayList<DT>(Arrays.asList(dt));
    }
}