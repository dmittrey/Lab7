package data;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Enum for study group form of education
 */
public enum FormOfEducation implements Comparable<FormOfEducation>, Serializable {
    DISTANCE_EDUCATION("DISTANCE_EDUCATION", "distance_education"),
    FULL_TIME_EDUCATION("FULL_TIME_EDUCATION", "full_time_education"),
    EVENING_CLASSES("EVENING_CLASSES", "evening_classes");

    private final String stringInUpperCaseRepresentation;
    private final String stringInLowerCaseRepresentation;

    FormOfEducation(String aStringInUpperCaseRepresentation, String aStringInLowerCaseRepresentation) {
        stringInUpperCaseRepresentation = aStringInUpperCaseRepresentation;
        stringInLowerCaseRepresentation = aStringInLowerCaseRepresentation;
    }

    private String getStringInLowerCaseRepresentation() {
        return stringInLowerCaseRepresentation;
    }

    private String getStringInUpperCaseRepresentation() {
        return stringInUpperCaseRepresentation;
    }

    public static boolean isIncludeElement(String aFormOfEducation) {

        return Arrays.stream(FormOfEducation.values()).anyMatch(formOfEducation ->
                aFormOfEducation.equals(formOfEducation.getStringInLowerCaseRepresentation()) ||
                        aFormOfEducation.equals(formOfEducation.getStringInUpperCaseRepresentation())
        );
    }

    @Override
    public String toString() {
        return getStringInUpperCaseRepresentation();
    }
}