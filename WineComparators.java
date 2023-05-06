import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class WineComparators {
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
    private static final Map<String, Integer> SWEETNESS_ORDER = new HashMap<>(Map.of(
       Wine.SWEETNESS_LEVELS[0], 1,
       Wine.SWEETNESS_LEVELS[1], 2,
       Wine.SWEETNESS_LEVELS[2], 3,
       Wine.SWEETNESS_LEVELS[3], 4,
       Wine.SWEETNESS_LEVELS[4], 5
    ));

    /** Comparator for sorting wines by color */
    public static Comparator<Wine> byColor() {
        return new Comparator<Wine>() {
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
    public static Comparator<Wine> byGrape() {
        return new Comparator<Wine>() {
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

    /** Comparator for sorting wines by sweetness */
    public static Comparator<Wine> bySweetness() {
        return new Comparator<Wine>() {
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

    /** Comparator for sorting wines by bottle price */
    public static Comparator<Wine> byBottlePrice() {
        return new Comparator<Wine>() {
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

    /** Comparator for sorting wines by glass price */
    public static Comparator<Wine> byGlassPrice() {
        return new Comparator<Wine>() {
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

    /** Comparator for sorting wines by number of favorites */
    public static Comparator<Wine> byNumFavorites() {
        return new Comparator<Wine>() {
            @Override
            public int compare(Wine w1, Wine w2) {
                int compareValue = Integer.compare(w1.getFavorites(), w2.getFavorites());
                // Compare by name if number of favorites is the same
                if (compareValue == 0)
                    return w1.compareTo(w2);
                else
                    return compareValue;
            }
        };
    }
}
