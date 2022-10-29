package persistence;

import model.ClothingItem;
import model.Listing;
import model.Store;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

// Represents a reader that reads Store from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads store from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Store read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStore(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Store from JSON object and returns it
    private Store parseStore(JSONObject jsonObject) {
        String title = jsonObject.getString("Store Name");
        Store store = new Store(title);
        addFavorites(store, jsonObject);
        addListings(store, jsonObject);
        return store;
    }

    // MODIFIES: store
    // EFFECTS: parses favorites from JSON object and adds them to store
    public void addFavorites(Store store, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Favorites");
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Listing listing = readListing(jsonObject);
            store.addListingToFavoritesInStore(listing);
            //for loop with call to read recipe and that recipe using addfavorites
            // don't need addFav for this or edited
        }
    }

    // MODIFIES: store
    // EFFECTS: parses listings from JSON object and adds them to store
    private void addListings(Store store, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Listings");
        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            Listing listing = readListing(jsonObject);
            store.addListingToListingsInStore(listing);
        }
    }

    //MODIFIES: This
    //EFFECTS: creates new listings object with jsonObject parameters
    private Listing readListing(JSONObject jsonObject) {
        String name = jsonObject.getString("title");
        String author = jsonObject.getString("author");

        ArrayList<ClothingItem> outfit = readOutfit(jsonObject.getJSONArray("Outfit"));

        Listing oneListing = new Listing(name, author);
        return oneListing;
    }

    //EFFECTS: loops through each element in JSONArray and converts it into an object
    private ArrayList<ClothingItem> readOutfit(JSONArray jsonArray) {
        ArrayList<ClothingItem> outfit = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            outfit.add(readClothingItem(jsonObject));
        }
        return outfit;
    }

    //MODIFIES: This
    //EFFECTS: Creates new clothing item with JsonObject Clothing description, price, and size and returns it
    private ClothingItem readClothingItem(JSONObject jsonObject) {
        String itemName = jsonObject.getString("item description");
        Integer price = jsonObject.getInt("price");
        String size = jsonObject.getString("size");
        ClothingItem cl = new ClothingItem(itemName, price, size);
        return cl;
    }


}