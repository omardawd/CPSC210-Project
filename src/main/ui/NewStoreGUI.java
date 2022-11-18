package ui;

import model.ClothingItem;
import model.Listing;
import model.Store;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private Listing selectedListing;
    private Listing createdListing;
    private ClothingItem createdClothingItem;

    // Constructor
    public NewStoreGUI() {
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

    // Store initializer
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

    // Header top of screen that has the store name
    public void initHeader() {
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(Color.DARK_GRAY);
        JLabel storeSign = new JLabel("The Gallery Thrift Store By Omar");
        header.add(storeSign);

        add(header, "North");
    }

    // Footer bottom of screen has buttons to control app
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

    public void setupAddListingButton(JButton addListingButton) {
        addListingButton.addActionListener(e -> {
            createAddListingWindow();
        });
    }

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
        createItemFieldsForListingAndPassData(listingInfoPanel, createdListing);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> createListingFrame.dispose());
        JButton createButton = setupCreateListingButton(createListingFrame, listingTitleField, listingAuthorField);

        createListingFrame.add(listingInfoPanel);
        createListingFrame.add(cancelButton);
        createListingFrame.add(createButton);
        createListingFrame.setVisible(true);
        createListingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void initListingInfoPanel(JPanel panel, JLabel title, JTextField titleField,
                                     JLabel author, JTextField authorField) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(title);
        panel.add(titleField);
        panel.add(author);
        panel.add(authorField);
    }

    public void createItemFieldsForListingAndPassData(JPanel listingInfoPanel, Listing createdListing) {
        for (int i = 1; i < 5; i++) {
            JLabel itemName = new JLabel("Enter item " + i + " name: ");
            JLabel itemSize = new JLabel("Enter item " + i + " size: ");
            JLabel itemPrice = new JLabel("Enter item " + i + " price: ");

            JTextField itemNameField = new JTextField();
            itemNameField.setColumns(20);
            JTextField itemSizeField = new JTextField();
            itemSizeField.setColumns(10);
            JTextField itemPriceField = new JTextField();
            itemPriceField.setColumns(10);

            listingInfoPanel.add(itemName);
            listingInfoPanel.add(itemNameField);
            listingInfoPanel.add(itemSize);
            listingInfoPanel.add(itemSizeField);
            listingInfoPanel.add(itemPrice);
            listingInfoPanel.add(itemPriceField);

            passDataToClasses(itemNameField.getText(),
                    itemSizeField.getText(), itemPriceField.getText(), createdListing);
        }
    }

    public void passDataToClasses(String name, String size, String price, Listing oneListing) {
        createdClothingItem = new ClothingItem("Omar", 10, "Medium");
        oneListing.addOneItemToOutfit(createdClothingItem);
    }

    public JButton setupCreateListingButton(JFrame createListingFrame, JTextField title, JTextField author) {
        JButton createListingButton = new JButton("Create Listing!");
        createListingButton.addActionListener(e -> {
            createdListing.setListingTitle(title.getText());
            createdListing.setAuthor(author.getText());
            store.addListingToListingsInStore(createdListing);
            listingModel.addElement(createdListing);
            createListingFrame.dispose();
            revalidate();
            repaint();
        });
        return createListingButton;
    }

    // Left panel that displays all the current listings in the store
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

    // Center panel that displays the image and description of each a selected listing
    public void initListing(Listing oneListing) {
        selectedListing = oneListing;
        itemList.setModel(itemModel);
        // listingpanel initialization
        listingPanel = new JPanel();
        listingPanel.removeAll();
        // Listing title up top
        JLabel listingTitle = new JLabel(oneListing.getListingTitle() + " By " + oneListing.getListingAuthor());
        listingTitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        setTextPositionForLabel(listingTitle);
        // Listing Image below title
        ImageIcon listingIcon = new ImageIcon(new ImageIcon("./data/tobs.jpg")
                .getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH));
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
        listingPanel.setBackground(Color.gray);
        add(listingPanel);
    }

    public void setTextPositionForLabel(JLabel listingTitle) {
        listingTitle.setHorizontalTextPosition(JLabel.CENTER);
        listingTitle.setVerticalTextPosition(JLabel.TOP);
    }

    // Left panel that displays all the current favorite Listings in the store
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

    public void continueViewFavoritesPanel(JPanel favoritesPanel) {
        favList.setBackground(Color.GREEN);
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

    // Center panel that displays the image and description of each a selected favorite listing
    public void initFavListing(Listing oneListing) {
        selectedListing = oneListing;
        itemList.setModel(itemModel);
        // listingpanel initialization
        favListingPanel = new JPanel();
        favListingPanel.removeAll();
        // Listing title up top
        JLabel listingTitle = new JLabel(oneListing.getListingTitle() + " By " + oneListing.getListingAuthor());
        listingTitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        setTextPositionForLabel(listingTitle);
        // Listing Image below title
        ImageIcon listingIcon = new ImageIcon(new ImageIcon("./data/tobs.jpg")
                .getImage().getScaledInstance(250, 300, Image.SCALE_SMOOTH));
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
        favListingPanel.setBackground(Color.gray);
        add(favListingPanel);
    }

    // Bottom panel for the listing page to add or remove listing from favorites
    public void initBottomPanel() {
        JPanel bottomPanelInListing = new JPanel();
        bottomPanelInListing.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanelInListing.setBackground(Color.RED);
        addToFavButton = new JButton("Add To Favorites");
        setupAddToFaveButton(addToFavButton);
        removeFromFavButton = new JButton("Remove From Favorites");
        setupRemoveButton(removeFromFavButton);
        bottomPanelInListing.add(addToFavButton);
        bottomPanelInListing.add(removeFromFavButton);
        listingPanel.add(bottomPanelInListing, "South");
        bottomPanelInListing.setVisible(true);
    }

    // setup helper method for add listing to favorite method
    public void setupAddToFaveButton(JButton addToFavButton) {
        addToFavButton.addActionListener(e -> {
            if (listingsList.getSelectedIndex() != -1) {
                Listing oneListing = listingsList.getSelectedValue();
                favModel.addElement(oneListing);
                store.removeListingFromFavoritesInStore(oneListing);
                favoritesPanel.updateUI();
            }
        });
    }

    // setup helper method for remove listing from favorite method
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


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            System.out.println("save");
        } else if (e.getSource() == loadButton) {
            System.out.println("load");
        }
    }

    public static void main(String[] args) {
        new NewStoreGUI();
    }
}
