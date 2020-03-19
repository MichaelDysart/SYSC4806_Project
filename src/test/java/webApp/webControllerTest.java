package webApp;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class webControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRetrieveSurveyNames() throws Exception {
        String surveyString = "{ \"name\" : \"survey1\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q2\" }, { \"type\": \"numberQuestion\", \"question\": \"q3\", \"min\": \"0\", \"max\": \"5\" }]}";

        this.mockMvc.perform(get("/retrieveSurveyNames")).andExpect(status().isOk())
               .andExpect(content().json("{ \"nameList\":[], \"idList\" : []}"));

        MvcResult result = this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString)).andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")))
                .andExpect(content().string(containsString("done")))
                .andReturn();

        Integer id1 = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        surveyString = "{ \"name\" : \"survey2\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q2\" }, { \"type\": \"numberQuestion\", \"question\": \"q3\", \"min\": \"0\", \"max\": \"5\" }]}";

        result = this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString)).andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")))
                .andExpect(content().string(containsString("done")))
                .andReturn();


        Integer id2 = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        this.mockMvc.perform(get("/retrieveSurveyNames")).andExpect(status().isOk())
               .andExpect(content().json("{ \"nameList\" : [\"survey1\",\"survey2\"], \"idList\" : ["+id1+","+id2+"]}"));

    }

    @Test
    public void testRestApplication() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk());

        String surveyString1 = "{ \"name\" : \"survey1\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q2\" }, { \"type\": \"numberQuestion\", \"question\": \"q3\", \"min\": 1, \"max\": 5 }]}";

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
                .andExpect(content().json("{ \"name\" : \"\", \"questions\" : [], \"status\"=\"error\", \"id\" : null}"));

        surveyString1 = "{ \"name\" : \"survey2\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"numberQuestion\", \"question\": \"q1\", \"min\": 1, \"max\": 5 }]}";

        this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Duplicate questions detected")));

        surveyString1 = "{ \"name\" : \"survey2\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"numberQuestion\", \"question\": \"q1\", \"min\": 5, \"max\": 1 }]}";
        this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Min is greater than max for question \\\"q1\\\"")));

    }

    @Test
    public void testAddAnswers() throws Exception {

        String surveyString1 = "{ \"name\" : \"survey1\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q2\" }, { \"type\": \"numberQuestion\", \"question\": \"q3\", \"min\": 1, \"max\": 5 }]}";

        MvcResult result = this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")))
                .andExpect(content().string(containsString("done")))
                .andReturn();

        Integer id1 = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        this.mockMvc.perform(get("/retrieveSurvey?id=" + id1)).andExpect(status().isOk())
                .andExpect(content().json("{ \"name\" : \"survey1\", \"questions\" : [ " +
                        "{ \"type\": \"openEnded\", \"question\": \"q1\", \"stringAnswerList\" : [], \"numberAnswerList\" : null }," +
                        "{ \"type\": \"openEnded\", \"question\": \"q2\", \"stringAnswerList\" : [], \"numberAnswerList\" : null }," +
                        "{ \"type\": \"numberQuestion\", \"question\": \"q3\", \"min\": 1, \"max\": 5, \"stringAnswerList\" : null, \"numberAnswerList\" : [] }" +
                        " ], \"status\"=\"ok\", \"id\" : " + id1 + "}"));

        surveyString1 = "{ \"id\" : " + id1 + ", \"questions\" : [" +
                "{ \"type\": \"openEnded\", \"question\": \"q1\", \"stringAnswer\" : \"myAnswer\" }, " +
                "{ \"type\": \"openEnded\", \"question\": \"q2\", \"stringAnswer\" : \"myAnswer\" }, " +
                "{ \"type\": \"numberQuestion\", \"question\": \"q3\", \"numberAnswer\" : 1 }]}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")))
                .andExpect(content().string(containsString("answers saved")));

        surveyString1 = "{ \"id\" : " + id1 + ", \"questions\" : [" +
                "{ \"type\": \"openEnded\", \"question\": \"q1\", \"stringAnswer\" : \"myAnswer2\" }, " +
                "{ \"type\": \"numberQuestion\", \"question\": \"q3\", \"numberAnswer\" : 3 }]}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")))
                .andExpect(content().string(containsString("answers saved")));

        this.mockMvc.perform(get("/retrieveSurvey?id=" + id1)).andExpect(status().isOk())
                .andExpect(content().json("{ \"name\" : \"survey1\", \"questions\" : [ " +
                        "{ \"type\": \"openEnded\", \"question\": \"q1\", \"stringAnswerList\" : [\"myAnswer\", \"myAnswer2\"], \"numberAnswerList\" : null }," +
                        "{ \"type\": \"openEnded\", \"question\": \"q2\", \"stringAnswerList\" : [\"myAnswer\"], \"numberAnswerList\" : null }," +
                        "{ \"type\": \"numberQuestion\", \"question\": \"q3\", \"min\": 1, \"max\": 5, \"stringAnswerList\" : null, \"numberAnswerList\" : [ 1, 3 ] }" +
                        " ], \"status\"=\"ok\", \"id\" : " + id1 + "}"));
    }

    @Test
    public void testAddAnswerErrors() throws Exception {

        String surveyString1 = "{ \"name\" : \"survey1\", \"questions\" : [{ \"type\": \"openEnded\", \"question\": \"q1\" }, { \"type\": \"openEnded\", \"question\": \"q2\" }, { \"type\": \"numberQuestion\", \"question\": \"q3\", \"min\": 1, \"max\": 5 }]}";

        MvcResult result = this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("ok")))
                .andExpect(content().string(containsString("done")))
                .andReturn();

        Integer id1 = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        surveyString1 = "{ \"questions\" : []}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Survey id was null")));

        surveyString1 = "{ \"id\": " + id1 + ", \"questions\" : [null]}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Question was null")));

        surveyString1 = "{ \"id\": " + id1 + ", \"questions\" : null}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Questions were null")));

        surveyString1 = "{ \"id\" : " + id1 + ", \"questions\" : [" +
                "{ \"type\": \"numberQuestion\", \"question\": \"q0\", \"numberAnswer\" : 3 }]}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Missing Question \\\"q0\\\"")));

        surveyString1 = "{ \"id\" : " + id1 + ", \"questions\" : [" +
                "{ \"type\": \"numberQuestion\", \"question\": \"q1\", \"numberAnswer\" : 3 }]}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Mismatched types for question \\\"q1\\\": Want openEnded but got numberQuestion")));

        surveyString1 = "{ \"id\" : " + id1 + ", \"questions\" : [" +
                "{ \"type\": \"openEnded\", \"question\": \"q3\", \"numberAnswer\" : 3 }]}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Mismatched types for question \\\"q3\\\": Want numberQuestion but got openEnded")));

        surveyString1 = "{ \"id\" : " + id1 + ", \"questions\" : [" +
                "{ \"type\": \"numberQuestion\", \"question\": \"q3\", \"numberAnswer\" : 1024 }]}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Value for question \\\"q3\\\" outside of range: Want 1 to 5 but got 1024")));

        surveyString1 = "{ \"id\" : " + (id1 + 1) + ", \"questions\" : [" +
                "{ \"type\": \"numberQuestion\", \"question\": \"q3\", \"numberAnswer\" : 4 }]}";

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Survey \\\"" + (id1 + 1) + "\\\" not available")));

    }

    @Test
    public void testCreateNullQuestions() throws Exception {
        String surveyString1 = "{ \"name\" : \"survey2\", \"questions\" : [null]}";

        this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Question was null")));

        surveyString1 = "{ \"name\" : \"survey2\", \"questions\" : null}";

        this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content(surveyString1)).andExpect(status().isOk())
                .andExpect(content().string(containsString("error")))
                .andExpect(content().string(containsString("Questions were null")));
    }

    @Test
    public void testBadRequestsApplication() throws Exception {
        this.mockMvc.perform(post("/createSurvey").contentType("application/json")
                .content("null")).andExpect(status().isBadRequest());

        this.mockMvc.perform(post("/addAnswers").contentType("application/json")
                .content("null")).andExpect(status().isBadRequest());
    }
}
