package utility;

import data.StudyGroup;

import java.util.Comparator;

public class Animator {

    private static Animator instance;

    private Animator() {
    }

    public static Animator getInstance() {
        if (instance == null) instance = new Animator();
        return instance;
    }

    public String animate(Response aResponse) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        if (aResponse.getStatus().equals(TypeOfAnswer.SUCCESSFUL)) {
            if (aResponse.getInformation() != null) {
                if (aResponse.getInformation().get("1") == null) {
                    aResponse.getInformation().
                            forEach((key, value) -> sb.append("\t")
                                    .append(TextFormatting.getGreenText(key))
                                    .append(" : ")
                                    .append(value.toUpperCase())
                                    .append("\n\n"));
                } else {
                    aResponse.getInformation()
                            .keySet()
                            .stream()
                            .map(Integer::parseInt)
                            .sorted(Integer::compareTo)
                            .map(String::valueOf)
                            .forEach(key -> sb.append("\t")
                                    .append(TextFormatting.getGreenText(key))
                                    .append(" : ")
                                    .append(aResponse.getInformation().get(key).toUpperCase())
                                    .append("\n\n"));
                }
            }
            if (aResponse.getSetOfStudyGroups() != null) {
                aResponse.getSetOfStudyGroups().stream()
                        .sorted(Comparator.comparing(StudyGroup::getCoordinates))
                        .forEach(sg -> sb.append(sg).append("\n\n"));
            }
            if (aResponse.getStudyGroup() != null) {
                sb.append(aResponse.getStudyGroup().toString())
                        .append("\n");
            }
            if (aResponse.getCount() != null) {
                sb.append("\tAmount of elements: ")
                        .append(TextFormatting.getGreenText(String.valueOf(aResponse.getCount())))
                        .append("\n");
            }
            if (sb.toString().equals("\n")) return TextFormatting.getGreenText("\n\tAction processed successful!\n");
        } else {
            switch (aResponse.getStatus()) {
                case OBJECTNOTEXIST:
                    return TextFormatting.getRedText("\n\tNo object with such parameters was found!\n");
                case DUPLICATESDETECTED:
                    return TextFormatting.getRedText("\tThis element probably duplicates " +
                            "existing one and can't be added\n");
                case ISNTMAX:
                    return TextFormatting.getRedText("\n\tStudy group isn't max!\n");
                case ISNTMIN:
                    return TextFormatting.getRedText("\n\tStudy group isn't min!\n");
                case PERMISSIONDENIED:
                    return TextFormatting.getRedText("\n\tPermission denied!\n");
                case SQLPROBLEM:
                    return TextFormatting.getRedText("\n\tSome problem's with database on server!\n");
                case EMPTYCOLLECTION:
                    return TextFormatting.getRedText("\n\tCollection is empty!\n");
                case ALREADYREGISTERED:
                    return TextFormatting.getRedText("\n\tThis account already registered!\n");
                case NOTMATCH:
                    return TextFormatting.getRedText("\n\tAccount with this parameters doesn't exist!\n");
            }
        }
        return sb.toString();
    }
}
