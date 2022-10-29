package persistence;

import model.Listing;
import model.Store;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Store store = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            Store store = reader.read();
            assertEquals("My Store", store.getStoreName());
            assertEquals(0, store.getFavoritesSize());
            assertEquals(0, store.getListingsSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            Store store = reader.read();
            assertEquals("Omar's Store", store.getStoreName());
            List<Listing> listings = store.getListings();
            List<Listing> favorites = store.getFavorites();

            assertEquals(1, store.getListingsSize());
            assertEquals(1, store.getFavoritesSize());

            Listing oneListing = store.getListingInPositionOf(0);
            Listing favListing = store.getFavoritesInPositionOf(0);

            checkListing("The Test Listing", "Omar Dawoud", oneListing);
            checkListing("The Test Listing", "Omar Dawoud", favListing);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
