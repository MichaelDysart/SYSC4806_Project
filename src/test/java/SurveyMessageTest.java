import org.junit.Before;
import org.junit.Test;
import webApp.QuestionMessage;
import webApp.SurveyMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class SurveyMessageTest {
    SurveyMessage sm1;
    SurveyMessage sm2;

    Collection<QuestionMessage> q1;
    Collection<QuestionMessage> q2;

    Collection<String> sal1;
    Collection<String> sal2;

    Collection<Integer> nal1;
    Collection<Integer> nal2;

    @Before
    public void setup() throws Exception {
        q1 = new ArrayList<QuestionMessage>(Arrays.asList(new QuestionMessage("Number", "First 3 numbers?", null, 1, 3, "1,2,3", 123, sal1, nal1)));
        q2 = new ArrayList<QuestionMessage>(Arrays.asList(new QuestionMessage("String", "How to say bye?", null, 0, 0, "Bye, See ya, Ciao", 0, sal2, nal2)));
        sm1 = new SurveyMessage(1, "200", "All good", q1);
        sm2 = new SurveyMessage(2, "404", "Error:Not Found", q2);
    }

    @Test
    public void getId() {
        assertEquals(sm1.getId(), new Integer(1));
        assertEquals(sm2.getId(), new Integer(2));
    }

    @Test
    public void getStatus() {
        assertEquals(sm1.getStatus(), "200");
        assertEquals(sm2.getStatus(), "404");
    }

    @Test
    public void getName() {
        assertEquals(sm1.getName(), "All good");
        assertEquals(sm2.getName(), "Error:Not Found");
    }

    @Test
    public void getQuestions() {
        assertEquals(sm1.getQuestions(),q1);
        assertEquals(sm2.getQuestions(),q2);
    }


}