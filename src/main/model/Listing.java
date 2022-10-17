package model;

import java.util.ArrayList;
import java.util.List;

// Represents a listing having a title, author, and a list of clothingItems
public class Listing {

    private String listingTitle;
    private String listingAuthor;
    private List<ClothingItem> clothingItems;

    // REQUIRES: title and author are of nonzero length
    // EFFECTS: listingTitle is set to title, listingAuthor is set to author
    public Listing(String title, String author) {
        clothingItems = new ArrayList<>();
        listingTitle = title;
        listingAuthor = author;
    }

    // MODIFIES: this
    // EFFECTS: appends a clothingItem to the list of clothingItems
    public void addOneItemToOutfit(ClothingItem anItem) {
        clothingItems.add(anItem);
    }

    // MODIFIES: this
    // EFFECTS: appends a whole outfit to the clothingItems
    public void addAllItemsToOutfit(ClothingItem jacket, ClothingItem shirt, ClothingItem pants, ClothingItem shoes) {
        clothingItems.add(jacket);
        clothingItems.add(shirt);
        clothingItems.add(pants);
        clothingItems.add(shoes);
    }

    // MODIFIES: this
    // EFFECTS: removes an item from the clothingItems list
    public void removeOneItemFromOutfit(ClothingItem anItem) {
        clothingItems.remove(anItem);
    }

    public String getListingTitle() {
        return listingTitle;
    }

    public String getListingAuthor() {
        return listingAuthor;
    }

    public int getOutfitSize() {
        return clothingItems.size();
    }

    // EFFECTS: searches for a clothingItem in the list and returns it
    public ClothingItem getThisClothingItem(int itemID) {
        return clothingItems.get(itemID);
    }

    public void setListingTitle(String listingTitle) {
        this.listingTitle = listingTitle;
    }

    public void setAuthor(String author) {
        listingAuthor = author;
    }

    public void setListingTitleAuthor(String listingTitle, String author) {
        this.listingTitle = listingTitle;
        listingAuthor = author;
    }

}
