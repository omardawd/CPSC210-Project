package persistence;

import model.ClothingItem;
import model.Listing;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkListing(String title, String author, Listing listing) {
        assertEquals(title, listing.getListingTitle());
        assertEquals(author, listing.getListingAuthor());
    }
}
