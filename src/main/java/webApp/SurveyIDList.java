package webApp;

import java.util.Collection;

public class SurveyIDList {
    private final Collection<String> nameList;
    private final Collection<Integer> idList;

    public SurveyIDList(Collection<String> nameList, Collection<Integer> idList){
        this.nameList = nameList;
        this.idList = idList;
    }

    public Collection<Integer> getIdList() {
        return idList;
    }

    public Collection<String> getNameList() {
        return nameList;
    }
}
