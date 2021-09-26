package data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class for study group admin
 */
public class Person implements Serializable {
    private String name;
    private Long weight;
    private Color hairColor;

    /**
     * Class constructor
     *
     * @param aName      - admin name
     * @param aWeight    - admin weight
     * @param aHairColor - admin color
     */
    public Person(String aName, Long aWeight, Color aHairColor) {
        name = aName;
        weight = aWeight;
        hairColor = aHairColor;
    }

    public String getName() {
        return name;
    }

    public Long getWeight() {
        return weight;
    }

    public Color getHairColor() {
        return hairColor;
    }

    @Override
    public String toString() {
        return "name = " + name + ", " +
                "weight = " + weight + ", " +
                "hair color = " + hairColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, hairColor);
    }
}