package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {
    private Event e1;
    private Event e2;
    private Store store;
    private Listing listing;
    private ClothingItem clothingItem;

    @BeforeEach
    public void runBefore() {
        store = new Store("Store tester");
        listing = new Listing("Listing tester", "someone");
        clothingItem = new ClothingItem("shirt", 10, "small");
        e1 = new Event("Testing");
        e2 = new Event("Testing again");
        EventLog el = EventLog.getInstance();
        el.clear();
        el.logEvent(e1);
        el.logEvent(e2);
    }

    @Test
    public void testLogEvent() {

        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();
        for (Event next : el) {
            l.add(next);
        }

        assertTrue(l.contains(e1));
        assertTrue(l.contains(e2));
        assertEquals(l.get(1).getDescription(), "Testing");
        assertEquals(l.get(2).getDescription(), "Testing again");
    }

    @Test
    public void testLogEventAddItemToListing() {

        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();
        listing.addOneItemToOutfit(clothingItem);
        for (Event next : el) {
            l.add(next);
        }

        assertEquals(l.get(3).getDescription(), "Added a clothing item to Listing tester listing");
    }

    @Test
    public void testLogEventAddListingToStoreListings() {

        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();
        store.addListingToListingsInStore(listing);
        for (Event next : el) {
            l.add(next);
        }

        assertEquals(l.get(3).getDescription(), "Added Listing tester listing to the current listings");
    }

    @Test
    public void testLogEventAddListingToStoreFavoriteListings() {

        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();
        store.addListingToFavoritesInStore(listing);
        for (Event next : el) {
            l.add(next);
        }

        assertEquals(l.get(3).getDescription(), "Added Listing tester listing to the favorite listings");
    }

    @Test
    public void testLogEventRemoveListingFromStoreFavoriteListings() {

        List<Event> l = new ArrayList<Event>();

        EventLog el = EventLog.getInstance();
        store.addListingToFavoritesInStore(listing);
        store.removeListingFromFavoritesInStore(listing);
        for (Event next : el) {
            l.add(next);
        }

        assertEquals(l.get(4).getDescription(), "Removed Listing tester listing from favorite listings");
    }



    @Test
    public void testClear() {
        EventLog el = EventLog.getInstance();
        el.clear();
        Iterator<Event> itr = el.iterator();
        assertTrue(itr.hasNext());   // After log is cleared, the clear log event is added
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}
