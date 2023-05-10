// **********************************************************************************
// Title: Wine Manage Pane
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: WineManagePane.java
// Description: Creates a page for either adding a new wine or editing an already created
//     wine. It also validates the user's inputs.
// **********************************************************************************
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.Year;

public class WineManagePane extends BorderPane {
    private Text title = new Text();
    private Text instructions = new Text();
    private TextField tfName = new TextField();
    private TextField tfProducer = new TextField();
    private ComboBox<String> cboColors = new ComboBox<>();
    private RadioButton rbYes = new RadioButton("Yes");
    private RadioButton rbNo = new RadioButton("No");
    private TextField tfGrape = new TextField();
    private TextField tfRegion = new TextField();
    private TextField tfVintage = new TextField();
    private TextField tfABV = new TextField();
    private TextField tfBottlePrice = new TextField();
    private TextField tfGlassPrice = new TextField();
    private ComboBox<String> cboSweetness = new ComboBox<>();
    private TextArea taTastingNotes = new TextArea();
    private TextArea taPairings = new TextArea();
    private Button btChooseImage = new Button("Choose an Image");
    private Text imagePath = new Text("");
    private Button btAddWine = new Button("Add Wine");
    private Button btBack = new Button("Go Back");
    private Text errorMessage = new Text("");
    private int finalVintage;
    private double finalABV;
    private int finalBottlePrice;
    private int finalGlassPrice;

    /** A pane for adding a new wine */
    public WineManagePane() {
        setStyle("-fx-background-color: rgba(255, 246, 244, 0.8)");
        setPadding(new Insets(10));

        // Heading for welcome and instructions
        VBox heading = new VBox(5);
        title.setText("Add A Wine");
        title.setFont(Font.font("Algerian", FontWeight.NORMAL,
                FontPosture.REGULAR, 54));
        title.setFill(Color.rgb(149, 111, 25, 0.96));
        // Admin instructions include 4-digit pin
        instructions.setText("To add a new wine, please fill out the fields below. If vintage is unknown " +
            "or inapplicable, please input N/A.");
        instructions.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.ITALIC, 16));
        instructions.setTextAlignment(TextAlignment.CENTER);
        instructions.setFill(Color.rgb(151, 100, 96, 0.96));
        heading.getChildren().addAll(title, instructions);
        heading.setAlignment(Pos.CENTER);
        setTop(heading);
        setAlignment(heading, Pos.TOP_CENTER);

        VBox centerBox = new VBox(10);

        // Grid pane for input fields
        GridPane inputPane = new GridPane();
        inputPane.setHgap(10);
        inputPane.setVgap(10);

        // Name input
        Text name = new Text("Name:");
        setInputHeadingStyle(name);
        setTfStyle(tfName);
        inputPane.addRow(0, name, tfName);
        // Producer input
        Text producer = new Text("Producer:");
        setInputHeadingStyle(producer);
        setTfStyle(tfProducer);
        inputPane.addRow(1, producer, tfProducer);
        // Color input from combo box
        HBox colorAndSparkleBox = new HBox(20);
        Text color = new Text("Color:");
        setInputHeadingStyle(color);
        String[] colors = Wine.COLORS;
        cboColors.setValue(colors[0]);
        ObservableList<String> colorItems = FXCollections.observableArrayList(colors);
        cboColors.getItems().addAll(colorItems);
        cboColors.setStyle("-fx-font: 16px \"Georgia\";" +
                "-fx-text-fill: rgba(0, 0, 0, 0.74)");
        // Sparkling input through radio buttons
        Text sparkling = new Text("Sparkling?");
        setInputHeadingStyle(sparkling);
        HBox sparklingBox = new HBox(10);
        rbYes.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 16));
        rbNo.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 16));
        ToggleGroup sparklingGroup = new ToggleGroup();
        rbYes.setToggleGroup(sparklingGroup);
        rbNo.setToggleGroup(sparklingGroup);
        sparklingBox.getChildren().addAll(rbYes, rbNo);
        colorAndSparkleBox.getChildren().addAll(cboColors, sparkling, sparklingBox);
        inputPane.addRow(2, color, colorAndSparkleBox);
        // Grape and Region inputs
        HBox grapeAndRegionBox = new HBox(20);
        Text grape = new Text("Grape:");
        setInputHeadingStyle(grape);
        setTfStyle(tfGrape);
        tfGrape.setMaxWidth(250);
        // Region input
        Text region = new Text("Region:");
        setInputHeadingStyle(region);
        setTfStyle(tfRegion);
        tfRegion.setMinWidth(350);
        grapeAndRegionBox.getChildren().addAll(tfGrape, region, tfRegion);
        inputPane.addRow(3, grape, grapeAndRegionBox);
        // Vintage, ABV, bottle and glass price input
        HBox numberInputBox = new HBox(20);
        Text vintage = new Text("Vintage:");
        setInputHeadingStyle(vintage);
        setTfStyle(tfVintage);
        tfVintage.setMaxWidth(75);
        Text abv = new Text("ABV%:");
        setInputHeadingStyle(abv);
        setTfStyle(tfABV);
        tfABV.setMaxWidth(75);
        // Bottle and glass price inputs
        Text bottlePrice = new Text("Bottle price:");
        setInputHeadingStyle(bottlePrice);
        setTfStyle(tfBottlePrice);
        tfBottlePrice.setMaxWidth(75);
        Text glassPrice = new Text("Glass price:");
        setInputHeadingStyle(glassPrice);
        setTfStyle(tfGlassPrice);
        tfGlassPrice.setMaxWidth(75);
        numberInputBox.getChildren().addAll(tfVintage, abv, tfABV, bottlePrice, tfBottlePrice,
                glassPrice, tfGlassPrice);
        inputPane.addRow(4, vintage, numberInputBox);
        // Sweetness input from combo box
        Text sweetness = new Text("Sweetness:");
        setInputHeadingStyle(sweetness);
        String[] sweetLevels = Wine.SWEETNESS_LEVELS;
        cboSweetness.setValue(sweetLevels[0]);
        ObservableList<String> sweetItems = FXCollections.observableArrayList(sweetLevels);
        cboSweetness.getItems().addAll(sweetItems);
        cboSweetness.setStyle("-fx-font: 16px \"Georgia\";" +
                "-fx-text-fill: rgba(0, 0, 0, 0.74)");
        inputPane.addRow(5, sweetness, cboSweetness);
        // Tasting notes input
        Text tastingNotes = new Text("Tasting Notes:");
        setInputHeadingStyle(tastingNotes);
        taTastingNotes.setPrefColumnCount(30);
        taTastingNotes.setPrefRowCount(3);
        taTastingNotes.setWrapText(true);
        taTastingNotes.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        taTastingNotes.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.74)");
        inputPane.addRow(6, tastingNotes, taTastingNotes);
        // Pairings input
        Text pairings = new Text("Pairings:");
        setInputHeadingStyle(pairings);
        taPairings.setPrefColumnCount(30);
        taPairings.setPrefRowCount(2);
        taPairings.setWrapText(true);
        taPairings.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        taPairings.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.74)");
        inputPane.addRow(7, pairings, taPairings);
        // Photo for wine
        Text wineImageHeading = new Text("Wine Image:");
        setInputHeadingStyle(wineImageHeading);
        FileChooser photoChooser = new FileChooser();
        photoChooser.setTitle("Select Image");
        photoChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        HBox imageButtonAndPathBox = new HBox(20);
        btChooseImage.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 16));
        imagePath.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        imagePath.setFill(Color.rgb(102, 41, 36, 0.96));
        imageButtonAndPathBox.getChildren().addAll(btChooseImage, imagePath);
        // Display path of chosen image with imagePath Text
        btChooseImage.setOnAction(e -> {
                File selectedFile = photoChooser.showOpenDialog(new Stage());
                if (selectedFile != null)
                    imagePath.setText(selectedFile.getPath());
        });
        inputPane.addRow(8, wineImageHeading, imageButtonAndPathBox);

        // Error message
        errorMessage.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.ITALIC, 14));
        errorMessage.setTextAlignment(TextAlignment.CENTER);
        errorMessage.setFill(Color.rgb(151, 100, 96, 0.96));

        // Add Wine button
        setDefaultAddWineButton();
        btAddWine.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 18));
        btAddWine.setOnMouseEntered(e -> darkenAddWineButton());
        btAddWine.setOnMouseExited(e -> setDefaultAddWineButton());

        centerBox.getChildren().addAll(inputPane, btAddWine, errorMessage);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);

        // Wine glass image on left for decoration
        Image wineGlass = new Image("images/wineGlass2.png", 400, 750, true, false);
        setLeft(new ImageView(wineGlass));

        // Back button on bottom right
        btBack.setFont(Font.font("Georgia", FontWeight.BOLD, FontPosture.REGULAR, 16));
        setDefaultBackButton();
        btBack.setMinWidth(150);
        btBack.setOnMouseEntered(e -> darkenBackButton());
        btBack.setOnMouseExited(e -> setDefaultBackButton());
        setBottom(btBack);
        setAlignment(btBack, Pos.CENTER_RIGHT);

    }

    /** A pane for editing an already established wine */
    public WineManagePane(Wine wine) {
        this();
        // Set title, instructions, and button to reflect edit action
        title.setText("Edit Wine");
        instructions.setText("Please change the field(s) you would like to edit below.");
        btAddWine.setText("Save Changes");

        // Fill text fields with the wine's previously entered attributes
        tfName.setText(wine.getName());
        tfProducer.setText(wine.getProducer());
        cboColors.setValue(wine.getColor().replaceAll("^Sparkling ", ""));
        if (wine.isSparkling())
            rbYes.setSelected(true);
        else
            rbNo.setSelected(false);
        tfGrape.setText(wine.getGrape());
        tfRegion.setText(wine.getRegion());
        int savedVintage = wine.getVintage();
        tfVintage.setText((savedVintage == 0)? "N/A": String.valueOf(savedVintage));
        tfABV.setText(String.format("%.1f", wine.getAlcoholByVolume()));
        tfBottlePrice.setText(String.valueOf(wine.getBottlePrice()));
        tfGlassPrice.setText(String.valueOf(wine.getGlassPrice()));
        cboSweetness.setValue(wine.getSweetness());
        taTastingNotes.setText(wine.getTastingNotes());
        taPairings.setText(wine.getPairings());
        imagePath.setText(wine.getImageFileName());
    }

    /** Getters */
    public TextField getTfName() {
        return tfName;
    }
    public TextField getTfProducer() {
        return tfProducer;
    }
    public ComboBox<String> getCboColors() {
        return cboColors;
    }
    public RadioButton getRbYes() {
        return rbYes;
    }
    public RadioButton getRbNo() {
        return rbNo;
    }
    public TextField getTfGrape() {
        return tfGrape;
    }
    public TextField getTfRegion() {
        return tfRegion;
    }
    public TextField getTfVintage() {
        return tfVintage;
    }
    public TextField getTfABV() {
        return tfABV;
    }
    public TextField getTfBottlePrice() {
        return tfBottlePrice;
    }
    public TextField getTfGlassPrice() {
        return tfGlassPrice;
    }
    public ComboBox<String> getCboSweetness() {
        return cboSweetness;
    }
    public TextArea getTaTastingNotes() {
        return taTastingNotes;
    }
    public TextArea getTaPairings() {
        return taPairings;
    }
    public Button getBtChooseImage() {
        return btChooseImage;
    }
    public Text getImagePath() {
        return imagePath;
    }
    public Button getBtAddWine() {
        return btAddWine;
    }
    public Button getBtBack() {
        return btBack;
    }
    public Text getErrorMessage() {
        return errorMessage;
    }
    public int getFinalVintage() {
        return finalVintage;
    }
    public double getFinalABV() {
        return finalABV;
    }
    public int getFinalBottlePrice() {
        return finalBottlePrice;
    }
    public int getFinalGlassPrice() {
        return finalGlassPrice;
    }

    /** Text style for input headings */
    private void setInputHeadingStyle(Text text) {
        text.setFont(Font.font("Bookman Old Style", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        text.setFill(Color.rgb(134, 76, 6, 0.96));
    }

    /** Style for Text Fields */
    private void setTfStyle(TextField textField) {
        textField.setMaxWidth(425);
        textField.setFont(Font.font("Georgia", FontWeight.NORMAL,
                FontPosture.REGULAR, 18));
        textField.setStyle("-fx-text-fill: rgba(0, 0, 0, 0.74)");
    }

    /** Default style for add wine button */
    private void setDefaultAddWineButton() {
        btAddWine.setStyle("-fx-background-color: rgba(178, 125, 88, 0.96);" +
                "-fx-text-fill: rgba(255, 245, 234, 1)");
    }

    /** Darken add wine button */
    private void darkenAddWineButton() {
        btAddWine.setStyle("-fx-background-color: rgba(164, 113, 77, 0.96);" +
                "-fx-text-fill: rgba(255, 245, 234, 1)");
    }

    /** Default style for Back button */
    private void setDefaultBackButton() {
        btBack.setStyle("-fx-background-color: rgba(183, 111, 101, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
    }

    /** Darken Back button */
    private void darkenBackButton() {
        btBack.setStyle("-fx-background-color: rgba(161, 88, 78, 0.96);" +
                "-fx-text-fill: rgba(255, 247, 247, 0.96)");
    }

    /** Validate inputs */
    public boolean validateInputs() {
        // Make sure all fields are filled out
        if (tfName.getText().isEmpty() || tfProducer.getText().isEmpty() || tfGrape.getText().isEmpty() ||
                tfRegion.getText().isEmpty() || tfVintage.getText().isEmpty() || tfABV.getText().isEmpty() ||
                tfBottlePrice.getText().isEmpty() || tfGlassPrice.getText().isEmpty() ||
                taTastingNotes.getText().isEmpty() || taPairings.getText().isEmpty()) {
            errorMessage.setText("Please make sure all entries are filled out.");
            return false;
        }
        // Validate Vintage field
        if (tfVintage.getText().equalsIgnoreCase("N/A"))
            finalVintage = 0;
        else {
                try {
                    finalVintage = Integer.parseInt(tfVintage.getText());
                    if (finalVintage < 1900 || finalVintage > Year.now().getValue()) {
                        errorMessage.setText("Please enter a valid vintage year between 1900 and " +
                                Year.now().getValue());
                        return false;
                    }
                }
                catch (NumberFormatException ex) {
                    errorMessage.setText("Please enter a valid vintage year between 1900 and " +
                            Year.now().getValue());
                    return false;
                }
        }
        // Validate ABV field
        try {
            finalABV = Double.parseDouble(tfABV.getText());
            if (finalABV < 0 || finalABV > 20) {
                errorMessage.setText("Please enter a valid ABV percentage between 0 and 20.");
                return false;
            }
        }
        catch (NumberFormatException ex) {
            errorMessage.setText("Please enter a valid ABV percentage between 0 and 20.");
            return false;
        }
        // Validate bottle and glass price fields
        try {
            finalBottlePrice = Integer.parseInt(tfBottlePrice.getText());
            finalGlassPrice = Integer.parseInt(tfGlassPrice.getText());
            if (finalBottlePrice < 0 || finalGlassPrice < 0 || finalGlassPrice > finalBottlePrice) {
                errorMessage.setText("Please make sure your glass and bottle prices are correct.");
                return false;
            }
        } catch (NumberFormatException ex) {
            errorMessage.setText("Please enter a valid integer greater than 0 for your bottle and glass prices.");
            return false;
        }

        // All fields are valid
        errorMessage.setText("");
        return true;
    }
}
