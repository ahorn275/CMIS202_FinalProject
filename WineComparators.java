// **********************************************************************************
// Title: Wine Comparators
// Author: Autumn Horn
// Course Section: CMIS202-ONL1 (Seidel) Spring 2023
// File: WineComparators.java
// Description: Defines methods for creating SerializableComparators that compare
//       two wine objects by either their name (Z-A), color, grape, sweetness, price,
//       or number of favorites
// **********************************************************************************
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class WineComparators implements Serializable {
    /** Wines prioritized white to red with sparkling wines first */
    private static final Map<String, Integer> COLOR_ORDER = new HashMap<>(Map.of(
            "Sparkling " + Wine.COLORS[0], 1,
            "Sparkling " + Wine.COLORS[1], 2,
            "Sparkling " + Wine.COLORS[2], 3,
            "Sparkling " + Wine.COLORS[3], 4,
            "Sparkling " + Wine.COLORS[4], 5,
            Wine.COLORS[0], 6,
            Wine.COLORS[1], 7,
            Wine.COLORS[2], 8,
            Wine.COLORS[3], 9,
            Wine.COLORS[4], 10

    ));
    /** Wines prioritized sweet to dry */
    private static final Map<String, Integer> SWEETNESS_ORDER = new HashMap<>(Map.of(
       Wine.SWEETNESS_LEVELS[0], 1,
       Wine.SWEETNESS_LEVELS[1], 2,
       Wine.SWEETNESS_LEVELS[2], 3,
       Wine.SWEETNESS_LEVELS[3], 4,
       Wine.SWEETNESS_LEVELS[4], 5
    ));

    /** Comparator for sorting wines by name in reverse order (Z to A) */
    public static SerializableComparator<Wine> byNameReverse() {
        return new SerializableComparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                return w2.getName().compareToIgnoreCase(w1.getName());
            }
        };
    }

    /** Comparator for sorting wines by color as outlined in the color map */
    public static SerializableComparator<Wine> byColor() {
        return new SerializableComparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                int color1 = COLOR_ORDER.get(w1.getColor());
                int color2 = COLOR_ORDER.get(w2.getColor());

                if (color1 < color2)
                    return -1;
                // Compare by name if same color
                else if (color1 == color2)
                    return w1.compareTo(w2);
                else
                    return 1;
            }
        };
    }

    /** Comparator for sorting wines alphabetically by grape */
    public static SerializableComparator<Wine> byGrape() {
        return new SerializableComparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                int compareValue = w1.getGrape().compareTo(w2.getGrape());
                // If grapes are the same compare by name
                if (compareValue == 0)
                    return w1.compareTo(w2);
                else
                    return compareValue;
            }
        };
    }

    /** Comparator for sorting wines by sweetness (sweet to dry) */
    public static SerializableComparator<Wine> bySweetness() {
        return new SerializableComparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                int sweetness1 = SWEETNESS_ORDER.get(w1.getSweetness());
                int sweetness2 = SWEETNESS_ORDER.get(w2.getSweetness());

                if (sweetness1 < sweetness2)
                    return -1;
                // Compare by name if same sweetness level
                else if (sweetness1 == sweetness2)
                    return w1.compareTo(w2);
                else
                    return 1;
            }
        };
    }

    /** Comparator for sorting wines by bottle price (low to high) */
    public static SerializableComparator<Wine> byBottlePrice() {
        return new SerializableComparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                int compareValue = Integer.compare(w1.getBottlePrice(), w2.getBottlePrice());
                // Compare by name if prices are the same
                if (compareValue == 0)
                    return w1.compareTo(w2);
                else
                    return compareValue;
            }
        };
    }

    /** Comparator for sorting wines by glass price (low to high) */
    public static SerializableComparator<Wine> byGlassPrice() {
        return new SerializableComparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                int compareValue = Integer.compare(w1.getGlassPrice(), w2.getGlassPrice());
                // Compare by name if prices are the same
                if (compareValue == 0)
                    return w1.compareTo(w2);
                else
                    return compareValue;
            }
        };
    }

    /** Comparator for sorting wines by number of favorites (high to low) */
    public static SerializableComparator<Wine> byNumFavorites() {
        return new SerializableComparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                int compareValue = Integer.compare(w2.getFavorites(), w1.getFavorites());
                // Compare by name if number of favorites is the same
                if (compareValue == 0)
                    return w1.compareTo(w2);
                else
                    return compareValue;
            }
        };
    }
}
