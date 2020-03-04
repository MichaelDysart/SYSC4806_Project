package webApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import repository.SurveyRepository;
import survey.Survey;
import survey.Question;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class webController {
    @Autowired
    private SurveyRepository repo;

    @GetMapping("/index")
    public String index(Model model){
        return "index";
    }

    @PostMapping("/createSurvey")
    public String createSurvey(@RequestParam("surveyName") String surveyName, @RequestBody Collection<Question> questions) {
        Survey survey = new Survey(surveyName);
        survey.setQuestions(questions);
        repo.save(survey);
        return "createSurvey";
    }

    @PostMapping("/retrieveSurvey")
    @ResponseBody
    public String retrieveSurvey(@RequestParam (name="name") String name, Model model) {
         List<Survey> surveys = repo.findByName(name);

         model.addAttribute("survey", surveys);

        return "retrieveSurvey";
    }

    @GetMapping("/getSurveys")
    public String getSurveys(Model model) {
        ArrayList<Survey> surveys = new ArrayList<Survey>();
        Iterator<Survey> iterate = repo.findAll().iterator();
        while (iterate.hasNext()) {
            surveys.add(iterate.next());
        }

        model.addAttribute("surveys", surveys);
        return "getSurvey";
    }
}
