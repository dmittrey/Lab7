package utility.Interfaces;

import data.Color;
import data.FormOfEducation;
import data.Semester;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public interface FieldsProtectorInterface {

    default boolean getIdCorrectStatus(String line) {

        if (line == null || line.equals("null")) return false;
        return isPositiveInt(line);
    }

    default boolean getNameCorrectStatus(String line) {

        return line != null && !line.equals("null");
    }

    default boolean getCoordinateXCorrectStatus(String line) {

        try {
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    default boolean getCoordinateYCorrectStatus(String line) {

        if (line == null || line.equals("null")) return false;

        try {
            Double.parseDouble(line);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    default boolean getDateCorrectStatus(Date aDate) {

        if (aDate == null) return false;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateInString = formatter.format(aDate);

        try {
            formatter.parse(dateInString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    default boolean getStudentsCountCorrectStatus(String line) {

        try {
            return Integer.parseInt(line) > 0;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    default boolean isPositiveInt(String line) {
        return getStudentsCountCorrectStatus(line);
    }

    default boolean getAverageMarkCorrectStatus(String line) {

        try {
            if (line == null || line.equals("null")) return true;
            return Double.parseDouble(line) > 0;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    default boolean getFormOfEducationCorrectStatus(String line) {

        if (line == null || line.equals("null")) return true;
        else return FormOfEducation.isIncludeElement(line);
    }

    default boolean getSemesterEnumCorrectStatus(String line) {

        return line != null && !line.equals("null") && Semester.isIncludeElement(line);
    }

    default boolean getGroupAdminWeightCorrectStatus(String line) {

        try {
            return Long.parseLong(line) > 0;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    default boolean getGroupAdminHairColorCorrectStatus(String line) {

        return line != null && !line.equals("null") && Color.isIncludeElement(line);
    }
}