// **********************************************************************************
// Title: Wine Class
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: Wine.java
// Description: Creates a Serializable Wine object with name, producer, color,
//      grape, region, vintage, ABV%, bottle price, glass price, sweetness level,
//      tasting notes, pairings, sparkling, and image attributes. It also keeps
//      track of how many favorites a particular wine has. The class implements
//      Comparable and overrides the compareTo method to compare two wines by their
//      name. It also overrides the equals method to define two wines as equal if all
//      of their attributes are the same
// **********************************************************************************
import java.io.Serializable;

public class Wine implements Serializable, Comparable<Wine> {
    private String name = "";
    private String producer = "";
    // Constants for wine colors
    public static final String[] COLORS = {"White", "Rosé", "Light Red", "Medium Red", "Full-Bodied Red"};
    private String color = "";
    private String grape = "";
    private String region = "";
    private int vintage;
    private double alcoholByVolume;
    private int bottlePrice;
    private int glassPrice;
    // Constants for sweetness levels
    public static final String[] SWEETNESS_LEVELS = {"Sweet", "Semi-Sweet", "Off-Dry", "Dry", "Brut"};
    private String sweetness = "";
    private String tastingNotes = "";
    private String pairings = "";
    private boolean sparkling = false;
    private int favorites;
    private String imageFileName = "";

    /** Constructor */
    public Wine(String name, String producer, String color, String grape, String region, int vintage,
                double alcoholByVolume, int bottlePrice, int glassPrice, String sweetness,
                String tastingNotes, String pairings, boolean sparkling, String imageFileName) {
        this.name = name;
        this.producer = producer;
        this.color = color;
        this.grape = grape;
        this.region = region;
        this.vintage = vintage;
        this.alcoholByVolume = alcoholByVolume;
        this.bottlePrice = bottlePrice;
        this.glassPrice = glassPrice;
        this.sweetness = sweetness;
        this.tastingNotes = tastingNotes;
        this.pairings = pairings;
        this.sparkling = sparkling;
        this.favorites = 0;
        this.imageFileName = imageFileName;
    }

    /** Accessors */
    public String getName() {
        return name;
    }

    public String getProducer() {
        return producer;
    }

    public String getColor() {
        return color;
    }

    public String getGrape() {
        return grape;
    }

    public String getRegion() {
        return region;
    }

    public int getVintage() {
        return vintage;
    }

    public double getAlcoholByVolume() {
        return alcoholByVolume;
    }

    public int getBottlePrice() {
        return bottlePrice;
    }

    public int getGlassPrice() {
        return glassPrice;
    }

    public String getSweetness() {
        return sweetness;
    }

    public String getTastingNotes() {
        return tastingNotes;
    }

    public String getPairings() {
        return pairings;
    }

    public boolean isSparkling() {
        return sparkling;
    }

    public int getFavorites() {
        return favorites;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    /** Mutators */
    public void setName(String name) {
        this.name = name;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setGrape(String grape) {
        this.grape = grape;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setVintage(int vintage) {
        this.vintage = vintage;
    }

    public void setAlcoholByVolume(double alcoholByVolume) {
        this.alcoholByVolume = alcoholByVolume;
    }

    public void setBottlePrice(int bottlePrice) {
        this.bottlePrice = bottlePrice;
    }

    public void setGlassPrice(int glassPrice) {
        this.glassPrice = glassPrice;
    }

    public void setSweetness(String sweetness) {
        this.sweetness = sweetness;
    }

    public void setTastingNotes(String tastingNotes) {
        this.tastingNotes = tastingNotes;
    }

    public void setPairings(String pairings) {
        this.pairings = pairings;
    }

    public void setSparkling(boolean sparkling) {
        this.sparkling = sparkling;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    /** Increment favorites by 1 */
    public void incrementFavorites() {
        this.favorites++;
    }

    /** Decrement favorites by 1 */
    public void decrementFavorites() {
        this.favorites--;
    }

    /** Compare two wines by their name */
    @Override
    public int compareTo(Wine otherWine) {
        return this.name.compareTo(otherWine.getName());
    }

    /** Check if two wines are equal */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Wine)) {
            return false;
        }
        Wine other = (Wine) obj;
        return name.equals(other.name)
                && producer.equals(other.producer)
                && color.equals(other.color)
                && grape.equals(other.grape)
                && region.equals(other.region)
                && vintage == other.vintage
                && Double.compare(alcoholByVolume, other.alcoholByVolume) == 0
                && bottlePrice == other.bottlePrice
                && glassPrice == other.glassPrice
                && sweetness.equals(other.sweetness)
                && tastingNotes.equals(other.tastingNotes)
                && pairings.equals(other.pairings)
                && sparkling == other.sparkling
                && favorites == other.favorites
                && imageFileName.equals(other.imageFileName);
    }


    /** Return a string with this wine's attributes */
    @Override
    public String toString() {
        return ("Name: " + getName() + "\nProducer: " + getProducer() + "\nColor: " + getColor() +
                "\nGrape: " + getGrape() + "\nRegion: " + getRegion() + "\nVintage: " +
                ((getVintage() == 0)? "N/A": getVintage()) + "\nABV: " + getAlcoholByVolume() +
                "\nBottle Price: " + getBottlePrice() + "\nGlass Price: " + getGlassPrice() +
                "\nSweetness: " + getSweetness() + "\nTasting Notes: " + getTastingNotes() +
                "\nPairings: " + getPairings() + "\nFavorites: " + getFavorites() +
                "\nImage File: " + getImageFileName());
    }
}
