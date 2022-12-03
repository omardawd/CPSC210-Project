package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventTest {
    private Event addToListing;
    private Date date;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        addToListing = new Event("Added My Fall Outfit! to the current listings");   // (1)
        date = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "Added My Fall Outfit! to the current listings",
                addToListing.toString());
    }
}
