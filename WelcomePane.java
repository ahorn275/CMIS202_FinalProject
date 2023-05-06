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
                     "This program acts as a database for wine vendors to categorize and display their inventories\n" +
                        "in a convenient and appealing format for their consumers. Vendors can input their wines into\n" +
                        "the system, including description fields such as the grape, region, vintage, ABV%, bottle and\n" +
                        "glass prices, sweetness level, tasting notes, and food pairings for that particular wine, and\n" +
                        "the Wine Wizard does all the rest! Consumers are able to see all that the vendor has to offer,\n" +
                        "and even keep track of all of their favorite wines.\n\n" +
                        "Wines can be sorted by color, grape, sweetness level, price, or even how many people have\n" +
                        "favorited them. Wines can also be searched by color, grape, producer, or region, so if the\n" +
                        "consumer is looking for something specific it will be easy to find! The Wine Wizard is a\n" +
                        "great tool for both vendors and consumers alike as the ease and accessibility will keep\n" +
                        "customers coming back and favoriting capabilities allow vendors to better understand their\n" +
                        "customers want!\n\n" +
                        "We are very excited for you to join us! To get started, please sign in or create an account below.");
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
