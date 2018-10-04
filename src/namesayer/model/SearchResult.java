package namesayer.model;

import java.util.List;

/**
 * This class is used to return results of a search. It contains a NameList of names that are found and can be played
 * and a List of the names that were not found.
 */
public class SearchResult {

    private NameList nameList;
    private List<String> namesNotFound;

    public SearchResult(NameList nameList, List<String> namesNotFound) {
        this.nameList = nameList;
        this.namesNotFound = namesNotFound;
    }

    public NameList getNameList() {
        return nameList;
    }

    public List<String> getNamesNotFound() {
        return namesNotFound;
    }
}
