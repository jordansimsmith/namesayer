package namesayer;

import java.util.ArrayList;
import java.util.List;

public class Name {

    protected String name;
    private List<Name> namesList = new ArrayList<>();

    public Name(String name) {
        this.name = name;
    }

    public void addName(Name name) {
        namesList.add(name);
    }

    public List<Name> getNames() {
        return namesList;
    }

    public boolean containName(String string) {

        // iterate through all names
        for (Name name : namesList) {

            // assert same name
            if (name.getName().equals(string)) {
                return true;
            }
        }

        // name not found
        return false;
    }

    public Name getNameByString(String string) {

        // iterate through all names
        for (Name name : namesList) {

            // assert same name
            if (name.getName().equals(string)) {
                return name;
            }
        }

        // name not found
        return null;
    }

    public NameVersion pickVersion() {
        //TODO: implement name choosing system based on name ratings
        return (NameVersion) namesList.get(0);
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
