import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class CreateAccountPane extends BorderPane {
    private TextField tfName = new TextField();
    private TextField tfUserName = new TextField();
    private PasswordField tfPassword = new PasswordField();
    private PasswordField tfConfirmPassword = new PasswordField();
    private PasswordField tfPin = new PasswordField();
    private PasswordField tfConfirmPin = new PasswordField();
    private Button btCreateAccount = new Button("Create Account");
    private Button btBack = new Button("Go back");
    private Text userNameError = new Text();
    private Text passwordError = new Text();
    private Text pinError = new Text();
    private String finalUsername;
    private String finalPassword;
    private String finalPin;

    /** Create a Create Account Pane, which includes fields for a 4-digit pin if creating an Admin account */
    public CreateAccountPane(boolean isAdmin) {
        setStyle("-fx-background-color: rgba(255, 246, 244, 0.8)");
        setPadding(new Insets(10));

        // Heading for welcome and instructions
        VBox heading = new VBox(5);
        Text welcome = new Text("Welcome, New User!");
        welcome.setFont(Font.font("Algerian", FontWeight.NORMAL,
                FontPosture.REGULAR, 54));
        welcome.setFill(Color.rgb(149, 111, 25, 0.96));
        // Admin instructions include 4-digit pin
        Text instructions = new Text((isAdmin) ?
                "Please choose a username and password for your account. As an admin of the Wine Wizard, you will\n" +
                "also need to choose a 4-digit pin. Your password should be at least 8 characters long, consist of\n" +
                "uppercase and lowercase letters, and include at least one number and one special character." :
                "Please choose a username and password for your account. Your password should be at least\n" +
                "eight characters long, consist of uppercase and lowercase letters, and include at least one\n" +
                "number and one special character."
        );
        instructions.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.ITALIC, 16));
        instructions.setTextAlignment(TextAlignment.CENTER);
        instructions.setFill(Color.rgb(151, 100, 96, 0.96));
        heading.getChildren().addAll(welcome, instructions);
        heading.setAlignment(Pos.CENTER);
        setTop(heading);
        setAlignment(heading, Pos.TOP_CENTER);

        // Center of pane consists of fields to enter name, username, password, and pin (if admin)
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        HBox inputBox = new HBox(20);
        inputBox.setAlignment(Pos.CENTER);

        // Name input
        Text name = new Text("Name");
        setInputHeadingStyle(name);
        setTfStyle(tfName);
        // Username input
        Text username = new Text("Username");
        setInputHeadingStyle(username);
        setTfStyle(tfUserName);
        // Password input
        Text password = new Text("Password");
        setInputHeadingStyle(password);
        setTfStyle(tfPassword);
        Text confirmPassword = new Text("Confirm Password");
        setInputHeadingStyle(confirmPassword);
        setTfStyle(tfConfirmPassword);
        // Pin input
        Text pin = new Text("Pin");
        setInputHeadingStyle(pin);
        setTfStyle(tfPin);
        Text confirmPin = new Text("Confirm Pin");
        setInputHeadingStyle(confirmPin);
        setTfStyle(tfConfirmPin);

        // Left column for name, password, and pin inputs (if admin)
        VBox leftBox = new VBox(10);
        leftBox.getChildren().addAll(name, tfName, password, tfPassword);
        if (isAdmin)
            leftBox.getChildren().addAll(pin, tfPin);
        leftBox.setAlignment(Pos.CENTER);

        // Right column for username and confirmation of password and pin inputs (if admin)
        VBox rightBox = new VBox(10);
        rightBox.getChildren().addAll(username, tfUserName, confirmPassword, tfConfirmPassword);
        if (isAdmin)
            rightBox.getChildren().addAll(confirmPin, tfConfirmPin);
        rightBox.setAlignment(Pos.CENTER);

        // Create Account button
        btCreateAccount.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        setDefaultCAButton();
        btCreateAccount.setOnMouseEntered(e -> darkenCAButton());
        btCreateAccount.setOnMouseExited(e -> setDefaultCAButton());

        // Error messages
        setErrorStyle(userNameError);
        setErrorStyle(passwordError);
        setErrorStyle(pinError);

        // Wine photo above input fields
        Image wineArt = new Image("images/wineArt.png", 250, 120, true, false);
        inputBox.getChildren().addAll(leftBox, rightBox);
        centerBox.getChildren().addAll(new ImageView(wineArt), inputBox, btCreateAccount, userNameError,
                passwordError);
        if (isAdmin)
            centerBox.getChildren().add(pinError);
        setCenter(centerBox);

        // Back button
        btBack.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 16));
        setDefaultBackButton();
        btBack.setMinWidth(150);
        btBack.setOnMouseEntered(e -> darkenBackButton());
        btBack.setOnMouseExited(e -> setDefaultBackButton());
        setBottom(btBack);
        setAlignment(btBack, Pos.CENTER_RIGHT);
    }

    /** Accessors */
    public TextField getTfName() {
        return tfName;
    }

    public TextField getTfUserName() {
        return tfUserName;
    }

    public PasswordField getTfPassword() {
        return tfPassword;
    }

    public PasswordField getTfConfirmPassword() {
        return tfConfirmPassword;
    }

    public PasswordField getTfPin() {
        return tfPin;
    }

    public PasswordField getTfConfirmPin() {
        return tfConfirmPin;
    }

    public Button getBtCreateAccount() {
        return btCreateAccount;
    }

    public Button getBtBack() {
        return btBack;
    }

    public Text getPasswordError() {
        return passwordError;
    }

    public Text getPinError() {
        return pinError;
    }

    public String getFinalUsername() {
        return finalUsername;
    }

    public String getFinalPassword() {
        return finalPassword;
    }

    public String getFinalPin() {
        return finalPin;
    }

    /** Text style for input headings */
    private void setInputHeadingStyle(Text text) {
        text.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        text.setFill(Color.rgb(134, 76, 6, 0.96));
    }

    /** Style for Text Fields */
    private void setTfStyle(TextField textField) {
        textField.setMaxWidth(250);
        textField.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        textField.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.74)");
    }

    /** Style for error messages */
    private void setErrorStyle(Text text) {
        text.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.ITALIC, 12));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.rgb(151, 100, 96, 0.96));
    }

    /** Default style for Create Account button */
    private void setDefaultCAButton() {
        btCreateAccount.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)");
    }

    /** Darken Create Account button */
    private void darkenCAButton() {
        btCreateAccount.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)");
    }

    /** Default style for Back button */
    private void setDefaultBackButton() {
        btBack.setStyle("-fx-background-color: rgba(186, 130, 62, 0.8);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)");
    }

    /** Darken Back button */
    private void darkenBackButton() {
        btBack.setStyle("-fx-background-color: rgba(176, 108, 25, 0.8);" +
                "-fx-text-fill: rgba(255, 251, 247, 0.8)");
    }

    /** Validate that username is unique and show error message if not */
    public boolean validateUserName(UserHashTable users) {
        String username = tfUserName.getText();
        if (users.containsUsername(username)) {
            userNameError.setText("Sorry, that username is already taken.");
            return false;
        }
        finalUsername = username;
        userNameError.setText("");
        return true;
    }

    /** Validate password and show error message if incorrect */
    public boolean validatePassword() {
        String password = tfPassword.getText();
        String confirmPassword = tfConfirmPassword.getText();

        // Passwords must match
        if (!password.equals(confirmPassword)) {
            passwordError.setText("Please make sure your passwords match.");
            return false;
        }
        // Password must be at least 8 characters
        if (password.length() < 8) {
            passwordError.setText("Please make sure your password is at least 8 characters.");
            return false;
        }
        // Password must have both uppercase and lowercase characters
        if (!password.matches("(.*[A-Z].*)") || !password.matches("(.*[a-z].*)")) {
            passwordError.setText("Please make sure your password has both uppercase and lowercase characters.");
            return false;
        }
        // Password must have at least one number
        if (!password.matches("(.*[0-9].*)")) {
            passwordError.setText("Please make sure your password has at least one number.");
            return false;
        }
        // Password must have at least one special character
        if (!password.matches("(.*[@,#,$,%,!].*$)")) {
            passwordError.setText("Please make sure your password has at least one special character " +
                    "(@, #, $, %, !).");
            return false;
        }

        finalPassword = password;
        passwordError.setText("");
        return true;
    }

    /** Validate pin and show error message if incorrect */
    public boolean validatePin() {
        String pin = tfPin.getText();
        String confirmPin = tfConfirmPin.getText();

        // Pins must match
        if (!pin.equals(confirmPin)) {
            pinError.setText("Please make sure your pins match.");
            return false;
        }
        // Pin must be four digits
        if (pin.length() != 4) {
            pinError.setText("Please make sure your pin is four digits.");
            return false;
        }
        // Pin must consist of only numbers
        if (!pin.matches("(^[0-9]*$)")) {
            pinError.setText("Please make sure your pin consists of numbers only.");
            return false;
        }

        finalPin = pin;
        pinError.setText("");
        return true;
    }

}
