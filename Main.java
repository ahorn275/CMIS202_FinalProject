import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Main extends Application {
    private static WineTree wines = new WineTree();
    private static UserHashTable users;
    private static User currentUser;
    private static File wineFile = new File("files/wines.dat");
    private static File userFile = new File("files/users.dat");
    private static Scene scene = new Scene(new Pane(), 1400, 750);

    /** Check if there are save files and load data in or create new structures before displaying welcome page */
    @Override
    public void start(Stage primaryStage) {
        if (userFile.exists())
            users = loadUsersFromFile(userFile.getName());
        else
            users = new UserHashTable();
        if (wineFile.exists())
            wines = loadWinesFromFile(wineFile.getName());
        else
            wines = new WineTree();
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

        scene.setRoot(welcomePane);
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(scene);
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
                    displayMenu(primaryStage);
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

        scene.setRoot(pane);
        primaryStage.setTitle("Sign In");
        primaryStage.setScene(scene);
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

        /** Enter button validates the entered pin and sends user to menu if correct */
        btEnter.setOnAction(e -> {
            String pin = pfPin.getText();
            if (pin.equals(admin.getPin())) {
                pinStage.close();
                displayMenu(primaryStage);
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
                displayMenu(primaryStage);
            }
        });

        // Back button takes user back to welcome page
        createAccountPane.getBtBack().setOnAction(e -> displayWelcomePage(primaryStage));

        scene.setRoot(createAccountPane);
        primaryStage.setTitle("Create Account");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Menu Page */
    public static void displayMenu(Stage primaryStage) {
        MenuPane menu = new MenuPane(currentUser);

        scene.setRoot(menu);
        primaryStage.setTitle("Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /** Add a Wine Page */
    public static void displayAddAWinePage(Stage primaryStage) {
        AddWinePane addWinePane = new AddWinePane();

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
                confirmWine(primaryStage, newWine);
            }
        });
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
        // Yes button adds the wine to wine tree and saves the wine tree
        btYes.setOnAction(e -> {
            wines.insert(wine);
            wines.saveToFile(wineFile.getName());
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
     /** Load in hash table from save file */
     public UserHashTable loadUsersFromFile(String filename) {
         UserHashTable loadedTable = null;
         try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {

             loadedTable = (UserHashTable) in.readObject();

         } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
         }
         return loadedTable;
     }

     /** Load in wine tree from save file */
     public WineTree loadWinesFromFile(String filename) {
         WineTree loadedTree = null;
         try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {

             loadedTree = (WineTree) in.readObject();

         } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
         }

         return loadedTree;
     }

}
