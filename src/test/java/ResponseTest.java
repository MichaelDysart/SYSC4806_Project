import org.junit.Before;
import org.junit.Test;
import webApp.Response;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;


public class ResponseTest {
    Response rep1;
    Response rep2;
    Response rep3;


    @Before
    public void setup() throws Exception{
        rep1 = new Response(1, "Perfection", "This is content");
        rep2 = new Response(2, "The program worked properly", "PASS");
        rep3 = new Response(3, "The program failed", "FAIL");
    }

    @Test
    public void getId(){
        assertEquals(rep1.getId(),new Integer(1));
        assertEquals(rep2.getId(),new Integer(2));
        assertEquals(rep3.getId(),new Integer(3));
    }

    @Test
    public void getMessage(){
        assertEquals(rep1.getMessage(),"Perfection");
        assertEquals(rep2.getMessage(),"The program worked properly");
        assertEquals(rep3.getMessage(),"The program failed");
    }

    @Test
    public void getContent(){
        assertEquals(rep1.getContent(),"This is content");
        assertEquals(rep2.getContent(),"PASS");
        assertEquals(rep3.getContent(),"FAIL");
    }

}
