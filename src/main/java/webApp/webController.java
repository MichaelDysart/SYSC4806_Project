package webApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class webController {
    @Autowired
    private Object repo;

    @PostMapping("/createSurvey")
    public String createSurvey() {
        return "";
    }

    @PostMapping("/retrieveSurvey")
    public String retrieveSurvey() {
        return "";
    }

    @PostMapping("/getSurveys")
    public String getSurveys() {
        return "";
    }
}
