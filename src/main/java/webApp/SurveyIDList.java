package webApp;

import java.util.Collection;

/*
 * Contains the list of survey names and ids
 */
public class SurveyIDList {
    private final Collection<String> nameList;
    private final Collection<Integer> idList;

    /*
     * A constructor to create a SurveyIDList object
     * @param nameList {String} list of survey names
     * @param idList {Integer} list of survey ids
     */
    public SurveyIDList(Collection<String> nameList, Collection<Integer> idList){
        this.nameList = nameList;
        this.idList = idList;
    }

    /*
     * Retrieve the list of survey ids
     * @returns {Integer}
     */
    public Collection<Integer> getIdList() {
        return idList;
    }

    /*
     * Retrieve the list of survey names
     * @returns {String}
     */
    public Collection<String> getNameList() {
        return nameList;
    }
}
