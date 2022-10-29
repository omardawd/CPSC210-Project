package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ClothingItemTest {
    private ClothingItem testClothingItem;
    private ClothingItem testBadPriceClothingItem;

    @BeforeEach
    void runBefore() {
        testClothingItem = new ClothingItem("Nike Shirt", 10, "Medium");
        testBadPriceClothingItem = new ClothingItem("Jacket", -10, "Small");
    }

    @Test
    void testConstructor(){
        assertEquals("Nike Shirt", testClothingItem.getItemName());
        assertEquals(10, testClothingItem.getItemPrice());
        assertEquals(0, testBadPriceClothingItem.getItemPrice());
        assertEquals("Medium", testClothingItem.getItemSize());
    }

    @Test
    void testSetters() {
        testClothingItem.setItemPrice(200);
        assertEquals(200, testClothingItem.getItemPrice());
        testClothingItem.setItemName("Shirt");
        assertEquals("Shirt", testClothingItem.getItemName());
        testClothingItem.setItemSize("Large");
        assertEquals("Large", testClothingItem.getItemSize());

    }


}