package webApp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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
    public void setup() {
        q1 = new ArrayList<>(Arrays.asList(new QuestionMessage("Number", "First 3 numbers?", null, 1, 3, "1,2,3", 123, sal1, nal1)));
        q2 = new ArrayList<>(Arrays.asList(new QuestionMessage("String", "How to say bye?", null, 0, 0, "Bye, See ya, Ciao", 0, sal2, nal2)));
        sm1 = new SurveyMessage(1, "200", "All good", false, q1);
        sm2 = new SurveyMessage(2, "404", "Error:Not Found", true, q2);
    }

    @Test
    public void getId() {
        assertEquals(sm1.getId(), Integer.valueOf(1));
        assertEquals(sm2.getId(), Integer.valueOf(2));
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
    public void getClosed() {
        assertFalse(sm1.getClosed());
        assertTrue(sm2.getClosed());
    }

    @Test
    public void getQuestions() {
        assertEquals(sm1.getQuestions(),q1);
        assertEquals(sm2.getQuestions(),q2);
    }


}
