import org.junit.Before;
import org.junit.Test;
import survey.OpenEndedQuestion;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class OpenEndedQuestionTest {
    OpenEndedQuestion oeq1;
    OpenEndedQuestion oeq2;
    OpenEndedQuestion oeq3;
    OpenEndedQuestion oeq4;

    @Before
    public void setup() throws Exception{
        oeq1 = new OpenEndedQuestion();
        oeq2 = new OpenEndedQuestion("Are you cool?");
        oeq3 = new OpenEndedQuestion("On a scale from 1-10, how much do you enjoy question quail?");
        oeq4 = new OpenEndedQuestion("On a scale from -10-9001, how much do you enjoy question quail?");
    }

    @Test
    public void getSetAddAnswers(){
        ArrayList<String> a1 = new ArrayList<String>(Arrays.asList("yes","no","maybe"));
        ArrayList<String> a2 = new ArrayList<String>(Arrays.asList("9001","9000"));

        oeq2.setAnswers(a1);
        oeq4.setAnswers(a2);
        assertEquals(oeq2.getAnswers(),new ArrayList<String>(Arrays.asList("yes","no","maybe")));
        assertEquals(oeq4.getAnswers(),new ArrayList<String>(Arrays.asList("9001","9000")));

        oeq2.addAnswer("so");
        assertEquals(oeq2.getAnswers(),new ArrayList<String>(Arrays.asList("yes","no","maybe","so")));
    }

    @Test
    public void getSetId() {
        oeq1.setId(8);
        assertEquals(oeq1.getId(),8);
        assertEquals(oeq2.getId(),0);
        oeq2.setId(-4);
        assertEquals(oeq2.getId(),-4);
        assertEquals(oeq3.getId(),0);
        assertEquals(oeq4.getId(),0);
    }

    @Test
    public void getSetQuestion() {
        oeq1.setQuestion("This is the best question ever");
        assertEquals(oeq1.getQuestion(),"This is the best question ever");
        assertEquals(oeq2.getQuestion(),"Are you cool?");
        oeq2.setQuestion("How many pets do you have?");
        assertEquals(oeq2.getQuestion(),"How many pets do you have?");
        assertEquals(oeq3.getQuestion(),"On a scale from 1-10, how much do you enjoy question quail?");
        assertEquals(oeq4.getQuestion(),"On a scale from -10-9001, how much do you enjoy question quail?");
    }

}
