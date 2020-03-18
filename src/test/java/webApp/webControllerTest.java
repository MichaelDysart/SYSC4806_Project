package webApp;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class webControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRestApplication() throws Exception {
        String surveyString1 = "{ \"name\" : \"survey1\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q2\" }, { \"type\": \"numberQuestion\", \"question\": \"q3\", \"min\": 0, \"max\": 5 }]}";

        MvcResult result = this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")))
                .andExpect(content().string(containsString("done")))
                .andReturn();

        Integer id1 = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        String surveyString2 = "{ \"name\" : \"survey1\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q2\" }]}";

        result = this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString2)).andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")))
                .andExpect(content().string(containsString("done")))
                .andReturn();

        Integer id2 = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        this.mockMvc.perform(get("/retrieveSurvey?id=" + id1)).andExpect(status().isOk())
                .andExpect(content().json(surveyString1));

        this.mockMvc.perform(get("/retrieveSurvey?id=" + id2)).andExpect(status().isOk())
                .andExpect(content().json(surveyString2));

        this.mockMvc.perform(get("/retrieveSurvey?id=" + (id1 + 1 == id2 ? id1 - 1: id1 + 1))).andExpect(status().isOk())
                .andExpect(content().json("{ \"name\" : \"\", \"questions\" : [], \"id\" : null}"));

        surveyString1 = "{ \"name\" : \"survey2\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"numberQuestion\", \"question\": \"q1\", \"min\": 0, \"max\": 5 }]}";

        this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Duplicate questions detected")));
    }
}
