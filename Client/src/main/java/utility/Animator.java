package utility;

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
                aResponse.getInformation()
                        .forEach((key, value) -> sb.append("\t")
                                .append(TextFormatting.getGreenText(key))
                                .append(" : ")
                                .append(value.toUpperCase())
                                .append("\n\n"));
            }
            if (aResponse.getSetOfStudyGroups() != null) {
                aResponse.getSetOfStudyGroups()
                        .forEach(sg -> sb.append(sg.toString())
                                .append("\n\n"));
            }
            if (aResponse.getStudyGroup() != null) {
                sb.append(aResponse.getStudyGroup().toString()).append("\n");
            }
            if (aResponse.getCount() != null) {
                sb.append(aResponse.getCount());
            }
        } else {
            switch (aResponse.getStatus()) {
                case OBJECTNOTEXIST:
                    return TextFormatting.getRedText("\n\tAn object with this id does not exist!\n");
                case DUPLICATESDETECTED:
                    return TextFormatting.getRedText("\tThis element probably duplicates " +
                            "existing one and can't be added\n");
                case ISNTMAX:
                    return TextFormatting.getRedText("\n\tStudy group isn't max!\n");
                case ISNTMIN:
                    return TextFormatting.getRedText("\n\tStudy group isn't min!\n");
                case PERMISSIONDENIED:
                    return TextFormatting.getRedText("\n\tPermission denied!\n");
                case SUCCESSFUL:
                    return TextFormatting.getGreenText("\n\tAction processed successful!\n");
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