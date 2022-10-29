package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a listing having a title, author, and a list of clothingItems
public class Listing implements Writable {

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

    // EFFECTS: returns the list of outfit in a listing
    public List<ClothingItem> getOutfit() {
        return clothingItems;
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", getListingTitle());
        json.put("author", getListingAuthor());
        json.put("Outfit", clothingItemsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray clothingItemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ClothingItem cl : clothingItems) {
            jsonArray.put(cl.toJson());
        }

        return jsonArray;
    }

}
