package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ClothingItemTest {
    private ClothingItem testClothingItem;

    @BeforeEach
    void runBefore() {
        testClothingItem = new ClothingItem("Nike Shirt", 10, "Medium");
    }

    @Test
    void testConstructor(){
        assertEquals("Nike Shirt", testClothingItem.getItemName());
        assertEquals(10, testClothingItem.getItemPrice());
        assertEquals("Medium", testClothingItem.getItemSize());
    }

}