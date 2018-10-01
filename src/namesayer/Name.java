package namesayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Name {

    private String name;
    private List<NameVersion> namesList = new ArrayList<>();

    public Name(String name) {
        this.name = name;
    }

    public void addName(NameVersion name) {
        namesList.add(name);
    }

    public List<NameVersion> getNames() {
        return namesList;
    }

    public boolean containName(String string) {

        // iterate through all names
        for (NameVersion name : namesList) {

            // assert same name
            if (name.getName().equals(string)) {
                return true;
            }
        }

        // name not found
        return false;
    }

    public NameVersion getNameByString(String string) {

        // iterate through all names
        for (NameVersion name : namesList) {

            // assert same name
            if (name.getName().equals(string)) {
                return name;
            }
        }

        // name not found
        return null;
    }

    public NameVersion pickVersion() {

        // names that do not have a bad rating
        List<NameVersion> acceptableVersions = new ArrayList<>();

        // check rating for each version
        for (NameVersion version : namesList) {

            // is a good name
            if (!version.isBadName()) {
                acceptableVersions.add(version);
            }
        }

        // if there are no acceptable names
        if (acceptableVersions.isEmpty()) {
            int index = new Random().nextInt(namesList.size());

            return namesList.get(index);
        }

        // pick a random acceptable name
        int index = new Random().nextInt(acceptableVersions.size());

        return acceptableVersions.get(index);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
