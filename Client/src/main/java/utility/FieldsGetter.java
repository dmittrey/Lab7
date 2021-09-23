package utility;

import data.*;
import Interfaces.FieldsReceiver;

import java.io.IOException;
import java.util.Arrays;

/**
 * Class is used to receive fields
 */
public class FieldsGetter implements FieldsReceiver {

    private final Console console;

    public FieldsGetter(Console aConsole) {
        console = aConsole;
    }

    /**
     * @return null - if end of script or empty String
     */
    private Object workWithTypes(String line, TypeOfArgument type, boolean minExist, boolean nullAvailable) {

        try {
            switch (type) {
                case INTEGER: {
                    if (line != null) Integer.parseInt(line);
                    else if (nullAvailable) return null;
                    break;
                }
                case DOUBLE: {
                    if (line != null) Double.parseDouble(line);
                    else if (nullAvailable) return null;
                    break;
                }
                case LONG: {
                    if (line != null) Long.parseLong(line);
                    else if (nullAvailable) return null;
                    break;
                }
                case STRING: {
                    if (line != null) return line;
                    break;
                }
            }
        } catch (NumberFormatException e) {
            line = null;
        }

        if (line != null) {
            switch (type) {

                case INTEGER: {
                    if (!minExist || Integer.parseInt(line) > 0) return Integer.parseInt(line);
                    break;
                }
                case DOUBLE: {
                    if (!minExist || Double.parseDouble(line) > 0) return Double.parseDouble(line);
                    break;
                }
                case LONG: {
                    if (!minExist || Long.parseLong(line) > 0) return Long.parseLong(line);
                    break;
                }
            }
        }

        return null;
    }

    private String getUniversalRequest(String requestField, String options, Console console, boolean nullAvailable) throws IOException {

        do {
            StringBuilder sb = new StringBuilder();
            sb.append("\t").append(TextFormatting.getRedText(TextFormatting.capitalize(requestField))).
                    append(TextFormatting.getRedText(" should be ")).append(TextFormatting.getRedText(options)).
                    append(TextFormatting.getRedText("!\n"));
            sb.append("Enter ").append(requestField).append(" again: ");
            console.print(sb, true);

            String line;

            line = console.read();

            if (line == null && console.getExeStatus()) return null;
            if (line != null || nullAvailable) return line;
        } while (true);
    }

    private Object getGeneralRequest(String requestField, String options,
                                     Console console, boolean minExist, boolean nullAvailable, TypeOfArgument type) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n-------------------\n");
        sb.append(requestField.toUpperCase()).append("\n");
        sb.append("-------------------\n\n");
        if (nullAvailable) sb.append("\t").append(TextFormatting.getRedText("\t(You can skip this field)\n\n"));
        sb.append("Enter ").append(requestField).append(": ");
        console.print(sb, true);

        String line;
        try {
            line = console.read();
        } catch (IOException exception) {
            return null;
        }

        if (line == null) return null;
        else if (line.trim().equals("")) line = null;

        Object firstRequest = workWithTypes(line, type, minExist, nullAvailable);

        if (firstRequest != null || (nullAvailable && line == null)) return firstRequest;

        do {

            try {
                line = getUniversalRequest(requestField, options, console, nullAvailable);
            } catch (IOException exception) {
                return null;
            }

            if (line == null) return null;
            else if (line.trim().equals("")) line = null;

            Object request = workWithTypes(line, type, minExist, nullAvailable);

            if (request != null || (nullAvailable && line == null)) return request;

        } while (true);
    }


    private String getFirstEnumRequest(String requestField, String enumerateList, Console console) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n-------------------\n");
        sb.append(requestField.toUpperCase()).append("\n");
        sb.append("-------------------\n\n");
        sb.append("Available ").append(requestField).append(":\n");
        sb.append("\t").append(TextFormatting.getGreenText(enumerateList));
        sb.append("\n\nEnter ").append(requestField).append(": ");
        console.print(sb, true);

        try {
            return console.read();
        } catch (IOException exception) {
            return null;
        }
    }

    private String getUniversalEnumRequest(String requestField, Console console) {

        StringBuilder sb = new StringBuilder();
        sb.append(TextFormatting.getRedText("\tIt's incorrect ")).
                append(TextFormatting.getRedText(requestField)).append(TextFormatting.getRedText("!"));
        sb.append("\nEnter ").append(requestField).append(" again: ");
        console.print(sb, true);

        try {
            return console.read();
        } catch (IOException exception) {
            return null;
        }
    }

    @Override
    public String getName() {

        return (String) getGeneralRequest("group name", "not null and not empty string",
                console, false, false, TypeOfArgument.STRING);
    }

    @Override
    public Coordinates getCoordinates() {

        console.print("\n-------------------\n" +
                "GROUP'S COORDINATES\n" +
                "-------------------\n\n", true);

        Integer x = (Integer) getGeneralRequest("x coordinate",
                "not null int number", console, false, false, TypeOfArgument.INTEGER);
        if (x == null) return null;

        Double y = (Double) getGeneralRequest("y coordinate", "not null Double number", console,
                false, false, TypeOfArgument.DOUBLE);
        if (y == null) return null;

        return new Coordinates(x, y);
    }

    @Override
    public Integer getStudentsCount() {

        return (Integer) getGeneralRequest("group student count",
                "not null positive Integer number", console, true, false, TypeOfArgument.INTEGER);
    }

    @Override
    public Double getAverageMark() {

        return (Double) getGeneralRequest("group average mark",
                "positive double or you can skip this field", console, true, true, TypeOfArgument.DOUBLE);
    }

    @Override
    public FormOfEducation getFormOfEducation() {

        String line = getFirstEnumRequest("form of education", Arrays.toString(FormOfEducation.values())
                + TextFormatting.getBlueText("\n-----")
                + TextFormatting.getBlueText("\n\tYou can write form of education in lower case!")
                + TextFormatting.getBlueText("\n-----")
                + TextFormatting.getRedText("\n\n\t(You can skip this field)"), console);
        if (line == null || line.trim().equals("")) return null;

        while (true) {

            if (line != null && line.trim().equals("")) line = null;
            if (line == null) return null;

            if (FormOfEducation.isIncludeElement(line.toUpperCase()))
                return FormOfEducation.valueOf(line.toUpperCase());

            line = getUniversalEnumRequest("form of education", console);
        }
    }

    @Override
    public Semester getSemester() {

        String line = getFirstEnumRequest("group semester", Arrays.toString(Semester.values())
                + TextFormatting.getBlueText("\n-----")
                + TextFormatting.getBlueText("\n\tYou can write form of education in lower case!")
                + TextFormatting.getBlueText("\n-----"), console);
        if (line == null) return null;

        while (line == null || !Semester.isIncludeElement(line.toUpperCase())) {

            line = getUniversalEnumRequest("semester", console);

            if (line == null) return null;
            if (line.trim().equals("")) line = null;
        }
        return Semester.valueOf(line.toUpperCase());
    }

    @Override
    public Person getGroupAdmin() {

        console.print("\n-------------------\n" +
                TextFormatting.getBlueText("\tGROUP ADMIN\n") +
                "-------------------\n\n", true);

        String name = (String) getGeneralRequest("group's admin name", "not null and not empty string",
                console, false, false, TypeOfArgument.STRING);

        if (name == null) return null;

        Long weight = (Long) getGeneralRequest("group admin weight",
                "not null positive long number", console, true, false, TypeOfArgument.LONG);

        if (weight == null) return null;

        String line = getFirstEnumRequest("group admin hair color", Arrays.toString(Color.values())
                + TextFormatting.getBlueText("\n-----")
                + TextFormatting.getBlueText("\n\tYou can write form of education in lower case!")
                + TextFormatting.getBlueText("\n-----"), console);

        if (line == null) return null;

        while (line == null || !Color.isIncludeElement(line.toUpperCase())) {

            line = getUniversalEnumRequest("group admin hair color", console);

            if (line == null) return null;
            if (line.trim().equals("")) line = null;
        }

        Color hairColor = Color.valueOf(line.toUpperCase());

        return new Person(name, weight, hairColor);
    }
}