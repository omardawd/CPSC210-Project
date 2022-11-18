package ui;

import model.ClothingItem;
import model.Listing;
import model.Store;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NewStoreGUI extends JFrame implements ActionListener {
    // Listings list and model
    private JList<Listing> listingsList = new JList<>();
    private DefaultListModel<Listing> listingModel = new DefaultListModel<>();
    // Favorites list and model
    private JList<Listing> favList = new JList<>();
    private DefaultListModel<Listing> favModel = new DefaultListModel<>();
    // Clothingitem list and model
    private JList<ClothingItem> itemList = new JList<>();
    private DefaultListModel<ClothingItem> itemModel = new DefaultListModel<>();
    // Panels
    private JPanel listingsPanel;
    private JPanel listingPanel;
    private JPanel favoritesPanel;
    private JPanel favListingPanel;
    // Control Center buttons
    private JButton addListingButton;
    private JButton saveButton;
    private JButton loadButton;
    // Buttons for listing page
    private JButton addToFavButton;
    private JButton removeFromFavButton;
    // Class fields
    private Store store;
    // Data Preservers
    private Listing createdListing;
    private ClothingItem createdClothingItem;
    // JSON
    private static final String JSON_STORE = "./data/store.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // MODIFIES: this
    // EFFECTS: initializes JFrame with all the panels
    public NewStoreGUI() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setSize(1000, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initStore(); // initialize store with listings
        initHeader(); // top of screen, has store sign
        initControlCenter(); // bottom side of screen, has buttons to use app
        initViewListingsPanel(); // left of the screen, displays current listings
        initViewFavoritesPanel(); // left of the screen, displays current favorite listings

        setVisible(true);
    }

    // MODIFIES: store
    // EFFECTS: initializes store with preset listings
    public void initStore() {
        store = new Store("Gallery Thrift Store");
        Listing listing0 = new Listing("My Fall Outfit!", "Omar Dawoud");
        Listing listing1 = new Listing("The Daily Commuter", "Omar Dawoud");

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
        // store listings
        store.addListingToListingsInStore(listing0);
        store.addListingToListingsInStore(listing1);
        // store favorites
        store.addListingToFavoritesInStore(listing0);
        store.addListingToFavoritesInStore(listing1);
    }

    // MODIFIES: this
    // EFFECTS: initializes header panel on the frame with the store name
    public void initHeader() {
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(Color.BLUE);
        JLabel storeSign = new JLabel("The Gallery Thrift Store By Omar");
        storeSign.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(storeSign);

        add(header, "North");
    }

    // MODIFIES: this
    // EFFECTS: initializes footer panel on the frame with control buttons
    public void initControlCenter() {
        // panel setup
        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(Color.lightGray);
        // button setup
        addListingButton = new JButton("Add a Listing");
        setupAddListingButton(addListingButton);
        saveButton = new JButton("Save Data");
        saveButton.addActionListener(this);
        loadButton = new JButton("Load Data");
        loadButton.addActionListener(this);
        // add to footer
        footer.add(addListingButton);
        footer.add(saveButton);
        footer.add(loadButton);
        // add to frame
        add(footer, "South");
    }

    // MODIFIES: addListingButton
    // EFFECTS: listens to click to pass onto the createAddListingWindowMethod to create a new listing
    public void setupAddListingButton(JButton addListingButton) {
        addListingButton.addActionListener(e -> {
            createAddListingWindow();
        });
    }

    // MODIFIES: this
    // EFFECTS: creates a new window JFrame where user can input their listing details
    public void createAddListingWindow() {
        JFrame createListingFrame = new JFrame();
        createListingFrame.setSize(300, 500);
        createListingFrame.setLayout(new FlowLayout());

        JLabel listingTitle = new JLabel("Enter Listing Name : ");
        JLabel listingAuthor = new JLabel("Enter Listing Author : ");

        JTextField listingTitleField = new JTextField();
        listingTitleField.setColumns(25);
        JTextField listingAuthorField = new JTextField();
        listingAuthorField.setColumns(25);

        JPanel listingInfoPanel = new JPanel();
        initListingInfoPanel(listingInfoPanel, listingTitle, listingTitleField, listingAuthor, listingAuthorField);
        createdListing = new Listing("Temp", "Temp");

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> createListingFrame.dispose());
        JButton createButton = setupCreateListingButton(createListingFrame, listingTitleField, listingAuthorField);

        createListingFrame.add(listingInfoPanel);
        createListingFrame.add(cancelButton);
        createListingFrame.add(createButton);
        createListingFrame.setVisible(true);
        createListingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // MODIFIES: panel
    // EFFECTS: method that adds labels and text fields to panel
    public void initListingInfoPanel(JPanel panel, JLabel title, JTextField titleField,
                                     JLabel author, JTextField authorField) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(title);
        panel.add(titleField);
        panel.add(author);
        panel.add(authorField);
    }

    // MODIFIES: this
    // EFFECTS: finalizes the listing name and author and opens the add items window
    public JButton setupCreateListingButton(JFrame createListingFrame, JTextField title, JTextField author) {
        JButton createListingButton = new JButton("Now Lets Add Your Items!");
        createListingButton.addActionListener(e -> {
            createdListing.setListingTitle(title.getText());
            createdListing.setAuthor(author.getText());
            createListingFrame.dispose();
            createAddItemsWindow(createdListing);
        });
        return createListingButton;
    }

    // MODIFIES: this, createdListing
    // EFFECTS: creates a new window JFrame and asks user to input their item's details
    public void createAddItemsWindow(Listing createdListing) {
        JFrame createItemFrame = new JFrame();
        createItemFrame.setSize(300, 500);
        createItemFrame.setLayout(new FlowLayout());

        JLabel itemName = new JLabel("What is your item?");
        JTextField itemNameField = new JTextField();
        itemNameField.setColumns(15);

        JLabel itemSize = new JLabel("What is your item size?");
        JTextField itemSizeField = new JTextField();
        itemSizeField.setColumns(15);

        JLabel itemPrice = new JLabel("What is your item price?");
        JTextField itemPriceField = new JTextField();
        itemPriceField.setColumns(15);

        JPanel itemInfoPanel = new JPanel();
        initItemInfoPanel(itemInfoPanel, itemName, itemNameField, itemSize, itemSizeField, itemPrice, itemPriceField);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> createItemFrame.dispose());
        JButton addItemToListingButton = setupAddItemButton(createItemFrame, itemNameField, itemSizeField,
                itemPriceField);

        createItemFrame.add(itemInfoPanel);
        createItemFrame.add(cancelButton);
        createItemFrame.add(addItemToListingButton);
        createItemFrame.setVisible(true);
        createItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: passes data onto the created listing and reiterates 3 more time to finalize the listing creation
    public JButton setupAddItemButton(JFrame createItemFrame, JTextField itemNameField, JTextField itemSizeField,
                                      JTextField itemPriceField) {
        JButton finalizeListingButton = new JButton("Finalize Listing");
        finalizeListingButton.addActionListener(e -> {
            createdClothingItem = new ClothingItem(itemNameField.getText(), Integer.parseInt(itemPriceField.getText()),
                    itemSizeField.getText());
            createdListing.addOneItemToOutfit(createdClothingItem);
            createItemFrame.dispose();
            if (createdListing.getOutfitSize() < 4) {
                createAddItemsWindow(createdListing);
            }
            if (!store.getListings().contains(createdListing)) {
                store.addListingToListingsInStore(createdListing);
                listingModel.addElement(createdListing);
            }
            revalidate();
            repaint();
        });
        return finalizeListingButton;
    }

    // MODIFIES: panel
    // EFFECTS: method that adds labels and text fields to panel
    public void initItemInfoPanel(JPanel panel, JLabel itemName, JTextField itemNameField, JLabel itemSize,
                                  JTextField itemSizeField, JLabel itemPrice, JTextField itemPriceField) {

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(itemName);
        panel.add(itemNameField);
        panel.add(itemSize);
        panel.add(itemSizeField);
        panel.add(itemPrice);
        panel.add(itemPriceField);
    }

    // MODIFIES: this
    // EFFECTS: builds the left panel that displays all the current listings
    public void initViewListingsPanel() {
        listingsList.setModel(listingModel); // initialize JList
        listingsPanel = new JPanel(); // panel for component

        for (Listing oneListing : store.getListings()) {
            listingModel.addElement(oneListing);
        }

        listingsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = listingsList.getSelectedIndex();
                    Listing oneListing = listingModel.elementAt(index);
                    System.out.println(oneListing.toString());
                    initListing(oneListing);
                    listingPanel.setVisible(true);
                    initBottomPanel();
                    listingPanel.updateUI();
                }
            }
        });

        continueInitViewListingsPanel(listingsPanel);
    }

    // MODIFIES: this
    // EFFECTS: method continuer for initViewListingPanel so it can pass the checkstyle
    public void continueInitViewListingsPanel(JPanel listingsPanel) {
        listingsPanel.setBackground(Color.cyan);
        JSplitPane splitter = new JSplitPane(); // divides center and left side
        JScrollPane scroll = new JScrollPane(listingsList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // scroll for the component
        listingsPanel.setLayout(new BoxLayout(listingsPanel, BoxLayout.Y_AXIS));
        listingsPanel.add(new JLabel("Listings"));
        scroll.setPreferredSize(new Dimension(250, 200));
        splitter.setLeftComponent(scroll);
        splitter.setRightComponent(null);
        listingsPanel.add(splitter);
        add(listingsPanel, "West");

        revalidate();
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates the center panel to display a selected listing from the listingsPanel
    public void initListing(Listing oneListing) {
        itemList.setModel(itemModel);
        // listingpanel initialization
        listingPanel = new JPanel();
        listingPanel.removeAll();
        // Listing title up top
        JLabel listingTitle = new JLabel(oneListing.getListingTitle() + " By " + oneListing.getListingAuthor());
        listingTitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        setTextPositionForLabel(listingTitle);
        // Listing Image below title
        ImageIcon listingIcon = new ImageIcon(new ImageIcon("./data/JamesFranco.jpeg")
                .getImage().getScaledInstance(300, 350, Image.SCALE_SMOOTH));
        listingTitle.setIcon(listingIcon);
        listingPanel.add(listingTitle);
        listingTitle.setVerticalAlignment(JLabel.TOP);
        listingTitle.setHorizontalAlignment(JLabel.CENTER);

        for (ClothingItem oneItem : oneListing.getOutfit()) {
            JLabel itemDescription = new JLabel();
            itemDescription.setIconTextGap(5);
            itemDescription.setText(oneItem.toString());
            itemDescription.setFont(new Font("SansSerif", Font.PLAIN, 15));
            listingPanel.add(itemDescription);
            itemModel.addElement(oneItem);
        }
        listingPanel.setBackground(Color.cyan);
        add(listingPanel);
    }

    // MODIFIES: listingTitle
    // EFFECTS: sets the text alignment for the listingTitle
    public void setTextPositionForLabel(JLabel listingTitle) {
        listingTitle.setHorizontalTextPosition(JLabel.CENTER);
        listingTitle.setVerticalTextPosition(JLabel.TOP);
    }

    // MODIFIES: this
    // EFFECTS: builds the left panel that displays all the current favorite listings
    public void initViewFavoritesPanel() {
        favList.setModel(favModel);
        favoritesPanel = new JPanel();

        for (Listing oneListing : store.getFavorites()) {
            favModel.addElement(oneListing);
        }

        favList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = favList.getSelectedIndex();
                    Listing oneListing = favModel.elementAt(index);
                    System.out.println(oneListing.toString());
                    initFavListing(oneListing);
                    favListingPanel.setVisible(true);
                    favListingPanel.updateUI();
                }
            }
        });
        continueViewFavoritesPanel(favoritesPanel);
    }

    // MODIFIES: this
    // EFFECTS: method continuer for initViewFavoritesPanel so it can pass the checkstyle
    public void continueViewFavoritesPanel(JPanel favoritesPanel) {
        favoritesPanel.setBackground(Color.yellow);
        JSplitPane splitter = new JSplitPane(); // divides center and left side
        JScrollPane scroll = new JScrollPane(favList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // scroll for the component
        favoritesPanel.setLayout(new BoxLayout(favoritesPanel, BoxLayout.Y_AXIS));
        favoritesPanel.add(new JLabel("Favorite Listings"));
        scroll.setPreferredSize(new Dimension(250, 200));
        splitter.setLeftComponent(scroll);
        splitter.setRightComponent(null);
        favoritesPanel.add(splitter);
        add(favoritesPanel, "East");
    }

    // MODIFIES: this
    // EFFECTS: creates the center panel to display a selected listing from the favoritesPanel
    public void initFavListing(Listing oneListing) {
        itemList.setModel(itemModel);
        // listingpanel initialization
        favListingPanel = new JPanel();
        favListingPanel.removeAll();
        // Listing title up top
        JLabel listingTitle = new JLabel(oneListing.getListingTitle() + " By " + oneListing.getListingAuthor());
        listingTitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        setTextPositionForLabel(listingTitle);
        // Listing Image below title
        ImageIcon listingIcon = new ImageIcon(new ImageIcon("./data/JamesFranco.jpeg")
                .getImage().getScaledInstance(300, 350, Image.SCALE_SMOOTH));
        listingTitle.setIcon(listingIcon);
        favListingPanel.add(listingTitle);
        listingTitle.setVerticalAlignment(JLabel.TOP);
        listingTitle.setHorizontalAlignment(JLabel.CENTER);

        for (ClothingItem oneItem : oneListing.getOutfit()) {
            JLabel itemDescription = new JLabel();
            itemDescription.setIconTextGap(5);
            itemDescription.setText(oneItem.toString());
            itemDescription.setFont(new Font("SansSerif", Font.PLAIN, 15));
            favListingPanel.add(itemDescription);
            itemModel.addElement(oneItem);
        }
        favListingPanel.setBackground(Color.yellow);
        add(favListingPanel);
    }

    // MODIFIES: this, listingPanel, favoritesPanel
    // EFFECTS: constructs the buttons to add or remove a listing from the favoritesPanel
    public void initBottomPanel() {
        JPanel bottomPanelInListing = new JPanel();
        bottomPanelInListing.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanelInListing.setBackground(Color.cyan);
        addToFavButton = new JButton("Add To Favorites");
        setupAddToFaveButton(addToFavButton);
        removeFromFavButton = new JButton("Remove From Favorites");
        setupRemoveButton(removeFromFavButton);
        bottomPanelInListing.add(addToFavButton);
        bottomPanelInListing.add(removeFromFavButton);
        listingPanel.add(bottomPanelInListing, "South");
        bottomPanelInListing.setVisible(true);
    }

    // MODIFIES: addToFavButton, favModel, store, favoritesPanel
    // EFFECTS: constructs the button to add a listing to favorites
    public void setupAddToFaveButton(JButton addToFavButton) {
        addToFavButton.addActionListener(e -> {
            if (listingsList.getSelectedIndex() != -1) {
                Listing oneListing = listingsList.getSelectedValue();
                favModel.addElement(oneListing);
                store.addListingToFavoritesInStore(oneListing);
                favoritesPanel.updateUI();
            }
        });
    }

    // MODIFIES: addToFavButton, favModel, store, favoritesPanel
    // EFFECTS: constructs the button to remove a listing from favorites
    public void setupRemoveButton(JButton removeFromFavButton) {
        removeFromFavButton.addActionListener(e -> {
            if (listingsList.getSelectedIndex() != -1) {
                Listing oneListing = listingsList.getSelectedValue();
                favModel.removeElement(oneListing);
                store.removeListingFromFavoritesInStore(oneListing);
                favoritesPanel.updateUI();
            }
        });
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
    // EFFECTS: loads store from file
    private void loadStore() {
        try {
            store = jsonReader.read();
            System.out.println("Loaded " + store.getStoreName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        initNewFavState();
        initNewListingsState();
    }

    // MODIFIES: this, favoritesPanel
    // EFFECTS: parses the new state of Favorites after choosing to load store
    public void initNewFavState() {
        favModel.removeAllElements();
        for (Listing oneListing : store.getFavorites()) {
            for (ClothingItem clothingItem : oneListing.getOutfit()) {
                itemModel.addElement(clothingItem);
            }
            favModel.addElement(oneListing);
        }
    }

    // MODIFIES: this, listingsPanel
    // EFFECTS: parses the new state of Listings after choosing to load store
    public void initNewListingsState() {
        listingModel.removeAllElements();
        for (Listing oneListing : store.getListings()) {
            for (ClothingItem clothingItem : oneListing.getOutfit()) {
                itemModel.addElement(clothingItem);
            }
            listingModel.addElement(oneListing);
        }
    }

    // EFFECTS: listens to when the save or load buttons are pressed to process command
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            saveStore();
        } else if (e.getSource() == loadButton) {
            loadStore();
        }
    }
}
