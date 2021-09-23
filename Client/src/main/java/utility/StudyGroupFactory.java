package utility;

import data.*;
import Interfaces.FieldsReceiver;

/**
 * Class for creating Study groups without id and Date
 */
public class StudyGroupFactory implements Interfaces.StudyGroupFactoryInterface {

    private final FieldsReceiver fieldsReceiver;

    public StudyGroupFactory() {

        fieldsReceiver = new FieldsGetter(Console.getInstance());
    }

    @Override
    public StudyGroup createStudyGroup() {

        String name = fieldsReceiver.getName();
        Coordinates coordinates = fieldsReceiver.getCoordinates();
        Integer studentsCount = fieldsReceiver.getStudentsCount();
        Double averageMark = fieldsReceiver.getAverageMark();
        FormOfEducation formOfEducation = fieldsReceiver.getFormOfEducation();
        Semester semester = fieldsReceiver.getSemester();
        Person groupAdmin = fieldsReceiver.getGroupAdmin();

        if ((name != null) &&
                (coordinates != null) &&
                (studentsCount != null) &&
                (semester != null) &&
                (groupAdmin != null)) {

            return new StudyGroup(0, name, coordinates, null, studentsCount,
                    averageMark, formOfEducation, semester, groupAdmin);
        }
        return null;
    }
}