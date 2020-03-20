import org.junit.Before;
import org.junit.Test;
import webApp.QuestionMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class QuestionMessageTest {
    QuestionMessage qm1;
    QuestionMessage  qm2;

    Collection<String>  sal1;
    Collection<String>  sal2;

    Collection<Integer> nal1;
    Collection<Integer> nal2;

    @Before
    public void setup() throws Exception{
        sal1 = new ArrayList<String>(Arrays.asList("1", "2", "3"));
        sal2 = new ArrayList<String>(Arrays.asList("Bye", "See ya", "Ciao"));

        nal1 = new ArrayList<Integer>(Arrays.asList(1,2,3));
        nal2 = new ArrayList<Integer>(Arrays.asList(-1,-2,-3));


        qm1 = new QuestionMessage("Number","First 3 numbers?", null, 1,3,"1,2,3", 123,sal1,nal1 );
        qm2 = new QuestionMessage("String", "How to say bye?", null, 0,0,"Bye, See ya, Ciao", 0,sal2,nal2);
    }

    @Test
    public void getType(){
        assertEquals(qm1.getType(), "Number");
        assertEquals(qm2.getType(), "String");
    }

    @Test
    public void getQuestion(){
        assertEquals(qm1.getQuestion(), "First 3 numbers?");
        assertEquals(qm2.getQuestion(), "How to say bye?");
    }

    @Test
    public void getMin(){
        assertEquals(qm1.getMin(), 1);
        assertEquals(qm2.getMin(), 0);
    }

    @Test
    public void getMax(){
        assertEquals(qm1.getMax(), 3);
        assertEquals(qm2.getMax(), 0);
    }

    @Test
    public void getStringAnswer(){
        assertEquals(qm1.getStringAnswer(), "1,2,3");
        assertEquals(qm2.getStringAnswer(), "Bye, See ya, Ciao");
    }

    @Test
    public void getNumberAnswer(){
        assertEquals(qm1.getNumberAnswer(), 123);
        assertEquals(qm2.getNumberAnswer(), 0);
    }

    @Test
    public void getStringAnswerList(){
        assertEquals(qm1.getStringAnswerList(), new ArrayList<String>(Arrays.asList("1", "2", "3")));
        assertEquals(qm2.getStringAnswerList(), new ArrayList<String>(Arrays.asList("Bye", "See ya", "Ciao")));
    }

    @Test
    public void getNumberAnswerList(){
        assertEquals(qm1.getNumberAnswerList(),new ArrayList<Integer>(Arrays.asList(1,2,3)));
        assertEquals(qm2.getNumberAnswerList(), new ArrayList<Integer>(Arrays.asList(-1,-2,-3)));
    }

}
