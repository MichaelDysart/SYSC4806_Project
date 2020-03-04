package webApp;

import java.util.Collection;

public class SurveyMessage {

    private final String name;
    private final Collection<QuestionMessage> questions;

    public SurveyMessage(String name, Collection<QuestionMessage> questions) {
        this.questions = questions;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Collection<QuestionMessage> getQuestions() {
        return questions;
    }
}
