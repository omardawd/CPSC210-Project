package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListingTest {

    private Listing listingTest;
    private ClothingItem jacketForOutfit;
    private ClothingItem shirtForOutfit;
    private ClothingItem pantsForOutfit;
    private ClothingItem shoesForOutfit;

    @BeforeEach
    void runBefore() {
        listingTest = new Listing("This is a Listing", "Omar Dawoud");
        jacketForOutfit = new ClothingItem("Jacket", 10, "Small");
        shirtForOutfit = new ClothingItem("Shirt", 15, "Medium");
        pantsForOutfit = new ClothingItem("pants", 20, "Small");
        shoesForOutfit = new ClothingItem("shoes", 50, "10.5");
    }

    @Test
    public void constructorTest() {
        assertEquals("This is a Listing", listingTest.getListingTitle());
        assertEquals("Omar Dawoud", listingTest.getListingAuthor());
        assertEquals("This is a Listing BY Omar Dawoud", listingTest.toString());
    }

    @Test
    public void addOneItemToOutfitTest() {
        assertEquals(0, listingTest.getOutfitSize());
        listingTest.addOneItemToOutfit(jacketForOutfit);
        assertEquals(1, listingTest.getOutfitSize());
        assertEquals(jacketForOutfit, listingTest.getThisClothingItem(0));
    }

    @Test
    public void addOneItemToOutfitMultipleTimesTest() {
        assertEquals(0, listingTest.getOutfitSize());
        listingTest.addOneItemToOutfit(jacketForOutfit);
        assertEquals(1, listingTest.getOutfitSize());
        assertEquals(jacketForOutfit, listingTest.getThisClothingItem(0));
        listingTest.addOneItemToOutfit(shirtForOutfit);
        assertEquals(2, listingTest.getOutfitSize());
        assertEquals(shirtForOutfit, listingTest.getThisClothingItem(1));
    }

    @Test
    public void addAllItemsToOutfitTest() {
        assertEquals(0, listingTest.getOutfitSize());
        listingTest.addAllItemsToOutfit(jacketForOutfit, shirtForOutfit, pantsForOutfit, shoesForOutfit);
        assertEquals(4, listingTest.getOutfitSize());
        assertEquals(jacketForOutfit, listingTest.getThisClothingItem(0));
        assertEquals(shirtForOutfit, listingTest.getThisClothingItem(1));
        assertEquals(pantsForOutfit, listingTest.getThisClothingItem(2));
        assertEquals(shoesForOutfit, listingTest.getThisClothingItem(3));
    }

    @Test
    public void addAllItemsToOutfitMultipleTimesTest() {
        assertEquals(0, listingTest.getOutfitSize());
        listingTest.addAllItemsToOutfit(jacketForOutfit, shirtForOutfit, pantsForOutfit, shoesForOutfit);
        assertEquals(4, listingTest.getOutfitSize());
        assertEquals(jacketForOutfit, listingTest.getThisClothingItem(0));
        assertEquals(shirtForOutfit, listingTest.getThisClothingItem(1));
        assertEquals(pantsForOutfit, listingTest.getThisClothingItem(2));
        assertEquals(shoesForOutfit, listingTest.getThisClothingItem(3));

        listingTest.addAllItemsToOutfit(jacketForOutfit, shirtForOutfit, pantsForOutfit, shoesForOutfit);
        assertEquals(8, listingTest.getOutfitSize());
        assertEquals(jacketForOutfit, listingTest.getThisClothingItem(4));
        assertEquals(shirtForOutfit, listingTest.getThisClothingItem(5));
        assertEquals(pantsForOutfit, listingTest.getThisClothingItem(6));
        assertEquals(shoesForOutfit, listingTest.getThisClothingItem(7));
    }

    @Test
    public void removeOneItemFromOutfitTest() {
        listingTest.addOneItemToOutfit(jacketForOutfit);
        assertEquals(1, listingTest.getOutfitSize());
        listingTest.removeOneItemFromOutfit(jacketForOutfit);
        assertEquals(0, listingTest.getOutfitSize());
    }

    @Test
    public void removeOneItemFromOutfitMultipleTimesTest() {
        listingTest.addOneItemToOutfit(jacketForOutfit);
        listingTest.addOneItemToOutfit(shirtForOutfit);
        assertEquals(2, listingTest.getOutfitSize());
        listingTest.removeOneItemFromOutfit(jacketForOutfit);
        assertEquals(1, listingTest.getOutfitSize());
        assertEquals(shirtForOutfit, listingTest.getThisClothingItem(0));

        listingTest.removeOneItemFromOutfit(shirtForOutfit);
        assertEquals(0, listingTest.getOutfitSize());
    }

    @Test
    public void testSetters() {
        listingTest.setListingTitle("Omar's Listing");
        assertEquals("Omar's Listing", listingTest.getListingTitle());
        listingTest.setAuthor("The Author");
        assertEquals("The Author", listingTest.getListingAuthor());
        listingTest.setListingTitleAuthor("title", "author");
        assertEquals("author", listingTest.getListingAuthor());
        assertEquals("title", listingTest.getListingTitle());
    }

    @Test
    public void getOutfitTest() {
        listingTest.addAllItemsToOutfit(jacketForOutfit, shirtForOutfit, pantsForOutfit, shoesForOutfit);
        assertEquals(4, listingTest.getOutfitSize());
        ArrayList<ClothingItem> outfit = new
                ArrayList<>(Arrays.asList(jacketForOutfit, shirtForOutfit, pantsForOutfit, shoesForOutfit));

        assertEquals(outfit, listingTest.getOutfit());
    }
}
