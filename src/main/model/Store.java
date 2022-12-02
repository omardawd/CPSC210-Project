package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a store having a name, collection of listings, collections of favorited listings
public class Store implements Writable {
    private String storeName;
    private List<Listing> listings;
    private List<Listing> favorites;

    // REQUIRES: name of store is of nonzero length
    // EFFECTS: name is set to storeName
    public Store(String name) {
        storeName = name;
        listings = new ArrayList<>();
        favorites = new ArrayList<>();
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public List<Listing> getFavorites() {
        return favorites;
    }

    // MODIFIES: listings
    // EFFECTS: appends a listing to the listings in store
    public void addListingToListingsInStore(Listing oneListing) {
        listings.add(oneListing);
        EventLog.getInstance().logEvent(new Event("Added " + oneListing.getListingTitle() + " listing to the"
                + " current listings"));
    }

    // MODIFIES: favorites
    // EFFECTS: appends a listing to the favorites in store
    public void addListingToFavoritesInStore(Listing oneListing) {
        favorites.add(oneListing);
        EventLog.getInstance().logEvent(new Event("Added " + oneListing.getListingTitle() + " listing to the"
                + " favorite listings"));
    }

    // MODIFIES: listings
    // EFFECTS: removes a listing from the listings in store
    public void removeListingFromStore(Listing oneListing) {
        if (listings.contains(oneListing)) {
            listings.remove(oneListing);
            EventLog.getInstance().logEvent(new Event("Removed " + oneListing.getListingTitle() + " listing "
                    + "from current listings"));
        }
    }

    // MODIFIES: listings
    // EFFECTS: removes a listing from the favorites in store
    public void removeListingFromFavoritesInStore(Listing oneListing) {
        if (favorites.contains(oneListing)) {
            favorites.remove(oneListing);
            EventLog.getInstance().logEvent(new Event("Removed " + oneListing.getListingTitle() + " listing "
                    + "from favorite listings"));
        }
    }

    public int getListingsSize() {
        return listings.size();
    }

    public int getFavoritesSize() {
        return favorites.size();
    }

    public Listing getListingInPositionOf(int position) {
        return listings.get(position);
    }

    public Listing getFavoritesInPositionOf(int position) {
        return favorites.get(position);
    }

    // EFFECTS: returns true if the favorites contain the given listing
    public boolean doesFavoritesContain(Listing oneListing) {
        return favorites.contains(oneListing);
    }

    // MODIFIES: listings
    // EFFECTS: add given clothing item to the listing in the position of listings
    public void addThisItemToThisListingInListings(int listingID, ClothingItem cl) {
        Listing thisListing = listings.get(listingID);
        thisListing.addOneItemToOutfit(cl);
        EventLog.getInstance().logEvent(new Event("Added " + cl.getItemName() + " item to "
                + listings.get(listingID).getListingTitle() + " listing in current listings"));
    }

    // MODIFIES: favorites
    // EFFECTS: add given clothing item to the listing in the position of favorites
    public void addThisItemToThisListingInFavorites(int listingID, ClothingItem cl) {
        Listing thisListing = favorites.get(listingID);
        thisListing.addOneItemToOutfit(cl);
        EventLog.getInstance().logEvent(new Event("Added " + cl.getItemName() + " item to "
                + favorites.get(listingID).getListingTitle() + " listing in favorite listings"));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Store Name", storeName);
        json.put("Listings", listingsToJson());
        json.put("Favorites", favoritesToJson());
        return json;
    }

    public JSONArray listingsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Listing listing : listings) {
            jsonArray.put(listing.toJson());
        }

        return jsonArray;
    }

    public JSONArray favoritesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Listing listing : favorites) {
            jsonArray.put(listing.toJson());
        }

        return jsonArray;
    }
}
