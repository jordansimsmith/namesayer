package namesayer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Name {

    private String name;
    private List<NameVersion> namesList = new ArrayList<>();
    private NameVersion lastPlayed;

    public Name(String name) {
        this.name = name;
    }

    public void addName(NameVersion name) {
        namesList.add(name);
    }

    public List<NameVersion> getNames() {
        return namesList;
    }

    public NameVersion pickVersion() {

        // Name to be returned
        NameVersion picked;

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

            picked = namesList.get(index);
        } else {
            // pick a random acceptable name
            int index = new Random().nextInt(acceptableVersions.size());

            picked = acceptableVersions.get(index);
        }

        lastPlayed = picked;
        return picked;

    }

    @Override
    public String toString() {
        return name;
    }

    public NameVersion getLastPlayed() {
        return lastPlayed;
    }

    public String getName() {
        return name;
    }
}
