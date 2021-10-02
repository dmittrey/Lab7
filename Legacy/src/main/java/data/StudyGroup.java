package data;

import utility.TextFormatting;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Class to study group
 */
public class StudyGroup implements Comparable<StudyGroup>, Serializable {

    private Integer id;
    private String name;
    private final Coordinates coordinates;
    private Date creationDate;
    private final Integer studentsCount;
    private final Double averageMark;
    private final data.FormOfEducation formOfEducation;
    private final data.Semester semesterEnum;
    private final data.Person groupAdmin;
    private String author;

    /**
     * Class construct
     *
     * @param aId              - group id
     * @param aName            - group name
     * @param aCoordinates     - group coordinates
     * @param aCreationDate    - group creation date
     * @param aStudentsCount   - group student's count
     * @param aAverageMark     - group average mark
     * @param aFormOfEducation - group form of education
     * @param aSemesterEnum    - group semester of education
     * @param aGroupAdmin      - group admin
     * @see data.Person
     */
    public StudyGroup(int aId, String aName, Coordinates aCoordinates, Date aCreationDate, int aStudentsCount,
                      Double aAverageMark, data.FormOfEducation aFormOfEducation, data.Semester aSemesterEnum,
                      data.Person aGroupAdmin) {
        id = aId;
        name = aName;
        coordinates = aCoordinates;
        creationDate = aCreationDate;
        studentsCount = aStudentsCount;
        averageMark = aAverageMark;
        formOfEducation = aFormOfEducation;
        semesterEnum = aSemesterEnum;
        groupAdmin = aGroupAdmin;
        author = null;
    }

    public StudyGroup(int aId, String aName, Coordinates aCoordinates, Date aCreationDate, int aStudentsCount,
                      Double aAverageMark, data.FormOfEducation aFormOfEducation, data.Semester aSemesterEnum,
                      data.Person aGroupAdmin, String anAuthor) {
        id = aId;
        name = aName;
        coordinates = aCoordinates;
        creationDate = aCreationDate;
        studentsCount = aStudentsCount;
        averageMark = aAverageMark;
        formOfEducation = aFormOfEducation;
        semesterEnum = aSemesterEnum;
        groupAdmin = aGroupAdmin;
        author = anAuthor;
    }

    public StudyGroup setId(Integer anId) {
        id = anId;
        return this;
    }

    public void setName(String aName) {
        name = aName;
    }

    public void setCreationDate(Date aCreationDate) {
        creationDate = aCreationDate;
    }

    public void setAuthor(String anAuthor) {
        author = anAuthor;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public Double getAverageMark() {
        return averageMark;
    }

    public data.FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public data.Semester getSemesterEnum() {
        return semesterEnum;
    }

    public data.Person getGroupAdmin() {
        return groupAdmin;
    }

    public String getAuthor() {
        return author;
    }

    /**
     * Method to compare two study groups by field(student's count)
     *
     * @param aStudyGroup - study group
     * @return "1" - if this group better than comparing group
     * "0" - if this group same comparing group
     * "-1" - if this group worse than comparing group
     * <p>
     * Fields priority:
     * StudentsCount - 100x
     * AverageMark - 50x
     * <p>
     * If the rest of the fields match we will compare them by the creation date
     */
    @Override
    public int compareTo(StudyGroup aStudyGroup) {
        Double aStudyGroupAverageMark, thisAverageMark;

        if (aStudyGroup.getAverageMark() == null) aStudyGroupAverageMark = (double) 0;
        else aStudyGroupAverageMark = aStudyGroup.getAverageMark();

        if (getAverageMark() == null) thisAverageMark = (double) 0;
        else thisAverageMark = getAverageMark();

        double compareStatus = ((getStudentsCount() * 100 + thisAverageMark * 50) -
                (aStudyGroup.getStudentsCount() * 100 + aStudyGroupAverageMark * 50));

        if (compareStatus > 0) return 1;
        else if (compareStatus < 0) return -1;
        else return getCreationDate().compareTo(aStudyGroup.getCreationDate());
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return TextFormatting.getBlueText(name) + ":" + "\n" +
                "Id" + "\t\t\t:\t" + id + "\n" +
                "Coordinates" + "\t\t:\t" + coordinates + "\n" +
                "Creation date" + "\t\t:\t" + formatter.format(creationDate) + "\n" +
                "Students count" + "\t\t:\t" + studentsCount + "\n" +
                "Average mark" + "\t\t:\t" + averageMark + "\n" +
                "Form of education" + "\t:\t" + formOfEducation + "\n" +
                "Semester enum" + "\t\t:\t" + semesterEnum + "\n" +
                "Group admin" + "\t\t:\t" + groupAdmin + "\n" +
                "Author" + "\t\t:\t" + author;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, studentsCount, averageMark, formOfEducation, semesterEnum, groupAdmin);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null) return false;

        if (!(otherObject instanceof StudyGroup)) return false;

        StudyGroup other = (StudyGroup) otherObject;

        if (((this.getAverageMark() == null) && (other.getAverageMark() != null))
                || ((this.getAverageMark() != null) && (other.getAverageMark() == null))) return false;

        if (((this.getFormOfEducation() == null) && (other.getFormOfEducation() != null))
                || ((this.getFormOfEducation() != null) && (other.getFormOfEducation() == null))) return false;


        return (this.getName().equals(other.getName())
                && this.getCoordinates().getX().equals(other.getCoordinates().getX())
                && this.getCoordinates().getY().equals(other.getCoordinates().getY())
                && this.getStudentsCount().equals(other.getStudentsCount())
                && ((this.getAverageMark() == null && other.getAverageMark() == null) ||
                this.getAverageMark().equals(other.getAverageMark()))
                && ((this.getFormOfEducation() == null && other.getFormOfEducation() == null) ||
                this.getFormOfEducation().equals(other.getFormOfEducation()))
                && this.getSemesterEnum().equals(other.getSemesterEnum())
                && this.getGroupAdmin().getName().equals(other.getGroupAdmin().getName())
                && this.getGroupAdmin().getWeight().equals(other.getGroupAdmin().getWeight())
                && this.getGroupAdmin().getHairColor().equals(other.getGroupAdmin().getHairColor()));
    }
}