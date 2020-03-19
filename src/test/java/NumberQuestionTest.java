
import org.junit.Before;
import org.junit.Test;
import survey.NumberQuestion;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class NumberQuestionTest {
    NumberQuestion nq1;
    NumberQuestion nq2;
    NumberQuestion nq3;
    NumberQuestion nq4;

    @Before
    public void setup() throws Exception{
        nq1 = new NumberQuestion();
        nq2 = new NumberQuestion("How old are you?",0,120);
        nq3 = new NumberQuestion("On a scale from 1-10, how much do you enjoy question quail?",1,10);
        nq4 = new NumberQuestion("On a scale from -10-9001, how much do you enjoy question quail?",-10,9001);
    }

    @Test
    public void getSetMin() {
        nq1.setMin(-7);
        assertEquals(nq1.getMin(),-7);
        assertEquals(nq2.getMin(),0);
        nq2.setMin(3);
        assertEquals(nq2.getMin(),3);
        assertEquals(nq3.getMin(),1);
        assertEquals(nq4.getMin(),-10);
    }

    @Test
    public void getSetMax() {
        nq1.setMax(-8);
        assertEquals(nq1.getMax(),-8);
        assertEquals(nq2.getMax(),120);
        nq2.setMax(5);
        assertEquals(nq2.getMax(),5);
        assertEquals(nq3.getMax(),10);
        assertEquals(nq4.getMax(),9001);
    }

    @Test
    public void getSetAddAnswers(){
        ArrayList<Integer> a1 = new ArrayList<Integer>(Arrays.asList(36,28,47));
        ArrayList<Integer> a2 = new ArrayList<Integer>(Arrays.asList(-9,28,47));

        nq2.setAnswers(a1);
        nq4.setAnswers(a2);
        assertEquals(nq2.getAnswers(),new ArrayList<Integer>(Arrays.asList(36,28,47)));
        assertEquals(nq4.getAnswers(), new ArrayList<Integer>(Arrays.asList(-9,28,47)));

        nq2.addAnswer(88);
        assertEquals(nq2.getAnswers(),new ArrayList<Integer>(Arrays.asList(36,28,47,88)));
    }

    @Test
    public void getSetId() {
        nq1.setId(8);
        assertEquals(nq1.getId(),8);
        assertEquals(nq2.getId(),0);
        nq2.setId(-4);
        assertEquals(nq2.getId(),-4);
        assertEquals(nq3.getId(),0);
        assertEquals(nq4.getId(),0);
    }

    @Test
    public void getSetQuestion() {
        nq1.setQuestion("This is the best question ever");
        assertEquals(nq1.getQuestion(),"This is the best question ever");
        assertEquals(nq2.getQuestion(),"How old are you?");
        nq2.setQuestion("How many pets do you have?");
        assertEquals(nq2.getQuestion(),"How many pets do you have?");
        assertEquals(nq3.getQuestion(),"On a scale from 1-10, how much do you enjoy question quail?");
        assertEquals(nq4.getQuestion(),"On a scale from -10-9001, how much do you enjoy question quail?");
    }

}
