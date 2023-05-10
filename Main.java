// **********************************************************************************
// Title: Wine Wizard Main
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: Main.java
// Description: Main class of Wine Wizard for running the GUI and letting the user
//    sign in, create an account, view, search, and sort wines on the main page,
//    and add, edit, and delete wines as an admin - configureWineDisplay method
//    refreshes the Wine Scroll Pane with the current wines in their correct order
// **********************************************************************************
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class Main extends Application {
    private static WineTree wines;
    private static WineList currentWines;
    private static VBox wineDisplayBox = new VBox(15);
    private static ScrollPane wineScroll = new ScrollPane(wineDisplayBox);
    private static UserHashTable users;
    private static User currentUser;
    private static File wineFile = new File("files/wines.dat");
    private static File userFile = new File("files/users.dat");
    private static final Scene SCENE = new Scene(new Pane(), 1400, 750);

    /** Check if there are save files and load data in or create new structures before displaying welcome page
     *     For the purposes of this class, I have made a file of sample wines that will be read in and added
     *     to the program upon first use for demo purposes */
    @Override
    public void start(Stage primaryStage) {
        if (userFile.exists() && wineFile.exists()) {
            DataLoadThread dataLoadThread = new DataLoadThread();
            dataLoadThread.start();

            try {
                dataLoadThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            users = dataLoadThread.getUsers();
            wines = dataLoadThread.getWines();
        }
        else {
            users = new UserHashTable();
            wines = new WineTree();
            loadSampleWines();
        }
        displayWelcomePage(primaryStage);
    }

    /** Display welcome page */
    public static void displayWelcomePage(Stage primaryStage) {
        WelcomePane welcomePane = new WelcomePane();
        // Set sign in button to go to sign in page
        welcomePane.getBtSignIn().setOnAction(e -> signIn(primaryStage));

        // Set create account button to go to create account page - first user will be an admin
        boolean firstUser = users.isEmpty();
        welcomePane.getBtCreateAccount().setOnAction(e -> createAccount(primaryStage, firstUser));

        SCENE.setRoot(welcomePane);
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(SCENE);
        primaryStage.show();
    }

    /** Sign In Page */
    public static void signIn(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: rgba(255, 246, 244, 0.8)");
        pane.setPadding(new Insets(10));

        // Welcome heading
        VBox heading = new VBox(5);
        Text welcome = new Text("Welcome back");
        welcome.setFont(Font.font("Algerian", FontWeight.NORMAL,
                FontPosture.REGULAR, 54));
        welcome.setFill(Color.rgb(171, 90, 79, 0.96));
        Text instructions = new Text("Please enter your credentials below.");
        instructions.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        instructions.setFill(Color.rgb(148, 102, 46, 0.8));
        heading.getChildren().addAll(welcome, instructions);
        heading.setAlignment(Pos.CENTER);
        pane.setTop(heading);
        pane.setAlignment(heading, Pos.TOP_CENTER);

        // Fields for user to enter credentials
        HBox centerBox = new HBox(20);
        centerBox.setAlignment(Pos.CENTER);
        VBox inputBox = new VBox(10);
        Text username = new Text("Username");
        username.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        username.setFill(Color.rgb(121, 45, 35, 0.96));
        TextField tfUsername = new TextField();
        tfUsername.setMaxWidth(250);
        tfUsername.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        Text password = new Text("Password");
        password.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        password.setFill(Color.rgb(121, 45, 35, 0.96));
        PasswordField pfPassword = new PasswordField();
        pfPassword.setMaxWidth(250);
        pfPassword.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        Text inputError = new Text("");
        inputError.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.ITALIC, 12));
        inputError.setTextAlignment(TextAlignment.CENTER);
        inputError.setFill(Color.rgb(151, 100, 96, 0.96));
        Button btSignIn = new Button("Sign In");
        btSignIn.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 16));
        btSignIn.setStyle("-fx-background-color: rgba(186, 130, 62, 0.8);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)");
        inputBox.getChildren().addAll(username, tfUsername, password, pfPassword, btSignIn);
        inputBox.setAlignment(Pos.CENTER);

        // Sign in button darkens as mouse enters
        btSignIn.setOnMouseEntered(e -> btSignIn.setStyle("-fx-background-color: rgba(176, 108, 25, 0.8);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)"));
        btSignIn.setOnMouseExited(e -> btSignIn.setStyle("-fx-background-color: rgba(186, 130, 62, 0.8);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)"));

        // Sign in button verifies the user and displays admin sign in if user is an admin
        btSignIn.setOnAction(e -> {
            String inputtedUsername = tfUsername.getText();
            String inputtedPassword = pfPassword.getText();
            if (users.verifyUser(inputtedUsername, inputtedPassword)) {
                inputError.setText("");
                currentUser = users.getUser(inputtedUsername);
                if (currentUser instanceof Admin)
                    displayAdminSignIn(primaryStage, (Admin) currentUser);
                else
                    displayMainPage(primaryStage);
            }
            else {
                inputError.setText("One or both fields is incorrect.");
            }
        });

        // Clinking glasses gif
        Image clinkGif = new Image("images/clink2.gif", 400, 250, true, false);
        centerBox.getChildren().addAll(inputBox, new ImageView(clinkGif));
        pane.setCenter(centerBox);

        // Button to go back
        Button btBack = new Button("Go back");
        btBack.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 16));
        btBack.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)");
        btBack.setMinWidth(150);
        pane.setBottom(btBack);
        pane.setAlignment(btBack, Pos.CENTER_RIGHT);

        // Back button darkens as mouse enters
        btBack.setOnMouseEntered(e -> btBack.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)"));
        btBack.setOnMouseExited(e -> btBack.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)"));

        // Back button takes user back to welcome page
        btBack.setOnAction(e -> displayWelcomePage(primaryStage));

        SCENE.setRoot(pane);
        primaryStage.setTitle("Sign In");
        primaryStage.setScene(SCENE);
        primaryStage.show();
    }

    /** Display window for admin to enter their pin */
    public static void displayAdminSignIn(Stage primaryStage, Admin admin) {
        Stage pinStage = new Stage();
        Scene pinScene = new Scene(new Pane(), 510, 300);
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: rgba(253, 250, 244, 0.8)");
        pane.setPadding(new Insets(10));

        // Heading
        VBox heading = new VBox(10);
        Text welcome = new Text("Welcome Back, Admin");
        welcome.setFont(Font.font("Vivaldi", FontWeight.NORMAL,
                FontPosture.REGULAR, 46));
        welcome.setFill(Color.rgb(149, 111, 25, 0.96));
        Text instructions = new Text("Please enter your pin to continue.");
        instructions.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.ITALIC, 14));
        instructions.setTextAlignment(TextAlignment.CENTER);
        instructions.setFill(Color.rgb(151, 100, 96, 0.96));
        heading.getChildren().addAll(welcome, instructions);
        heading.setAlignment(Pos.CENTER);
        pane.setTop(heading);
        pane.setAlignment(heading, Pos.TOP_CENTER);

        VBox inputBox = new VBox(10);
        inputBox.setAlignment(Pos.CENTER);
        HBox fieldAndButtonBox = new HBox(10);
        fieldAndButtonBox.setAlignment(Pos.CENTER);
        // Password Field for user to enter pin
        PasswordField pfPin = new PasswordField();
        pfPin.setMaxWidth(100);
        pfPin.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        pfPin.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.74)");
        // Enter button
        Button btEnter = new Button("Enter");
        btEnter.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btEnter.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)");
        btEnter.setOnMouseEntered(e -> btEnter.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)"));
        btEnter.setOnMouseExited(e -> btEnter.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)"));
        fieldAndButtonBox.getChildren().addAll(pfPin, btEnter);
        // Error text
        Text inputError = new Text("");
        inputError.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.ITALIC, 12));
        inputError.setTextAlignment(TextAlignment.CENTER);
        inputError.setFill(Color.rgb(151, 100, 96, 0.96));
        inputBox.getChildren().addAll(fieldAndButtonBox, inputError);
        inputBox.setAlignment(Pos.CENTER);
        pane.setCenter(inputBox);
        pane.setAlignment(inputBox, Pos.CENTER);

        /** Enter button validates the entered pin and sends user to main page if correct */
        btEnter.setOnAction(e -> {
            String pin = pfPin.getText();
            if (pin.equals(admin.getPin())) {
                currentUser = admin;
                pinStage.close();
                displayMainPage(primaryStage);
            }
            else {
                inputError.setText("Sorry, that pin doesn't match.");
            }
        });

        pinScene.setRoot(pane);
        pinStage.setTitle("Admin Sign-In");
        pinStage.setScene(pinScene);
        pinStage.show();

    }

    /** Create Account Page */
    public static void createAccount(Stage primaryStage, boolean isAdmin) {
        CreateAccountPane createAccountPane = new CreateAccountPane(isAdmin);

        // Configure Create Account button to create an admin or user once fields are validated and go to main menu
        createAccountPane.getBtCreateAccount().setOnAction(e -> {
            boolean usernameValid = createAccountPane.validateUserName(users);
            boolean passwordValid = createAccountPane.validatePassword();
            boolean pinValid = (isAdmin)? createAccountPane.validatePin(): true;
            if (usernameValid && passwordValid && pinValid) {
                if (isAdmin) {
                    currentUser = new Admin(createAccountPane.getTfName().getText(),
                            createAccountPane.getFinalUsername(), createAccountPane.getFinalPassword(),
                            createAccountPane.getFinalPin());
                    users.addUser(currentUser);
                } else {
                    currentUser = new User(createAccountPane.getTfName().getText(),
                            createAccountPane.getFinalUsername(), createAccountPane.getFinalPassword());
                    users.addUser(currentUser);
                }
                saveData();
                displayMainPage(primaryStage);
            }
        });

        // Back button takes user back to welcome page if there is no current user and back to the
        //    main page if there is a current user (for admins adding new admins)
        createAccountPane.getBtBack().setOnAction(e -> {
            if (currentUser == null)
                displayWelcomePage(primaryStage);
            else
                displayMainPage(primaryStage);
        });

        SCENE.setRoot(createAccountPane);
        primaryStage.setTitle("Create Account");
        primaryStage.setScene(SCENE);
        primaryStage.show();
    }

    /** Main Page allows user to view, search, and sort wines */
    public static void displayMainPage(Stage primaryStage) {
        // current wines are all wines by default
        currentWines = wines.toList();

        BorderPane mainPane = new BorderPane();
        mainPane.setStyle("-fx-background-color: rgba(234, 227, 218, 0.96)");
        mainPane.setPadding(new Insets(15));

        // Greeting and instructions
        VBox heading = new VBox(5);
        Text greeting = new Text("Hello, " + currentUser.getName() + "!");
        greeting.setFont(Font.font("Vivaldi", FontWeight.EXTRA_BOLD,
                FontPosture.REGULAR, 46));
        greeting.setFill(Color.rgb(126, 18, 18, 0.96));
        greeting.setTextAlignment(TextAlignment.CENTER);
        Text instructions = new Text("Please use the options below to search and sort through our " +
                "selection of wines.");
        instructions.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD,
                FontPosture.ITALIC, 16));
        instructions.setFill(Color.rgb(125, 17, 17, 0.74));
        instructions.setTextAlignment(TextAlignment.CENTER);
        heading.getChildren().addAll(greeting, instructions);
        heading.setAlignment(Pos.CENTER);
        mainPane.setTop(heading);
        mainPane.setAlignment(heading, Pos.TOP_CENTER);

        // Center of pane shows a scroll pane of wines as well as options for searching, sorting,
        // and filtering the wines
        BorderPane centerPane = new BorderPane();
        centerPane.setPadding(new Insets(15));
        // Search bar and error text that displays if user enters no search criteria
        HBox searchBox = new HBox();
        VBox searchAndErrorBox = new VBox(5);
        searchAndErrorBox.setAlignment(Pos.CENTER);
        Text searchErrorText = new Text("");
        searchErrorText.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.ITALIC, 12));
        searchErrorText.setTextAlignment(TextAlignment.CENTER);
        searchErrorText.setFill(Color.rgb(151, 100, 96, 0.96));
        searchErrorText.setTextAlignment(TextAlignment.CENTER);
        TextField tfSearch = new TextField("Search");
        tfSearch.setMaxWidth(425);
        tfSearch.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 14));
        tfSearch.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.74)");
        String[] searchOptions = {"by Name", "by Grape", "by Producer", "by Region"};
        ComboBox cboSearchOptions = new ComboBox<>(FXCollections.observableArrayList(searchOptions));
        cboSearchOptions.setValue(searchOptions[0]);
        cboSearchOptions.setStyle("-fx-font: 14px \"Georgia\";" +
                "-fx-text-fill: rgba(0, 0, 0, 0.74)");
        Button btSearch = new Button();
        Image magnifyingGlass = new Image("images/magnifyingGlass.png", 18, 18, false, false);
        ImageView magnifyingGlassView = new ImageView(magnifyingGlass);
        btSearch.setGraphic(magnifyingGlassView);
        searchBox.getChildren().addAll(tfSearch, cboSearchOptions, btSearch);
        searchBox.setAlignment(Pos.CENTER);
        searchAndErrorBox.getChildren().addAll(searchBox, searchErrorText);
        centerPane.setTop(searchAndErrorBox);
        centerPane.setAlignment(searchAndErrorBox, Pos.CENTER);

        // Buttons for filtering wines on the left
        VBox filterBox = new VBox(10);
        filterBox.setAlignment(Pos.CENTER);
        Text filterHeading = new Text("Show me...");
        filterHeading.setFill(Color.rgb(99, 43, 20, 0.96));
        filterHeading.setFont(Font.font("Cambria", FontWeight.NORMAL, FontPosture.ITALIC, 20));
        Button btSparkling = new Button("Sparkling Wines");
        setButtonTextAndSize(btSparkling);
        setDefaultButtonStyle(btSparkling);
        btSparkling.setOnMouseEntered(e -> lightenButtonStyle(btSparkling));
        btSparkling.setOnMouseExited(e -> setDefaultButtonStyle(btSparkling));
        Button btWhite = new Button("White Wines");
        setButtonTextAndSize(btWhite);
        setDefaultButtonStyle(btWhite);
        btWhite.setOnMouseEntered(e -> lightenButtonStyle(btWhite));
        btWhite.setOnMouseExited(e -> setDefaultButtonStyle(btWhite));
        Button btRose = new Button("Rosé Wines");
        setButtonTextAndSize(btRose);
        setDefaultButtonStyle(btRose);
        btRose.setOnMouseEntered(e -> lightenButtonStyle(btRose));
        btRose.setOnMouseExited(e -> setDefaultButtonStyle(btRose));
        Button btRed = new Button("Red Wines");
        setButtonTextAndSize(btRed);
        setDefaultButtonStyle(btRed);
        btRed.setOnMouseEntered(e -> lightenButtonStyle(btRed));
        btRed.setOnMouseExited(e -> setDefaultButtonStyle(btRed));
        Button btFavorites = new Button("Favorite Wines");
        setButtonTextAndSize(btFavorites);
        setDefaultButtonStyle(btFavorites);
        btFavorites.setOnMouseEntered(e -> lightenButtonStyle(btFavorites));
        btFavorites.setOnMouseExited(e -> setDefaultButtonStyle(btFavorites));
        Button btAllWines = new Button("All Wines");
        setButtonTextAndSize(btAllWines);
        setDefaultButtonStyle(btAllWines);
        btAllWines.setOnMouseEntered(e -> lightenButtonStyle(btAllWines));
        btAllWines.setOnMouseExited(e -> setDefaultButtonStyle(btAllWines));
        filterBox.getChildren().addAll(filterHeading, btSparkling, btWhite, btRose,
                btRed, btFavorites, btAllWines);
        centerPane.setLeft(filterBox);

        // Sort options and scroll pane for displaying wine
        VBox sortAndDisplayBox = new VBox();
        HBox sortBox = new HBox(5);
        Text sortText = new Text("Sort by:");
        sortText.setFont(Font.font("Cambria", FontWeight.SEMI_BOLD,
                FontPosture.REGULAR, 16));
        sortText.setFill(Color.rgb(99, 43, 20, 0.96));
        String[] sortOptions = {"Color", "Name: A to Z", "Name: Z to A", "Grape: A to Z",
                "Grape: Z to A", "Sweetness: Sweet to Dry", "Sweetness: Dry to Sweet",
                "Bottle Price: Low to High", "Bottle Price: High to Low",
                "Glass Price: Low to High", "Glass Price: High to Low", "Most Popular"
        };
        ComboBox cboSortOptions = new ComboBox<>(FXCollections.observableArrayList(sortOptions));
        cboSortOptions.setValue(sortOptions[0]);
        cboSortOptions.setStyle("-fx-font: 14px \"Georgia\";" +
                "-fx-text-fill: rgba(0, 0, 0, 0.74)");
        Button btSort = new Button("Go");
        btSort.setFont(Font.font("Sitka Text", FontWeight.NORMAL, FontPosture.REGULAR, 13));
        btSort.setOnMouseEntered(e -> lightenButtonStyle(btSort));
        btSort.setOnMouseExited(e -> setDefaultButtonStyle(btSort));
        setDefaultButtonStyle(btSort);
        sortBox.getChildren().addAll(sortText, cboSortOptions, btSort);
        sortBox.setAlignment(Pos.TOP_RIGHT);
        // Scroll pane for displaying wines
        wineDisplayBox.setAlignment(Pos.CENTER);
        wineDisplayBox.setStyle("-fx-background: rgba(238, 214, 206, 0.96)");
        wineScroll.setPadding(new Insets(15));
        wineScroll.setFitToWidth(true);
        wineScroll.setStyle("-fx-background: rgba(234, 227, 218, 0.96)");
        wineScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        wineScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sortAndDisplayBox.getChildren().addAll(sortBox, wineScroll);
        // Configure scroll pane to show all wines by default
        try {
            configureWineDisplay(primaryStage);
        }
        catch (NoWinesFoundException ex) {
            displayNoWinesFoundText();
        }
        centerPane.setCenter(sortAndDisplayBox);
        mainPane.setCenter(centerPane);

        // Configure search button (all buttons save data to account for users favoriting wines)
        btSearch.setOnAction(e -> {
            saveData();
            String searchInput = tfSearch.getText();
            if (tfSearch.getText().equals("Search") || tfSearch.getText().isEmpty()) {
                searchErrorText.setText("Please input criteria to search.");
            }
            else {
                searchErrorText.setText("");
                currentWines.clear();
                // Search by name
                if (cboSearchOptions.getValue().equals(searchOptions[0])) {
                    currentWines = wines.findByName(searchInput);
                    try {
                        configureWineDisplay(primaryStage);
                    } catch (NoWinesFoundException ex) {
                        displayNoWinesFoundText();
                    }
                }
                // Search by grape
                else if (cboSearchOptions.getValue().equals(searchOptions[1])) {
                    currentWines = wines.findByGrape(searchInput);
                    try {
                        configureWineDisplay(primaryStage);
                    } catch (NoWinesFoundException ex) {
                        displayNoWinesFoundText();
                    }
                }
                // Search by producer
                else if (cboSearchOptions.getValue().equals(searchOptions[2])) {
                    currentWines = wines.findByProducer(searchInput);
                    try {
                        configureWineDisplay(primaryStage);
                    } catch (NoWinesFoundException ex) {
                        displayNoWinesFoundText();
                    }
                }
                // Search by region
                else {
                    currentWines = wines.findByRegion(searchInput);
                    try {
                        configureWineDisplay(primaryStage);
                    } catch (NoWinesFoundException ex) {
                        displayNoWinesFoundText();
                    }
                }
            }
        });

        // Configure filter buttons (all buttons save data to account for users favoriting wines)
        btSparkling.setOnAction(e -> {
            saveData();
            currentWines = wines.findByColor("Sparkling");
            try {
                configureWineDisplay(primaryStage);
            } catch (NoWinesFoundException ex) {
                displayNoWinesFoundText();
            }
        });
        btWhite.setOnAction(e -> {
            saveData();
            currentWines = wines.findByColor("White");
            try {
                configureWineDisplay(primaryStage);
            } catch (NoWinesFoundException ex) {
                displayNoWinesFoundText();
            }
        });
        btRose.setOnAction(e -> {
            saveData();
            currentWines = wines.findByColor("Rosé");
            try {
                configureWineDisplay(primaryStage);
            } catch (NoWinesFoundException ex) {
                displayNoWinesFoundText();
            }
        });
        btRed.setOnAction(e -> {
            saveData();
            currentWines = wines.findByColor("Red");
            try {
                configureWineDisplay(primaryStage);
            } catch (NoWinesFoundException ex) {
                displayNoWinesFoundText();
            }
        });
        btFavorites.setOnAction(e -> {
            saveData();
            currentWines = currentUser.getFavoriteWines();
            try {
                configureWineDisplay(primaryStage);
            } catch (NoWinesFoundException ex) {
                displayNoWinesFoundText();
            }
        });
        btAllWines.setOnAction(e -> {
            saveData();
            currentWines = wines.toList();
            try {
                configureWineDisplay(primaryStage);
            } catch (NoWinesFoundException ex) {
                displayNoWinesFoundText();
            }
        });

        // Configure sort options (all buttons save data to account for users favoriting wines)
        btSort.setOnAction(e -> {
            saveData();
            // Sort by color
            if (cboSortOptions.getValue().equals(sortOptions[0])) {
                currentWines.mergeSort(WineComparators.byColor());
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by name (A to Z)
            else if (cboSortOptions.getValue().equals(sortOptions[1])) {
                currentWines.mergeSort();
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by name (Z to A)
            else if (cboSortOptions.getValue().equals(sortOptions[2])) {
                currentWines.mergeSort(WineComparators.byNameReverse());
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by grape (A to Z)
            else if (cboSortOptions.getValue().equals(sortOptions[3])) {
                currentWines.mergeSort(WineComparators.byGrape());
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by grape (Z to A)
            else if (cboSortOptions.getValue().equals(sortOptions[4])) {
                Comparator<Wine> grapeComparator = WineComparators.byGrape();
                Comparator<Wine> reverseGrapeComparator = grapeComparator.reversed();
                currentWines.mergeSort(reverseGrapeComparator);
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by sweetness (sweet to dry)
            else if (cboSortOptions.getValue().equals(sortOptions[5])) {
                currentWines.mergeSort(WineComparators.bySweetness());
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by sweetness (dry to sweet)
            else if (cboSortOptions.getValue().equals(sortOptions[6])) {
                Comparator<Wine> sweetnessComparator = WineComparators.bySweetness();
                Comparator<Wine> reverseSweetnessComparator = sweetnessComparator.reversed();
                currentWines.mergeSort(reverseSweetnessComparator);
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by bottle price (low to high)
            else if (cboSortOptions.getValue().equals(sortOptions[7])) {
                currentWines.mergeSort(WineComparators.byBottlePrice());
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by bottle price (high to low)
            else if (cboSortOptions.getValue().equals(sortOptions[8])) {
                Comparator<Wine> bottlePriceComparator = WineComparators.byBottlePrice();
                Comparator<Wine> reverseBottlePriceComparator = bottlePriceComparator.reversed();
                currentWines.mergeSort(reverseBottlePriceComparator);
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by glass price (low to high)
            else if (cboSortOptions.getValue().equals(sortOptions[9])) {
                currentWines.mergeSort(WineComparators.byGlassPrice());
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by glass price (high to low)
            else if (cboSortOptions.getValue().equals(sortOptions[10])) {
                Comparator<Wine> glassPriceComparator = WineComparators.byGlassPrice();
                Comparator<Wine> reverseGlassPriceComparator = glassPriceComparator.reversed();
                currentWines.mergeSort(reverseGlassPriceComparator);
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
            // Sort by most popular
            else if (cboSortOptions.getValue().equals(sortOptions[11])) {
                currentWines.mergeSort(WineComparators.byNumFavorites());
                try {
                    configureWineDisplay(primaryStage);
                } catch (NoWinesFoundException ex) {
                    displayNoWinesFoundText();
                }
            }
        });

        // Sign out option for all users
        HBox bottomBox = new HBox(10);
        Text signOutText = new Text("Sign Out");
        signOutText.setFont(Font.font("Sitka Text", FontWeight.NORMAL,
                FontPosture.REGULAR, 12));
        signOutText.setFill(Color.rgb(125, 17, 17, 0.74));
        signOutText.setUnderline(true);
        signOutText.setStyle("-fx-cursor: hand");
        signOutText.setOnMouseClicked(e -> {
            saveData();
            confirmSignOut(primaryStage);
        });

        // Add admin and add wine options for admin
        if (currentUser instanceof Admin) {
            Text addWineText = new Text("Add Wine");
            addWineText.setFont(Font.font("Sitka Text", FontWeight.NORMAL,
                    FontPosture.REGULAR, 12));
            addWineText.setFill(Color.rgb(125, 17, 17, 0.74));
            addWineText.setUnderline(true);
            addWineText.setStyle("-fx-cursor: hand");
            // Clicking add wine text saves data and takes admin to add wine page
            addWineText.setOnMouseClicked(e -> {
                saveData();
                displayAddWinePage(primaryStage);
            });
            Text addAdminText = new Text("Add Admin");
            addAdminText.setFont(Font.font("Sitka Text", FontWeight.NORMAL,
                    FontPosture.REGULAR, 12));
            addAdminText.setFill(Color.rgb(125, 17, 17, 0.74));
            addAdminText.setUnderline(true);
            addAdminText.setStyle("-fx-cursor: hand");
            // Clicking add admin text saves data and takes admin to the new admin page
            addAdminText.setOnMouseClicked(e -> {
                saveData();
                createAccount(primaryStage, true);
            });
            bottomBox.getChildren().addAll(addWineText, addAdminText);
        }
        bottomBox.getChildren().add(signOutText);
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);
        mainPane.setBottom(bottomBox);
        mainPane.setAlignment(bottomBox, Pos.BOTTOM_RIGHT);


        SCENE.setRoot(mainPane);
        primaryStage.setTitle("Wine Wizard");
        primaryStage.setScene(SCENE);
        primaryStage.show();
    }

    /** Confirm sign out */
    public static void confirmSignOut(Stage primaryStage) {
        Stage confirmationStage = new Stage();
        Scene confirmationScene = new Scene(new Pane(), 570, 125);
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: rgba(255, 246, 244, 0.8)");
        pane.setPadding(new Insets(10));

        // Confirm all fields are correct
        Text confirmText = new Text("Are you sure you would like to sign out?");
        confirmText.setFont(Font.font("Vivaldi", FontWeight.BOLD,
                FontPosture.REGULAR, 28));
        confirmText.setFill(Color.rgb(121, 45, 35, 0.96));
        pane.setTop(confirmText);
        pane.setAlignment(confirmText, Pos.CENTER);
        pane.setMargin(confirmText, new Insets(5));

        // HBox for buttons
        HBox buttonPane = new HBox(15);
        buttonPane.setAlignment(Pos.CENTER);
        Button btYes = new Button("Yes");
        Button btNo = new Button("No, go back");
        btYes.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
        btNo.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
        btYes.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btNo.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btYes.setMinWidth(120);
        btNo.setMinWidth(120);

        // Buttons darken as mouse enters them
        btYes.setOnMouseEntered(e -> btYes.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btYes.setOnMouseExited(e -> btYes.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btNo.setOnMouseEntered(e -> btNo.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btNo.setOnMouseExited(e -> btNo.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));

        // No button exits the stage
        btNo.setOnAction(e -> confirmationStage.close());
        buttonPane.getChildren().addAll(btYes, btNo);
        // Yes button takes user to welcome page (data was saved before displaying this screen)
        btYes.setOnAction(e -> {
            currentUser = null;
            confirmationStage.close();
            displayWelcomePage(primaryStage);
        });

        pane.setBottom(buttonPane);
        pane.setAlignment(buttonPane, Pos.CENTER);
        pane.setMargin(buttonPane, new Insets(5));

        confirmationScene.setRoot(pane);
        confirmationStage.setTitle("Confirm Sign Out");
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();
    }

    /** Add a Wine Page */
    public static void displayAddWinePage(Stage primaryStage) {
        WineManagePane addWinePane = new WineManagePane();

        // Add wine button creates a new wine after validating the inputs and calls upon the confirmWine method
        addWinePane.getBtAddWine().setOnAction(e -> {
            if (addWinePane.validateInputs()) {
                String nameInput = addWinePane.getTfName().getText();
                String producerInput = addWinePane.getTfProducer().getText();
                boolean sparklingInput = addWinePane.getRbYes().isSelected();
                String colorInput = ((sparklingInput)? "Sparkling ": "") + addWinePane.getCboColors().getValue();
                String grapeInput = addWinePane.getTfGrape().getText();
                String regionInput = addWinePane.getTfRegion().getText();
                int vintageInput = addWinePane.getFinalVintage();
                double abvInput = addWinePane.getFinalABV();
                int bottlePriceInput =addWinePane.getFinalBottlePrice();
                int glassPriceInput = addWinePane.getFinalGlassPrice();
                String sweetnessInput = addWinePane.getCboSweetness().getValue();
                String tastingNotesInput = addWinePane.getTaTastingNotes().getText();
                String pairingsInput = addWinePane.getTaPairings().getText();
                // Assign default image if no image specified
                String imageFileName = "";
                if (addWinePane.getImagePath().getText().isEmpty()) {
                    imageFileName += "images/default" + (colorInput.equals(Wine.COLORS[0])? "White" :
                            (colorInput.equals(Wine.COLORS[1])? "Rose": "Red")) + ".png";
                } else {
                    imageFileName = addWinePane.getImagePath().getText();
                }
                Wine newWine = new Wine(nameInput, producerInput, colorInput, grapeInput, regionInput, vintageInput,
                        abvInput, bottlePriceInput, glassPriceInput, sweetnessInput, tastingNotesInput, pairingsInput,
                        sparklingInput, imageFileName);
                // If wine is a duplicate, display duplicate wine window, otherwise display confirm window
                if (wines.search(newWine))
                    duplicateWine(primaryStage, wines.find(newWine.getName()));
                else
                    confirmWine(primaryStage, newWine);
            }
        });

        // Configure back button to go back to main page
        addWinePane.getBtBack().setOnAction(e -> displayMainPage(primaryStage));

        SCENE.setRoot(addWinePane);
        primaryStage.setTitle("Add Wine");
        primaryStage.setScene(SCENE);
        primaryStage.show();
    }

    /** Display pane of duplicate wine and prompt user to change name of new wine if it differs */
    public static void duplicateWine(Stage primaryStage, Wine wine) {
        Stage duplicateWineStage = new Stage();
        Scene duplicateWineScene = new Scene(new Pane(), 650, 474);
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: rgba(255, 246, 244, 0.8)");
        pane.setPadding(new Insets(10));

        // Heading explains there is already a wine with that name and to choose a new name if it is
        //   a different wine
        VBox heading = new VBox(5);
        Text duplicateWineText = new Text("Oops, It looks like that wine may already exist...");
        duplicateWineText.setFont(Font.font("Bookman Old Style", FontWeight.BOLD,
                FontPosture.REGULAR, 24));
        duplicateWineText.setFill(Color.rgb(121, 45, 35, 0.96));
        Text instructions = new Text("If this is a different wine, please use a different name for your newly " +
                "entered wine.");
        instructions.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD,
                FontPosture.ITALIC, 14));
        instructions.setFill(Color.rgb(125, 17, 17, 0.74));
        instructions.setTextAlignment(TextAlignment.CENTER);
        heading.getChildren().addAll(duplicateWineText, instructions);
        heading.setAlignment(Pos.CENTER);
        pane.setTop(heading);
        pane.setAlignment(heading, Pos.CENTER);
        pane.setMargin(heading, new Insets(5));

        // Wine Pane for new wine at center of screen
        WinePane winePane = new WinePane(wine);
        pane.setCenter(winePane);

        // HBox for buttons
        Button btOk = new Button("Ok");
        btOk.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
        btOk.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btOk.setMinWidth(120);

        // Buttons darken as mouse enters them
        btOk.setOnMouseEntered(e -> btOk.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btOk.setOnMouseExited(e -> btOk.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));

        // No button exits the stage
        btOk.setOnAction(e -> duplicateWineStage.close());

        pane.setBottom(btOk);
        pane.setAlignment(btOk, Pos.CENTER);
        pane.setMargin(btOk, new Insets(5));

        duplicateWineScene.setRoot(pane);
        duplicateWineStage.setTitle("Duplicate Wine");
        duplicateWineStage.setScene(duplicateWineScene);
        duplicateWineStage.show();
    }

    /** Edit Wine Page */
    public static void displayEditWinePage(Stage primaryStage, Wine wineToEdit) {
        WineManagePane editWinePane = new WineManagePane(wineToEdit);

        editWinePane.getBtAddWine().setOnAction(e -> {
            if (editWinePane.validateInputs()) {
                String nameInput = editWinePane.getTfName().getText();
                String producerInput = editWinePane.getTfProducer().getText();
                boolean sparklingInput = editWinePane.getRbYes().isSelected();
                String colorInput = ((sparklingInput)? "Sparkling ": "") + editWinePane.getCboColors().getValue();
                String grapeInput = editWinePane.getTfGrape().getText();
                String regionInput = editWinePane.getTfRegion().getText();
                int vintageInput = editWinePane.getFinalVintage();
                double abvInput = editWinePane.getFinalABV();
                int bottlePriceInput = editWinePane.getFinalBottlePrice();
                int glassPriceInput = editWinePane.getFinalGlassPrice();
                String sweetnessInput = editWinePane.getCboSweetness().getValue();
                String tastingNotesInput = editWinePane.getTaTastingNotes().getText();
                String pairingsInput = editWinePane.getTaPairings().getText();
                // Assign default image if no image specified
                String imageFileName = "";
                if (editWinePane.getImagePath().getText().isEmpty()) {
                    imageFileName += "images/default" + (colorInput.equals(Wine.COLORS[0])? "White" :
                            (colorInput.equals(Wine.COLORS[1])? "Rose": "Red")) + ".png";
                } else {
                    imageFileName = editWinePane.getImagePath().getText();
                }
                Wine newWine = new Wine(nameInput, producerInput, colorInput, grapeInput, regionInput, vintageInput,
                        abvInput, bottlePriceInput, glassPriceInput, sweetnessInput, tastingNotesInput, pairingsInput,
                        sparklingInput, imageFileName);
                confirmWine(primaryStage, newWine, wineToEdit);
            }
        });

        // Configure back button to go back to main page
        editWinePane.getBtBack().setOnAction(e -> displayMainPage(primaryStage));

        SCENE.setRoot(editWinePane);
        primaryStage.setTitle("Edit Wine");
        primaryStage.setScene(SCENE);
        primaryStage.show();
    }

    /** Displays a new stage with a wine pane of the newly entered wine for the user to confirm */
    public static void confirmWine(Stage primaryStage, Wine wine) {
        Stage confirmationStage = new Stage();
        Scene confirmationScene = new Scene(new Pane(), 570, 474);
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: rgba(255, 246, 244, 0.8)");
        pane.setPadding(new Insets(10));

        // Confirm all fields are correct
        Text confirmText = new Text("Do all fields look correct?");
        confirmText.setFont(Font.font("Vivaldi", FontWeight.BOLD,
                FontPosture.REGULAR, 28));
        confirmText.setFill(Color.rgb(121, 45, 35, 0.96));
        pane.setTop(confirmText);
        pane.setAlignment(confirmText, Pos.CENTER);
        pane.setMargin(confirmText, new Insets(5));

        // Wine Pane for new wine at center of screen
        WinePane winePane = new WinePane(wine);
        pane.setCenter(winePane);

        // HBox for buttons
        HBox buttonPane = new HBox(15);
        buttonPane.setAlignment(Pos.CENTER);
        Button btYes = new Button("Yes");
        Button btNo = new Button("No, go back");
        btYes.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
        btNo.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
        btYes.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btNo.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btYes.setMinWidth(120);
        btNo.setMinWidth(120);

        // Buttons darken as mouse enters them
        btYes.setOnMouseEntered(e -> btYes.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btYes.setOnMouseExited(e -> btYes.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btNo.setOnMouseEntered(e -> btNo.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btNo.setOnMouseExited(e -> btNo.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));

        // No button exits the stage
        btNo.setOnAction(e -> confirmationStage.close());
        // Yes button adds the wine to wine tree and saves the wine tree before going to main page
        btYes.setOnAction(e -> {
            wines.insert(wine);
            saveData();
            confirmationStage.close();
            displayMainPage(primaryStage);
        });
        buttonPane.getChildren().addAll(btYes, btNo);

        pane.setBottom(buttonPane);
        pane.setAlignment(buttonPane, Pos.CENTER);
        pane.setMargin(buttonPane, new Insets(5));

        confirmationScene.setRoot(pane);
        confirmationStage.setTitle("Confirm Wine");
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();
    }

    /** Confirm wine page for editing a wine - deletes old wine and adds new wine to wine tree */
    public static void confirmWine(Stage primaryStage, Wine newWine, Wine oldWine) {
        Stage confirmationStage = new Stage();
        Scene confirmationScene = new Scene(new Pane(), 570, 474);
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: rgba(255, 246, 244, 0.8)");
        pane.setPadding(new Insets(10));

        // Confirm all fields are correct
        Text confirmText = new Text("Do all fields look correct?");
        confirmText.setFont(Font.font("Vivaldi", FontWeight.BOLD,
                FontPosture.REGULAR, 28));
        confirmText.setFill(Color.rgb(121, 45, 35, 0.96));
        pane.setTop(confirmText);
        pane.setAlignment(confirmText, Pos.CENTER);
        pane.setMargin(confirmText, new Insets(5));

        // Wine Pane for new wine at center of screen
        WinePane winePane = new WinePane(newWine);
        pane.setCenter(winePane);

        // HBox for buttons
        HBox buttonPane = new HBox(15);
        buttonPane.setAlignment(Pos.CENTER);
        Button btYes = new Button("Yes");
        Button btNo = new Button("No, go back");
        btYes.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
        btNo.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
        btYes.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btNo.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btYes.setMinWidth(120);
        btNo.setMinWidth(120);

        // Buttons darken as mouse enters them
        btYes.setOnMouseEntered(e -> btYes.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btYes.setOnMouseExited(e -> btYes.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btNo.setOnMouseEntered(e -> btNo.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btNo.setOnMouseExited(e -> btNo.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));

        // No button exits the stage
        btNo.setOnAction(e -> confirmationStage.close());
        // Yes button removes the old wine and replaces it with the new wine before saving and going back to main page
        btYes.setOnAction(e -> {
            wines.delete(oldWine);
            wines.insert(newWine);
            saveData();
            confirmationStage.close();
            displayMainPage(primaryStage);
        });
        buttonPane.getChildren().addAll(btYes, btNo);

        pane.setBottom(buttonPane);
        pane.setAlignment(buttonPane, Pos.CENTER);
        pane.setMargin(buttonPane, new Insets(5));

        confirmationScene.setRoot(pane);
        confirmationStage.setTitle("Confirm Wine");
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();
    }

    public static void confirmDelete(Stage primaryStage, Wine wine) {
        Stage confirmationStage = new Stage();
        Scene confirmationScene = new Scene(new Pane(), 570, 474);
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: rgba(255, 246, 244, 0.8)");
        pane.setPadding(new Insets(10));

        // Confirm all fields are correct
        Text confirmText = new Text("Are you sure you would like to delete this wine?");
        confirmText.setFont(Font.font("Vivaldi", FontWeight.BOLD,
                FontPosture.REGULAR, 28));
        confirmText.setFill(Color.rgb(121, 45, 35, 0.96));
        pane.setTop(confirmText);
        pane.setAlignment(confirmText, Pos.CENTER);
        pane.setMargin(confirmText, new Insets(5));

        // Wine Pane for new wine at center of screen
        WinePane winePane = new WinePane(wine);
        pane.setCenter(winePane);

        // HBox for buttons
        HBox buttonPane = new HBox(15);
        buttonPane.setAlignment(Pos.CENTER);
        Button btYes = new Button("Yes");
        Button btNo = new Button("No, go back");
        btYes.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
        btNo.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
        btYes.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btNo.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btYes.setMinWidth(120);
        btNo.setMinWidth(120);

        // Buttons darken as mouse enters them
        btYes.setOnMouseEntered(e -> btYes.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btYes.setOnMouseExited(e -> btYes.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btNo.setOnMouseEntered(e -> btNo.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));
        btNo.setOnMouseExited(e -> btNo.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)"));

        // No button exits the stage
        btNo.setOnAction(e -> confirmationStage.close());
        // Yes button deletes the wine from the wine tree and current wine list and saves
        btYes.setOnAction(e -> {
            wines.delete(wine);
            currentWines.remove(wine);
            saveData();
            try {
                configureWineDisplay(primaryStage);
            }
            catch (NoWinesFoundException ex) {
                displayNoWinesFoundText();
            }
            confirmationStage.close();
        });
        buttonPane.getChildren().addAll(btYes, btNo);

        pane.setBottom(buttonPane);
        pane.setAlignment(buttonPane, Pos.CENTER);
        pane.setMargin(buttonPane, new Insets(5));

        confirmationScene.setRoot(pane);
        confirmationStage.setTitle("Confirm Wine");
        confirmationStage.setScene(confirmationScene);
        confirmationStage.show();
    }

    /** Button text style and size for main page */
    private static void setButtonTextAndSize(Button button) {
        button.setFont(Font.font("Sitka Text", FontWeight.NORMAL, FontPosture.REGULAR, 16));
        button.setMinWidth(170);
    }

    /** Default button style for main page */
    private static void setDefaultButtonStyle(Button button) {
        button.setStyle("-fx-background-color: rgba(154, 114, 114, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 238, 0.96)");
    }

    /** Lightened button style for main page */
    private static void lightenButtonStyle(Button button) {
        button.setStyle("-fx-background-color: rgba(201, 145, 145, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 238, 0.96)");
    }

    /** Configure the wine display vbox by adding a wine pane for each wine in current wine list */
    private static void configureWineDisplay(Stage primaryStage) throws NoWinesFoundException {
        wineDisplayBox.getChildren().clear();
        // Throw exception if list is empty
        if (currentWines.isEmpty())
            throw new NoWinesFoundException(currentWines);

        for (Wine wine: currentWines) {
            WinePane winePane = new WinePane(wine, currentUser);
            // Configure edit and delete options for admins
            if (currentUser instanceof Admin) {
                winePane.getEditText().setOnMouseClicked(e -> displayEditWinePage(primaryStage, wine));
                winePane.getDeleteText().setOnMouseClicked(e -> confirmDelete(primaryStage, wine));
            }
            wineDisplayBox.getChildren().add(winePane);
        }
    }

    /** Display no wines found message in cases where there are no wines to display */
    private static void displayNoWinesFoundText() {
        Text noWines = new Text("\n\n\n\n\n\n\n\nSorry, no wines found.");
        noWines.setFont(Font.font("Cambria", FontWeight.MEDIUM, FontPosture.ITALIC,
                20));
        noWines.setTextAlignment(TextAlignment.CENTER);
        noWines.setFill(Color.rgb(99, 43, 20, 0.96));
        wineDisplayBox.getChildren().add(noWines);
    }

    /** Save wine tree and user hash table using a data save thread */
    private static void saveData() {
        DataSaveThread dataSaveThread = new DataSaveThread(users, wines);
        dataSaveThread.start();
        try {
            dataSaveThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print("Saved successfully\n");
    }

    /** Loads sample wines into wine tree for demo purposes */
    private static void loadSampleWines() {
        try (Scanner scanner = new Scanner(new File("files/sampleWines.txt"))) {
            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                String producer = scanner.nextLine();
                String color = scanner.nextLine();
                String grape = scanner.nextLine();
                String region = scanner.nextLine();
                int vintage = Integer.parseInt(scanner.nextLine());
                double alcoholByVolume = Double.parseDouble(scanner.nextLine());
                int bottlePrice = Integer.parseInt(scanner.nextLine());
                int glassPrice = Integer.parseInt(scanner.nextLine());
                String sweetness = scanner.nextLine();
                String tastingNotes = scanner.nextLine();
                String pairings = scanner.nextLine();
                boolean sparkling = Boolean.parseBoolean(scanner.nextLine());
                String imageFileName = scanner.nextLine();
                Wine wine = new Wine(name, producer, color, grape, region, vintage, alcoholByVolume,
                        bottlePrice, glassPrice, sweetness, tastingNotes, pairings, sparkling, imageFileName);
                wines.insert(wine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
