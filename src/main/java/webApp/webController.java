package webApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import survey.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

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
        if (repo.findById(surveyMessage.getName()).isPresent()) {
            return new Response("error", "Survey already exists with that name");
        }

        Survey survey = new Survey(surveyMessage.getName());
        Collection<Question> questionList = new ArrayList<>();

        ArrayList<String> questionTexts = new ArrayList<>();

        for (QuestionMessage question : surveyMessage.getQuestions()) {
            questionTexts.add(question.getQuestion());
            if (question.getType().equals("openEnded")) {
                questionList.add(new OpenEndedQuestion(question.getQuestion()));
            } else if(question.getType().equals("numberQuestion")) {
                questionList.add(new NumberQuestion(question.getQuestion(), question.getMin(), question.getMax()));
            }
        }

        HashSet<String> set = new HashSet<>(questionTexts);

        if(set.size() < questionTexts.size()){
            return new Response("error", "Duplicate questions detected");
        }

        survey.setQuestions(questionList);
        repo.save(survey);
        return new Response("ok", "done");
    }

    @GetMapping(value = "/retrieveSurvey", produces = "application/json")
    @ResponseBody
    public SurveyMessage retrieveSurvey(@RequestParam (name="name") String name) {

        Collection<QuestionMessage> questionMessages = new ArrayList<>();

        Optional<Survey> survey = repo.findById(name);
        if (survey.isPresent()) {

            for (Question question : survey.get().getQuestions()) {
                if(question instanceof OpenEndedQuestion) {
                    questionMessages.add(new QuestionMessage("openEnded", question.getQuestion(), 0, 0));
                }else if(question instanceof NumberQuestion) {
                    NumberQuestion numQ = (NumberQuestion)question;
                    questionMessages.add(new QuestionMessage("numberQuestion", numQ.getQuestion(), numQ.getMin(), numQ.getMax()));

                }
            }

            return new SurveyMessage(name, questionMessages);
        }
        return new SurveyMessage("", questionMessages);
    }
}
