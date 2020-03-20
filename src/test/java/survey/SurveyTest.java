package survey;

import org.junit.Before;
import org.junit.Test;
import survey.NumberQuestion;
import survey.OpenEndedQuestion;
import survey.Question;
import survey.Survey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class SurveyTest {

    Survey survey1;
    Survey survey2;
    Survey survey3;
    Survey survey4;
    Collection<Question> questions;

    @Before
    public void setup() {
        survey1 = new Survey();
        survey2 = new Survey("The greatest survey known to man");
        questions = new ArrayList<>(Arrays.asList(new NumberQuestion("How old are you?",0,120),new OpenEndedQuestion("What is your SID?")));
        survey3 = new Survey("Take the survey for a free $100 itunes gift card",questions);
    }

    @Test
    public void getSetQuestions(){
        Collection<Question> q1 = new ArrayList<>(Arrays.asList(new NumberQuestion("How many jelly beans do you have?",0,120),new OpenEndedQuestion("How much wood could a wood chuck chuck if a wood chuck could chuck wood?")));
        survey1.setQuestions(q1);
        assertEquals(survey1.getQuestions(),q1);
        assertEquals(survey3.getQuestions(),questions);

        Collection<Question> q2 = new ArrayList<>(Arrays.asList(new NumberQuestion("How many jelly beans can I have?",0,120),new OpenEndedQuestion("How sea shells does she sell down by the seashore?")));
        survey3.setQuestions(q2);
        assertEquals(survey3.getQuestions(),q2);
    }

    @Test
    public void getSetId() {
        survey1.setId(8);
        assertEquals(survey1.getId(),8);
        assertEquals(survey2.getId(),0);
        survey2.setId(-4);
        assertEquals(survey2.getId(),-4);
        assertEquals(survey3.getId(),0);
    }

    @Test
    public void getSetName() {
        survey1.setName("Favorite survey software survey");
        assertEquals(survey1.getName(),"Favorite survey software survey");
        assertEquals(survey2.getName(),"The greatest survey known to man");
        survey2.setName("An even better survey");
        assertEquals(survey2.getName(),"An even better survey");
        assertEquals(survey3.getName(),"Take the survey for a free $100 itunes gift card");
    }
}
