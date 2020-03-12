package webApp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
        String surveyString = "{ \"name\" : \"survey1\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q2\" }, { \"type\": \"numberQuestion\", \"question\": \"q3\", \"min\": 0, \"max\": 5 }]}";

        this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString)).andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")))
                .andExpect(content().string(containsString("done")));

        this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Survey already exists with that name")));

        this.mockMvc.perform(get("/retrieveSurvey?name=survey1")).andExpect(status().isOk())
                .andExpect(content().json(surveyString));

        surveyString = "{ \"name\" : \"survey2\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"numberQuestion\", \"question\": \"q1\", \"min\": 0, \"max\": 5 }]}";

        this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Duplicate questions detected")));
    }
}
