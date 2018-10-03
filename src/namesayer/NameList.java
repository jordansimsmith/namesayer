package namesayer;

import java.util.List;

public class NameList {

    private List<Name> names;

    public NameList(List<Name> names) {
        this.names = names;
    }

    public void add(Name name) {
        names.add(name);
    }

    public Name get(int index) {
        return names.get(index);
    }

    public String generateFileName() {
        StringBuilder builder = new StringBuilder();

        for (Name name : names) {
            builder.append(name);
            builder.append("-");
        }

        // delete last "-"
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (Name name : names) {
            builder.append(name);
            builder.append(" ");
        }

        return builder.toString();
    }

    public List<Name> getNames() {
        return names;
    }
}
