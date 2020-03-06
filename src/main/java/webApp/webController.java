package webApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import survey.*;

import java.util.ArrayList;
import java.util.Collection;

/*
 * The controller that accepts web requests to update and retrieve surveys
 */
@Controller
public class webController {
    @Autowired
    private SurveyRepository repo;

    @GetMapping("/")
    public String index(Model model){
       return "root";
    }

    @PostMapping(value = "/createSurvey", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response createSurvey(@RequestBody SurveyMessage surveyMessage) {
        Survey survey = new Survey(surveyMessage.getName());
        Collection<Question> questionList = new ArrayList<>();

        for (QuestionMessage question : surveyMessage.getQuestions()) {
            if (question.getType().equals("openEnded")) {
                questionList.add(new OpenEndedQuestion(question.getQuestion()));
            } else if(question.getType().equals("numberQuestion")) {
                questionList.add(new NumberQuestion(question.getQuestion(), question.getMin(), question.getMax()));
            }
        }

        survey.setQuestions(questionList);
        repo.save(survey);
        return new Response("ok", "done");
    }

    @GetMapping(value = "/retrieveSurvey", produces = "application/json")
    @ResponseBody
    public SurveyMessage retrieveSurvey(@RequestParam (name="name") String name) {

        Collection<QuestionMessage> questionMessages = new ArrayList<>();

        Collection<Survey> surveys = repo.findByName(name);
        if (surveys.size() == 1 ) {

            for (Question question : surveys.iterator().next().getQuestions()) {
                if(question.instanceOf()) {
                    questionMessages.add(new QuestionMessage("openEnded", question.getQuestion()));
                }else if(question.getType().equals("numberQuestion")) {
                    NumberQuestion numQ = (NumberQuestion)question;
                    questionMessages.add(new QuestionMessage("numberQuestion", numQ.getQuestion(), numQ.getMin(), numQ.getMax()));

                }
            }

            return new SurveyMessage(name, questionMessages);
        }
        return new SurveyMessage("", questionMessages);
    }
}
