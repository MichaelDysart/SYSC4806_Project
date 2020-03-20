package webApp;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import webApp.Response;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


public class SurveyIDListTest {
    SurveyIDList rep1;
    SurveyIDList rep2;
    SurveyIDList rep3;
    SurveyIDList rep4;

    ArrayList<Integer> na1;
    ArrayList<Integer> na2;
    ArrayList<String> sa1;
    ArrayList<String> sa2;

    @Before
    public void setup() {

        na1 = new ArrayList<>();
        na2 = new ArrayList<>();
        na2.add(1);
        na2.add(2);
        sa1 = new ArrayList<>();
        sa2 = new ArrayList<>();
        sa2.add("a");
        sa2.add("");

        rep1 = new SurveyIDList(sa1, na1);
        rep2 = new SurveyIDList(sa2, na1);
        rep3 = new SurveyIDList(sa1, na2);
        rep4 = new SurveyIDList(sa2, na2);
    }

    @Test
    public void getIdList(){
        assertEquals(rep1.getIdList(), na1);
        assertEquals(rep2.getIdList(), na1);
        assertEquals(rep3.getIdList(), na2);
        assertEquals(rep4.getIdList(), na2);
    }

    @Test
    public void getStringList(){
        assertEquals(rep1.getNameList(), sa1);
        assertEquals(rep2.getNameList(), sa2);
        assertEquals(rep3.getNameList(), sa1);
        assertEquals(rep4.getNameList(), sa2);
    }

}
