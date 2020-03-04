package webApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import survey.OpenEndedQuestion;
import survey.Survey;
import survey.Question;

import java.util.ArrayList;
import java.util.Collection;

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
        Collection<Question> questionList = new ArrayList<Question>();

        for (QuestionMessage question : surveyMessage.getQuestions()) {
            if (question.getType().equals("openEnded")) {
                questionList.add(new OpenEndedQuestion(question.getQuestion()));
            }
        }

        survey.setQuestions(questionList);
        repo.save(survey);
        return new Response("ok", "done");
    }

    @GetMapping(value = "/retrieveSurvey", produces = "application/json")
    @ResponseBody
    public SurveyMessage retrieveSurvey(@RequestParam (name="name") String name) {

        Collection<QuestionMessage> questionMessages = new ArrayList<QuestionMessage>();

        Collection<Survey> surveys = repo.findByName(name);
        if (surveys.size() == 1 ) {
            surveys.iterator().next().getQuestions();



            for (Question question : surveys.iterator().next().getQuestions()) {
                questionMessages.add(new QuestionMessage("openEnded", question.getQuestion()));
            }

            return new SurveyMessage(name, questionMessages);
        }
        return new SurveyMessage("", questionMessages);
    }
}
