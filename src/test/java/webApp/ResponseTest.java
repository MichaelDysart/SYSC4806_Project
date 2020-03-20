package webApp;

import org.junit.Before;
import org.junit.Test;
import webApp.Response;

import static org.junit.Assert.assertEquals;


public class ResponseTest {
    Response rep1;
    Response rep2;
    Response rep3;


    @Before
    public void setup() {
        rep1 = new Response(1, "Perfection", "This is content");
        rep2 = new Response(2, "The program worked properly", "PASS");
        rep3 = new Response(3, "The program failed", "FAIL");
    }

    @Test
    public void getId(){
        assertEquals(rep1.getId(),Integer.valueOf(1));
        assertEquals(rep2.getId(),Integer.valueOf(2));
        assertEquals(rep3.getId(),Integer.valueOf(3));
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
