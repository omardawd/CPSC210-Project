package persistence;

import model.ClothingItem;
import model.Listing;
import model.Store;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Store store = new Store("My Store");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Store store = new Store("My Store");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(store);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            store = reader.read();
            assertEquals("My Store", store.getStoreName());
            assertEquals(0, store.getFavoritesSize());
            assertEquals(0, store.getListingsSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Store store = new Store("My Store");
            store.addListingToListingsInStore(new Listing("Some Listing", "Omar"));
            store.addListingToFavoritesInStore(new Listing("Some Favorite", "Omar"));

            store.addThisItemToThisListingInFavorites(0,
                    new ClothingItem("Jacket", 10, "Small"));
            store.addThisItemToThisListingInListings(0,
                    new ClothingItem("Jacket", 10, "Small"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(store);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            store = reader.read();
            assertEquals("My Store", store.getStoreName());
            List<Listing> listings = store.getListings();
            List<Listing> favorites = store.getFavorites();
            assertEquals(1, listings.size());
            assertEquals(1, favorites.size());

            checkListing("Some Listing", "Omar", listings.get(0));
            checkListing("Some Favorite", "Omar", favorites.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
