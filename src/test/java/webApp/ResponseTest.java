package webApp;

import org.junit.Before;
import org.junit.Test;
import webApp.Response;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ResponseTest {
    Response rep1;
    Response rep2;
    Response rep3;

    UUID link1 = UUID.fromString("133a4559-e55c-18b3-2456-555563322002");
    UUID link2 = UUID.fromString("233a4559-e55c-18b3-2456-555563322002");
    UUID link3 = UUID.fromString("333a4559-e55c-18b3-2456-555563322002");


    @Before
    public void setup() {
        rep1 = new Response(1, link1, "Perfection", "This is content");
        rep2 = new Response(2, link2, "The program worked properly", "PASS");
        rep3 = new Response(3, link3, "The program failed", "FAIL");
    }

    @Test
    public void getId(){
        assertEquals(rep1.getId(),Integer.valueOf(1));
        assertEquals(rep2.getId(),Integer.valueOf(2));
        assertEquals(rep3.getId(),Integer.valueOf(3));
    }

    @Test
    public void getLink(){
        // compareTo is the comparison method for UUIDs
        // It returns 0 when they are equal, hence I check for equality with 0
        assertEquals(rep1.getLink().compareTo(link1), 0);
        assertEquals(rep2.getLink().compareTo(link2), 0);
        assertEquals(rep3.getLink().compareTo(link3), 0);
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
