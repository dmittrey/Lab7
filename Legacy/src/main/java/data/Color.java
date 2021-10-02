package data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Enum for study group admin eyes color
 */
public enum Color implements Serializable {
    BLACK("BLACK", "black"),
    BLUE("BLUE", "blue"),
    YELLOW("YELLOW", "yellow"),
    WHITE("WHITE", "white"),
    BROWN("BROWN", "brown");

    private final String stringInUpperCaseRepresentation;
    private final String stringInLowerCaseRepresentation;

    Color(String aStringInUpperCaseRepresentation, String aStringInLowerCaseRepresentation) {
        stringInUpperCaseRepresentation = aStringInUpperCaseRepresentation;
        stringInLowerCaseRepresentation = aStringInLowerCaseRepresentation;
    }

    private String getStringInLowerCaseRepresentation() {
        return stringInLowerCaseRepresentation;
    }

    private String getStringInUpperCaseRepresentation() {
        return stringInUpperCaseRepresentation;
    }

    public static boolean isIncludeElement(String aColor) {

        return Arrays.stream(Color.values()).anyMatch(color -> aColor.equals(
                color.getStringInLowerCaseRepresentation()) ||
                aColor.equals(color.getStringInUpperCaseRepresentation())
        );
    }

    @Override
    public String toString() {
        return getStringInUpperCaseRepresentation();
    }
}