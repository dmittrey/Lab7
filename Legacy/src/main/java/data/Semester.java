package data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Enum for study group education semester
 */
public enum Semester implements Serializable {
    SECOND("SECOND", "second"),
    THIRD("THIRD", "third"),
    FOURTH("FOURTH", "fourth"),
    FIFTH("FIFTH", "fifth"),
    SIXTH("SIXTH", "sixth");

    private final String stringInUpperCaseRepresentation;
    private final String stringInLowerCaseRepresentation;

    Semester(String aStringInUpperCaseRepresentation, String aStringInLowerCaseRepresentation) {
        stringInUpperCaseRepresentation = aStringInUpperCaseRepresentation;
        stringInLowerCaseRepresentation = aStringInLowerCaseRepresentation;
    }

    private String getStringInLowerCaseRepresentation() {
        return stringInLowerCaseRepresentation;
    }

    private String getStringInUpperCaseRepresentation() {
        return stringInUpperCaseRepresentation;
    }

    public static boolean isIncludeElement(String aSemester) {

        return Arrays.stream(Semester.values()).anyMatch(semester ->
                aSemester.equals(semester.getStringInLowerCaseRepresentation()) ||
                        aSemester.equals(semester.getStringInUpperCaseRepresentation())
        );
    }

    @Override
    public String toString() {
        return getStringInUpperCaseRepresentation();
    }
}