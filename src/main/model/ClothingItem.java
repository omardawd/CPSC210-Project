package model;

// Represents a clothing item having a brief description, a price, and a size
public class ClothingItem {

    private String itemName;
    private int itemPrice;
    private String itemSize;

    /*
    * REQUIRES: itemName has a non-zero length, itemPrice > 0, itemSize has a non-zero length
    * MODIFIES: itemDescription is set to itemName, price is set to itemPrice, size is set to itemSize
    *           if price >= 0 then itemPrice is set to price, otherwise itemPrice is zero.
     */
    public ClothingItem(String itemDescription, int price, String size) {
        itemName = itemDescription;
        itemSize = size;
        if (price >= 0) {
            itemPrice = price;
        } else {
            itemPrice = 0;
        }
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemSize(String itemSize) {
        this.itemSize = itemSize;
    }
}
