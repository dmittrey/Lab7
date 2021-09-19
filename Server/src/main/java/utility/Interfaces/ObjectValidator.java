package utility.Interfaces;

import data.StudyGroup;

public interface ObjectValidator extends FieldsProtectorInterface {

    default boolean validateObject(StudyGroup aStudyGroup) {
        return getIdCorrectStatus(String.valueOf(aStudyGroup.getId())) &&
                getNameCorrectStatus(String.valueOf(aStudyGroup.getName())) &&
                getCoordinateXCorrectStatus(String.valueOf(aStudyGroup.getCoordinates().getX())) &&
                getCoordinateYCorrectStatus(String.valueOf(aStudyGroup.getCoordinates().getY())) &&
                getDateCorrectStatus(aStudyGroup.getCreationDate()) &&
                getStudentsCountCorrectStatus(String.valueOf(aStudyGroup.getStudentsCount())) &&
                getAverageMarkCorrectStatus(String.valueOf(aStudyGroup.getAverageMark())) &&
                getFormOfEducationCorrectStatus(String.valueOf(aStudyGroup.getFormOfEducation())) &&
                getSemesterEnumCorrectStatus(String.valueOf(aStudyGroup.getSemesterEnum())) &&
                getNameCorrectStatus(String.valueOf(aStudyGroup.getGroupAdmin().getName())) &&
                getGroupAdminWeightCorrectStatus(String.valueOf(aStudyGroup.getGroupAdmin().getWeight())) &&
                getGroupAdminHairColorCorrectStatus(String.valueOf(aStudyGroup.getGroupAdmin().getHairColor()));
    }
}
