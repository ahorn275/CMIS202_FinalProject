import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class MenuPane extends BorderPane {
    private Button btWinesByColor = new Button("Wines By Color");
    private Button btWinesByGrape = new Button("Wines By Grape");
    private Button btFavorites = new Button("Favorite Wines");
    private Button btAllWines = new Button("All Wines");
    private Button btSearchWines = new Button("Search Wines");
    private Button btAccountManagement = new Button("Manage Account");
    private Button btAdmin = new Button("Amin Tools");

    /** Constructor */
    public MenuPane(User user) {
        setStyle("-fx-background-color: rgba(209, 197, 181, 0.96)");
        setPadding(new Insets(15));
        
        // Greeting and instructions
        VBox heading = new VBox(5);
        Text greeting = new Text("Hello, " + user.getName() + "!");
        greeting.setFont(Font.font("Vivaldi", FontWeight.EXTRA_BOLD,
                FontPosture.REGULAR, 52));
        greeting.setFill(Color.rgb(126, 18, 18, 0.96));
        greeting.setTextAlignment(TextAlignment.CENTER);
        Text instructions = new Text("Please select from the options below:");
        instructions.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD,
                FontPosture.ITALIC, 20));
        instructions.setFill(Color.rgb(125, 17, 17, 0.74));
        instructions.setTextAlignment(TextAlignment.CENTER);
        heading.getChildren().addAll(greeting, instructions);
        heading.setAlignment(Pos.CENTER);
        setTop(heading);
        setAlignment(heading, Pos.TOP_CENTER);

        // Center box for buttons and wine picture
        VBox selections = new VBox(10);
        selections.setAlignment(Pos.CENTER);
        
        // Style buttons and have them darken when mouse enters them
        setDefaultButtonStyle(btWinesByColor);
        btWinesByColor.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 22));
        btWinesByColor.setMinWidth(300);
        btWinesByColor.setOnMouseEntered(e -> darkenButtonStyle(btWinesByColor));
        btWinesByColor.setOnMouseExited(e -> setDefaultButtonStyle(btWinesByColor));

        setDefaultButtonStyle(btWinesByGrape);
        btWinesByGrape.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 22));
        btWinesByGrape.setMinWidth(300);
        btWinesByGrape.setOnMouseEntered(e -> darkenButtonStyle(btWinesByGrape));
        btWinesByGrape.setOnMouseExited(e -> setDefaultButtonStyle(btWinesByGrape));

        setDefaultButtonStyle(btFavorites);
        btFavorites.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 22));
        btFavorites.setMinWidth(300);
        btFavorites.setOnMouseEntered(e -> darkenButtonStyle(btFavorites));
        btFavorites.setOnMouseExited(e -> setDefaultButtonStyle(btFavorites));

        setDefaultButtonStyle(btAllWines);
        btAllWines.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 22));
        btAllWines.setMinWidth(300);
        btAllWines.setOnMouseEntered(e -> darkenButtonStyle(btAllWines));
        btAllWines.setOnMouseExited(e -> setDefaultButtonStyle(btAllWines));

        setDefaultButtonStyle(btSearchWines);
        btSearchWines.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 22));
        btSearchWines.setMinWidth(300);
        btSearchWines.setOnMouseEntered(e -> darkenButtonStyle(btSearchWines));
        btSearchWines.setOnMouseExited(e -> setDefaultButtonStyle(btSearchWines));

        setDefaultManageButtonStyle(btAccountManagement);
        btAccountManagement.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16));
        btAccountManagement.setMinWidth(200);
        btAccountManagement.setOnMouseEntered(e -> darkenManageButtonStyle(btAccountManagement));
        btAccountManagement.setOnMouseExited(e -> setDefaultManageButtonStyle(btAccountManagement));

        setDefaultManageButtonStyle(btAdmin);
        btAdmin.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16));
        btAdmin.setMinWidth(200);
        btAdmin.setOnMouseEntered(e -> darkenManageButtonStyle(btAdmin));
        btAdmin.setOnMouseExited(e -> setDefaultManageButtonStyle(btAdmin));

        selections.getChildren().addAll(btWinesByColor, btWinesByGrape, btFavorites, btAllWines, btSearchWines);

        setCenter(selections);
        Image wineGlass = new Image("images/wineGlass.png", 400, 750, true, false);
        setRight(new ImageView(wineGlass));

        Image wineGlass2 = new Image("images/wineGlass2.png", 400, 750, true, false);
        setLeft(new ImageView(wineGlass2));


        // Buttons on bottom for account management and admin tools
        BorderPane bottomBox = new BorderPane();
        bottomBox.setRight(btAccountManagement);
        if (user instanceof Admin)
            bottomBox.setLeft(btAdmin);
        setBottom(bottomBox);
    }
    
    /** Accessors */
    public Button getBtWinesByColor() {
        return btWinesByColor;
    }

    public Button getBtWinesByGrape() {
        return btWinesByGrape;
    }

    public Button getBtFavorites() {
        return btFavorites;
    }

    public Button getBtAllWines() {
        return btAllWines;
    }

    public Button getBtSearchWines() {
        return btSearchWines;
    }

    public Button getBtAccountManagement() {
        return btAccountManagement;
    }
    
    public Button getBtAdmin() {
        return btAdmin;
    }

    /** Set button's default style and darkened style for when mouse enters */
    private void setDefaultButtonStyle(Button button) {
        button.setStyle("-fx-background-color: rgba(149, 61, 30, 0.8); " +
                "-fx-text-fill: rgba(246, 229, 208, 0.96)");
    }

    private void darkenButtonStyle(Button button) {
        button.setStyle("-fx-background-color: rgba(185, 41, 41, 0.8);" +
                "-fx-text-fill: rgba(246, 229, 208, 0.96)");
    }

    private void setDefaultManageButtonStyle(Button button) {
        button.setStyle("-fx-background-color: rgba(128, 64, 9, 0.87); " +
                "-fx-text-fill: rgba(255, 246, 238, 0.87)");
    }

    private void darkenManageButtonStyle(Button button) {
        button.setStyle("-fx-background-color: rgba(167, 78, 0, 0.87); " +
                "-fx-text-fill: rgba(255, 246, 238, 0.87)");
    }
}
