package ui;

import model.Listing;
import model.ClothingItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Thrift Store Application
public class ThriftStoreApp {

    private Scanner input;
    private List<Listing> listings;
    private List<Listing> favorites;

    // EFFECTS: runs the thrift store app
    public ThriftStoreApp() {
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
        listings = new ArrayList<>();
        favorites = new ArrayList<>();

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

        listings.add(listing0);
        listings.add(listing1);
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void useCommand(String command) {
        if (command.equals("s")) {
            printAllListings();
            handleListingCommand();
        } else if (command.equals("f")) {
            viewFavorites();
            handleFavoriteCommand();
        } else {
            System.out.println("Invalid Selection! Please try again");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to the control center, choose from the options below");
        System.out.println("\t s -> View listings");
        System.out.println("\t f -> View your favorite listings");
        System.out.println("\t x -> Exit");
    }

    //EFFECTS: displays all the listings available to user
    private void printAllListings() {
        System.out.println("Check those out! Choose a listing to get a closer look \n");

        for (int i = 0; i < listings.size(); i++) {
            Listing oneListing = listings.get(i);

            if (oneListing != null) {
                System.out.println(i + " - \t"
                        + oneListing.getListingTitle() + " By " + oneListing.getListingAuthor());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command when in the listings menu
    public void handleListingCommand() {
        String listingID = getUserCommand();

        for (int i = 0; i < listings.size(); i++) {
            Listing oneListing = listings.get(i);
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
        System.out.println("\nTo add this listing to your favorites enter 'e' or "
                + "enter 'x' to go back to control center");
    }

    // REQUIRES: favorites does not contain selected listing
    // MODIFIES: this
    // EFFECTS: appends selected listing to favorites
    public void addToFavoriteCommand(Listing selectedListing) {
        String command = getUserCommand();

        if (favorites.contains(selectedListing)) {
            System.out.println("This listing is already in your favorites");
        } else if (command.equals("e")) {
            favorites.add(selectedListing);
            System.out.println("Listing has been added to your favorites!");
        } else if (command.equals("x")) {
            displayMenu();
            useCommand(command);
        }
    }

    // EFFECTS: displays the listings in favorites to the user
    private void viewFavorites() {
        System.out.println("Welcome to your favorite listings\n");

        for (int i = 0; i < favorites.size(); i++) {
            Listing oneFavorite = favorites.get(i);

            if (oneFavorite != null) {
                System.out.println(i + " - \t"
                        + oneFavorite.getListingTitle() + " By " + oneFavorite.getListingAuthor());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    public void handleFavoriteCommand() {
        String favoriteID = getUserCommand();

        for (int i = 0; i < favorites.size(); i++) {
            Listing favListing = listings.get(i);
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
}
