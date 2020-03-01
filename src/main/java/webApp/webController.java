package webApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import survey.Survey;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class webController {
    @Autowired
    private SurveyRepository repo;

    @GetMapping("/index")
    public String index(Model model){
        return "index";
    }

    @PostMapping("/createSurvey")
    public String createSurvey() {
        return "";
    }

    @PostMapping("/retrieveSurvey")
    @ResponseBody
    public String retrieveSurvey(@RequestParam (name="name") String name, Model model) {
         Survey survey = repo.findByName(name);

         model.addAttribute("survey", survey)

        return "retrieveSurvey";
    }

    @GetMapping("/getSurveys")
    public String getSurveys(Model model) {
        ArrayList<Survey> surveys = repo.findAll().collect(Collectors.toList());

        model.addAttribute("surveys", surveys);
        return "getSurvey";
    }
}
