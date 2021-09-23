package utility;

import data.StudyGroup;
import Interfaces.ObjectValidator;

import java.util.Set;

/**
 * Class to validate objects from xml file and add correct study groups in collection
 */
public class CollectionValidator implements ObjectValidator {

    CollectionManager collectionManager;

    public CollectionValidator(CollectionManager aCollectionManager) {
        collectionManager = aCollectionManager;
    }

    public String validateCollection(Set<StudyGroup> inputCollection) {
        inputCollection.stream().filter(this::validateObject).forEach(studyGroup -> collectionManager.add(studyGroup));

        return TextFormatting.getGreenText("\n\tCollection was loaded!\n\n");
    }
}