package webApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import survey.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

/*
 * The controller that accepts web requests to update and retrieve surveys
 */
@Controller
public class webController {
    private static final String numQuestionType = "numberQuestion";
    private static final String openQuestionType = "openEnded";
    private static final String dropdownQuestionType = "dropdown";

    @Autowired
    private SurveyRepository repo;

    @GetMapping("/")
    public String index(Model model){
       return "root";
    }

    @PostMapping(value = "/createSurvey", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response createSurvey(@RequestBody SurveyMessage surveyMessage) {

        Collection<Question> questionList = new ArrayList<>();

        ArrayList<String> questionTexts = new ArrayList<>();

        if (surveyMessage.getQuestions() == null) {
            return new Response(null, null, "error", "Questions were null");
        }

        for (QuestionMessage questionMsg : surveyMessage.getQuestions()) {
            if (questionMsg == null) {
                return new Response(null, null, "error", "Question was null");
            }
            questionTexts.add(questionMsg.getQuestion());
            switch (questionMsg.getType()) {
                case openQuestionType:
                    questionList.add(new OpenEndedQuestion(questionMsg.getQuestion()));
                    break;
                case numQuestionType:
                    if (questionMsg.getMin() <= questionMsg.getMax()) {
                        questionList.add(new NumberQuestion(questionMsg.getQuestion(), questionMsg.getMin(), questionMsg.getMax()));
                    } else {
                        return new Response(null, null, "error",
                                "Min is greater than max for question \"" + questionMsg.getQuestion() + "\"");
                    }
                    break;
                case dropdownQuestionType:
                    questionList.add(new DropdownQuestion(questionMsg.getQuestion(), questionMsg.getOptions()));
                    break;
            }
        }

        HashSet<String> set = new HashSet<>(questionTexts);

        if(set.size() < questionTexts.size()){
            return new Response(null, null, "error", "Duplicate questions detected");
        }

        Survey survey = new Survey(surveyMessage.getName());
        survey.setQuestions(questionList);
        repo.save(survey);
        return new Response(survey.getId(), survey.getLink(), "ok", "done");
    }

    @GetMapping(value = "/retrieveSurvey", produces = "application/json")
    @ResponseBody
    public SurveyMessage retrieveSurvey(@RequestParam (name="id", required=false) Integer id, @RequestParam(name="link", required=false) UUID link) {

        Collection<QuestionMessage> questionMessages = new ArrayList<>();

        Optional<Survey> survey = null;

        if (id != null) {
            survey = repo.findById(id);
        } else if (link != null) {
            survey = repo.findByLink(link);
        } else {
            return new SurveyMessage(null, "error", "", false, questionMessages);
        }

        if (survey.isPresent()) {

            for (Question question : survey.get().getQuestions()) {
                if(question instanceof OpenEndedQuestion) {
                    OpenEndedQuestion openQ = (OpenEndedQuestion)question;
                    questionMessages.add(new QuestionMessage(openQuestionType, question.getQuestion(), null, 0, 0, "", 0, openQ.getAnswers(), null));
                } else if(question instanceof NumberQuestion) {
                    NumberQuestion numQ = (NumberQuestion)question;
                    questionMessages.add(new QuestionMessage(numQuestionType, question.getQuestion(), null, numQ.getMin(), numQ.getMax(), "", 0, null, numQ.getAnswers()));
                } else if(question instanceof DropdownQuestion) {
                    DropdownQuestion dropQ = (DropdownQuestion) question;
                    questionMessages.add(new QuestionMessage(dropdownQuestionType, question.getQuestion(), dropQ.getOptions(), 0, 0, "", 0, dropQ.getAnswers(), null));
                }
            }

            return new SurveyMessage(survey.get().getId(), "ok", survey.get().getName(), survey.get().getClosed(), questionMessages);
        }
        return new SurveyMessage(null, "error", "", false, questionMessages);
    }

    @PostMapping(value = "/closeSurvey", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response closeSurvey(@RequestBody SurveyMessage surveyMessage) {
        if (surveyMessage.getId() == null) {
            return new Response(null, null, "error", "Survey id was null");
        }
        Optional<Survey> survey = repo.findById(surveyMessage.getId());
        if (survey.isPresent()) {
            if (!survey.get().getClosed()) {
                survey.get().setClosed(true);
                repo.save(survey.get());
                return new Response(surveyMessage.getId(), null, "ok", "Survey \""+ surveyMessage.getId() +"\" closed successfully");
            } else {
                return new Response(null, null, "error", "Survey \""+ surveyMessage.getId() +"\" already closed");
            }
        } else {
            return new Response(null, null, "error", "Survey \""+ surveyMessage.getId() +"\" not available");
        }
    }

    @PostMapping(value = "/addAnswers", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response addAnswers(@RequestBody SurveyMessage surveyMessage) {
        if (surveyMessage.getQuestions() == null) {
            return new Response(null, null, "error", "Questions were null");
        }
        if (surveyMessage.getId() == null) {
            return new Response(null, null, "error", "Survey id was null");
        }
        Optional<Survey> survey = repo.findById(surveyMessage.getId());
        if (survey.isPresent()) {

            if (!survey.get().getClosed()) {
                for (QuestionMessage questionMsg : surveyMessage.getQuestions()) {
                    if (questionMsg == null) {
                        return new Response(surveyMessage.getId(), null, "error", "Question was null");
                    }
                    Question question = null;
                    for (Question searchQuestion : survey.get().getQuestions()) {
                        if(searchQuestion.getQuestion().equals(questionMsg.getQuestion())) {
                            question = searchQuestion;
                            break;
                        }
                    }
                    if (question == null) {
                        return new Response(surveyMessage.getId(), null, "error", "Missing Question \"" + questionMsg.getQuestion() + "\"");
                    } else if(question instanceof OpenEndedQuestion && questionMsg.getType().equals(openQuestionType)) {
                        ((OpenEndedQuestion) question).addAnswer(questionMsg.getStringAnswer());
                    } else if(question instanceof NumberQuestion && questionMsg.getType().equals(numQuestionType)) {
                        NumberQuestion nQuestion = (NumberQuestion) question;
                        if (questionMsg.getNumberAnswer() <= nQuestion.getMax() &&
                                questionMsg.getNumberAnswer() >= nQuestion.getMin()) {
                            nQuestion.addAnswer(questionMsg.getNumberAnswer());
                        } else {
                            return new Response(surveyMessage.getId(), null, "error",
                                    "Value for question \"" + questionMsg.getQuestion() + "\" outside of range: Want " +
                                            nQuestion.getMin() + " to " + nQuestion.getMax() + " but got " + questionMsg.getNumberAnswer());
                        }
                    } else if(question instanceof DropdownQuestion && questionMsg.getType().equals(dropdownQuestionType)) {
                        DropdownQuestion dQuestion = (DropdownQuestion) question;
                        if (dQuestion.getOptions().contains(questionMsg.getStringAnswer())) {
                            dQuestion.addAnswer(questionMsg.getStringAnswer());
                        } else {
                            return new Response(surveyMessage.getId(), null, "error",
                                    "Answer for question \"" + questionMsg.getQuestion() + "\" is " +
                                            questionMsg.getStringAnswer() + " which is not an option for this question");
                        }
                    } else {
                        String expectedType = "unknown";
                        if(question instanceof OpenEndedQuestion) {
                            expectedType = openQuestionType;
                        } else if(question instanceof NumberQuestion) {
                            expectedType = numQuestionType;
                        } else if(question instanceof DropdownQuestion) {
                            expectedType = dropdownQuestionType;
                        }
                        return new Response(surveyMessage.getId(), null, "error",
                                "Mismatched types for question \"" + questionMsg.getQuestion() + "\": Want " +
                                        expectedType + " but got " + questionMsg.getType());
                    }
                }
                repo.save(survey.get());
                return new Response(surveyMessage.getId(), null, "ok", "answers saved");
            } else {
                return new Response(null, null, "error", "Could not add answers: Survey \""+ surveyMessage.getId() +"\" closed");
            }
        }
        return new Response(null, null, "error", "Survey \""+ surveyMessage.getId() +"\" not available");
    }

    @DeleteMapping(value = "/survey/{id}", produces = "application/json")
    @ResponseBody
    public Response deleteSurvey(@PathVariable int id) {
        Optional<Survey> survey = repo.findById(id);
        if (survey.isPresent()) {
            repo.deleteById(id);
            return new Response(id, null, "ok", "Survey deleted");
        }
        return new Response(id, null, "error", "Survey" + id + " not found");
    }

    @GetMapping(value = "/retrieveSurveyNames", produces = "application/json")
    @ResponseBody
    public SurveyIDList retrieveSurveyNames() {
        List<String> nameList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();

        for (Survey survey : this.repo.findAllSorted()) {
            nameList.add(survey.getName());
            idList.add(survey.getId());
        }

        return new SurveyIDList(nameList, idList);
    }
}
