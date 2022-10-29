package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {

    private Store storeTest;
    private Listing listingTest1;
    private Listing listingTest2;


    @BeforeEach
    void runBefore() {
        storeTest = new Store("Gallery Thrift Store");
        listingTest1 = new Listing("A Listing", "An Author");
        listingTest2 = new Listing("Another Listing", "Another Author");
        storeTest.addListingToListingsInStore(listingTest1);
        storeTest.addListingToListingsInStore(listingTest2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Gallery Thrift Store", storeTest.getStoreName());
        assertEquals(0, storeTest.getFavoritesSize());
        assertEquals(2, storeTest.getListingsSize());
        assertEquals(listingTest1, storeTest.getListingInPositionOf(0));
        assertEquals(listingTest2, storeTest.getListingInPositionOf(1));
        storeTest.setStoreName("A New Store");
        assertEquals("A New Store", storeTest.getStoreName());
    }

    @Test
    public void testAdders() {
        storeTest.addListingToListingsInStore(listingTest2);
        assertEquals(3, storeTest.getListingsSize());
        assertEquals(listingTest2, storeTest.getListingInPositionOf(2));
        ArrayList<Listing> someListings = new ArrayList<>(Arrays.asList(listingTest1, listingTest2, listingTest2));
        assertEquals(someListings, storeTest.getListings());

        storeTest.addListingToFavoritesInStore(listingTest1);
        ArrayList<Listing> someFavorites = new ArrayList<>(Arrays.asList(listingTest1));
        assertEquals(1, storeTest.getFavoritesSize());
        assertEquals(listingTest1, storeTest.getFavoritesInPositionOf(0));
        assertEquals(someFavorites, storeTest.getFavorites());
    }

    @Test
    public void testRemovers() {
        storeTest.removeListingFromStore(listingTest2);
        assertEquals(1, storeTest.getListingsSize());
        assertEquals(listingTest1, storeTest.getListingInPositionOf(0));
        storeTest.removeListingFromStore(listingTest1);
        assertEquals(0, storeTest.getListingsSize());

        storeTest.addListingToFavoritesInStore(listingTest1);
        storeTest.addListingToFavoritesInStore(listingTest2);
        assertEquals(2, storeTest.getFavoritesSize());
        assertEquals(listingTest1, storeTest.getFavoritesInPositionOf(0));
        assertEquals(listingTest2, storeTest.getFavoritesInPositionOf(1));
        storeTest.removeListingFromFavoritesInStore(listingTest2);
        assertTrue(storeTest.doesFavoritesContain(listingTest1));
        assertFalse(storeTest.doesFavoritesContain(listingTest2));
        assertEquals(1, storeTest.getFavoritesSize());
    }





}
