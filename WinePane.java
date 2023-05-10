// **********************************************************************************
// Title: Wine Pane
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: WinePane.java
// Description: Creates a Stack Pane for displaying the photo and description of a
//    wine object. It also contains methods for toggling the heart icon on mouse click
//    and adding/removing the wine from the user's list of favorite wines. Clickable
//    text is added to the wine pane for admins for editing or removing a wine.
// **********************************************************************************
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class WinePane extends StackPane {
    private VBox mainBox = new VBox(15);
    private Wine wine;
    private Image unfilledHeart = new Image("images/unfilledHeart.png", 20, 20, true, false);
    private Image filledHeart = new Image("images/filledHeart.png", 20, 20, true, false);
    private ImageView heartView = new ImageView();
    private boolean heartFilled;
    private Text favoriteText = new Text("");
    private Text editText = new Text("Edit Wine");
    private Text deleteText = new Text("Delete Wine");

    /** Create a standard pane for a wine with no specific interactions for a user */
    public WinePane(Wine wine) {
        this.wine = wine;
        Image wineImage= new Image(wine.getImageFileName(), 150, 150, true, false);
        setPadding(new Insets(5));
        setStyle("-fx-border-color: rgba(192, 183, 174, 1); -fx-background-color: rgba(255, 248, 242, 0.76);" +
                "-fx-border-width: 2px");

        // Wine name and grape
        mainBox.setAlignment(Pos.CENTER);
        VBox heading = new VBox(5);
        heading.setAlignment(Pos.CENTER);
        Text wineName = new Text(wine.getName());
        wineName.setFont(Font.font("Ink Free", FontWeight.BOLD,
                FontPosture.REGULAR, 18));
        wineName.setUnderline(true);
        wineName.setFill(Color.rgb(125, 17, 17, 0.74));
        Text colorAndProducer = new Text("(" + wine.getColor() + " Wine from " + wine.getProducer() + ")");
        colorAndProducer.setFont(Font.font("Century Schoolbook", FontWeight.NORMAL,
                FontPosture.ITALIC, 14));
        colorAndProducer.setFill(Color.rgb(168, 143, 122, 0.86));
        colorAndProducer.setTextAlignment(TextAlignment.CENTER);
        colorAndProducer.setWrappingWidth(400);
        setAlignment(wineName, Pos.CENTER);
        heading.getChildren().addAll(wineName, colorAndProducer);


        HBox centerBox = new HBox(10);
        centerBox.setAlignment(Pos.CENTER);
        ImageView wineImageView = new ImageView(wineImage);
        VBox descriptionBox = new VBox(10);
        // Grape
        HBox grapeBox = new HBox(5);
        Text grapeHeading = new Text("Grape:");
        setHeadingStyle(grapeHeading);
        Text grapeAttribute = new Text(wine.getGrape());
        setAttributeStyle(grapeAttribute);
        grapeBox.getChildren().addAll(grapeHeading, grapeAttribute);
        // Region
        HBox regionBox = new HBox(5);
        Text regionHeading = new Text("Region:");
        setHeadingStyle(regionHeading);
        Text regionAttribute = new Text(wine.getRegion());
        setAttributeStyle(regionAttribute);
        regionBox.getChildren().addAll(regionHeading, regionAttribute);
        // Vintage and ABV
        HBox vintageAndABVBox = new HBox(5);
        Text vintageHeading = new Text("Vintage:");
        setHeadingStyle(vintageHeading);
        Text vintageAttribute = new Text((wine.getVintage() == 0)? "N/A": String.valueOf(wine.getVintage()));
        setAttributeStyle(vintageAttribute);
        Text abvHeading = new Text("ABV:");
        setHeadingStyle(abvHeading);
        Text abvAttribute = new Text(String.format("%.1f", wine.getAlcoholByVolume()) + "%");
        setAttributeStyle(abvAttribute);
        vintageAndABVBox.getChildren().addAll(vintageHeading, vintageAttribute, abvHeading, abvAttribute);
        // Bottle and glass price
        HBox pricesBox = new HBox(5);
        Text bottlePriceHeading = new Text("Bottle Price:");
        setHeadingStyle(bottlePriceHeading);
        Text bottlePriceAttribute = new Text("$" + String.valueOf(wine.getBottlePrice()));
        setAttributeStyle(bottlePriceAttribute);
        Text glassPriceHeading = new Text("Glass Price:");
        setHeadingStyle(glassPriceHeading);
        Text glassPriceAttribute = new Text("$" + String.valueOf(wine.getGlassPrice()));
        setAttributeStyle(glassPriceAttribute);
        pricesBox.getChildren().addAll(bottlePriceHeading, bottlePriceAttribute, glassPriceHeading,
                glassPriceAttribute);
        // Sweetness
        HBox sweetnessBox = new HBox(5);
        Text sweetnessHeading = new Text("Sweetness:");
        setHeadingStyle(sweetnessHeading);
        Text sweetnessAttribute = new Text(wine.getSweetness());
        setAttributeStyle(sweetnessAttribute);
        sweetnessBox.getChildren().addAll(sweetnessHeading, sweetnessAttribute);
        // Tasting Notes
        VBox tastingNotesBox = new VBox(5);
        Text tastingNotesHeading = new Text("Tasting Notes:");
        setHeadingStyle(tastingNotesHeading);
        Text tastingNotesAttribute = new Text(wine.getTastingNotes());
        setAttributeStyle(tastingNotesAttribute);
        tastingNotesAttribute.setWrappingWidth(300);
        tastingNotesBox.getChildren().addAll(tastingNotesHeading, tastingNotesAttribute);
        // Pairings
        VBox pairingsBox = new VBox(5);
        Text pairingsHeading = new Text("Pairings:");
        setHeadingStyle(pairingsHeading);
        Text pairingsAttribute = new Text(wine.getPairings());
        setAttributeStyle(pairingsAttribute);
        pairingsAttribute.setWrappingWidth(300);
        pairingsBox.getChildren().addAll(pairingsHeading, pairingsAttribute);

        descriptionBox.getChildren().addAll(grapeBox, regionBox, vintageAndABVBox, pricesBox, sweetnessBox,
                tastingNotesBox, pairingsBox);
        descriptionBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(wineImageView, descriptionBox);
        mainBox.getChildren().addAll(heading, centerBox, favoriteText);
        getChildren().add(mainBox);
        setAlignment(mainBox, Pos.CENTER);
    }

    /** Create a Wine Pane with a heart image for the user to favorite the wine and edit/delete buttons for admins */
    public WinePane (Wine wine, User user) {
        this(wine);
        // Whether heart is filled depends on if wine is in user's favorited wines list
        heartFilled = (user.getFavoriteWines().contains(wine))? true : false;
        heartView.setImage((heartFilled)? filledHeart: unfilledHeart);
        heartView.setStyle("-fx-cursor: hand");
        HBox favoritesBox = new HBox(5, heartView, favoriteText);
        favoritesBox.setAlignment(Pos.CENTER);
        updateFavoriteText(wine);
        favoriteText.setFont(Font.font("Sitka Text", FontWeight.NORMAL,
                FontPosture.ITALIC, 10));
        favoriteText.setFill(Color.rgb(134, 76, 6, 0.96));
        mainBox.getChildren().add(favoritesBox);

        // Clicking the heart will favorite or unfavorite the wine and fill or unfill the heart
        //   depending on its current status
        heartView.setOnMouseClicked(e -> {
            if (heartFilled)
                unfillHeart(wine, user);
            else
                fillHeart(wine, user);
            updateFavoriteText(wine);
        });

        // Edit and delete buttons for admin
        HBox editBox = new HBox(10, editText, deleteText);
        editBox.setAlignment(Pos.CENTER);
        setAttributeStyle(editText);
        setAttributeStyle(deleteText);
        editText.setUnderline(true);
        deleteText.setUnderline(true);
        editText.setFill(Color.rgb(125, 17, 17, 0.74));
        deleteText.setFill(Color.rgb(125, 17, 17, 0.74));
        editText.setStyle("-fx-cursor: hand");
        deleteText.setStyle("-fx-cursor: hand");
        if (user instanceof Admin)
            mainBox.getChildren().add(editBox);

    }

    /** Getters */
    public Text getEditText() {
        return editText;
    }

    public Text getDeleteText() {
        return deleteText;
    }

    /** Set headings style */
    private void setHeadingStyle(Text text) {
        text.setFont(Font.font("Sitka Text", FontWeight.BOLD,
                FontPosture.REGULAR, 12));
        text.setFill(Color.rgb(134, 76, 6, 0.96));
    }

    /** Set attribute text style */
    private void setAttributeStyle(Text text) {
        text.setFont(Font.font("Sitka Text", FontWeight.NORMAL,
                FontPosture.REGULAR, 12));
        text.setFill(Color.rgb(0, 0, 0, 0.74));
    }

    /** Fill heart and add wine to user's favorites */
    private void fillHeart(Wine wine, User user) {
        heartView.setImage(filledHeart);
        user.addFavoriteWine(wine);
        wine.incrementFavorites();
        heartFilled = true;
    }

    /** Unfill heart and delete wine from user's favorites */
    private void unfillHeart(Wine wine, User user) {
        heartView.setImage(unfilledHeart);
        user.unFavoriteWine(wine);
        wine.decrementFavorites();
        heartFilled = false;
    }

    /** Update favorite text to reflect how many users have favorited this wine */
    private void updateFavoriteText(Wine wine) {
        int favorites = wine.getFavorites();
        favoriteText.setText(favorites + ((favorites == 1)? " favorite": " favorites"));
    }

}
