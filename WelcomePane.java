// **********************************************************************************
// Title: Welcome Pane
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: WelcomePane.java
// Description: Creates a welcome page which gives information about the program and
//    provides buttons for signing in or creating a new account
// **********************************************************************************
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;


public class WelcomePane extends BorderPane {
    Button btSignIn = new Button("Sign In");
    Button btCreateAccount = new Button("Create Account");

    public WelcomePane() {
        setStyle("-fx-background-color: rgba(117, 28, 28, 0.8)");

        HBox titleLine = new HBox(20);
        titleLine.setAlignment(Pos.CENTER);
        Text welcome = new Text("Welcome to the Wine Wizard");
        welcome.setFill(Color.rgb(234, 219, 177, 0.8));
        welcome.setFont(Font.font("Vivaldi", FontWeight.EXTRA_BOLD,
                FontPosture.REGULAR, 56));
        ImageView wineGlasses = new ImageView(new Image("images/wine.png", 80, 80,
                true, false));
        titleLine.getChildren().addAll(welcome, wineGlasses);
        setTop(titleLine);
        setAlignment(titleLine, Pos.CENTER);
        setMargin(titleLine, new Insets(30, 20, 20, 20));

        VBox centerBox = new VBox(30);
        centerBox.setAlignment(Pos.CENTER);
        Text message = new Text(
                     "This program is designed to help wine vendors showcase their inventory in a user-friendly and\n" +
                        "visually appealing format for their consumers. Vendors can easily input information about their\n" +
                        "wine, including grape, region, vintage, ABV%, bottle & glass prices, sweetness level, tasting\n" +
                        " notes, and food pairings, and the Wine Wizard takes care of the rest.\n\n" +
                        "Consumers can browse through the vendor's wine selection and keep track of their favorite\n" +
                        "wines. The wines can be sorted by color, grape, sweetness level, price, or popularity, making\n" +
                        "it easy for consumers to find what they're looking for. Additionally, the search function allows\n" +
                        "consumers to find specific wines by color, grape, producer, or region.\n\n" +
                        "With this program, vendors can efficiently manage their inventory and provide consumers with\n" +
                        "a seamless experience, ultimately boosting sales and customer satisfaction.");
        message.setFont(Font.font("Georgia", FontWeight.MEDIUM, FontPosture.REGULAR, 22));
        message.setFill(Color.rgb(32, 3, 3, 0.8));

        Text loginPrompt = new Text("To get started, please sign in or create a new account.");
        loginPrompt.setFont(Font.font("Arial", FontWeight.MEDIUM, FontPosture.ITALIC, 30));
        loginPrompt.setFill(Color.rgb(240, 174, 174, 0.54));

        centerBox.getChildren().addAll(message, loginPrompt);
        setCenter(centerBox);
        setMargin(centerBox, new Insets(10, 50, 20, 50));

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        setDefaultButtonStyle(btSignIn);
        btSignIn.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 20));
        setDefaultButtonStyle(btCreateAccount);
        btCreateAccount.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 20));
        btSignIn.setMinWidth(250);
        btCreateAccount.setMinWidth(250);

        buttonBox.getChildren().addAll(btSignIn, btCreateAccount);
        setBottom(buttonBox);
        setAlignment(buttonBox, Pos.CENTER);
        setMargin(buttonBox, new Insets(0, 50, 50, 50));

        // Buttons darken as mouse enters
        btSignIn.setOnMouseEntered(e -> darkenButton(btSignIn));
        btSignIn.setOnMouseExited(e -> setDefaultButtonStyle(btSignIn));
        btCreateAccount.setOnMouseEntered(e -> darkenButton(btCreateAccount));
        btCreateAccount.setOnMouseExited(e -> setDefaultButtonStyle(btCreateAccount));

    }
    /** Get buttons */
    public Button getBtSignIn() {
        return btSignIn;
    }

    public Button getBtCreateAccount() {
        return btCreateAccount;
    }

    /** Set default button style */
    public static void setDefaultButtonStyle(Button button) {
        button.setStyle("-fx-background-color: rgba(226, 175, 114, 0.69);" +
                "-fx-text-fill: rgba(119, 78, 11, 0.94)");
    }

    /** Darken button style */
    public static void darkenButton(Button button) {
        button.setStyle("-fx-background-color: rgba(178, 133, 78, 0.69);" +
                "-fx-text-fill: rgba(210, 165, 91, 0.94)");
    }

}
