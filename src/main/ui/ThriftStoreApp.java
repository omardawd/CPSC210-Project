package ui;

import model.Listing;
import model.ClothingItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Store;
import persistence.JsonReader;
import persistence.JsonWriter;

// Thrift Store Application
public class ThriftStoreApp {
    private static final String JSON_STORE = "./data/store.json";
    private Scanner input;
    private Store store;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the thrift store app
    public ThriftStoreApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        launchStore();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void launchStore() {
        input = new Scanner(System.in);
        boolean resume = true;

        init();
        String command;

        while (resume) {
            displayMenu();
            command = getUserCommand();

            if (command.equals("x")) {
                resume = false;
            } else {
                useCommand(command);
            }
        }

        System.out.println("Thanks for visiting the Gallery Thrift Store!");
    }

    // MODIFIES: this
    // EFFECTS: initializes listings with clothing items
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        Listing listing0 = new Listing("My Fall Outfit!", "Omar Dawoud");
        Listing listing1 = new Listing("The Daily Commuter", "Omar Dawoud");
        store = new Store("Gallery Thrift Store");

        ClothingItem jacket = new ClothingItem("Nike Jacket", 25, "Small");
        ClothingItem shirt = new ClothingItem("A&F Shirt", 12, "X-Small");
        ClothingItem pants = new ClothingItem("Dickies 874", 40, "29x30");
        ClothingItem shoes = new ClothingItem("White converse", 100, "10");

        ClothingItem jacket1 = new ClothingItem("Stussy Jacket", 30, "Medium");
        ClothingItem shirt1 = new ClothingItem("Palace Shirt", 20, "Small");
        ClothingItem pants1 = new ClothingItem("Polo Corduroy Pants", 40, "32x30");
        ClothingItem shoes1 = new ClothingItem("White Bapestas", 100, "11");

        listing0.addAllItemsToOutfit(jacket, shirt, pants, shoes);
        listing1.addAllItemsToOutfit(jacket1, shirt1, pants1, shoes1);

        store.addListingToListingsInStore(listing0);
        store.addListingToListingsInStore(listing1);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void useCommand(String command) {
        if (command.equals("s")) {
            printAllListings();
            handleListingCommand();
        } else if (command.equals("a")) {
            addListing();
        } else if (command.equals("f")) {
            viewFavorites();
            handleFavoriteCommand();
        } else if (command.equals("k")) {
            saveStore();
        } else if (command.equals("l")) {
            loadStore();
        } else {
            System.out.println("Invalid Selection! Please try again");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to the control center, choose from the options below");
        System.out.println("\t s -> View listings");
        System.out.println("\t a -> Add a listing");
        System.out.println("\t f -> View your favorite listings");
        System.out.println("\t k -> Save your current progress");
        System.out.println("\t l -> Load your past progress");
        System.out.println("\t x -> Exit");
    }

    //EFFECTS: displays all the listings available to user
    private void printAllListings() {
        System.out.println("Check those out! Choose a listing to get a closer look \n");

        int i = 0;
        for (Listing oneListing : store.getListings()) {
            System.out.println(i + " - \t"
                    + oneListing.getListingTitle() + " By " + oneListing.getListingAuthor());
            i++;
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command when in the listings menu
    public void handleListingCommand() {
        String listingID = getUserCommand();

        for (int i = 0; i < store.getListingsSize(); i++) {
            Listing oneListing = store.getListingInPositionOf(i);
            if (Integer.parseInt(listingID) == i) {
                printSelectedListing(oneListing);
                addToFavoriteCommand(oneListing);
            }
        }
    }

    // EFFECTS: displays the data of the listing selected by the user
    private void printSelectedListing(Listing selectedListing) {
        System.out.println(selectedListing.getListingTitle() + " By " + selectedListing.getListingAuthor() + "\n");
        for (int i = 0; i < selectedListing.getOutfitSize(); i++) {
            ClothingItem oneItem = selectedListing.getThisClothingItem(i);

            if (oneItem != null) {
                System.out.println(i + "\t - \t" + oneItem.getItemName() + "|\tSize: " + oneItem.getItemSize()
                        + "|\tPrice $" + oneItem.getItemPrice());
            }
        }
        System.out.println("\nTo add this listing to your favorites enter 'e', to remove this listing from favorites"
                + " enter 'r' or enter 'x' to go back to control center");
    }

    // MODIFIES: this
    // EFFECTS: adds a listing from user to the listings in the store
    public void addListing() {
        System.out.println("Give your listing a name, be creative!");
        String listingTitle = input.next();
        System.out.println("Who is this listing by?(enter first and last name)");
        String listingAuthor = input.next();
        Listing userListing = new Listing(listingTitle, listingAuthor);
        addOutfitToListing(userListing);
    }


    // MODIFIES: this
    // adds an outfit to the listing the user is creating
    public void addOutfitToListing(Listing userListing) {
        System.out.println("Now its time to add your outfit!");
        System.out.println("How many items are in your outfit?");
        int outfitSize = Integer.parseInt(input.next());

        for (int i = 0; i < outfitSize; i++) {
            System.out.println("Please enter your item description(jacket, shirt, etc...)");
            String itemDescription = input.next();
            System.out.println("Please enter your item price");
            int price = Integer.parseInt(input.next());
            System.out.println("Enter your item's size(small, medium, X-Large, etc...)");
            String size = input.next();
            ClothingItem oneItem = new ClothingItem(itemDescription, price, size);
            userListing.addOneItemToOutfit(oneItem);
            System.out.println("Your item has been added");
        }
        System.out.println("Great! Now your listing is up");
        store.addListingToListingsInStore(userListing);
    }

    // REQUIRES: favorites does not contain selected listing
    // MODIFIES: this
    // EFFECTS: appends selected listing to favorites
    public void addToFavoriteCommand(Listing selectedListing) {
        String command = getUserCommand();

        if (command.equals("e")) {
            if (store.doesFavoritesContain(selectedListing)) {
                System.out.println("This listing is already in your favorites");
            } else {
                store.addListingToFavoritesInStore(selectedListing);
                System.out.println("Listing has been added from your favorites");
            }
        } else if (command.equals("x")) {
            displayMenu();
            String nextCommand = input.next();
            useCommand(nextCommand);
        } else if (command.equals("r")) {
            store.removeListingFromFavoritesInStore(selectedListing);
            System.out.println("Listing has been removed from your favorites");
        }
    }

    // EFFECTS: displays the listings in favorites to the user
    private void viewFavorites() {
        System.out.println("Welcome to your favorite listings\n");

        if (store.getFavoritesSize() < 1) {
            System.out.println("There's nothing in your favorites");
            displayMenu();
            String nextCommand = input.next();
            useCommand(nextCommand);
        } else {
            for (int i = 0; i < store.getFavoritesSize(); i++) {
                Listing oneFavorite = store.getFavoritesInPositionOf(i);

                if (oneFavorite != null) {
                    System.out.println(i + " - \t"
                            + oneFavorite.getListingTitle() + " By " + oneFavorite.getListingAuthor());
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    public void handleFavoriteCommand() {
        String favoriteID = getUserCommand();

        for (int i = 0; i < store.getFavoritesSize(); i++) {
            Listing favListing = store.getFavoritesInPositionOf(i);
            if (Integer.parseInt(favoriteID) == i) {
                printSelectedListing(favListing);
                addToFavoriteCommand(favListing);
            }
        }
    }

    // EFFECTS: returns the input given by the user
    public String getUserCommand() {
        String command = "";
        if (input.hasNext()) {
            command = input.nextLine();
        }
        return command;
    }

    // EFFECTS: saves the workroom to file
    private void saveStore() {
        try {
            jsonWriter.open();
            jsonWriter.write(store);
            jsonWriter.close();
            System.out.println("Saved " + store.getStoreName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadStore() {
        try {
            store = jsonReader.read();
            System.out.println("Loaded " + store.getStoreName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
